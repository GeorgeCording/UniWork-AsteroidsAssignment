package Controllers;

/*
 * Created by gcordi on 27/01/2015.
 * This is a very simple interface, used to ensure that all controllers implementing it have the action() function that
 * can be called on by other classes.
 */
public interface Controller {
    public Action action();
}
