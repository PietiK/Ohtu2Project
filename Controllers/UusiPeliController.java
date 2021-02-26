package Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Pelaaja;

public class UusiPeliController {

    @FXML
    private AnchorPane Anchorpane;

    @FXML
    private Text LisääPelaajiaTxt;

    @FXML
    private TextField TextField;

    @FXML
    private Button LisääBtn;

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<Pelaaja, String> TablecolumPelaajat;

    @FXML
    private TableColumn<?, ?> TableColumnLuku;

    @FXML
    private Button ValmisBtndrop;

    @FXML
    private MenuButton ValitsePeliBtn;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private Text PäivämääräTxt;

    @FXML
    private Button TakaisinBtn;

    @FXML
    void initialize() {
        TablecolumPelaajat.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
    }

    @FXML
    public void SiirryTakaisin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Aloitusnäyttö.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene UusipeliS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(UusipeliS);
        window.show();
    }
}
