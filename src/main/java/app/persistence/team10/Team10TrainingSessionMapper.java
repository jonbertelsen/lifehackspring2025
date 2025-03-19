package app.persistence.team10;

import app.entities.team10.Team10Exercise;
import app.entities.team10.Team10TrainingSession;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Team10TrainingSessionMapper {

    public static List<Team10Exercise> getExercisesByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        List<Team10Exercise> exercises = new ArrayList<>();
        String sql = "SELECT e.exercise_id, e.name, e.description " +
                "FROM exercises e " +
                "JOIN training_session_exercises tse ON e.exercise_id = tse.exercise_id " +
                "JOIN training_sessions ts ON tse.session_id = ts.session_id " +
                "WHERE ts.user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);  // Set userId in query

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int exerciseId = rs.getInt("exercise_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    exercises.add(new Team10Exercise(exerciseId, name, description));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching exercises for user " + userId, e.getMessage());
        }
        return exercises;
    }
}
