package Mips.Processor;

import Mips.Utils.Utils;

public class SignExtend {
    private static final String padding16 = "00000000000000000";
    private static final String padding16Ones = "11111111111111111";

    public static String extendSign(String stuff){
/*        int integerStuff = Utils.parseSignedInt(stuff);
            return Utils.to32BitBinary(integerStuff);*/
        if(stuff.toCharArray()[0]=='0'){
            return padding16 + stuff;
        }
        else{
            return padding16Ones +stuff;
        }
    }
}
