package main.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TulevatTurnauksetController {
    public static Pelaajataulu pelaajat = new Pelaajataulu();
    public static Turnaus turnaus;
    public static ArrayList<Pelaaja> pel;
    public static int kierros_id;

    public void setKierros_id(int id) {
        this.kierros_id = id; 
    }

    public static int getKierrosId() {
        return kierros_id;
    }

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
        TableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (TableView.getSelectionModel().getSelectedItem() != null) {
                Turnaus valittu = TableView.getSelectionModel().getSelectedItem(); 
            }
        });
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
        //kun aloitetaan uusi turnaus, tyhjennetään pelaajalista
        pelaajat.Tyhjenna();
        Turnaus turnaus = TableView.getSelectionModel().getSelectedItem();

        //tämän saikin yksinkertaisemmin.
        // nämä rivit ovat nyt turhat?
        /* Haetaan turnauksen nimi valitusta rivistä.
        https://stackoverflow.com/questions/29090583/javafx-tableview-how-to-get-cells-data

        TablePosition pos = TableView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Turnaus item = TableView.getItems().get(row);
        TableColumn col = TableView.getColumns().get(1);
        String nimi = (String) col.getCellObservableValue(item).getValue();

        //haetaan turnauksen ID turnauksen nimellä
        int id = Tietokanta.HaeTurnauksenID(nimi);
        */

        //turnauksen pelaajat turnauksen perusteella
        ArrayList<Pelaaja> turnauksenpelaajat =  Tietokanta.TurnauksenPelaajat(turnaus);

        //System.out.println(turnaus.getId());
        /*
        for (Pelaaja pip : turnauksenpelaajat) {
            System.out.println(pip.getNimi() + pip.getPeliNro());
        }

        for (Pelaaja p : pel){
            pelaajat.setPelaaja(p);
        }
        turnaus.setPelaajat(pel);
        */

        Tietokanta.TurnausKäyntiin(turnaus.getId()); 
        Kierros uusi_kierros = new Kierros();
        uusi_kierros.setTurnaus(turnaus);

        //kierroksen ID on auto increment tietokannassa.
        //vaikka kierros olisikin 1, tietokannasta tulee joku ihan eri numero.
        uusi_kierros.setKierros(1);
        Tietokanta.LisaaKierros(uusi_kierros);
        int kid = Tietokanta.HaeUusinKierrosID();
        setKierros_id(kid);

        System.out.println("kierroksen id ensin " + kierros_id);

         
        for (Pelaaja pelaaja : turnauksenpelaajat) {
            pelaajat.setPelaaja(pelaaja);
            //System.out.println("nimi = " + pelaaja.getNimi());
        } 

        //otteluparien jako
        List<Ottelu> ottelut  = pelaajat.jaaOtteluparit();

        //pitää tehdä joku haku tietokannasta, että saadaan jo pleatut parit selville.
        //alkuperäinen ei taida toimia oikein, kun haetaan pelaajat tietokannasta.

        ArrayList<Pelaaja> ptaulu = pelaajat.getPelaajat();

        //tuodaan jaetut otteluparit Pelaajataulusta
        //= new ArrayList<Ottelu>();
        //ottelut = pelaajat.getKierros();
        /*
        for (int i = 0; i < ptaulu.size(); i = i+2) {
            Ottelu o = new Ottelu();
            o.setKierros(kid);
            o.setPelaaja1(ptaulu.get(i));
            o.setPelaaja2(ptaulu.get(i+1));
            ottelut.add(o);
        }
        */
        for (Ottelu ot : ottelut) {
            ot.setKierros(kid); // asetetaan otteluille kierroksen ID
            Tietokanta.LisaaOttelu(ot); //lisätaan ottelut tietokantaan.
            ot.setID(Tietokanta.HaeUusinOtteluID()); //haetaan äsken lisätyn ottelut ID
            Tietokanta.PelaajatOtteluun(ot); //lisätään pelaajat otteluun.
        }

        //taitaa olla turha
        //kierros = pelaajat.getKierros(); mikä tämä on

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Kilpailuparinäkymä.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene PariS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(PariS);
        window.show(); 
    }

    public void PoistaTurnaus(ActionEvent event) throws IOException {
        TablePosition pos = TableView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Turnaus item = TableView.getItems().get(row);
        TableColumn col = TableView.getColumns().get(1);
        String nimi = (String) col.getCellObservableValue(item).getValue();

        //haetaan turnauksen ID turnauksen nimellä
        int id = Tietokanta.HaeTurnauksenID(nimi);


        ArrayList<Integer> Poistettavatottelut = Tietokanta.HaeTurnauksenOttelut(id);
        Alert poistetaanko = new Alert(Alert.AlertType.CONFIRMATION);
        poistetaanko.setTitle("Poistetaanko turnaus");
        poistetaanko.setHeaderText(null);
        poistetaanko.setResizable(false);
        poistetaanko.setContentText("Haluatko varmasti poistaa turnauksen");
        Optional<ButtonType> result = poistetaanko.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);
        if (button == ButtonType.OK) {
            if (Tietokanta.PoistaTurnaus(id)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Turnaus poistettu");
                alert.setHeaderText(null);
                alert.setContentText("Turnaus poistettu");
                alert.showAndWait();
                Tietokanta.PoistaTurnauksenPelaaja_Ottelut(Poistettavatottelut);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Turnausta ei voitu poistaa");
                alert.setHeaderText(null);
                alert.setContentText("Turnausta ei voitu poistaa");
                alert.showAndWait();
            }
        } else {
            System.out.println("canceled");
        }

        initialize();
    }

}
