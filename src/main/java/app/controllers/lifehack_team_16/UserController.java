package app.controllers.lifehack_team_16;

import app.entities.lifehack_team_16.User;
import app.exceptions.DatabaseException;
import app.persistence.lifehack_team_16.ConnectionPool;
import app.persistence.lifehack_team_16.UserMapper;
import io.javalin.http.Context;

import java.util.List;

public class UserController {

    ConnectionPool connectionPool;

    //Dependency injection
    public UserController(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    //TODO Make this if we have time
   /* public static void createUser(Context ctx, ConnectionPool connectionPool) {
        String userName = ctx.formParam("userName");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

            if (password1.equals(password2)) {
            try{
                UserMapper.createUser(userName, password1);
            } catch (Exception e) {

            }

            }
    } */

        public static void login(Context ctx) throws DatabaseException{
            String userName = ctx.formParam("userName");
            String password = ctx.formParam("password");

            //Create user with username and password from UserMapper
            User user = UserMapper.Signin(userName, password);

            try {

                //Compares if User data from input equals User data from database
                    if(user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                        ctx.render("HomePage.html");
                    }
                    else {
                    ctx.sessionAttribute("error", "Wrong username or password");
                    ctx.render("SignIn.html");
                }
                //
            } catch (Exception e) {
                throw new DatabaseException("Database failed getting information: "+ e);
            }

        }

}
