package test;

import model.UserReader;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserReaderTest {
    @org.junit.jupiter.api.Test
    void updateMyConcerts() {
        ArrayList<String> newConcert = new ArrayList<String>(Arrays.asList("マジカルミライ2020", "ボーカロイド", "2020/08/10", "幕張メッセ", "5600円", "100人"));
        UserReader ur = new UserReader("yyahata");
        ur.updateMyConcerts(newConcert);
    }

    @org.junit.jupiter.api.Test
    void getMyConcerts() {
        UserReader ur = new UserReader("yyahata");

        ArrayList<ArrayList<String>> expected = new ArrayList<>();
        expected.add(new ArrayList<String>(Arrays.asList("マジカルミライ2020", "ボーカロイド", "2020/08/10", "幕張メッセ", "5600円", "100人")));

        assertEquals(expected, ur.getMyConcerts());
    }

    @org.junit.jupiter.api.Test
    void isCorrectUser() {
        assertTrue(UserReader.isCorrectUser("yyahata", "yyahata"));
    }

    @org.junit.jupiter.api.Test
    void getAllUsers() {
        ArrayList<ArrayList<String>> users = UserReader.getAllUsers();

        ArrayList<ArrayList<String>> expected = getExpectedUsers();

        assertEquals(expected, users);
    }

    ArrayList<ArrayList<String>> getExpectedUsers() {
        ArrayList<ArrayList<String>> e = new ArrayList<>();

        e.add(new ArrayList<>(Arrays.asList("yyahata", "yyahata")));
        e.add(new ArrayList<>(Arrays.asList("shatanaka", "shatanaka")));
        e.add(new ArrayList<>(Arrays.asList("stakada", "stakada")));

        return e;
    }
}
