package app.controllers.Team05;

import app.entities.Team05.Workout;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team05Mapper;
import io.javalin.http.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class Team05Controllers {
    private static final Logger LOGGER = Logger.getLogger(Team05Controllers.class.getName());
    private final ConnectionPool connectionPool;

    public Team05Controllers(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // GET-metode til at vise workout log
    public void viewWorkoutlog(Context ctx) {
        try {
            List<Workout> workoutLog = Team05Mapper.getAllWorkoutLog(connectionPool);
            ctx.attribute("workout", workoutLog);
            ctx.render("index05.html");  // Thymeleaf-side til visning af workout log
        } catch (DatabaseException e) {
            LOGGER.severe("Error retrieving workout log: " + e.getMessage());
            ctx.result("An error occurred while retrieving the workout log.");
        }
    }

    // POST-metode til at håndtere login/signup (behandler formulardata)
    public void handleLoginSignup(Context ctx) {
        try {
            // Hent formulardata
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");
            String action = ctx.formParam("action");

            // Håndter login/signup-logik
            if ("signup".equals(action)) {
                // Håndter signup logik
                // For eksempel kan du tilføje en metode til at oprette en bruger i databasen
                ctx.status(200).result("Signup successful.");
            } else if ("signin".equals(action)) {
                // Håndter signin logik
                // For eksempel kan du validere brugeren mod databasen
                ctx.status(200).result("Signin successful.");
            } else {
                ctx.status(400).result("Invalid action.");
            }
        } catch (Exception e) {
            ctx.status(500).result("Error processing the request.");
            LOGGER.severe("Error handling login/signup: " + e.getMessage());
        }
    }

    // Håndter tilføjelse af workout
    public static void addWorkoutLog(Context ctx, ConnectionPool connectionPool) {
        try {
            // Opret nødvendige mapper, hvis de ikke eksisterer
            Path workoutDir = Path.of("files");
            if (!Files.exists(workoutDir)) {
                Files.createDirectories(workoutDir);
            }

            // Hent og valider formular-data
            String email = ctx.formParam("email");
            String type_id = ctx.formParam("type_id");
            String duration = ctx.formParam("duration");
            String date = ctx.formParam("date");
            String extraNotes = ctx.formParam("extra_notes");
            String password = ctx.formParam("password");

            if (password == null || !password.equals("1234")) {
                ctx.status(403).result("Wrong password!");
                return;
            }
            if (email == null || type_id == null || date == null || duration == null) {
                ctx.status(400).result("Missing required fields.");
                return;
            }

            // Konverter dato korrekt
            LocalDate publishedDate;
            try {
                publishedDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                ctx.status(400).result("Invalid date format. Use YYYY-MM-DD.");
                return;
            }

            // Opret workout objekt
            Workout newWorkout = new Workout(0, email, Integer.parseInt(type_id), Integer.parseInt(duration), java.sql.Date.valueOf(publishedDate), extraNotes);

            // Indsæt i databasen
            Team05Mapper.creatWorkout(newWorkout, connectionPool);

            ctx.status(200).result("Workout successfully added!");
        } catch (IOException e) {
            ctx.status(500).result("Server error while creating directory.");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error saving workout to database.");
        }
    }

    // DELETE-metode til at slette workout log
    public void deleteWorkoutLog(Context ctx) {
        try {
            int workoutId = Integer.parseInt(ctx.pathParam("id"));
            Team05Mapper.deleteWorkoutLog(workoutId, connectionPool);
            ctx.status(200).result("Workout log deleted successfully.");
        } catch (NumberFormatException | DatabaseException e) {
            LOGGER.severe("Error deleting workout log: " + e.getMessage());
            ctx.status(500).result("Error deleting workout log.");
        }
    }

    // EDIT-metode til at redigere workout log
    public void editWorkoutLog(Context ctx) {
        try {
            int workoutId = Integer.parseInt(ctx.pathParam("id"));
            int typeId = Integer.parseInt(ctx.formParam("type_id"));
            int duration = Integer.parseInt(ctx.formParam("duration"));
            String extraNotes = ctx.formParam("extra_notes");

            Team05Mapper.editWorkoutLog(workoutId, typeId, duration, extraNotes, connectionPool);
            ctx.status(200).result("Workout log updated successfully.");
        } catch (NumberFormatException | DatabaseException e) {
            LOGGER.severe("Error editing workout log: " + e.getMessage());
            ctx.status(500).result("Error editing workout log.");
        }
    }
}
