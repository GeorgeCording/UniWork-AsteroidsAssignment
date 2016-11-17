package Auxillary;

import javax.swing.*;
import java.awt.*;

public class JEasyFrame extends JFrame{
    public Component comp;

    /*
        This is a standard initialisation of a JFrame to simplify the Game class.
        A Component added to the centre of the content pane is stored for easy access.
     */
    public JEasyFrame(Component comp, String title) {
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}
