package app.persistence.team10;

import app.entities.team10.Team10Exercise;
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

    public static List<Team10Exercise> getExercisesByUserIdAndDate(int userId, String date, ConnectionPool connectionPool) throws DatabaseException {
        List<Team10Exercise> exercises = new ArrayList<>();

        String sql = "SELECT e.exercise_id, e.name, e.description FROM exercises e " +
                "JOIN training_session_exercises tse ON e.exercise_id = tse.exercise_id " +
                "JOIN training_sessions ts ON tse.session_id = ts.session_id " +
                "WHERE ts.user_id = ? AND ts.session_date = TO_DATE(?, 'YYYY-MM-DD')";


        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int exerciseId = rs.getInt("exercise_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                exercises.add(new Team10Exercise(exerciseId, name, description));

            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching exercises for user on " + date, e.getMessage());
        }

        return exercises;
    }


    public static int getOrCreateSession(int userId, ConnectionPool connectionPool) throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            // Tjek om der findes en session for i dag
            String checkSql = "SELECT session_id FROM training_sessions WHERE user_id = ? AND session_date = CURRENT_DATE";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("session_id"); // Returner eksisterende session
            } else {
                // Opret en ny session
                String insertSql = "INSERT INTO training_sessions (user_id, session_date) VALUES (?, CURRENT_DATE)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setInt(1, userId);
                insertStmt.executeUpdate();

                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Kunne ikke oprette session.");
                }
            }
        }
    }
}

