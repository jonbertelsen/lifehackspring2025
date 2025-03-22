package app.controllers;

import app.entities.Team14Entity;
import app.persistence.Team14Mapper;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;
import io.javalin.Javalin;
import java.util.List;

import static app.Main.connectionPool;

public class Team14Controller {
    public static void getMovies(Context ctx) {
        String mainGenre = ctx.queryParam("main-genre");

        try {
            List<Team14Entity> movies = Team14Mapper.getMovies(connectionPool, mainGenre);
            ctx.render("/src/main/resources/templates/team14/index.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error retrieving movies");
        }
    }


    public static void registerRoutes(Javalin app) {
        app.get("/index", Team14Controller::getMovies);
    }
}
