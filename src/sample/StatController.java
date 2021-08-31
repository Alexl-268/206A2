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
    private TableView<Products> statField;
    @FXML
    private TableColumn<Products, String> wordCol;
    @FXML
    private TableColumn<Products, Integer> masterCol;
    @FXML
    private TableColumn<Products, Integer> faultedCol;
    @FXML
    private TableColumn<Products, Integer> failedCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordCol.setCellValueFactory(new PropertyValueFactory<Products, String>("word"));
        masterCol.setCellValueFactory(new PropertyValueFactory<Products, Integer>("master"));
        faultedCol.setCellValueFactory(new PropertyValueFactory<Products, Integer>("fault"));
        failedCol.setCellValueFactory(new PropertyValueFactory<Products, Integer>("fail"));

        statField.setItems(getProduct());
    }

    public ObservableList<Products> getProduct(){
        ObservableList<Products> products = FXCollections.observableArrayList();
//        products.add(new Products("word", 1,1,1));


        try {
            FileReader file = new FileReader(new File("src/stats/.stats.txt"));
            BufferedReader read = new BufferedReader(file);
            String line = read.readLine();
            while (line != null){
                String[] arr = line.split(" ");
                System.out.print(arr[0]);
                System.out.print(arr[1]);
                System.out.print(arr[2]);
                System.out.print(arr[3]);
                products.add(new Products(arr[0], Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3])));
                line = read.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return products;


    }

    public void switchSceneMain(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
