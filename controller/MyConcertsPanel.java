package controller;

import components.ColleagueButton;
import components.ConcertColumnLabel;
import lib.GUILibrary;
import model.UserReader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyConcertsPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 900;
    public static int PANEL_WIDTH = 800;
    public static int PANEL_HEIGHT = 1000;
    public static String[] COLUMN_NAMES = {"Delete","Title","Genre","Day","Place","Fee","Capacity"};

    private ColleagueButton buttonDelete;
    private ColleagueButton buttonAllConcerts;
    private ColleagueButton buttonLogout;
    private JTable myConcertsTable;
    private JScrollPane scrolleTable;
    private final MainFrame mainFrame;

    public MyConcertsPanel(MainFrame mf, String title) {
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

        // JPanel for category
        /*
        JPanel categoryPanel = new JPanel();

        categoryPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));

        categoryPanel.add(new ConcertColumnLabel("Register"));
        categoryPanel.add(new ConcertColumnLabel("Title"));
        categoryPanel.add(new ConcertColumnLabel("Genre"));
        categoryPanel.add(new ConcertColumnLabel("Day"));
        categoryPanel.add(new ConcertColumnLabel("Artist"));
        categoryPanel.add(new ConcertColumnLabel("Fee"));
        categoryPanel.add(new ConcertColumnLabel("Capacity"));
        */

        //delete and logoutButton
        JPanel clickPanel = new JPanel();

        clickPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));

        clickPanel.setLayout(new GridLayout(1, 1));
        clickPanel.add(buttonDelete);
        clickPanel.add(buttonAllConcerts);
        clickPanel.add(buttonLogout);


        this.add(labelPanel);
        add(GUILibrary.getHr(800, 0));
        //this.add(categoryPanel, BorderLayout.CENTER);
        this.add(scrolleTable);
        add(GUILibrary.getHr(800, 0));
        this.add(clickPanel);
    }

    public void initialize() {
        var allConcerts = UserReader.getAllConcerts();
        var myConcerts = this.mainFrame.getUserReader().getMyConcerts();

        // FIXME: change the view
        this.setMyConcertsTable();
    }

    public void createColleagues() {
        buttonDelete = new ColleagueButton("Delete");
        buttonAllConcerts = new ColleagueButton("Show All Concerts");
        buttonLogout = new ColleagueButton("Logout");

        myConcertsTable = new JTable();
        scrolleTable = new JScrollPane(myConcertsTable);

        buttonDelete.setMediator(this);
        buttonAllConcerts.setMediator(this);
        buttonLogout.setMediator(this);

        buttonDelete.addActionListener(buttonDelete);
        buttonAllConcerts.addActionListener(buttonAllConcerts);
        buttonLogout.addActionListener(buttonLogout);
    }

    public void colleagueChanged() {
        if (this.buttonDelete.nowAction()) {
            // FIXME: should add checked concerts to myConcerts
            //ArrayList<Integer> deletedConcertsIndexList = new ArrayList<>();

            /*
            for (var i : deletedConcertsIndexList) {
                this.mainFrame.getUserReader().deleteMyConcert(i);
            }
             */
            /*updateMyConcerts*/
            this.updateCheckedConcerts();

            this.mainFrame.setNextPanelName(MainFrame.MyConcertsPanelName);

        } else if (this.buttonAllConcerts.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.ConcertsPanelName);
        } else if (this.buttonLogout.nowAction()) {
            this.mainFrame.setNextPanelName(MainFrame.LoginPanelName);
        }

        this.mainFrame.colleagueChanged();
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void setMyConcertsTable(){
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 1; i < myConcertsTable.getColumnModel().getColumnCount(); i++){
            TableColumn col = myConcertsTable.getColumnModel().getColumn(i);
            col.setCellRenderer(tableCellRenderer);
        }

        scrolleTable.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT/2));

        //MyConcerts Table
        Object[][] showMyConcertsOgject = this.mainFrame.getUserReader().getMyConcertsAsObject();

        DefaultTableModel modeltable = new DefaultTableModel(COLUMN_NAMES,0){
            public Class<?> getColumnClass(int columnIndex){
                if(columnIndex == 0){
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        for(var myConcertObject : showMyConcertsOgject){
            modeltable.addRow(myConcertObject);
            this.myConcertsTable.setModel(modeltable);
        }
    }

    public void updateCheckedConcerts() {
        ArrayList<ArrayList<String>> addedConcerts = new ArrayList<>();
        for (int i = 0; i < this.myConcertsTable.getRowCount(); i++) {
            if (Boolean.parseBoolean(this.myConcertsTable.getValueAt(i, 0).toString())) {
                ArrayList<String> addedConcert = new ArrayList<>();
                for (int j = 0; j < this.myConcertsTable.getColumnCount(); j++) {
                    addedConcert.add(this.myConcertsTable.getValueAt(i, j).toString());
                }
                addedConcerts.add(addedConcert);
            }
        }

        this.mainFrame.getUserReader().writeMyConcerts(addedConcerts);
    }


}
