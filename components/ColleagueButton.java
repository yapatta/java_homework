package components;

import controller.Mediator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColleagueButton extends JButton implements ActionListener, Colleague {
    private Mediator mediator;
    private boolean action = false;

    public ColleagueButton(String caption) {
        super(caption);
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void setColleagueEnabled(boolean enabled) {
        setEnabled(enabled);
    }

    public void actionPerformed(ActionEvent e) {
        this.setAction();
        mediator.colleagueChanged();
        this.unsetAction();
    }

    public void setAction() {
        this.action = true;
    }

    public void unsetAction() {
        this.action = false;
    }

    public boolean nowAction() {
        return this.action;
    }
}
