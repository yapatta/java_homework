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
import java.util.EventListener;
import java.util.Vector;


public class AdminPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 900;
    public static int PANEL_WIDTH = 800;
    public static int PANEL_HEIGHT = 1000;
    private ColleagueButton searchConcertButton;
    private ColleagueButton searchUserButton;
    private ColleagueButton buttonLogout;
    private JComboBox<String> comboConcerts;
    private JComboBox<String> comboUsers;
    private JTextArea resultTextArea;
    //private JPanel searchConcertPanel;
    //private JPanel searchUserPanel;
    private String searchConcertName;
    private String searchUserName;
    private int numberOfReservationPerson;
    private MainFrame mainFrame;

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
        JPanel searchConcertPanel = new JPanel();
        searchConcertPanel.setLayout(new GridLayout(1, 3));
        searchConcertPanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 30));

        JLabel searchConcertLabel = new JLabel("Concert Information:");
        searchConcertLabel.setHorizontalAlignment(JLabel.CENTER);

        ArrayList<String> allConcertsList = UserReader.getAllConcertsName();
        Vector<String> allConcertsVector = new Vector<String>();
        for (int i = 0; i < allConcertsList.size(); i++) {
            allConcertsVector.add(allConcertsList.get(i));
        }
        //searchConcertName = allConcertsList.get(0);
        comboConcerts = new JComboBox<String>(allConcertsVector);
        comboConcerts.setEditable(true);
        //comboConcerts.addItemListener( new MyListSelect() );
        comboConcerts.addActionListener(new MyListSelect());

        searchConcertPanel.add(searchConcertLabel);
        searchConcertPanel.add(comboConcerts);
        searchConcertPanel.add(searchConcertButton);


        // search User Panel
        JPanel searchUserPanel = new JPanel();
        searchUserPanel.setLayout(new GridLayout(1, 4));
        searchUserPanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 30));

        JLabel searchUserLabel = new JLabel("User Information:");
        searchUserLabel.setHorizontalAlignment(JLabel.CENTER);

        ArrayList<ArrayList<String>> allUsersList = UserReader.getAllUsers();
        Vector<String> allUserVector = new Vector<String>();
        for (int i = 0; i < allUsersList.size(); i++) {
            ArrayList<String> userlist = allUsersList.get(i);
            allUserVector.add(userlist.get(0));
        }
        //searchConcertName = allUserVector.get(0);
        comboUsers = new JComboBox<String>(allUserVector);
        comboUsers.setEditable(true);
        //comboUsers.addItemListener( new MyListSelect() );
        comboUsers.addActionListener(new MyListSelect());

        searchUserPanel.add(searchUserLabel);
        //userCombo.add(new JLabel("Select User:"));
        searchUserPanel.add(comboUsers);
        searchUserPanel.add(searchUserButton);

        //Result or Edit Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, 1));

        resultTextArea = new JTextArea(10, 50);
        JScrollPane resultScroll = new JScrollPane(resultTextArea);
        resultScroll.setBorder(new TitledBorder("Result"));

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
        //####### temporary space for lugout button ######### //
        add(GUILibrary.getHr(800, 0));
        this.add(logoutPanel);


    }


    @Override
    public void createColleagues() {
        searchConcertButton = new ColleagueButton("Show");
        searchUserButton = new ColleagueButton("Show");
        buttonLogout = new ColleagueButton("Logout");

        searchConcertButton.setMediator(this);
        searchUserButton.setMediator(this);
        buttonLogout.setMediator(this);

        searchConcertButton.addActionListener(searchConcertButton);
        searchUserButton.addActionListener(searchUserButton);
        buttonLogout.addActionListener(buttonLogout);

    }

    @Override
    public void colleagueChanged() {
        if (this.searchConcertButton.nowAction()) {
            ArrayList<ArrayList<String>> resultUsersList = UserReader.getSpecificConcerts(searchConcertName);
            ArrayList<String> resultUser = new ArrayList<>();
            if (resultUsersList == null) {
                JOptionPane.showMessageDialog(this, "Select Concert Again", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                resultTextArea.setText("");
                for (int i = 0; i < resultUsersList.size(); i++) {
                    resultUser.add((resultUsersList.get(i)).get(0));
                    resultTextArea.append(resultUser.get(i));
                    resultTextArea.append("\n");
                }
                //System.out.println(resultUser); // User Name
                //JOptionPane.showMessageDialog(this,resultUser,"Result",JOptionPane.INFORMATION_MESSAGE);

            }
        } else if (this.searchUserButton.nowAction()) {
            ArrayList<ArrayList<String>> resultConcertsList = UserReader.getUserConcerts(searchUserName);
            ArrayList<String> resultConcert = new ArrayList<>();
            if (resultConcertsList.size() == 0) {
                JOptionPane.showMessageDialog(this, "Select User Again", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                resultTextArea.setText("");
                for (int i = 0; i < resultConcertsList.size(); i++) {
                    resultConcert.add((resultConcertsList.get(i)).get(1));
                    resultTextArea.append(resultConcert.get(i));
                    resultTextArea.append("\n");
                }
                //System.out.println(resultConcert); // Concert Name
                //JOptionPane.showMessageDialog(this,resultConcert,"Result",JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (this.buttonLogout.nowAction()) {
            this.mainFrame.unsetUserReader();
            this.mainFrame.setNextPanelName(MainFrame.LoginPanelName);
        }

        this.mainFrame.colleagueChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }


    class MyListSelect implements ActionListener, EventListener {
        /*@Override
        public void itemStateChanged(ItemEvent e) {
        }
        */

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == comboConcerts) {
                searchConcertName = (String) comboConcerts.getSelectedItem();
                System.out.println("Now Concert Name:" + searchConcertName);
            } else if (e.getSource() == comboUsers) {
                searchUserName = (String) comboUsers.getSelectedItem();
                System.out.println("Now User Name:" + searchUserName);
            }
        }
    }
}
