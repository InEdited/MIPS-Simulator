package Mips.Processor;

import java.util.HashMap;

public class DataMemory {
    private Processor processor;
    HashMap<Long, String> dataMemory = new HashMap<Long, String>();

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
    public void memWrite(long address, String data){
        if(processor.controlUnit.isMemWrite()){

            if(address%4==0) {
                dataMemory.put(address, data);
            }

            else{
                String temp = data.substring(24,32);
                String x = dataMemory.get(address);
                int offset = (int) (address%4);
                //String fullAddress = dataMemory.get(address-offset);
                if(x==null){
                    x="00000000000000000000000000000000";
                }
                if(offset==0)
                    temp = x.substring(0,24).concat(temp);
                else if (offset == 1)
                    temp = x.substring(0,16)+temp+x.substring(24,32);
                else if (offset == 2)
                    temp = x.substring(0,8)+temp+x.substring(16,32);
                else if (offset==3)
                    temp = temp + x.substring(8,32);
                dataMemory.put(address,temp);
            }


        }
        else {
            System.out.println("Error : Trying to write to memory with no access");

        }
    }

}
