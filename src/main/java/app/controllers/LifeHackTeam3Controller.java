package app.controllers;

import app.exceptions.LifeHackTeam3DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.LifeHackTeam3MailSender;
import app.persistence.LifeHackTeam3SubscriberMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Map;

public class LifeHackTeam3Controller {
    private static final String TEAM_PREFIX = "/lifehackTeam3";
    private static LifeHackTeam3SubscriberMapper subscriberMapper = new LifeHackTeam3SubscriberMapper();
    private static LifeHackTeam3MailSender mailSender;

    public static void routes(Javalin app) {

        // Redirect from root to /login
        app.get(TEAM_PREFIX, ctx -> redirect(ctx));

        // Login
        app.get(TEAM_PREFIX + "/login", ctx -> showLoginPage(ctx));
        app.post(TEAM_PREFIX + "/login", ctx -> handleLogin(ctx));

        // Signup routes
        app.get(TEAM_PREFIX + "/signup", ctx -> showSignupPage(ctx));
        app.post(TEAM_PREFIX + "/signup", ctx -> handleSignup(ctx));

        // Dashboard
        app.get(TEAM_PREFIX + "/dashboard", ctx -> showDashboardPage(ctx));
        app.post(TEAM_PREFIX + "/dashboard", ctx -> handleDashboard(ctx));

        // Logout
        app.post(TEAM_PREFIX + "/logout", ctx -> handleLogout(ctx));

        //Tilmeld
        app.post(TEAM_PREFIX + "/tilmeld", ctx -> handleTilmeld(ctx));
        app.post(TEAM_PREFIX + "/afmeld", ctx -> handleAfmeld(ctx));

        //Send reminder
        app.post(TEAM_PREFIX + "/sendReminder", ctx -> handleSendReminder(ctx));
    }

    private static void redirect(Context ctx) {
        ctx.redirect(TEAM_PREFIX + "/login");
    }

    private static void showLoginPage(Context ctx) {
        ctx.render("lifehackTeam3/index.html");
    }

    private static void handleLogin(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        //Kalder verifyUser til at validere login
        try {
            boolean validUser = subscriberMapper.verifyUserCredentials(ConnectionPool.getInstance(), username, password);
            if (validUser) {
                String email = subscriberMapper.getEmailFromUsername(ConnectionPool.getInstance(), username);
                ctx.sessionAttribute("username", username);
                ctx.sessionAttribute("email", email);
                showDashboardPage(ctx);
            } else {
                ctx.redirect(TEAM_PREFIX + "/login");
            }
        } catch (LifeHackTeam3DatabaseException e) {
            e.printStackTrace();
            ctx.redirect(TEAM_PREFIX + "/login");
        }
    }


    private static void showSignupPage(Context ctx) {
        ctx.render("lifehackTeam3/signup.html");
    }

    private static void handleSignup(Context ctx) {
        String username = ctx.formParam("username");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            //Kalder metode addNewuser og tilføjer new user til database
            subscriberMapper.addNewUser(ConnectionPool.getInstance(), email, username, password);
        } catch (LifeHackTeam3DatabaseException e) {
            System.out.println(e.getMessage());
        }

        ctx.sessionAttribute("username", username);
        ctx.redirect(TEAM_PREFIX + "/dashboard");
    }

    private static void handleLogout(Context ctx) {
        ctx.sessionAttribute("username", null);
        ctx.redirect(TEAM_PREFIX + "/login");
    }

    //Session attribute som bliver sendt til dashboard page, netop så der kan stå "Logged in as" {user}
    private static void showDashboardPage(Context ctx) {
        String username = ctx.sessionAttribute("username");
        if (username == null) {
            username = "Guest";
        }
        ctx.render("lifehackTeam3/dashboard.html", Map.of("username", username));
    }

    private static void handleDashboard(Context ctx) {

    }

    private static void handleTilmeld(Context ctx) {
        try {
            String email = ctx.sessionAttribute("email");
            String reminderId = ctx.formParam("reminderId");

            //Kalder executeAddSub method til "tilmeld"
            if (email != null && reminderId != null) {
                subscriberMapper.executeAddSubscriberToReminder(ConnectionPool.getInstance(), email, reminderId);
                System.out.println("User " + email + " subscribed to " + reminderId);
            } else {
                System.out.println("Email or Reminder ID is missing.");
            }
        } catch (LifeHackTeam3DatabaseException e) {
            System.out.println(e.getMessage());
        }
        ctx.redirect(TEAM_PREFIX + "/dashboard");
    }

    private static void handleAfmeld(Context ctx) {
        try {
            String email = ctx.sessionAttribute("email");
            String reminderId = ctx.formParam("reminderId");

            if (email != null && reminderId != null) {
                subscriberMapper.executeRemoveSubscriberFromReminder(ConnectionPool.getInstance(), email, reminderId);
                System.out.println("User " + email + " unsubscribed from " + reminderId);
            } else {
                System.out.println("Email or Reminder ID is missing.");
            }
        } catch (LifeHackTeam3DatabaseException e) {
            System.out.println(e.getMessage());
        }
        ctx.redirect(TEAM_PREFIX + "/dashboard");
    }

    private static void handleSendReminder(Context ctx) {
        String email = ctx.sessionAttribute("email");
        if (email != null) {
            LifeHackTeam3MailSender mailSender = new LifeHackTeam3MailSender(ConnectionPool.getInstance());
            mailSender.sendReminderForUser(email);
            ctx.redirect(TEAM_PREFIX + "/dashboard");
        } else {
            ctx.redirect(TEAM_PREFIX + "/login");
        }
    }
}
