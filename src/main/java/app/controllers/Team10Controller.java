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
        app.get("/", ctx -> home(ctx));
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> handleLogin(ctx,connectionPool));
        app.get("/create-user", ctx-> ctx.render("create-user.html"));
        app.post("/create-user", ctx -> handleCreateUser(ctx,connectionPool));

    }

    public static void home(Context ctx) throws DatabaseException {
        ctx.render("index.html");
    }

    // Login method
    private static void handleLogin(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");


        //Check if the user is in the database and check what their role is
        try {
            Team10User user = Team10UserMapper.login(username, password, connectionPool);
            ctx.redirect("homepage.html");
        }catch (DatabaseException e){
            ctx.attribute("message",e.getMessage());
            ctx.redirect("login.html");
        }
    }
    private static void handleCreateUser(Context ctx, ConnectionPool connectionPool) {
        // Retrieve user information from the form
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String role = "user";

        try {
            // Create the new user in the database
            Team10UserMapper.createUser(email, password, connectionPool);
            ctx.attribute("message", "User created successfully!");
            ctx.redirect("/login"); // Redirect to login page after successful user creation
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error creating user: " + e.getMessage());
            ctx.redirect("create-user.html");
        }
    }
}
