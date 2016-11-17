package Models;

import Auxillary.Vector2D;
import Controllers.Action;
import Controllers.Controller;
import View.SoundManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

import static Auxillary.Constants.DT;

/**
 * Created by gcordi on 04/03/2015.
 *
 */
public abstract class AbstractShip extends GameObject{
    //Static values
    protected double MAG_ACC;       //magnitude of acceleration when thrust is applied
    protected double SHOT_DELAY;    //how long its' shots are delayed
    public static double LOSS = 0.99;         //constant speed loss factor
    protected int SIZE;
    //

    //Changing values
    protected boolean thrusting;        //if the ship is thrusting
    protected double shootDelay = 1;      //countdown till can shoot
    public int lives;                   //lives the ship has
    protected double invincibleTime = 0;  //how long the ship has left for being invincible
    protected boolean particles = false;  //indication to update to create particles
    //

    //Constant but unique for each ship
    public Controller controller;  //what controls the ship
    //

    //shape of ship
    protected int[] XPTHRUST = {};   //shape of ship jet (X)
    protected int[] YPTHRUST = {};   //shape of ship jet (Y)
    protected double RADIUS;
    //

    public AbstractShip(){
        this.deathSound = getDeathSound(SIZE);
    }

    protected Bullet mkBullet() {
        Bullet bullet = new Bullet(new Vector2D(s), new Vector2D(v));
        bullet.s.add(d,RADIUS/getSize());
        bullet.v.add(d, bullet.BULLET_SPEED + MAG_ACC*(getAcceleration(SIZE,lives)+1.5));
        bullet.update(null);
        SoundManager.fire();
        return bullet;
    }

