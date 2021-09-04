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
    int state = 0;  //0 - before start
                    //1 - waiting for first try
                    //2 - waiting for second try
    String word;    //stores the word to be read

    @FXML
    public void handleSubmit(javafx.event.ActionEvent event) throws IOException, InterruptedException {
        if (state == 0) {                               //the user have to press ready to start the programme
            submitButton.setText("Submit");
            speakWord("", "once");
            state = 1;                                  //change to state 1 - waiting for first try
            textField.setText("");
        } else if (state == 1){
            if (textField.getText().equalsIgnoreCase(word)){        //if the user gets the first try correct, call the mastered method
                mastered();
            }else{                                                  //otherwise speak the same word twice and enter state 2
                speakWord("incorrect please try again", "twice");
                state = 2;
            }
            textField.setText("");
        } else {
            state=1;
            if (textField.getText().equalsIgnoreCase(word)){        // if the attempt is correct, call faulted
                faulted();
            }else{                                                  // if the attempt is failed again, call failed
                failed();
            }
            textField.setText("");
        }
    }


    private void speakWord(String speakLine, String times) throws IOException {
        //surround with try catch to catch non existing words
        try {
            if (times.equalsIgnoreCase("once")){            //note this method updates a new word if it is only requited to speak the word once
                String getWord = "shuf -n1 src/words/popular";
                if (gametype == 1){                                     //get word from failed if the gametype is 1
                    getWord = "shuf -n1 src/stats/.failed.txt;";
                }
                Thread.sleep(100);                                      //slight delay to wait for txt files to update
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", getWord);
                Process process = pb.start();
                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));

                word = stdout.readLine();                           //update word and throws error if nothing is found
                word.equalsIgnoreCase("null");          //note this accepts the string "null" but not null

                speak(new String[]{speakLine,"spell", word});       //speak spell word with a pause in between
            } else{                                                 //the programme does not need to update word if it needs to speak twice
                speak(new String[]{speakLine,"spell", word, word});
            }
        } catch (Exception exception){                              //if an exception is thrown, this means that nothing is found in the words list
            speak(new String[]{"no more failed words"});            //disable the button and change the label to notify the user HE IS A WINNER to leave the game
            submitButton.setDisable(true);
            submitButton.setText("You are a winner");
            label.setText("Congrats! You have passed all words!");
        }
    }

    private void speak(String[] input) throws IOException {
        String line = "";
        for (String part : input){                                  //create a string with multiple commands so that the programme speak with pauses in between
            line = line + "echo " + part + " | festival --tts;";
        }
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", line);         //this will sound nicer rather than speaking everything in one go
        Process process = pb.start();
    }

    private void faulted() throws IOException {         //call the newGame.sh bash code to update stats and call speakWord method again with updated word
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 1");
        Process process = pb.start();
        speakWord("correct","once");
    }

    private void failed() throws IOException {          //call the newGame.sh bash code to update stats and call speakWord method again with updated word
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 2");
        Process process = pb.start();
        speakWord("incorrect","once");
    }

    private void mastered() throws IOException {        //call the newGame.sh bash code to update stats and call speakWord method again with updated word
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "bash src/sample/newGame.sh " + word + " 0");
        Process process = pb.start();
        speakWord("correct well done","once");
    }
}
