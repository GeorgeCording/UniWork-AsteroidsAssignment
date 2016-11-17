package Controllers;

import Auxillary.Vector2D;
import Core.Game;
import Models.EnemyShip;
import Models.PlayerShip;
import static Auxillary.Constants.*;

/**
 * Created by gcordi on 13/03/2015.
 * This is the AI for a type of enemy ship called AimNShoot.
 * It was an earlier version of the enemy AI and has since been adapted to form the DrifterAI, SeekerAI and SniperAI.
 *
 * The Behaviour of this AI was simply to turn towards the player and thrust full speed until within 1/8 of the screen-
 * Width. At which point it would begin shooting. This gave the behaviour of enemies throwing themselves at the player
 * and missing assuming the player moved.
 */

public class AimNShoot implements Controller{

    /*Four variables are stored in this class:
        action, holds details of the AI's action that it decided upon last AI tick.
        player, holds reference to the player ship, Used to know it's location.
        me,     holds reference to the EnemyShip this instance of AI controls.
        game,   holds reference to the active Game instance.
     */
    public Action action;
    private PlayerShip player;
    private EnemyShip me;
    public Game game;

    //This is a standard initializer that assigns values to the variables.
    //action can start out as a blank action, player can be gotten from the game but is held for easier reference.
    public AimNShoot(Game game, EnemyShip enemyShip){
        action = new Action();
        this.game = game;
        player = game.ship;
        me = enemyShip;

    }

    //This function defines the action function in the controller interface, it is called by the game.
    public Action action() {
        checkActions();
        return action;
    }

    /*
        This function assigns values to the action variable when it is called by the "Action" function.
        These values in the action variable are given to the game and detail how the ship should behave in the upcoming
        movement tick.
     */
    public void checkActions(){
        /*  An if statement checks to see if the distance between the player and the enemy ship is further than an 8th
            of the window width, If so then the thruster value in the action is assigned 1 and the shoot value is set to
            false.
            Otherwise the thrust value is set to 0 and the shoot boolean is set to true.
        */
        if(player.s.dist(me.s)> FRAME_WIDTH/8){
            action.thrust = 1;
            action.shoot = false;
        }
        else {
            action.thrust = 0;
            action.shoot = true;
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
        action.turn = 0;
        Vector2D direction = new Vector2D();
        direction.add(player.s.x-me.s.x,-me.s.y+player.s.y);
        direction.normalise();
        int myDire = (int)(me.d.theta()*10);
        int playerD = (int)(direction.theta()*10);
        me.AddVectors(direction);
        int diff = myDire - playerD;
        if (Math.abs(diff) < 32&&Math.abs(diff)>1){
            if(diff > 0){
                action.turn = -1;
            }else{
                action.turn = 1;
            }
        }else if(Math.abs(diff)> 32){
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
