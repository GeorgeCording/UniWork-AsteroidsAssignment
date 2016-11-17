package Models;

import Auxillary.Vector2D;
import Controllers.Controller;
import View.SoundManager;

import java.awt.*;
import java.util.List;

import static Auxillary.Constants.FRAME_HEIGHT;
import static Auxillary.Constants.FRAME_WIDTH;

/**
 * Created by gcordi on 14/03/2015.
 * EnemyShip is a class that extends AbstractShip. All classes of enemy ships in the game extend from this class.
 * This ship holds a basic framework as well as default values for a standard enemy ship.
 * Enemy ships are controlled by an AI class that is given to them by the GiveController(Controller) function.
 */
public class EnemyShip extends AbstractShip {

    public Vector2D direction = new Vector2D();

    //Standard initializer.
    public EnemyShip(){
        this.SetGuts();
        thrusting = false;
        enemy = true;
        s = new Vector2D(FRAME_WIDTH/4,FRAME_HEIGHT/4);
        v = new Vector2D(0,0);
        d = new Vector2D(0,-1);
        radius = RADIUS;
        reset();
    }

    // A function called by the initializer that assigns values to an enemy ship, potentially unique to each type.
    // This function exists to be overwritten without having to overwrite the main initializer.
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

    // Simple function used to set the controller.
    public void GiveController(Controller c){
        controller = c;
    }

    // Function that exists to be overwritten by enemy classes that need to.
    @Override
    public void reset() {
    }

    //function that handles what to do when the ship has been hit.
    @Override
    public void hit(){
        dead = true;
        particles = true;
    }

    //simple function that overrides the AbstractShip update.
    @Override
    public void update(List<GameObject> objects) {
        super.update(objects);
        controller.action();
    }

    /*
        A function that creates a bullet, mostly the function of it's parent, in AbstractShip. But with a few differences.
        bullet.life is reduced to 1/4.
        bullet.enemy is set to true, meaning it will only collide with the player.
    */
    @Override
    protected Bullet mkBullet() {
        Bullet bullet = new Bullet(new Vector2D(s), new Vector2D(v));
        bullet.enemy = true;
        bullet.life = bullet.life/4;
        bullet.s.add(d,RADIUS/getSize());
        bullet.v.add(d, bullet.BULLET_SPEED + MAG_ACC*(getAcceleration(SIZE,lives)+1.5));
        bullet.update(null);
        SoundManager.fire();
        return bullet;
    }
    //Simple function to assign a direction to the ship.
    public void AddVectors(Vector2D direction){
        this.direction = direction;
    }
}
