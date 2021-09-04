package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StatController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TableView<Products> statField;              //the stats table itself
    @FXML
    private TableColumn<Products, String> wordCol;      //word column
    @FXML
    private TableColumn<Products, Integer> masterCol;   //master column
    @FXML
    private TableColumn<Products, Integer> faultedCol;  //faulted column
    @FXML
    private TableColumn<Products, Integer> failedCol;   //failed column


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the table with all the columns
        wordCol.setCellValueFactory(new PropertyValueFactory<Products, String>("word"));
        masterCol.setCellValueFactory(new PropertyValueFactory<Products, Integer>("master"));
        faultedCol.setCellValueFactory(new PropertyValueFactory<Products, Integer>("fault"));
        failedCol.setCellValueFactory(new PropertyValueFactory<Products, Integer>("fail"));
        statField.setItems(getProduct());           //get all the lines in stats and display it in the table format
    }

    public ObservableList<Products> getProduct(){
        //use an observable list as the data structure
        ObservableList<Products> products = FXCollections.observableArrayList();

        try {
            FileReader file = new FileReader(new File("src/stats/.stats.txt"));
            BufferedReader read = new BufferedReader(file);         //set up buffer reader to read all the lines in the stats file
            String line = read.readLine();

            while (line != null){
                String[] arr = line.split(" ");             //splits each line by spaces where the last element is the word, 0th 1st and 2nd is the mastered, faulted and failed
                products.add(new Products(arr[arr.length-1], Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2])));      //add the data to the observable list
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }


    //Switch to main button
    public void switchSceneMain(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
