package Mips;

import Mips.Processor.PC;
import Mips.Processor.Processor;
import Mips.Processor.Register;
import Mips.Processor.RegisterFile;
import Mips.Utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Mips.Assembler.Assembler;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class  Controller implements Initializable {

    private Processor processor;

    @FXML
    private TextArea assemblyText;

    @FXML
    private TextField startingAddress;

    @FXML
    private Button stepButton;

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

    int delay;
    Timer timer = new Timer();



    @FXML
    public void stepProgram(){
        //System.out.println(Utils.to32BitBinary(2));
       try{
           processor.singleCycle();
       }
       catch (StringIndexOutOfBoundsException e){
           System.out.println(e.fillInStackTrace());
           System.out.println("Program Ended with exception");
           startButton.setDisable(true);
           stepButton.setDisable(true);
           stopProgram();
       }

        //processor.registerFile.printRegisters();
    }

    @FXML
    public void startProgram(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stepProgram();
            }
        },delay,delay);

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
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Starting the Program");
        stepButton.setDisable(true);
        startButton.setDisable(true);
        changeSpeed();
        //processor = new Processor();
        processor = new Processor();

    }

    @FXML
    public void changeSpeed(){
        speedLabel.setText(String.valueOf((int)speedSlider.getValue()));
        int speed = (int)speedSlider.getValue();
        if(speed == 100)
            delay = 1;
        else
            delay = speed *50;
    }

    public TextField getStartingAddress() {
        return startingAddress;
    }
    @FXML
    public void stopProgram(){
        timer.cancel();
        timer.purge();
    }
}
