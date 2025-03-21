package app.controllers.lifehack_team_16;

import app.entities.lifehack_team_16.User;
import app.exceptions.DatabaseException;
import app.persistence.lifehack_team_16.ConnectionPool;
import app.persistence.lifehack_team_16.UserMapper;
import app.persistence.lifehack_team_16.WaterLogMapper;
import io.javalin.http.Context;
import app.controllers.lifehack_team_16.WaterLogController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserController {

   private static ConnectionPool connectionPool;

    //Dependency injection
    public UserController(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    //TODO Make this if we have time

        public static int login(Context ctx) throws DatabaseException{
            String userName = ctx.formParam("userName");
            String password = ctx.formParam("password");

            //Create user with username and password from UserMapper
            User user = UserMapper.Signin(userName, password);

            try {

                //Compares if User data from input equals User data from database
                    if(user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                        ctx.render("lifehack_team_16/HomePage.html");
                        ctx.attribute("waterMl", WaterLogMapper.getWaterLogByUserId(user.getUserId()));
                        return user.getUserId();
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


    public static void createUser(Context ctx){
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");


        if (password1.equals(password2))
        {

            try {
                UserMapper.createuser(username, password1);
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

}
