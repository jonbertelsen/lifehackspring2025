package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.team02.Team02Mapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class Team02Controller {
    private static ConnectionPool connectionPool;

    public static void setConnectionPool(ConnectionPool pool) {
        connectionPool = pool;
    }

    public static void routes(Javalin app) {
        app.get("/team2/index", ctx -> team2FrontHome(ctx));
        app.post("/team2/index", ctx -> randomExcuse(ctx));
    }

    private static void team2FrontHome(@NotNull Context ctx) {
        ctx.render("team2/index.html");
    }

    public static void randomExcuse(Context ctx) {
        try {
            int category = Integer.parseInt(ctx.formParam("category"));
            Team02Mapper team02Mapper = new Team02Mapper();
            String randomExcuse = team02Mapper.getRandomExcuseByCategory(connectionPool, category);

            ctx.attribute("excuse", randomExcuse);
            ctx.attribute("selectedCategory", category);
            ctx.render("team2/index.html");

        } catch (NumberFormatException e) {
            ctx.attribute("error", "Invalid category. Use a number (1-5).");
            ctx.render("team2/index.html");
        } catch (DatabaseException e) {
            ctx.attribute("error", "Database error: " + e.getMessage());
            ctx.render("team2/index.html");
        }
    }
}
