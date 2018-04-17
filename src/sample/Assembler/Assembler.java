package sample.Assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Assembler {

    final String sixZeros = "000000";
    final String fiveZeros = "00000";
    Hashmap<String,String> registers = new Hashmap<String,String>();
    Hashmap<String,String> rInstructions = new Hashmap<String,String>();
    Hashmap<String,String> iInstructions = new Hashmap<String,String>();

    public static void Assemble(String code,int currentAddress) throws IOException {
        initMaps();
        System.out.println(code);
        codeUpdated = code.replaceAll(","," ");
        BufferedReader bufReader = new BufferedReader(new StringReader(codeUpdated));
        String codeLine = null;

        while((codeLine=bufReader.readLine())!=null){
            String[] splittedCode = codeLine.split("\\s+");
            for(Map.Entry entry:rInstructions.entrySet()){
                if(entry.equals(splittedCode[0]))
                    assembleTypeR(splittedCode);
            }
            for (String thing: splittedCode){
                System.out.println(thing);

            }
            currentAddress++;
        }
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
        return builder.toString();
    }

    private static void initMaps(){
        //Initializing Registers
        registers.put("$zero","00000");
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
    }
}
