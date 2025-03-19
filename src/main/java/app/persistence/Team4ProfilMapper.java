package app.persistence;

import app.entities.Team4ProfileEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team4ProfilMapper {

private ConnectionPool connectionPool;


public Team4ProfilMapper(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
}

public List<Team4ProfileEntity> getProfiles() {

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






}
