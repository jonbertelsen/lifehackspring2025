import app.entities.Team4ProfileEntity;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team4ProfileMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Team4ProfileMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "lifehackspring2025";

    private static  ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static Team4ProfileMapper profileMapper;

    @BeforeAll
    public static void setUpClass() {
        try {
            connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
            profileMapper = new Team4ProfileMapper(connectionPool);
            try (Connection testConnection = connectionPool.getConnection())
            {
                try (Statement stmt = testConnection.createStatement())
                {
                    // The test schema is already created, so we only need to delete/create test tables
                    stmt.execute("DROP TABLE IF EXISTS test.profile");


                    // Create tables as copy of original public schema structure
                    stmt.execute("CREATE TABLE test.profile AS (SELECT * from public.profile) WITH NO DATA");

                    // Create sequences for auto generating id's for members and sports
                    stmt.execute("DROP SEQUENCE IF EXISTS test.id CASCADE;");
                    stmt.execute("CREATE SEQUENCE test.id");
                    stmt.execute("ALTER TABLE test.profile ALTER COLUMN id SET DEFAULT nextval('test.id')");

                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
                 fail("Database connection failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
        //Runs once at the beginning of EVERY test, so if you run 5 tests, it will run 5 times
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                //Clean up, removing any data inside each of the five tables
                stmt.execute("DELETE FROM test.profile");

                // Reset the sequence number
                stmt.execute("SELECT setval('test.id', COALESCE((SELECT MAX(id) FROM test.profile), 1), false)");

                // Insert rows
                stmt.execute("INSERT INTO test.profile VALUES " +
                        "(1, 101, 'Loves to bark and play fetch.', 'Doggo', 5, 'doggo@example.com'), " +
                        "(2, 202, 'Graceful and agile, enjoys napping in sunbeams.', 'Kitty', 3, 'kitty@example.com'), " +
                        "(3, 303, 'Chirpy and energetic, loves flying around.', 'Birdie', 2, 'birdie@example.com'), " +
                        "(4, 404, 'Small but mighty, always on the move.', 'Hammy', 1, 'hammy@example.com')");


                // Set sequence to continue from the largest member_id
                stmt.execute("SELECT setval('test.id', COALESCE((SELECT MAX(id)+1 FROM test.profile), 1), false)");
            }
        } catch (SQLException e) {
            fail("Database connection failed");
        }
    }

    //Connection is all good
    @Test
    void testConnection() throws SQLException { //Test is successful
        //assertFalse to make test fail
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void getAllProfiles() { //Test is successful whhen Team4ProfileEntity object has equals has been overiden
        List<Team4ProfileEntity> profiles = profileMapper.getAllProfiles();
        assertEquals(3, profiles.size());
        //Change either expected size to 4 or more,
        //Change any of the informatons for the "new" members that are being compared to the old ones
        assertEquals(profiles.get(0), new Team4ProfileEntity(1, 101, "Loves to bark and play fetch.", "Doggo", 5));
        assertEquals(profiles.get(1), new Team4ProfileEntity(2, 202, "Graceful and agile, enjoys napping in sunbeams.", "Kitty", 3));
        assertEquals(profiles.get(2), new Team4ProfileEntity(3, 303, "Chirpy and energetic, loves flying around.", "Birdie", 2));
    }

/*

    @Test
    //Test will fail because test data does not have email parameter
    void deleteProfile() throws DatabaseException {
        List<Team4ProfileEntity> profiles = profileMapper.getAllProfiles();
        //Change the size, you delete one (3 - 1), if the size isn't 2 it will fail
        assertTrue(profileMapper.deleteProfile(connectionPool, "doggo@example.com"));
        assertEquals(3, profileMapper.getAllProfiles().size());
    }


    @Test
    void updateMember() throws DatabaseException {
        profileMapper.updateProfile(connectionPool, new Team4ProfileEntity(2, 3760,"Gudhjem","m",1999));
        int x = profileMapper.getAllProfiles().size();
        assertEquals(x, 3);
    }

*/


}
