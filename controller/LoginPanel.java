package controller;

import components.ColleagueButton;
import components.ColleagueTextField;
import model.UserReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener, Mediator {
    private ColleagueTextField textUser;
    private ColleagueTextField textUserId;
    private ColleagueButton buttonOk;
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(400, 600);

        this.setLayout(new FlowLayout());

        this.createColleagues();

        // JPanel for Main Message
        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new GridLayout(1, 1));
        spacePanel.setPreferredSize(new Dimension(300, 100));
        JLabel subject = new JLabel(title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase());
        subject.setHorizontalAlignment(JLabel.CENTER);
        subject.setFont(new Font("Arial", Font.PLAIN, 30));
        spacePanel.add(subject);

        // JPanel for Input
        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(300, 100));

        GridLayout inputLayout = new GridLayout(3, 2);
        inputLayout.setHgap(10);
        inputPanel.setLayout(inputLayout);

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(textUser);
        inputPanel.add(new JLabel("Member ID:"));
        inputPanel.add(textUserId);

        // JPanel for OK Button
        JPanel submitPanel = new JPanel();
        submitPanel.setPreferredSize(new Dimension(300, 50));

        submitPanel.setLayout(new GridLayout(1, 1));
        submitPanel.add(buttonOk);

        this.add(spacePanel);
        this.add(inputPanel);
        this.add(submitPanel);
    }

    public void createColleagues() {

        textUser = new ColleagueTextField("", 10);
        textUserId = new ColleagueTextField("", 10);
        buttonOk = new ColleagueButton("Login");

        textUser.setMediator(this);
        textUserId.setMediator(this);
        buttonOk.setMediator(this);

        buttonOk.addActionListener(buttonOk);
    }

    public void colleagueChanged() {
        // Login
        if (UserReader.isCorrectUser(textUser.getText(), textUserId.getText())) {

        } else {
            JOptionPane.showMessageDialog(this, "Login Failure", "Warn", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        System.exit(0);
    }
}
