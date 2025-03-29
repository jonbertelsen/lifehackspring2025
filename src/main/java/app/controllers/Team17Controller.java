package app.controllers;

import app.entities.team17.Team17Soda;
import app.persistence.ConnectionPool;
import app.persistence.team17.Team17Mapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Team17Controller {

    private static ConnectionPool connectionPool;

    public Team17Controller(ConnectionPool connectionPool) {
        Team17Controller.connectionPool = connectionPool;
    }

    public static void setConnectionPool(ConnectionPool connectionPool) {
        Team17Controller.connectionPool = connectionPool;
    }



    public static void home(Context ctx) {
        Team17Mapper team17Mapper = new Team17Mapper();
        List<Team17Soda> team17Sodas = team17Mapper.getSodaFromDataBase(connectionPool);
        ctx.attribute("team17Sodas", team17Sodas);  // Pass soda data to frontend
    }


    public static void routes(Javalin app) {
        app.get("/calculator", ctx -> team17Calculator(ctx));
        app.get("/consequence", ctx -> team17Consequence(ctx));
        app.get("/get-sodas", ctx -> getSodas(ctx));  // Endpoint to fetch sodas as JSON
    }

    private static void team17Calculator(@NotNull Context ctx) {
        home(ctx);
        ctx.render("lifehack-team-17/calculator.html");  // Render calculator page
    }

    private static void team17Consequence(@NotNull Context ctx) {
        ctx.render("lifehack-team-17/consequences.html");  // Render consequences page
    }

    private static void getSodas(Context ctx) {
        List<Team17Soda> team17Sodas = new Team17Mapper().getSodaFromDataBase(connectionPool);
        ctx.json(team17Sodas);  // Return the list of team17Sodas as a JSON response
    }
}
