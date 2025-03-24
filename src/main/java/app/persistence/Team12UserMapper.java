package app.persistence;

import app.entitites.Team12User;
import app.exceptions.Team12DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Team12UserMapper {

        // Authenticates the user by verifying username and password from the database
    public static Team12User login(String username, String password, ConnectionPool connectionPool) throws Team12DatabaseException {
        String sql = "select * from team12_users where username=? and password_hash=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                return new Team12User(id, username, password, createdAt);
            } else {
                throw new Team12DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new Team12DatabaseException("DB fejl", e.getMessage());
        }
    }
    // Creates a new user by inserting username and password into the database
    public static void createUser(String username, String password, ConnectionPool connectionPool) throws Team12DatabaseException {
        String sql = "insert into team12_users (username, password_hash) values (?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new Team12DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new Team12DatabaseException(msg, e.getMessage());
        }
    }


}

