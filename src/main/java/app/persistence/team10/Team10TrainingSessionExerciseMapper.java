package app.persistence.team10;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team10TrainingSessionExerciseMapper {

    public static void addExerciseToSession(int sessionId, int exerciseId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO training_session_exercises (session_id, exercise_id) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, sessionId);
            ps.setInt(2, exerciseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved tilføjelse af øvelse til session", e.getMessage());
        }
    }
}

