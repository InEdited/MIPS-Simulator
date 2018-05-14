package Mips.Processor;

import Mips.Utils.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;




public class ALU {
    public Boolean zeroFlag = false;
    public long ALUResult;

    public String calculate(String firstValue,String secondValue,String controlSignal){
        zeroFlag = false;
        System.out.println("ALU unit executing : " + controlSignal + "on values : " + firstValue + " and " + secondValue);
        //add
        if(controlSignal.equals("0010"))
            ALUResult =  Utils.parseSignedLong(firstValue) + Utils.parseSignedLong(secondValue);
        //sub
        if(controlSignal.equals("0110")) {
            ALUResult = Utils.parseSignedLong(Utils.to32BitBinary(Long.parseLong(firstValue, 2) - Long.parseLong(secondValue, 2)));
            zeroFlag = ALUResult == 0;
        }
        //and
        if(controlSignal.equals("0000"))
            ALUResult =  Utils.parseSignedLong(firstValue) & Utils.parseSignedLong(secondValue);
        //or
        if(controlSignal.equals("0001"))
            ALUResult =  Utils.parseSignedLong(firstValue) | Utils.parseSignedLong(secondValue);
        //mul
        if(controlSignal.equals("1110"))
            ALUResult = Long.parseLong(firstValue,2) * Long.parseLong(secondValue,2);

        //slt
        if(controlSignal.equals("0111")){
            if(Utils.parseSignedLong(firstValue)<Utils.parseSignedLong(secondValue)){
                ALUResult = 1;
            }
            else{
                ALUResult = 0;
            }
        }
        //sltu
        if(controlSignal.equals("1011")){
            if(Long.parseLong(firstValue,2)<Long.parseLong(secondValue,2)){
                ALUResult = 1;
            }
            else{
                ALUResult = 0;
            }
        }
        //sll
        if(controlSignal.equals("1001")){
            ALUResult = Utils.parseSignedLong(firstValue)<< Utils.parseSignedInt(secondValue);
        }
        //srl
        if(controlSignal.equals("1010")){
            ALUResult = Utils.parseSignedLong(firstValue) >>> Utils.parseSignedInt(secondValue);
        }
        //nor
        if(controlSignal.equals("1000")) {
            ALUResult = ~(Long.parseLong(firstValue, 2) | Long.parseLong(secondValue, 2));
        }
        //System.out.println("Error: Problem with Alu calculation");
        return Utils.to32BitBinary(ALUResult);
    }
}
