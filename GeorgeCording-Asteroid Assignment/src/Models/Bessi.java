package Models;

import Controllers.Controller;

/**
 * Created by gcordi on 11/03/2015.
 * The Bessi class is a type of ship that extends PlayerShip.
 * This ship is a normal heavy class ship.
 * This ship has a fairly fast fire rate and performs as an example of a heavy class ship.
 */
public class Bessi extends PlayerShip{
    public Bessi(Controller controller) {
        super(controller);
    }

    /*
        This function overrides the parent SetGuts and is for assigning values to the PlayerShip that are unique to Bessi.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 3, denoting it to be a heavy ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration.
        RADIUS is the size of the Ships hit box.
     */

    @Override
    protected void setGuts() {
        this.XP = new int[] {0,20,10,20,10,-10,-20,-10,-20};
        this.YP = new int[] {-25,-5,5,15,25,25,15,5,-5};
        this.XPTHRUST = new int[] {-20,-10,10,20,20,10,-10,-20};
        this.YPTHRUST = new int[] {25,35,35,25,45,35,35,45};
        this.lives = 5;
        this.SHOT_DELAY = 10;
        this.MAG_ACC = 150;
        this.RADIUS = 20;
        this.SIZE = 3;
    }
}
