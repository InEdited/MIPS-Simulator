package Mips.Assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import Mips.Controller;
import Mips.Processor.InstructionMemory;
import Mips.Utils.*;

public class Assembler {

    private static final String sixZeros = "000000";
    private static final String fiveZeros = "00000";
    private static final String sixteenZeroes = "00000000000000000";
    private static HashMap<String,String> registers = new HashMap<String,String>();
    private static HashMap<String,String> rInstructions = new HashMap<String,String>();
    private static HashMap<String,String> iInstructions = new HashMap<String,String>();
    private static HashMap<String,String> jInstructions = new HashMap<String,String>();


    public static void Assemble(String code,int currentAddress) throws IOException {
        initMaps();
        int baseAddress = currentAddress;
        //System.out.println(code);
        String codeUpdated = code.replaceAll(","," ")
                .replaceAll("[()]", " ")
                .replaceAll(":","\n")
                .replaceAll("\n+","\n");
        System.out.println(codeUpdated);
        BufferedReader bufReader = new BufferedReader(new StringReader(codeUpdated));
        String codeLine = null;
        while((codeLine=bufReader.readLine())!=null){
            String[] splittedCode = codeLine.trim().split("\\s+");
            for (String thing: splittedCode){
                System.out.println(thing);
            }
            for(Map.Entry entry:rInstructions.entrySet()) {
                if (entry.getKey().equals(splittedCode[0])){
                    System.out.println(assembleTypeR(splittedCode));
                    InstructionMemory.setInstruction(currentAddress, assembleTypeR(splittedCode));
                    //System.out.println(InstructionMemory.getInstructionAt(currentAddress));
                    currentAddress += 4;
                }
            }
            for(Map.Entry entry:iInstructions.entrySet()){
                if(entry.getKey().equals(splittedCode[0])) {
                    System.out.println(assembleTypeI(splittedCode));
                    InstructionMemory.setInstruction(currentAddress, assembleTypeI(splittedCode));
                    currentAddress += 4;
                }
            }
            for(Map.Entry entry:jInstructions.entrySet()){
                if(entry.getKey().equals(splittedCode[0])) {
                    System.out.println(weScrewedBoiJump(splittedCode, codeUpdated, baseAddress,currentAddress));
                    InstructionMemory.setInstruction(currentAddress,weScrewedBoiJump(splittedCode, codeUpdated, baseAddress,currentAddress));
                    currentAddress += 4;

                }
            }
        }
    }

    private static String weScrewedBoiJump(String[] codeLine,String code,int address,int currentAddress) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(code));
        if(codeLine[0].equals("j") || codeLine[0].equals("jal")){
            int lineNum = 0;
            String readerNextLine = null;
            while ((readerNextLine=bufferedReader.readLine())!=null){
                if(readerNextLine.split("\\s+").length>=2) {
                    lineNum += 4;
                }
                if (readerNextLine.split("\\s+")[0].equals(codeLine[1])){
                    address += lineNum;
                    break;
                }
            }
            builder.append(jInstructions.get(codeLine[0]));
            //builder.append(registers.get(codeLine[1]));
            builder.append(Utils.to26BitBinary(address/4));
        }

        if(codeLine[0].equals("beq")){
            int lineNum = address-4;
            String readerNextLine = null;
            while ((readerNextLine=bufferedReader.readLine())!=null){
                if(readerNextLine.split("\\s+").length>=2) {
                    lineNum += 4;
                }
                if (readerNextLine.split("\\s+")[0].equals(codeLine[3])){
                    address = (lineNum - currentAddress)/4;
                    break;
                }
            }
            builder.append(jInstructions.get(codeLine[0]));
            builder.append(registers.get(codeLine[1]));
            builder.append(registers.get(codeLine[2]));
            builder.append(Utils.to16BitBinary(address));
        }
        if(codeLine[0].equals("jr")){
            builder.append(sixZeros);
            builder.append(registers.get(codeLine[1]));
            builder.append("000000000000000");
            builder.append(jInstructions.get(codeLine[0]));
        }

        return builder.toString();
    }

    private static String assembleTypeR(String[] code){
        StringBuilder builder = new StringBuilder();
        builder.append(sixZeros);
        String rd = registers.get(code[1]);
        String rs = registers.get(code[2]);
        String rt = registers.get(code[3]);
        builder.append(rs);
        builder.append(rt);
        builder.append(rd);
        builder.append(fiveZeros);
        builder.append(rInstructions.get(code[0]));
        //System.out.println(builder.toString());
        return builder.toString();
    }

    private static String assembleTypeI(String[] code){
        StringBuilder builder = new StringBuilder();
        builder.append(iInstructions.get(code[0]));
        String rs,rt,immediate;
        if(code[0].equals("lw") || code[0].equals("sw") || code[0].equals("lb") || code[0].equals("lbu") || code[0].equals("sb")){
         rt = registers.get(code[1]);
         immediate = Utils.to16BitBinary(Integer.parseInt(code[2]));
         rs = registers.get(code[3]);
        }
        else {
            rt = registers.get(code[1]);
            rs = registers.get(code[2]);

            immediate = Utils.to16BitBinary(Integer.parseInt(code[3]));
        }
        builder.append(rs);
        builder.append(rt);
        builder.append(immediate);
        return builder.toString();
    }

    private static void initMaps(){
        //Initializing Registers
        registers.put("$zero","00000");
        registers.put("$0","00000");
        registers.put("$at","00001");
        registers.put("$v0","00010");
        registers.put("$v1","00011");
        registers.put("$a0","00100");
        registers.put("$a1","00101");
        registers.put("$a2","00110");
        registers.put("$a3","00111");
        registers.put("$t0","01000");
        registers.put("$t1","01001");
        registers.put("$t2","01010");
        registers.put("$t3","01011");
        registers.put("$t4","01100");
        registers.put("$t5","01101");
        registers.put("$t6","01110");
        registers.put("$t7","01111");
        registers.put("$s0","10000");
        registers.put("$s1","10001");
        registers.put("$s2","10010");
        registers.put("$s3","10011");
        registers.put("$s4","10100");
        registers.put("$s5","10101");
        registers.put("$s6","10110");
        registers.put("$s7","10111");
        registers.put("$t8","11000");
        registers.put("$t9","11001");
        registers.put("$k0","11010");
        registers.put("$k1","11011");
        registers.put("$gp","11100");
        registers.put("$sp","11101");
        registers.put("$fp","11110");
        registers.put("$ra","11111");

        //Initializing R type Instructions
        rInstructions.put("add","100000");
        rInstructions.put("sll","000000");
        rInstructions.put("nor","100011");
        rInstructions.put("slt","101010");

        //Initializing I type Instructions
        iInstructions.put("addi","001000");
        iInstructions.put("lw","100011");
        iInstructions.put("sw","101011");
        iInstructions.put("lb","100000");
        iInstructions.put("lbu","100100");
        iInstructions.put("sb","101000");
        iInstructions.put("slti","001010");
        //iInstructions.put("","");

        //Initializing J type Instructions
        jInstructions.put("beq","000100");
        jInstructions.put("j","000010");
        jInstructions.put("jal","000011");
        jInstructions.put("jr","001000");

    }
}
