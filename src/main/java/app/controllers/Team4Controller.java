package app.controllers;

import app.entities.Team4ProfileEntity;
import app.persistence.ConnectionPool;
import app.persistence.Team4ProfileMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Team4Controller {

private static ConnectionPool connectionPool;

    public static void searchProfile(Context ctx){
        String query = ctx.queryParam("query");
        if (query == null || query.trim().isEmpty()){
            String msg = "Søgefeltet må ikke være tomt!";
            ctx.attribute("message", msg);
            ctx.redirect("/");
            return;
        }
        List<Team4ProfileEntity> results = Team4ProfileMapper.searchProfiles(query, connectionPool);
        ctx.attribute("profiles", results);
        ctx.render("/index.html");
    }
}
