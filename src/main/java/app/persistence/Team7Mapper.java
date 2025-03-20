package app.persistence;

import app.entities.Team7Admin;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Team7Mapper {
    public static Team7Admin login(String userName, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "select * from team_7_admin where team_7_username=? and team_7_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int id = rs.getInt("user_id");
                String role = rs.getString("role");
                return new Team7Admin(id, userName, password, role);
            } else
            {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

   public static int subscribe(String email, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO team_7_subscribe (email) VALUES (?)";

        try(
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, email);
            return ps.executeUpdate();
        }catch(SQLException e){
            String msg = "It failed";
            throw new DatabaseException(msg, e.getMessage());
        }
   }

}
