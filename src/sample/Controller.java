package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class Controller {

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

    public void switchSceneNew(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
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
