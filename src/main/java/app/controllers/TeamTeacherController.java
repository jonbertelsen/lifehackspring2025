package app.controllers;

import app.persistence.ConnectionPool;
import app.persistence.TeamTeacherMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamTeacherController {






    public static void routes(Javalin app) {
        app.get("/team-teacher/index", ctx -> teamTeacherFrontHome(ctx));
       // app.get("/ask", ctx -> ctx.render("answer.html"));

    };

    private static void teamTeacherFrontHome(@NotNull Context ctx) {
        ctx.render("team-teacher/index.html");
    }

    private static void getAnswer(Context ctx, ConnectionPool connectionPool){


    }


}
