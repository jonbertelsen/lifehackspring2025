package app.controllers.lifehack_team_16;

import io.javalin.http.Context;
import app.entities.lifehack_team_16.*;

public class WaterLogController {

    public static void addWater(Context ctx, WaterLog waterLog){
        waterLog.setWaterMl(waterLog.getWaterMl() + 250);
        //updateWaterLog(waterLog);
        ctx.attribute("waterMl", waterLog.getWaterMl());
        ctx.render("login.html");
    }
}
