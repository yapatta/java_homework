package controller;

import model.UserReader;

import javax.swing.*;

public class MainFrame extends JFrame implements Mediator {
    public static String LoginPanelName = "Login";
    public static String ConcertsPanelName = "Concerts";
    public static String MyConcertsPanelName = "MyConcerts";
    public static int WIDTH = 900;
    public static int HEIGHT = 1000;

    // make several panels
    LoginPanel loginPanel;
    ConcertsPanel concertsPanel;
    MyConcertsPanel myConcertsPanel;

    private UserReader ur;
    private String nextPanelName = "";


    public MainFrame() {
        this.createColleagues();

        this.add(loginPanel);
        loginPanel.setVisible(true);

        this.add(concertsPanel);
        concertsPanel.setVisible(false);

        this.add(myConcertsPanel);
        myConcertsPanel.setVisible(false);

        // Start with Login Panel
        this.setSize(LoginPanel.ALL_PANEL_WIDTH, LoginPanel.PANEL_HEIGHT);
        // this.setSize(ConcertsPanel.ALL_PANEL_WIDTH, ConcertsPanel.PANEL_HEIGHT);
    }

    public void showLoginPanel() {
        this.setAllVisibleFalse();

        this.setSize(LoginPanel.ALL_PANEL_WIDTH, LoginPanel.PANEL_HEIGHT);
        this.setLocationRelativeTo(null);

        this.loginPanel.setVisible(true);
    }

    public void showConcertsPanel() {
        this.setAllVisibleFalse();

        this.setSize(ConcertsPanel.ALL_PANEL_WIDTH, ConcertsPanel.PANEL_HEIGHT);
        this.setLocationRelativeTo(null);

        this.concertsPanel.initialize();

        this.concertsPanel.setVisible(true);
    }

    public void showMyConcertsPanel() {
        this.setAllVisibleFalse();

        this.setSize(ConcertsPanel.ALL_PANEL_WIDTH, ConcertsPanel.PANEL_HEIGHT);
        this.setLocationRelativeTo(null);

        this.myConcertsPanel.initialize();

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
            this.showMyConcertsPanel();
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

    public void setUserReader(String userName) {
        this.ur = new UserReader(userName);
    }

    public UserReader getUserReader() {
        return this.ur;
    }

    public void setNextPanelName(String npn) {
        this.nextPanelName = npn;
    }

    public String getNextPanelName() {
        return this.nextPanelName;
    }
}
