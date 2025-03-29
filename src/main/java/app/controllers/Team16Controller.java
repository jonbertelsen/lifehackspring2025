package app.controllers;

import app.entities.team16.Team16User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.team16.Team16UserMapper;
import app.persistence.team16.Team16WaterLogMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class Team16Controller {

    //Dependency injection
    private ConnectionPool connectionPool;

    public Team16Controller(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public void routes(Javalin app) {
        app.get("/waterlog", ctx -> ctx.render("team_16/index.html"));
        app.get("homepage", ctx -> ctx.render("team_16/HomePage.html"));
        app.get("createuser", ctx -> ctx.render("team_16/createuser.html"));
        app.post("createuser", ctx -> createUser(ctx));

       // app.post("addWater", ctx -> WaterLogController.addWater(ctx));
    }

    //TODO Make this if we have time

    public int login(Context ctx) throws DatabaseException {
        String userName = ctx.formParam("username");
        String password = ctx.formParam("password");

        //Create team16User with username and password from Team16UserMapper
        Team16User team16User = Team16UserMapper.Signin(userName, password, connectionPool);
        ctx.sessionAttribute("currentUser", team16User);

        try {

            //Compares if Team05User data from input equals Team05User data from database
            if(team16User.getUserName().equals(userName) && team16User.getPassword().equals(password)) {
                ctx.render("lifehack_team_16/HomePage.html");
                ctx.attribute("waterMl", Team16WaterLogMapper.getWaterLogByUserId(team16User.getUserId()));
                return team16User.getUserId();
            }
            else {
                ctx.sessionAttribute("error", "Wrong username or password");
                ctx.render("lifehack_team_16/SignIn.html");
            }
            //
        } catch (Exception e) {
            throw new DatabaseException("Database failed getting information: "+ e);
        }
        return 0;
    }


    public void createUser(Context ctx){
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");


        if (password1.equals(password2))
        {

            try {
                Team16UserMapper.createuser(username, password1, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: " + username + ". Nu skal du logge på.");
                ctx.render("lifehack_team_16/index.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createuser.html");
        }

    }

     /*
    public static void addWater(Context ctx, Team16WaterLog waterLog) throws DatabaseException {
        waterLog.setWaterMl(waterLog.getWaterMl() + 250);
        try {
            Team16WaterLogMapper.updateWaterLog(waterLog);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        ctx.attribute("waterMl", waterLog.getWaterMl());
        ctx.render("lifehack_team_167HomePage.html");
    }

     */




}
