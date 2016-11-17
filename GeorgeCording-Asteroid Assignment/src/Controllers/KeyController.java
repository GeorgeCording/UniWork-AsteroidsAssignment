package Controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
 * Created by gcordi on 27/01/2015.
 * This KeyController class handles the keyboard input of the player.
 * Like the enemy AI this implements the controller Interface.
 * This class extends the KeyAdapter class.
 *
 * One instance of this class is created to handle user input and acts as a controller for the player ship.
 * There is an Action variable that stores the current state of the user input in regards to how the player ship should
 * respond.
 */
public class KeyController extends KeyAdapter implements Controller{

    /*
        The following are variables tracked by the class.
        menu, boolean that is toggled by the player pressing the escape key. This is read by the game to pause the game.
        restart, boolean that is true when the player presses a restart key (R) the Game reads this and resets the game.
        selectedShip, an integer that tracks the current type of player ship. when the player presses a number key this
            value is changed and the Game detects this.
        ChangeMode, a boolean set true when the player presses the I key. This will change the graphics of the game when
            the Game reads it.
        ChaosMode, a boolean set true when the player presses the C key. This is read by the Game (and reset to false)
            the Game then toggles chaos mode (a mode where destroying asteroids spawns projectiles).

     */
    public boolean menu = false;
    public boolean restart = false;
    public int selectedShip = 1;
    public boolean ChangeMode = false;
    public boolean ChaosMode = false;

    // An Action variable that stores the current state of the user input
    public Action action;

    //Simple Initializer
    public KeyController(){
        action = new Action();
    }

    //Function to return the action variable, required by the controller interface.
    public Action action() {
        return action;
    }

    // This function extends KeyAdapters KeyPressed(KeyEvent e) and checks what key has been pressed.
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                //If the up (thrust) key is being pressed.
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                //if the left key is being pressed.
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                //If the right key is being pressed.
                action.turn = +1;
                break;
            case KeyEvent.VK_SPACE:
                //If the space (Shoot) key is being pressed.
                action.shoot = true;
                break;
            case KeyEvent.VK_ESCAPE:
                //If the escape (Pause) key is being pressed.
                menu = !menu;
                break;
            case KeyEvent.VK_R:
                //If the R (restart) key is being pressed.
                restart = true;
                break;
            case KeyEvent.VK_1:
                //If the 1 (select ship 1) key is being pressed.
                selectedShip = 1;
                break;
            case KeyEvent.VK_2:
                //If the 2 (select ship 2) key is being pressed.
                selectedShip = 2;
                break;
            case KeyEvent.VK_3:
                //If the 3 (select ship 3) key is being pressed.
                selectedShip = 3;
                break;
            case KeyEvent.VK_4:
                //If the 4 (select ship 4) key is being pressed.
                selectedShip = 4;
                break;
            case KeyEvent.VK_5:
                //If the 5 (select ship 5) key is being pressed.
                selectedShip = 5;
                break;
            case KeyEvent.VK_I:
                //If the I (Change Graphics mode) key is being pressed.
                ChangeMode = true;
                break;
            case KeyEvent.VK_C:
                //If the C (Toggle Chaos mode) key is being pressed.
                ChaosMode = true;
                break;

        }
    }

    // This function extends KeyAdapters KeyReleased(KeyEvent e) and checks what key has been released.
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                //If the up (thrust) key is being released.
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
                //if the left key is being released.
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                //If the right key is being released.
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                //If the space (Shoot) key is being released.
                action.shoot = false;
                break;
        }
    }
}
