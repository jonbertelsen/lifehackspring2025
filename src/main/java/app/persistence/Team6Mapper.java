package app.persistence;

import app.entities.Team6Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team6Mapper {

    public static List<Team6Movie> getTop100Movies(ConnectionPool connectionPool) {
        List<Team6Movie> top100movies = new ArrayList<>();

        String sql = "SELECT * FROM lifehack_team_6_top_100_movies";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()){
                Team6Movie movie = new Team6Movie(
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
