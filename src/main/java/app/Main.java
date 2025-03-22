package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;

import app.controllers.Team1Controller;
import app.controllers.Team2Controller;
import app.controllers.Team4Controller;
import app.controllers.Team6Controller;
import app.controllers.Team7Controller;
import app.controllers.LifehackTeam08GameController;
import app.controllers.Team10Controller;
import app.controllers.Team12Controller;
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

    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);
    }      
        // Frontpage
        app.get("/", ctx ->  ctx.render("index.html"));
      
        // Philosophers
        TeamTeacherController.routes(app, connectionPool);
      
        // Team01
        Team1Controller team1Controller = new Team1Controller(connectionPool);
        team1Controller.routes(app);
      
        // Team02
        Team2Controller.routes(app);
        Team2Controller.setConnectionPool(connectionPool);
        app.get("/team2/index", ctx ->  ctx.render("team2/index.html"));

        // Team04
        app.get("/", ctx -> ctx.render("team4login");
        app.post("/login", Team4Controller::handleLogin);
  
        // Team06
        Team6Controller.routes(app, connectionPool);
      
        // Team07
        Team7Controller.add(app, connectionPool);
      
         // Team08
         LifehackTeam08GameController.Routes(app);
      
        // Team10
        Team10Controller.routes(app, connectionPool);
        // Team12
        Team12Controller.addRoutes(app, connectionPool);
      
        // Team17
        LifeHack_Team_17_Controller.routes(app);


   }
}
