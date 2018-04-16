package sample.Assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Assembler {
    public static void Assemble(String code) throws IOException {
        System.out.println(code);
        BufferedReader bufReader = new BufferedReader(new StringReader(code));
        String codeLine = null;
        while((codeLine=bufReader.readLine())!=null){
            String[] splittedCode = codeLine.split("\\s+");
            for (String thing: splittedCode){
                thing.replace(","," ");
                System.out.println(thing);

            }
        }
    }
}
