package Models.Colectibles;

import Core.Game;

import java.awt.*;

/**
 * Created by gcordi on 18/03/2015.
 *  The OneUp class is a type of Treasure that can spawn in the game.
 *  This is only a small class that extends Treasure to adjust the colour of the object and what happens when the player
 *      shoots it.
 */
public class OneUp extends Treasure{

    // A standard initializer that mostly calls the Treasure initializer, It also sets the colour to be blue.
    public OneUp (Game game){
        super(game);
        COLOR = Color.BLUE;
    }

    /*
        The dead function overrides the dead function in treasure.
        This function uses the game.ship public variable to access the player ship and increments the lives by 1.
    */
    @Override
    public void Dead() {
        game.ship.lives++;
    }
}
