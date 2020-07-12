package controller;

import javax.swing.*;

public class MainFrame extends JFrame implements Mediator {
    public static String LoginPanelName = "Login";
    public static String ConcertsPanelName = "Concerts";
    public static String MyConcertsPanelName = "MyConcerts";

    // make several panels
    LoginPanel loginPanel;
    ConcertsPanel concertsPanel;
    MyConcertsPanel myConcertsPanel;

    private String userName = "";
    private String nextPanelName = "";

    public MainFrame() {
        this.createColleagues();

        this.add(loginPanel);
        loginPanel.setVisible(true);

        this.add(concertsPanel);
        concertsPanel.setVisible(false);

        this.add(myConcertsPanel);
        myConcertsPanel.setVisible(false);

        // FIXME: modify accordingly
        // this is the size of main panel
        this.setSize(800, 1000);
    }

    public void showLoginPanel() {
        this.setAllVisibleFalse();

        this.loginPanel.setVisible(true);
    }

    public void showConcertsPanel() {
        this.setAllVisibleFalse();

        this.concertsPanel.initialize();

        this.concertsPanel.setVisible(true);
    }

    public void showMyConcertsPanel(String userName) {
        this.setAllVisibleFalse();

        this.myConcertsPanel.initialize(userName);

        this.myConcertsPanel.setVisible(true);
    }

    public void setAllVisibleFalse() {
        this.loginPanel.setVisible(false);
        this.concertsPanel.setVisible(false);
        this.myConcertsPanel.setVisible(false);
    }

    public void createColleagues() {
        this.loginPanel = new LoginPanel(this, LoginPanelName);
        this.concertsPanel = new ConcertsPanel(this, ConcertsPanelName);
        this.myConcertsPanel = new MyConcertsPanel(this, MyConcertsPanelName);
    }

    public void colleagueChanged() {
        // 渡された値を元に, Viewを変更
        // Login後MyConcertsに移行
        if (this.getNextPanelName().equals(MyConcertsPanelName)) {
            String userName = this.getUserName();

            this.showMyConcertsPanel(userName);
        } else if (this.getNextPanelName().equals(ConcertsPanelName)) {
            this.showConcertsPanel();
        } else if (this.getNextPanelName().equals(LoginPanelName)) {
            this.showLoginPanel();
        } else {
            System.err.println("存在しない画面です");
            // for now, go back to the login screen
            this.showLoginPanel();
        }
    }

    public void setUserName(String un) {
        this.userName = un;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setNextPanelName(String npn) {
        this.nextPanelName = npn;
    }

    public String getNextPanelName() {
        return this.nextPanelName;
    }
}
