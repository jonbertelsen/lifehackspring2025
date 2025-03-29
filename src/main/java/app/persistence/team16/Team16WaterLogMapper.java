package app.persistence.team16;

import app.entities.team16.Team16WaterLog;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Team16WaterLogMapper {

    private static ConnectionPool connectionPool;

    public Team16WaterLogMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public static Team16WaterLog addWaterlog(Team16WaterLog team16WaterLog) throws DatabaseException, SQLException {

        String sql = "INSERT INTO lifehack_team_16_waterlog (water_goal, water_ml, date) " +
                "VALUES (?,?,?) RETURNING waterlog_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, team16WaterLog.getWaterGoal());
            ps.setInt(2, team16WaterLog.getWaterMl());
            ps.setObject(3, team16WaterLog.getDate());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt("waterlog_id");

                    return new Team16WaterLog(generatedId, team16WaterLog.getWaterGoal(), team16WaterLog.getWaterMl(), team16WaterLog.getDate());
                } else {
                    throw new DatabaseException("Fejl ved tilføjelse af din waterlog: Ingen ID returneret.");
                }

            } catch (SQLException e) {
                String msg = "Fejl ved indsættelse a waterlog";
                throw new DatabaseException(msg);
            }

        }
    }

    public static void updateWaterLog(Team16WaterLog team16WaterLog) throws DatabaseException {
        String sql = "UPDATE lifehack_team_16_waterlog SET water_ml = 250";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(2, team16WaterLog.getWaterMl());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
            } else {
                throw new DatabaseException("Team16WaterLog with id: " + team16WaterLog.getWaterLogId() + "could not be updated");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not insert member in database");
        }

    }

    public static Team16WaterLog getWaterLogByUserId(int userId) throws DatabaseException {
        Team16WaterLog team16WaterLog = null;
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
                    team16WaterLog = new Team16WaterLog(water_goal, water_ml, (LocalDate) date);
                } else {
                    throw new DatabaseException("user with id = " + userId + " is not found");
                }
            } catch (SQLException ex) {
                throw new DatabaseException("Could not find user with id = " + userId + " in database");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not establish connection to database");
        }
        return team16WaterLog;
    }


}









