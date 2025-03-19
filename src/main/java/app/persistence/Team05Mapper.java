package app.persistence;

import app.entities.Team05.WorkoutLog;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team05Mapper {
    public static List<WorkoutLog> getAllWorkoutLog(ConnectionPool ConnectionPool) throws DatabaseException {
        List<WorkoutLog> workoutLogs = new ArrayList<>();
        String sql = "SELECT * FROM workoutlog";

        try (Connection conn = ConnectionPool.getConnection();  // Sørg for, at connection poolen er korrekt brugt
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                WorkoutLog workoutLog = new WorkoutLog(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getInt("type_id"),
                        rs.getInt("duration"),
                        rs.getDate("date")
                );
                workoutLogs.add(workoutLog);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af nyhedsbreve", e.getMessage());
        }
        return workoutLogs;
    }
    public static void creatWorkout(WorkoutLog workoutLog, ConnectionPool myConnectionPool) throws DatabaseException {
        String sql = "INSERT INTO workoutlog (email, type_id, duration, date) VALUES (?, ?, ?, CURRENT_DATE)";

        try (
                Connection connection = myConnectionPool.getConnection();  // Brug connection poolen
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, workoutLog.getEmail());
            ps.setInt(2, workoutLog.getType_id());
            ps.setInt(3, workoutLog.getDuration());
            ps.setDate(4, new java.sql.Date(workoutLog.getDate().getTime()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af nyhedsbrev");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved indsættelse af nyhedsbrev: " + e.getMessage());
        }
    }

    public static int signUp(String email, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO user (email, name,password) VALUES (?,?,?) ON CONFLICT (email) DO NOTHING";
        try (
                Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
        catch (SQLException e) {
            String msg = "Der er sket en fejl under din signUp til workout log. Prøv igen";
            throw new DatabaseException(msg, e.getMessage());
        }
    }

}
