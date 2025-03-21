package app.persistence;

import app.entities.Team14Entity;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class Team14Mapper {
    public static List<Team14Entity> getMovies(ConnectionPool connectionPool) throws DatabaseException {
        List<Team14Entity> movies = new ArrayList<>();
        String sql =
                "SELECT title, duration, director, platform, main_genre\n " +
                "FROM public.movie\n " +
                "inner join public.platform using(movie_id)\n " +
                "inner join public.main_genre using(movie_id)\n " +
                "ORDER BY imdb_score DESC";
        try (Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String title = rs.getString("title");
                    int duration = rs.getInt("duration");
                    String director = rs.getString("director");
                    String platform = rs.getString("platform");
                    String main_genre = rs.getString("main_genre");
                    Team14Entity movie = new Team14Entity(title, duration, director, platform, main_genre);
                    movies.add(movie);
                }
            }
        }catch (SQLException EX){
            EX.printStackTrace();
        }
        movies.forEach(System.out::println);
        return movies;
    }
}
