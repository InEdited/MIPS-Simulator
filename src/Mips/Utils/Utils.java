package Mips.Utils;

public class Utils {
    private static final String padding = "000000000000000000000000000000000";
    private static final String padding26 = "000000000000000000000000000";
    public static String to16BitBinary(int number){
        if(number>0)
            return Integer.toBinaryString(0x10000 | number).substring(1);
        else
            return Integer.toBinaryString(number).substring(16);
    }
    public static String to32BitBinary(int number){
        if(number>=0){
            String temp = Long.toBinaryString(number);
            String thingie = padding + temp;

            return thingie.substring(temp.length()+1);
        }
        else
            return Long.toBinaryString(number);
    }

    public static String to32BitBinary(long number){
        if(number>=0){
            String temp = Long.toBinaryString(number);
            String thingie = padding + temp;

            return thingie.substring(temp.length()+1);
        }
        else
            return Long.toBinaryString(number);
    }

    public static String to26BitBinary(long number) {
        if (number >= 0){
            String temp = Long.toBinaryString(number);
            String thingie = padding26 + temp;
            return thingie.substring(temp.length()+1);
        }
        else
            return Long.toBinaryString(number).substring(Long.toBinaryString(number).length()+1);
    }
}
