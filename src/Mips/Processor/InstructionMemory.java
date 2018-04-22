package Mips.Processor;

import javafx.beans.property.StringProperty;

import java.util.HashMap;

public class InstructionMemory {
    private static HashMap<Integer,String> instructions = new HashMap<Integer, String>();

    public static String getInstructionAt(int address) {
        if (instructions.get(address) == null){
            System.out.println("Error : address doesnt exist or has no data ");
            return "";
        }
        //System.out.println("get instruct : " + instructions.get(address));
        return instructions.get(address);
    }

    public static void setInstruction(int address,String instruction) {
        System.out.println("Putting instruction in memory : " + instruction + "At address : " + address);
        InstructionMemory.instructions.put(address,instruction);
        System.out.println("Instruction successfully put in memory  : " + instructions.get(address) + "At address : " + address);
    }
}
