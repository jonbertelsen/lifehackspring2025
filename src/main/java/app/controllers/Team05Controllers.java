package app.controllers;

import app.entities.team05.Team05Workout;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.team05.Team05Mapper;
import io.javalin.Javalin;
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

    public void routes(Javalin app) {

        app.get("/workoutlogger", ctx -> ctx.render("team05/index05.html"));

        // Create instances of the controllers
        Team05Controllers team05Controllers = new Team05Controllers(connectionPool);

        // Route for at håndtere login og signup
        app.post("/authenticate", ctx -> authenticate(ctx));

        // Hvis login er succesfuldt, omdiriger til log.html
        app.get("team05/log", ctx -> {
            ctx.render("team05/log.html");  // Sørg for at filen er i den rigtige mappe
        });

        // Rute for upload.html
        app.get("/upload", ctx -> {
            ctx.render("team05/upload.html");  // Sørg for at filen er i den rigtige mappe
        });

        app.post("/add-workout", ctx -> Team05Controllers.addWorkoutlog(ctx, connectionPool));
        app.get("/workoutlog", team05Controllers::viewWorkoutlog);

        app.get("/editworkoutlog/{id}", team05Controllers::showEditWorkoutPage);
        app.post("/editworkoutlog/{id}", team05Controllers::editWorkoutLog);

        app.post("/deleteworkoutlog/{id}", team05Controllers::deleteWorkoutLog);
    }


    //This method fetches all workout logs from the database and sends them to the view (team05/log.html).
    public void viewWorkoutlog(Context ctx) {
        try {
            // Hent workout logs fra databasen
            List<Team05Workout> team05WorkoutLog = Team05Mapper.getAllWorkoutLog(connectionPool);

            // Log hvad der er hentet
            System.out.println("Antal logs hentet: " + team05WorkoutLog.size());

            // Send workout logs til Thymeleaf
            ctx.attribute("workout", team05WorkoutLog);  // Sørg for at listen er korrekt sendt til Thymeleaf
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
            Team05Workout newTeam05Workout = new Team05Workout(1, Integer.parseInt(type_id), Integer.parseInt(duration), java.sql.Date.valueOf(publishedDate), extraNotes);

            // Indsæt i databasen
            Team05Mapper.creatWorkout(newTeam05Workout, connectionPool);

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
            ctx.status(400).result("Team05Workout ID is missing.");
            return;
        }

        try {
            int workoutId = Integer.parseInt(workoutIdStr);  // Konverter ID til int
            // Hent workout-log fra databasen (hvis nødvendigt for visning af data på redigeringssiden)
            Team05Workout team05WorkoutLog = Team05Mapper.getWorkoutById(workoutId, connectionPool);

            if (team05WorkoutLog == null) {
                ctx.status(404).result("Team05Workout log not found.");
                return;
            }

            // Hent opdaterede data fra formularen (POST-anmodning)
            int typeId = Integer.parseInt(ctx.formParam("type_id"));
            String durationStr = ctx.formParam("duration");
            if (durationStr == null || durationStr.isEmpty()) {
                ctx.status(400).result("Duration is missing or invalid.");
                return;
            }

            int duration;
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                ctx.status(400).result("Duration must be a valid number.");
                return;
            }

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
        Team05Workout team05Workout = Team05Mapper.getWorkoutById(workoutId, connectionPool); // Hent team05Workout fra DB
        ctx.attribute("team05Workout", team05Workout); // Sender team05Workout-data til Thymeleaf
        ctx.render("team05/edit_workout.html");// Render HTML-siden
    }

    //metode, der håndterer brugerens login- og tilmeldingsanmodninger.
    public void authenticate(Context ctx) {
        try {
            //Hentning af brugerinput: Får email, password, og action
            // (dvs. hvad brugeren forsøger at gøre - tilmelde sig eller logge ind) fra formularen.
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");
            String action = ctx.formParam("action");

            // Håndter sign-up
            if ("signup".equals(action)) {
                //Hvis brugeren forsøger at tilmelde sig, parses password som et heltal, og der tjekkes,
                // om en bruger med den angivne email allerede eksisterer i databasen.
                int passwordInt = Integer.parseInt(password);
                boolean userExists = Team05Mapper.userExists(email, connectionPool);

                //Hvis brugeren allerede eksisterer, returneres en fejl. Hvis ikke, forsøger systemet at oprette brugeren med signUp-metoden.
                //Hvis tilmeldingen lykkes, redirects brugeren til log.html, ellers vises en fejlmeddelelse.

                if (userExists) {
                    ctx.status(400).result("Team05User already exists. Please log in.");
                } else {
                    int result = Team05Mapper.signUp(email, passwordInt, connectionPool);
                    if (result == 1) {
                        ctx.status(200).redirect("/workoutlog");  // Redirect til log.html
                    } else {
                        ctx.status(400).result("Sign-up failed.");
                    }
                }
            } else if ("signin".equals(action)) {

                //Hvis brugeren forsøger at logge ind, forsøger systemet at validere loginoplysningerne med login-metoden.

                String userEmail = Team05Mapper.login(email, Integer.parseInt(password), connectionPool);

                if (userEmail != null) {
                    ctx.sessionAttribute("user", userEmail);  // Gem bruger i session
                    ctx.redirect("/workoutlog");  // Redirect til log.html
                } else {
                    ctx.status(400).result("Incorrect email or password.");
                }
            } else {
                ctx.status(400).result("Invalid action.");
            }
        } catch (DatabaseException e) {
            LOGGER.severe("Error during authentication: " + e.getMessage());
            ctx.status(500).result("Error during authentication.");
        }
    }
}
