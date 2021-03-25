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
        TableColmun1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("Pelaaja1"));
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("Pelaaja2"));
        //TableView.setItems(Tietokanta.haeKilpailuparinPelaajat())

        //hakee edellisessä näytössä olleen kierroksen ID:n
        int testi = TulevatTurnauksetController.getKierrosId();

        //hakee aina kierroksen 10?
        //int id = Tietokanta.haeKierrosID();
        //System.out.println("kierroksen id " + id + "testi = " + testi);

        ArrayList<Integer> ottelu_idt = new ArrayList<Integer>();

        //edellisellä näytöllä jaetut otteluiden ID:t.
        ottelu_idt = Tietokanta.getKierroksenOttelut(testi);

        //System.out.println("ottelut idt " + ottelu_idt);

        ObservableList<Ottelu> ottelut = FXCollections.observableArrayList(); 

        for (Integer i : ottelu_idt) {
            Ottelu o = new Ottelu(); 
            o.setID(i);
                ArrayList<Pelaaja> pelaajat = Tietokanta.haeKilpailuparinPelaajat(i); 
                o.setPelaaja1(pelaajat.get(0));
                System.out.println("pelaaja1  " + o.getPelaaja1().getNimi()); 
                o.setPelaaja2(pelaajat.get(1));
                ottelut.add(o); 
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
        //System.out.println(ottelu.getID());


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Pisteenlaskunäkymä.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene PisteS = new Scene(AloitusNayttoP);


        Stage window = new Stage(); 

        window.setScene(PisteS);
        window.show(); 
        luoIlmoitus();
    }
    //siirry TulevatPelit näkymään
    @FXML
    public void SiirryTakaisin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/TulevatTurnaukset.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene AloitusS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AloitusS);
        window.show();
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

