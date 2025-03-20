package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.LifehackTeam3Controller;
import app.controllers.TeamTeacherController;
import app.exceptions.LifeHackTeam3DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.LifeHackTeam3SubscriberMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehackspring2025";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static LifeHackTeam3SubscriberMapper subscriberMapper = new LifeHackTeam3SubscriberMapper();


    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("index.html"));
        //TeamTeacherController.routes(app);
        LifehackTeam3Controller.routes(app);


    }
}


