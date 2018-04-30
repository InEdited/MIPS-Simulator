package Mips.Processor;

import Mips.Utils.Utils;

public class ShiftLeft {

    public static String shiftLeft(String thing){
        return Utils.to32BitBinary(Long.parseLong(thing + "00" , 2));
    }
}
