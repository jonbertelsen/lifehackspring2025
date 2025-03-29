package app.persistence.team04;

import app.entities.team04.Team04Profile;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team04ProfileMapper {

    private ConnectionPool connectionPool;


    public Team04ProfileMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Team04Profile> getAllProfiles() {

        List<Team04Profile> profiles = new ArrayList<>();

        String sql = "SELECT name, age, bio, species, color " +
                "FROM profile " +
                "ORDER BY species";

        //Tries resource closes connection automatically
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String bio = rs.getString("bio");
                    int species = rs.getInt("species");
                    int color = rs.getInt("color");
                    profiles.add(new Team04Profile(color, species, bio, name, age));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return profiles;
    }

    public static Team04Profile addProfile(ConnectionPool connectionPool, Team04Profile team04Profile) throws DatabaseException {
        String sql = "INSERT INTO profile (color, species, bio, name, age, image, email, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, team04Profile.getColor());
            ps.setInt(2, team04Profile.getSpecies());
            ps.setString(3, team04Profile.getBio());
            ps.setString(4, team04Profile.getName());
            ps.setInt(5, team04Profile.getAge());
            ps.setString(6, team04Profile.getImage());
            ps.setString(7, team04Profile.getEmail());
            ps.setString(8, team04Profile.getPassword());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt("id");

                    return new Team04Profile(team04Profile.getColor(), team04Profile.getSpecies(),
                                             team04Profile.getBio(), team04Profile.getName(), team04Profile.getAge(),
                                             team04Profile.getImage(), team04Profile.getEmail(), team04Profile.getPassword(), generatedId);

                } else {
                    throw new DatabaseException("Der skete en fejl ved oprettelse af bruger, ingen ID returneret.");
                }
            }
        } catch (SQLException e) {
            String msg = "Fejl ved tilføjelse af ny bruger. Prøv igen.";
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public static List<Team04Profile> searchProfiles(String query, ConnectionPool connectionPool) throws DatabaseException {
        List<Team04Profile> profiles = new ArrayList<>();
        String sql = "SELECT color, species, bio, name, age " +
                "FROM profile " +
                "WHERE name ILIKE ? OR bio ILIKE ? " +
                "ORDER BY species DESC";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String pattern = "%" + query + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Team04Profile profileEntity = new Team04Profile(
                            rs.getInt("color"),
                            rs.getInt("species"),
                            rs.getString("bio"),
                            rs.getString("name"),
                            rs.getInt("age")
                    );
                    profiles.add(profileEntity);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return profiles;
    }

    public static boolean deleteProfile(ConnectionPool connectionPool, String email) throws DatabaseException {

        boolean deleteSuccess = false;
        String sql = "DELETE FROM profile WHERE email = ? ";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                int rowThatIsAffected = ps.executeUpdate();  //executeUpdate(); is for DELETE, UPDATE, CREATE
                if (rowThatIsAffected == 1) {
                    deleteSuccess = true;
                } else {
                    throw new DatabaseException("Profile with email " + email + "could not be deleted");
                }
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new DatabaseException("error", e.getMessage());

        }

        return deleteSuccess;
    }


    public Team04Profile updateProfile(ConnectionPool connectionPool, Team04Profile profile) {

        String sql = "UPDATE profile " +
                "SET color = ?, species = ?, bio = ?, name = ?, age = ? " +
                "WHERE email = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, profile.getColor());
                ps.setInt(2, profile.getSpecies());
                ps.setString(3, profile.getBio());
                ps.setString(4, profile.getName());
                ps.setInt(5, profile.getAge());
                ps.setString(6, profile.getEmail());
                int rowThatIsAffected = ps.executeUpdate();  //executeUpdate(); is for DELETE, UPDATE, CREATE
                if (rowThatIsAffected == 1) {
                    return profile;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return profile;
    }
}