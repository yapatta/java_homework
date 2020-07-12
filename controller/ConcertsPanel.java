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
import java.util.Vector;
@SuppressWarnings("serial")
public class ConcertsPanel extends JPanel implements ActionListener, Mediator {
    public static int ALL_PANEL_WIDTH = 900;
    public static int PANEL_WIDTH = 800;
    public static int PANEL_HEIGHT = 1000;

    private ArrayList<ArrayList<String>> concerts = new ArrayList<>();
    private ColleagueButton buttonRegister;
    private ColleagueButton buttonLogout;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private MainFrame mainFrame;

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

        // JPanel for category
        JPanel categoryPanel = new JPanel();
        categoryPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 500));

        categoryPanel.add(new ConcertColumnLabel("Register"));
        categoryPanel.add(new ConcertColumnLabel("Title"));
        categoryPanel.add(new ConcertColumnLabel("Genre"));
        categoryPanel.add(new ConcertColumnLabel("Day"));
        categoryPanel.add(new ConcertColumnLabel("Place"));
        categoryPanel.add(new ConcertColumnLabel("Fee"));
        categoryPanel.add(new ConcertColumnLabel("Capacity"));

        //Concert List
        /*
        listModel = new DefaultListModel<String>();
        var allConcerts = UserReader.getAllConcerts();
        for(var concert : allConcerts){
            String detail = "";
            for(var str : concert){
                detail += str + " ";
            }
            listModel.addElement(detail);
        }
        list = new JList<String>(listModel);
        list.setVisibleRowCount(10);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPanel = new JScrollPane(list);
        scrollPanel.createVerticalScrollBar();
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
        */

        //Concert Table
        String[] columnNames = {"Register","Title","Genre","Day","Place","Fee","Capacity"};
        /*Test Data*/
        Object[][] data = new Object[][]{{false, "Magicl Mirai 2020", "Vocaloid", "2020/08/10", "Makuhari Messe", "5600", "100"}};
        JTable table = new JTable();
        DefaultTableModel modeltable = new DefaultTableModel(columnNames,0){

            public Class<?> getColumnClass(int columnIndex){
                switch(columnIndex){
                    case 0: return Boolean.class;
                    default:
                        return super.getColumnClass(columnIndex);
                }
            }
        };
        /*add All data for each row*/
        modeltable.addRow(data[0]); //Test data push
        /*format string in center*/
        table.setModel(modeltable);
        DefaultTableCellRenderer tableCellRenderer= new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=1;i<7;i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            col.setCellRenderer(tableCellRenderer);
        }

        /*set scroll*/
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setPreferredSize(new Dimension(PANEL_WIDTH,500));



        //register and logoutButton
        JPanel clickPanel = new JPanel();

        clickPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));

        clickPanel.setLayout(new GridLayout(1, 1));
        clickPanel.add(buttonRegister);
        clickPanel.add(buttonLogout);


        this.add(labelPanel);
        add(GUILibrary.getHr(800, 0));
        //this.add(categoryPanel, BorderLayout.CENTER);
        //add(GUILibrary.getHr(800, 0));
        //this.add(scrollPanel);
        this.add(scrollTable);
        add(GUILibrary.getHr(800, 0));
        this.add(clickPanel);
    }

    private void setConcerts(ArrayList<ArrayList<String>> concerts) {
        this.concerts = concerts;
    }

    /*if change view then start initialize*/
    public void initialize() {
        this.setConcerts(UserReader.getAllConcerts());
        // FIXME: add view initializer later
    }

    /*set gui*/
    public void createColleagues() {
        buttonRegister = new ColleagueButton("Register");
        buttonLogout = new ColleagueButton("Logout");

        buttonRegister.setMediator(this);
        buttonLogout.setMediator(this);

        buttonRegister.addActionListener(buttonRegister);
        buttonLogout.addActionListener(buttonLogout);
    }

    /*change status*/
    public void colleagueChanged() {

    }

    /*Button action*/
    public void actionPerformed(ActionEvent e) {

    }
}
