package test;

import model.UserReader;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserReaderTest {
    @org.junit.jupiter.api.Test
    void getUserConcerts() {
        var ucs = UserReader.getUserConcerts("shatanaka");

        ArrayList<ArrayList<String>> expected = new ArrayList<>();
        expected.add(new ArrayList<String>(Arrays.asList("false", "Magical Mirai 2020", "Vocaloid", "2020/08/10", "Makuhari Messe", "5600", "100")));

        assertEquals(expected, ucs);
    }

    @org.junit.jupiter.api.Test
    void getMyConcerts() {
        UserReader ur = new UserReader("shatanaka");

        ArrayList<ArrayList<String>> expected = new ArrayList<>();
        expected.add(new ArrayList<String>(Arrays.asList("false", "Magical Mirai 2020", "Vocaloid", "2020/08/10", "Makuhari Messe", "5600", "100")));

        assertEquals(expected, ur.getMyConcerts());
    }

    @org.junit.jupiter.api.Test
    void deleteConcertByName() {
        UserReader.deleteConcertByName("Magical Mirai 2020");
    }

    @org.junit.jupiter.api.Test
    void deleteUserByName() {
        UserReader.deleteUserByName("syanagisawa");
    }

    @org.junit.jupiter.api.Test
    void makeUser() {
        UserReader.makeUser(new ArrayList<>(Arrays.asList("ytanaka", "ytanaka")));
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

    @org.junit.jupiter.api.Test
    void getAllConcerts() {
        ArrayList<ArrayList<String>> concerts = UserReader.getAllConcerts();

        ArrayList<ArrayList<String>> expected = getExpectedConcerts();

        assertEquals(expected, concerts);
    }

    ArrayList<ArrayList<String>> getExpectedConcerts() {
        ArrayList<ArrayList<String>> expected = new ArrayList<>();

        expected.add(new ArrayList<>(Arrays.asList("false", "Magical Mirai 2020", "Vocaloid", "2020/08/10", "Makuhari Messe", "5600", "100")));

        return expected;
    }

    @org.junit.jupiter.api.Test
    void getSpecificConcerts() {
        var users = UserReader.getSpecificConcerts("Magical Mirai 2020");

        ArrayList<ArrayList<String>> expected = new ArrayList<>();
        expected.add(new ArrayList<String>(Arrays.asList("shatanaka", "shatanaka")));

        assertEquals(expected, users);
    }

    @org.junit.jupiter.api.Test
    void searchForConcerts() {
        var c = UserReader.searchForConcerts("Magical");

        ArrayList<ArrayList<String>> expected = new ArrayList<>();
        expected.add(new ArrayList<String>(Arrays.asList("false", "Magical Mirai 2020", "Vocaloid", "2020/08/10", "Makuhari Messe", "5600", "100")));

        assertEquals(expected, c);

        c = UserReader.searchForConcerts("aiueo");

        expected = new ArrayList<>();

        assertEquals(expected, c);
    }

    @org.junit.jupiter.api.Test
    void getShowConcertsAsObject() {
        var co = new UserReader("yyahata").getShowConcertsAsObject();
        for (var c : co) {
            for (var cc : c) {
                System.out.println(cc);
            }
        }
    }
}
