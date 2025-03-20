package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class Team7Controller {
public static void add(Javalin app, ConnectionPool connectionPool) {

    app.get("/DadJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.get("/MomJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.get("/JesperJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.get("/NationalJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.get("/BlondeJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.get("/DatamatikerJokes", ctx -> ctx.render("team7_frontpage.html"));

    app.get("/TryLuck", ctx -> ctx.render("team7_try_luck.html"));
    app.post("/login", ctx -> ctx.render("team7_frontpage.html"));
}
}
