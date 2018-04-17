package Mips.Utils;

public class Utils {
    public static String to16BitBinary(int number){
        if(number>0)
            return Integer.toBinaryString(0x10000 | number).substring(1);
        else
            return Integer.toBinaryString(number).substring(16);
    }
}
