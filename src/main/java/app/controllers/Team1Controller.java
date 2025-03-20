package app.controllers;

import app.entities.Team1Entities;
import app.persistence.Team1CategoriesMapper;
import app.persistence.Team1QuestionMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import app.persistence.ConnectionPool;

import java.util.List;

public class Team1Controller {

    // Database connection pool
    private static ConnectionPool connectionPool;

    // Constructor to initialize the connection pool
    public Team1Controller(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // Defines the routes for the application
    public static void routes(Javalin app) {
        app.get("/lifehack_team_1/showMenu", ctx -> showMenu(ctx));
        app.get("/lifehack_team_1/quiz", ctx -> showGame(ctx));
        app.post("/lifehack_team_1/question", ctx -> showQuestion(ctx));
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
        Team1Entities.Questions newQuestion = new Team1Entities.Questions(question, answer, points);
        Team1QuestionMapper.addQuestion(newQuestion);

        ctx.redirect("/questions");
        //Todo mangler og connecte med mappers

    }
    public static void addCategories(Context ctx){

        String categoryName = ctx.formParam("Category name");
            if (categoryName == null || categoryName.isEmpty()) {
                ctx.status(400).result("Category name can't be empty!");
                return;
            }
        Team1Entities.Categories newCategory = new Team1Entities.Categories(categoryName);
        Team1CategoriesMapper.addCategory(newCategory);

        ctx.redirect("/game");

    }
    /*public static void addQuiz(Context ctx){
        String title = ctx.formParam("Title of the question");
        if (title == null || title.isEmpty()) {
            ctx.status(400).result("Quiz-titel can't be empty!");
            return;
        }
        Team1Entities.Quiz newQuiz = new Team1Entities.Quiz(title);
        Team1QuestionMapper.addQuiz(newQuiz);

        ctx.redirect("/quiz");
    }*/
    /*public static void updateQuestion(Context ctx) {

        String newQuestionText = ctx.formParam("Question");
        String newAnswer = ctx.formParam("Answer");

        if (newQuestionText == null || newAnswer == null) {
            ctx.status(400).result("Question or answer can't be empty!");
            return;
        }

        Team1QuestionMapper.updateQuestion(newQuestionText, newAnswer);
        ctx.redirect("/questions");
    }


/*public static void updateCategories(Context ctx) {

    String newCategoryName = ctx.formParam("Category name");

    if (newCategoryName == null || newCategoryName.isEmpty()) {
        ctx.status(400).result("category name can't be empty!");
        return;
    }

    Team1CategoriesMapper.updateCategory(newCategoryName);
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
            List<Team1Entities.Categories> categories = Team1CategoriesMapper.listOfCategories(connectionPool, 1);
            ctx.attribute("category_list", categories);
            ctx.render("lifehack_team_1/game.html");
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception
        }
    }

    // Displays a question page with a specific question
    public static void showQuestion(Context ctx) {
        try {
            Team1Entities.Questions questions = Team1QuestionMapper.getQuestionById(connectionPool, 1);
            ctx.attribute("question", questions);
            ctx.render("lifehack_team_1/question.html");
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception
        }
    }
}
