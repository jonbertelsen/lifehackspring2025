package app.persistence;

import app.entities.Team05.Workout;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team05Mapper {
    //Retrieves all workout logs from the workoutlog table.
    public static List<Workout> getAllWorkoutLog(ConnectionPool ConnectionPool) throws DatabaseException {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workoutlog";

        try (Connection conn = ConnectionPool.getConnection();  // Sørg for, at connection poolen er korrekt brugt
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Workout workout = new Workout(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getInt("type_id"),
                        rs.getInt("duration"),
                        rs.getDate("date"),
                        rs.getString("extra_Notes")
                );
                workouts.add(workout);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving newsletters", e.getMessage());
        }
        return workouts;
    }
    //Inserts a new workout log into the workoutlog table.
    public static void creatWorkout(Workout workout, ConnectionPool myConnectionPool) throws DatabaseException {
        String sql = "INSERT INTO workoutlog (type_id, duration, date, extra_notes) VALUES (?, ?,CURRENT_DATE,?)";

        try (
                Connection connection = myConnectionPool.getConnection();  // Brug connection poolen
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, workout.getType_id());
            ps.setInt(2, workout.getDuration());
            ps.setDate(3, new java.sql.Date(workout.getDate().getTime()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Error during newsletter insertion:");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved indsættelse af nyhedsbrev: " + e.getMessage());
        }
    }

    //signUp and login: Methods for user authentication.
    // Users can sign up and log in using their email and password.

    public static int signUp(String email, int password, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO users (email,password) VALUES (?,?) ON CONFLICT (email) DO NOTHING";
        try (
                Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setInt(2,password);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
        catch (SQLException e) {
            String msg = "There was an error during your sign-up for the workout log. Please try again.";
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    //Checks if a user exists in the database based on the email provided.
    public static String login(String email, int password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT email , password FROM users WHERE email = ? AND password = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setInt(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("email"); // Returnerer brugerens email ved succesfuldt login
            } else {
                throw new DatabaseException("Incorrect email or password. Please try again.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Login-error: try again", e.getMessage());
        }
    }

    //Deletes a workout log based on the ID.
    public static void deleteWorkoutLog(int workoutId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM workoutlog WHERE id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, workoutId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Error: Workoutlog with ID " + workoutId + " was not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting workout log.", e.getMessage());
        }
    }

    public static void editWorkoutLog(int workoutId, int typeId, int duration, String extraNotes, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE workoutlog" +
                "SET type_id = ?, duration = ?, extra_notes = ?" +
                "WHERE id = ?";

        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setInt(1, typeId);
            ps.setInt(2, duration);
            ps.setString(3, extraNotes);
            ps.setInt(4, workoutId);

        } catch (SQLException e) {
            throw new DatabaseException("Error editing workout log.", e.getMessage());
        }
    }

    public static boolean userExists(String email, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Returnerer true hvis brugeren findes, ellers false
        } catch (SQLException e) {
            throw new DatabaseException("Error checking if user exists.", e.getMessage());
        }
    }

}
