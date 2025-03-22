package app.controllers;

import app.entitites.Team12SleepRecords;
import app.entitites.Team12User;
import app.exceptions.Team12DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team12SleepMapper;
import app.persistence.Team12UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class Team12Controller {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        // app.get("/", ctx -> ctx.render("/team12/team12_index.html"));
        app.get("/team12", ctx -> ctx.render("/team12/team12_index.html"));
        app.post("/team12/login", ctx -> login(ctx, connectionPool));
        app.get("/team12/tracker", ctx -> ctx.render("/team12/team12_tracker.html"));
        app.get("/team12/logout", Team12Controller::logout);
        app.get("/team12/createuser", ctx -> ctx.render("/team12/team12_createuser.html"));
        app.post("/team12/createuser", ctx -> createUser(ctx, connectionPool));
        app.post("/team12/calculate", ctx -> sleep(ctx, connectionPool));
        app.get("/team12/dashboard", ctx -> ctx.render("/team12/team12_dashboard.html"));
        app.get("/team12/sleep-data", ctx -> fetchSleepData(ctx, connectionPool));

    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (!password1.equals(password2)) {
            ctx.attribute("message", "Passwords do not match, try again.");
            ctx.render("/team12/team12_createuser.html");
            return;
        }

        try {
            Team12UserMapper.createUser(username, password1, connectionPool);
            ctx.attribute("message", "User created successfully. Please log in.");
            ctx.render("/team12/team12_index.html");
        } catch (Team12DatabaseException e) {
            ctx.attribute("message", "User already exists. Try again or log in.");
            ctx.render("/team12/team12_createuser.html");
        }
    }

    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/team12");
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            Team12User user = Team12UserMapper.login(username, password, connectionPool);

            if (user == null) {
                ctx.attribute("message", "Invalid username or password.");
                ctx.render("/team12/team12_index.html");
                return;
            }

            ctx.sessionAttribute("currentUser", user);
            ctx.redirect("/team12/tracker");
        } catch (Team12DatabaseException e) {
            ctx.attribute("message", "Login failed. Please try again.");
            ctx.render("/team12/team12_index.html");
        }
    }

    private static void sleep(Context ctx, ConnectionPool connectionPool) {
        Team12User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser == null) {
            ctx.attribute("message", "User not logged in.");
            ctx.render("/team12/team12_tracker.html");
            return;
        }

        try {
            Timestamp sleepStart = Timestamp.valueOf(ctx.formParam("sleep_start").replace('T', ' ') + ":00");
            Timestamp sleepEnd = Timestamp.valueOf(ctx.formParam("sleep_end").replace('T', ' ') + ":00");

            Team12SleepMapper.saveSleepData(currentUser.getUserId(), sleepStart, sleepEnd, connectionPool);
            ctx.attribute("message", "Sleep data recorded.");
        } catch (Team12DatabaseException e) {
            ctx.attribute("message", "Failed to record sleep data.");
        } catch (IllegalArgumentException e) {
            ctx.attribute("message", "Invalid date format.");
        }
        ctx.render("/team12/team12_tracker.html");
    }

    private static void fetchSleepData(Context ctx, ConnectionPool connectionPool) {
        Team12User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser == null) {
            ctx.status(401).json(Map.of("error", "User not logged in"));
            return;
        }

        try {
            List<Team12SleepRecords> sleepRecords = Team12SleepMapper.getSleepDataByUserId(currentUser.getUserId(), connectionPool);
            ctx.json(sleepRecords);
        } catch (Team12DatabaseException e) {
            ctx.status(500).json(Map.of("error", "Failed to fetch sleep data"));
        }
    }
}