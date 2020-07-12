package controller;

import components.ColleagueButton;
import components.ConcertColumnLabel;
import lib.GUILibrary;
import model.UserReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyConcertsPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 900;
    public static int PANEL_WIDTH = 800;
    public static int PANEL_HEIGHT = 1000;

    private ColleagueButton buttonDelete;
    private ColleagueButton buttonAllConcerts;
    private ColleagueButton buttonLogout;
    private final MainFrame mainFrame;

    public MyConcertsPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(ALL_PANEL_WIDTH, PANEL_HEIGHT);

        this.setLayout(new FlowLayout());

        this.createColleagues();

        // JPanel for Main Message
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 1));
        labelPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
        JLabel subject = new JLabel(title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase());
        subject.setHorizontalAlignment(JLabel.CENTER);
        subject.setFont(new Font("Arial", Font.PLAIN, 30));
        labelPanel.add(subject);

        // JPanel for category
        JPanel categoryPanel = new JPanel();

        categoryPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));

        categoryPanel.add(new ConcertColumnLabel("Register"));
        categoryPanel.add(new ConcertColumnLabel("Title"));
        categoryPanel.add(new ConcertColumnLabel("Genre"));
        categoryPanel.add(new ConcertColumnLabel("Day"));
        categoryPanel.add(new ConcertColumnLabel("Artist"));
        categoryPanel.add(new ConcertColumnLabel("Fee"));
        categoryPanel.add(new ConcertColumnLabel("Capacity"));

        //register and logoutButton
        JPanel clickPanel = new JPanel();

        clickPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));

        clickPanel.setLayout(new GridLayout(1, 1));
        clickPanel.add(buttonDelete);
        clickPanel.add(buttonAllConcerts);
        clickPanel.add(buttonLogout);


        this.add(labelPanel);
        add(GUILibrary.getHr(800, 0));
        this.add(categoryPanel, BorderLayout.CENTER);
        add(GUILibrary.getHr(800, 0));
        this.add(clickPanel);
    }

    public void initialize() {
        var allConcerts = UserReader.getAllConcerts();
        var myConcerts = this.mainFrame.getUserReader().getMyConcerts();

        // FIXME: change the view
    }

    public void createColleagues() {
        buttonDelete = new ColleagueButton("Delete");
        buttonAllConcerts = new ColleagueButton("Show All Concerts");
        buttonLogout = new ColleagueButton("Logout");

        buttonDelete.setMediator(this);
        buttonAllConcerts.setMediator(this);
        buttonLogout.setMediator(this);

        buttonDelete.addActionListener(buttonDelete);
        buttonAllConcerts.addActionListener(buttonAllConcerts);
        buttonLogout.addActionListener(buttonLogout);
    }

    public void colleagueChanged() {
        if (this.buttonDelete.nowAction()) {
            // FIXME: should add checked concerts to myConcerts
            ArrayList<Integer> deletedConcertsIndexList = new ArrayList<>();

            /*
            for (var i : deletedConcertsIndexList) {
                this.mainFrame.getUserReader().deleteMyConcert(i);
            }
             */

            this.mainFrame.setNextPanelName(MainFrame.MyConcertsPanelName);

        } else if (this.buttonAllConcerts.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.ConcertsPanelName);
        } else if (this.buttonLogout.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.LoginPanelName);
        }

        this.mainFrame.colleagueChanged();
    }

    public void actionPerformed(ActionEvent e) {

    }
}
