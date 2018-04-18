package Mips.Processor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class Control {
    public static BooleanProperty RegDst
            ,Jump
            ,Branch
            ,MemRead
            ,MemToReg
            ,MemWrite
            ,ALUSrc
            ,RegWrite;
    public static StringProperty ALUOp;

    public static void initControl(){
        RegDst.set(false);
        Jump.set(false);
        Branch.set(false);
        MemRead.set(false);
        MemToReg.set(false);
        MemWrite.set(false);
        ALUSrc.set(false);
        RegWrite.set(false);
        ALUOp.set("00");
    }

    public static void control(String instruction){

    }

}
