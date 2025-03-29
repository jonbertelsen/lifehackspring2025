package app.controllers;

import app.entities.team01.Team01Entity;
import app.persistence.team01.Team01CategoriesMapper;
import app.persistence.team01.Team01QuestionMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import app.persistence.ConnectionPool;

import java.util.List;

public class Team01Controller {

    // Database connection pool
    private static ConnectionPool connectionPool;

    // Constructor to initialize the connection pool
    public Team01Controller(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // Defines the routes for the application
    public static void routes(Javalin app) {
        app.get("/lifehack_team_1/showMenu", ctx -> showMenu(ctx));
        app.get("/lifehack_team_1/quiz", ctx -> showGame(ctx));
        app.get("/lifehack_team_1/question/{id}", ctx -> showQuestion(ctx));
    }

    // Renders the index page
    private static void team1Controller(@NotNull Context ctx) {
        ctx.render("team-1controller/index.html");
    }


    /*public static void addQuestion(Context ctx) {
        String question = ctx.formParam("Question");
        String answer = ctx.formParam("Answer");
        int points = Integer.parseInt(ctx.formParam("Points"));

        if (question == null || answer == null) {
            ctx.status(400).result("Question or answer can't be empty");
            return;
        }
        Team01Entity.Questions newQuestion = new Team01Entity.Questions(question, answer, points);
        Team01QuestionMapper.addQuestion(newQuestion);

        ctx.redirect("/questions");
        //Todo mangler og connecte med mappers

    }
    public static void addCategories(Context ctx){

        String categoryName = ctx.formParam("Category name");
            if (categoryName == null || categoryName.isEmpty()) {
                ctx.status(400).result("Category name can't be empty!");
                return;
            }
        Team01Entity.Categories newCategory = new Team01Entity.Categories(categoryName);
        Team01CategoriesMapper.addCategory(newCategory);

        ctx.redirect("/game");

    }
    /*public static void addQuiz(Context ctx){
        String title = ctx.formParam("Title of the question");
        if (title == null || title.isEmpty()) {
            ctx.status(400).result("Quiz-titel can't be empty!");
            return;
        }
        Team01Entity.Quiz newQuiz = new Team01Entity.Quiz(title);
        Team01QuestionMapper.addQuiz(newQuiz);

        ctx.redirect("/quiz");
    }*/
    /*public static void updateQuestion(Context ctx) {

        String newQuestionText = ctx.formParam("Question");
        String newAnswer = ctx.formParam("Answer");

        if (newQuestionText == null || newAnswer == null) {
            ctx.status(400).result("Question or answer can't be empty!");
            return;
        }

        Team01QuestionMapper.updateQuestion(newQuestionText, newAnswer);
        ctx.redirect("/questions");
    }


/*public static void updateCategories(Context ctx) {

    String newCategoryName = ctx.formParam("Category name");

    if (newCategoryName == null || newCategoryName.isEmpty()) {
        ctx.status(400).result("category name can't be empty!");
        return;
    }

    Team01CategoriesMapper.updateCategory(newCategoryName);
    ctx.redirect("/game");
}

/*public static void updateQuiz(Context ctx) {
    String newQuizTitle = ctx.formParam("Title of the question");

    if (newQuizTitle == null || newQuizTitle.isEmpty()) {
        ctx.status(400).result("Quiz title can't be empty!");
        return;
    }

    QuizMapper.updateQuiz(newQuizTitle);
    ctx.redirect("/quiz");
}*/


    // Displays the menu page
    public static void showMenu(Context ctx) {
        ctx.render("lifehack_team_1/menu.html");
    }

    // Displays the game page with a list of categories
    public static void showGame(Context ctx) {
        try {
            List<Team01Entity.Categories> categories = Team01CategoriesMapper.listOfCategories(connectionPool, 1);
            ctx.attribute("category_list", categories);
            ctx.render("lifehack_team_1/game.html");
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception
        }
    }

    // Displays a question page with a specific question
    public static void showQuestion(Context ctx) {
        try {
            int questionId = Integer.parseInt(ctx.pathParam("id"));
            Team01Entity.Questions questions = Team01QuestionMapper.getQuestionById(connectionPool, questionId);
            ctx.attribute("question", questions);
            ctx.render("lifehack_team_1/question.html");
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception
        }
    }
}
