package app.persistence.team01;

import app.entities.team01.Team01Entity;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team01UsersMapper {
    //A method which can create an user
    public static void createUser(ConnectionPool connectionPool, Team01Entity.Users users) throws DatabaseException {
        String sql = "INSERT INTO lifehack_team_1_users (name, password) VALUES (?, ?);";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, users.getName());
            ps.setString(2, users.getPassword());

            int rowsCreated = ps.executeUpdate();

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not insert into user");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not insert user into database", ex.getMessage());
        }
    }
}
