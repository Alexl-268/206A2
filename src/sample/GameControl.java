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
    String submittedWord;
    int state = 0;  //before start
                    //1 waiting for first try
                    //2waiting for second try
    String word;

    @FXML
    public void handleSubmit(javafx.event.ActionEvent event) throws IOException {
        if (state == 0) {
            submitButton.setText("Submit");
            String command = "bash src/sample/newGame.sh";
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
            Process process = pb.start();
            updateWord();
            speak("spell" + word);
            state = 1;
            textField.setText("");
        } else if (state == 1){
            if (textField.getText().equalsIgnoreCase(word)){
                updateWord();
                speak("correct well done spell      " + word);
            }else{
                speak("incorrect please try again      " + word + word);
                state = 2;
            }
            textField.setText("");
        } else {
            state=1;
            updateWord();
            if (textField.getText().equalsIgnoreCase(word)){
                speak("correct spell      " + word);
            }else{
                speak("incorrect spell      "+ word);
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
        System.out.println(word);
    }

    private void speak(String word) throws IOException {
        String getWord = "echo " + word + " | festival --tts";
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", getWord);
        Process process = pb.start();
    }
}
