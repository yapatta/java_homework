package controller;

import model.UserReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyConcertsPanel extends JPanel implements ActionListener, Mediator {
    private MainFrame mainFrame;
    private ArrayList<ArrayList<String>> concerts = new ArrayList<>();

    public MyConcertsPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        // FIXME: should add some panels
        this.createColleagues();
    }

    private void setConcerts(ArrayList<ArrayList<String>> concerts) {
        this.concerts = concerts;
    }

    public void initialize(String userName) {
        UserReader ur = new UserReader(userName);
        this.setConcerts(ur.getMyConcerts());

        // FIXME: change the view
    }

    public void createColleagues() {

    }

    public void colleagueChanged() {

    }

    public void actionPerformed(ActionEvent e) {

    }
}
