package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.TeamTeacherController;
import app.entities.Team1Entities;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team1AnswerMapper;
import app.persistence.Team1QuestionMapper;
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

    public static void main(String[] args) throws DatabaseException {
        Team1QuestionMapper questionMapper = new Team1QuestionMapper();

        questionMapper.createAnswer(connectionPool, new Team1Entities.Questions(100,"spg","svar"),new Team1Entities.Categories(1,"Java", null));


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