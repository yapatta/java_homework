package controller;

import components.ColleagueButton;
import components.ColleagueTextField;
import model.UserReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConcertsPanel extends JPanel implements ActionListener, Mediator {
    private ArrayList<ArrayList<String>> concerts = new ArrayList<>();
    //private ColleagueTextField textLabel;
    private ColleagueButton buttonRegister;
    private ColleagueButton buttonLogout;
    private MainFrame mainFrame;


    public ConcertsPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(700,900);

        this.setLayout(new GridLayout(4,0));
        //this.setLayout(new FlowLayout());

        this.createColleagues();

        // JPanel for Main Message
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1,1));
        labelPanel.setPreferredSize(new Dimension(300,100));
        JLabel subject = new JLabel(title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase());
        subject.setHorizontalAlignment(JLabel.CENTER);
        subject.setFont(new Font("Arial", Font.PLAIN,30));
        labelPanel.add(subject);

        // JPanel for category
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(1,7));
        categoryPanel.add(new Label("Register"));
        categoryPanel.add(new Label("Title"));
        categoryPanel.add(new Label("Genre"));
        categoryPanel.add(new Label("Day"));
        categoryPanel.add(new Label("Artist"));
        categoryPanel.add(new Label("Fee"));
        categoryPanel.add(new Label("Capacity"));
        //labelPanel.setPreferredSize(new Dimension(300,150));




        //register and logoutButton
        JPanel clickPanel = new JPanel();

        clickPanel.setLayout(new GridLayout(1,0));
        clickPanel.setPreferredSize(new Dimension(100,50));
        clickPanel.add(buttonRegister);
        clickPanel.add(Box.createRigidArea(new Dimension(100,100)));
        //clickPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        clickPanel.add(buttonLogout);


        this.add(labelPanel);
        this.add(categoryPanel);
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
    public void createColleagues(){
        buttonRegister = new ColleagueButton("Register");
        buttonLogout = new ColleagueButton("Logout");

        buttonRegister.setMediator(this);
        buttonLogout.setMediator(this);

        buttonRegister.addActionListener(buttonRegister);
        buttonLogout.addActionListener(buttonLogout);
    }

    /*change status*/
    public void colleagueChanged(){

    }

    /*Button action*/
    public void actionPerformed(ActionEvent e){

    }
}
