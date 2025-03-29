package app.controllers;

import app.entities.Team21Conversion;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team21ConverterMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;

public class Team21Controller {

    //registers routes with their corresponding handlers
    public static void routes(Javalin app, ConnectionPool connectionPool) {
        app.get("/team21/index", ctx -> team21FrontHome(ctx, connectionPool));
        app.post("/convert", ctx -> unitConvert(ctx, connectionPool));
        app.post("/alldata", ctx -> showAllData(ctx, connectionPool));
        app.get("/alldata", ctx -> showAllData(ctx, connectionPool));
    }



    // Retrieves all conversion data from the database and renders it on the data.html template.
    private static void showAllData(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        List<Team21Conversion> allData = Team21ConverterMapper.getData(connectionPool);
        ctx.attribute("conversions", allData); // Pass data to the template
        ctx.render("/team21/data.html");
    }

    // Renders the home page of the application.
    private static void team21FrontHome(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/team21/index.html");
    }


    // Performs the unit conversion based on user input and updates the database with the result.
    private static double unitConvert(Context ctx, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        String units = ctx.formParam("unitType");
        String oldValue = ctx.formParam("value");
        double value = Double.parseDouble(oldValue);
        double result = 0;
        // Determine the type of conversion and perform it.
        if (units.equals("km-to-mi")){
            result = kilometerToMiles(value);
        }
        else if (units.equals("mi-to-km")){
            result = milesToKilometer(value);
        }
        else if (units.equals("km-to-ff")){
            result = kilometerToFootballFields(value);
        }
        else {
            result = footballFieldsToKilometer(value);
        }
        ctx.attribute("result", result); // Store the result in the context for use in the front-end.
        team21FrontHome(ctx, connectionPool);  // Render the home page with the updated result.
        Team21ConverterMapper.insertData(result, units, connectionPool, ctx);  // Save the conversion to the database.
        return result;

    }

    // Converts kilometers to miles.
    private static double kilometerToMiles(double value){
        return value * 0.621371;
    }

    // Converts miles to kilometers.
    private static double milesToKilometer(double value){
        return value / 0.621371;
    }

    // Converts kilometers to the equivalent number of football fields.
    private static double kilometerToFootballFields(double value){
        return value * 10.9361;
    }

    // Converts football fields to the equivalent number of kilometers.
    private static double footballFieldsToKilometer(double value){
        return value / 10.9361;
    }
}
