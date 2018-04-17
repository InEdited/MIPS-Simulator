package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import sample.Assembler.Assembler;

import java.awt.event.ActionEvent;
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
        String assembly;
        assembly = assemblyText.getText();
        int startAddress = Integer.parseInt(startingAddress.getText());
        Assembler.Assemble(assembly,startAddress);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Starting the Program");

    }
}
