package app.controllers;
import app.entities.Team4ProfileEntity;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;


public class Team4Controller {
    static int idCounter;

    public Team4Controller() {
        idCounter++;
    }


    static ArrayList<Team4ProfileEntity> users = new ArrayList<Team4ProfileEntity>();
    static HashMap<Team4ProfileEntity, Integer> teams = new HashMap<Team4ProfileEntity, Integer>();

    public static class UserController {
        public static void createUser(Context ctx, String name, String email, String password, int age, int species, int color, String bio, String image) {
            // Create a new animal
            Team4ProfileEntity.AnimalClass animal = new Team4ProfileEntity.AnimalClass(name, bio, age, species, color);
            // Create a new profile
            Team4ProfileEntity profile = new Team4ProfileEntity(animal, image, email, password, idCounter);
            // Save the user
            Team4Controller.users.add(profile);
            // Save the animal
            // Save the profile
        }

        public static void updateUser(Context ctx, int id, String name, String email, String password, int age, int species, int color, String bio, String image) {
            // Find the user
            // Find the animal
            // Find the profile
            // Update the user
            // Update the animal
            // Update the profile
        }


        public static boolean login (Context ctx, String email, String password) {

        }

        public static void getProfile(Context ctx, int id) {
            // Find the user
            // Find the animal
            // Find the profile
            // Return the user
        }

        public static void getUsers(Context ctx) {
            // Find all users
            // Find all animals
            // Find all profiles
            // Return all users
        }
    }
}
