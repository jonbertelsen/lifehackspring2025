package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class Team15Controller {

    public class team15Controller {

        public static void routes(Javalin app) {
            app.get("/lifehackTeam15/index", ctx -> team15FrontHome(ctx));
        };

        private static void team15FrontHome(@NotNull Context ctx) {
            ctx.render("lifehackTeam15/index.html");
        }
    }
}
