package Controllers;

/*
 * Created by gcordi on 27/01/2015.
 * This is a class that holds data for the AI ships. Each AI ship will create and hold an object of this type.
 * This stores required data to persist over to the next time the AI runs.
 * This class holds variables for the AI ships current thrusting strength, It's turn speed and if it is shooting.
 */
public class Action {
    public int thrust;
    public double turn;
    public boolean shoot;
}
