package app.persistence;

import app.entities.TeamTeacherEntitiy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class TeamTeacherMapperTest {

    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private final static String DB = "lifehackspring2025";

    private static ConnectionPool connectionPool;


    @BeforeAll
    public static void setUpClass() {
       connectionPool = ConnectionPool.getInstance(USER,PASSWORD,URL,DB);
            try (Connection testConnection = connectionPool.getConnection()) {
                try (Statement stmt = testConnection.createStatement()) {
                    // Drop eksisterende testtabeller hvis de findes
                    stmt.execute("DROP TABLE IF EXISTS test.keywords_answers");
                    stmt.execute("DROP TABLE IF EXISTS test.answers");
                    stmt.execute("DROP TABLE IF EXISTS test.keywords");
                    stmt.execute("DROP TABLE IF EXISTS test.philosophers");

                    // Drop sekvenser hvis de findes
                    stmt.execute("DROP SEQUENCE IF EXISTS test.philosophers_id_seq CASCADE;");
                    stmt.execute("DROP SEQUENCE IF EXISTS test.keywords_id_seq CASCADE;");
                    stmt.execute("DROP SEQUENCE IF EXISTS test.answers_id_seq CASCADE;");

                    // Opret test-tabeller som kopier af produktionsstrukturen uden data
                    stmt.execute("CREATE TABLE test.philosophers AS (SELECT * FROM public.philosophers) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.keywords AS (SELECT * FROM public.keywords) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.answers AS (SELECT * FROM public.answers) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.keywords_answers AS (SELECT * FROM public.keywords_answers) WITH NO DATA");

                    // Opret sekvenser for auto-generering af ID'er
                    stmt.execute("CREATE SEQUENCE test.philosophers_id_seq");
                    stmt.execute("ALTER TABLE test.philosophers ALTER COLUMN id SET DEFAULT nextval('test.philosophers_id_seq')");

                    stmt.execute("CREATE SEQUENCE test.keywords_id_seq");
                    stmt.execute("ALTER TABLE test.keywords ALTER COLUMN id SET DEFAULT nextval('test.keywords_id_seq')");

                    stmt.execute("CREATE SEQUENCE test.answers_id_seq");
                    stmt.execute("ALTER TABLE test.answers ALTER COLUMN id SET DEFAULT nextval('test.answers_id_seq')");

                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
                fail("Database connection failed");
            }
    }

    @BeforeEach
    public void setUp() {

        try (Connection testConnection = connectionPool.getConnection()){
            try (Statement stmt = testConnection.createStatement()) {

                // Rens testtabeller før hver test
                stmt.execute("TRUNCATE TABLE test.keywords_answers RESTART IDENTITY CASCADE");
                stmt.execute("TRUNCATE TABLE test.answers RESTART IDENTITY CASCADE");
                stmt.execute("TRUNCATE TABLE test.keywords RESTART IDENTITY CASCADE");
                stmt.execute("TRUNCATE TABLE test.philosophers RESTART IDENTITY CASCADE");

                // Indsæt testdata i philosophers-tabellen
                stmt.execute("INSERT INTO test.philosophers (name) VALUES " +
                        "('Sokrates'), " +
                        "('Platon'), " +
                        "('Aristoteles'), " +
                        "('Kierkegaard'), " +
                        "('Nietzsche')");

                // Indsæt testdata i keywords-tabellen
                stmt.execute("INSERT INTO test.keywords (keyword) VALUES " +
                        "('eksistens'), " +
                        "('moral'), " +
                        "('sandhed'), " +
                        "('lykke'), " +
                        "('magt')");

                // Indsæt testdata i answers-tabellen
                stmt.execute("INSERT INTO test.answers (quote, philosopher_id) VALUES " +
                        "('Det uundersøgte liv er ikke værd at leve.', (SELECT id FROM test.philosophers WHERE name='Sokrates'))," +
                        "('Kendskab til det gode er vejen til et dydigt liv.', (SELECT id FROM test.philosophers WHERE name='Platon'))," +
                        "('Vi er, hvad vi gentagne gange gør. Fortræffelighed er derfor ikke en handling, men en vane.', (SELECT id FROM test.philosophers WHERE name='Aristoteles'))," +
                        "('Livet forstås baglæns, men må leves forlæns.', (SELECT id FROM test.philosophers WHERE name='Kierkegaard'))," +
                        "('Gud er død.', (SELECT id FROM test.philosophers WHERE name='Nietzsche'))");

                // Indsæt testdata i keywords_answers-tabellen (mange-til-mange relation)
                stmt.execute("INSERT INTO test.keywords_answers (keyword_id, answer_id) VALUES " +
                        "((SELECT id FROM test.keywords WHERE keyword='eksistens'), (SELECT id FROM test.answers WHERE quote='Det uundersøgte liv er ikke værd at leve.'))," +
                        "((SELECT id FROM test.keywords WHERE keyword='moral'), (SELECT id FROM test.answers WHERE quote='Kendskab til det gode er vejen til et dydigt liv.'))," +
                        "((SELECT id FROM test.keywords WHERE keyword='sandhed'), (SELECT id FROM test.answers WHERE quote='Vi er, hvad vi gentagne gange gør. Fortræffelighed er derfor ikke en handling, men en vane.'))," +
                        "((SELECT id FROM test.keywords WHERE keyword='lykke'), (SELECT id FROM test.answers WHERE quote='Livet forstås baglæns, men må leves forlæns.'))," +
                        "((SELECT id FROM test.keywords WHERE keyword='magt'), (SELECT id FROM test.answers WHERE quote='Gud er død.'))");

            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database setup failed in @BeforeEach");
        }
    }


    @Test
    void getPhilosophicalAnswer() {
        String expectedQuote = "Vi er, hvad vi gentagne gange gør. Fortræffelighed er derfor ikke en handling, men en vane.";
        String expectedPhilosopher = "Aristoteles";
        TeamTeacherEntitiy teamTeacherEntitiy = TeamTeacherMapper.getPhilosophicalAnswer("Hvad er sandhed?", connectionPool);
        assertEquals(expectedQuote,teamTeacherEntitiy.getQuote() );
        assertEquals(expectedPhilosopher, expectedPhilosopher);
    }

    @Test
    void getKeyWordIDs() {
        List<Integer> keywordIDs = TeamTeacherMapper.getKeyWordIDs("Er sandhed og moral lykken?", connectionPool);
        assertEquals(3, keywordIDs.size());

    }
}