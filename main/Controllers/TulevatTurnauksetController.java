package main.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TulevatTurnauksetController {
    public static Pelaajataulu pelaajat = new Pelaajataulu();
    public static Turnaus turnaus = new Turnaus();
    public static ArrayList<Pelaaja> pel;
    List<Ottelu> kierros = new ArrayList();


    public static Pelaajataulu getPelaajat(){
        return pelaajat;
    }
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

        /* Haetaan turnauksen nimi valitusta rivistä.
        https://stackoverflow.com/questions/29090583/javafx-tableview-how-to-get-cells-data
         */
        TablePosition pos = TableView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Turnaus item = TableView.getItems().get(row);
        TableColumn col = TableView.getColumns().get(1);
        String nimi = (String) col.getCellObservableValue(item).getValue();

        //haetaan turnauksen ID turnauksen nimellä
        int id = Tietokanta.HaeTurnauksenID(nimi);


        pel =  Tietokanta.TurnauksenPelaajat(id);
        for (Pelaaja p : pel){
            pelaajat.setPelaaja(p);
        }

        turnaus.setPelaajat(pel);
        Kierros uusi_kierros = new Kierros();

        pelaajat.jaaOtteluparit();
        kierros = pelaajat.getKierros();
        Tietokanta.PelaajatOtteluun(kierros);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(PariS);
        window.show(); 
    }
}
