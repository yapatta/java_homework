package components;

import controller.ConcertsPanel;

import javax.swing.*;
import java.awt.*;

public class ConcertColumnLabel extends JLabel {
    public ConcertColumnLabel(String text) {
        super(text);
        this.setPreferredSize(new Dimension(ConcertsPanel.PANEL_WIDTH / 8, 50));
        this.setHorizontalAlignment(JLabel.CENTER);
    }
}
