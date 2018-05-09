package Mips.Processor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

public class ALUControl {
    //private StringProperty aluControl;
    private BooleanProperty JR = new SimpleBooleanProperty();
    private BooleanProperty shamt = new SimpleBooleanProperty();


    public String getALUControlOp(String control,String functionCode){
        JR.set(false);
        shamt.set(false);
        if(control.equals("000"))
                return "0010";
        if(control.equals("001"))
                return "0110";

        //slti
        if(control.equals("100")){
            return "0111";
        }
        if(control.equals("010")) {
            if (functionCode.equals("100000"))
                return "0010";
            if (functionCode.equals("100010"))
                return "0110";
            if (functionCode.equals("100100"))
                return "0000";
            if (functionCode.equals("100101"))
                return "0001";
            //slt
            if (functionCode.equals("101010"))
                return "0111";
            //NOR
            if (functionCode.equals("100111"))
                return "1000";

            //jr
            if (functionCode.equals("001000")) {
                JR.set(true);
                return "0010";
            }

            //sll
            if(functionCode.equals("000000")){
                shamt.set(true);
                return "1001";
            }
            //srl
            if(functionCode.equals("000010")){
                shamt.set(true);
                return "1010";
            }
        }




        System.out.println("Error : Problem with the ALUControl unit.");
        return "";

    }

    public boolean isJR() {
        return JR.get();
    }

    public BooleanProperty JRProperty() {
        return JR;
    }

    public boolean isShamt() {
        return shamt.get();
    }

    public BooleanProperty shamtProperty() {
        return shamt;
    }
}
