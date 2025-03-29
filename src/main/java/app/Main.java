package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;

import app.controllers.Team1Controller;
import app.controllers.Team2Controller;
import app.controllers.Team4Controller;
import app.controllers.Team05.HomeController;
import app.controllers.Team05.Team05Controllers;
import app.controllers.Team6Controller;
import app.controllers.Team7Controller;
import app.controllers.LifehackTeam08GameController;
import app.controllers.LifeHackTeam9Controller;
import app.controllers.Team10Controller;
import app.controllers.Team12Controller;
import app.controllers.Team14Controller;
import app.controllers.lifehack_team_16.Team16Controller;
import app.controllers.LifeHack_Team_17_Controller;
import app.controllers.TeamTeacherController;

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

    public static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {

        LifeHack_Team_17_Controller.setConnectionPool(connectionPool);

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
        Team1Controller team1Controller = new Team1Controller(connectionPool);
        team1Controller.routes(app);
      
        // Team02
        Team2Controller.routes(app);
        Team2Controller.setConnectionPool(connectionPool);

        // Team04
        app.get("/team4", ctx -> ctx.render("team4login"));
        app.post("/login", Team4Controller::handleLogin);
      
      // Team05
      
        // Routing
     
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
      
      
  
        // Team06
        Team6Controller.routes(app, connectionPool);
      
        // Team07
        Team7Controller.add(app, connectionPool);
      
         // Team08
         LifehackTeam08GameController.Routes(app);

        // Team09
        LifeHackTeam9Controller.addRoutes(app, connectionPool);
      
        // Team10
        Team10Controller.routes(app, connectionPool);
        // Team12
        Team12Controller.addRoutes(app, connectionPool);
        
        // Team14
        Team14Controller.registerRoutes(app);

        // Team16
        Team16Controller.routes(app);
      
        // Team17
        LifeHack_Team_17_Controller.routes(app);

   }
}
