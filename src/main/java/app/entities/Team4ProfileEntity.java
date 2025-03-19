package app.entities;

import app.enums.ColorTeam4;
import app.enums.SpeciesTeam4;

public class Team4ProfileEntity {

    private int color;
    private int species;
    private String bio;
    private String name;
    private int age;
    private String image;
    private String email;
    private String password;
    private int id;

    public Team4ProfileEntity(AnimalClass animal, String image, String email, String password, int id)
    {
        this.color = animal.getColorInt();
        this.species = animal.getSpeciesInt();
        this.bio = animal.bio;
        this.name = animal.name;
        this.age = animal.age;
        this.image = image;
        this.email = email;
        this.password = password;
        this.id = id;
    }


    //Getter methods
    public int getColor() {
        return this.color;
    }

    public int getSpecies() {
        return this.species;
    }

    public String getBio() {
        return this.bio;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getImage() {
        return this.image;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public int getId() {
        return this.id;
    }


    @Override
    public String toString() {
        return "Color: " + this.color + " Species: " + this.species + " Bio: " + this.bio +
                " Name: " + this.name + " Age: " + this.age + " Image: " + this.image + " Email: " + this.email + " ID: " + this.id;
    }

    public static class AnimalClass {
        private String name;
        private ColorTeam4 color;
        private SpeciesTeam4 species ;
        private int age;
        private int id;
        private String bio;

        public AnimalClass(String name, String bio, int age, int color, int species, int id)
        {
            this.name = name;
            this.age = age;
            this.color = ColorTeam4.int2Enum(color);
            this.species = SpeciesTeam4.int2Enum(species);
            this.id = id;
            this.bio = bio;
        }

        //Getter methods
        public String getName()
        {
            return this.name;
        }

        public int getAge()
        {
            return age;
        }

        public ColorTeam4 getColorEnum()
        {
            return color;
        }

        public int getColorInt()
        {
            return color.getCode();
        }

        public SpeciesTeam4 getSpeciesEnum()
        {
            return species;
        }

        public int getSpeciesInt()
        {
            return species.getCode();
        }

        public int getId()
        {
            return this.id;
        }
    }
}
