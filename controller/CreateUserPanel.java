package controller;

import components.Colleague;
import components.ColleagueButton;
import components.ColleagueTextField;
import lib.GUILibrary;
import model.UserReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 400;
    public static int WIDTH = 300;
    public static int PANEL_HEIGHT = 600;

    private ColleagueTextField textUser;
    private ColleagueTextField textUserId;
    private JPasswordField textPassWord;
    private ColleagueButton buttonOk;
    private ColleagueButton buttonBack;
    private MainFrame mainFrame;

    public CreateUserPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(ALL_PANEL_WIDTH, PANEL_HEIGHT);

        this.setLayout(new FlowLayout());

        this.createColleagues();

        //JPanel for Main Message
        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new GridLayout(1, 1));
        spacePanel.setPreferredSize(new Dimension(WIDTH, 100));
        JLabel subject = new JLabel(title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase());
        subject.setHorizontalAlignment(JLabel.CENTER);
        subject.setFont(new Font("Arial", Font.PLAIN, 30));
        spacePanel.add(subject);

        // JPanel for Input
        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(WIDTH, 100));

        GridLayout inputLayout = new GridLayout(3, 2);
        inputLayout.setHgap(10);
        inputPanel.setLayout(inputLayout);

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(textUser);
        inputPanel.add(new JLabel("Member ID:"));
        inputPanel.add(textUserId);
        //inputPanel.add(new JLabel("Pass Word:"));
        //inputPanel.add(textPassWord);

        // JPanel for Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(WIDTH, 100));

        GridLayout buttonLayout = new GridLayout(0, 1);
        buttonLayout.setVgap(20);
        buttonsPanel.setLayout(buttonLayout);
        buttonsPanel.add(buttonOk);
        buttonsPanel.add(buttonBack);

        this.add(spacePanel);
        this.add(GUILibrary.getHr(WIDTH, 0));
        this.add(inputPanel);
        this.add(GUILibrary.getHr(WIDTH, 0));
        this.add(buttonsPanel);

    }


    @Override
    public void createColleagues() {

        textUser = new ColleagueTextField("", 10);
        textUserId = new ColleagueTextField("", 10);

        buttonOk = new ColleagueButton("Register");
        buttonBack = new ColleagueButton("Back");

        textUser.setMediator(this);
        textUserId.setMediator(this);
        buttonOk.setMediator(this);
        buttonBack.setMediator(this);

        buttonOk.addActionListener(buttonOk);
        buttonBack.addActionListener(buttonBack);

    }

    @Override
    public void colleagueChanged() {
        // Create
        if (this.buttonOk.nowAction()) {
            // ********************************
            // Create data in users.csv below
            // ********************************
            JOptionPane.showMessageDialog(this, "Created!", "info", JOptionPane.INFORMATION_MESSAGE);
            this.mainFrame.setNextPanelName(MainFrame.AdminPanelName);
        } else if (this.buttonBack.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.AdminPanelName);
        }

        this.mainFrame.colleagueChanged();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
