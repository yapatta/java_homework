package controller;

import model.UserReader;

import javax.swing.*;
import java.util.ArrayList;

public class ConcertsPanel extends JPanel /* implements ActionListener, Mediator */ {
    private MainFrame mainFrame;
    private ArrayList<ArrayList<String>> concerts = new ArrayList<>();

    public ConcertsPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);
    }

    private void setConcerts(ArrayList<ArrayList<String>> concerts) {
        this.concerts = concerts;
    }

    public void initialize() {
        this.setConcerts(UserReader.getAllConcerts());
    }
}
