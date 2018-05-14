package Mips.Processor;

import Mips.Utils.Utils;

import java.util.HashMap;

public class DataMemory {
    private Processor processor;
    HashMap<Long, String> dataMemory = new HashMap<Long, String>();
    String memOp;
    public static final String padding32 = "00000000000000000000000000000000";
    private static final String padding24 = "000000000000000000000000";
    private static final String padding16 = "00000000000000000";


    public DataMemory(Processor processor){
        this.processor = processor;
    }

    public String memRead(long address){
        String returnDis;
        memOp = processor.memOp;
        if(processor.controlUnit.isMemRead()) {
            int offset = Math.toIntExact(address % 4);
            if (dataMemory.get((address-offset))!=null) {
                if (offset == 0) {
                    //lb
                    if(memOp.equals("000")) {
                        returnDis = dataMemory.get(address - offset).substring(24,32);
                        return Utils.to32BitBinary(Utils.parseSignedLong(returnDis));

                    }
                    //lbu
                    if (memOp.equals("001")){
                        returnDis = dataMemory.get(address - offset).substring(24,32);
                        return padding24 + returnDis;

                    }
                    //lh
                    if(memOp.equals("010")){
                        returnDis = dataMemory.get(address - offset).substring(16,32);
                        return Utils.to32BitBinary(Utils.parseSignedLong(returnDis));
                    }
                    //lhu
                    if(memOp.equals("011")){
                        returnDis = dataMemory.get(address - offset).substring(16,32);
                        return padding16 + returnDis;
                    }
                    //lw
                    if(memOp.equals("100")){
                        return dataMemory.get(address);
                    }
                }
                else if (offset == 1) {
                    returnDis = dataMemory.get(address - offset).substring(16,24);
                    if (memOp.equals("000")) {
                        return Utils.to32BitBinary(Utils.parseSignedLong(returnDis));
                    }
                    if (memOp.equals("001")){
                        return padding24 + returnDis;
                    }
                    //lh
                    if(memOp.equals("010")){
                        System.out.println("Problem with the offset using Lh with a non multiple of 2");
                        return padding32;
                    }
                    //lhu
                    if(memOp.equals("011")){
                        System.out.println("Problem with the offset using Lhu with a non multiple of 2");
                        return padding32;
                    }
                    //lw
                    if(memOp.equals("100")){
                        System.out.println("Problem with the offset using LW with a non multiple of 4");
                        return padding32;
                    }
                }
                else if (offset == 2) {
                    //lb
                    if(memOp.equals("000")) {
                        returnDis = dataMemory.get(address - offset).substring(8,16);
                        return Utils.to32BitBinary(Utils.parseSignedLong(returnDis));

                    }
                    //lbu
                    if (memOp.equals("001")){
                        returnDis = dataMemory.get(address - offset).substring(8, 16);
                        return padding24 + returnDis;

                    }
                    //lh
                    if(memOp.equals("010")){
                        returnDis = dataMemory.get(address - offset).substring(0,16);
                        return Utils.to32BitBinary(Utils.parseSignedLong(returnDis));
                    }
                    //lhu
                    if(memOp.equals("011")){
                        returnDis = dataMemory.get(address - offset).substring(0, 16);
                        return padding16 + returnDis;
                    }
                    //lw
                    if(memOp.equals("100")){
                        System.out.println("Problem with the offset using LW with a non multiple of 4");
                        return padding32;
                    }

                }
                else if (offset == 3) {
                    //lb
                    if(memOp.equals("000")) {
                        returnDis = dataMemory.get(address - offset).substring(0, 8);
                        return Utils.to32BitBinary(Utils.parseSignedLong(returnDis));

                    }
                    //lbu
                    if (memOp.equals("001")){
                        returnDis = dataMemory.get(address - offset).substring(0,8);
                        return padding24 + returnDis;

                    }
                    //lh
                    if(memOp.equals("010")){
                        System.out.println("Problem with the offset using Lh with a non multiple of 2");
                        return padding32;
                    }
                    //lhu
                    if(memOp.equals("011")){
                        System.out.println("Problem with the offset using Lhu with a non multiple of 2");
                        return padding32;
                    }
                    //lw
                    if(memOp.equals("100")){
                        System.out.println("Problem with the offset using LW with a non multiple of 4");
                        return padding32;
                    }
                }
                else {
                    returnDis = padding32;
                    System.out.println("Error : Memory fetching a byte with offset ");
                }
                //String returnee = Utils.to32BitBinary(Utils.parseSignedLong(returnDis));
                //return returnee;

            }
            else {
                System.out.println("Error : trying to fetch a non located address in memory");

            }
        }
        else
            return padding32;
        return padding32;
    }
    public void memWrite(long address, String data){
        if(processor.controlUnit.isMemWrite()){
            dataMemory.putIfAbsent(address, padding32);
            long offset = address % 4;
            String makeDis = padding32;

            //Sb
            if(memOp.equals("101")){
                String addDis = data.substring(24,32);
                String temp = dataMemory.get(address-offset);
                if(offset==0){
                    makeDis = temp.substring(0,24) + addDis ;
                }
                if(offset==1){
                    makeDis = temp.substring(0,16) + addDis + temp.substring(24,32);
                }
                if(offset==2){
                    makeDis = temp.substring(0,8) + addDis + temp.substring(16,32);
                }
                if(offset==3){
                    makeDis = addDis + temp.substring(8,32);
                }
            }
            if(memOp.equals("110")){
                String addDis = data.substring(16,32);
                String temp = dataMemory.get(address-offset);
                if(offset==0){
                    makeDis = temp.substring(0,16) + addDis;
                }
                if(offset==2){
                    makeDis = addDis + temp.substring(16,32);
                }
                else{
                    System.out.println("Error writing in memory offsetting not correct with SH");
                }
            }
            if(memOp.equals("111")){
                makeDis = data;
            }

            dataMemory.put((address-offset),makeDis);


        }
        else {
            //System.out.println("Error : Trying to write to memory with no access");

        }
    }

    public String getMemOp() {
        return memOp;
    }

    public void setMemOp(String memOp) {
        this.memOp = memOp;
    }

    public HashMap<Long, String> getDataMemory() {
        return dataMemory;
    }
}
