package app.controllers;

import app.entities.Soda;
import app.persistence.ConnectionPool;
import app.persistence.LifeHackTeam17Mapper;
import io.javalin.http.Context;

import java.util.List;

public class LifeHack_Team_17_Controller {

    private static ConnectionPool connectionPool;

    public static void home(Context ctx) {
        LifeHackTeam17Mapper lifeHackTeam17Mapper = new LifeHackTeam17Mapper();
        List<Soda> sodas = lifeHackTeam17Mapper.getSodaFromDataBase(connectionPool);
        ctx.attribute("sodas", sodas);
        ctx.render("calculator.html");
    }

}

