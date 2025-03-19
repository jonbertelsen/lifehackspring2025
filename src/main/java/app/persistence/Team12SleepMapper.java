package app.persistence;

import app.exceptions.Team12DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Team12SleepMapper {

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
}
