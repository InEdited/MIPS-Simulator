package Mips.Processor;

public class RegisterFile {
    private static Register[] registers = new Register[32];

    private Processor processor;

    public RegisterFile(Processor processor){
        this.processor = processor;
        registers[0] = new Register("$zero","00000");
        //registers[1] = new Register("$0","00000");
        registers[1] = new Register("$at","00001");
        registers[2] = new Register("$v0","00010");
        registers[3] = new Register("$v1","00011");
        registers[4] = new Register("$a0","00100");
        registers[5] = new Register("$a1","00101");
        registers[6] = new Register("$a2","00110");
        registers[7] = new Register("$a3","00111");
        registers[8] = new Register("$t0","01000");
        registers[9] = new Register("$t1","01001");
        registers[10] = new Register("$t2","01010");
        registers[11] = new Register("$t3","01011");
        registers[12] = new Register("$t4","01100");
        registers[13] = new Register("$t5","01101");
        registers[14] = new Register("$t6","01110");
        registers[15] = new Register("$t7","01111");
        registers[16] = new Register("$s0","10000");
        registers[17] = new Register("$s1","10001");
        registers[18] = new Register("$s2","10010");
        registers[19] = new Register("$s3","10011");
        registers[20] = new Register("$s4","10100");
        registers[21] = new Register("$s5","10101");
        registers[22] = new Register("$s6","10110");
        registers[23] = new Register("$s7","10111");
        registers[24] = new Register("$t8","11000");
        registers[25] = new Register("$t9","11001");
        registers[26] = new Register("$k0","11010");
        registers[27] = new Register("$k1","11011");
        registers[28] = new Register("$gp","11100");
        registers[29] = new Register("$sp","11101");
        registers[30] = new Register("$fp","11110");
        registers[31] = new Register("$ra","11111");
    }

    public String readRegister(String code){
        System.out.println("Reading register : " + registers[Integer.parseInt(code,2)].getName() + " Value : " + registers[Integer.parseInt(code,2)].getValue() );
        return registers[Integer.parseInt(code,2)].getValue();
    }

    public void writeRegister(String code, String value){
        if(processor.controlUnit.isRegWrite())
            registers[Integer.parseInt(code,2)].setValue(value);
    }

    public void printRegisters() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <32 ; i++) {
            stringBuilder.append(registers[i].printReg());
            stringBuilder.append("\n");
        }

        processor.controller.registersData.setText(stringBuilder.toString());
    }
}
