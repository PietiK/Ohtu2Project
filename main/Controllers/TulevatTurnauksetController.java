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
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class TulevatTurnauksetController {
    public static Pelaajataulu pelaajat = new Pelaajataulu();
    public static Turnaus turnaus;
    public static ArrayList<Pelaaja> pel;
    public static int kierros_id;
    public static int turnaus_id;
    //public static Turnaus turnaus;

    public static void setKierros_id(int id) {
        kierros_id = id; 
    }

    public static void setTurnaus_id(int id) {
        turnaus_id = id; 
    }

    public static int getTurnaus_id() {
        return turnaus.getId();
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

        turnaus = TableView.getSelectionModel().getSelectedItem();
        System.out.println(turnaus.getId());
        ArrayList<Kierros> turnauksenkierrokset = Tietokanta.haeTurnauksenKierrokset(turnaus.getId()); 
        /*
        Tässä testataan, onko turnaus jo aloitettu aiemmin, eli onko sille luotu vielä kierroksia.
        Jos ei, niin luodaan ensimmäinen kierros sekä sen ottelut. Tätä voidaan käyttää myös
        seuraavalle kierrokselle siirtyessä sen kierroksen ja otteluiden luomiseen. 
        Ensimmäisen kierroksen peliparit tulee vaan suoraan turnauksen pelaajat listasta
        */
        if (turnauksenkierrokset.isEmpty()) {
            //turnauksen pelaajat turnauksen perusteella
            ArrayList<Pelaaja> turnauksenpelaajat =  Tietokanta.TurnauksenPelaajat(turnaus);

            Tietokanta.TurnausKäyntiin(turnaus.getId());

            setTurnaus_id(turnaus.getId());
            Kierros uusi_kierros = new Kierros();
            uusi_kierros.setTurnaus(turnaus);

            //asetetaan kierroksen järjestysnumero ja lisätään kierros tietokantaan

            uusi_kierros.setKierros(1);
            Tietokanta.LisaaKierros(uusi_kierros); //Tietokannan kierros_id on auto increment
            int kid = Tietokanta.HaeUusinKierrosID();
            setKierros_id(kid);
            ArrayList<Ottelu> ottelut = new ArrayList<Ottelu>(); 
        
            /*
            for (int i = 0; i < turnauksenpelaajat.size(); i = i+2) {
                Ottelu o = new Ottelu();
                o.setKierros(kid);
                o.setPelaaja1(turnauksenpelaajat.get(i));
                o.setPelaaja2(turnauksenpelaajat.get(i+1));
                ottelut.add(o);
            }


             */
            ottelut = JaaParit(turnauksenpelaajat,kid,turnaus);

            for (Ottelu ot : ottelut) {
                //ot.setKierros(kid); // asetetaan otteluille kierroksen ID
                Tietokanta.LisaaOttelu(ot); //lisätaan ottelut tietokantaan.
                ot.setID(Tietokanta.HaeUusinOtteluID()); //haetaan äsken lisätyn ottelut ID
                Tietokanta.PelaajatOtteluun(ot); //lisätään pelaajat otteluun.
                System.out.println(ot.toString());
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/Kilpailuparinäkymä.fxml"));
            Parent AloitusNayttoP = loader.load();
            Scene PariS = new Scene(AloitusNayttoP);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(PariS);
            window.show(); 
        } else {
            /*
            Tässä etsitään jo aloitetun turnauksen tiedot tietokannasta, eli kierros jolla ollaan keskeytetty.
            Sen kierroksen id asetetaan kierros_id:ksi, jotta kierrosnäkymä
            voi etsiä oikeat ottelut. 
            Tässä pitää todnäk tehdä muutakin mutta nyt en muista enää mitä  
            */
            ArrayList<Integer> k = new ArrayList<Integer>(); 
            for (Kierros ki : turnauksenkierrokset) {
                k.add(ki.getJnum()); 
            }
            Integer max = Collections.max(k); 
            for (Kierros kie : turnauksenkierrokset) {
                if (kie.getJnum() == max) { 
                    setKierros_id(kie.getID());
                }
            }
            Tietokanta.TurnausKäyntiin(turnaus.getId());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/Kilpailuparinäkymä.fxml"));
            Parent AloitusNayttoP = loader.load();
            Scene PariS = new Scene(AloitusNayttoP);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(PariS);
            window.show(); 
        }
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

    public static ArrayList<Ottelu> JaaParit(ArrayList<Pelaaja> turnauksenpelaajat, int kid,Turnaus turnaus) {
        ArrayList<Ottelu> ottelut = new ArrayList<>();

        ArrayList<Kierros> kierrokset = Tietokanta.haeTurnauksenKierrokset(turnaus.getId());

        //Järjestetään pelaajien lista heidän pelinumeronsa mukaan
        Collections.sort(turnauksenpelaajat, Collections.reverseOrder(Pelaaja.peliNroSorter));
        ArrayList<Pelaaja> temp = turnauksenpelaajat;
        System.out.println("Tässä pelurit " + temp);

        //jos kierroksia on alle 3, niin jaetaan 2 ensimmäistä kierrosta.
        if(kierrokset.size() < 3){
            //parillinen parien jako.
            if (temp.size() % 2 == 0){
                for (int i = 0; i < temp.size();i = i+2){
                    Ottelu o = new Ottelu();
                    o.setKierros(kid);
                    o.setPelaaja1(temp.get(i));
                    o.setPelaaja2(temp.get(i+1));
                    ottelut.add(o);
                }
                //tyhjennetään väliaikainen lista ja tuodaan pelaajat takaisin.
                temp.clear();
                for (Ottelu o : ottelut) {
                    if (!temp.contains(o.getPelaaja1())) {
                        temp.add(o.getPelaaja1());
                    }
                }
                for (Ottelu o : ottelut){
                    if (!temp.contains(o.getPelaaja2())) {
                        temp.add(o.getPelaaja2());
                    }
                }
                //luodan uusi kierros
                Kierros uusi_kierros = new Kierros();
                uusi_kierros.setTurnaus(turnaus);

                //asetetaan kierroksen järjestysnumero ja lisätään kierros tietokantaan
                uusi_kierros.setKierros(kierrokset.size()+1);
                Tietokanta.LisaaKierros(uusi_kierros);
                int tokakierros = Tietokanta.HaeUusinKierrosID();
                setKierros_id(tokakierros);

                for (int i = 0; i < temp.size();i = i+2){
                    Ottelu o = new Ottelu();
                    o.setKierros(tokakierros);
                    o.setPelaaja1(temp.get(i));
                    o.setPelaaja2(temp.get(i+1));
                    ottelut.add(o);
                }
            //pariton jako
            } else {
                //otetaan "viimeinen" pelaaja talteen
                Pelaaja vika_pelaaja = temp.get(temp.size()-1);
                //jaetaan pelaajat
                for (int i = 0; i < temp.size()-1;i = i+2){
                    Ottelu o = new Ottelu();
                    o.setKierros(kid);
                    o.setPelaaja1(temp.get(i));
                    o.setPelaaja2(temp.get(i+1));
                    ottelut.add(o);
                }
                //tyhjennetään lista ja lisätään "viimeinen" ja tuodaan loput pelaajat
                temp.clear();
                temp.add(vika_pelaaja);
                for (Ottelu o : ottelut) {
                    if (!temp.contains(o.getPelaaja1())) {
                        temp.add(o.getPelaaja1());
                    }
                }
                for (Ottelu o : ottelut){
                    if (!temp.contains(o.getPelaaja2())) {
                        temp.add(o.getPelaaja2());
                    }
                }
                //lisätään viimeinen uudestaan, näin hänelle tulee 2 peliä toisella kierroksella
                //ja kaikki pelaajat pelaavat yhtä monta ensimmäisen kahden kierroksen aikana
                temp.add(vika_pelaaja);

                Kierros uusi_kierros = new Kierros();
                uusi_kierros.setTurnaus(turnaus);

                //asetetaan kierroksen järjestysnumero ja lisätään kierros tietokantaan
                uusi_kierros.setKierros(kierrokset.size()+1);
                Tietokanta.LisaaKierros(uusi_kierros);
                int tokakierros = Tietokanta.HaeUusinKierrosID();
                setKierros_id(tokakierros);
                System.out.println(tokakierros);
                for (int i = 0; i < temp.size();i = i+2){
                    Ottelu o = new Ottelu();
                    o.setKierros(tokakierros);
                    o.setPelaaja1(temp.get(i));
                    o.setPelaaja2(temp.get(i+1));
                    ottelut.add(o);
                }
            }
        } else {
          System.out.println("Jqwe");
          return Pelaajataulu.jaaSeuraavaKierros(turnauksenpelaajat, 
            kierrokset.get(kierrokset.size()-1).getJnum(), 
            turnaus.getId());
        }
        return ottelut;
    }

    @FXML
    public void SiirryMuokkaamaan(ActionEvent event) throws IOException {
        turnaus = TableView.getSelectionModel().getSelectedItem();
        ArrayList<Kierros> turnauksenkierrokset = Tietokanta.haeTurnauksenKierrokset(turnaus.getId());
        //System.out.println(Tietokanta.OnkoKaynnissa(turnaus.getId()));
        if (turnauksenkierrokset.isEmpty()){
            //System.out.println(Tietokanta.OnkoKaynnissa(turnaus.getId()));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/Muokkaa.fxml"));
            Parent AloitusNayttoP = loader.load();
            Scene UusipeliS = new Scene(AloitusNayttoP);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(UusipeliS);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Turnaus on jo käynnissä");
            alert.setTitle("Turnaus on jo aloitettu");
            alert.setHeaderText("");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK) {
                alert.close();;
            }
        }



    }

}
