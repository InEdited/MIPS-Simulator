package Mips;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class DataPathController {
    Controller controller;

    @FXML
    private Label testLabel;



    public void injectController(Controller controller){
        this.controller = controller;
    }

    public DataPathController(){
        testLabel = new Label();
        testLabel.textProperty().setValue(testLabel.getText());
        testLabel.textProperty().bind(controller.processor.aluResultProperty());

    }

/*    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testLabel = new Label();
        testLabel.textProperty().bind(controller.processor.controlUnit.ALUOpProperty());

    }*/

    @FXML
    private void showRegisters(){

    }

    @FXML
    private void showControlUnit(){

    }
}
