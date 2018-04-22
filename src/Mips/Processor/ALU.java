package Mips.Processor;

import Mips.Utils.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class ALU {
    public Boolean zeroFlag;
    public String ALUResult;

    public String calculate(String firstValue,String secondValue,String controlSignal){
        System.out.println("ALU unit executing : " + controlSignal + "on values : " + firstValue + " and " + secondValue);
        //add
        if(controlSignal.equals("0010"))
            ALUResult =  String.valueOf(Integer.parseInt(firstValue,2) + Integer.parseInt(secondValue,2));
        //sub
        if(controlSignal.equals("0110")) {
            ALUResult = String.valueOf(Integer.parseInt(firstValue, 2) - Integer.parseInt(secondValue, 2));
            zeroFlag = Integer.parseInt(ALUResult) == 0;
        }
        //and
        if(controlSignal.equals("0000"))
            ALUResult =  String.valueOf(Integer.parseInt(firstValue,2) & Integer.parseInt(secondValue,2));
        //or
        if(controlSignal.equals("0001"))
            ALUResult =  String.valueOf(Integer.parseInt(firstValue,2) | Integer.parseInt(secondValue,2));
        //stl
        if(controlSignal.equals("0111"))
            ALUResult =  String.valueOf(Integer.parseInt(firstValue,2) + Integer.parseInt(secondValue,2));
        //System.out.println("Error: Problem with Alu calculation");
        return Utils.to32BitBinary(Integer.parseInt(ALUResult,2));
    }
}
