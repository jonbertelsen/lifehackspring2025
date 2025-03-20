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
    private static int correctGuessCount = 0; // Counter variable

    public static void routes(Javalin app, ConnectionPool pool) {
        //app.post("/list", ctx -> getMovieList(ctx, pool)); // Ensure correct route
        app.get("/startgame", ctx -> {
            getMovieList(ctx, pool);  // Populate movie list when game starts
            ctx.render("lifehack_team_6/game.html");
        });
        app.post("/guess", ctx -> checkGuess(ctx, pool));
    }

    private static void getMovieList(Context ctx, ConnectionPool pool) {
        allMovies = Team6Mapper.getTop100Movies(pool);
        correctGuessCount = 0;
    }

    public static void checkGuess(Context ctx, ConnectionPool pool) {
        String guess = ctx.formParam("user-input");


        int indexToRemove = -1;
        for (int i = 0; i < allMovies.size(); i++) {
            if (fixed(allMovies.get(i).getTitle()).equals(fixed(guess))) {
                indexToRemove = i;
                break;
            } /*else {
                for (int g = 0; g < guessedMovies.size(); i++) {
                    if (fixed(guessedMovies.get(i).getTitle()).equals(fixed(guess))) {
                        ctx.attribute("message", "You have already guessed that movie!");
                        ctx.render("lifehack_team_6/game.html");
                    }
                    break;
                }
            }*/
        }

        if (indexToRemove != -1) {
            rightAnswerMovie(ctx, allMovies.get(indexToRemove));
            allMovies.remove(indexToRemove);
            correctGuessCount++; // Increment counter
            top100counter(ctx); // Update the counter on the frontend

            if (allMovies.isEmpty()) {
                winner(ctx);
            }
        } else {
            top100counter(ctx);
            ctx.attribute("message", "Incorrect guess! Try again.");
            ctx.render("lifehack_team_6/game.html");

        }

    }

    private static void winner(Context ctx) {
        ctx.attribute("message", "Congratulations! You've guessed all 100 movies!");
        ctx.render("lifehack_team_6/game.html");
    }

    private static void rightAnswerMovie(Context ctx, Team6Movie team6Movie) {
        ctx.attribute("message", team6Movie.toString());
        ctx.render("lifehack_team_6/game.html");
    }

    private static void top100counter(Context ctx) {
        String counterText = correctGuessCount + "/100";
        ctx.attribute("counter", counterText);
        ctx.render("lifehack_team_6/game.html");
    }

    private static String fixed(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .toLowerCase()
                .trim();
    }
}


