package Models.EnemyShips;

import Models.Bullet;
import Models.EnemyShip;

import java.awt.*;

/**
 * Created by gcordi on 18/03/2015.
 * The Sniper class extends the EnemyShip class and changes the bullet velocity.
 *
 */
public class Sniper extends EnemyShip{

    //An initializer that calls its parents initializer.
    public Sniper(){
        super();
    }


    /*
        This function overrides the parent set guts and is necessary for assigning values to the EnemyShip Unique to Sniper.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 1, denoting it to be a light ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration. This is set to 0 as the sniper can not move, however permanently
            Thrusting means the XPTHRUST and YPTHRUST that make up part of the ship stay active.
        RADIUS is the size of the Ships hit box.
        COLOR is the ships colour. All enemy ships are magenta.
     */
    @Override
    protected void SetGuts() {
        this.XP = new int[] {20,5,5,10,20,20,-20,-5,-5,-10,-20,-20,-5,-5,0,5,5};
        this.YP = new int[] {5,20,-5,-15,-5,5,5,20,-5,-15,-5,5,5,-5,-20,-5,5};
        this.XPTHRUST = new int[] {0,-5,0,5};
        this.YPTHRUST = new int[] {5,0,-30,0};
        this.SHOT_DELAY = 100;
        this.SIZE = 3;
        this.lives = 5;
        this.MAG_ACC = 0;
        this.RADIUS = 20;
        this.SIZE = 1;
        COLOR = Color.magenta;
    }

    //This overrides the EnemyShip.mkBullet() and does a similar function but with a much higher velocity.
    @Override
    protected Bullet mkBullet() {
        Bullet temp = super.mkBullet();
        temp.v.add(d,1000);
        return temp;
    }
}
