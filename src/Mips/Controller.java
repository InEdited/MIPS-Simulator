package Mips;

import Mips.Processor.PC;
import Mips.Processor.Processor;
import Mips.Processor.Register;
import Mips.Processor.RegisterFile;
import Mips.Utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Mips.Assembler.Assembler;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class  Controller implements Initializable {

    private Processor processor;

    @FXML
    private TextArea assemblyText;

    @FXML
    private TextField startingAddress;

    @FXML
    private Button startButton;

    @FXML
    private Button assembleButton;

    @FXML
    public void startProgram(){
        //System.out.println(Utils.to32BitBinary(2));
        processor.singleCycle();
        //processor.registerFile.printRegisters();
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
        processor = new Processor();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Starting the Program");
        //processor = new Processor();
    }


    public TextField getStartingAddress() {
        return startingAddress;
    }
}
