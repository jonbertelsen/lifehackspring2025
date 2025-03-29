package app.persistence.team17;

import app.entities.team17.Team17Soda;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Team17Mapper {

    private static ConnectionPool connectionPool;

    public ArrayList<Team17Soda> getSodaFromDataBase(ConnectionPool connectionPool) {
        ArrayList<Team17Soda> team17SodaList = new ArrayList<>();

        String sql = "SELECT * FROM lifehack_team_17_soda ORDER BY name";
        //Creates the connection between server and software
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();
            //As long as the server still has another row of data we will go through them to get the data and use it to make object with it
            while(rs.next()) {
                String name = rs.getString("name");
                float liter = rs.getFloat("liter");
                team17SodaList.add(new Team17Soda(name, liter));
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return team17SodaList;
    }


        // Hardcoded calorie values per liter for different sodas
        private static final Map<String, Integer> sodaCalories = new HashMap<>();

        static {
            sodaCalories.put("Coca-Cola", 150); // 150 calories per liter
            sodaCalories.put("Pepsi", 160);     // 160 calories per liter
            sodaCalories.put("Sprite", 130);    // 130 calories per liter
            sodaCalories.put("Fanta", 140);     // 140 calories per liter
        }

        // Method to calculate total calories
        public static int calculateCalories(Team17Soda team17Soda) {
            int baseCalories = sodaCalories.getOrDefault(team17Soda.getName(), 0); // Default to 0 if team17Soda name is not found
            return baseCalories * (int) team17Soda.getLiter();
        }

        //I just needed to add something different
    }



