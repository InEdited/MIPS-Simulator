package Mips;

import Mips.Processor.*;
import Mips.Utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Mips.Assembler.Assembler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class  Controller implements Initializable {

    public Processor processor;
    private DataPathController secondController;

    @FXML
    private TextArea assemblyText;

    @FXML
    private TextField startingAddress;

    @FXML
    private Button stepButton;

    @FXML
    private Button dataPath;

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    private Button assembleButton;

    @FXML
    private Slider speedSlider;

    @FXML
    private Label speedLabel;

    @FXML
    private TextField dataAddress;

    @FXML
    private TextField dataMemory;

    @FXML
    private Button addData;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea consoleArea;

    @FXML
    private Region registersRegion;

    @FXML
    private Region controlUnitRegion;

    @FXML
    private Region aluRegion;

    @FXML
    private Region instructionMemoryRegion;

    @FXML
    private Region pcRegion;

    @FXML
    private Region dataMemoryRegion;



    int delay =1
            ,period=1;
    Timer timer = new Timer();
    private PrintStream ps ;

    public Tooltip registersData = new Tooltip();
    public Tooltip controlUnitData = new Tooltip();
    public Tooltip instructionMemoryData = new Tooltip();
    public Tooltip dataMemoryData = new Tooltip();
    public Tooltip pcData = new Tooltip();
    public Tooltip aluData = new Tooltip();



    @FXML
    public void stepProgram(){
        //System.out.println(Utils.to32BitBinary(2));
       try{
           processor.singleCycle();

       }
       catch (StringIndexOutOfBoundsException e){
           System.out.println(e.fillInStackTrace());
           printStuff("Program Ended.");
           startButton.setDisable(true);
           stepButton.setDisable(true);
           stopProgram();
       }
       catch (Exception e){
           printStuff("Exception happened please check your assembly code");
       }

        //processor.registerFile.printRegisters();
    }

    @FXML
    private void updateToolTips() {
        controlUnitData.setText(controlUnitData());
        aluData.setText(String.valueOf(processor.alu.ALUResult));
        pcData.setText(String.valueOf(PC.getPc()));
        dataMemoryData.setText(String.valueOf(processor.dataMemory.getDataMemory()));
        instructionMemoryData.setText(InstructionMemory.getInstructionAt(PC.getPc()));
    }

    @FXML
    public void startProgram(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stepProgram();
            }
        },delay,period);

    }

    @FXML
    public void assembleProgram() throws IOException {
        String assembly = assemblyText.getText();
        String startAddress;
        if (startingAddress.getText() == null || startingAddress.getText().isEmpty())
            startAddress = String.valueOf(1000);
        else
            startAddress = startingAddress.getText();

        Assembler.Assemble(assembly, Integer.parseInt(startAddress));
        PC.setPc(startingAddress.getText().isEmpty()?1000:Integer.parseInt(startingAddress.getText()));
        processor.registerFile = new RegisterFile(processor);
        stepButton.setDisable(false);
        startButton.setDisable(false);
        Processor.cycles = 0;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        printStuff("Starting the Program");
        imageView.setImage(new Image("Mips/dataPath.png"));
        stepButton.setDisable(true);
        startButton.setDisable(true);
        changeSpeed();
        //processor = new Processor();
        processor = new Processor(this);
        //PrintStream printStream = new PrintStream(new CustomOutputStream(consoleArea));
        //System.setOut(printStream);
        //System.setErr(printStream);


        //data path scene
        //Scene scene = dataPath.getScene();

        Tooltip.install(registersRegion,registersData);
        Tooltip.install(dataMemoryRegion,dataMemoryData);
        Tooltip.install(instructionMemoryRegion,instructionMemoryData);
        Tooltip.install(aluRegion,aluData);
        Tooltip.install(pcRegion,pcData);
        Tooltip.install(controlUnitRegion,controlUnitData);
    }

    @FXML
    public void goToDataPath(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dataPath.fxml"));
        try{Pane root = fxmlLoader.load();
            secondController = fxmlLoader.getController();
            secondController.injectController(Controller.this);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeSpeed(){
        speedLabel.setText(String.valueOf((int)speedSlider.getValue()));
        int speed = (int)speedSlider.getValue();
        if(speed == 100) {
            delay = 1;
            period = 5;
        }
        else {
            delay =  25;
            period = 2500/speed;
        }
    }

    public TextField getStartingAddress() {
        return startingAddress;
    }
    @FXML
    public void stopProgram(){
        timer.cancel();
        timer.purge();
    }

    @FXML
    public void openFile(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);


        if(selectedFile != null){
            try {
                FileReader reader = new FileReader(selectedFile);
                BufferedReader bufferedReader = new BufferedReader(reader);
                while (bufferedReader.readLine()!=null) {
                    assemblyText.setText(assemblyText.getText() + "\n" + bufferedReader.readLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void addData(){
        processor.controlUnit.setMemWrite(true);
        processor.dataMemory.setMemOp("111");
        processor.dataMemory.memWrite(Long.parseLong(dataAddress.getText())
                , String.valueOf(Utils.parseSignedLong(Utils.to32BitBinary(Integer.parseInt(dataMemory.getText())))));
        processor.controlUnit.setMemWrite(false);
        printStuff("Putting the value"+
                                dataMemory + " At the address " + dataAddress);
    }
    public class CustomOutputStream extends OutputStream {
        private TextArea textArea;

        public CustomOutputStream(TextArea textArea) {
            this.textArea = textArea;
        }
        @Override
        public void write(int b) throws IOException {

            //textArea.appendText(String.valueOf((char)b));
        }
    }

    public  void printStuff(String string){
        consoleArea.appendText(string+"\n");
    }



    private String controlUnitData(){
        String stringBuilder = ("RegDst -->" + processor.controlUnit.isRegDst() + "\n") +
                "Jump -->" + processor.controlUnit.isJump() + "\n" +
                "Branch -->" + processor.controlUnit.isBranch() + "\n" +
                "MemRead -->" + processor.controlUnit.isMemRead() + "\n" +
                "MemToReg -->" + processor.controlUnit.isMemToReg() + "\n" +
                "ALUOp -->" + processor.controlUnit.getALUOp() + "\n" +
                "MemWrite -->" + processor.controlUnit.isMemWrite() + "\n" +
                "MemToReg -->" + processor.controlUnit.isMemToReg() + "\n" +
                "JAL -->" + processor.controlUnit.isJAL() + "\n" +
                "ALUSrc -->" + processor.controlUnit.isALUSrc() + "\n" +
                "RegWrite -->" + processor.controlUnit.isRegWrite() + "\n";
        return stringBuilder;
    }
}
