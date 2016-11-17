package Models;

import Auxillary.Vector2D;

import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.List;

import static Auxillary.Constants.*;
import static Auxillary.Sprite.*;

/**
 * Created by gcordi on 11/02/2015
 * This class is the asteroid class and extends GameObject.
 * There are three sizes of asteroid and this is denoted by the size integer, when an asteroid larger than size 1 is destroyed,
 *      two asteroids one size smaller are spawned.
 * This class handles the main bulk of spawning the asteroids with random speeds, trajectories and rotations.
 * Each asteroid has an invincible time of 20 update cycles before they can be shot.
 * Each asteroid is a random shape, selected from one of 4 shapes using the AssignP(Asteroid) static function.
 */
public class Asteroid extends GameObject {

    /*
        Static values are stored for easy access.
        MAX_SPEED and MIN_SPEED : Two values that denote the upper and lower range of potential speeds for the asteroid
                                    to spawn with.
        RADIUS_PER_SIZE is the  : the value that is multiplied by the asteroids size to get the hitbox radius of the asteroid.
        CHAOS_MODE              : Set by the Game. If Chaos mode is activated then an asteroid will spawn three bullets
                                    as well as asteroids.
     */
    public static final double MAX_SPEED = 200;
    public static final double MIN_SPEED = 60;
    private static final int RADIUS_PER_SIZE = 6;
    public static boolean CHAOS_MODE = false;

    /*
        Three variables are stored for each asteroid alive:
        size            : The size of an asteroid, aa value that modifies the radius and scale of the polygon, also, if
            size is greater than 1, then two asteroids one size smaller will be spawned when the asteroid is destroyed.
        invincibleTime  : The number of updates an asteroid has left before it stops being invincible, this number starts
            at 20 and ticks down by one each update until it reaches 0, If it is 0 then it can be destroyed.
        turnSpeed       : Asteroids have a random rotation applied to their model, this value is set in the initializer.
     */
    protected int size;
    private double invincibleTime = 20;
    private double turnSpeed;

    // An initializer for the Asteroid. Requires a location (s), trajectory (v) and size (size).
    public Asteroid(Vector2D s, Vector2D v, int size){

        //assigns the polygon that is drawn as a visual representation of the asteroid.
        AssignP(this);
        this.s = s;
        this.v = v;
        this.size = size;
        this.COLOR = Color.red;

        //assigns hit box
        this.radius = size*RADIUS_PER_SIZE;

        this.enemy = true;
        this.invincible = true;

        //Assigning how fast it rotates
        this.turnSpeed = (Math.random() - 0.5)/(size*2);
        this.d = new Vector2D(0,-1);
    }

    @Override
    public void draw(Graphics2D g) {

        // Code that draws a circle the size of the hitbox of the Asteroid, used for testing and making new potential shapes.
        /*
        int x = (int) s.x;
        int y = (int) s.y;
        g.setColor(Color.green);
        g.fillOval((int) (x - radius),(int)(y - radius), (int)(2 * radius), (int)(2 * radius));
        */

        /*
            Simple process used to draw the object, starts by exiting if the asteroid is dead.
            The value of g is saved before manipulation.
            g is then translated to the asteroids x and y coordinates (saved as a part of a GameObject).
            g is then rotated to the rotation of d (saved as a part of GameObject).
            g is then scaled by the value of size (a bigger sized asteroid is bigger).
            the colour of g is set to COLOR, for an asteroid, this is Color.red.
            the polygon is then drawn.
            the value of g is then reset to the original value. ----!This step is important!----
         */
        if(this.dead)
            return;
        AffineTransform at = g.getTransform();//BEGIN g MANIPULATION.
        g.translate(s.x, s.y);
        double rot = d.theta() + Math.PI / 2;
        g.rotate(rot);
        g.scale(size, size);
        g.setColor(COLOR);
        g.drawPolygon(XP, YP, XP.length);
        g.setTransform(at);//END g MANIPULATION.
    }

    //static variable called to spawn a random asteroid of a given size (size).
    static public Asteroid makeRandomAsteroid(int size) {
        Math.random();
        Vector2D tS = new Vector2D((Math.random()*2-1) * FRAME_WIDTH,(Math.random()*2-1) * FRAME_HEIGHT);
        Vector2D tV = new Vector2D((Math.random()*2-1) * MAX_SPEED + MIN_SPEED, (Math.random()*2-1) * MAX_SPEED + MIN_SPEED);
        Asteroid toReturn = new Asteroid(tS,tV,size);
        return toReturn;
    }

