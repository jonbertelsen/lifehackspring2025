package app.persistence.lifehack_team_16;

import app.entities.lifehack_team_16.User;
import app.entities.lifehack_team_16.WaterLog;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class WaterLogMapper {

    private static ConnectionPool connectionPool;

    public WaterLogMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public static WaterLog addWaterlog(WaterLog waterLog) throws DatabaseException, SQLException {

        String sql = "INSERT INTO lifehack_team_16_waterlog (water_goal, water_ml, date) " +
                "VALUES (?,?,?) RETURNING waterlog_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, waterLog.getWaterGoal());
            ps.setInt(2, waterLog.getWaterMl());
            ps.setObject(3, waterLog.getDate());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt("waterlog_id");

                    return new WaterLog(generatedId, waterLog.getWaterGoal(), waterLog.getWaterMl(), waterLog.getDate());
                } else {
                    throw new DatabaseException("Fejl ved tilføjelse af din waterlog: Ingen ID returneret.");
                }

            } catch (SQLException e) {
                String msg = "Fejl ved indsættelse a waterlog";
                throw new DatabaseException(msg);
            }

        }
    }

    public static boolean updateWaterLog(WaterLog waterLog) throws DatabaseException {
        boolean result = false;
        String sql = "UPDATE lifehack_team_16_waterlog SET water_ml = 250";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(2, waterLog.getWaterMl());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                result = true;
            } else {
                throw new DatabaseException("WaterLog with id: " + waterLog.getWaterLogId() + "could not be updated");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not insert member in database");
        }

        return result;
    }

    public static WaterLog getWaterLogByUserId(int userId) throws DatabaseException {
        WaterLog waterLog = null;
        String sql = "SELECT * FROM lifehack_team_16.waterlog WHERE user_id = ? AND date = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                    int water_goal = rs.getInt("water_goal");
                    int water_ml = rs.getInt("water_ml");
                    Object date = rs.getObject("date");
                    waterLog = new WaterLog(water_goal, water_ml, (LocalDate) date);
                } else {
                    throw new DatabaseException("user with id = " + userId + " is not found");
                }
            } catch (SQLException ex) {
                throw new DatabaseException("Could not find user with id = " + userId + " in database");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not establish connection to database");
        }
        return waterLog;
    }


}









