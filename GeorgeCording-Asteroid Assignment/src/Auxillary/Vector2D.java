package Auxillary;

/*
 * Created by gcordi on 20/01/2015.
*/

public class Vector2D {

    // fields
    public double x, y;

    // construct a null vector
    public Vector2D(){

    }

    // construct a vector with given coordinates
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    // construct a vector that is a copy of the argument
    public Vector2D(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }

    // set coordinates
    public void set (double x, double y) {
        this.x = x;
        this.y = y;
    }

    // set coordinates to argument vector coordinates
    public void set (Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    // compare for equality (needs to allow for Object type argument...)
    public boolean equals(Object o) {

        if (o.getClass() == Vector2D.class){
            Vector2D v = (Vector2D) o;
            if(this.x == v.x && this.y == v.y){
                return true;
            }else{
                return false;
            }
        }
         return false;
    }
    //  magnitude (= "length") of this vector

    public double mag() {
        return Math.hypot(x,y);
    }

    // angle between vector and horizontal axis in radians
    public double theta() {
        return Math.atan2(y,x);
    }

    // String for displaying vector as text
    public String toString() {
        return "X: "+x+" Y: "+y;
    }

    // add argument vector
    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    // add coordinate values
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    // weighted add - frequently useful
    public void add(Vector2D v, double fac) {
        this.x += v.x * fac;
        this.y += v.y * fac;
    }

    // multiply with factor
    public void mult(double fac) {
        this.x *= fac;
        this.y *= fac;
    }

    // "wrap" vector with respect to given positive values w and h
    // method assumes that x >= -w and y >= -h
    public void wrap(double w, double h) {
        if(x>= w) {
            x = x% w;
        }else if (x< 0){
            x += w;
        }

        if(y >= h) {
            y = y% h;
        }else if (y < 0){
            y += h;
        }
    }

    // rotate by angle given in radians
    public void rotate(double theta) {
        double xx = x;
        double yy = y;
        x = xx * Math.cos(theta) - yy * Math.sin(theta);
        y = xx * Math.sin(theta) + yy * Math.cos(theta) ;
    }

    // scalar product with argument vector
    public double scalarProduct(Vector2D v) {
        return this.x*v.x + this.y*v.y;
    }

    // distance to argument vector
    public double dist(Vector2D v) {
        double xDiff = this.x - v.x;
        Double yDiff = this.y - v.y;
        return Math.hypot(xDiff,yDiff);
    }

    // normalise vector so that mag becomes 1
    // direction is unchanged
    public void normalise() {
        Double m = mag();
        x /= m;
        y /= m;
    }
}

