package Mips.Processor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

public class ALUControl {
    //private StringProperty aluControl;
    private BooleanProperty JR = new SimpleBooleanProperty();


    public String getALUControlOp(String control,String functionCode){
        JR.set(false);
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
                //NOR
                if(functionCode.equals("100111"))
                    return "1000";

                //jr
                if(functionCode.equals("001000")){
                    JR.set(true);
                    return "0010";
                }



        else{
                    System.out.println("Error : Problem with the ALUControl unit.");
                return "";
            }
    }

    public boolean isJR() {
        return JR.get();
    }

    public BooleanProperty JRProperty() {
        return JR;
    }
}
