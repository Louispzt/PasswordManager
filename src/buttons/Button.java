package buttons;

import displays.DisplayEditor;
import displays.DisplayManager;
import frames.Frame;
import src.Main;
import utils.RSA;
import utils.SaveFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Button extends JButton {
    public ButtonEnum buttonEnum;
    private static Frame displayFrame;

    public Button(String text, ButtonEnum buttonEnum) {
        super(text);
        this.buttonEnum = buttonEnum;
    }

    @Override
    protected void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(event);
        switch (buttonEnum){
            case QUIT:
                System.exit(0);
                break;
            case OPENEDIT:
                try {
                    if (displayFrame == null || !displayFrame.isDisplayable())
                        displayFrame = new Frame(new DisplayEditor(Main.display.saveFile), 500, 400, "Edit Window", JFrame.DISPOSE_ON_CLOSE);
                    else{
                        displayFrame.requestFocus();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CLOSE:
                if (displayFrame != null) {
                    displayFrame.dispose();
                    Main.display.reload();
                }
        }
    }
}
