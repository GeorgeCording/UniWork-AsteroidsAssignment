package View;

import static Auxillary.Constants.*;
import static Auxillary.Sprite.*;
import Auxillary.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import Core.Game;
import Models.GameObject;

/*
    The View class is a class that extends JComponent.
    This class handles creating the screen and drawing each GameObject onto the screen.
    This class ties heavily in to the Game class, while Game handles running the logic, View handles displaying the game.
 */

public class View extends JComponent {
	// background colour
	public final Color BG_COLOR = Color.BLACK;

    /*
        image holds a reference to BG_IMAGE (From the Sprite class) that is used as the background in image mode.
        transform is an AffineTransform that is assigned the default transform of the game.
        game is a reference to the instance of Game class running. This is to access variables as necessary.
     */
    Image image = BG_IMAGE;
    AffineTransform transform;
	private Game game;

    /*
        Simple initializer that requires a reference to the Game that it will be working alongside.
        A calculation is performed using the image width and height as well as the frame width and height,
        This is to determine which value is larger and then transform is set to the larger of the two values.
     */
	public View(Game game) {
		this.game = game;
        double imWidth = image.getWidth(null);
        double imHeight = image.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH ? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT ? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        transform = new AffineTransform();
        transform.scale(stretchx, stretchy);
	}

	/*
	    Main function of the view class. This is called by the Game to repaint the screen.
	    This is done by creating a graphics2D out of the Graphics given.
	    This Graphics2D is then used to draw the following:
	        First, the background, either image in RealisticMode or a black rectangle for normal mode.
	        Then, the Game.object list is accessed (syncronizing is done to prevent thread conflict)
	        For each GameObject in Game.object the draw(Graphics2D) function is called using g.
	        After this the player ship's is drawn using its draw(Graphics2D) function to ensure it is on top of all other GameObjects.
	        After these, text is drawn, The score, lives and level are drawn as strings in the correct positions.
	        At this point checks are made for other text that needs to be displayed.
	            If the game is in the gameover state, the Game Over text is drawn in the middle.
	            If the invincible time is greater than 0 its value is displayed.
	            If the game is paused then Paused is written in the middle if the screen.
	 */
	@Override
	public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        if (game.RealisticMode)
            g.drawImage(image,transform,null);
        else {
            g.setColor(BG_COLOR);
            g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }
		// paint the background

        synchronized (Game.class) {
            for (GameObject object : game.objects)
                object.draw(g);
            game.ship.draw(g);
            g.setColor(Color.YELLOW);
            g.drawString("Score: " + game.getScore(), 10, 10);
            g.drawString("Lives: " + game.getLives(), 10, 20);
            g.drawString("Level: " + game.level, 10, 30);
            if (game.gameOver)
                g.drawString("Game Over", FRAME_WIDTH / 2, FRAME_HEIGHT / 2);

            if (game.INVINCIBLE_TIME > 0 && !game.gameOver)
                g.drawString("" + (int) game.INVINCIBLE_TIME / 50, FRAME_WIDTH / 2, FRAME_HEIGHT / 4);

            if(game.ctrl.menu){
                g.drawString("Paused", FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
            }
        }
	}

	//Simple function used to assign the value of game.
    public void setGame(Game game){
        this.game = game;
    }

    //A Function used to get the size of the frame.
	@Override
	public Dimension getPreferredSize() {
		return Constants.FRAME_SIZE;
	}
}