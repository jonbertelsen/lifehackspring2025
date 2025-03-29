package app.controllers.lifehack_team_16;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

import static app.controllers.lifehack_team_16.UserController.createUser;

public class Team16Controller {

    //Dependency injection
    ConnectionPool connectionPool;

    Team16Controller(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public static void routes(Javalin app) {
        app.get("/waterlog", ctx -> ctx.render("lifehack_team_16/index.html"));
        app.get("homepage", ctx -> ctx.render("lifehack_team_16/HomePage.html"));
        app.get("createuser", ctx -> ctx.render("lifehack_team_16/createuser.html"));
        app.post("createuser", ctx -> createUser(ctx));
       // app.post("addWater", ctx -> WaterLogController.addWater(ctx));
    }

     /*
    public static void addWater(Context ctx, WaterLog waterLog) throws DatabaseException {
        waterLog.setWaterMl(waterLog.getWaterMl() + 250);
        try {
            WaterLogMapper.updateWaterLog(waterLog);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        ctx.attribute("waterMl", waterLog.getWaterMl());
        ctx.render("lifehack_team_167HomePage.html");
    }

     */




}
