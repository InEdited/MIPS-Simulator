package Mips.Utils;

public class Utils {
    private static final String padding = "000000000000000000000000000000000";
    private static final String paddingOnes = "11111111111111111111111111111111";
    private static final String padding26 = "000000000000000000000000000";

    public static String to16BitBinary(int number){
        if(number>=0)
            return Integer.toBinaryString(0x10000 | number).substring(1);
        else
            return Integer.toBinaryString(number).substring(16);
    }
    public static String to5BitBinary(int number){
        if(number>=0){
            String temp = Integer.toBinaryString(number);
            String thingie = "00000" + temp;

            return thingie.substring(temp.length());
        }
        else {
            String temp = Integer.toBinaryString(number);
            String thingie = "11111" + temp;

            return thingie.substring(temp.length());
        }
    }

    public static String to32BitBinary(int number){
        if(number>=0){
            String temp = Long.toBinaryString(number);
            String thingie = padding + temp;

            return thingie.substring(temp.length()+1);
        }
        else {
            String temp = Long.toBinaryString(number);
            String thingie = paddingOnes + temp;

            return thingie.substring(temp.length());
        }
    }


    public static String to32BitBinary(long number){
        if(number>=0){
            String temp = Long.toBinaryString(number);
            String thingie = padding + temp;

            return thingie.substring(temp.length()+1);
        }
        else {
            String temp = Long.toBinaryString(number);
            String thingie = paddingOnes + temp;

            return thingie.substring(temp.length());
        }
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

    public static int parseSignedInt(String binString){
        //String binString = "11010011100101010001100010010010";
        if(binString.charAt(1) == '0'){
            return Integer.parseInt(binString,2);
        }
        StringBuilder onesComplementBuilder = new StringBuilder();
        for(char bit : binString.toCharArray()) {
            // if bit is '0', append a 1. if bit is '1', append a 0.
            onesComplementBuilder.append((bit == '0') ? 1 : 0);
        }
        String onesComplement = onesComplementBuilder.toString();
        //System.out.println(onesComplement); // should be the NOT of binString
        int converted = Integer.valueOf(onesComplement, 2);
        // two's complement = one's complement + 1. This is the positive value
        // of our original binary string, so make it negative again.
        int value = -(converted + 1);
        return value;
    }

    public static long parseSignedLong(String binString){
        //String binString = "11010011100101010001100010010010";
        if(binString.charAt(1) == '0'){
            return Long.parseLong(binString,2);
        }
        StringBuilder onesComplementBuilder = new StringBuilder();
        for(char bit : binString.toCharArray()) {
            // if bit is '0', append a 1. if bit is '1', append a 0.
            onesComplementBuilder.append((bit == '0') ? 1 : 0);
        }
        String onesComplement = onesComplementBuilder.toString();
        //System.out.println(onesComplement); // should be the NOT of binString
        long converted = Long.valueOf(onesComplement, 2);
        // two's complement = one's complement + 1. This is the positive value
        // of our original binary string, so make it negative again.
        Long value = -(converted + 1);
        return value;
    }
}
