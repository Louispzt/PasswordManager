package Displays;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    public Frame(Display display) {
        this.setTitle("Bouton");
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.getContentPane().add(display);

        this.setVisible(true);
    }
}
