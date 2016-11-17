package Models;

import Controllers.Controller;

/**
 * Created by gcordi on 11/03/2015.
 * The FishTail class is a class that extends PlayerShip.
 * The FishTail class ship is an example of a light ship and is fairly normal in design.
 * It has a fast fire rate and is the default type of ship.
 */
public class FishTail  extends PlayerShip{
    public FishTail(Controller controller){
        super(controller);
    }

    /*
        This function overrides the parent SetGuts and is for assigning values to the PlayerShip that are unique to FishTail.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 1, denoting it to be a light ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration.
        RADIUS is the size of the Ships hit box.
     */

    @Override
    protected void setGuts(){     //Default one (FishTail)
        this.XP = new int[] {0,20,15,10,5,-5,-10,-15,-20};
        this.YP = new int[] {-20,15,20,15,25,25,15,20,15};
        this.XPTHRUST = new int[] {-5,5,10,0,-10};
        this.YPTHRUST = new int[] {30,30,40,35,40};
        this.lives = 4;
        this.SHOT_DELAY = 10;
        this.MAG_ACC = 400;
        this.RADIUS = 20;
        this.SIZE = 1;
    }
}
