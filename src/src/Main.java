package src;

import displays.Display;
import displays.DisplayPassword;
import frames.Frame;

import javax.swing.*;

public class Main {
    public static Frame frame;
    public static Display display;
    public static void main(String[] args) {
        display = new DisplayPassword();
        frame = new Frame(display, 400, 300, "Password Manager", JFrame.EXIT_ON_CLOSE);
    }
}
