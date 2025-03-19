package app.controllers;

import app.entities.Team6Movie;
import app.persistence.ConnectionPool;
import app.persistence.Team6Mapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.text.Normalizer;
import java.util.List;


public class Team6Controller {
    private static List<Team6Movie> allMovies;

    public static void routes(Javalin app, ConnectionPool pool) {
    app.post("begin", ctx -> getMovieList(ctx, pool));
    app.get("game", ctx -> ctx.render("game.html"));
    app.post("guess", ctx -> checkGuess(ctx, pool));
    }

    private static void getMovieList(Context ctx, ConnectionPool pool) {
        allMovies = Team6Mapper.getTop100Movies(pool);
    }

    public static void checkGuess(Context ctx, ConnectionPool pool) {
        String guess = ctx.formParam("guess");

        int indexToRemove = -1;
        for (int i = 0; i < allMovies.size(); i++) {
            if (fixed(allMovies.get(i).getTitle()).equals(fixed(guess))) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            //bruger ctx attribute til at sende et besked til html. Måske ny metode.
            rightAnswerMovie(ctx, allMovies.get(indexToRemove));
            allMovies.remove(indexToRemove);
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
