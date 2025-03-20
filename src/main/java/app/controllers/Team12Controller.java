package app.controllers;

import app.entitites.Team12User;
import app.exceptions.Team12DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team12SleepMapper;
import app.persistence.Team12UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Timestamp;

public class Team12Controller {

    // Configures routes for the application
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> ctx.render("/team12/team12_index.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("/team12_main", ctx -> ctx.render("/team12/team12_main.html"));
        app.get("/logout", ctx -> logout(ctx));
        app.get("/createuser", ctx -> ctx.render("/team12/team12_createuser.html"));
        app.post("/createuser", ctx -> createUser(ctx, connectionPool));

        app.post("/calculate", ctx -> sleep(ctx, connectionPool));
        app.get("/dashboard", ctx -> ctx.render("/team12/team12_dashboard.html"));
    }
    // Handles user creation and adds the user to the database
    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2)) {
            try {
                Team12UserMapper.createUser(username, password1, connectionPool);
                ctx.attribute("message", "You are now created " + username + ". Now you have to log in.");
                ctx.render("/team12/team12_index.html");
            } catch (Team12DatabaseException e) {
                ctx.attribute("message", "User already exists, try again or log in.");
                ctx.render("/team12/team12_createuser.html");
            }
        } else {
            ctx.attribute("message", "Passwords do not match, try again.");
            ctx.render("/team12/team12_createuser.html");
        }
    }
    // Logs out the user by invalidating the session
    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }
    // Handles login requests and validates user credentials
    public static void login(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        System.out.println("Login attempt for user: " + username);

        try {
            Team12User user = Team12UserMapper.login(username, password, connectionPool);

            if (user == null) {
                System.out.println("User not found in database.");
                ctx.attribute("message", "Invalid username or password.");
                ctx.render("/team12/team12_index.html");
                return;
            }

            ctx.sessionAttribute("currentUser", user);
            System.out.println("User logged in successfully: " + user.getUsername());

            ctx.redirect("/team12_main");

        } catch (Team12DatabaseException e) {
            System.out.println("Database error: " + e.getMessage());
            ctx.attribute("message", "Login failed. Please try again.");
            ctx.render("/team12/team12_index.html");
        }
    }
    // Processes sleep data and saves it to the database
    private static void sleep(Context ctx, ConnectionPool connectionPool) {
        Team12User currentUser = ctx.sessionAttribute("currentUser");
        String sleepStartString = ctx.formParam("sleep_start").replace('T', ' ') + ":00"; // Add seconds
        String sleepEndString = ctx.formParam("sleep_end").replace('T', ' ') + ":00"; // Add seconds

        try {
            assert currentUser != null;
            Timestamp sleepStart = Timestamp.valueOf(sleepStartString);
            Timestamp sleepEnd = Timestamp.valueOf(sleepEndString);

            Team12SleepMapper.saveSleepData(currentUser.getUserId(), sleepStart, sleepEnd, connectionPool);
            ctx.attribute("message", "Sleep data recorded for " + currentUser.getUsername());
            ctx.render("/team12/team12_main.html");
        } catch (Team12DatabaseException e) {
            ctx.attribute("message", "Failed to record sleep data, please try again.");
            ctx.render("/team12/team12_main.html");
        } catch (IllegalArgumentException e) {
            ctx.attribute("message", "Invalid date format. Please use the correct format.");
            ctx.render("/team12/team12_main.html");
        }
    }
}
