package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.TeamTeacherController;
import app.controllers.lifehack_team_16.UserController;
import app.controllers.lifehack_team_16.WaterLogController;
import app.persistence.lifehack_team_16.ConnectionPool;
import app.persistence.lifehack_team_16.WaterLogMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.logging.Logger;

import static app.controllers.lifehack_team_16.UserController.createUser;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehack_team_16";
    private static int user_id;

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
       app.get("/", ctx ->  ctx.render("lifehack_team_16/index.html"));


        //SignIn endpoints
        app.post("/user/SignIn", ctx -> user_id = userController.login(ctx));
       /* app.get("SignIn", ctx -> {
            ctx.attribute("error", ctx.sessionAttribute("error"));
            ctx.sessionAttribute("error", null); // Nulstil error efter visning
            ctx.render("lifehack_team_16/SignIn.html");
        });

        */


        app.get("createuser", ctx -> ctx.render("lifehack_team_16/createuser.html"));
        app.post("createuser", ctx -> createUser(ctx));
        app.get("SignIn", ctx -> ctx.render("lifehack_team_16/SignIn.html"));
        app.post("addWater", ctx -> WaterLogController.addWater(ctx, WaterLogMapper.getWaterLogByUserId(user_id)));

    }

}