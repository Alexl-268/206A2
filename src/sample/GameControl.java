package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.nio.Buffer;
import java.util.Locale;

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
    @FXML
    public Text label;

    String submittedWord;
    int state = 0;  //before start
                    //1 waiting for first try
                    //2 waiting for second try
    String word;

    @FXML
    public void handleSubmit(javafx.event.ActionEvent event) throws IOException {
        if (state == 0) {
            submitButton.setText("Submit");
            updateWord();
            speak(new String[]{"spell", word});
            state = 1;
            textField.setText("");
        } else if (state == 1){
            if (textField.getText().equalsIgnoreCase(word)){
                mastered();
            }else{
                speak(new String[]{"incorrect please try again ", word, word});
                state = 2;
            }
            textField.setText("");
        } else {
            state=1;
            if (textField.getText().equalsIgnoreCase(word)){
                faulted();
            }else{
                failed();
            }
            textField.setText("");
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

    private void speak(String[] input) throws IOException {
        String line = "";
        for (String part : input){
            line = line + "echo " + part + " | festival --tts;";
        }
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", line);
        Process process = pb.start();
    }

    private void faulted() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 1");
        Process process = pb.start();
        updateWord();
        speak(new String[]{"correct spell ", word});
    }

    private void failed() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 2");
        Process process = pb.start();
        updateWord();
        speak(new String[]{"incorrect spell ", word});
    }

    private void mastered() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 0");
        Process process = pb.start();
        updateWord();
        speak(new String[]{"correct well done","spell", word});
    }
}
