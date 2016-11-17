package Controllers;

import Auxillary.Vector2D;
import Core.Game;
import Models.EnemyShip;
import Models.PlayerShip;

import static Auxillary.Constants.FRAME_WIDTH;

/**
 * Created by gcordi on 18/03/2015.
 * This is an AI for a type of enemy ship called DrifterAI.
 * A Drifter type enemy will drift across the map until the player gets close, when the player gets within 1/8th of the
 * screen width the Drifter AI will turn towards the player and chase them, shooting.
 */
public class DrifterAI implements Controller{

    /*
        Four variables are stored in this class:
        action, holds details of the AI's action that it decided upon last AI tick.
        player, holds reference to the player ship, Used to know it's location.
        me,     holds reference to the EnemyShip this instance of AI controls.
        game,   holds reference to the active Game instance.
     */

    public Action action;
    private PlayerShip player;
    private EnemyShip me;
    public Game game;
    private double delay = 1;


    //This is a standard initializer that assigns values to the variables.
    //action can start out as a blank action, player can be gotten from the game but is held for easier reference.
    //action.turn is set to 1 so the drifters rotate to a diagonal rotation before moving forwards straight.
    public DrifterAI(Game game, EnemyShip enemyShip){
        action = new Action();
        action.turn = 1;
        this.game = game;
        player = game.ship;
        me = enemyShip;
    }

    //This function defines the action function in the controller interface, it is called by the game.
    public Action action() {
        checkActions();
        return action;
    }

    //This function checks to see if the player is far away. If so it will not shoot and follow IdleMode behaviour.
    //If the player is within 1/8th of the screen it will switch to tracking mode and start shooting.
    public void checkActions(){
        if(player.s.dist(me.s)> FRAME_WIDTH/8){
            IdleMode();
            action.shoot = false;
        }
        else {
            TrackingMode();
            action.shoot = true;
        }
    }

    /*
        This mode works on the premise that a drifter in idle mode will carry on in a straight line until it reaches
        A given velocity (100) at which point it will randomly start turning either left or right (Represented by
        action.turn being assigned 1 or -1 with an equal chance).
     */
    private void IdleMode(){
        if (me.v.mag() < 100){
            if(action.thrust == 0){
                action.turn = (Math.random() < 0.5) ? 0.1 : -0.1;
            }
            action.thrust = 1;
        }else if (me.v.mag() > 300){
            action.turn = 0;
            action.thrust = 0;
        }
    }

    /*
        The following calculates the turning force of the ship. "direction", a temporary Vector2D is created and the
        'add' function is called with the values (Player's x coordinate minus this ship's x coordinate) and
        (Player's y coordinate plus this ship's y coordinate).
        this Vector2D, when normalised is used to assign the playerD value, which represents the direction from this
        ship towards the player ship, myDire represents the direction the ship is currently facing.
        The difference between these two values is the distance the ship can turn in this current tick.
        as such it is assigned to the "Me.AddVectors()"

        The action turn value is also calculated to be either -1, 1 or 0. These represent turning left, right or
        being in the middle of the dead zone used to stop excess twitching.
     */

    private void TrackingMode(){
        action.turn = 0;
        action.thrust = 0;
        Vector2D direction = new Vector2D();
        direction.add(player.s.x-me.s.x,-me.s.y+player.s.y);
        direction.normalise();
        int myDire = (int)(me.d.theta()*20);
        int playerD = (int)(direction.theta()*20);
        me.AddVectors(direction);
        int diff = myDire - playerD;
        if (Math.abs(diff) < 64&&Math.abs(diff)>1){
            if(diff > 0){
                action.turn = -1;
            }else{
                action.turn = 1;
            }
        }else if(Math.abs(diff)> 64){
            if(diff > 0){
                action.turn = 1;
            }else{
                action.turn = -1;
            }
        }else{
            action.turn = 0;
        }
    }
}
