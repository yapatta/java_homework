package controller;

import javax.swing.*;
import java.util.ArrayList;

public class ConcertsPanel extends JPanel /* implements ActionListener, Mediator */ {
    private ArrayList<ArrayList<String>> concerts = new ArrayList<>();

    public ConcertsPanel(MainFrame mf, String title) {

    }

    public void setConcerts(ArrayList<ArrayList<String>> concerts) {
        this.concerts = concerts;
    }

    public void initialize() {

    }
}
