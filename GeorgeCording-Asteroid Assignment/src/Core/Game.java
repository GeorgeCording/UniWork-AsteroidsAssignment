package Core;

import Controllers.DrifterAI;
import Controllers.SeekerAI;
import Controllers.KeyController;
import Controllers.SniperAI;
import Models.*;
import Auxillary.*;
import Models.Colectibles.Invincibility;
import Models.Colectibles.OneUp;
import Models.Colectibles.Treasure;
import Models.EnemyShip;
import Models.EnemyShips.Drifter;
import Models.EnemyShips.Seeker;
import Models.EnemyShips.Sniper;
//import Models.PlayerShips.*;
import View.*;
import static Auxillary.Constants.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gcordi on 20/01/2015.
 * This is the Primary class of the program, It contains the "public static void main" that is run to start the program.
 * This class is run as the main thread and handles the execution of the game.
 */

public class Game {
    /*
        A number of variables are created and held in the Game class, These are:
        objects         : A list of game objects that exist in the game, these are things such as
                        enemy ships, Asteroids, Bullets and the player ship. This is passed through each time the game updates
        ship            : An object of type player ship, the player ship is held here, however a reference to the player ship
                        is also held in the objects list.
        lives           : An integer that stores the number of lives the player has, This is used for calculations on the
                        Game's side as an AbstractShip (the class that a player ship extends from) holds a value of its
                        own lives separately
        asteroidsleft   : A boolean that tracks whether there are still asteroids left in the level. If this is still
                        false after the objects list has been scanned through the level is incremented
        gameOver        : A boolean that tracks if the game has ended.
        RealisticMode   : A boolean that tracks if the game has been set to realistic graphics mode.

        level           : An integer that tracks what level the player is currently on. (used to calculate number of
                        asteroids and enemies to spawn in)

        score           : An integer that tracks the players current score, It is reset each new game.
        backScore       : An integer that records the value of the score the last time a treasure was spawned, When score
                        reaches 100 more than backscore, backscore is incremented by 100 and treasure is set to true
        CurrentShip     : An integer tracking the current player ship type.
        treasure        : A boolean telling the game if a treasure should be spawned next pass through the update stage.
        INVINCIBLE_TIME : A double that records the time left for the player to be invincible.
        ctrl            : A KeyController that monitors keyboard input from the player.

     */
    public List<GameObject> objects;
    public PlayerShip ship;
    private int lives;
    private boolean asteroidsLeft = true;
    public boolean gameOver = false;
    public boolean RealisticMode = false;

    public int level = 1;

    private int score = 0;
    private int backScore = 0;
    private int CurrentShip = 1;
    private boolean treasure = false;

    public double INVINCIBLE_TIME = 0;

    public KeyController ctrl;

    //A standard initializer that assigns values where necessary, buildLevel is called to create the first level.
    //The player ship is added to the list and a sound is played to force the SoundManager to be initialised.
    public Game() {
        objects = new ArrayList<GameObject>();
        ctrl = new KeyController();
        ship = new FishTail(ctrl);
        buildLevel(1);
        objects.add(ship);
        SoundManager.fire();

    }

    /*
        The main function, ran to start the game.
        This starts out by creating a new Game and a View, with an JEasyFrame being placed in the view and a KeyListener
            added to the JEasyFrame.

        Then the game enters into an unending while loop, This performs the following processes:
            A check to see if the game isn't paused, If so, the Game runs the update function.
            A check to see if the restart boolean has been set or if the current ship is not the ship that has been selected
                If either of these are true a newGame will be run with restart being set to false.
            A check to see if the ChangeMode boolean has been set to true, If so RealisticMode is toggled and ChangeMode
                is set to false.
            A check to see if ChaosMode has been set to true, If so the CHAOS_MODE static boolean in Asteroid class is toggled

        Lastly, the View is set to repaint the display and this thread is set to sleep for the DELAY value.
     */
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Basic Game").addKeyListener(game.ctrl);
        while (true) {

            if(!game.ctrl.menu)
                game.update();

            if(game.ctrl.restart||game.CurrentShip != game.ctrl.selectedShip)
            {
                game.ctrl.restart = false;
                game.newGame(game.ctrl.selectedShip);
            }
            if(game.ctrl.ChangeMode) {
                game.RealisticMode = !game.RealisticMode;
                game.ctrl.ChangeMode = false;
            }
            if(game.ctrl.ChaosMode)
            {
                Asteroid.CHAOS_MODE = !Asteroid.CHAOS_MODE;
                game.ctrl.ChaosMode = false;
            }

            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    /*
        The update function is the function run each "Game Tick" It handles going through each game object and calling
        their update function, sorting out the living game objects from the ones that have died, and other such things.

        At the start of this function, asteroidsLeft is set to false, if the run through finds an asteroid it will set this
        to true, else it will still be false at the end of the pass through (and the function will handle spawning a new level)

        A for loop passes through the list of game objects and for each one has the object handle collisions of each other
        game object. If asteroidsLeft is false and the game object is an asteroid or imageasteroid then asteroidsLeft is
        set to true. If the object died then the score is incremented by 10.

        Outside of the loop is a check to see if the player ship is dead, If so GameOver is set to true.

        lives and INVINCIBLE_TIME is set from the player ships' tracked value.

        A temporary array of GameObjects called alive is made and a loop through each game object is done, those that are
        alive will be added to the list and then if treasure is true a new treasure will be made and added to this list.

        at this point the thread synchronizes to ensure that the objects array isn't in use. (It is also used by the view class)
        The array is cleared and replaced with the values of alive.

        At this point if there are no asteroidsLeft, then level is incremented, built and the player ship is set invincible.
     */
    public void update() {
        asteroidsLeft = false;
        for (int i = 0; i<objects.size();i++){
            for (int j = i; j < objects.size(); j++){
                objects.get(i).collisionHandling(objects.get(j));
            }
            if(!asteroidsLeft && (objects.get(i).getClass() == Asteroid.class||objects.get(i).getClass() == ImageAsteroid.class))
                asteroidsLeft = true;
            if(objects.get(i).dead)
                incScore(10);
        }
        if (ship.dead)
            gameOver = true;

        lives = ship.getLives();
        INVINCIBLE_TIME = ship.getInvincibleTime();

        List<GameObject> alive = new ArrayList<GameObject>();
        for (GameObject object : objects) {
            object.update(alive);
            if (!object.dead)
                alive.add(object);
        }
        if (treasure){
            alive.add(makeTreasure(this));
            treasure = false;
        }

        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }

        if(!asteroidsLeft){
            level++;
            buildLevel(level);
            ship.setInvincible();
        }
    }

