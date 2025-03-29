package app.persistence.team09;
import app.entities.team09.Team09Event;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Team09EventMapper {
    private final ConnectionPool connectionPool;

    public Team09EventMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    // JOIN EVENT
    public void joinEvent(int eventId, String userName) {
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
    // LEAVE EVENT
    public void leaveEvent(int eventId, String userName) {
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

    // GET ALL EVENTS
    public static List<Team09Event> getAllEvents(ConnectionPool connectionPool) throws SQLException {
        List<Team09Event> events = new ArrayList<>();
        String sql = "SELECT event_id, title, date_and_time, host_name, description FROM team_9_event ORDER BY event_id ASC";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Team09Event event = new Team09Event(
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

    //CREATE EVENT
    public static void createEvent(Team09Event event, ConnectionPool connectionPool) {
        String sql = "INSERT INTO team_9_event (title, date_and_time, host_name, description) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // <- Tilføjet her

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDateAndTime());
            ps.setString(3, event.getHostName());
            ps.setString(4, event.getDescription());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            // Hent det genererede event_id
            try (ResultSet idResultSet = ps.getGeneratedKeys()) {
                if (idResultSet.next()) {
                    int newId = idResultSet.getInt(1);
                    event.setEventId(newId); // Gem det nye ID
                } else {
                    event = null; // Hvis intet ID returneres, sættes event til null
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //PARTICIPANTS
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