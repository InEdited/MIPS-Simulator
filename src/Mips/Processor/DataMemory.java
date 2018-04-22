package Mips.Processor;

import java.util.HashMap;

public class DataMemory {
    private Processor processor;
    HashMap<Integer,String> dataMemory = new HashMap<Integer, String>();

    public DataMemory(Processor processor){
        this.processor = processor;
    }

    public String memRead(int address){
        if(processor.controlUnit.isMemRead())
            if(dataMemory.get(address)!=null)
                return dataMemory.get(address);
            else{
                System.out.println("Error : trying to fetch a non located address in memory");
                return "";
            }
        else
            return "";
    }
    public String memWrite(int address,String data){
        if(processor.controlUnit.isMemWrite()){

                return dataMemory.put(address,data);

        }
        else {
            System.out.println("Error : Trying to write to memory with no access");
            return "";
        }
    }

}
