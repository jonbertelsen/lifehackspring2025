package app.controllers;

import app.entities.team10.Team10Exercise;
import app.entities.team10.Team10TrainingSession;
import app.entities.team10.Team10User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.team10.Team10ExerciseMapper;
import app.persistence.team10.Team10TrainingSessionExerciseMapper;
import app.persistence.team10.Team10TrainingSessionMapper;
import app.persistence.team10.Team10UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Team10Controller {

    //Routing
    public static void routes(Javalin app,ConnectionPool connectionPool) {
        app.get("/team10", ctx -> home(ctx));
        app.get("/login", ctx -> ctx.render("team10/login.html"));
        app.post("/login", ctx -> handleLogin(ctx,connectionPool));
        app.get("/create-user", ctx-> ctx.render("team10/create-user.html"));
        app.post("/create-user", ctx -> handleCreateUser(ctx,connectionPool));
        app.get("/homepage", ctx -> ctx.render("team10/homepage.html"));

        app.get("/start-workout", ctx -> startWorkout(ctx, connectionPool));
        app.get("/workout-history", ctx -> workoutHistory(ctx, connectionPool));

        app.post("/start-workout/{exerciseId}", ctx -> {
            Integer userId = ctx.sessionAttribute("user_id"); // Hent userId fra sessionen

            if (userId == null) {
                ctx.json(Map.of("message", "User not logged in."));
                return;
            }

            int exerciseId = Integer.parseInt(ctx.pathParam("exerciseId"));

            try {
                int sessionId = Team10TrainingSessionMapper.getOrCreateSession(userId, connectionPool);
                Team10TrainingSessionExerciseMapper.addExerciseToSession(sessionId, exerciseId, connectionPool);

                ctx.json(Map.of("message", "Workout startet for session ID: " + sessionId));
            } catch (SQLException e) {
                ctx.json(Map.of("message", "Fejl: " + e.getMessage()));
            }
        });


    }


private static void startWorkout(Context ctx, ConnectionPool connectionPool) {
    try {
        List<Team10Exercise> exercises = Team10ExerciseMapper.getAllExercises(connectionPool);
        ctx.attribute("exercises", exercises);  // Pass exercises to the template
        ctx.render("team10/start-workout.html");  // Render start workout page with exercises
    } catch (DatabaseException e) {
        ctx.attribute("message", "Error fetching exercises: " + e.getMessage());
        ctx.render("team10/homepage.html");
    }
}

    private static void workoutHistory(Context ctx, ConnectionPool connectionPool) {
        Integer userId = ctx.sessionAttribute("user_id");  // Retrieve the logged-in user's ID from the session
        try {
            List<Team10Exercise> exerciseHistory = Team10TrainingSessionMapper.getExercisesByUserId(userId, connectionPool);
            ctx.attribute("exerciseHistory", exerciseHistory);  // Pass exercise history to the template
            ctx.render("team10/workout-history.html");  // Render the workout history page
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error fetching workout history: " + e.getMessage());
            ctx.render("team10/homepage.html");
        }
    }


    public static void home(Context ctx) throws DatabaseException {
        ctx.render("team10/index.html");
    }

    // Login method
    private static void handleLogin(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");


        //Check if the user is in the database and check what their role is
        try {
            Team10User user = Team10UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("user_id",user.getUserId());
            ctx.redirect("/homepage");
        }catch (DatabaseException e){
            ctx.attribute("message",e.getMessage());
            ctx.redirect("/login");
        }
    }
    private static void handleCreateUser(Context ctx, ConnectionPool connectionPool) {
        // Retrieve user information from the form
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String role = "user"; // Standard role

        try {
            // Create the new user in the database
            Team10UserMapper.createUser(email, password, connectionPool);
            ctx.attribute("message", "User created successfully!");
            ctx.redirect("/login"); // Redirect to login page after successful user creation
        } catch (DatabaseException e) {
            // If the email is already in use, display an error message
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Email already in use")) {
                ctx.attribute("message", "This email is already in use. Please login or use another email.");
            } else {
                ctx.attribute("message", "Error creating user: " + e.getMessage());
            }
            // Stay on the create-user page if there is an error
            ctx.render("team10/create-user.html");
        }
    }

}
