import controller.MainFrame;

import javax.swing.*;

class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        // Position Center
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
