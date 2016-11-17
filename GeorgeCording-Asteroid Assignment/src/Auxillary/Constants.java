package Auxillary;

import View.ImageManager;

import java.awt.*;

/**
 * Created by gcordi on 11/02/2015
 * This class defines variables that remain constant throughout the program
 * These include: Screen Height and width, calculated at runtime,
 *      A dimension called Frame Size that stores the Height and Width for easy access,
 *      The time DELAY in milliseconds between each frame, and one stored in seconds,
 *      A constant reference to an ImageManager (Class in View folder).
 */
public class Constants {

    public static final int FRAME_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.95);
    public static final int FRAME_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.95);
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    // sleep time between two frames in milliseconds
    public static final int DELAY = 20;
    // sleep time between two frames in seconds
    public static final double DT = DELAY / 1000.0;
    //public static final Sprite sprite = new Sprite();
    public static ImageManager imageManager = new ImageManager();
}