package Mips.Processor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Control {
    private BooleanProperty RegDst = new SimpleBooleanProperty()
            ,Jump = new SimpleBooleanProperty()
            ,Branch = new SimpleBooleanProperty()
            ,MemRead = new SimpleBooleanProperty()
            ,MemToReg = new SimpleBooleanProperty()
            ,MemWrite = new SimpleBooleanProperty()
            ,ALUSrc = new SimpleBooleanProperty()
            ,RegWrite = new SimpleBooleanProperty()
            ,JAL = new SimpleBooleanProperty();
    public StringProperty ALUOp = new SimpleStringProperty();

    public Control(){
        this.RegDst.set(false);
        this.Jump.set(false);
        this.Branch.set(false);
        this.MemRead.set(false);
        this.MemToReg.set(false);
        this.MemWrite.set(false);
        this.ALUSrc.set(false);
        this.RegWrite.set(false);
        this.JAL.set(false);
        //this.ALUOp.set("00");
    }

    public void controlStuff(String instruction){
        String usedInstruction = instruction.substring(0,6);
        System.out.println("Control unit took the instruction : " + usedInstruction);
        //R type instruction
        if(usedInstruction.equals("000000")){
            RegDst.set(true);
            ALUSrc.set(false);
            MemToReg.set(false);
            RegWrite.set(true);
            MemRead.set(false);
            MemWrite.set(false);
            JAL.set(false);
            Branch.set(true);
            Jump.set(false);
            ALUOp.setValue("10");
        }

        //lw instruction
        if(usedInstruction.equals("100011")){
            RegDst.set(false);
            ALUSrc.set(true);
            MemToReg.set(true);
            RegWrite.set(true);
            MemRead.set(true);
            MemWrite.set(false);
            Branch.set(false);
            Jump.set(false);
            JAL.set(false);
            ALUOp.setValue("00");
        }

        //addi instruction
        if(usedInstruction.equals("001000")){
            RegDst.set(false);
            ALUSrc.set(true);
            MemToReg.set(false);
            RegWrite.set(true);
            MemRead.set(false);
            MemWrite.set(false);
            Branch.set(false);
            Jump.set(false);
            JAL.set(false);
            ALUOp.setValue("00");
        }

        //sw instruction
        if(usedInstruction.equals("101011")){
            RegDst.set(true);
            ALUSrc.set(true);
            MemToReg.set(false);
            RegWrite.set(false);
            MemRead.set(false);
            MemWrite.set(true);
            Branch.set(false);
            Jump.set(false);
            JAL.set(false);
            ALUOp.setValue("00");
        }

        //beq instruction
        if(usedInstruction.equals("000100")){
            RegDst.set(true);
            ALUSrc.set(false);
            MemToReg.set(false);
            RegWrite.set(false);
            MemRead.set(false);
            MemWrite.set(false);
            Branch.set(true);
            Jump.set(false);
            JAL.set(false);
            ALUOp.setValue("01");
        }

        //jumping shit
        if(usedInstruction.equals("000010") ){
            RegDst.set(true);
            ALUSrc.set(false);
            MemToReg.set(false);
            RegWrite.set(false);
            MemRead.set(false);
            MemWrite.set(false);
            Branch.set(true);
            Jump.set(true);
            JAL.set(false);
            ALUOp.setValue("01");
        }
        //jal
        if(usedInstruction.equals("000011")){
            RegDst.set(true);
            ALUSrc.set(false);
            MemToReg.set(false);
            RegWrite.set(true);
            MemRead.set(false);
            MemWrite.set(false);
            Branch.set(true);
            Jump.set(true);
            JAL.set(true);
            ALUOp.setValue("01");
        }
    }

    public static void control(String instruction){

    }


    public boolean isRegDst() {
        return RegDst.get();
    }

    public BooleanProperty regDstProperty() {
        return RegDst;
    }

    public boolean isJump() {
        return Jump.get();
    }

    public BooleanProperty jumpProperty() {
        return Jump;
    }

    public boolean isBranch() {
        return Branch.get();
    }

    public  BooleanProperty branchProperty() {
        return Branch;
    }

    public  boolean isMemRead() {
        return MemRead.get();
    }

    public  BooleanProperty memReadProperty() {
        return MemRead;
    }

    public  boolean isMemToReg() {
        return MemToReg.get();
    }

    public  BooleanProperty memToRegProperty() {
        return MemToReg;
    }

    public  boolean isMemWrite() {
        return MemWrite.get();
    }

    public  BooleanProperty memWriteProperty() {
        return MemWrite;
    }

    public  boolean isALUSrc() {
        return ALUSrc.get();
    }

    public  BooleanProperty ALUSrcProperty() {
        return ALUSrc;
    }

    public  boolean isRegWrite() {
        return RegWrite.get();
    }

    public  BooleanProperty regWriteProperty() {
        return RegWrite;
    }

    public  String getALUOp() {
        return ALUOp.get();
    }

    public  StringProperty ALUOpProperty() {
        return ALUOp;
    }
}
