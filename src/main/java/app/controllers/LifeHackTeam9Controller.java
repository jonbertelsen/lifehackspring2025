package app.controllers;

import app.entities.LifeHackTeam9Event;
import app.persistence.ConnectionPool;
import app.persistence.LifeHackTeam9EventDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;


public class LifeHackTeam9Controller {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/index99", ctx -> getAllEvents(ctx, connectionPool));
        app.get("/index9", ctx -> ctx.render("/team9/index9.html"));
        app.post("/eventplanner/create", ctx -> createEvent(ctx, connectionPool));


        app.post("/events/join/", ctx -> joinEvent(ctx,connectionPool));
        app.post("/events/leave/", ctx -> leaveEvent(ctx,connectionPool));
    }

    private static void leaveEvent(Context ctx, ConnectionPool connectionPool) {

    }

    public static void joinEvent (Context ctx, ConnectionPool connectionPool) {

    }

    //Renders the frontpage and retrieves all events from DB
    public static void getAllEvents(Context ctx, ConnectionPool connectionPool) throws SQLException {
        List<LifeHackTeam9Event> eventPlannerList = LifeHackTeam9EventDAO.getAllEvents(connectionPool);

        ctx.attribute("eventPlannerList", eventPlannerList);
        ctx.render("/team9/index99.html");
    }

    //TODO Exception handling and update-messages to user
    public static void createEvent(Context ctx, ConnectionPool connectionPool) {
        String title = ctx.formParam("eventtitle");
        String description = ctx.formParam("eventdescription");
        String dateAndTime = ctx.formParam("eventdateandtime");
        String hostName = ctx.formParam("hostname");

        LifeHackTeam9Event event = new LifeHackTeam9Event(title, description, dateAndTime, hostName);
        LifeHackTeam9EventDAO.createEvent(event, connectionPool);
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