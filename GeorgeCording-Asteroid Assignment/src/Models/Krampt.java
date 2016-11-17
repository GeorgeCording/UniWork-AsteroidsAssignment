package Models;

import Auxillary.Vector2D;
import Controllers.Action;
import Controllers.Controller;
import View.SoundManager;

import java.awt.*;
import java.util.List;

import static Auxillary.Constants.DT;

/**
 * Created by gcordi on 11/03/2015.
 * The Krampt class is a class that extends PlayerShip.
 * This is medium sized ship that Has a slow fire rate, However, It fires multiple projectiles when it fires.
 *  This creates a shotgun effect, To emphasize this The ship is rotated as a recoil effect.
 */
public class Krampt extends PlayerShip{
    public Krampt(Controller controller){
        super(controller);
    }

    /*
        This function overrides the parent SetGuts and is for assigning values to the PlayerShip that are unique to Krampt.
        XP, YP, XPTHRUST and YPTHRUST are coordinates to draw the two polygons tht make up the ship.
        SHOT_DELAY is the time between shots.
        The size is a number from 1 to 3, In this case 2, denoting it to be a medium ship. (See AbstractShip.getAcceleration).
        Lives is used for calculating Acceleration and Turn speed, since one shot will kill an EnemyShip this can be done.
        MAG_ACC denotes the magnitude of acceleration.
        RADIUS is the size of the Ships hit box.
     */

    @Override
    protected void setGuts() {
        this.XP = new int[] {0,10,10,20,20,30,20,20,10,0,-10,-20,-20,-30,-20,-20,-10,-10,};
        this.YP = new int[] {-10,-10,-30,-10,0,0,10,20,30,20,30,20,10,0,0,-10,-30,-10,};
        this.XPTHRUST = new int[] {0,10,20,20,10,10,0,-10,-10,-20,-20,-10,};
        this.YPTHRUST = new int[] {30,40,30,50,40,60,50,60,40,50,30,40,30,};
        this.lives = 4;
        this.SHOT_DELAY = 200;
        this.MAG_ACC = 400;
        this.RADIUS = 30;
        this.SIZE = 2;
    }

    /*
        Function that overrides PlayerShip update and does mostly the same thing, with the addition of the shotgun effect.
        This is done by rotating the ship, running the standard mkBullet() function and then rotating the ship to another position.
        at the end of this the ship is rotated by a random degree to simulate a recoil effect.
     */
    @Override
    public void update(List<GameObject> objects) {

        //line for grow mechanic
        //double acc = MAG_ACC/ MAXSIZE;
        Action action = this.controller.action();

        if (action.thrust == 1) {
            thrusting = true;
            if (!SoundManager.thrusting)
                SoundManager.startThrust();
        }else{
            thrusting = false;
            if (SoundManager.thrusting)
                SoundManager.stopThrust();
        }

        if(particles){
            for (int i = 0; i < 5; i++){
                Particle particle = new Particle(new Vector2D(s), Color.cyan);
                objects.add(particle);
            }
            reset();
            particles = false;
        }

        if (action.shoot && shootDelay<0 && !invincible){
            d.rotate(0.2);
            objects.add(mkBullet());
            d.rotate(-0.1);
            objects.add(mkBullet());
            d.rotate(-0.1);
            objects.add(mkBullet());
            d.rotate(-0.1);
            objects.add(mkBullet());
            d.rotate(-0.1);
            objects.add(mkBullet());
            d.rotate(0.2);
            d.rotate(((Math.random()+1)/4));

            shootDelay = 1;

        }else{
            shootDelay -= 1/SHOT_DELAY;
        }

        if (invincible){
            invincibleTime--;
            if (invincibleTime<0){
                COLOR = Color.cyan;
                invincible = false;
            }
        }

        //d.rotate(DT * action.turn * STEER_RATE);
        //v.add(d, acc * action.thrust * DT * MAXSIZE / getSize());
        v.mult(LOSS);
        super.update(objects);
    }
}
