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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
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

    int delay;
    Timer timer = new Timer();
    private PrintStream ps ;



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
        imageView.setImage(new Image("Mips/dataPath.png"));
        stepButton.setDisable(true);
        startButton.setDisable(true);
        changeSpeed();
        //processor = new Processor();
        processor = new Processor();
        //PrintStream printStream = new PrintStream(new CustomOutputStream(consoleArea));
        //System.setOut(printStream);
        //System.setErr(printStream);
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
}
