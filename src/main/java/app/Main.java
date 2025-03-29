package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;

import app.controllers.*;
import app.controllers.Team08Controller;
import app.controllers.Team16Controller;

import app.persistence.ConnectionPool;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
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

        // Frontpage
        app.get("/", ctx ->  ctx.render("index.html"));
      
        // Philosophers
        TeamTeacherController.routes(app, connectionPool);
      
        // Team01
        Team01Controller team1Controller = new Team01Controller(connectionPool);
        team1Controller.routes(app);
      
        // Team02
        Team02Controller.routes(app);
        Team02Controller.setConnectionPool(connectionPool);

        // Team03
        Team17Controller.setConnectionPool(connectionPool);
        Team03Controller.routes(app);

        // Team04
        app.get("/team4", ctx -> ctx.render("team4login"));
        app.post("/login", Team04Controller::handleLogin);
      
        // Team05
        Team05Controllers team05Controllers = new Team05Controllers(connectionPool);
        team05Controllers.routes(app);

        // Team06
        Team06Controller.routes(app, connectionPool);
      
        // Team07
        Team07Controller.add(app, connectionPool);
      
         // Team08
         Team08Controller.Routes(app);

        // Team09
        Team09Controller.addRoutes(app, connectionPool);
      
        // Team10
        Team10Controller.routes(app, connectionPool);
      
        // Team11
        app.get("/team11", ctx ->  ctx.render("team11-index.html"));
        Team11Controller.routes(app);

        // Team12
        Team12Controller.addRoutes(app, connectionPool);
        
        // Team14
        Team14Controller.registerRoutes(app, connectionPool);
      
        // Team15
        Team15Controller.team15Controller.routes(app);

        // Team16
        Team16Controller team16Controller = new Team16Controller(connectionPool);
        team16Controller.routes(app);
     
        // Team17
        Team17Controller.routes(app);

        // Team21
        app.get("/team21", ctx ->  ctx.render("/team21/index.html"));
        Team21Controller.routes(app, connectionPool);

   }
}