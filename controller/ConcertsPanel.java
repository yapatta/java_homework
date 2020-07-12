package controller;

import components.ColleagueButton;
import components.ConcertColumnLabel;
import model.UserReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConcertsPanel extends JPanel implements ActionListener, Mediator {
    public static int WIDTH = 800;
    public static int HEIGHT = 1000;

    private ArrayList<ArrayList<String>> concerts = new ArrayList<>();
    private ColleagueButton buttonRegister;
    private ColleagueButton buttonLogout;
    private MainFrame mainFrame;

    public ConcertsPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(WIDTH, HEIGHT);

        this.setLayout(new FlowLayout());

        this.createColleagues();

        // JPanel for Main Message
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 1));
        labelPanel.setPreferredSize(new Dimension(WIDTH, 100));
        JLabel subject = new JLabel(title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase());
        subject.setHorizontalAlignment(JLabel.CENTER);
        subject.setFont(new Font("Arial", Font.PLAIN, 30));
        labelPanel.add(subject);

        // JPanel for category
        JPanel categoryPanel = new JPanel();

        categoryPanel.setPreferredSize(new Dimension(WIDTH, 100));

        categoryPanel.add(new ConcertColumnLabel("Register"));
        categoryPanel.add(new ConcertColumnLabel("Title"));
        categoryPanel.add(new ConcertColumnLabel("Genre"));
        categoryPanel.add(new ConcertColumnLabel("Day"));
        categoryPanel.add(new ConcertColumnLabel("Artist"));
        categoryPanel.add(new ConcertColumnLabel("Fee"));
        categoryPanel.add(new ConcertColumnLabel("Capacity"));

        //register and logoutButton
        JPanel clickPanel = new JPanel();

        clickPanel.setPreferredSize(new Dimension(WIDTH, 50));

        clickPanel.setLayout(new GridLayout(1, 1));
        clickPanel.add(buttonRegister);
        clickPanel.add(buttonLogout);


        this.add(labelPanel);
        add(getHr(800, 0));
        this.add(categoryPanel, BorderLayout.CENTER);
        add(getHr(800, 0));
        this.add(clickPanel);
    }

    public void setConcerts(ArrayList<ArrayList<String>> concerts) {
        this.concerts = concerts;
    }

    /*if change view then start initialize*/
    public void initialize() {
        this.setConcerts(UserReader.getAllConcerts());
    }


    /*set gui*/
    public void createColleagues() {
        buttonRegister = new ColleagueButton("Register");
        buttonLogout = new ColleagueButton("Logout");

        buttonRegister.setMediator(this);
        buttonLogout.setMediator(this);

        buttonRegister.addActionListener(buttonRegister);
        buttonLogout.addActionListener(buttonLogout);
    }

    /*change status*/
    public void colleagueChanged() {

    }

    /*Button action*/
    public void actionPerformed(ActionEvent e) {

    }

    public static JSeparator getHr(int width, int hight) {
        JSeparator sp = new JSeparator(JSeparator.HORIZONTAL);
        sp.setPreferredSize(new Dimension(width, hight));
        return sp;
    }
}
