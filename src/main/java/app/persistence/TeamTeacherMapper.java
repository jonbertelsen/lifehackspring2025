package app.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TeamTeacherMapper {


    public static List<Integer> getKeyWordIDs(String input, ConnectionPool connectionPool){
        List<Integer> keywordIDs = new ArrayList<>();
        String[] words = input.toLowerCase().split("\\s+");
        String query = "SELECT keyword, keyword_id FROM keywords";
        try (Connection conn = connectionPool.getConnection()){
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             while (rs.next()) {
                 String keyword = rs.getString("keyword").toLowerCase();
                 int keywordID = rs.getInt("id");
                 for (String word : words) {
                     if (word.equals(keyword)) {
                         keywordIDs.add(keywordID);
                         break; // Undgår at tilføje samme keyword flere gange
                     }
                 }
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keywordIDs;
    }



}
