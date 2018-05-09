package Mips.Processor;

public class MemoryController {
    //this is an extenstion component

    public MemoryController(){

    }



    public String getMemOp(String instruction){
        //lb
        if(instruction.equals("100000")){
            return "000";
        }
        //lbu
        if(instruction.equals("100100")){
            return "001";
        }
        //lh
        if(instruction.equals("100001")){
            return "010";
        }
        //lhu
        if(instruction.equals("100101")){
            return "011";
        }
        //lw
        if(instruction.equals("10011")){
            return "100";
        }
        //sb
        if(instruction.equals("101000")){
            return "101";
        }
        //sh
        if(instruction.equals("101001")){
            return "110";
        }
        //sw
        if(instruction.equals("101011")){
            return "111";
        }
        else{
            System.out.println("Error : memory controller error");
            return "";
        }
    }

}
