package Models;

import Auxillary.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static Auxillary.Constants.FRAME_HEIGHT;
import static Auxillary.Constants.FRAME_WIDTH;
import static Auxillary.Sprite.ASTEROID_IMAGE;

/**
 * Created by gcordi on 14/03/2015.
 * This class is a type of Asteroid GameObject and as such extends Asteroid for simplicity.
 * The primary difference of this class is that it uses the ASTEROID_IMAGE rather than drawing a polygon.
 */
public class ImageAsteroid extends Asteroid{

    private AffineTransform transform;

    //A simple initializer that mostly calls the Asteroid Initializer but also calculates the radius from the image height.
    public ImageAsteroid(Vector2D s, Vector2D v, int size){
        super(s, v, size);
        this.radius = size*ASTEROID_IMAGE.getHeight(null)*0.5;
    }

    /*
        Simple draw function that creates a transform and has g draw the image with the given transform.
     */
    @Override
    public void draw(Graphics2D g) {
        if (dead)
            return;
        transform = new AffineTransform();
        transform.scale(size,size);
        AffineTransform ghold = g.getTransform();
        g.translate(s.x-radius,s.y-radius);
        g.drawImage(ASTEROID_IMAGE,transform,null);
        g.setTransform(ghold);
    }

    //Function called to create an ImageAsteroid with random position and velocity.
    static public ImageAsteroid makeRandomAsteroid(int size) {
        Math.random();
        Vector2D tS = new Vector2D((Math.random()*2-1) * FRAME_WIDTH,(Math.random()*2-1) * FRAME_HEIGHT);
        Vector2D tV = new Vector2D((Math.random()*2-1) * MAX_SPEED + MIN_SPEED, (Math.random()*2-1) * MAX_SPEED + MIN_SPEED);
        ImageAsteroid toReturn = new ImageAsteroid(tS,tV,size);
        return toReturn;
    }
}
