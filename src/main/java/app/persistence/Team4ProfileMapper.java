package app.persistence;

import app.entities.Team4ProfileEntity;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team4ProfileMapper {

private ConnectionPool connectionPool;


public Team4ProfileMapper(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
}

public List<Team4ProfileEntity> getAllProfiles() {

    List<Team4ProfileEntity> profiles = new ArrayList<>();

    String sql = "SELECT name, age, bio, species, color " +
            "FROM profile " +
            "ORDER BY species";

    //Tries resource closes connection automatically
    try(Connection connection = connectionPool.getConnection()){
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String name = rs.getString("name");
                int age  = rs.getInt("age");
                String bio = rs.getString("bio");
                int species = rs.getInt("species");
                int color = rs.getInt("color");
                profiles.add(new Team4ProfileEntity(color,species,bio,name, age));
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return profiles;
}

public static Team4ProfileEntity addProfile(ConnectionPool connectionPool ,Team4ProfileEntity team4ProfileEntity) throws DatabaseException {
    String sql = "INSERT INTO profile (color, species, bio, name, age, image, email, password) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

    try(
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
            ) {
                ps.setInt(1, team4ProfileEntity.getColor());
                ps.setInt(2, team4ProfileEntity.getSpecies());
                ps.setString(3, team4ProfileEntity.getBio());
                ps.setString(4, team4ProfileEntity.getName());
                ps.setInt(5, team4ProfileEntity.getAge());
                ps.setString(6, team4ProfileEntity.getImage());
                ps.setString(7, team4ProfileEntity.getEmail());
                ps.setString(8, team4ProfileEntity.getPassword());

                try(var rs = ps.executeQuery()){
                    if(rs.next()){
                        int generatedId = rs.getInt("id");

                        return new Team4ProfileEntity(team4ProfileEntity.getColor(), team4ProfileEntity.getSpecies(),
                                    team4ProfileEntity.getBio(), team4ProfileEntity.getName(), team4ProfileEntity.getAge(),
                                    team4ProfileEntity.getImage(), team4ProfileEntity.getEmail(), team4ProfileEntity.getPassword(), generatedId );

                    } else {
                        throw new DatabaseException("Der skete en fejl ved oprettelse af bruger, ingen ID returneret.");
                    }
                }
    } catch (SQLException e){
        String msg = "Fejl ved tilføjelse af ny bruger. Prøv igen.";
        throw new DatabaseException(msg, e.getMessage());
    }
}


public static List<Team4ProfileEntity> searchProfiles(String query, ConnectionPool connectionPool) throws DatabaseException {
                List<Team4ProfileEntity> profiles = new ArrayList<>();
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
                                       Team4ProfileEntity profileEntity = new Team4ProfileEntity(
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

}

