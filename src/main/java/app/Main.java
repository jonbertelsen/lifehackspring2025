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
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "workoutlog";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH); // Gør public-mappen tilgængelig
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);


        // Routing

        app.get("/", ctx -> ctx.render("team05/index05.html"));

        // Create instances of the controllers
        HomeController homeController = new HomeController(connectionPool);
        Team05Controllers team05Controllers = new Team05Controllers(connectionPool);

        // Route for at håndtere login og signup
        app.post("/authenticate", homeController::authenticate);

        // Hvis login er succesfuldt, omdiriger til log.html
        app.get("/team05/log.html", ctx -> {
            ctx.render("team05/log.html");  // Sørg for at filen er i den rigtige mappe
        });

        // Rute for upload.html
        app.get("/upload.html", ctx -> {
            ctx.render("team05/upload.html");  // Sørg for at filen er i den rigtige mappe
        });

        // Register routes for Team05Controllers
        app.get("/workoutlog", team05Controllers::viewWorkoutlog);
        app.post("/add-workout", ctx -> Team05Controllers.addWorkoutLog(ctx,connectionPool));
        app.delete("/deleteworkoutlog", team05Controllers::deleteWorkoutLog);
        app.put("/editworkoutlog", team05Controllers::editWorkoutLog);
    }
}
