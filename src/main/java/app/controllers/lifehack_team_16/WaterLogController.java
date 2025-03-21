package app.controllers.lifehack_team_16;

import app.exceptions.DatabaseException;
import app.persistence.lifehack_team_16.ConnectionPool;
import io.javalin.http.Context;
import app.entities.lifehack_team_16.*;
import app.persistence.lifehack_team_16.WaterLogMapper;

public class WaterLogController {

    private static ConnectionPool connectionPool;

    //Dependency injection
    public WaterLogController(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }


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




}