    //Function called to spawn smaller asteroids when an asteroid larger than size 1 breaks.
    private void BreakUp(List<GameObject> objects){
        //calls the GameObject.hit() to handle killing the old asteroid.
        super.hit();
        //check to see if the asteroid is larger than size 1.
        //The two new asteroids will have a slightly altered trajectory to the old one but will still be based off of it.
        if (this.size > 1){
            //creates an asteroid one size smaller than the old with the same position and trajectory,
            //but then rotates the trajectory by 1/4Pi. (reason for not using a for loop)
            Asteroid asteroid = new Asteroid(new Vector2D(this.s),new Vector2D(this.v), (int) this.size - 1);
            asteroid.v.rotate(0.25*Math.PI);
            objects.add(asteroid);

            //creates an asteroid one size smaller than the old with the same position and trajectory,
            //but then rotates the trajectory by -1/4Pi. (reason for not using a for loop)
            Asteroid asteroid2 = new Asteroid(new Vector2D(this.s),new Vector2D(this.v), (int) this.size - 1);
            asteroid2.v.rotate(-0.25*Math.PI);
            objects.add(asteroid2);
        }

        //If chaos mode is activated then three bullets will also be spawned based off of the old asteroids position and trajectory.
        if(CHAOS_MODE) {
            Bullet bullet = new Bullet(new Vector2D(s), new Vector2D(v));
            Bullet bullet2 = new Bullet(new Vector2D(s), new Vector2D(v));
            Bullet bullet3 = new Bullet(new Vector2D(s), new Vector2D(v));
            bullet2.v.rotate(0.5);
            bullet3.v.rotate(-0.5);
            objects.add(bullet);
            objects.add(bullet2);
            objects.add(bullet3);
        }
    }

    //Overrides the GameObjects update function that is called each game tick.
    @Override
    public void update(List<GameObject> objects) {
        //moves the asteroid in the direction of trajectory, the distance it should have moved since the last tick.
        s.add(v, DT);
        //If the asteroid moves off the screen this will wrap it back round to the other side of the screen.
        s.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        //Rotates the rotation by turnSpeed, note, this doesn't affect the trajectory, only the image orientation.
        d.rotate(turnSpeed);

        //Check to see if the asteroid has died this update. If so the BreakUp(objects) is called to spawn smaller asteroids.
        if (this.dead){
                BreakUp(objects);
            //Spawns 5 particles in the location of the asteroid.
            for (int i = 0; i < 5; i++){
                Particle particle = new Particle(new Vector2D(s), Color.lightGray);
                objects.add(particle);
            }
        }
        //Ticks down the invincible timer, when this hits 0 the asteroid is no longer invincible (a value in GameObject).
        if (invincible){
            invincibleTime--;
            if (invincibleTime<0){
                invincible = false;
            }
        }
    }

    //-------------------------------------------------------------------------------------------
    //Asteroids have one of 5 shapes randomly selected during the initialization of the asteroid.
    // The following functions are for assigning this polygon.
    //-------------------------------------------------------------------------------------------

    //returns an integer array of the X coordinates of a polygon that an asteroid can be.
    public static int[] getXP(int number){
        switch (number){
            case 0:
                return new int[] {-2,0,4,6,6,4,6,2,-2,-6,-6,-4,-6};
            case 1:
                return new int[] {-2,0,6,6,4,6,0,-4,-6,-6};
            case 2:
                return new int[] {4,6,4,6,6,2,-4,-6,-6,-4,-6,-6,0};
            case 3:
                return new int[] {-4,4,6,4,6,4,-2,-6,-6,-4,-6};
            default:
                // A square shape, not supposed to be selected but is a default value to prevent error.
                return new int[] {-6,6,6,-6};
        }
    }

    //returns an integer array of the Y coordinates of a polygon that an asteroid can be.
    public static int[] getYP(int number){
        switch (number){
            case 0:
                return new int[] {-6,-6,-4,-2,0,2,6,6,4,6,4,-2,-4};
            case 1:
                return new int[] {-6,-6,-4,-2,0,6,4,6,4,-2};
            case 2:
                return new int[] {-6,-2,0,2,6,4,6,6,4,0,-2,-6,-4};
            case 3:
                return new int[] {-6,-6,-2,2,6,6,4,6,2,-2,-4};
            default:
                // A square shape, not supposed to be selected but is a default value to prevent error.
                return new int[] {-6,-6,6,6};
        }
    }

    //small function that assigns the asteroid a random polygon appearance. Called by Asteroids during the initializer.
    private static void AssignP(Asteroid asteroid){
        //Selects a random number from 0 to 3 and gets the x and y coordinates of that type of polygon,
        //      assigning it to the asteroid.
        int random = (int) (Math.random()*4);
        asteroid.XP = getXP(random);
        asteroid.YP = getYP(random);
    }
}

