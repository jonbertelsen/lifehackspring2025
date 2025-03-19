package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team1CreateUserMapper {


    public static void createUser(ConnectionPool connectionPool, Team1Entities.Users users) throws DatabaseException {
        String sql = "INSERT INTO users (name, password)\n" +
                "VALUES (?, ?);";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int rowsCreated = ps.executeUpdate();

            ps.setString(1, users.getName());
            ps.setString(2, users.getPassword());

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not insert into user");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not insert user into database", ex.getMessage());
        }
    }
}