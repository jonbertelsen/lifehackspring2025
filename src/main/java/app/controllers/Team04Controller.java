package app.controllers;

import app.entities.team04.Team04Profile;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.team04.Team04ProfileMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Team04Controller {

private static ConnectionPool connectionPool;


    public static void handleLogin(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
            ctx.sessionAttribute("email", email);
            ctx.redirect("/index");
        } else {
            ctx.redirect("/login?error=Invalid credentials");
        }
    }



    public static void routes(Javalin app, ConnectionPool connectionPool) {
        app.get("/team4", ctx -> team4Home(ctx));
        app.post("/team4", ctx -> searchProfile(ctx, connectionPool));
    }

    private static void team4Home(@NotNull Context ctx) {
        ctx.render("teat4/index.html");
    }

    public static void searchProfile(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String query = ctx.queryParam("query");
        if (query == null || query.trim().isEmpty()){
            String msg = "Søgefeltet må ikke være tomt!";
            ctx.attribute("message", msg);
            ctx.redirect("/");
            return;
        }
        List<Team04Profile> results = Team04ProfileMapper.searchProfiles(query, connectionPool);
        ctx.attribute("profiles", results);
        ctx.render("/index.html");
    }
}
