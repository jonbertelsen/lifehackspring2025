import app.entities.Team4ProfileEntity;
import app.persistence.ConnectionPool;
import app.persistence.Team4ProfilMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Team4ProfilMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "lifehackspring2025";

    private static  ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static Team4ProfilMapper profilMapper;

    @BeforeAll
    public static void setUpClass() {
        try {
            connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
            profilMapper = new Team4ProfilMapper(connectionPool);
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
                        "(6, 3, 'Cute dog', 'Doggo', 4), " +
                        "(21, 3, 'Cute cat', 'asdsd', 8), " +
                        "(5, 3, 'Sad chicken', 'Nugget', 12)");


                // Set sequence to continue from the largest member_id
                stmt.execute("SELECT setval('test.id', COALESCE((SELECT MAX(id)+1 FROM test.profile), 1), false)");
            }
        } catch (SQLException e) {
            fail("Database connection failed");
        }
    }

    //Connection is all good
    @Test
    void testConnection() throws SQLException {
        //assertFalse to make test fail
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void getAllProfiles() {
        List<Team4ProfileEntity> profiles = profilMapper.getProfiles();
        assertEquals(3, profiles.size());
        //Change either expected size to 4 or more,
        //Change any of the informatons for the "new" members that are being compared to the old ones
        assertEquals(profiles.get(0), new Team4ProfileEntity(6, 3, "Cute dog", "Doggo", 4));
        assertEquals(profiles.get(1), new Team4ProfileEntity(21, 3, "Cute cat", "asdsd", 8));
        assertEquals(profiles.get(2), new Team4ProfileEntity(5, 3, "Sad chicken", "Nugget", 12));
    }


    /*

    @Test
    void getMemberById() throws DatabaseException {
        //Change the memberId, or change the name to make the test fail
        assertEquals(new Member(3, "Peter Hansen","Ahlegårdsvejen 7",3700,"Rønne","m",2002), memberMapper.getMemberById(3));
    }

    @Test
    void deleteMember() throws DatabaseException {
        //Change the size, you delete one (3 - 1), if the size isn't 2 it will fail
        assertTrue(memberMapper.deleteMember(2));
        assertEquals(2, memberMapper.getAllMembers().size());
    }

    @Test
    void insertMember() throws DatabaseException, IllegalInputException {
        Member m1 = memberMapper.insertMember(new Member("Jon Snow","Wintherfell 3", 3760, "Gudhjem", "m", 1992));
        assertNotNull(m1);
        //Change the size, you add one (3 + 1), if the size isn't 4 it will fail
        assertEquals(4, memberMapper.getAllMembers().size());
        assertEquals(m1, memberMapper.getMemberById(4));
    }

    @Test
    void updateMember() throws DatabaseException {
        boolean result = memberMapper.updateMember(new Member(2, "Jens Kofoed","Agrevej 5",3760,"Gudhjem","m",1999));
        assertTrue(result);
        //To make this test fail: change the memberId or just make something up in the expected values below
        Member m1 = memberMapper.getMemberById(2);
        assertEquals(1999, m1.getYear());
        assertEquals(3, memberMapper.getAllMembers().size());
    }


    @Test
    void deleteTwoMembers() throws DatabaseException {
        //assertTrue instead of assertEquals because these are operations
        assertTrue(memberMapper.deleteMember(1));
        assertTrue(memberMapper.deleteMember(2));
        assertEquals(1, memberMapper.getAllMembers().size());
    }


    @Test
    void deleteMemberUnknownId() throws DatabaseException {
        //Expecting an error to be thrown, and that the error is a DatabaseException(Our own exception)
        //Lambda expression () are the empty parameters, and after the arrows come the body of code to be executed
        assertThrows(DatabaseException.class, () -> memberMapper.deleteMember(12312));
    }

    @Test
    void insertMemberIllegalGender() throws DatabaseException {
        assertThrows(IllegalInputException.class, () -> memberMapper.insertMember(
                //gender 'x' throws an error which in turn makes the test pass, whereas m/k makes the test fail
                new Member("Jon Snow","Wintherfell 3", 3760, "Gudhjem", "y", 1992)));
    }



}
*/



}
