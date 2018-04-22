package Mips.Processor;

import javafx.beans.property.StringProperty;

public class ALUControl {
    //private StringProperty aluControl;

    public String getALUControlOp(String control,String functionCode){
        if(control.equals("00"))
                return "0010";
        if(control.equals("01"))
                return "0110";
        if(control.equals("10"))
                if(functionCode.equals("100000"))
                    return "0010";
                if(functionCode.equals("100010"))
                    return "0110";
                if(functionCode.equals("100100"))
                    return "0000";
                if(functionCode.equals("100101"))
                    return "0001";
                if(functionCode.equals("101010"))
                    return "0111";


        else{
                    System.out.println("Error : Problem with the ALUControl unit.");
                return "";
            }
    }
}
