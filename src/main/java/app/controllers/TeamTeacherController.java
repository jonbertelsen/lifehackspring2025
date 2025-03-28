package app.controllers;

import app.entities.teamteacher.TeamTeacherQuote;
import app.persistence.ConnectionPool;
import app.persistence.teamteacher.TeamTeacherMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;


public class TeamTeacherController {

    public static void routes(Javalin app, ConnectionPool connectionPool) {
        app.get("/philosophers", ctx -> philosophersHome(ctx));
        app.post("/philosophers", ctx -> ask(ctx, connectionPool));
       // app.get("/ask", ctx -> ctx.render("answer.html"));

    }

    private static void philosophersHome(@NotNull Context ctx) {
        ctx.render("teamteachers/index.html");
    }

    private static void ask(@NotNull Context ctx, ConnectionPool connectionPool) {
        String input = ctx.formParam("ask");
        TeamTeacherQuote philosophicalAnswer = TeamTeacherMapper.getPhilosophicalAnswer(input, connectionPool);
        ctx.attribute("quote", philosophicalAnswer.getQuote());
        ctx.attribute("philosopher", philosophicalAnswer.getPhilosopher());
        ctx.attribute("philosopherImage", philosophicalAnswer.getPicture());
        ctx.render("teamteachers/index.html");
        /*
        // Her søges og hentes fra datamapper - indtil da hardcodes et citat. Tænker også at vi kan gemme billedenavn i db
        ctx.attribute("quote", "Det uundersøgte liv er ikke værd at leve.");
        ctx.attribute("philosopher", "Socrates");*/


    }




    private static void getAnswer(Context ctx, ConnectionPool connectionPool){


    }


}
