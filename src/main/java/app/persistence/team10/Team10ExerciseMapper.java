package app.persistence.team10;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import app.entities.team10.Team10Exercise;



public class Team10ExerciseMapper {
        public static List<Team10Exercise> getAllExercises(ConnectionPool connectionPool) throws DatabaseException {
            String sql = "SELECT * FROM exercises";

            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                List<Team10Exercise> exercises = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("exercise_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    exercises.add(new Team10Exercise(id, name,description));
                }
                return exercises;
            } catch (SQLException e) {
                throw new DatabaseException("Error retrieving exercises", e.getMessage());
            }
        }

    }


