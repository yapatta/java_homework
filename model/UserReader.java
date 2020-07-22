package model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserReader {
    private final String userName;
    public static int NAME_INDEX = 1;

    public UserReader(String userName) {
        this.userName = userName;
    }

    public static Object[][] getAllConcertsAsObject() {
        return getConcertsAsObject(getAllConcerts());
    }

    public Object[][] getMyConcertsAsObject() {
        return getConcertsAsObject(this.getMyConcerts());
    }

    public static Object[][] getConcertsAsObject(ArrayList<ArrayList<String>> concerts) {
        Object[][] retConcerts = new Object[concerts.size()][7];

        for (int i = 0; i < concerts.size(); i++) {
            for (int j = 0; j < concerts.get(i).size(); j++) {
                if (j == 0) {
                    retConcerts[i][j] = concerts.get(i).get(j).equals("true");
                } else {
                    retConcerts[i][j] = concerts.get(i).get(j);
                }
            }
        }
        return retConcerts;
    }

    public Object[][] getShowConcertsAsObject() {
        var allConcerts = getAllConcerts();
        var myConcerts = this.getMyConcerts();

        ArrayList<ArrayList<String>> retConcertsObject = new ArrayList<>();
        int index = 0;
        for (var concert : allConcerts) {
            ArrayList<String> retConcert = concert;
            for (var myConcert : myConcerts) {
                boolean sameFlag = true;
                for (int i = 1; i < 7; i++) {
                    if (!concert.get(i).equals(myConcert.get(i))) {
                        sameFlag = false;
                        break;
                    }
                }
                if (sameFlag && myConcert.get(0).equals("true")) {
                    retConcert = myConcert;
                    break;
                }
            }

            retConcertsObject.add(retConcert);
        }

        return getConcertsAsObject(retConcertsObject);
    }

    public String getUserName() {
        return this.userName;
    }

    private String getUserConcertsFilePath() {
        return System.getProperty("user.dir") + "/model/data/user_concerts/" + "concerts_" + this.userName + ".csv";
    }

    // based on the idea in which concerts name isn't duplicated

    public void writeMyConcerts(ArrayList<ArrayList<String>> mc) {
        writeUserConcerts(this.getUserName(), mc);
    }

    public ArrayList<ArrayList<String>> getMyConcerts() {
        String fname = getUserConcertsFilePath();
        File f = new File(fname);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Failed to read a file");
                System.exit(1);
            }

            return new ArrayList<>();
        }

        BufferedReader fbr = null;

        try {
            fbr = new BufferedReader(new FileReader(f));
        } catch (IOException e) {
            System.exit(1);
        }

        ArrayList<ArrayList<String>> myConcerts = new ArrayList<ArrayList<String>>();

        while (true) {
            String tmpLine = null;

            try {
                tmpLine = fbr.readLine();
            } catch (IOException e) {
                System.exit(1);
            }

            if (tmpLine == null) break;

            String[] tmp = tmpLine.split(",", 0);
            ArrayList<String> concert = new ArrayList<>(Arrays.asList(tmp));
            myConcerts.add(concert);
        }

        try {
            fbr.close();
        } catch (IOException e) {
            System.exit(1);
        }

        return myConcerts;
    }

    public static void deleteUserByName(String uname) {
        ArrayList<ArrayList<String>> allUsers = UserReader.getAllUsers();
        ArrayList<ArrayList<String>> retUsers = new ArrayList<>();

        for (var user : allUsers) {
            if (!uname.equals(user.get(0))) {
                retUsers.add(user);
            }
        }

        writeUsers(retUsers);

        File file = new File(getUserConcertsFilePath(uname));

        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Cannot delete file");
            }
        }
    }

    public static void makeUser(ArrayList<String> addedUser) {
        var allUsers = getAllUsers();
        allUsers.add(addedUser);
        writeUsers(allUsers);
    }

    public static void writeUsers(ArrayList<ArrayList<String>> users) {
        BufferedWriter UsersBuffer = null;

        try {
            UsersBuffer = Files.newBufferedWriter(Paths.get(getFileNamePath("users.csv")), Charset.defaultCharset());
        } catch (IOException e) {
            System.exit(1);
        }

        for (var user : users) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < user.size(); i++) {
                s.append(user.get(i));
                if (i != user.size() - 1) {
                    s.append(",");
                }
            }

            try {
                UsersBuffer.write(s.toString());
                UsersBuffer.newLine();
            } catch (IOException e) {
                System.exit(1);
            }
        }

        try {
            UsersBuffer.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public static void writeUserConcerts(String userName, ArrayList<ArrayList<String>> userConcerts) {
        BufferedWriter userConcertBuffer = null;

        try {
            userConcertBuffer = Files.newBufferedWriter(Paths.get(getUserConcertsFilePath(userName)), Charset.defaultCharset());
        } catch (IOException e) {
            System.exit(1);
        }

        for (var concert : userConcerts) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < concert.size(); i++) {
                s.append(concert.get(i));
                if (i != concert.size() - 1) {
                    s.append(",");
                }
            }

            try {
                userConcertBuffer.write(s.toString());
                userConcertBuffer.newLine();
            } catch (IOException e) {
                System.exit(1);
            }
        }

        try {
            userConcertBuffer.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public static void deleteConcertByName(String concertName) {
        ArrayList<ArrayList<String>> allConcerts = getAllConcerts();
        ArrayList<ArrayList<String>> retConcerts = new ArrayList<>();

        for (var concert : allConcerts) {
            if (!concert.get(NAME_INDEX).equals(concertName)) {
                retConcerts.add(concert);
            }
        }

        writeBaseConcerts(retConcerts);

        // search for users -> delete if the user has the concert
        ArrayList<ArrayList<String>> allUsers = UserReader.getAllUsers();

        for (ArrayList<String> user : allUsers) {
            String uname = user.get(NAME_INDEX);

            var concerts = getUserConcerts(uname);
            ArrayList<ArrayList<String>> retUserConcerts = new ArrayList<>();

            for (ArrayList<String> concert : concerts) {
                if (!concert.get(NAME_INDEX).equals(concertName)) {
                    concert.set(0, "true");
                    retUserConcerts.add(concert);
                }
            }

            writeUserConcerts(uname, retUserConcerts);
        }
    }

    public static void writeBaseConcerts(ArrayList<ArrayList<String>> concerts) {
        BufferedWriter concertBuffer = null;

        try {
            concertBuffer = Files.newBufferedWriter(Paths.get(getFileNamePath("concerts.csv")), Charset.defaultCharset());
        } catch (IOException e) {
            System.exit(1);
        }

        for (var concert : concerts) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < concert.size(); i++) {
                s.append(concert.get(i));
                if (i != concert.size() - 1) {
                    s.append(",");
                }
            }

            try {
                concertBuffer.write(s.toString());
                concertBuffer.newLine();
            } catch (IOException e) {
                System.exit(1);
            }
        }

        try {
            concertBuffer.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    private static String getUserConcertsFilePath(String userName) {
        return System.getProperty("user.dir") + "/model/data/user_concerts/" + "concerts_" + userName + ".csv";
    }

    private static String getFileNamePath(String fileName) {
        return System.getProperty("user.dir") + "/model/data/" + fileName;
    }

    public static boolean isCorrectUser(String userName, String password) {
        ArrayList<ArrayList<String>> usersList = getAllUsers();

        for (var up : usersList) {
            // if use password this index changed (0,1) -> (1,2)
            if (userName.equals(up.get(0)) && password.equals(up.get(1))) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCorrectAdmin(String userName, String password) {
        ArrayList<ArrayList<String>> usersList = getAllAdmins();

        for (var up : usersList) {
            // if use password this index changed (0,1) -> (1,2)
            if (userName.equals(up.get(0)) && password.equals(up.get(1))) {
                return true;
            }
        }

        return false;
    }

    public static ArrayList<ArrayList<String>> getAllUsers() {
        List<String> rows = null;

        try {
            rows = Files.readAllLines(Paths.get(getFileNamePath("users.csv")), Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Failed to read a file");
            System.exit(1);
        }

        ArrayList<ArrayList<String>> userslist = new ArrayList<>();

        for (String row : rows) {
            ArrayList<String> up = new ArrayList<>();
            Collections.addAll(up, row.split(",", 0));
            userslist.add(up);
        }

        return userslist;
    }

    public static ArrayList<ArrayList<String>> getAllConcerts() {
        List<String> rows = null;

        try {
            rows = Files.readAllLines(Paths.get(getFileNamePath("concerts.csv")), Charset.defaultCharset());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("Failed to read a file");
            System.exit(1);
        }

        ArrayList<ArrayList<String>> concertslist = new ArrayList<>();

        for (String row : rows) {
            ArrayList<String> c = new ArrayList<>();
            Collections.addAll(c, row.split(",", 0));
            concertslist.add(c);
        }

        return concertslist;
    }

    public static ArrayList<String> getAllConcertsName() {
        ArrayList<ArrayList<String>> allConcerts = getAllConcerts();

        ArrayList<String> allConcertsName = new ArrayList<String>();

        for (int i = 0; i < allConcerts.size(); i++) {
            ArrayList<String> concert = allConcerts.get(i);
            allConcertsName.add(concert.get(NAME_INDEX));
        }

        return allConcertsName;
    }

    public static ArrayList<ArrayList<String>> getAllAdmins() {
        List<String> rows = null;

        try {
            rows = Files.readAllLines(Paths.get(getFileNamePath("admin.csv")), Charset.defaultCharset());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("Failed to read a admin file");
            System.exit(1);
        }

        ArrayList<ArrayList<String>> adminslist = new ArrayList<>();

        for (String row : rows) {
            ArrayList<String> c = new ArrayList<>();
            Collections.addAll(c, row.split(",", 0));
            adminslist.add(c);
        }

        return adminslist;
    }

    public static ArrayList<ArrayList<String>> getUserConcerts(String userName) {
        List<String> rows = null;

        try {
            String fname = getUserConcertsFilePath(userName);
            File f = new File(fname);

            if (!f.exists()) {
                return new ArrayList<>();
            }

            rows = Files.readAllLines(Paths.get(fname), Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Failed to read a file");
            System.exit(1);
        }

        ArrayList<ArrayList<String>> concertslist = new ArrayList<>();

        for (String row : rows) {
            ArrayList<String> c = new ArrayList<>();
            Collections.addAll(c, row.split(",", 0));
            concertslist.add(c);
        }

        return concertslist;
    }


    // Search for specific subscribers with a concert name
    public static ArrayList<ArrayList<String>> getSpecificConcerts(String concertName) {
        ArrayList<ArrayList<String>> allUsers = UserReader.getAllUsers();
        ArrayList<ArrayList<String>> retUsers = new ArrayList<>();

        try {
            for (ArrayList<String> user : allUsers) {
                String uname = user.get(NAME_INDEX);

                var concerts = getUserConcerts(uname);

                for (ArrayList<String> concert : concerts) {
                    if (concertName.equals(concert.get(NAME_INDEX))) {
                        retUsers.add(user);
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
            return null;
        }
        return retUsers;
    }

    // search for concert name with prefix
    public static ArrayList<ArrayList<String>> searchForConcerts(String prefix) {
        ArrayList<ArrayList<String>> allConcerts = getAllConcerts();

        String regex = "^" + prefix;
        Pattern p = Pattern.compile(regex);

        ArrayList<ArrayList<String>> retConcerts = new ArrayList<>();

        for (var concert : allConcerts) {
            Matcher m = p.matcher(concert.get(NAME_INDEX));

            if (m.find()) {
                retConcerts.add(concert);
            }
        }

        return retConcerts;
    }

    public static ArrayList<String> getConcertByIndex(int index) {
        var allConcerts = getAllConcerts();
        for (int i = 0; i < allConcerts.size(); i++) {
            if (index == i) {
                return allConcerts.get(i);
            }
        }
        return new ArrayList<String>();
    }
}
