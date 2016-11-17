package Core;

/**
 * Created by gcordi on 12/03/2015.
 *This is a class used in developing to scale the coordinates of visual point arrays. Though this isn't used in the
 * program, it is included as it was a useful tool for adding and testing images to ensure they were to scale.
 * This was mostly used for scaling the size of ships.
 * Running the program after assigning a value to the scale variable will print out the integer arrays in a form that can
 * be placed into the ships constructor function.
 */
public class Test {

    public static void main(String[] args) {
        int[] XP;
        int[] YP;
        int[] XPTHRUST;
        int[] YPTHRUST;

        double scale = 5;

        XP = new int[] {0,5,5,10,10,15,10,10,5,0,-5,-10,-10,-15,-10,-10,-5,-5};
        YP = new int[] {-5,-5,-15,-5,0,0,5,10,15,10,15,10,5,0,0,-5,-15,-5};
        XPTHRUST = new int[] {0,5,10,10,5,5,0,-5,-5,-10,-10,-5};
        YPTHRUST = new int[] {15,20,15,25,20,30,25,30,20,25,15,20,15};

        XP = new int[] {0,2,2,4,4,2,-2,-4,-4,-2,-2};
        YP = new int[] {-8,-6,-4,-2,2,4,4,2,-2,-4,-6};
        XPTHRUST = new int[] {-4,-2,2,4,4,2,0,-2,-4};
        YPTHRUST = new int[] {4,6,6,4,8,6,10,6,8};

        String out = "this.XP = new int[] {";
        for(int i : XP){
            out+=(int)(i*scale)+",";
        }
        out+="};";
        System.out.println(out);

        out = "this.YP = new int[] {";
        for(int i : YP){
            out+=(int)(i*scale)+",";
        }
        out+="};";
        System.out.println(out);

        out = "this.XPTHRUST = new int[] {";

        for(int i : XPTHRUST){
            out+=(int)(i*scale)+",";
        }
        out+="};";
        System.out.println(out);

        out = "this.YPTHRUST = new int[] {";

        for(int i : YPTHRUST){
            out+=(int)(i*scale)+",";
        }
        out+="};";
        System.out.println(out);
    }
}
