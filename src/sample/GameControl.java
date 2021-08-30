package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.Buffer;

public class GameControl {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;

    public void switchSceneMain(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button submitButton;
    @FXML
    private TextField textField;
    String submittedWord;
    int state = 0;
    String word;

    @FXML
    public void handleSubmit(javafx.event.ActionEvent event) throws IOException {
        if (state == 0) {
            state=1;
            submitButton.setText("Submit");
            updateWord();
            System.out.println(word);
            textField.setText("");
//            String command = "bash src/sample/newGame.sh "+word;
            String command = "echo hello";
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
            Process process = pb.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = stdout.readLine();
            System.out.println(line);
        }else {
//            submittedWord = textField.getText();
//            ProcessBuilder pb = new ProcessBuilder("bash", "-c", submittedWord);
//            Process process = pb.start();
//            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = stdout.readLine();
//            System.out.println(line);
        }
    }

    private void updateWord() throws IOException {
        String getWord = "shuf -n1 src/words/popular";
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", getWord);
        Process process = pb.start();
        BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = stdout.readLine();
        word = line;
    }

    private void playGame() throws IOException {

    }
}
