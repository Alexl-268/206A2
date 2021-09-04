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
    public Button statsButton;
    public Button clearButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;
    static int gametype = 0;
    private int sure = 0;

    public void switchSceneNew(javafx.event.ActionEvent event) throws IOException {
        gametype = 0;
        root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchSceneReview(javafx.event.ActionEvent event) throws IOException {
        gametype = 1;
        root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchClearStats(javafx.event.ActionEvent event) throws IOException {
        if (sure == 0){
            clearButton.setText("Are you Sure?");
            sure = 1;
        }else if (sure == 1){
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "rm -r src/stats/; mkdir src/stats ;touch src/stats/.stats.txt src/stats/.failed.txt src/stats/.faulted.txt");
            Process process = pb.start();
            clearButton.setText("Cleared");
            clearButton.setDisable(true);
            sure = 0;
        }
    }

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
