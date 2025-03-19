package app.persistence.lifehack_team_16;

import app.entities.lifehack_team_16.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserMapper {

    //Dependency injection
    private static ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public static User Signin(String username, String password) throws DatabaseException{

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(query)){


                ps.setString(1, username);
                ps.setString(2, password);
                int userId = ps.executeQuery().getInt("user_id");

                User user = new User(userId, username, password);
                return user;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get user " + e);
        }
    }
}
