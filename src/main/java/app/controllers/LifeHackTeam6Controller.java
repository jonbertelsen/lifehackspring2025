package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.parseInt;

public class LifeHackTeam6Controller {

    public static void routes(Javalin app) {
        app.get("/team-teacher/index", ctx -> teamTeacherFrontHome(ctx));
    };

    private static void teamTeacherFrontHome(@NotNull Context ctx) {
        ctx.render("team-teacher/index.html");
    }

    private static void top100counter(){
        String counter = parseInt(100 - allMovies.size())+"/100";
        ctx.attribute("counter", counter);
    }
}
