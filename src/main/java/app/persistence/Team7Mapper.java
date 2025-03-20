package app.persistence;

import app.entities.Team7Admin;
import app.exceptions.DatabaseException;

import java.sql.*;


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

   public static String getJoke1ById(ConnectionPool connectionPool, int id ) throws DatabaseException{
        String sql = "select joke_names from team_7_joke where joke_id = ?";
        String firstName = null;

        try(Connection connection = connectionPool.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                firstName = rs.getString("joke_names");
            }

        }catch(SQLException e){
            throw new DatabaseException("failed while trying to fetch the joke", e.getMessage());
        }
        return firstName;
   }


}
