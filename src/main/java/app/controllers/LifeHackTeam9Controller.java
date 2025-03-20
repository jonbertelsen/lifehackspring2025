package app.controllers;

import app.entities.LifeHackTeam9Event;
import app.persistence.ConnectionPool;
import app.persistence.LifeHackTeam9EventDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.List;

public class LifeHackTeam9Controller {

    private static LifeHackTeam9EventDAO eventDAO;


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/index99", ctx -> getAllEvents(ctx, connectionPool));
        app.get("/index9", ctx -> ctx.render("/team9/index9.html"));
        app.post("/eventplanner/create", ctx -> createEvent(ctx, connectionPool));

    }



    public static void getAllEvents(Context ctx, ConnectionPool connectionPool) throws SQLException {
        List<LifeHackTeam9Event> eventPlannerList = LifeHackTeam9EventDAO.getAllEvents(connectionPool);

        ctx.attribute("eventPlannerList", eventPlannerList);
        ctx.render("/team9/index99.html");
    }


    public static void createEvent(Context ctx, ConnectionPool connectionPool) {
        String title = ctx.formParam("eventtitle");
        String description = ctx.formParam("eventdescription");
        String dateAndTime = ctx.formParam("eventdateandtime");
        String hostName = ctx.formParam("hostname");

        // Du kan tilføje validering her, hvis nødvendigt.
        if (title != null && description != null && dateAndTime != null && hostName != null) {
            LifeHackTeam9Event event = new LifeHackTeam9Event(title, description, dateAndTime, hostName);
            LifeHackTeam9EventDAO.createEvent(event, connectionPool);
            ctx.redirect("/index99"); // Hvis eventet er oprettet, kan du redirecte til en oversigtsside
        } else {
            ctx.status(400).result("Fejl i formularen. Alle felter skal udfyldes.");
        }
    }



    //TODO
    public static void getNoOfParticipantsInEventById(Context ctx, ConnectionPool connectionPool) {
        try {
            int eventId = Integer.parseInt(ctx.formParam("event_id"));

            int participants = LifeHackTeam9EventDAO.getNoOfParticipantsInEventById(eventId, connectionPool);
            ctx.attribute("participants_number", participants);

        } catch (NumberFormatException ex) {
            ctx.attribute("message", "Input must be a number");
        }
    }
}
