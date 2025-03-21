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
import java.util.Map;
import java.util.logging.Logger;

public class Team05Controllers {
    private static final Logger LOGGER = Logger.getLogger(Team05Controllers.class.getName());
    private final ConnectionPool connectionPool;

    public Team05Controllers(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    //This method fetches all workout logs from the database and sends them to the view (team05/log.html).
    public void viewWorkoutlog(Context ctx) {
        try {
            // Hent workout logs fra databasen
            List<Workout> workoutLog = Team05Mapper.getAllWorkoutLog(connectionPool);

            // Log hvad der er hentet
            System.out.println("Antal logs hentet: " + workoutLog.size());

            // Send workout logs til Thymeleaf
            ctx.attribute("workout", workoutLog);  // Sørg for at listen er korrekt sendt til Thymeleaf
            ctx.render("team05/log.html");
        } catch (DatabaseException e) {
            LOGGER.severe("Error retrieving workout log: " + e.getMessage());
            ctx.result("An error occurred while retrieving the workout log.");
        }
    }


    //This method adds a new workout log. It validates the incoming data,
    // handles directory creation for file storage (if necessary),and inserts the workout log into the database.
    public static void addWorkoutlog(Context ctx, ConnectionPool connectionPool) {
        try {
            // Opret nødvendige mapper, hvis de ikke eksisterer
            Path workoutDir = Path.of("files");

            if (!Files.exists(workoutDir)) {
                Files.createDirectories(workoutDir);
            }

            // Hent og valider formular-data
            String type_id = ctx.formParam("type_id");
            String duration = ctx.formParam("duration");
            String date = ctx.formParam("date");
            String extraNotes = ctx.formParam("extra_notes");

            if (type_id == null || date == null || duration == null) {
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
            Workout newWorkout = new Workout(1,Integer.parseInt(type_id), Integer.parseInt(duration), java.sql.Date.valueOf(publishedDate), extraNotes);

            // Indsæt i databasen
            Team05Mapper.creatWorkout(newWorkout, connectionPool);

            ctx.redirect("/workoutlog");
        } catch (IOException e) {
            ctx.status(500).result("Server error while creating directory.");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error saving workout to database.");
        }
    }

    //This method deletes a workout log based on the ID passed in the URL.
    public void deleteWorkoutLog(Context ctx) {
        int workoutId = Integer.parseInt(ctx.pathParam("id"));

        try {
            Team05Mapper.deleteWorkoutLog(workoutId, connectionPool);
            ctx.redirect("/workoutlog");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error deleting workout log.");
        }
    }

    // This method updates an existing workout log with new details (type, duration, and extra notes).
    public void editWorkoutLog(Context ctx) {
        String workoutIdStr = ctx.pathParam("id");  // Hent ID fra URL'en som en streng
        if (workoutIdStr == null || workoutIdStr.isEmpty()) {
            ctx.status(400).result("Workout ID is missing.");
            return;
        }

        try {
            int workoutId = Integer.parseInt(workoutIdStr);  // Konverter ID til int
            // Hent workout-log fra databasen (hvis nødvendigt for visning af data på redigeringssiden)
            Workout workoutLog = Team05Mapper.getWorkoutById(workoutId, connectionPool);

            if (workoutLog == null) {
                ctx.status(404).result("Workout log not found.");
                return;
            }

            // Hent opdaterede data fra formularen (POST-anmodning)
            int typeId = Integer.parseInt(ctx.formParam("type_id"));
            int duration = Integer.parseInt(ctx.formParam("duration"));
            String extraNotes = ctx.formParam("extra_notes");

            // Kald metoden for at opdatere workout-loggen
            Team05Mapper.editWorkoutLog(workoutId, typeId, duration, extraNotes, connectionPool);

            // Efter opdateringen kan du enten vise et succes-besked eller omdirigere til en listevisning
            ctx.redirect("/workoutlog");

        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid workout ID or invalid input.");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error updating workout log: " + e.getMessage());
        }
    }


    // Håndter redigering af en workout (viser edit-siden)
    public void showEditWorkoutPage(Context ctx) throws DatabaseException {
        int workoutId = Integer.parseInt(ctx.pathParam("id")); // Henter ID fra URL
        Workout workout = Team05Mapper.getWorkoutById(workoutId, connectionPool); // Hent workout fra DB
        ctx.attribute("workout", workout); // Sender workout-data til Thymeleaf
        ctx.render("edit_workout.html"); // Render HTML-siden
    }
}
