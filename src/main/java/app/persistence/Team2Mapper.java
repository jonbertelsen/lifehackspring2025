package app.persistence;


import app.entities.Team2Entity;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team2Mapper {

    public static List<Team2Entity> getAllExcuses(ConnectionPool connectionPool) throws DatabaseException {
        List<Team2Entity> excuseList = new ArrayList<>();
        String sql = "select * from excuse";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String excusetext = rs.getString("excusetext");
                    int sillyness_level = rs.getInt("sillyness_level");
                    int category = rs.getInt("category");

                    Team2Entity excuse = new Team2Entity(id, excusetext, sillyness_level, category);
                    excuseList.add(excuse);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not get users from database");
        }
        return excuseList;
    }

    public String getRandomExcuseByCategory(ConnectionPool connectionPool, int category) throws DatabaseException {
        Team2Entity excuse = null;
        String sql = "SELECT * FROM excuse WHERE category = ? ORDER BY RANDOM() LIMIT 1;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, category);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String excusetext = rs.getString("excusetext");
                    int sillyness_level = rs.getInt("sillyness_level");
                    int category1 = rs.getInt("category");
                    excuse = new Team2Entity(id, excusetext, sillyness_level, category1);
                }
            } catch (SQLException ex) {
                throw new DatabaseException(ex, "Failed to execute SQL query.");
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Failed to obtain database connection.");
        }

        return (excuse != null) ? excuse.getExcusetext() : "No excuse found for this category.";
    }
}