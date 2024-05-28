package common;

import java.util.ArrayList;
import java.util.Random;

/**
 * this class is used to handle requests that are related to plate
 */
public class pelak {

    ArrayList<String> list = new ArrayList<>();
    Random random = new Random();
    private String next  = "null";

    public String getNext() {
        return next;
    }

    /**
     * this function make a list from a to z for using this list in making a plate
     */
    public void add(){
        list.add("a");list.add("b");list.add("c");list.add("d");list.add("e");
        list.add("f");list.add("g");list.add("h");list.add("i");list.add("j");
        list.add("k");list.add("l");list.add("m");list.add("n");list.add("n");
        list.add("o");list.add("p");list.add("q");list.add("r");list.add("s");
        list.add("t");list.add("u");list.add("v");list.add("w");list.add("x");
        list.add("y");list.add("z");
    }

    /**
     * this function makes a random plate
     * @return plate that is made random
     */
    public String rand(){
        add();
        String st = list.get(random.nextInt(26));
        String pelak = "[";
        pelak += random.nextInt(10);
        pelak += random.nextInt(10);
        pelak += st;
        pelak += random.nextInt(10);
        pelak += random.nextInt(10);
        pelak += random.nextInt(10);
        pelak += "]";
        return pelak;
    }

    /**
     * when one user requests a plate this method is called and gives user plate that is in the next filed if next does not equal null
     * @return an string that is plate
     */
    public String getPelak(){
        if (next.equals("null")){
            next = rand();
            return rand();
        }
        else {
            String current = next;
            next = rand();
            return current;
        }
    }
}
