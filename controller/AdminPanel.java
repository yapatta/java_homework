package controller;

import components.ColleagueButton;
import lib.GUILibrary;
import model.UserReader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;


public class AdminPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 900;
    public static int PANEL_WIDTH = 800;
    public static int PANEL_HEIGHT = 1000;
    private ColleagueButton searchConcertButton;
    private ColleagueButton searchUserButton;
    private ColleagueButton deleteConcertButton;
    private ColleagueButton deleteUserButton;
    private ColleagueButton addNewConcertButton;
    private ColleagueButton addNewUserButton;
    private ColleagueButton buttonLogout;
    private JComboBox<String> comboConcerts;
    private JComboBox<String> comboUsers;
    private JTextArea resultTextArea;
    private JPanel searchConcertPanel;
    private JPanel searchUserPanel;
    // private int numberOfReservationPerson;
    private final MainFrame mainFrame;

    public AdminPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(ALL_PANEL_WIDTH, PANEL_HEIGHT);
        this.setLayout(new FlowLayout());
        this.createColleagues();

        // JPanel for Main Message
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 1));
        labelPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
        title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase() + "Screen";
        JLabel subject = new JLabel(title);

        subject.setHorizontalAlignment(JLabel.CENTER);
        subject.setFont(new Font("Arial", Font.PLAIN, 30));
        labelPanel.add(subject);

        // search Concert Panel
        searchConcertPanel = new JPanel();
        searchConcertPanel.setLayout(new GridLayout(1, 3));
        searchConcertPanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 30));

        JLabel searchConcertLabel = new JLabel("Concert Information:");
        searchConcertLabel.setHorizontalAlignment(JLabel.CENTER);

        // JCombo Concerts
        comboConcerts = new JComboBox<>();
        loadComboConcerts();

        searchConcertPanel.add(searchConcertLabel);
        searchConcertPanel.add(comboConcerts);
        searchConcertPanel.add(searchConcertButton);
        searchConcertPanel.add(deleteConcertButton);


        // search User Panel
        searchUserPanel = new JPanel();
        searchUserPanel.setLayout(new GridLayout(1, 4));
        searchUserPanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 30));

        JLabel searchUserLabel = new JLabel("User Information:");
        searchUserLabel.setHorizontalAlignment(JLabel.CENTER);

        comboUsers = new JComboBox<>();
        loadComboUsers();

        searchUserPanel.add(searchUserLabel);
        searchUserPanel.add(comboUsers);
        searchUserPanel.add(searchUserButton);
        searchUserPanel.add(deleteUserButton);

        //Result or Edit Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, 1));

        resultTextArea = new JTextArea(10, 50);
        JScrollPane resultScroll = new JScrollPane(resultTextArea);
        resultScroll.setBorder(new TitledBorder("Result"));

        //Create New Data Panel
        JPanel createNewDataPanel = new JPanel();
        createNewDataPanel.setLayout(new GridLayout(1, 3));
        createNewDataPanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 30));

        JLabel createNewDataLabel = new JLabel("Create New Data:");
        createNewDataLabel.setHorizontalAlignment(JLabel.CENTER);

        createNewDataPanel.add(createNewDataLabel);
        createNewDataPanel.add(addNewConcertButton);
        createNewDataPanel.add(addNewUserButton);

        //logout Panel
        JPanel logoutPanel = new JPanel();
        logoutPanel.setPreferredSize(new Dimension(PANEL_WIDTH / 3, 50));
        logoutPanel.setLayout(new GridLayout(1, 4));
        logoutPanel.add(buttonLogout);

        this.add(labelPanel);
        this.add(searchConcertPanel);
        add(GUILibrary.getHr(800, 0));
        this.add(searchUserPanel);
        add(GUILibrary.getHr(800, 0));
        this.add(resultScroll);
        add(GUILibrary.getHr(800, 0));
        this.add(createNewDataPanel);
        add(GUILibrary.getHr(800, 0));
        this.add(logoutPanel);
    }


    @Override
    public void createColleagues() {
        searchConcertButton = new ColleagueButton("Show");
        searchUserButton = new ColleagueButton("Show");
        deleteConcertButton = new ColleagueButton("Delete");
        deleteUserButton = new ColleagueButton("Delete");
        addNewConcertButton = new ColleagueButton("New Concert");
        addNewUserButton = new ColleagueButton("New User");
        buttonLogout = new ColleagueButton("Logout");

        searchConcertButton.setMediator(this);
        searchUserButton.setMediator(this);
        deleteConcertButton.setMediator(this);
        deleteUserButton.setMediator(this);
        addNewConcertButton.setMediator(this);
        addNewUserButton.setMediator(this);
        buttonLogout.setMediator(this);

        searchConcertButton.addActionListener(searchConcertButton);
        searchUserButton.addActionListener(searchUserButton);
        deleteConcertButton.addActionListener(deleteConcertButton);
        deleteUserButton.addActionListener(deleteUserButton);
        addNewConcertButton.addActionListener(addNewConcertButton);
        addNewUserButton.addActionListener(addNewUserButton);
        buttonLogout.addActionListener(buttonLogout);
    }

    @Override
    public void colleagueChanged() {
        if (this.searchConcertButton.nowAction()) {
            var searchConcertName = (String) comboConcerts.getSelectedItem();

            System.out.println(searchConcertName);

            ArrayList<ArrayList<String>> resultUsersList = UserReader.getSpecificConcerts(searchConcertName);

            resultTextArea.setText("");

            if (resultUsersList != null) {
                for (ArrayList<String> strings : resultUsersList) {
                    resultTextArea.append(strings.get(0));
                    resultTextArea.append("\n");
                }
            }

        } else if (this.searchUserButton.nowAction()) {
            var searchUserName = (String) comboUsers.getSelectedItem();

            System.out.println(searchUserName);

            ArrayList<ArrayList<String>> resultConcertsList = UserReader.getUserConcerts(searchUserName);

            resultTextArea.setText("");

            for (ArrayList<String> strings : resultConcertsList) {
                resultTextArea.append(strings.get(1));
                resultTextArea.append("\n");
            }

        } else if (this.deleteConcertButton.nowAction()) {
            var searchConcertName = (String) comboConcerts.getSelectedItem();

            JOptionPane.showMessageDialog(this, "Are you sure to delete " + searchConcertName + " ?", "Warning", JOptionPane.WARNING_MESSAGE);

            UserReader.deleteConcertByName(searchConcertName);

            this.reload();
        } else if (this.deleteUserButton.nowAction()) {
            var searchUserName = (String) comboUsers.getSelectedItem();

            JOptionPane.showMessageDialog(this, "Are you sure to delelte " + searchUserName + " ?", "Warning", JOptionPane.WARNING_MESSAGE);

            UserReader.deleteUserByName(searchUserName);

            this.reload();
        } else if (this.buttonLogout.nowAction()) {
            this.mainFrame.unsetUserReader();
            this.mainFrame.setNextPanelName(MainFrame.LoginPanelName);
        } else if (this.addNewUserButton.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.CreateUserPanelName);
        } else if (this.addNewConcertButton.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.CreateConcertPanelName);
        }

        this.mainFrame.colleagueChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void reload() {
        loadComboConcerts();
        loadComboUsers();

        resultTextArea.setText("");
    }

    public void loadComboConcerts() {
        Vector<String> allConcertsVector = new Vector<>(UserReader.getAllConcertsName());

        comboConcerts.setModel(new DefaultComboBoxModel<String>(allConcertsVector));
    }

    public void loadComboUsers() {
        ArrayList<ArrayList<String>> allUsersList = UserReader.getAllUsers();
        Vector<String> allUserVector = new Vector<String>();

        for (ArrayList<String> userList : allUsersList) {
            allUserVector.add(userList.get(0));
        }

        comboUsers.setModel(new DefaultComboBoxModel<String>(allUserVector));
    }
}
