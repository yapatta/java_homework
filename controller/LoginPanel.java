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

public class LoginPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 400;
    public static int WIDTH = 300;
    public static int PANEL_HEIGHT = 600;

    private ColleagueTextField textUser;
    private ColleagueTextField textUserId;
    private JPasswordField textPassWord;
    private ColleagueButton buttonOk;
    private JButton exitButton;
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(ALL_PANEL_WIDTH, PANEL_HEIGHT);

        this.setLayout(new FlowLayout());

        this.createColleagues();

        // JPanel for Main Message
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

        GridLayout buttonLayout = new GridLayout(0,1);
        buttonLayout.setVgap(20);
        buttonsPanel.setLayout(buttonLayout);
        buttonsPanel.add(buttonOk);
        buttonsPanel.add(exitButton);

        this.add(spacePanel);
        this.add(GUILibrary.getHr(WIDTH, 0));
        this.add(inputPanel);
        this.add(GUILibrary.getHr(WIDTH, 0));
        this.add(buttonsPanel);

    }

    public void createColleagues() {

        textUser = new ColleagueTextField("", 10);
        textUserId = new ColleagueTextField("", 10);
        //textUserId = new JPasswordField();
        //textUserId.setEchoChar('*');
        //textPassWord = new JPasswordField();
        //textPassWord.setEchoChar('*');

        buttonOk = new ColleagueButton("Login");

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        textUser.setMediator(this);
        textUserId.setMediator(this);
        buttonOk.setMediator(this);

        buttonOk.addActionListener(buttonOk);
    }

    public void colleagueChanged() {
        // Login
        //String passWordStr = new String(textPassWord.getPassword());
        if (UserReader.isCorrectUser(textUser.getText(), textUserId.getText())) {
            // if use Password field, make this line Changed: testUser.getText() -> textUserId.getText()
            this.mainFrame.setUserReader(textUser.getText());
            this.mainFrame.setNextPanelName(MainFrame.MyConcertsPanelName);
            this.mainFrame.colleagueChanged();
        } else if (UserReader.isCorrectAdmin(textUser.getText(), textUserId.getText())){
            JOptionPane.showMessageDialog(this, "You are Admin", "info", JOptionPane.INFORMATION_MESSAGE);
            this.mainFrame.setUserReader(textUser.getText());
            this.mainFrame.setNextPanelName(MainFrame.AdminPanelName);
            this.mainFrame.colleagueChanged();
        } else {
            JOptionPane.showMessageDialog(this, "Login Failure", "Warn", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton){
            System.exit(0);
        }
    }
}
