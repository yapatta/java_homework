package controller;

import model.UserReader;

import javax.swing.*;

public class MainFrame extends JFrame implements Mediator {
    public static String[] FrameNames = {"login", "concerts", "user_concerts"};

    // make several panels
    LoginPanel loginPanel = new LoginPanel(this, FrameNames[0]);
    // FIXME: add other frames for concerts and user concerts.
    ConcertsPanel concertsPanel = new ConcertsPanel(this, FrameNames[1]);

    public MainFrame() {
        this.add(loginPanel);
        loginPanel.setVisible(false);

        // FIXME: add other frames for concerts and user concerts.
        // ex this.add(concertPanel);
        // concertPanel.setVisible(false);
        this.add(concertsPanel);
        concertsPanel.setVisible(true);

        // FIXME: modify accordingly
        setSize(800, 1000);
    }

    // hide old panel and show new panel
    public static void showNewPanel(JPanel nowPanel, JPanel newPanel) {
        nowPanel.setVisible(false);
        newPanel.setVisible(true);
    }

    public void showConcertsPanel(JPanel nowPanel) {
        nowPanel.setVisible(false);

        this.concertsPanel.setConcerts(UserReader.getAllConcerts());

        // update concerts to show
        this.concertsPanel.initialize();

        this.concertsPanel.setVisible(true);
    }

    public void createColleagues() {

    }

    public void colleagueChanged() {

    }
}
