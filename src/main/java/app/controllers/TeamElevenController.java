package app.controllers;

import app.persistence.Team11Mapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class TeamElevenController {

    public static void routes(Javalin app) {
        app.post("/team11/index", ctx -> calculateTotalPrice(ctx));
        app.get("/team11/index", ctx -> displayFinalPrice(ctx));
    }

    private static void displayFinalPrice(Context ctx){
        // Henter det der blev gemt i session
        String calculatedPrice = ctx.sessionAttribute("calculatedPrice");

        ctx.sessionAttribute("calculatedPrice", null);

        ctx.attribute("calculatedPrice", calculatedPrice);

        // LOGGING: Tjekker hvad der bliver vist som resultat
        System.out.println("🖥️ Displaying calculated price: " + calculatedPrice);

        ctx.render("team11/index.html");
    }

    private static void calculateTotalPrice(Context ctx) {

        try {
            // LOGGING: Efter værdierne er hentet fra HTML-formularen
            String imp_country = ctx.formParam("imp_country");
            String ex_country = ctx.formParam("ex_country");
            String category = ctx.formParam("category");
            String priceInput = ctx.formParam("price");
            double priceMinimum = Double.parseDouble(priceInput);

            System.out.println("📥 Country from form: " + imp_country);
            System.out.println("📥 Category from form: " + category);
            System.out.println("📥 Price from form: " + priceInput);


            if (priceInput == null || priceInput.isEmpty()) {
                ctx.attribute("error", "Prisen skal udfyldes");
                ctx.render("team11/index.html");
                return;
            } else if(priceMinimum <= 1200){
                ctx.render("team11/index.html");
            }

            // LOGGING: Efter oversættelse fra dansk til engelsk
            if ("Danmark".equalsIgnoreCase(imp_country)) {
                imp_country = "Denmark";
            }
            if ("Tyskland".equalsIgnoreCase(imp_country)) {
                imp_country = "Germany";
            }
            if ("Japan".equalsIgnoreCase(imp_country)) {
                imp_country = "Japan";
            }
            if ("USA".equalsIgnoreCase(imp_country)) {
                imp_country = "USA";
            }
            if ("Danmark".equalsIgnoreCase(ex_country)) {
                ex_country = "Denmark";
            }
            if ("Tyskland".equalsIgnoreCase(ex_country)) {
                ex_country = "Germany";
            }
            if ("Japan".equalsIgnoreCase(ex_country)) {
                ex_country = "Japan";
            }
            if ("USA".equalsIgnoreCase(ex_country)) {
                ex_country = "USA";
            }

            if ("Biler".equalsIgnoreCase(category)) {
                category = "Cars";
            }
            if ("Elektronik".equalsIgnoreCase(category)) {
                category = "Electronics";
            }
            if ("Møbler".equalsIgnoreCase(category)) {
                category = "Furniture";
            }
            if ("Mad".equalsIgnoreCase(category)) {
                category = "Food";
            }

            System.out.println("🌍 Translated country: " + imp_country);
            System.out.println("🌍 Translated country: " + ex_country);
            System.out.println("🚗 Translated category: " + category);

            double price = Double.parseDouble(priceInput);

            try {
                // LOGGING: Efter værdierne er hentet fra databasen via mapperen
                double tariffRate = Team11Mapper.getTariffRate(ex_country, category);
                System.out.println("🛢️ Tariff Rate from mapper: " + tariffRate);

                double vatRate = Team11Mapper.getVatRate(imp_country);
                System.out.println("💰 VAT Rate from mapper: " + vatRate);

                // LOGGING: Lige før beregning for at sikre, at værdier er korrekte
                System.out.println("💡 Tariff Rate used in calculation: " + tariffRate);
                System.out.println("💡 VAT Rate used in calculation: " + vatRate);

                // Beregner told
                double tariffAmount = calculateTariff(price, tariffRate);
                double priceAfterTariff = price + tariffAmount;

                // Beregner moms
                double vatAmount = calculateVat(priceAfterTariff, vatRate);

                // Beregner det samlede beløb efter told og moms er beregnet
                double finalPrice = priceAfterTariff + vatAmount;

                // LOGGING: Efter beregning for at bekræfte det endelige beløb
                System.out.println("✅ Tariff Amount: " + tariffAmount);
                System.out.println("✅ Price After Tariff: " + priceAfterTariff);
                System.out.println("✅ VAT Amount: " + vatAmount);
                System.out.println("🏆 Final Price: " + finalPrice);

                // Gemmer resultatet i session, undgår dobbeltberegning ved refresh


                    ctx.sessionAttribute("calculatedPrice", String.format("%.2f", finalPrice));
                    ctx.redirect("/team11/index");

            } catch (NumberFormatException e) {
                ctx.attribute("error", "Prisen skal være et gyldigt tal");
                System.err.println("❌ Error parsing price: " + e.getMessage()); // LOGGING
                ctx.render("team11/index.html");
                return;
            }

        } catch (Exception e) {
            ctx.attribute("error", "Der opstod en fejl ved beregningen");
            System.err.println("❌ General error: " + e.getMessage()); // LOGGING
            ctx.render("team11/index.html");
        }
    }

    private static double calculateTariff(double price, double tariffRate) {
        // LOGGING: Tjekker værdierne i tariff-beregningen

        /* if(price >= 1200) {System.out.println("📊 Calculating tariff with price: " + price + ", tariffRate: " + tariffRate);
        return price * (tariffRate / 100);}
        else{System.out.println("Import is free of tariff because start value is below 1200kr.");  return price; }  */
       System.out.println("📊 Calculating tariff with price: " + price + ", tariffRate: " + tariffRate);
        return price * (tariffRate / 100);
    }

    private static double calculateVat(double priceAfterTariff, double vatRate) {
        // LOGGING: Tjekker værdierne i moms-beregningen
        System.out.println("📊 Calculating VAT with priceAfterTariff: " + priceAfterTariff + ", vatRate: " + vatRate);
        return priceAfterTariff * (vatRate / 100);
    }
}
