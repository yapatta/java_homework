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

    public UserReader(String userName) {
        this.userName = userName;
    }

    private String getUserConcertsFilePath() {
        return System.getProperty("user.dir") + "/src/model/data/user_concerts/" + "concerts_" + this.userName + ".csv";
    }

    // 接頭辞からコンサート名を検索
    public static ArrayList<ArrayList<String>> searchForConcerts(String prefix) {
        ArrayList<ArrayList<String>> allConcerts = getAllConcerts();

        String regex = "^" + prefix;
        Pattern p = Pattern.compile(regex);

        ArrayList<ArrayList<String>> retConcerts = new ArrayList<>();

        for(var concert : allConcerts) {
            // コンサート名取得
            Matcher m = p.matcher(concert.get(0));

            if(m.find()) {
                retConcerts.add(concert);
            }
        }

        return retConcerts;
    }

    public void updateMyConcerts(ArrayList<String> addedConcert) {
        ArrayList<ArrayList<String>> mc = getMyConcerts();

        // すでに登録してあるコンサートを追加しようとした場合は何も行わない
        for (var c : mc) {
            if (addedConcert.equals(c)) {
                return;
            }
        }

        mc.add(addedConcert);

        BufferedWriter myConcertBuffer = null;
        try {
            myConcertBuffer = Files.newBufferedWriter(Paths.get(getUserConcertsFilePath()), Charset.defaultCharset());
        } catch (IOException e) {
            System.exit(1);
        }

        for (var concert : mc) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < concert.size(); i++) {
                s.append(concert.get(i));
                if (i != concert.size() - 1) {
                    s.append(",");
                }
            }

            try {
                myConcertBuffer.write(s.toString());
                myConcertBuffer.newLine();
            } catch (IOException e) {
                System.exit(1);
            }
        }

        try {
            myConcertBuffer.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void deleteMyConcert(int index) {
        getMyConcerts().remove(index);
    }

    public ArrayList<ArrayList<String>> getMyConcerts() {
        String fname = getUserConcertsFilePath();
        File f = new File(fname);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("ファイル読み込み失敗");
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

    private static String getFileNamePath(String fileName) {
        return System.getProperty("user.dir") + "/src/model/data/" + fileName;
    }

    public static boolean isCorrectUser(String userName, String password) {
        ArrayList<ArrayList<String>> usersList = getAllUsers();

        for (var up : usersList) {
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
            System.out.println("ファイル読み込み失敗");
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
            System.out.println("ファイル読み込み失敗");
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
}
