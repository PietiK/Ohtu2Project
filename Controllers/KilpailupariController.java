package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.Pelaaja;

public class KilpailupariController{

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text EnsimmäinenTxt;

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<Pelaaja, String> TableColmun1;

    @FXML
    private TableColumn<Pelaaja, String> TableColumn2;

    @FXML
    private Button TakaisinBtn;

    @FXML
    private Button SeuraavaBtn;

    @FXML
    void initialize() {
        TableColmun1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
    }
}
