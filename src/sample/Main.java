package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spelling Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        String setUpFiles = "touch src/stats/.stats.txt src/stats/.failed.txt src/stats/.faulted.txt";
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", setUpFiles);
        Process process = pb.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
