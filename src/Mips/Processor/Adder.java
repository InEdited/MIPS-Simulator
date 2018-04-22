package Mips.Processor;

public class Adder {

    public static String add(String firstThing, String secondThing){
        return String.valueOf(Integer.parseInt(firstThing,2) + Integer.parseInt(secondThing,2));
    }
}
