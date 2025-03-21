package app.persistence;

import app.entitites.Team12SleepRecords;
import app.exceptions.Team12DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Team12SleepMapper {

    // Saves sleep data (user ID, start time, end time) to the database
    public static void saveSleepData(int userId, Timestamp sleepStart, Timestamp sleepEnd, ConnectionPool connectionPool) throws Team12DatabaseException {
        String sql = "INSERT INTO team12_sleep_records (user_id, sleep_start, sleep_end) VALUES (?, ?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setTimestamp(2, sleepStart);
            ps.setTimestamp(3, sleepEnd);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new Team12DatabaseException("Failed to insert sleep data.");
            }
        } catch (SQLException e) {
            String msg = "An error occurred. Please try again.";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Duplicate entry error.";
            }
            System.out.println("Error in database: " + e.getMessage());
            throw new Team12DatabaseException(msg, e.getMessage());
        }
    }

    public static List<Team12SleepRecords> getAllSleepRecords(ConnectionPool connectionPool) throws Team12DatabaseException {
        List<Team12SleepRecords> sleepRecords = new ArrayList<>();
        String sql = "SELECT user_id, sleep_start, sleep_end FROM team12_sleep_records";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                Timestamp sleepStart = rs.getTimestamp("sleep_start");
                Timestamp sleepEnd = rs.getTimestamp("sleep_end");

                double sleepDuration = (sleepEnd.getTime() - sleepStart.getTime()) / (1000.0 * 60 * 60); // Convert to hours

                sleepRecords.add(new Team12SleepRecords(0, userId, sleepStart, sleepStart, sleepEnd, sleepDuration, ""));
            }
        } catch (SQLException e) {
            throw new Team12DatabaseException("Database error while fetching sleep data", e.getMessage());
        }

        return sleepRecords;
    }


}
