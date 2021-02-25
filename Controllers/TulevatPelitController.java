package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.Turnaus;

public class TulevatPelitController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text TulevatPelitTxt;

    @FXML
    private TableView<Turnaus> TableView;

    @FXML
    private TableColumn<Turnaus, String> TableColumnPelityyppi;

    @FXML
    private TableColumn<Turnaus, String> TableColumnPäivämäärä;

    @FXML
    private Button PelaaBtn;

    @FXML
    private Button MuokkaaBtn;

    @FXML
    private Button PeruPeliBtn;

    @FXML
    private Button TakaisinBtn;

    @FXML
    void initialize() {
        TableColumnPelityyppi.setCellValueFactory(new PropertyValueFactory<Turnaus, String>("pelityyppi"));
        TableColumnPäivämäärä.setCellValueFactory(new PropertyValueFactory<Turnaus, String>("pvm"));
    }
}
