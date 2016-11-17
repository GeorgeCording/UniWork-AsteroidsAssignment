package Models;

import Auxillary.Vector2D;
import Models.Bullet;

import static Auxillary.Constants.DT;

/**
 * Created by gcordi on 11/03/2015.
 * This class is a child of the Bullet class and are bullets fired by the Archie class ship.
 * They have a verry short duration but otherwise function the same as normal bullets.
 */
public class ArchiesBullet extends Bullet {

    // A simple initializer that calls the parent initializer but then sets the lifespan to be tiny (DT*250).
    public ArchiesBullet(Vector2D s, Vector2D v){
        super(s,v);
        life = DT * 250;
    }
}
