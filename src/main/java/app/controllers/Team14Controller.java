package app.controllers;

import app.entities.team14.Team14Entity;
import app.persistence.ConnectionPool;
import app.persistence.team14.Team14Mapper;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;
import io.javalin.Javalin;
import java.util.List;

public class Team14Controller {
    public static void getMovies(Context ctx, ConnectionPool connectionPool) {
        String mainGenre = ctx.queryParam("main-genre");

        try {
            List<Team14Entity> movies = Team14Mapper.getMovies(connectionPool, mainGenre);
            ctx.attribute("movies", movies);
            ctx.render("index.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error retrieving movies");
        }
    }

    public static void registerRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/team14-index", ctx ->  Team14Controller.getMovies(ctx, connectionPool));
    }
}
