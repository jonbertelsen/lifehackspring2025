package app.controllers.Team05;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team05Mapper;
import io.javalin.http.Context;
import java.util.logging.Logger;


public class HomeController {

    private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName());
    private final ConnectionPool connectionPool;

    public HomeController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void signUp(Context ctx) {
        try {
            String email = ctx.formParam("email");
            String name = ctx.formParam("name");
            int password = Integer.parseInt(ctx.formParam("password"));

            int result = Team05Mapper.signUp(email, password, connectionPool);

            if (result == 1) {
                ctx.status(200).result("Sign-up successful!");
            } else {
                ctx.status(400).result("Sign-up failed.");
            }
        } catch (DatabaseException e) {
            LOGGER.severe("Error during sign-up: " + e.getMessage());
            ctx.status(500).result("Error during sign-up.");
        }
    }

    public void login(Context ctx) {
        try {
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            String userEmail = Team05Mapper.login(email, Integer.parseInt(password), connectionPool);

            if (userEmail != null) {
                ctx.status(200).result("Login successful.");
            } else {
                ctx.status(400).result("Incorrect email or password.");
            }
        } catch (DatabaseException e) {
            LOGGER.severe("Error during login: " + e.getMessage());
            ctx.status(500).result("Error during login.");
        }
    }



}
