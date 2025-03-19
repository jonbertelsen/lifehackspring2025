package app.entities;

import app.enums.ColorTeam4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class Team4ProfileEntityTest {

    private Team4ProfileEntity.AnimalClass animal = new Team4ProfileEntity.AnimalClass( "Jesper",
                                                                                        "bio",
                                                                                        1,
                                                                                        2,
                                                                                        3,
                                                                                        1);

    private Team4ProfileEntity team4ProfileEntity = new Team4ProfileEntity( animal,
                                                                            "image",
                                                                            "email",
                                                                            "password",
                                                                            0);

    @Test
    void getColor() {
        assertEquals(2, team4ProfileEntity.getColor());
    }

    @Test
    void getColorEnum() {
        assertEquals(ColorTeam4.values()[1], animal.getColorEnum());
    }

    @Test
    void getSpecies() {
        assertEquals(3, team4ProfileEntity.getSpecies());
    }

    @Test
    void getBio() {
        assertEquals("bio", team4ProfileEntity.getBio());
    }

    @Test
    void getName() {
        assertEquals("Jesper", team4ProfileEntity.getName());
    }

    @Test
    void getAge() {
        assertEquals(1, team4ProfileEntity.getAge());
    }

    @Test
    void getImage() {
        assertEquals("image", team4ProfileEntity.getImage());
    }

    @Test
    void getEmail() {
        assertEquals("email", team4ProfileEntity.getEmail());
    }

    @Test
    void getPassword() {
        assertEquals("password", team4ProfileEntity.getPassword());
    }

    @Test
    void getId() {
        assertEquals(0, team4ProfileEntity.getId());
    }

    @Test
    void testToString() {
        assertEquals("Color: 2 Species: 3 Bio: bio Name: Jesper Age: 1 Image: image Email: email ID: 0", team4ProfileEntity.toString());
    }
}