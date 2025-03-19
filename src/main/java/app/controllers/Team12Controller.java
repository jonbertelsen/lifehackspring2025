package app.controllers;

import app.entitites.Team12User;
import app.exceptions.Team12DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team12SleepMapper;
import app.persistence.Team12UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class Team12Controller {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> ctx.render("/team12/team12_index.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("/team12_main", ctx -> ctx.render("/team12/team12_main.html"));
        app.get("/logout", ctx -> logout(ctx));
        app.get("/createuser", ctx -> ctx.render("/team12/team12_createuser.html"));
        app.post("/createuser", ctx -> createUser(ctx, connectionPool));

        app.post("/calculate", ctx -> sleep(ctx, connectionPool));
    }

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

    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

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

    private static void sleep(Context ctx, ConnectionPool connectionPool) {
        ctx.sessionAttribute("currentUser", ctx.sessionAttribute("currentUser"));
        String sleepStart = ctx.formParam("sleep_start");
        String sleepEnd = ctx.formParam("sleep_end");
        String sleepDuration = ctx.formParam("sleep_duration");

        try {
            Team12SleepMapper.sleep(sleepStart, sleepEnd, sleepDuration, connectionPool);
            ctx.attribute("message", "You are now created " + Team12User.getUsername() + ". Now you have to log in.");
            ctx.render("/team12/team12_index.html");
        } catch (Team12DatabaseException e) {
            ctx.attribute("message", "User already exists, try again or log in.");
            ctx.render("/team12/team12_createuser.html");
        }
    }
}