    @Override
    public void draw(Graphics2D g) {
        /*int x = (int) s.x;
        int y = (int) s.y;
        g.setColor(Color.green);
        g.fillOval((int) (x - radius),(int)(y - radius), (int)(2 * radius), (int)(2 * radius));*/

        /*
            Simple process used to draw the object, starts by exiting if the object is dead.
            The value of g is saved before manipulation.
            g is then translated to the objects x and y coordinates (saved as a part of a GameObject).
            g is then rotated to the rotation of d (saved as a part of GameObject).
            g is then scaled by the value of getSize().
            the colour of g is set to COLOR.
            the main polygon is then drawn.
            to emphasise the corners tiny ovals are drawn at each point.
            If the ship is thrusting then the polygon for the thrust is also drawn in orange.
            the value of g is then reset to the original value. ----!This step is important!----
         */
        if(this.dead)
            return;
        AffineTransform at = g.getTransform();
        g.translate(s.x, s.y);
        double rot = d.theta() + Math.PI / 2;
        g.rotate(rot);
        g.scale(getSize(),getSize());
        g.setColor(COLOR);
        g.drawPolygon(XP, YP, XP.length);
        for(int i = 0; i < XP.length; i++){
            g.drawOval((int)(XP[i]),(int)(YP[i]),1,1);
        }
        if (thrusting) {
            g.setColor(Color.orange);
            g.drawPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }

    @Override
    public void update(List<GameObject> objects) {

        //line for growth mechanic
        double acc = MAG_ACC*(getAcceleration(SIZE,lives)+1.5);

        Action action = this.controller.action();

        //updating sound and graphics for if the ship is thrusting
        if (action.thrust == 1) {
            thrusting = true;
            if (!SoundManager.thrusting)
                SoundManager.startThrust();
        }else{
            thrusting = false;
            if (SoundManager.thrusting)
                SoundManager.stopThrust();
        }

        //handle generating particles when a ship dies
        if(particles){

            for (int i = 0; i < 5; i++){
                Particle particle = new Particle(new Vector2D(s), COLOR);
                objects.add(particle);
            }
            reset();
            COLOR = Color.gray;
            particles = false;
        }

        //handle shooting and calculating delay between shots
        if (action.shoot && shootDelay<0 && !invincible){
            objects.add(mkBullet());
            shootDelay = 1;

        }else{
            shootDelay -= 1/SHOT_DELAY;
        }

        //handle calculating invincibility time and becoming vincible
        if (invincible){
            invincibleTime--;
            if (invincibleTime<0){
                COLOR = Color.cyan;
                invincible = false;
            }
        }

        //calculating movement of the ship
        d.rotate(DT * action.turn * getTurnRate(SIZE,lives));
        v.add(d, acc * action.thrust * DT);
        v.mult(LOSS);
        super.update(objects);
    }


    protected double getSize(){
        //calculates scale to enlarge polygon by when drawn
        double toReturn = 0.6+0.05*lives;
        //ensures minimum size of 0.99
        if(toReturn < 1)
            return toReturn;
        return 0.99;
    }

    /*
        This function calculates the rate of acceleration of the ship (dependant on number of lives and ship size)
        The weight of a ship is one of three categories, Light, Medium and Heavy. Represented by the numbers (num) 1,2,3
        respectively.

        The more lives a ship has the lower its acceleration. represented by (X-L*Y) where L is the number of lives and
        both X and Y are pre-determined values based on the weight category.
        There is one exception to this rule, this being Heavy ships, at 0 lives Heavy ships get a boost to acceleration
        this is to provide a noticeable improvement to a medium sized ship at 0 lives.

        These calculations are balanced to have the following effect:
        -Light ships are least effected by the live count, (Ranging from 1.25 down to 1 vs 1.375 down to 0.625 and 1.5 down to 0.4)
        -At 1 Life or less, a medium ship has better acceleration than a light ship.
        -A medium ship has better acceleration than a heavy ship until 0 lives left.
        -At 0 Lives, a Heavy ship has the best acceleration (made more significant by the bonus).
     */
    public static double getAcceleration(int num, int lives){
        // The more lives a ship has the slower it accelerates, this caps at 5.
        if(lives > 5)
            lives = 5;

        //For heavy ships, having 0 lives means the acceleration is 1.5, else it is 1.4 - lives * 0.2 (to a minimum of 0.4)
        if (num == 3) {
            if (lives == 0)
                return 1.5;
            return (1.4 - lives*0.2);
        }
        //For medium ships, the acceleration is 1.375 - Lives * 0.15, to a minimum of 0.625
        if (num == 2)
            return (1.375 - lives*0.15);

        //For light ships, the acceleration is 1.25 - lives*0.05, to a minimum of 1
        return (1.25 - lives*0.05);
    }

    /*
        Similar to getAcceleration (see above) this function is supplied the weight category of a ship and the number of lives
        It then calculates the ships turning speed.
        In a similar manner, Light ships are least effected by live count and the best speed at higher lives.
        Medium ships have better turn speed than light ships at 1 life or less.
        Heavy ships have the worst turn speed until their boost at 0 lives remaining and are most effected by the life count.
     */
    public static double getTurnRate(int num, int lives){

        //These calculations are within the range of 0 - 5 lives.
        if(lives > 5)
            lives = 5;

        if (num == 3) {
            if (lives == 0)
                return 3 * Math.PI;
            return (2.8 - lives*0.4)*Math.PI;
        }
        if (num == 2)
            return (2.65 - lives*0.2)*Math.PI;

        return (2.5 - lives*0.1)*Math.PI;
    }

    //A function that returns the sound played when the ship is destroyed,
    //This is a different sound effect depending on the weight of the ship.
    public static Clip getDeathSound(int size){
        switch (size){
            case 1:
                return SoundManager.bangSmall;
            case 2:
                return SoundManager.bangMedium;
            default:
                return SoundManager.bangLarge;
        }

    }

    //A function that is required by the class that implements this interface.
    //Reset is the function called when the ship must be reset to default values. E.G. it lost a life but still has more lives.
    public abstract void reset();

    //A function that sets the COLOR of the GameObject to grey. This is mostly for if a child wishes to overwrite this function.
    public void setCOLOR(){
        COLOR = Color.gray;
    }
}
