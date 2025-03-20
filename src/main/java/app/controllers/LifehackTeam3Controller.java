package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Map;

public class LifehackTeam3Controller {
    private static final String TEAM_PREFIX = "/lifehackTeam3";

    public static void routes(Javalin app) {
        //Redirect from root to /login
        app.get(TEAM_PREFIX, ctx -> redirect(ctx));

        //Login
        app.get(TEAM_PREFIX + "/login", ctx -> showLoginPage(ctx));
        app.post(TEAM_PREFIX + "/login", ctx -> handleLogin(ctx));

        // Signup routes
        app.get(TEAM_PREFIX + "/signup", ctx -> showSignupPage(ctx));
        app.post(TEAM_PREFIX + "/signup", ctx -> handleSignup(ctx));

        // Dashboard
        app.get(TEAM_PREFIX + "/dashboard", ctx -> showDashboardPage(ctx));
        app.post(TEAM_PREFIX + "/dashboard", ctx -> handleDashboard(ctx));

        //Logout
        app.post(TEAM_PREFIX + "/logout", ctx -> handleLogout(ctx));

    }


    private static void redirect(Context ctx) {
        ctx.redirect(TEAM_PREFIX + "/login");
    }
    private static void showLoginPage(Context ctx) {
        ctx.render("lifehackTeam3/index.html");
    }

    private static void handleLogin(Context ctx) {
        //TODO Tilføj login logik som læser mod database og kan verify

        String username = ctx.formParam("username");
        ctx.sessionAttribute("username", username);
        ctx.redirect(TEAM_PREFIX + "/dashboard");
    }

    private static void showSignupPage(Context ctx) {
        ctx.render("lifehackTeam3/signup.html");
    }

    private static void handleSignup(Context ctx) {
        String username = ctx.formParam("username");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        ctx.sessionAttribute("username", username);
        ctx.redirect(TEAM_PREFIX + "/dashboard");
    }

    private static void handleLogout(Context ctx) {
        ctx.sessionAttribute("username", null);
        ctx.redirect(TEAM_PREFIX + "/login");
    }

    private static void showDashboardPage(Context ctx) {
        String username = ctx.sessionAttribute("username");
        ctx.render("lifehackTeam3/dashboard.html", Map.of("username", username));
    }

    private static void handleDashboard(Context ctx) {

    }
}