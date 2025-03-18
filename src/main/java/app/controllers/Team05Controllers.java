package app.controllers;

import app.entities.Team05.WorkoutLog;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team05Controllers {
    public static List<WorkoutLog> getAllWorkoutLog(ConnectionPool ConnectionPool) throws DatabaseException {
        List<WorkoutLog> newsletters = new ArrayList<>();
        String sql = "SELECT * FROM workoutlog";

        try (Connection conn = ConnectionPool.getConnection();  // Sørg for, at connection poolen er korrekt brugt
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                WorkoutLog newsletter = new WorkoutLog(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getInt("type_id"),
                        rs.getInt("duration"),
                        rs.getDate("date")
                );
                newsletters.add(newsletter);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af nyhedsbreve", e.getMessage());
        }
}
