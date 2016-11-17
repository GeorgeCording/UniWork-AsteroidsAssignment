package Models.Colectibles;

import Core.Game;

import java.awt.*;

/**
 * Created by gcordi on 18/03/2015.
 *  The Invincibility class is a type of Treasure that can spawn in the game.
 *  This is only a small class that extends Treasure to adjust the colour of the object and what happens when the player
 *      shoots it.
 */
public class Invincibility extends Treasure{

    // A standard initializer that mostly calls the Treasure initializer, It also sets the colour to be white.
    public Invincibility(Game game){
        super(game);
        COLOR = Color.WHITE;
    }

    /*
        The dead function overrides the dead function in treasure.
        This function calls the setInvincible() function in the player ship (accessed from game.ship public variable).
        It then calls the setCOLOR() function for the player ship to ensure the player ship has changed colour correctly.
     */
    @Override
    public void Dead() {
        game.ship.setInvincible();
        game.ship.setCOLOR();
    }
}
