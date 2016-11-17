package Models;

import Auxillary.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

import static Auxillary.Constants.DT;
import static Auxillary.Constants.FRAME_HEIGHT;

/**
 * Created by gcordi on 04/03/2015.
 * The Particle class is a class that extends GameObject.
 * A particle is a GameObject that doesn't interact with any other game object and exists as a visual thing.
 * Particles, like asteroids come in random preset shapes.
 * To create a particle, one must define what colour the particle will be, this is to allow for a variety of colours,
 *  For example, a player ship will spawn them in cyan while an asteroid makes them in Light grey and a treasure spawns
 *  a variety, some in yellow, others in the colour of the treasure.
 * Like bullets, they exist for a limited lifetime before self-terminating.
 * Like Asteroid, the class handles spawning one in a random trajectory and rotation speed however,
 *  Since they are created by the death of another GameObject, the location of the particle will be assigned by its creator.
 */
public class Particle extends GameObject{

    //Two static variables MAXSPEED ans MINSPEED define the limits of a random speed that can be assigned to the particle.
    private static int MAXSPEED = 80;
    private static int MINSPEED = 20;

    //XP and YP are the x and y coordinates to draw a polygon.
    private int[] XP = {};
    private int[] YP = {};
    //turn speed is the particles speed of rotation per update.
    private double turnSpeed;
    //life is how long the particle will exist for. This value starts out at its default value and ticks down each update.
    protected double life = DT * FRAME_HEIGHT*Math.random()*10;
    private Color color;

    //Standard initializer that requires a location and a color for the particle.
    public Particle(Vector2D s, Color c){
        this.s = s;
        color = c;
        //Three static functions in the Particle class that handle assigning values to the instance of Particle.
        AssignP(this);
        AssignD(this);
        AssignV(this);

        //turnSpeed is set to a random rotation.
        this.turnSpeed = (Math.random() - 0.5)/2;
        this.d = new Vector2D(0,-1);

        //The particle doesn't interact with any other GameObject so invincible is set to true to prevent collision calculations.
        this.invincible = true;
    }

    // A simple update function that calls the parent to handle moving the particle,
    // and then rotates the particle and ticks its lifetime down, killing it if the life has reached 0.
    @Override
    public void update(List<GameObject> objects) {
        super.update(objects);
        d.rotate(turnSpeed);
        life--;
        if (life < 0)
            dead = true;
    }

    /*
        Simple Draw(Graphics2D) function that implements the GameObject draw function.
     */
    @Override
    public void draw(Graphics2D g) {
        /*
            Simple process used to draw the object, starts by exiting if the object is dead.
            The value of g is saved before manipulation.
            g is then translated to the objects x and y coordinates (saved as a part of a GameObject).
            g is then rotated to the rotation of d (saved as a part of GameObject).
            the colour of g is set to COLOR.
            the polygon is then drawn.
            the value of g is then reset to the original value. ----!This step is important!----
         */
        if(this.dead)
            return;
        AffineTransform at = g.getTransform();
        g.translate(s.x, s.y);
        double rot = d.theta() + Math.PI / 2;
        g.rotate(rot);
        g.scale(1,1);
        g.setColor(color);
        g.drawPolygon(XP, YP, XP.length);
        g.setTransform(at);

    }

    //-----------------------------------------------------------------------------------------------------
    //The following are static functions that handle assigning values to the particle as it is spawned.
    //Since these functions aare only called during initialization they are left as static.
    //-----------------------------------------------------------------------------------------------------

    // Called by AssignP(Particle), this function returns the int array respective to the number.
    // This is the X coordinates of the particle.
    static private int[] getXP(int num){
        switch (num){
            case 0:
                return new int[] {-5,15,-5};
            case 1:
                return new int[] {-5,5,15,-5};
            case 2:
                return new int[] {0,10,0,0};
            case 3:
                return new int[] {-5,5,5,-5};
            case 4:
                return new int[] {-5,5,5,-5};
            case 5:
                return new int[] {-5,5,5};
            case 6:
                return new int[] {-5,5,5,-5};
            case 7:
                return new int[] {0,5,5,-5,-5,0};
            default:
                return new int[] {-10,10};
        }
    }

    // Called by AssignP(Particle), this function returns the int array respective to the number.
    // This is the Y coordinates of the particle.
    static private int[] getYP(int num){
        switch (num){
            case 0:
                return new int[] {-15,5,5};
            case 1:
                return new int[] {-5,-5,15,5};
            case 2:
                return new int[] {-10,-10,-10,10};
            case 3:
                return new int[] {-10,-10,10,10};
            case 4:
                return new int[] {-5,-5,15,5};
            case 5:
                return new int[] {0,-10,10};
            case 6:
                return new int[] {-10,0,10,0};
            case 7:
                return new int[] {-5,-5,0,0,5,5};
            default:
                return new int[] {-10,10};
        }
    }

    // A function called by the Particle Initializer.
    // This generates a random number representing one of several potential polygon shapes a particle can have.
    // The two int arrays that relate to this polygon is then gotten from getXP and getYP and set to the polygons XP and YP
    static private void AssignP(Particle particle){
        int random = (int) (Math.random()*8);
        particle.XP = getXP(random);
        particle.YP = getYP(random);
    }

    // A function called by the Particle Initializer.
    // This handles creating two random values between the MINSPEED and MAXSPEED for the x and y direction of the particle.
    static private void AssignD(Particle particle){
        double[] D = {Math.random()*100, Math.random()*100};
        for (int i = 0; i < 2; i++){
            if(D[i] < MINSPEED)
                D[i] = MINSPEED;
            else if (D[i]> MAXSPEED)
                D[i] = MAXSPEED;
        }
        particle.d = new Vector2D(D[0],D[1]);
    }

    // A function called by the Particle Initializer.
    // A function that creates a random velocity for the particle within the range of max and min speed.
    static private void AssignV(Particle particle){
        particle.v = new Vector2D((Math.random()*2-1) * MAXSPEED + MINSPEED, (Math.random()*2-1) * MAXSPEED + MINSPEED);
    }
}
