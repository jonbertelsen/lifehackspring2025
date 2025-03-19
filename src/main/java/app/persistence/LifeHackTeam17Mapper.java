package app.persistence;
import app.entities.Soda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LifeHackTeam17Mapper {

    private static ConnectionPool connectionPool;

    public ArrayList<Soda> getSodaFromDataBase(ConnectionPool connectionPool) {
        ArrayList<Soda> sodaList = new ArrayList<>();

        String sql = "SELECT * FROM \"Lifehack_Team_17_Soda\" ORDER BY name";//TODO: Check in later if you need to add ASC or DECS
        //Creates the connection between server and software
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();
            //As long as the server still has another row of data we will go through them to get the data and use it to make object with it
            while(rs.next()) {
                String name = rs.getString("Name");
                float liter = rs.getFloat("Liter");
                sodaList.add(new Soda(name, liter));
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return sodaList;
    }
}
