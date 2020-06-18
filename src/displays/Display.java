package displays;

import utils.SaveFile;

import javax.swing.*;
import java.awt.*;

public abstract class Display extends JPanel {
    public SaveFile saveFile;
    public Display(){
        super(new BorderLayout());
    }

    public abstract void reload();
}
