package mediator;

import components.ColleagueButton;
import components.ColleagueTextField;
import model.UserReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener, Mediator {
    private ColleagueTextField textUser;
    private ColleagueTextField textUserId;
    private ColleagueButton buttonOk;

    public LoginFrame(String title) {
        super(title);

        setSize(400, 600);
        // Position Center
        setLocationRelativeTo(null);
        // Finish the application when quit button is pushed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        createColleagues();

        // JPanel for Main Message
        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new BorderLayout());
        JLabel subject = new JLabel("Very Useful" + title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase());
        subject.setFont(new Font("Arial", Font.PLAIN, 20));
        spacePanel.add(subject);
        spacePanel.add(Box.createVerticalStrut(50), BorderLayout.SOUTH);

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

        Container contentPane = getContentPane();
        contentPane.add(spacePanel);
        contentPane.add(inputPanel);
        contentPane.add(submitPanel);

        setVisible(true);
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
            JOptionPane.showMessageDialog(this, "Success", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failure", "Warn", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        System.exit(0);
    }
}
