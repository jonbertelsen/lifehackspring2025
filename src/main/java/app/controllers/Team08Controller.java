package app.controllers;

import app.entities.team08.Team08User;
import app.persistence.team08.Team08UserMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.math.BigInteger;

public class Team08Controller {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "chicken_clicker"
    );
    private static final Team08UserMapper userMapper = new Team08UserMapper(connectionPool);
    private static Team08Controller controller = new Team08Controller();

    public static void Routes(Javalin app) {

        app.get("/lifehack-team-08/load-game", Team08Controller::loadUserData);


        app.get("/team8", ctx ->  ctx.render("lifehack-team-08/login.html"));
     //   app.get("/login", Team08Controller::loginPage);
     //   app.post("/login", Team08Controller::loginUser);
        app.get("/lifehack-team-08/register", Team08Controller::registerPage);
        app.post("/lifehack-team-08/register", Team08Controller::registerUser);
        app.get("/lifehack-team-08/index", Team08Controller::gamePage);
        app.get("/lifehack-team-08/logout", Team08Controller::logout);

        app.post("/lifehack-team-08/save", Team08Controller::saveUserData);
    }

    public static void loginPage(Context ctx) {
        ctx.render("lifehack-team-08/login.html");
    }

    public static void loginUser(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        Team08User user = userMapper.getUserByUsername(username);

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

        Team08User existingUser = userMapper.getUserByUsername(username);
        if (existingUser != null) {
            ctx.sessionAttribute("Error", "Username already exists.");
            ctx.redirect("/lifehack-team-08/register");
        } else {
            userMapper.createUser(username, password);
            ctx.redirect("/login");
        }
    }

    public static void gamePage(Context ctx) {
        Team08User user = ctx.sessionAttribute("user");

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


    public static void loadUserData(Context ctx) {
        Team08User user = ctx.sessionAttribute("user");

        if (user != null) {
            // Retrieve the user data (including eggs, chickenFeedTier, predatorTier)
            Team08User updatedUser = userMapper.getUserById(user.getId());

            if (updatedUser != null) {
                // Send the data to the front-end as JSON (Thanks ChatGPT)
                ctx.json(new Object() {
                    public final BigInteger eggs = updatedUser.getEggs();
                    public final int chickenFeedTier = updatedUser.getChickenFeedTier();
                    public final int predatorTier = updatedUser.getPredatorTier();
                });
            } else {

                ctx.json(new Object() {
                    public final BigInteger eggs = BigInteger.ZERO;
                    public final int chickenFeedTier = 0;
                    public final int predatorTier = 0;
                });
            }
        }
    }


    public static void saveUserData(Context ctx) {
        Team08User user = ctx.sessionAttribute("user");

        if (user != null) {
            // Retrieve form parameters
            int eggs = Integer.parseInt(ctx.formParam("eggs"));
            int chickenFeedTier = Integer.parseInt(ctx.formParam("chickenFeedTier"));
            int predatorTier = Integer.parseInt(ctx.formParam("predatorTier"));

            // Update user data
            user.setEggs(BigInteger.valueOf(eggs));
            user.setChickenFeedTier(chickenFeedTier);
            user.setPredatorTier(predatorTier);

            // Save to the database
            userMapper.updateUser(user);

            // Respond with a success message
            ctx.result("Game saved successfully!");
        } else {
            ctx.result("Team05User not logged in.");
        }
    }





}



