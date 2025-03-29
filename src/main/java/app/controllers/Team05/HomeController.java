package app.controllers.Team05;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.Team05Mapper;
import io.javalin.http.Context;

import java.util.logging.Logger;

// Hovedcontrolleren for håndtering af brugerautentifikation.
public class HomeController {
    private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName()); // Logger, der bruges til at logge fejl og handlinger i systemet.
    private final ConnectionPool connectionPool; //Et objekt, der bruges til at få adgang til forbindelser til databasen.

    // Initialiserer HomeController med en ConnectionPool, som bruges til at oprette forbindelse til databasen.
    public HomeController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    //metode, der håndterer brugerens login- og tilmeldingsanmodninger.
    public void authenticate(Context ctx) {
        try {
            //Hentning af brugerinput: Får email, password, og action
            // (dvs. hvad brugeren forsøger at gøre - tilmelde sig eller logge ind) fra formularen.
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");
            String action = ctx.formParam("action");

            // Håndter sign-up
            if ("signup".equals(action)) {
                //Hvis brugeren forsøger at tilmelde sig, parses password som et heltal, og der tjekkes,
                // om en bruger med den angivne email allerede eksisterer i databasen.
                int passwordInt = Integer.parseInt(password);
                boolean userExists = Team05Mapper.userExists(email, connectionPool);

                //Hvis brugeren allerede eksisterer, returneres en fejl. Hvis ikke, forsøger systemet at oprette brugeren med signUp-metoden.
                //Hvis tilmeldingen lykkes, redirects brugeren til log.html, ellers vises en fejlmeddelelse.

                if (userExists) {
                    ctx.status(400).result("User already exists. Please log in.");
                } else {
                    int result = Team05Mapper.signUp(email, passwordInt, connectionPool);
                    if (result == 1) {
                        ctx.status(200).redirect("/workoutlog");  // Redirect til log.html
                    } else {
                        ctx.status(400).result("Sign-up failed.");
                    }
                }
            } else if ("signin".equals(action)) {

                //Hvis brugeren forsøger at logge ind, forsøger systemet at validere loginoplysningerne med login-metoden.

                String userEmail = Team05Mapper.login(email, Integer.parseInt(password), connectionPool);

                if (userEmail != null) {
                    ctx.sessionAttribute("user", userEmail);  // Gem bruger i session
                    ctx.redirect("/workoutlog");  // Redirect til log.html
                } else {
                    ctx.status(400).result("Incorrect email or password.");
                }
            } else {
                ctx.status(400).result("Invalid action.");
            }
        } catch (DatabaseException e) {
            LOGGER.severe("Error during authentication: " + e.getMessage());
            ctx.status(500).result("Error during authentication.");
        }
    }



}

//Denne controller håndterer både brugerens login og tilmelding. Afhængig af den handling,
// der angives af brugeren (signup eller signin), vil systemet validere brugeren,
// oprette en ny bruger i databasen, eller gemme brugerens session og logge dem ind.
// Hvis der opstår fejl undervejs, håndteres det med undtagelser og logning.