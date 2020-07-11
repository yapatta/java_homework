package controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    public static String[] FrameNames = {"login", "concerts", "user_concerts"};

    // make several panels
    LoginPanel loginPanel = new LoginPanel(this, FrameNames[0]);
    // FIXME: add other frames for concerts and user concerts.
    // ex: ConcertFrame concertFrame = new ConcertFrame(FrameNames[1]);

    public MainFrame() {
        this.add(loginPanel);
        loginPanel.setVisible(true);

        // FIXME: add other frames for concerts and user concerts.
        // ex this.add(concertFrame);
        // concertFrame.setVisible(false);

        // FIXME: modify accordingly
        // this.setBounds(100, 100, 1200, 800);
        setSize(400, 600);
    }
}
