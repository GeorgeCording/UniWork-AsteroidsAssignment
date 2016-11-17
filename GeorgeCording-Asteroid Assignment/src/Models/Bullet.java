package Models;

import Auxillary.Vector2D;
import View.SoundManager;

import java.awt.*;
import java.util.List;

import static Auxillary.Constants.*;

/**
 * Created by gcordi on 04/03/2015.
 * The Bullet class is a class that extends GameObject.
 * This class handles the majority of the logic behind the bullet projectiles in the game.
 * A bullet is a dot projectile that is spawned by ships (and asteroids in chaos mode).
 * These dots are propelled across the map in a direction and velocity set at initialization of the shot.
 * A bullet will exist until it collides with something that it can kill or if it's life time has expired.
 * The life time of a bullet is calculated using the FRAME_HEIGHT as this ensures that no matter the resolution a bullet
 *      can travel a good distance and wrap around the screen if nothing gets in its way.
 * All bullets are green.
 *
 * A Bullet can be either an enemy or not, this depends on what kind of object spawned it. (Player ship no else yes).
 * A Bullet collides with GameObjects with the opposite boolean value to their "enemy" variable (see GameObject variables).
 */
public class Bullet extends GameObject{
    protected double life = DT * FRAME_HEIGHT*10;
    static public double BULLET_SPEED = 400;

    // A simple initializer that requires a position and velocity for the bullet.
    public Bullet(Vector2D s, Vector2D v){
        this.s = s;
        this.v = v;
        //this.d isn't used by this object, as it is a sphere.
        this.COLOR = Color.green;
        this.radius = 2;
    }

    /*
        This function overrides the update function in GameObject.
        It starts out by calling it's parent function as this will handle it's movement.
        It then decreases the life by one.
        When the life is less than 0 the Bullet is set to being dead and will be removed by the Game update process.
     */
    @Override
    public void update(List<GameObject> objects) {
        super.update(objects);
        life--;
        if (life < 0)
            dead = true;
    }

    /*
        A simple draw (Graphics2D) function that is called to display the Bullet in the game view.
        Very simply, g is set to being green and will fill an oval in the position of the bullet, with a radius to make a dot.
     */
    @Override
    public void draw(Graphics2D g) {
        int x = (int) s.x;
        int y = (int) s.y;
        g.setColor(COLOR);
        g.fillOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
    }

    //Simple function that sets the bullet to being dead (See GameObject.killShots() for why this is a thing).
    @Override
    public void killShots() {
        dead = true;
    }

    //Called when the bullet has collided with an appropriate target.
    @Override
    public void hit() {
        dead = true;
        SoundManager.play(SoundManager.bangSmall);
    }
}
