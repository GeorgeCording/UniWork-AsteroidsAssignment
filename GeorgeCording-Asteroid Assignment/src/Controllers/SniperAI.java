package Controllers;

import Auxillary.Vector2D;
import Core.Game;
import Models.EnemyShip;
import Models.PlayerShip;

/**
 * Created by gcordi on 18/03/2015.
 * This is an AI for a type of enemy ship called SniperAI.
 * A Sniper type enemy will turn towards the player and shoot.
 */
public class SniperAI implements Controller{

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

    //This is a standard initializer that assigns values to the variables.
    //action can start out as a blank action, player can be gotten from the game but is held for easier reference.
    public SniperAI(Game game, EnemyShip enemyShip){
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

    //This function is called locally by action().
    public void checkActions(){

        /*
            Since a part of the ships design uses the orange polygon, thrust is set to 1 so that the "Aiming" part of the
            sniper is always visible.
            To ensure the ship doesn't actually move the MAG_ACC is set to 0 (See the Sniper class).
         */
        action.thrust = 1;
        action.shoot = true;

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
        int myDire = (int)(me.d.theta()*50);
        int playerD = (int)(direction.theta()*50);
        me.AddVectors(direction);
        int diff = myDire - playerD;
        if (Math.abs(diff) < 160&&Math.abs(diff)>1){
            if(diff > 0){
                action.turn = -1;
            }else{
                action.turn = 1;
            }
        }else if(Math.abs(diff)> 160){
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
