package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team7Mapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class Team7Controller {
    public static void add(Javalin app, ConnectionPool connectionPool) {

        app.get("/DadJokes", ctx -> dadJokes(ctx, connectionPool));
        app.get("/MomJokes", ctx -> ctx.render("team7_frontpage.html"));
        app.get("/JesperJokes", ctx -> ctx.render("team7_frontpage.html"));
        app.get("/NationalJokes", ctx -> ctx.render("team7_frontpage.html"));
        app.get("/BlondeJokes", ctx -> ctx.render("team7_frontpage.html"));
        app.get("/DatamatikerJokes", ctx -> ctx.render("team7_frontpage.html"));
        app.post("/login", ctx -> ctx.render("team7_frontpage.html"));
    }

    private static void dadJokes(@NotNull Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        Random random = new Random();
        int jokeId = random.nextInt(5);  // lav et tilfældigt tal

        String joke = Team7Mapper.getJoke1ById(connectionPool, jokeId);
        ctx.attribute("joke", joke);
        ctx.render("team7_frontpage.html");
    }

}