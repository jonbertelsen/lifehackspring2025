package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class Team7Controller {
public static void add(Javalin app, ConnectionPool connectionPool){

    app.post("/DadJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.post("/MomJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.post("/JesperJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.post("/TryLuck", ctx -> ctx.render("team7_frontpage.html"));
    app.post("/NationalJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.post("/BlondeJokes", ctx -> ctx.render("team7_frontpage.html"));
    app.post("/DatamatikerJokes", ctx -> ctx.render("team7_frontpage.html"));
}




}
