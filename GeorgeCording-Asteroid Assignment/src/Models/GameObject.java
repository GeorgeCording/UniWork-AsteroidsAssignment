package Models;

import Auxillary.Vector2D;
import View.SoundManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.List;
import static Auxillary.Constants.*;

/**
 * Created by gcordi on 04/03/2015.
 *
 */
public abstract class GameObject {

    public Vector2D s,v,d;      //s = position vector, v = velocity vector, d = direction facing vector
    public double radius;       //radius of object, used in collision detection
    public Clip deathSound = SoundManager.bangMedium;  //sound it makes when it dies

    public boolean dead = false;            //If it is still relevant
    protected boolean enemy = false;        //If it won't collide with an enemy
    protected boolean invincible = false;   //If it is invincible

    protected Color COLOR = Color.GRAY; //colour of ship
    protected int[] XP = {};            //shape of object(X)
    protected int[] YP = {};            //shape of object(Y)


    public GameObject(){};

    /*
        Overlap checks to see if the two objects are overlapping on a 2D plain
     */

    private boolean overlap(GameObject other){
        if(this.invincible||other.invincible)
            return false;

        if (s.dist(other.s) < this.radius+other.radius )
            return true;
        else
            return false;
    }

    /*
        Checks to see if the two objects can interact with each other
     */
    private boolean canHit(GameObject other){
        if(this.getClass() == other.getClass())
            return false;
        if (this.enemy && other.enemy)
            return false;
        return true;
    }

    /*
        Determines if two objects have hit each other and designates them both as hit
     */

    public void collisionHandling(GameObject other) {
        if (!this.dead && !other.dead && canHit(other) &&  this.overlap(other)) {
            this.hit();
            other.hit();
        }
    }

    /*
        Basic response to being hit
     */

    protected void hit(){
        dead = true;
        SoundManager.play(deathSound);
    }

    /*
        standard response to an update call
     */

    public void update(List<GameObject> objects){
        s.add(v, DT);
        s.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    /*
        Function called by the game to kill any active bullets, This isn't used by most game objects, however any bullet
        or projectile that shouldn't exist after a round has ended will override this function and add necessary functionality
        This will also avoid any Audio or graphical effect that would occur if the bullet were to be hit.
     */

    public void killShots(){}

    /*
        abstract method to draw the object
     */

    public abstract void draw(Graphics2D g);
}
