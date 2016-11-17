package Models.EnemyShips;

import Auxillary.Vector2D;
import Controllers.Action;
import Models.EnemyShip;
import Models.GameObject;
import Models.Particle;
import View.SoundManager;

import java.awt.*;
import java.util.*;

import static Auxillary.Constants.DT;
import static Auxillary.Constants.FRAME_HEIGHT;
import static Auxillary.Constants.FRAME_WIDTH;

/**
 * Created by gcordi on 18/03/2015.
 * The Drifter class extends the EnemyShip class and doesn't alter much.
 *
 */
public class Drifter extends EnemyShip{

    //An initializer that calls its parents initializer.
    public Drifter(){
        super();
    }

    /*
        This function overrides the parent set guts and is necessary for assigning values to the EnemyShip Unique to Drifter.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 3, denoting it to be a heavy ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration.
        RADIUS is the size of the Ships hit box.
        COLOR is the ships colour. All enemy ships are magenta.
     */
    @Override
    protected void SetGuts() {
        this.XP = new int[] {0,5,5,10,15,20,20,15,5,0,-5,-15,-20,-20,-15,-10,-5,-5};
        this.YP = new int[] {0,-5,-15,-20,-20,-15,10,20,20,15,20,20,10,-15,-20,-20,-15,-5};
        this.XPTHRUST = new int[] {0,5,15,5,0,-5,-15,-5};
        this.YPTHRUST = new int[] {20,25,25,35,30,35,25,25};
        this.SHOT_DELAY = 25;
        this.SIZE = 3;
        this.lives = 5;
        this.MAG_ACC = 80;
        this.RADIUS = 25;
        COLOR = Color.magenta;
    }
}
