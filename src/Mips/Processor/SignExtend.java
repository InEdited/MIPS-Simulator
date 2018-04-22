package Mips.Processor;

import Mips.Utils.Utils;

public class SignExtend {

    public static String extendSign(String stuff){
        int integerStuff = Integer.parseInt(stuff,2);
            return Utils.to32BitBinary(integerStuff);
    }
}
