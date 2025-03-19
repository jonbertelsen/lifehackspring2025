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
            throw new DatabaseException("Fejl ved hentning af nyhedsbreve", e.getMessage());
        }
        return workouts;
    }
    public static void creatWorkout(Workout workout, ConnectionPool myConnectionPool) throws DatabaseException {
        String sql = "INSERT INTO workoutlog (email, type_id, duration, date) VALUES (?, ?, ?, CURRENT_DATE)";

        try (
                Connection connection = myConnectionPool.getConnection();  // Brug connection poolen
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, workout.getEmail());
            ps.setInt(2, workout.getType_id());
            ps.setInt(3, workout.getDuration());
            ps.setDate(4, new java.sql.Date(workout.getDate().getTime()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af nyhedsbrev");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved indsættelse af nyhedsbrev: " + e.getMessage());
        }
    }

    public static int signUp(String email, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO users (email, name,password) VALUES (?,?,?) ON CONFLICT (email) DO NOTHING";
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

    public static int login(String email, int password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT email, password FROM users WHERE email = ? AND password = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setInt(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("email"); // Returner brugerens ID ved succesfuldt login
            } else {
                return -1; // Indikerer fejl (forkert email eller password)
            }
        } catch (SQLException e) {
            throw new DatabaseException("Login-fejl: Prøv igen", e.getMessage());
        }
    }

    public static void deleteWorkoutLog(int workoutId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM workoutlog WHERE id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, workoutId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Error: Workoutlog with ID " + workoutId + " blev ikke fundet.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning af workoutlog", e.getMessage());
        }
    }

    public static void editWorkoutLog(int workoutId, int typeId, int duration, String extraNotes, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE workoutlog" +
                "SET typed_id = ?, duration = ?, extra_notes = ?" +
                "WHERE workout_id = ?";

        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
                ){
                ps.setInt(1, typeId);
                ps.setInt(2, duration);
                ps.setString(3, extraNotes);
                ps.setInt(4, workoutId);

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved redigering af workoutlog", e.getMessage());
        }
    }
}
