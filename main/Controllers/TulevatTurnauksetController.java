package main.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Tietokanta;
import main.Turnaus;

public class TulevatTurnauksetController {


    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text TulevatTurnauksetTxt;

    @FXML
    private TableView<Turnaus> TableView;

    @FXML
    private TableColumn<Turnaus, String> TableColumnPelityyppi;

    @FXML
    private TableColumn<Turnaus, String> TableColumnTurnauksenNimi;

    @FXML
    private TableColumn<Turnaus, String> TableColumnAloituspäivämäärä;

    @FXML
    private TableColumn<Turnaus, String> TableColumnLopetuspäivämäärä;

    @FXML
    private Button NäytäPeliBtn;

    @FXML
    private Button MuokkaaBtn;

    @FXML
    private Button PeruPeliBtn;

    @FXML
    private Button TakaisinBtn;

    @FXML
    void initialize() {
        TableColumnPelityyppi.setCellValueFactory(new PropertyValueFactory<Turnaus, String>("pelityyppi"));
        TableColumnTurnauksenNimi.setCellValueFactory(new PropertyValueFactory<Turnaus, String>("nimi"));
        TableColumnAloituspäivämäärä.setCellValueFactory(new PropertyValueFactory<Turnaus, String>("aloituspvm"));
        TableColumnLopetuspäivämäärä.setCellValueFactory(new PropertyValueFactory<Turnaus, String>("lopetuspvm"));
        TableView.setItems(Tietokanta.haeTurnaukset());
    }

    @FXML
    public void SiirryTakaisin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Aloitusnäyttö.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene AloitusS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AloitusS);
        window.show();
    }

    @FXML
    public void Pelaa(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Kilpailuparinäkymä.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene PariS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(PariS);
        window.show(); 
    }
}
