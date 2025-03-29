package app.persistence.team06;

import app.entities.team06.Team06Movie;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team06Mapper {

    public static List<Team06Movie> getTop100Movies(ConnectionPool connectionPool) {
        List<Team06Movie> top100movies = new ArrayList<>();

        String sql = "SELECT * FROM lifehack_team_6_top_100_movies";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()){
                Team06Movie movie = new Team06Movie(
                        rs.getString("title"),
                        rs.getDouble("rating"),
                        rs.getInt("release_year")
                );
                top100movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return top100movies;
    }
}
