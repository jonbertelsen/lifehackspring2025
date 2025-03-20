package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.TeamTeacherController;
import app.exceptions.LifeHackTeam3DatabaseException;
import app.controllers.LifehackTeam3Controller;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import app.persistence.LifeHackTeam3SubscriberMapper;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehackspring2025";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static LifeHackTeam3SubscriberMapper subscriberMapper = new LifeHackTeam3SubscriberMapper();

    
    public static void main(String[] args) {


        //TODO sæt denne så det er en knap der gør det når man signer up  i starten
        try {
            subscriberMapper.addNewSubscriber(connectionPool, "jonas.outzen.j@gmail.com", "faster_jones", "1234");
        } catch (LifeHackTeam3DatabaseException e) {
            System.out.println(e.getMessage());
        }

        //TODO sæt denne så det er en knap der kører koden når man tilmelder sig
        //TODO for eksempel med en getter/setter til reminder id (1,2,3)
        //TODO derudover skal email ikke være hardcoded, men skal være den man er logged ind som
        try {
            subscriberMapper.executeAddSubscriberToReminder(connectionPool, "jonas.outzen.j@gmail.com", 2);
        } catch (LifeHackTeam3DatabaseException e) {
            throw new RuntimeException(e);
        }

        //TODO når der trykkes afmeld skal denne kode køres
        try {
            subscriberMapper.executeRemoveSubscriberFromReminder(connectionPool, "jonas.outzen.j@gmail.com", 2);
        } catch (LifeHackTeam3DatabaseException e) {
            throw new RuntimeException(e);
        }


=======
    public static void main(String[] args)
    {

        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing


        app.get("/", ctx -> ctx.render("index.html"));
        TeamTeacherController.routes(app);

        app.get("/", ctx ->  ctx.render("index.html"));
        //TeamTeacherController.routes(app);
        LifehackTeam3Controller.routes(app);




    }

}