package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class Team1Controller {

    public static void routes(Javalin app) {

        app.post("/lifehack-team-1/addQuestion", ctx -> addQuestion(ctx));
        app.put("/lifehack-team-1/updateQuestion",ctx -> updateQuestion(ctx));
        app.post("/lifehack-team-1/addCategories", ctx -> addCategories(ctx));
        app.put("/lifehack-team-1/updateCategories", ctx -> updateCategories(ctx));
        app.post("/lifehack-team-1/addQuiz", ctx -> addQuiz(ctx));
        app.put("/lifehack-team-1/updateQuiz", ctx -> updateQuiz(ctx));
        app.post("/lifehack-team-1/addUsers", ctx -> addUsers(ctx));
        app.put("/lifehack-team-1/updateUsers", ctx -> updateUsers(ctx));

    };

    private static void team1Controller(@NotNull Context ctx) {

        ctx.render("team-1controller/index.html");
    }

    public static void addQuestion(Context ctx) {
        String question = ctx.formParam("question");
        String answer = ctx.formParam("Answer");
        int points = Integer.parseInt(ctx.formParam("points"));

        //Todo mangler og connecte med mappers

    }
    public static void addCategories(Context ctx){

        String categoryName = ctx.formParam("Category name");
       // Todo Mangler og connecte med mapper
    }
    public static void addQuiz(Context ctx){
        String title = ctx.formParam("Title of the question");
        // Todo Mangler og connecte med mapper
    }
    public static void addUsers(Context ctx){
        String name = ctx.formParam("Add a name");
        String password = ctx.formParam("Add a password");
        // Todo mangler og connecte med mapper
    }
    public static void updateQuestion(Context ctx){
        String changeQuestion = ctx.formParam("Question");
        String answer = ctx.formParam("Answer");

    }
    public static void updateCategories(Context ctx) {
        String changeCategoryName = ctx.formParam("Category name");

    }
    public static void updateQuiz(Context ctx) {
        String changeName = ctx.formParam("Title of the question");
    }
    public static void updateUsers(Context ctx) {
        String changeName = ctx.formParam("Add a name");
        String changePassword = ctx.formParam("Add a password");
    }

    }
