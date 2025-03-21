package app.persistence.lifehack_team_16;

import app.entities.lifehack_team_16.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper {

    //Dependency injection
    private static ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public static User Signin(String username, String password, ConnectionPool connectionPool) throws DatabaseException{

        String query = "SELECT * FROM lifehack_team_16_users WHERE username = ? AND password = ?";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(query)){


                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    int userId = rs.getInt("user_id");
                    return new User(userId, username, password);
                } else {
                    throw new DatabaseException("Fejl i login. Prøv igen");
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get user " + e);
        }
    }

    public static void createuser(String userName, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into lifehack_team_16_users (username, password) values (?,?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, userName);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        }
        catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value "))
            {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}
