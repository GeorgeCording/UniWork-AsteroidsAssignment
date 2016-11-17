package Models;

import Auxillary.Vector2D;
import Controllers.Controller;
import View.SoundManager;

/**
 * Created by gcordi on 11/03/2015.
 *  The Archie class is a type of ship that extends PlayerShip.
 *  This ship is a Heavy sized ship yet is the smallest sized ship in the game.
 *  This ship Has a unique type of bullet that has a very short range.
 *  This ship has a very fast fire rate.
 */
public class Archie extends PlayerShip{
    public Archie(Controller controller){
        super(controller);
    }

    /*
        This function overrides the parent SetGuts and is for assigning values to the PlayerShip that are unique to Archie.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 3, denoting it to be a heavy ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration.
        RADIUS is the size of the Ships hit box.
     */

    @Override
    protected void setGuts() {
        this.XP = new int[] {0,5,5,10,10,15,5,10,5,-5,-10,-5,-15,-10,-10,-5,-5};
        this.YP = new int[] {-15,-10,-5,-5,-10,-5,5,10,15,15,10,5,-5,-10,-5,-5,-10};
        this.XPTHRUST = new int[] {-5,0,5,15,15,10,15,5,-5,-15,-10,-15,-15,};
        this.YPTHRUST = new int[] {20,30,20,15,0,5,15,20,20,15,5,0,15,};
        this.lives = 5;
        this.SHOT_DELAY = 0.0;
        this.MAG_ACC = 100;
        this.RADIUS = 10;
        this.SIZE = 3;
    }

    /*
        The Archie class ship uses unique shots that have a very short lifespan, these are a class called ArchiesBullets
     */
    @Override
    protected Bullet mkBullet() {
        ArchiesBullet bullet = new ArchiesBullet(new Vector2D(s), new Vector2D(v));
        bullet.s.add(d,RADIUS/getSize());
        bullet.v.add(d, bullet.BULLET_SPEED + MAG_ACC*(getAcceleration(SIZE,lives)+1.5));
        bullet.update(null);
        SoundManager.fire();
        return bullet;
    }
}
