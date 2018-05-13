package Mips.Processor;

import Mips.Utils.Utils;

public class Processor {

    public ALUControl aluControl;
    public Mux shamtMux,aluSrcMux,memToRegMux,pcBranchMux,pcBranchJumpMux,regDstMux,regDstJalMux,jalMux,jrMux;
    public ALU alu;
    public Control controlUnit;
    public RegisterFile registerFile;
    public Adder adder;
    public ShiftLeft shiftLeft,jumpShiftLeft;
    public PC pc;
    public DataMemory dataMemory;
    public static int cycles =1;
    public MemoryController memoryController;

    //Instructions
    private String instruction20_16;
    private String instruction15_11;
    private String instruction25_21;
    private String instruction15_0;
    private String insruction25_0;
    private String instruction31_26;
    private String instruction6_10;
    private String instruction5_0;

    //Wires
    private String currentInstruction;
    private String registerWrite;
    private String registerWriteTany;
    private boolean regDst;
    private String readData1;
    private String readData2noMux;
    private String signExtendedThing;
    private String readData2Tany;
    private String readData2;
    private String aluCont;
    private String aluResult;
    private String memRead;
    private String regWriteData;
    private String regWriteDataTany;
    private boolean zeroFlag;
    private boolean beqAndGate;
    boolean jumpFlag;
    private boolean jalFlag;
    private boolean jrFlag;
    private boolean shamtFlag;
    private String shiftedLeftJumpThing;
    private String shiftedLeftSignExtendedThing;
    private String branchAddress;
    private int newPcBeforeJump;
    private int newPc;
    private int pcIncremented;
    private String betweenJRandJump;
     String memOp;




    public Processor(){
        aluControl = new ALUControl();
        aluSrcMux = new Mux();
        shamtMux = new Mux();
        pcBranchJumpMux = new Mux();
        pcBranchMux = new Mux();
        memToRegMux = new Mux();
        regDstMux = new Mux();
        regDstJalMux = new Mux();
        jalMux = new Mux();
        jrMux = new Mux();
        alu = new ALU();
        adder = new Adder();
        controlUnit = new Control();
        registerFile = new RegisterFile(this);
        //registerFile.initRegisters();
        shiftLeft = new ShiftLeft();
        jumpShiftLeft = new ShiftLeft();
        dataMemory = new DataMemory(this);
        memoryController = new MemoryController();

    }

    public void stepOne(){

    }

    public void singleCycle(){

        //System.out.println("current instruction " + InstructionMemory.getInstructionAt(PC.getPc()));
        //System.out.println("memory address: " + PC.getPc());
        currentInstruction = InstructionMemory.getInstructionAt(PC.getPc());
        instruction20_16 = currentInstruction.substring(11, 16);
        instruction6_10 = currentInstruction.substring(21,26);
        instruction15_11 = currentInstruction.substring(16, 21);
        instruction25_21 = currentInstruction.substring(6, 11);
        instruction15_0 = currentInstruction.substring(16, 32);
        insruction25_0 = currentInstruction.substring(6,32);
        instruction31_26 = currentInstruction.substring(0,6);
        instruction5_0 = currentInstruction.substring(26, 32);
        //System.out.println("Current instruction : " + currentInstruction);
        PC.setPc(PC.getPc()+4);
        pcIncremented = PC.getPc();
        controlUnit.controlStuff(currentInstruction);
        memOp  = memoryController.getMemOp(instruction31_26);
        dataMemory.setMemOp(memOp);
        aluCont = aluControl.getALUControlOp(String.valueOf(controlUnit.getALUOp()), instruction5_0);
        regDst = controlUnit.isRegDst();
        jalFlag = controlUnit.isJAL();
        jrFlag = aluControl.isJR();
        jumpFlag = controlUnit.isJump();
        shamtFlag = aluControl.isShamt();
        boolean isMemWrite = controlUnit.isMemWrite();
        readData1 = registerFile.readRegister(instruction25_21);
        readData2noMux = registerFile.readRegister(instruction20_16);
        signExtendedThing = SignExtend.extendSign(instruction15_0);
        readData2 = shamtMux.mux(readData2noMux,instruction6_10,shamtFlag);
        readData2Tany = aluSrcMux.mux(readData2,signExtendedThing,controlUnit.isALUSrc());
        //System.out.println("Aluop : " + controlUnit.ALUOp);
        aluResult = alu.calculate(readData1, readData2Tany,aluCont);
        System.out.println("Alu result : " + aluResult);
        dataMemory.memWrite(Long.parseLong(aluResult,2),readData2noMux);
        memRead = dataMemory.memRead(Utils.parseSignedLong(aluResult));
        zeroFlag = alu.zeroFlag;
        if(zeroFlag && controlUnit.isBranch())
            beqAndGate = true;
        else
            beqAndGate = false;
        shiftedLeftJumpThing = shiftLeft.shiftLeft(insruction25_0);
        shiftedLeftSignExtendedThing = shiftLeft.shiftLeft(signExtendedThing);
        branchAddress = adder.add(Utils.parseSignedInt(shiftedLeftSignExtendedThing), PC.getPc());
        newPcBeforeJump = (int) Long.parseLong(pcBranchMux.mux(Long.toBinaryString(PC.getPc()), branchAddress, beqAndGate),2);
        betweenJRandJump = jrMux.mux(Integer.toBinaryString(newPcBeforeJump),readData1,jrFlag);
        newPc = (int)Long.parseLong(pcBranchJumpMux.mux(betweenJRandJump,shiftedLeftJumpThing, jumpFlag),2);
        PC.setPc(newPc);

        registerWrite = regDstMux.mux(instruction20_16, instruction15_11,regDst);
        registerWriteTany = regDstJalMux.mux(registerWrite,"11111",jalFlag);
        regWriteData = memToRegMux.mux(aluResult,memRead,controlUnit.isMemToReg());
        regWriteDataTany = jalMux.mux(regWriteData,Utils.to32BitBinary(pcIncremented),jalFlag);
        registerFile.writeRegister(registerWriteTany,regWriteDataTany);
        registerFile.printRegisters();

        System.out.println("Number of cycles : " + cycles);
        cycles++;
    }
}
