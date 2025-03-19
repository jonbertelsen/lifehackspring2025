package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.TeamTeacherController;
import app.controllers.lifehack_team_16.UserController;
import app.persistence.lifehack_team_16.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehack_team_16_waterlog";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static final UserController userController = new UserController(connectionPool);
    public static void main(String[] args)
    {


        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public.lifehack_team_16");
            config.jetty.modifyServletContextHandler(handler ->  handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing
        app.get("/", ctx ->  ctx.render("index.html"));
        TeamTeacherController.routes(app);


        //SignIn endpoints
        app.post("/user/signIn", ctx -> userController.login(ctx));
        app.get("/signIn", ctx -> {
            ctx.attribute("error", ctx.sessionAttribute("error"));
            ctx.sessionAttribute("error", null); // Nulstil error efter visning
            ctx.render("signIn.html");
        });

        app.get("/user/signIn", ctx -> ctx.redirect("/signIn"));

    }

}