package src;

import Displays.DisplayPassword;
import Displays.Frame;

public class Main {
    public static Frame frame;
    public static void main(String[] args) {
        frame = new Frame(new DisplayPassword());
    }
}
