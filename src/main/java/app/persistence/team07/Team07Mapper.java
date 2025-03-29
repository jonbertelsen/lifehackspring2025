package app.persistence.team07;

import app.entities.team07.Team07Admin;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;


public class Team07Mapper {
    public static Team07Admin login(String userName, String password, ConnectionPool connectionPool) throws DatabaseException
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
                return new Team07Admin(id, userName, password, role);
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

   // make a list with Team07Jokes Select * Joke return list for loop

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

    public static ArrayList<String> selectAllJokes(ConnectionPool connectionPool ) throws DatabaseException {
        ArrayList<String> JokeNames = new ArrayList<>();
        String sql = "SELECT joke_names FROM team_7_joke";
        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                String row = rs.getString("joke_names");
                JokeNames.add(row);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return JokeNames;
    }

}
