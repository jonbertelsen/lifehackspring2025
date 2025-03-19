package app.controllers;

import app.entities.Team6Movie;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Optional;

public class Team6Controller {
    private static ArrayList<Team6Movie> allMovies = new ArrayList<>();

    public static void routes(Javalin app, ConnectionPool pool) {
    app.post("begin", ctx -> getMovieList(ctx, pool));
    app.get("game", ctx -> ctx.render("game.html"));
    app.post("guess", ctx -> checkGuess(ctx, pool));
    }

    private static void getMovieList(Context ctx, ConnectionPool pool) {
        // kalder på mapper metoden og sætter den til arraylisten
    }

    private static void checkGuess(Context ctx, ConnectionPool pool) {
        String guess = ctx.formParam("guess");

        Optional<Team6Movie> guessedMovie = allMovies.stream()
                .filter(movie -> movie.getTitle().equals(fixed(guess)))
                .findFirst();

        if(guessedMovie.isPresent()) {
            allMovies.remove(guessedMovie.get());
            //bruger ctx attribute til at sende et besked til html. Måske ny metode.
            rightAnswerMovie(ctx, guessedMovie.get());
            if(allMovies.isEmpty()) {
                // Sender winner conditions. Måske ny metode
                winner(ctx);
            }
        }
    }

    private static void winner(Context ctx) {

    }

    private static void rightAnswerMovie(Context ctx, Team6Movie team6Movie) {
        ctx.attribute("rightanswer", team6Movie);
        ctx.render("game.html");
    }


    private static String fixed(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .toLowerCase()
                .trim();
    }

    ;
}