    public void incScore(int score){
        this.score += score;
        if (this.score>= backScore + 100){
            treasure = true;
            backScore +=100;
        }

    }

    /*
        This buildLevel(int level) function is used to spawn a new level, It controls creating more enemy ships and asteroids.

        The primary part of this function is three for loops that spawn seekers, snipers and drifters in turn, seekers
        will be spawned at a rate of 3+level, snipers one for each level and drifters, one per level + 1.
        A new EnemyShip is made for the loop, this is given a random position on screen and an AI respective to the type
        of enemy.

        After these loops another one occurs to create asteroids, depending on if RealisticMode is set to true it will
        create ImageAsteroids or normal Asteroids. the number of asteroids spawn is 3*level + either 3 or 20 depending on
        if CHAOS_MODE is activated.

        Lastly any existing bullets are destroyed.
     */
    public void buildLevel(int level){
        for(int i = 0; i<level+3;i++) {
            EnemyShip temp = new Seeker();
            temp.s = new Vector2D((Math.random()*2-1) * FRAME_WIDTH,(Math.random()*2-1) * FRAME_HEIGHT);
            temp.GiveController(new SeekerAI(this, temp));
            objects.add(temp);
        }
        for (int i = 0; i<level;i++){
            EnemyShip temp = new Sniper();
            temp.s = new Vector2D((Math.random()*2-1) * FRAME_WIDTH,(Math.random()*2-1) * FRAME_HEIGHT);
            temp.GiveController(new SniperAI(this, temp));
            objects.add(temp);
        }
        for (int i = 0; i<level+1;i++){
            EnemyShip temp = new Drifter();
            temp.s = new Vector2D((Math.random()*2-1) * FRAME_WIDTH,(Math.random()*2-1) * FRAME_HEIGHT);
            temp.GiveController(new DrifterAI(this, temp));
            objects.add(temp);
        }

        for (int i = 0; i < 3*level + (Asteroid.CHAOS_MODE ? 20: 3); i++) {
            if (RealisticMode)
                objects.add(ImageAsteroid.makeRandomAsteroid(3));
            else
                objects.add(Asteroid.makeRandomAsteroid(3));
        }
        for (GameObject object : objects){
            object.killShots();
        }
    }

    //simple get function that returns the score.
    public int getScore(){
        return score;
    }

    //simple get function that returns the player lives.
    public int getLives(){
        return lives;
    }

    /*
        The newGame(int ship) function creates a new game at level one, the integer it requires represents the type of
        player ship that is made. Necessary values are reset and a switch statement handles assigning the correct player
        ship.
        Lastly the buildLevel(level) function is called to make the enemies and asteroids.
     */
    public void newGame(int ship){
        level = 1;
        score = 0;
        backScore = 0;
        gameOver = false;
        asteroidsLeft = true;
        CurrentShip = ship;
        objects = new ArrayList<GameObject>();
        switch(ship){
            default:
                this.ship = new FishTail(ctrl);
                break;
            case 2:
                this.ship = new Archie(ctrl);
                break;
            case 3:
                this.ship = new Bessi(ctrl);
                break;
            case 4:
                this.ship = new Buster(ctrl);
                break;
            case 5:
                this.ship = new Krampt(ctrl);
                break;
        }
        objects.add(this.ship);
        buildLevel(level);
    }

    /*
        The makeTreasure (Game game) function is a static function that is called to spawn a treasure in the given game.

        An integer called chance is created as a random number from 1 - 10, if this value is 1 then a OneUp treasure is
        spawned, if it is 2 then an Invincibility treasure is spawned, else it spawns a standard Treasure.
     */
    private static Treasure makeTreasure(Game game){
        int chance = (int) (Math.random()*10);
        switch (chance){
            case 1:
                return new OneUp(game);
            case 2:
                return new Invincibility(game);
            default:
                return new Treasure(game);
        }
    }
}