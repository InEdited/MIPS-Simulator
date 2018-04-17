package Mips;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Mips.Assembler.Assembler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Starting the Program");

    }


    public TextField getStartingAddress() {
        return startingAddress;
    }
}
