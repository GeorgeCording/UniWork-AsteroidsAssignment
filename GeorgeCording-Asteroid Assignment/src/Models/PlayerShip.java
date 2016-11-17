package Models;

import Auxillary.Vector2D;
import Controllers.*;
import Models.AbstractShip;
import View.SoundManager;

import java.awt.*;
import java.util.*;

import static Auxillary.Constants.*;

/**
 * Created by gcordi on 04/03/2015.
 * PlayerShip is a class that extends AbstractShip class.
 * Anything that extends this class is designed to be a ship the player controls.
 * This class lays out the framework for how a player ship should function.
 * Player ships have multiple lives which can increase as well as decrease.
 * Player ships can become invincible, after losing a life and by outside factors.
 * When a player ship dies its position, rotation, and trajectory are reset, in addition it's size must be recalculated.
 */
public class PlayerShip extends AbstractShip {

    //Standard initializer that requires a controller, this is a KeyController for PlayerShips.
    /*
        A player ship starts out in the center of the screen, facing up and not moving. It will also start invincible.
     */
    public PlayerShip(Controller controller){
        this.setGuts();
        this.controller = controller;
        thrusting = false;
        s = new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2);
        v = new Vector2D(0,0);
        d = new Vector2D(0,-1);
        radius = RADIUS*getSize();
        reset();
    }

    // A function declared here to ensure that any class extending this class includes it.
    protected void setGuts(){
    }

    // A reset function that places the ship back to its starting position, recalculates its size and sets it to be invincible.
    public void reset() {
        thrusting = false;
        setInvincible();
        s.set(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        v.set(0,0);
        d.set(0,-1);
        radius = RADIUS*getSize();
    }

    /*
        Handles Player having multiple lives and a different death sound to other objects.
     */
    @Override
    public void hit() {
        particles = true;
        if (hasLife()) {
            lives--;
            setInvincible();
        }else{
            dead = true;
            SoundManager.play(SoundManager.bangLarge);
        }
    }

    // A Function to set the player ship to be invincible for 199 updates.
    public void setInvincible(){
        invincible = true;
        invincibleTime = 199;
    }

    // A Function that can be used to add a life to the player.
    public void addLife(){
        lives++;
        radius = RADIUS*lives;
    }

    // A Function to check to see if the player ship still has lives left.
    public boolean hasLife(){
        if (lives>0)
            return true;
        return false;
    }

    // A Function used to get the value of lives.
    public int getLives(){
        return lives;
    }

    // A Function used to get the value of invincibleTime.
    public double getInvincibleTime(){return invincibleTime;}

    // A Function used to set the value of lives.
    public void setLives(int num){
        lives = num;
    }
}
