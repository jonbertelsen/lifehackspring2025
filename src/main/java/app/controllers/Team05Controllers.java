package app.controllers;

import app.entities.Team05.Workout;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team05Mapper;
import io.javalin.http.Context;

import java.util.List;
import java.util.logging.Logger;

public class Team05Controllers {
    private static final Logger LOGGER = Logger.getLogger(Team05Controllers.class.getName());
    private final ConnectionPool connectionPool;

    public Team05Controllers(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void viewWorkoutlog(Context ctx) {
        try {
            List<Workout> workoutLog = Team05Mapper.getAllWorkoutLog(connectionPool);
            ctx.attribute("workout", workoutLog);
            ctx.render("index05.html");
        } catch (DatabaseException e) {
            LOGGER.severe("Error retrieving workout log: " + e.getMessage());
            ctx.result("An error occurred while retrieving the workout log.");
        }
    }

}
