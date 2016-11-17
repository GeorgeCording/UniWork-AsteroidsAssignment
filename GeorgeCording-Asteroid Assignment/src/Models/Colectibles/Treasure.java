package Models.Colectibles;

import Auxillary.Vector2D;
import Core.Game;
import Models.GameObject;
import Models.Particle;

import static Auxillary.Constants.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

/**
 * Created by gcordi on 18/03/2015.
 *  The Treasure class is a GameObject that creates a small disk in the game, this is classed as an enemy but does not
 *  act in any way other than providing the player a benefit when destroyed. Though the player can collide into them.
 */
public class Treasure extends GameObject{

    /*
        Two variables are stored in addition to ones made by GameObject.
        Value   : An integer that stores the amount of points the player receives when the Treasure is destroyed.
        game    : A reference to the game, this is useful for easy manipulation of values, see OneUp and Invincibility.
     */
    int Value = 30;
    Game game;

    /*
        A simple initializer that assigns values to the object.
        The color of the treasure defaults to green, it is classed as an enemy for the purposes of players shooting them.
        The XP and YP are set to resemble an Octagon and s is assigned random coordinates on the game screen
     */
    public Treasure(Game game){
        Value = 30;
        this.game = game;
        COLOR = Color.GREEN;
        this.enemy = true;
        this.radius = 12;
        this.XP = new int[] {-2,2,6,6,2,-2,-6,-6};
        this.YP = new int[] {-6,-6,-2,2,6,6,2,-2};
        s = new Vector2D(Math.random()*FRAME_WIDTH, Math.random()*FRAME_HEIGHT);
        v = new Vector2D(0,0);
        d = new Vector2D(0,1);
    }

    /*
        The draw(Graphics2D) function extends that from GameObject. This function is responsible for drawing the game image.
        This is done first by drawing a circle with the fillOval function and is the colour of COLOR value of the GameObject.

        A quick check is done to see if the treasure is dead, if so it will exit at this point.

        At this point the position of g is saved, g is then translated to the position of the treasure, the Octagon is
        drawn in the colour YELLOW. then the position of g is set back to its saved value.
     */
    @Override
    public void draw(Graphics2D g) {

        int x = (int) s.x;
        int y = (int) s.y;
        g.setColor(COLOR);
        g.fillOval((int) (x - radius),(int)(y - radius), (int)(2 * radius), (int)(2 * radius));

        if(this.dead)
            return;

        AffineTransform at = g.getTransform();
        g.translate(s.x, s.y);
        g.scale(2, 2);
        g.setColor(Color.YELLOW);
        g.drawPolygon(XP, YP, XP.length);
        g.setTransform(at);
    }

    /*
        The update(list<GameObject>) is a function that overrides the GameObjects function.

        This starts by calling the GameObjects version of the function, It then checks to see if it has died.
        If it has died then two for loops are run, spawning 10 particles of yellow colour and of COLOR colour.
        These objects are added to the list<GameObject> (objects) supplied.
        after the for loops, if the treasure is dead then it will call its dead function.
     */
    @Override
    public void update(List<GameObject> objects) {

        super.update(objects);
        if (dead){
            for (int i = 0; i < 10; i ++){
                Particle particle = new Particle(new Vector2D(s), Color.YELLOW);
                objects.add(particle);
            }
            for (int i = 0; i < 10; i ++){
                Particle particle = new Particle(new Vector2D(s), COLOR);
                objects.add(particle);
            }
            Dead();
        }

    }

    // A very simple function that calls incScore(int) in the game class.
    public void Dead(){
        game.incScore(Value);
    }
}
