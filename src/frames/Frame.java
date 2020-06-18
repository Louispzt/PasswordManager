package frames;

import displays.Display;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(Display display, int width, int heigth, String title, int exitoption) {
        this.setTitle(title);
        this.setSize(width, heigth);
        this.setDefaultCloseOperation(exitoption);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.getContentPane().add(display);

        this.pack();
        this.setVisible(true);
    }
}
