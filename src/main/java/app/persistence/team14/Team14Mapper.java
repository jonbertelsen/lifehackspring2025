package app.persistence.team14;

import app.entities.team14.Team14Entity;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team14Mapper {
    public static List<Team14Entity> getMovies(ConnectionPool connectionPool, String mainGenre) throws DatabaseException {
        List<Team14Entity> movies = new ArrayList<>();
        String sql = "SELECT title, duration, director, main_genre " +
                "FROM public.movie " +
                "INNER JOIN public.main_genre USING(movie_id) " +
                "WHERE main_genre = ? " +
                "ORDER BY imdb_score DESC";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, mainGenre);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String title = rs.getString("title");
                    int duration = rs.getInt("duration");
                    String director = rs.getString("director");
                    String genre = rs.getString("main_genre");
                    Team14Entity movie = new Team14Entity(title, duration, director, genre);
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error executing query");
        }
        return movies;
    }
}
