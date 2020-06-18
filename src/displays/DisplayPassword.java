package displays;

import buttons.Button;
import buttons.ButtonEnum;
import frames.Frame;
import src.Main;
import utils.RSA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class DisplayPassword extends Display{
    private final JPasswordField passwordField;
    private final JProgressBar progressBar;
    private final JButton next_bt;

    public DisplayPassword() {
        next_bt = new JButton("Next");
        next_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (progressBar.getValue() >= 50){
                    Main.frame.dispose();
                    try {
                        Main.frame = new Frame(Main.display = new DisplayManager(new RSA(String.valueOf(passwordField.getPassword()), 512)), 500, 400, "Password Manager", JFrame.EXIT_ON_CLOSE);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        passwordField = new JPasswordField(20);
        passwordField.addKeyListener(new KeyListener() {
            private static final int MAX_LENGTH = 22;
            private final Color[] PB_COLORS = {Color.red, Color.yellow, Color.green, Color.green};
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    next_bt.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                char[] pw = passwordField.getPassword();
                int value = (pw.length * 100) / MAX_LENGTH;
                value = Math.min(value, 100);
                progressBar.setValue(value);

                int colorIndex = (PB_COLORS.length * value) / 100;
                colorIndex = Math.min(colorIndex, PB_COLORS.length - 1);
                progressBar.setForeground(PB_COLORS[colorIndex]);
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0); //margin
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 3;
        add(new JLabel("<html><h1>Password Manager</h1></html>"), gbc);

        gbc.insets = new Insets(5, 5, 5, 5); //5px margin
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Master Password:"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx=1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("strength:"), gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        progressBar = new JProgressBar();
        add(progressBar, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 5, 5, 5); //margin
        add(next_bt, gbc);
        
        gbc.gridy++;
        gbc.gridx=0;
        gbc.insets = new Insets(5, 5, 5, 5); //margin
        add(new Button("Quit", ButtonEnum.QUIT), gbc);
    }

    @Override
    public void reload() {

    }

}
