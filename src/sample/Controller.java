package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class Controller {
    //Game-----------------------------------------------
    public Button statsButton;          //button for showing stats
    public Button clearButton;          //button for clearing
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;
    static int gametype = 0;            //0 = read from popular, 1 = read from failed
    private int sure = 0;               //This make sures the clearing is intended

    //Switch to new game scene with game mode as new game
    public void switchSceneNew(javafx.event.ActionEvent event) throws IOException {
        gametype = 0;
        root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Switch to new game scene with game mode as review mistakes
    public void switchSceneReview(javafx.event.ActionEvent event) throws IOException {
        gametype = 1;
        root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Clare the stats when the user hits the clear button twice
    public void switchClearStats(javafx.event.ActionEvent event) throws IOException {
        if (sure == 0){                                 //the first time the user presses the button
            clearButton.setText("Are you Sure?");
            sure = 1;
        }else if (sure == 1){                           //when hit the second time, the programme resets the following files: stats, failed and faulted
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "rm -r src/stats/; mkdir src/stats ;touch src/stats/.stats.txt src/stats/.failed.txt src/stats/.faulted.txt");
            Process process = pb.start();
            clearButton.setText("Cleared");             //changing the button text and disabling the button
            clearButton.setDisable(true);
            sure = 0;
        }
    }

    //Switch to view stats
    public void switchViewStats(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("viewStats.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void quitApp(javafx.event.ActionEvent event) throws IOException {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

}
