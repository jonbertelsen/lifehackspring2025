package app.controllers;

import app.entities.Team6Movie;
import app.persistence.Team6Mapper;
import io.javalin.http.Context;

import app.persistence.ConnectionPool;
import org.junit.jupiter.api.Test;

import java.text.Normalizer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class Team6ControllerTest {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "movie_guesser";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private Context ctx;

    @Test
     void checkGuess() {
        List<Team6Movie> allMovies = Team6Mapper.getTop100Movies(connectionPool);

        String guess = "terminator 2 judgment day";

        int indexToRemove = -1;
        for (int i = 0; i < allMovies.size(); i++) {
            if (fixed(allMovies.get(i).getTitle()).equals(fixed(guess))) {
                indexToRemove = i;
                break;
            }
        }

        //bruger ctx attribute til at sende et besked til html. Måske ny metode.
        //rightAnswerMovie(ctx, guessedMovie.get());
        if (indexToRemove != -1) {
            allMovies.remove(indexToRemove);
        }

        assertEquals(99, allMovies.size());
    }


    private static String fixed(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .toLowerCase()
                .trim();
    }



}