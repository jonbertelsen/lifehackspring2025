package app.persistence;

import app.entities.LifeHackTeam9Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LifeHackTeam9EventDAO {

    public static List<LifeHackTeam9Event> getAllEvents(ConnectionPool connectionPool) throws SQLException {
        List<LifeHackTeam9Event> events = new ArrayList<>();
        String sql = "SELECT event_id, title, date_and_time, host_name, description FROM team_9_event ORDER BY event_id ASC";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                LifeHackTeam9Event event = new LifeHackTeam9Event(
                        rs.getInt("event_id"),
                        rs.getString("title"),
                        rs.getString("date_and_time"),
                        rs.getString("host_name"),
                        rs.getString("description")
                );
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            String msg = "Fejl ved hentning af nyhedsbreve.";
            throw new SQLException(msg, e.getMessage());
        }
    }

    public static void joinEvent(LifeHackTeam9Event event, ConnectionPool connectionPool) {
        String sql = "INSERT INTO public.team_9_participation (participation_id, event_id, user_id) VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            //(sql, Statement.RETURN_GENERATED_KEYS )) needed if we want to return an Event-object with id from DB

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDateAndTime());
            ps.setString(3, event.getHostName());


            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            //TODO void or return Event?
            ResultSet idResultset = ps.getGeneratedKeys();
            if (idResultset.next()) {
                int newId = idResultset.getInt(1);
                event.setEventId(newId);
            } else {
                event = null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createEvent(LifeHackTeam9Event event, ConnectionPool connectionPool) {
        String sql = "INSERT INTO team_9_event (title, date_and_time, host_name, description) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            //(sql, Statement.RETURN_GENERATED_KEYS )) needed if we want to return an Event-object with id from DB

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDateAndTime());
            ps.setString(3, event.getHostName());
            ps.setString(4, event.getDescription());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            //TODO void or return Event?
            ResultSet idResultset = ps.getGeneratedKeys();
            if (idResultset.next()) {
                int newId = idResultset.getInt(1);
                event.setEventId(newId);
            } else {
                event = null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int getNoOfParticipantsInEventById(int eventId, ConnectionPool connectionPool) {
        int count = 0;

        String sql = "SELECT COUNT(event_id) FROM public.team_9_participation WHERE event_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}


