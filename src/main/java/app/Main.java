package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.Team05.HomeController;
import app.controllers.Team05.Team05Controllers;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.logging.Logger;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehackspring2025";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH); // Gør public-mappen tilgængelig
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);


        // Routing

        app.get("/", ctx -> ctx.redirect("/workoutlogger"));
        app.get("/workoutlogger", ctx -> ctx.render("team05/index05.html"));


        // Create instances of the controllers
        HomeController homeController = new HomeController(connectionPool);
        Team05Controllers team05Controllers = new Team05Controllers(connectionPool);

        // Route for at håndtere login og signup
        app.post("/authenticate", homeController::authenticate);

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
}
