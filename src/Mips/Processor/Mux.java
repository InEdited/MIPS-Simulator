package Mips.Processor;

public class Mux {
    public String mux(String firstValue, String secondValue, boolean controlBit){
        if(!controlBit)
            return firstValue;
        if(controlBit)
            return secondValue;
        System.out.println("Error : A problem with mux input.");
        return "";
    }
}
