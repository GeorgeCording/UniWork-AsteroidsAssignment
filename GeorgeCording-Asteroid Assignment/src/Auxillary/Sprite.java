package Auxillary;

import static Auxillary.Constants.*;

import java.awt.*;

/**
 * Created by gcordi on 10/03/2015.
 * Only of use when in Image Mode.
 * Image mode is a mode put in place to demonstrate the ability to import and manipulate Images.
 * A requirement of the assignment.
 */
public class Sprite {

    public static Image ASTEROID_IMAGE, BG_IMAGE;
    static {
        try {
            ASTEROID_IMAGE = imageManager.loadImage("asteroid");
            BG_IMAGE = imageManager.loadImage("milkyway1");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
