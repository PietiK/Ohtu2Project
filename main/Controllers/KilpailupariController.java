package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KilpailupariController{


    public static Ottelu ottelu;
    public static int OtteluId;
    public static int turnauksen_id;
    public static ObservableList<Ottelu> ottelut;
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

    @FXML
    private Button tuloksetButton;

    @FXML
    private Button PelaajalistaBtn;

    public static ObservableList<Ottelu> get_ottelut() {
        return ottelut;
    }

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
        turnauksen_id = TulevatTurnauksetController.getTurnaus_id();

        ArrayList<Kierros> kierrokset = Tietokanta.haeTurnauksenKierrokset(turnauksen_id);

        ArrayList<Integer> ottelu_idt = Tietokanta.getKierroksenOttelut(viimeisein_kierros);

        //edellisellä näytöllä jaetut otteluiden ID:t.
        //jos kierroksia on alle 3 haetaan myös ensimmäinen kierros.
        if (kierrokset.size()<3){
            ArrayList<Integer> eka_kierros_idt = Tietokanta.getKierroksenOttelut(viimeisein_kierros-1);
            int indexi = 0;
            for (Integer i :eka_kierros_idt){
                ottelu_idt.add(indexi,i);
                indexi++;
            }
        }

        ottelut = FXCollections.observableArrayList();

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
        //System.out.println("Ottelun kierros: " + ottelut.get(ottelut.size() -1 ).getKierros());
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
        }
    }

    @FXML
    public void Tulokset(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Tulosnäyttö.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene PisteS = new Scene(AloitusNayttoP);
        Stage window = new Stage(); 
        window.setScene(PisteS);
        window.show(); 
    }

    private void Estä() {   
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Tämä ottelu on jo pelattu!");
        alert.setTitle("Ottelu on pelattu");
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
            Turnaus turnaus = Tietokanta.haeTurnaus(tid);
            uusi.setTurnaus(turnaus);

            int k = Tietokanta.HaeSuurinKierrosnumero(tid) + 1; 
            uusi.setKierros(k);
            Tietokanta.LisaaKierros(uusi);
            int kieid = Tietokanta.HaeUusinKierrosID();
            uusi.setID(kieid);
            ArrayList<Ottelu> uudetottelut = new ArrayList<Ottelu>(); 
            ArrayList<Pelaaja> pelaajatjäljellä = Tietokanta.haeJäljelläPelaajat(tid); 
            System.out.println("Pelaajat: " + pelaajatjäljellä); 
            if (pelaajatjäljellä.size() > 1) {
            uudetottelut = TulevatTurnauksetController.JaaParit(pelaajatjäljellä, kieid, turnaus);
            ObservableList<Ottelu> seuraavatottelut = FXCollections.observableArrayList();
            for (Ottelu ot : uudetottelut) {
                ot.setKierros(kid); // asetetaan otteluille kierroksen ID
                Tietokanta.LisaaOttelu(ot); //lisätaan ottelut tietokantaan.
                ot.setID(Tietokanta.HaeUusinOtteluID()); //haetaan äsken lisätyn ottelut ID
                Tietokanta.PelaajatOtteluun(ot); //lisätään pelaajat otteluun.
                System.out.println(ot.toString());
                seuraavatottelut.add(ot);
            }
            System.out.println(uudetottelut); 
            System.out.println(seuraavatottelut);
            TableView.setItems(seuraavatottelut); 
            } else {
                Pelaaja voittaja = pelaajatjäljellä.get(0); 
                TurnausOhi(voittaja);
                Tietokanta.TurnausEiKäyntiin();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/TulevatTurnaukset.fxml"));
                Parent AloitusNayttoP = loader.load();
                Scene AloitusS = new Scene(AloitusNayttoP);
        
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
                window.setScene(AloitusS);
                window.show();
            }
        }
    }

    private void TurnausOhi(Pelaaja voittaja) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Turnauksen voittaja on " + voittaja.getNimi());
        alert.setTitle("Turnaus loppunut!");
        alert.setHeaderText("");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            alert.close();;
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

    @FXML
    public void Pelaajat(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Pelaajalista.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene PisteS = new Scene(AloitusNayttoP);
        Stage window = new Stage(); 
        window.setScene(PisteS);
        window.show(); 
    }


    //TODO
    //Voittajan väritys
    //public void varita(TableColumn<Ottelu, String> column1, TableColumn<Ottelu, String> column2){
    //}
      
      
        
}

