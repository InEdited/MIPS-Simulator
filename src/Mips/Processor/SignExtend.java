package Mips.Processor;

import Mips.Utils.Utils;

public class SignExtend {

    public static String extendSign(String stuff){
        int integerStuff = Utils.parseSignedInt(stuff);
            return Utils.to32BitBinary(integerStuff);
    }
}
