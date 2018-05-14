package Mips;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainProgram.fxml"));
        primaryStage.setTitle("MIPS Simulator");

        primaryStage.setScene(new Scene(root, root.prefWidth(1366), root.prefHeight(768)));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
