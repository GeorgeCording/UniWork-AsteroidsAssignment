package Models.EnemyShips;

import Models.EnemyShip;

import java.awt.*;

/**
 * Created by gcordi on 18/03/2015.
 * The Seeker class extends the EnemyShip class and doesn't alter much.
 *
 */
public class Seeker extends EnemyShip {

    //An initializer that calls its parents initializer.
    public Seeker(){
        super();
    }


    /*
        This function overrides the parent set guts and is necessary for assigning values to the EnemyShip Unique to Seeker.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 1, denoting it to be a light ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration.
        RADIUS is the size of the Ships hit box.
        COLOR is the ships colour. All enemy ships are magenta.
     */
    @Override
    protected void SetGuts() {
        this.XP = new int[] {-30,-10,0,10,30,20,0,-20};
        this.YP = new int[] {-20,0,-10,0,-20,20,10,20};
        this.XPTHRUST = new int[] {0,10,0,-10};
        this.YPTHRUST = new int[] {15,20,50,20};
        this.SHOT_DELAY = 25;
        this.SIZE = 3;
        this.lives = 1;
        this.MAG_ACC = 50;
        this.RADIUS = 25;
        this.SIZE = 1;
        COLOR = Color.magenta;
    }
}
