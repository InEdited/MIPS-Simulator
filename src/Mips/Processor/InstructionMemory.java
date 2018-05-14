package Mips.Processor;

import javafx.beans.property.StringProperty;

import java.util.HashMap;

public class InstructionMemory {
    private static HashMap<Integer,String> instructions = new HashMap<Integer, String>();
    private static HashMap<Integer,String> instructionsString = new HashMap<Integer, String>();
    private static String currentInstruction = instructionsString.get(1000);

    public static String getInstructionAt(int address) {
        if (instructions.get(address) == null){
            System.out.println("Error : address doesnt exist or has no data ");
            return "";
        }
        currentInstruction = instructionsString.get(address);
        //System.out.println("Getting instruction : " + instructionsString.get(address) +
        //                    "At : " +address);
        return instructions.get(address);
    }

    public static void setInstruction(int address,String instruction,String instructionString) {
        //System.out.println("Putting instruction in memory : " + instruction + "At address : " + address);
        InstructionMemory.instructions.put(address,instruction);
        instructionsString.put(address,instructionString);
        //System.out.println("Instruction successfully put in memory  : " + instructions.get(address) + "At address : " + address);
    }

    public static String getCurrentInstruction(){
        return currentInstruction;
    }
}
