package lib;

import javax.swing.*;
import java.awt.*;

public class GUILibrary {
    public static JSeparator getHr(int width, int height) {
        JSeparator sp = new JSeparator(JSeparator.HORIZONTAL);
        sp.setPreferredSize(new Dimension(width, height));
        return sp;
    }
}
