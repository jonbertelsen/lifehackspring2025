package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class TeamTeacherController {

    public static void routes(Javalin app) {
        app.get("/team-teacher/index", ctx -> teamTeacherFrontHome(ctx));
    };

    private static void teamTeacherFrontHome(@NotNull Context ctx) {
        ctx.render("team-teacher/team12_index.html");
    }
}
