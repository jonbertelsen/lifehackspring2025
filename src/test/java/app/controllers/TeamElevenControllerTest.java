package app.controllers;

import app.persistence.ConnectionPool;
import app.persistence.Team11Mapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamElevenControllerTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";  // Testdatabase URL
    private static final String DB = "lifehackspring2025";  // Testdatabase navn

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    @BeforeEach
    public void setUp() {
        // Opret testdata i testdatabasen
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "INSERT INTO team11_tariff_rate (country_name, category_name, tariff_rate_in_percent) VALUES ('Germany', 'Cars', 10)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @AfterEach
    public void tearDown() {
        // Ryd testdata efter test
        try (Connection connection = connectionPool.getConnection()) {
            String sql = "DELETE FROM team11_tariff_rate WHERE country_name = 'Germany' AND category_name = 'Cars'";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @Test
    public void testGetTariffRate() {
        double rate = Team11Mapper.getTariffRate("Germany", "Cars");
        assertEquals(10.0, rate, 0.0);  // Tjek om den returnerede tarif er korrekt
    }


}