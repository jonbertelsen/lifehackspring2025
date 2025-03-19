package app.controllers.lifehack_team_16;

import app.persistence.lifehack_team_16.ConnectionPool;
import io.javalin.Javalin;

public class RouterController {

    //Dependency injection
    ConnectionPool connectionPool;

    RouterController(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public static void routes(Javalin app) {


    }
    

}
