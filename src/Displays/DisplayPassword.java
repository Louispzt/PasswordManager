package Displays;

import Buttons.QuitButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayPassword extends Display {
    private JPasswordField passwordField;
    private JProgressBar progressBar;
    private JButton next_bt;
    private JButton quit_bt;

    public DisplayPassword(){
        passwordField = new JPasswordField();
        progressBar = new JProgressBar();
        next_bt = new JButton();
        quit_bt = new QuitButton("Quit");

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        add(new JLabel("<html><h1><strong><i></i></strong></h1><hr></html>"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.add(new JButton("Start"), gbc);
        buttons.add(new JButton("Show scores"), gbc);
        buttons.add(new JButton("Help"), gbc);
        buttons.add(new JButton("Exit"), gbc);

        gbc.weighty = 1;
        add(buttons, gbc);
    }

}
