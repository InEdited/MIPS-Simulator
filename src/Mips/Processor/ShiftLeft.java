package Mips.Processor;

import Mips.Utils.Utils;

import javax.rmi.CORBA.Util;

public class ShiftLeft {

    public static String shiftLeft2(String thing){
        return Utils.to32BitBinary(Integer.parseInt(thing + "00" , 2));
    }
}
