package app.controllers;

import app.entities.team10.Team10User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.team10.Team10UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class Team10Controller {

    //Routing
    public static void routes(Javalin app,ConnectionPool connectionPool) {
        app.get("/team10", ctx -> home(ctx));
        app.get("/login", ctx -> ctx.render("team10/login.html"));
        app.post("/login", ctx -> handleLogin(ctx,connectionPool));
        app.get("/create-user", ctx-> ctx.render("team10/create-user.html"));
        app.post("/create-user", ctx -> handleCreateUser(ctx,connectionPool));
        app.get("/homepage", ctx -> ctx.render("team10/homepage.html"));

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
