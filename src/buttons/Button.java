package Buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class QuitButton extends JButton {

    public QuitButton(String text) {
        super(text);
    }

    @Override
    protected void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(event);
        System.exit(0);
    }
}
