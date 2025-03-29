package app.controllers;

import app.persistence.team11.Team11Mapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class Team11Controller {

    public static void routes(Javalin app) {
        app.post("/team11/index", ctx -> calculateTotalPrice(ctx));
        app.get("/team11/index", ctx -> displayFinalPrice(ctx));
    }

    private static void displayFinalPrice(Context ctx){
        // Henter det der blev gemt i session
        String calculatedPrice = ctx.sessionAttribute("calculatedPrice");

        ctx.sessionAttribute("calculatedPrice", null);

        ctx.attribute("calculatedPrice", calculatedPrice);

        ctx.render("team11/index.html");
    }

    private static void calculateTotalPrice(Context ctx) {

        try {String imp_country = ctx.formParam("imp_country");
            String ex_country = ctx.formParam("ex_country");
            String category = ctx.formParam("category");
            String priceInput = ctx.formParam("price");


            if (priceInput == null || priceInput.isEmpty()) {
                ctx.attribute("error", "Price is Empty and need to be filled");
                ctx.render("team11/index.html");
                return;}

            // Translates the danish frontend input to english, so it cooperates with the naming in DB.
            //Eventually another method, probably translating within SQL will be used.
            if ("Danmark".equalsIgnoreCase(imp_country)) {imp_country = "Denmark";}
            if ("Tyskland".equalsIgnoreCase(imp_country)) { imp_country = "Germany"; }
            if ("Japan".equalsIgnoreCase(imp_country)) { imp_country = "Japan"; }
            if ("USA".equalsIgnoreCase(imp_country)) { imp_country = "USA"; }
            if ("Danmark".equalsIgnoreCase(ex_country)) { ex_country = "Denmark"; }
            if ("Tyskland".equalsIgnoreCase(ex_country)) { ex_country = "Germany"; }
            if ("Japan".equalsIgnoreCase(ex_country)) { ex_country = "Japan"; }
            if ("USA".equalsIgnoreCase(ex_country)) { ex_country = "USA"; }
            if ("Biler".equalsIgnoreCase(category)) { category = "Cars"; }
            if ("Elektronik".equalsIgnoreCase(category)) { category = "Electronics"; }
            if ("Møbler".equalsIgnoreCase(category)) { category = "Furniture"; }
            if ("Mad".equalsIgnoreCase(category)) {category = "Food";}

            double price = Double.parseDouble(priceInput);

            try { double tariffRate = Team11Mapper.getTariffRate(ex_country, category);
                double vatRate = Team11Mapper.getVatRate(imp_country);

                // Calculates tariffs and VAT
                double calculatedTariffAmount = calculateTariff(price, tariffRate);
                double finalPrice = calculateVat(calculatedTariffAmount,vatRate);

                //vatAmount is for later use, if we want to showcase the user what the amounts of Vat and tariff's are:
                //double vatAmount = vatRate / 100 * calculatedTariffAmount ;

                ctx.sessionAttribute("calculatedPrice", String.format("%.2f", finalPrice));
                ctx.redirect("/team11/index"); }
            catch (NumberFormatException e) { ctx.attribute("error", "Prisen skal være et gyldigt tal");
                System.err.println(" Error parsing price: " + e.getMessage());
                ctx.render("team11/index.html"); return; }
        } catch (Exception e) { ctx.attribute("error", "Der opstod en fejl ved beregningen");
            System.err.println("General error: " + e.getMessage());
            ctx.render("team11/index.html"); }
    }

    //Calculates the value of tariff
    private static double calculateTariff(double price, double tariffRate)
    { if(price >= 1200) { return price * (tariffRate / 100 + 1); }
    else{ return price; }
        //Udgangspunkt før rettelse:
        //System.out.println("📊 Calculating tariff with price: " + price + ", tariffRate: " + tariffRate);
        // return price * (tariffRate / 100);
    }

    //Calculats the VATamount, after tariffs have been added to the original price.
    private static double calculateVat(double priceAfterTariff, double vatRate) {
        return priceAfterTariff * (vatRate / 100 + 1); }
}
