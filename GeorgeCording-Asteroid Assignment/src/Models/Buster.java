package Models;

import Controllers.Controller;

/**
 * Created by gcordi on 11/03/2015.
 * The Buster class is a type of ship that extends PlayerShip.
 * This ship is a normal medium class ship.
 * This ship has a fairly fast fire rate and performs as an example of a medium class ship.
 */
public class Buster extends PlayerShip {

    public Buster(Controller controller){
        super(controller);
    }

    /*
        This function overrides the parent SetGuts and is for assigning values to the PlayerShip that are unique to Buster.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 2, denoting it to be a medium ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration.
        RADIUS is the size of the Ships hit box.
     */

    @Override
    protected void setGuts() {
        this.XP = new int[] {0,10,10,20,20,10,-10,-20,-20,-10,-10,};
        this.YP = new int[] {-40,-30,-20,-10,10,20,20,10,-10,-20,-30,};
        this.XPTHRUST = new int[] {-20,-10,10,20,20,10,0,-10,-20,};
        this.YPTHRUST = new int[] {20,30,30,20,40,30,50,30,40,};
        this.lives = 4;
        this.SHOT_DELAY = 10;
        this.MAG_ACC = 400;
        this.RADIUS = 20;
        this.SIZE = 2;
    }
}
