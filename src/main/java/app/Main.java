package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.TeamTeacherController;
import app.entities.Team1Entities;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team1CategoriesMapper;
import app.persistence.Team1QuestionMapper;
import app.persistence.Team1UsersMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehackspring2025";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) throws DatabaseException, SQLException {
        Team1QuestionMapper qm = new Team1QuestionMapper();

        qm.listOfQuestions(connectionPool, 1);


        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler ->  handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        app.get("/", ctx ->  ctx.render("index.html"));
        TeamTeacherController.routes(app);
    }



}