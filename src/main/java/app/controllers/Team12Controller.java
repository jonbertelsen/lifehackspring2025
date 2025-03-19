package app.controllers;

import app.entitites.Team12User;
import app.exceptions.Team12DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team12UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class Team12Controller {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx ->  ctx.render("/team12/team12_index.html"));
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.get("createuser", ctx -> ctx.render("team12_createuser.html"));
        app.post("createuser", ctx -> createUser(ctx,connectionPool));

    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        //hent form parametre
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if(password1.equals(password2))
        {

            try
            {
                Team12UserMapper.createuser(username,password1,connectionPool);
                ctx.attribute("message", "You are now created" + username
                        + ". Now you have to login");
                ctx.render("index.html");
            }
            catch (Team12DatabaseException e) {
                ctx.attribute("message", "Passwords already exists, try again or login");
                ctx.render("createuser.html");
            }
        }
        else {
            ctx.attribute("message", "Passwords do not match, try again");
            ctx.render("createuser.html");
        }
    }



    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }



    // todo ændre metode til at vide ens specifikke sleeps records
    public static void login(Context ctx, ConnectionPool connectionPool) {
        //hent form parametre
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        try {
            Team12User user = Team12UserMapper.login(username, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            // hvis ja, send videre til task siden

            ctx.render("team12_main.html");
        } catch (Team12DatabaseException e) {
            // hvis nej, send tilbage til login side med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }

        // tjek om bruger findes i databaser med de angivne username og password

    }

}
