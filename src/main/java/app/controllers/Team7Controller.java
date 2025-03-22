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
        app.get("/Jokes", ctx -> Jokes(ctx, connectionPool));
        app.post("/login", ctx -> ctx.render("team7_frontpage.html"));
    }

    private static  void Jokes(@NotNull Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        Random random = new Random();
        int maxJokeId = Team7Mapper.selectAllJokes(connectionPool).size();
        int jokeId = random.nextInt(maxJokeId);  // lav et tilfældigt tal

        String joke = Team7Mapper.getJoke1ById(connectionPool, jokeId);
        ctx.attribute("joke", joke);
        ctx.render("team7_frontpage.html");
    }





}
