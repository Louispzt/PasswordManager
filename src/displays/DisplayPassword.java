package Displays;

import Buttons.QuitButton;
import src.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayPassword extends Display{
    private JPasswordField passwordField;
    private JProgressBar progressBar;
    private JButton next_bt;
    private JButton quit_bt;

    public DisplayPassword() {
        passwordField = new JPasswordField(20);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0); //margin
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        add(new JLabel("<html><h1>Password Manager</h1></html>"), gbc);

        gbc.insets = new Insets(5, 5, 5, 5); //5px margin
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Master Password:"), gbc);
        gbc.gridx++;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 5, 5, 5); //margin
        add(new JButton("Next"), gbc);
        
        gbc.gridy++;
        gbc.gridx=0;
        gbc.insets = new Insets(5, 5, 5, 5); //margin
        add(new QuitButton("Quit"), gbc);
    }

}
