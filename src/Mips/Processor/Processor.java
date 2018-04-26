package Mips.Processor;

public class Processor {

    public ALUControl aluControl;
    public Mux aluSrcMux,memToRegMux,pcBranchMux,pcBranchJumpMux,regDstMux;
    public ALU alu;
    public Control controlUnit;
    public RegisterFile registerFile;
    public Adder adder;
    public ShiftLeft shiftLeft,jumpShiftLeft;
    public PC pc;
    public DataMemory dataMemory;
    public static int cycles =1;

    public Processor(){
        aluControl = new ALUControl();
        aluSrcMux = new Mux();
        pcBranchJumpMux = new Mux();
        pcBranchMux = new Mux();
        memToRegMux = new Mux();
        regDstMux = new Mux();
        alu = new ALU();
        controlUnit = new Control();
        registerFile = new RegisterFile(this);
        //registerFile.initRegisters();
        shiftLeft = new ShiftLeft();
        jumpShiftLeft = new ShiftLeft();
        dataMemory = new DataMemory(this);
    }

    public void stepOne(){

    }

    public void singleCycle(){

        //System.out.println("current instruction " + InstructionMemory.getInstructionAt(PC.getPc()));
        System.out.println("memory address: " + PC.getPc());
        String currentInstruction = InstructionMemory.getInstructionAt(PC.getPc());
        System.out.println("Current instruction : " + currentInstruction);
        PC.setPc(PC.getPc()+4);
        controlUnit.controlStuff(currentInstruction);
        boolean regDst = controlUnit.isRegDst();
        String registerWrite = regDstMux.mux(currentInstruction.substring(11,16),currentInstruction.substring(16,21),regDst);
        String readData1 = registerFile.readRegister(currentInstruction.substring(6,11));
        String readData2noMux = registerFile.readRegister(currentInstruction.substring(11,16));
        String readData2 = aluSrcMux.mux(readData2noMux,SignExtend.extendSign(currentInstruction.substring(16,32)),controlUnit.isALUSrc());
        System.out.println("Aluop : " + controlUnit.ALUOp);
        String aluCont = aluControl.getALUControlOp(String.valueOf(controlUnit.getALUOp()),currentInstruction.substring(26,32));
        String aluResult = alu.calculate(readData1,readData2,aluCont);
        System.out.println("Alu result : " + aluResult);
        dataMemory.memWrite(Integer.parseInt(aluResult,2),readData2noMux);
        String memRead = dataMemory.memRead(Integer.parseInt(aluResult,2));
        String regWriteData = memToRegMux.mux(aluResult,memRead,controlUnit.isMemToReg());
        registerFile.writeRegister(registerWrite,regWriteData);
        registerFile.printRegisters();

        System.out.println("Number of cycles : " + cycles);
        cycles++;
    }
}
