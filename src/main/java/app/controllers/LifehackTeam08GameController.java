package app.controllers;

import app.entities.LifehackTeam08User;
import app.persistence.LifehackTeam08UserMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.math.BigInteger;

public class LifehackTeam08GameController {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "chicken_clicker"
    );
    private static final LifehackTeam08UserMapper userMapper = new LifehackTeam08UserMapper(connectionPool);

    public static void Routes(Javalin app) {
        app.get("/", ctx ->  ctx.render("lifehack-team-08/login.html"));
        app.get("/login", LifehackTeam08GameController::loginPage);
        app.post("/login", LifehackTeam08GameController::loginUser);
        app.get("/lifehack-team-08/register", LifehackTeam08GameController::registerPage);
        app.post("/lifehack-team-08/register", LifehackTeam08GameController::registerUser);
        app.get("/lifehack-team-08/index", LifehackTeam08GameController::gamePage);
        app.get("/lifehack-team-08/logout", LifehackTeam08GameController::logout);
    }

    public static void loginPage(Context ctx) {
        ctx.render("lifehack-team-08/login.html");
    }

    public static void loginUser(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        LifehackTeam08User user = userMapper.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("user authenticated:" + user.getUsername());
            ctx.sessionAttribute("user", user);
            ctx.redirect("/lifehack-team-08/index");
        } else {
            System.out.println("Invalid login attempt: " + username);
            ctx.sessionAttribute("Error", "Invalid username or password.");
            ctx.redirect("/login");
        }
    }

    public static void registerPage(Context ctx) {
        ctx.render("/lifehack-team-08/register.html");
    }

    public static void registerUser(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        LifehackTeam08User existingUser = userMapper.getUserByUsername(username);
        if (existingUser != null) {
            ctx.sessionAttribute("Error", "Username already exists.");
            ctx.redirect("/lifehack-team-08/register");
        } else {
            userMapper.createUser(username, password);
            ctx.redirect("/login");
        }
    }

    public static void gamePage(Context ctx) {
        LifehackTeam08User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.redirect("/login");
        } else {
            System.out.println("rendering index html for user: " + user.getUsername());
            ctx.render("/lifehack-team-08/index.html");
        }
    }

    public static void logout(Context ctx) {
        ctx.sessionAttribute("user", null);
        ctx.redirect("/login");
    }


}
