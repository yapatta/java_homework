package controller;

import components.ColleagueButton;
import lib.GUILibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ConcertsPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 900;
    public static int PANEL_WIDTH = 800;
    public static int PANEL_HEIGHT = 1000;
    public static String[] COLUMN_NAMES = {"Register", "Title", "Genre", "Day", "Place", "Fee", "Capacity"};

    private ColleagueButton buttonRegister;
    private ColleagueButton buttonMyConcerts;
    private ColleagueButton buttonLogout;
    private JTable concertsTable;
    private JScrollPane scrollTable;
    private final MainFrame mainFrame;

    public ConcertsPanel(MainFrame mf, String title) {
        this.mainFrame = mf;
        this.setName(title);

        this.setSize(ALL_PANEL_WIDTH, PANEL_HEIGHT);

        this.setLayout(new FlowLayout());

        this.createColleagues();

        // JPanel for Main Message
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 1));
        labelPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
        JLabel subject = new JLabel(title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase());

        subject.setHorizontalAlignment(JLabel.CENTER);
        subject.setFont(new Font("Arial", Font.PLAIN, 30));
        labelPanel.add(subject);

        //register and logoutButton
        JPanel clickPanel = new JPanel();

        clickPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));

        clickPanel.setLayout(new GridLayout(1, 1));
        clickPanel.add(buttonRegister);
        clickPanel.add(buttonMyConcerts);
        clickPanel.add(buttonLogout);


        this.add(labelPanel);
        add(GUILibrary.getHr(800, 0));
        this.add(scrollTable);
        add(GUILibrary.getHr(800, 0));
        this.add(clickPanel);
    }

    /*if change view then start initialize*/
    public void initialize() {
        this.setConcertsTable();
    }

    /*set gui*/
    public void createColleagues() {
        buttonRegister = new ColleagueButton("Update");
        buttonMyConcerts = new ColleagueButton("Show My Concerts");
        buttonLogout = new ColleagueButton("Logout");

        concertsTable = new JTable();
        scrollTable = new JScrollPane(concertsTable);

        buttonRegister.setMediator(this);
        buttonMyConcerts.setMediator(this);
        buttonLogout.setMediator(this);

        buttonRegister.addActionListener(buttonRegister);
        buttonMyConcerts.addActionListener(buttonMyConcerts);
        buttonLogout.addActionListener(buttonLogout);
    }

    /*change status*/
    public void colleagueChanged() {
        if (this.buttonRegister.nowAction()) {
            this.updateCheckedConcerts();

            this.mainFrame.setNextPanelName(MainFrame.MyConcertsPanelName);
        } else if (this.buttonMyConcerts.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.MyConcertsPanelName);
        } else if (this.buttonLogout.nowAction()) {
            this.mainFrame.unsetUserReader();
            this.mainFrame.setNextPanelName(MainFrame.LoginPanelName);
        }

        this.mainFrame.colleagueChanged();
    }

    /*Button action*/
    public void actionPerformed(ActionEvent e) {

    }

    public void setConcertsTable() {
        // format of table
        /*add All data for each row*/
        /*format string in center*/
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 1; i < concertsTable.getColumnModel().getColumnCount(); i++) {
            TableColumn col = concertsTable.getColumnModel().getColumn(i);
            col.setCellRenderer(tableCellRenderer);
        }

        /*set scroll*/
        scrollTable.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT/2));
        //Concert Table
        Object[][] showConcertsObject = this.mainFrame.getUserReader().getShowConcertsAsObject();

        DefaultTableModel modeltable = new DefaultTableModel(COLUMN_NAMES, 0) {
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        for (var concertObject : showConcertsObject) {
            modeltable.addRow(concertObject);
            this.concertsTable.setModel(modeltable);
        }
    }

    public void updateCheckedConcerts() {
        ArrayList<ArrayList<String>> addedConcerts = new ArrayList<>();
        for (int i = 0; i < this.concertsTable.getRowCount(); i++) {
            if (Boolean.parseBoolean(this.concertsTable.getValueAt(i, 0).toString())) {
                ArrayList<String> addedConcert = new ArrayList<>();
                for (int j = 0; j < this.concertsTable.getColumnCount(); j++) {
                    addedConcert.add(this.concertsTable.getValueAt(i, j).toString());
                }
                addedConcerts.add(addedConcert);
            }
        }

        this.mainFrame.getUserReader().writeMyConcerts(addedConcerts); }
}
