package app.controllers;

import app.entities.team09.Team09Event;
import app.persistence.ConnectionPool;
import app.persistence.team09.Team09EventMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class Team09Controller {

    private static Team09EventMapper eventDAO;

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/index99", ctx -> getAllEvents(ctx, connectionPool));
        app.get("/index9", ctx -> ctx.render("/team9/index9.html"));
        app.post("/eventplanner/create", ctx -> createEvent(ctx, connectionPool));
        app.get("/eventplanner/create", ctx -> ctx.render("/team9/create.html"));
        //Add og leave metoders routes mangler her ->
    }

    public static void getAllEvents(Context ctx, ConnectionPool connectionPool) throws SQLException {
        List<Team09Event> eventPlannerList = Team09EventMapper.getAllEvents(connectionPool);

        ctx.attribute("eventPlannerList", eventPlannerList);
        ctx.render("/team9/index99.html");
    }

    public static void createEvent(Context ctx, ConnectionPool connectionPool) {
        String title = ctx.formParam("eventtitle");
        String description = ctx.formParam("eventdescription");
        String dateAndTime = ctx.formParam("eventdateandtime");
        String hostName = ctx.formParam("hostname");


        if (title != null && description != null && dateAndTime != null && hostName != null) {
            Team09Event event = new Team09Event(title, description, dateAndTime, hostName);
            Team09EventMapper.createEvent(event, connectionPool);
            ctx.redirect("/index99"); // Hvis eventet er oprettet, kan du redirecte til en oversigtsside
        } else {
            ctx.status(400).result("Fejl i formularen. Alle felter skal udfyldes.");
        }
    }

    //TODO
    public static void getNoOfParticipantsInEventById(Context ctx, ConnectionPool connectionPool) {
        try {
            int eventId = Integer.parseInt(ctx.formParam("event_id"));

            int participants = Team09EventMapper.getNoOfParticipantsInEventById(eventId, connectionPool);
            ctx.attribute("participants_number", participants);

        } catch (NumberFormatException ex) {
            ctx.attribute("message", "Input must be a number");
        }
    }

    public static void joinEvent(int eventId, String userName, ConnectionPool connectionPool) {
        String sql = "INSERT INTO team_9_participation (event_id, user_name) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setString(2, userName);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void leaveEvent(int eventId, String userName, ConnectionPool connectionPool) {
        String sql = "DELETE FROM team_9_participation WHERE event_id = ? AND user_name = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setString(2, userName);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}