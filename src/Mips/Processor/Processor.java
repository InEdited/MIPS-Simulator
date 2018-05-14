package Mips.Processor;

import Mips.Controller;
import Mips.Utils.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Processor {
    public Controller controller;

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
    private SimpleStringProperty instruction20_16;
    private SimpleStringProperty instruction15_11;
    private SimpleStringProperty instruction25_21;
    private SimpleStringProperty instruction15_0;
    private SimpleStringProperty insruction25_0;
    private SimpleStringProperty instruction31_26;
    private SimpleStringProperty instruction6_10;
    private SimpleStringProperty instruction5_0;

    //Wires
    private SimpleStringProperty currentInstruction;
    private SimpleStringProperty registerWrite;
    private SimpleStringProperty registerWriteTany;
    private BooleanProperty regDst;
    private SimpleStringProperty readData1;
    private SimpleStringProperty readData2noMux;
    private SimpleStringProperty signExtendedThing;
    private SimpleStringProperty readData2Tany;
    private SimpleStringProperty readData2;
    private SimpleStringProperty aluCont;
    private SimpleStringProperty aluResult;
    private SimpleStringProperty memRead;
    private SimpleStringProperty regWriteData;
    private SimpleStringProperty regWriteDataTany;
    private boolean zeroFlag;
    private boolean beqAndGate;
    boolean jumpFlag;
    private boolean jalFlag;
    private boolean jrFlag;
    private boolean shamtFlag;
    private SimpleStringProperty shiftedLeftJumpThing;
    private SimpleStringProperty shiftedLeftSignExtendedThing;
    private SimpleStringProperty branchAddress;
    private int newPcBeforeJump;
    private int newPc;
    private int pcIncremented;
    private SimpleStringProperty betweenJRandJump;
     String memOp;




    public Processor(Controller controller){
        this.controller = controller;

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

        instruction20_16 = new SimpleStringProperty(this, "instruction20_16");
        instruction15_11 = new SimpleStringProperty(this, "instruction15_11");
        instruction25_21 = new SimpleStringProperty(this, "instruction25_21");
        instruction15_0 = new SimpleStringProperty(this, "instruction15_0");
        insruction25_0 = new SimpleStringProperty(this, "insruction25_0");
        instruction31_26 = new SimpleStringProperty(this, "instruction31_26");
        instruction6_10 = new SimpleStringProperty(this, "instruction6_10");
        instruction5_0 = new SimpleStringProperty(this, "instruction5_0");
        currentInstruction = new SimpleStringProperty(this, "currentInstruction");
        registerWrite = new SimpleStringProperty(this, "registerWrite");
        registerWriteTany = new SimpleStringProperty(this, "registerWriteTany");
        regDst = new SimpleBooleanProperty();
        readData1 = new SimpleStringProperty(this, "readData1");
        readData2noMux = new SimpleStringProperty(this, "readData2noMux");
        signExtendedThing = new SimpleStringProperty(this, "signExtendedThing");
        readData2Tany = new SimpleStringProperty(this, "readData2Tany");
        readData2 = new SimpleStringProperty(this, "readData2");
        aluCont = new SimpleStringProperty(this, "aluCont");
        aluResult = new SimpleStringProperty(this, "aluResult");
        memRead = new SimpleStringProperty(this, "memRead");
        regWriteData = new SimpleStringProperty(this, "regWriteData");
        regWriteDataTany = new SimpleStringProperty(this, "regWriteDataTany");
        shiftedLeftJumpThing = new SimpleStringProperty(this, "shiftedLeftJumpThing");
        shiftedLeftSignExtendedThing = new SimpleStringProperty(this, "shiftedLeftSignExtendedThing");
        branchAddress = new SimpleStringProperty(this, "branchAddress");
        betweenJRandJump = new SimpleStringProperty(this, "betweenJRandJump");
    }

    public void stepOne(){

    }

    public void singleCycle(){

        //System.out.println("current instruction " + InstructionMemory.getInstructionAt(PC.getPc()));
        //System.out.println("memory address: " + PC.getPc());
        currentInstruction.set(InstructionMemory.getInstructionAt(PC.getPc()));
        instruction20_16.set(currentInstruction.get().substring(11, 16));
        instruction6_10.set(currentInstruction.get().substring(21, 26));
        instruction15_11.set(currentInstruction.get().substring(16, 21));
        instruction25_21.set(currentInstruction.get().substring(6, 11));
        instruction15_0.set(currentInstruction.get().substring(16, 32));
        insruction25_0.set(currentInstruction.get().substring(6, 32));
        instruction31_26.set(currentInstruction.get().substring(0, 6));
        instruction5_0.set(currentInstruction.get().substring(26, 32));
        //System.out.println("Current instruction : " + currentInstruction);
        PC.setPc(PC.getPc()+4);
        pcIncremented = PC.getPc();
        controlUnit.controlStuff(currentInstruction.get());
        memOp  = memoryController.getMemOp(instruction31_26.get());
        dataMemory.setMemOp(memOp);
        aluCont.set(aluControl.getALUControlOp(String.valueOf(controlUnit.getALUOp()), instruction5_0.get()));
        regDst.set(controlUnit.isRegDst());
        jalFlag = controlUnit.isJAL();
        jrFlag = aluControl.isJR();
        jumpFlag = controlUnit.isJump();
        shamtFlag = aluControl.isShamt();
        boolean isMemWrite = controlUnit.isMemWrite();
        readData1.set(registerFile.readRegister(instruction25_21.get()));
        readData2noMux.set(registerFile.readRegister(instruction20_16.get()));
        signExtendedThing.set(SignExtend.extendSign(instruction15_0.get()));
        readData2.set(shamtMux.mux(readData2noMux.get(), instruction6_10.get(), shamtFlag));
        readData2Tany.set(aluSrcMux.mux(readData2.get(), signExtendedThing.get(), controlUnit.isALUSrc()));
        //System.out.println("Aluop : " + controlUnit.ALUOp);
        aluResult.set(alu.calculate(readData1.get(), readData2Tany.get(), aluCont.get()));
        System.out.println("Alu result : " + aluResult.get());
        dataMemory.memWrite(Long.parseLong(aluResult.get(),2), readData2noMux.get());
        memRead.set(dataMemory.memRead(Utils.parseSignedLong(aluResult.get())));
        zeroFlag = alu.zeroFlag;
        if(zeroFlag && controlUnit.isBranch()) {
            beqAndGate = true;
        }
        else{
            beqAndGate = false;
        }

        shiftedLeftJumpThing.set(shiftLeft.shiftLeft(insruction25_0.get()));
        shiftedLeftSignExtendedThing.set(shiftLeft.shiftLeft(signExtendedThing.get()));
        branchAddress.set(adder.add(Utils.parseSignedInt(shiftedLeftSignExtendedThing.get()), PC.getPc()));
        newPcBeforeJump = (int) Long.parseLong(pcBranchMux.mux(Long.toBinaryString(PC.getPc()), branchAddress.get(), beqAndGate),2);
        betweenJRandJump.set(jrMux.mux(Integer.toBinaryString(newPcBeforeJump), readData1.get(), jrFlag));
        newPc = (int)Long.parseLong(pcBranchJumpMux.mux(betweenJRandJump.get(), shiftedLeftJumpThing.get(), jumpFlag),2);
        PC.setPc(newPc);

        registerWrite.set(regDstMux.mux(instruction20_16.get(), instruction15_11.get(), regDst.get()));
        registerWriteTany.set(regDstJalMux.mux(registerWrite.get(), "11111", jalFlag));
        regWriteData.set(memToRegMux.mux(aluResult.get(), memRead.get(), controlUnit.isMemToReg()));
        regWriteDataTany.set(jalMux.mux(regWriteData.get(), Utils.to32BitBinary(pcIncremented), jalFlag));
        registerFile.writeRegister(registerWriteTany.get(), regWriteDataTany.get());
        registerFile.printRegisters();

        controller.printStuff("Number of cycles : " + cycles );
        cycles++;
    }




    //==================================
    //     GETTERS AND SETTERS
    //==================================


    public ALUControl getAluControl() {
        return aluControl;
    }

    public Mux getShamtMux() {
        return shamtMux;
    }

    public Mux getAluSrcMux() {
        return aluSrcMux;
    }

    public Mux getMemToRegMux() {
        return memToRegMux;
    }

    public Mux getPcBranchMux() {
        return pcBranchMux;
    }

    public Mux getPcBranchJumpMux() {
        return pcBranchJumpMux;
    }

    public Mux getRegDstMux() {
        return regDstMux;
    }

    public Mux getRegDstJalMux() {
        return regDstJalMux;
    }

    public Mux getJalMux() {
        return jalMux;
    }

    public Mux getJrMux() {
        return jrMux;
    }

    public ALU getAlu() {
        return alu;
    }

    public Control getControlUnit() {
        return controlUnit;
    }

    public RegisterFile getRegisterFile() {
        return registerFile;
    }

    public Adder getAdder() {
        return adder;
    }

    public ShiftLeft getShiftLeft() {
        return shiftLeft;
    }

    public ShiftLeft getJumpShiftLeft() {
        return jumpShiftLeft;
    }

    public PC getPc() {
        return pc;
    }

    public DataMemory getDataMemory() {
        return dataMemory;
    }

    public static int getCycles() {
        return cycles;
    }

    public MemoryController getMemoryController() {
        return memoryController;
    }

    public String getInstruction20_16() {
        return instruction20_16.get();
    }

    public SimpleStringProperty instruction20_16Property() {
        return instruction20_16;
    }

    public String getInstruction15_11() {
        return instruction15_11.get();
    }

    public SimpleStringProperty instruction15_11Property() {
        return instruction15_11;
    }

    public String getInstruction25_21() {
        return instruction25_21.get();
    }

    public SimpleStringProperty instruction25_21Property() {
        return instruction25_21;
    }

    public String getInstruction15_0() {
        return instruction15_0.get();
    }

    public SimpleStringProperty instruction15_0Property() {
        return instruction15_0;
    }

    public String getInsruction25_0() {
        return insruction25_0.get();
    }

    public SimpleStringProperty insruction25_0Property() {
        return insruction25_0;
    }

    public String getInstruction31_26() {
        return instruction31_26.get();
    }

    public SimpleStringProperty instruction31_26Property() {
        return instruction31_26;
    }

    public String getInstruction6_10() {
        return instruction6_10.get();
    }

    public SimpleStringProperty instruction6_10Property() {
        return instruction6_10;
    }

    public String getInstruction5_0() {
        return instruction5_0.get();
    }

    public SimpleStringProperty instruction5_0Property() {
        return instruction5_0;
    }

    public String getCurrentInstruction() {
        return currentInstruction.get();
    }

    public SimpleStringProperty currentInstructionProperty() {
        return currentInstruction;
    }

    public String getRegisterWrite() {
        return registerWrite.get();
    }

    public SimpleStringProperty registerWriteProperty() {
        return registerWrite;
    }

    public String getRegisterWriteTany() {
        return registerWriteTany.get();
    }

    public SimpleStringProperty registerWriteTanyProperty() {
        return registerWriteTany;
    }

    public boolean isRegDst() {
        return regDst.get();
    }

    public BooleanProperty RegDstProperty(){
        return regDst;
    }

    public String getReadData1() {
        return readData1.get();
    }

    public SimpleStringProperty readData1Property() {
        return readData1;
    }

    public String getReadData2noMux() {
        return readData2noMux.get();
    }

    public SimpleStringProperty readData2noMuxProperty() {
        return readData2noMux;
    }

    public String getSignExtendedThing() {
        return signExtendedThing.get();
    }

    public SimpleStringProperty signExtendedThingProperty() {
        return signExtendedThing;
    }

    public String getReadData2Tany() {
        return readData2Tany.get();
    }

    public SimpleStringProperty readData2TanyProperty() {
        return readData2Tany;
    }

    public String getReadData2() {
        return readData2.get();
    }

    public SimpleStringProperty readData2Property() {
        return readData2;
    }

    public String getAluCont() {
        return aluCont.get();
    }

    public SimpleStringProperty aluContProperty() {
        return aluCont;
    }

    public String getAluResult() {
        return aluResult.get();
    }

    public SimpleStringProperty aluResultProperty() {
        return aluResult;
    }

    public String getMemRead() {
        return memRead.get();
    }

    public SimpleStringProperty memReadProperty() {
        return memRead;
    }

    public String getRegWriteData() {
        return regWriteData.get();
    }

    public SimpleStringProperty regWriteDataProperty() {
        return regWriteData;
    }

    public String getRegWriteDataTany() {
        return regWriteDataTany.get();
    }

    public SimpleStringProperty regWriteDataTanyProperty() {
        return regWriteDataTany;
    }

    public boolean isZeroFlag() {
        return zeroFlag;
    }

    public boolean isBeqAndGate() {
        return beqAndGate;
    }

    public boolean isJumpFlag() {
        return jumpFlag;
    }

    public boolean isJalFlag() {
        return jalFlag;
    }

    public boolean isJrFlag() {
        return jrFlag;
    }

    public boolean isShamtFlag() {
        return shamtFlag;
    }

    public String getShiftedLeftJumpThing() {
        return shiftedLeftJumpThing.get();
    }

    public SimpleStringProperty shiftedLeftJumpThingProperty() {
        return shiftedLeftJumpThing;
    }

    public String getShiftedLeftSignExtendedThing() {
        return shiftedLeftSignExtendedThing.get();
    }

    public SimpleStringProperty shiftedLeftSignExtendedThingProperty() {
        return shiftedLeftSignExtendedThing;
    }

    public String getBranchAddress() {
        return branchAddress.get();
    }

    public SimpleStringProperty branchAddressProperty() {
        return branchAddress;
    }

    public int getNewPcBeforeJump() {
        return newPcBeforeJump;
    }

    public int getNewPc() {
        return newPc;
    }

    public int getPcIncremented() {
        return pcIncremented;
    }

    public String getBetweenJRandJump() {
        return betweenJRandJump.get();
    }

    public SimpleStringProperty betweenJRandJumpProperty() {
        return betweenJRandJump;
    }

    public String getMemOp() {
        return memOp;
    }
}
