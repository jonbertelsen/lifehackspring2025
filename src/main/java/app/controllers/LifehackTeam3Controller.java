package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class LifehackTeam3Controller {
    private static final String TEAM_PREFIX = "/lifehackTeam3";

    public static void routes(Javalin app) {
        // Login routes
        app.get(TEAM_PREFIX + "/login", ctx -> showLoginPage(ctx));
        app.post(TEAM_PREFIX + "/login", ctx -> handleLogin(ctx));

        // Signup routes
        app.get(TEAM_PREFIX + "/signup", ctx -> showSignupPage(ctx));
        app.post(TEAM_PREFIX + "/signup", ctx -> handleSignup(ctx));

        // Dashboard
        app.get(TEAM_PREFIX + "/dashboard", ctx -> showDashboardPage(ctx));
        app.post(TEAM_PREFIX + "/dashboard", ctx -> handleDashboard(ctx));

    }

    private static void showLoginPage(Context ctx) {
        ctx.render("lifehackTeam3/index.html");
    }

    private static void handleLogin(Context ctx) {
        ctx.redirect(TEAM_PREFIX + "/dashboard");
    }

    private static void showSignupPage(Context ctx) {
        ctx.render("lifehackTeam3/signup.html");
    }

    private static void handleSignup(Context ctx) {
        ctx.redirect(TEAM_PREFIX + "/dashboard");
    }

    private static void showDashboardPage(Context ctx) {
        ctx.render("lifehackTeam3/dashboard.html");
    }

    private static void handleDashboard(Context ctx) {

    }
}