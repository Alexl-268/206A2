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

import javax.crypto.spec.PSource;
import javax.swing.*;
import java.io.*;
import java.nio.Buffer;
import java.util.Locale;

public class GameControl extends Controller{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Button submitButton;
    @FXML
    private TextField textField;
    @FXML
    public Text label;

    public void switchSceneMain(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    String submittedWord;
    int state = 0;  //before start
                    //1 waiting for first try
                    //2 waiting for second try
    String word;
    @FXML
    public void handleSubmit(javafx.event.ActionEvent event) throws IOException, InterruptedException {
        if (state == 0) {
            submitButton.setText("Submit");
            updateWord("", "once");
            state = 1;
            textField.setText("");
        } else if (state == 1){
            if (textField.getText().equalsIgnoreCase(word)){
                mastered();
            }else{
                updateWord("incorrect please try again", "twice");
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


    private void updateWord(String speakLine, String times) throws IOException {

        try {
            if (times.equalsIgnoreCase("once")){
                String getWord = "shuf -n1 src/words/popular";
                if (gametype == 1){
                    getWord = "shuf -n1 src/stats/.failed.txt;";
                }
                Thread.sleep(100);
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", getWord);
                Process process = pb.start();
                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));

                word = stdout.readLine();
                word.equalsIgnoreCase("null");

                speak(new String[]{speakLine,"spell", word});
            } else{
                speak(new String[]{speakLine,"spell", word, word});
            }
        } catch (Exception exception){
            speak(new String[]{"no more failed words"});
            submitButton.setDisable(true);
            submitButton.setText("You are a winner");
            label.setText("Congrats! You have passed all words!");

        }
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
        updateWord("correct","once");
    }

    private void failed() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 2");
        Process process = pb.start();
        updateWord("incorrect","once");
    }

    private void mastered() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 0");
        Process process = pb.start();
        updateWord("correct well done","once");
    }
}
