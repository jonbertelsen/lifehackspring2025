package app.controllers;

import app.entities.Soda;
import app.persistence.ConnectionPool;
import app.persistence.LifeHackTeam17Mapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LifeHack_Team_17_Controller {

    private static ConnectionPool connectionPool;

    public LifeHack_Team_17_Controller(ConnectionPool connectionPool) {
        LifeHack_Team_17_Controller.connectionPool = connectionPool;
    }

    public static void setConnectionPool(ConnectionPool connectionPool) {
        LifeHack_Team_17_Controller.connectionPool = connectionPool;
    }



    public static void home(Context ctx) {
        LifeHackTeam17Mapper lifeHackTeam17Mapper = new LifeHackTeam17Mapper();
        List<Soda> sodas = lifeHackTeam17Mapper.getSodaFromDataBase(connectionPool);
        ctx.attribute("sodas", sodas);  // Pass soda data to frontend
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
        List<Soda> sodas = new LifeHackTeam17Mapper().getSodaFromDataBase(connectionPool);
        ctx.json(sodas);  // Return the list of sodas as a JSON response
    }
}
