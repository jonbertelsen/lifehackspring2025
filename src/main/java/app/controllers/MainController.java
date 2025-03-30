package app.controllers;

import app.entities.LifehackSite;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    public static void routes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> index(ctx));
    }

    private static void index(@NotNull Context ctx) {
        List<LifehackSite> lifehackSites = new ArrayList<>();

        lifehackSites.add(new LifehackSite("Not Jeopardy", "Play now", "team01/showMenu", "team01/images/thumbnail.png"));
        lifehackSites.add(new LifehackSite("Filosoffernes hjørne", "Få et råd", "/philosophers", "teamteachers/images/thumbnail.png"));
        lifehackSites.add(new LifehackSite("Excuse Generator", "Get an excuse", "/team2/index", "team02/images/thumbnail.png"));
        lifehackSites.add(new LifehackSite("Only Paws", "Get a friend", "/team4", "team04/images/thumbnail.png"));
        ctx.attribute("lifehackSites", lifehackSites);
        ctx.render("index.html");
    }
}
