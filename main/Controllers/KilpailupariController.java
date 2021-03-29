package main.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.*;

public class KilpailupariController{


    public static Ottelu ottelu;
    public static int OtteluId;

    public static Ottelu getOttelu() {
        return ottelu;
    }

    public static int getOtteluId() {
        return OtteluId;
    }

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text EnsimmäinenTxt;

    @FXML
    private TableView<Ottelu> TableView;

    @FXML
    private TableColumn<Ottelu, Integer> TableColmun0;
    @FXML
    private TableColumn<Ottelu, String> TableColmun1;

    @FXML
    private TableColumn<Ottelu, String> TableColumn2;

    @FXML
    private Button TakaisinBtn;

    @FXML
    private Button SeuraavaBtn;

    //HUOM! KESKEN
    //
    //
    @FXML
    void initialize() {
        TableColmun0.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("kierros"));
        TableColmun1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("Pelaaja1"));
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("Pelaaja2"));
        //TableView.setItems(Tietokanta.haeKilpailuparinPelaajat())

        //hakee edellisessä näytössä olleen kierroksen ja turnauksen ID:n
        int viimeisein_kierros = TulevatTurnauksetController.getKierrosId();
        int turnauksen_id = TulevatTurnauksetController.getTurnaus_id();

        System.out.println(viimeisein_kierros + " " + turnauksen_id);

        ArrayList<Kierros> kierrokset = Tietokanta.haeTurnauksenKierrokset(turnauksen_id);
        System.out.println(kierrokset.size());

        ArrayList<Integer> ottelu_idt = new ArrayList<Integer>();

        //edellisellä näytöllä jaetut otteluiden ID:t.
        ottelu_idt = Tietokanta.getKierroksenOttelut(viimeisein_kierros);
        //jos kierroksia on alle 3 haetaan myös ensimmäinen kierros.
        if (kierrokset.size()<3){
            ArrayList<Integer> eka_kierros_idt = Tietokanta.getKierroksenOttelut(viimeisein_kierros-1);
            int indexi = 0;
            for (Integer i :eka_kierros_idt){
                ottelu_idt.add(indexi,i);
                indexi++;
            }
        }

        ObservableList<Ottelu> ottelut = FXCollections.observableArrayList();

        //jos samaa näyttöä käytetään myöhäisemmillä kierroksilla
        //tähän pitää kehittää joku if lause, että jos viimeisin kierros on 2, niin lista pitää jakaa kahtia.
        if (kierrokset.size()<3){
            for (int i = 0; i < ottelu_idt.size()/2;i++){
                Ottelu o = new Ottelu();
                o.setID(ottelu_idt.get(i));
                ArrayList<Pelaaja> pelaajat = Tietokanta.haeKilpailuparinPelaajat(ottelu_idt.get(i));
                o.setPelaaja1(pelaajat.get(0));
                o.setPelaaja2(pelaajat.get(1));
                o.setKierros(kierrokset.size()/2);
                ottelut.add(o);
            }
            for (int i = ottelu_idt.size()/2;i < ottelu_idt.size();i++){
                Ottelu o = new Ottelu();
                o.setID(ottelu_idt.get(i));
                ArrayList<Pelaaja> pelaajat = Tietokanta.haeKilpailuparinPelaajat(ottelu_idt.get(i));
                o.setPelaaja1(pelaajat.get(0));
                o.setPelaaja2(pelaajat.get(1));
                o.setKierros(kierrokset.size());
                ottelut.add(o);
            }
        } else {
            for (Integer i : ottelu_idt) {
                Ottelu o = new Ottelu();
                o.setID(i);
                ArrayList<Pelaaja> pelaajat = Tietokanta.haeKilpailuparinPelaajat(i);
                o.setPelaaja1(pelaajat.get(0));
                o.setPelaaja2(pelaajat.get(1));
                o.setKierros(kierrokset.size());
                ottelut.add(o);
            }
        }

        TableView.setItems(ottelut);
    }  

        /*
        TableView.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, oldValue, newValue) -> {
            if (TableView.getSelectionModel().getSelectedItem() != null) {
                Ottelu ottelu = TableView.getSelectionModel().getSelectedItem();
                //Pelaaja eka = ottelu.getPelaaja1().;
                //Pelaaja toka = ottelu.getPelaaja2();

            }
        }); 
        */

    
    @FXML
    public void Pelaa(ActionEvent event) throws IOException {

        ottelu = TableView.getSelectionModel().getSelectedItem();
        OtteluId = ottelu.getID();
        
        if (Tietokanta.OnkoVoittajaa(OtteluId)) {
            Estä(); 
        } else {
        System.out.println("Pelaajat: " + ottelu.getPelaaja1().getId());


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Pisteenlaskunäkymä.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene PisteS = new Scene(AloitusNayttoP);


        Stage window = new Stage(); 

        window.setScene(PisteS);
        window.show(); 
        luoIlmoitus();
        }
    }
    private void Estä() {   
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Tämä ottelu on jo pelattu!");
        alert.setTitle("Ottelun pelattu");
        alert.setHeaderText("");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
          alert.close();;
        }
    }

    //siirry TulevatPelit näkymään
    @FXML
    public void SiirryTakaisin(ActionEvent event) throws IOException {
        Tietokanta.TurnausEiKäyntiin();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/TulevatTurnaukset.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene AloitusS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AloitusS);
        window.show();
    }

    @FXML
    public void SeuraavaKierros(ActionEvent event) throws IOException {
        int kid = TulevatTurnauksetController.getKierrosId();
        ArrayList<Integer> ottelut = Tietokanta.getKierroksenOttelut(kid); 
        boolean valmis = true; 
        for (Integer i : ottelut) {
            if (!Tietokanta.OnkoVoittajaa(i)) {
                valmis = false; 
            }
        }
        if (!valmis) {
            Estä2();
        } else {
             /*
            Tässä luodaan uusi kierros ja sen ottelut, tableviewin pitäsi päivittyä 
            */
            Kierros uusi = new Kierros (); 
            int tid = TulevatTurnauksetController.getTurnaus_id();
            uusi.setTurnaus(Tietokanta.haeTurnaus(tid));
            int k = Tietokanta.HaeSuurinKierrosnumero(tid) + 1; 
            uusi.setKierros(k);
            Tietokanta.LisaaKierros(uusi);
            uusi.setID(Tietokanta.HaeUusinKierrosID());
            ArrayList<Pelaaja> pelaajatjäljellä = Tietokanta.haeJäljelläPelaajat(tid); 
            /* kesken, tähän tarvitaan pelaajien paritus */
          
        }
    }

    private void Estä2() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Kierroksen kaikkia otteluita ei ole vielä pelattu!");
        alert.setTitle("Kierros kesken");
        alert.setHeaderText("");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            alert.close();;
        }
    }

    /*
    public KilpailupariController(FXMLLoader fxmlLoader, Parent root, TulevatTurnauksetController ttcontroller) {
        this.fxmlLoader = fxmlLoader;
        this.root = root;
        this.ttcontroller = ttcontroller;
    }


    public KilpailupariController(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }
    */
    public void luoIlmoitus(){
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setContentText("Paina ok aloittaaksesi ottelu");
      alert.setTitle("Ottelun aloitus");
      alert.setHeaderText("Aloita ottelu");
      alert.showAndWait();
      if(alert.getResult() == ButtonType.OK){
        PisteenlaskuController.aloitaKello();
      }
    }
}

