package app.persistence.team10;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Team10TrainingSessionExerciseMapper {

    public static void addExerciseToSession(int sessionId, int exerciseId, ConnectionPool connectionPool) throws DatabaseException {
        String checkSql = "SELECT COUNT(*) FROM training_session_exercises WHERE session_id = ? AND exercise_id = ?";
        String insertSql = "INSERT INTO training_session_exercises (session_id, exercise_id) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection()) {
            // First, check if the exercise is already added to the session
            try (PreparedStatement checkPs = connection.prepareStatement(checkSql)) {
                checkPs.setInt(1, sessionId);
                checkPs.setInt(2, exerciseId);
                ResultSet rs = checkPs.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    // If the exercise is already added, skip the insert
                    return;
                }
            }

            // If not already added, insert the new record
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setInt(1, sessionId);
                ps.setInt(2, exerciseId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved tilføjelse af øvelse til session", e.getMessage());
        }
    }

}

