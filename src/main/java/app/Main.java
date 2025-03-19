package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.LifeHack_Team_17_Controller;
import app.controllers.TeamTeacherController;
import app.persistence.ConnectionPool;
import app.persistence.LifeHackTeam17Mapper;
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

    public static void main(String[] args)
    {


        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler ->  handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing
        LifeHack_Team_17_Controller lifeHack_team_17_controller = new LifeHack_Team_17_Controller(connectionPool);

        //app.get("/", ctx ->  ctx.render("index.html"));
        app.get("/", ctx -> lifeHack_team_17_controller.home(ctx));
        app.get("/calculator", ctx -> lifeHack_team_17_controller.home(ctx));


        TeamTeacherController.routes(app);
        LifeHack_Team_17_Controller.routes(app);


    }

}