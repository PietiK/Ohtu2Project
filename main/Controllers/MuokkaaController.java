package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;

import java.io.IOException;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Pelaaja;
import main.Tietokanta;
import main.Turnaus; 
import main.PelaajaNumerot;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class MuokkaaController {
    int turnaus_id = TulevatTurnauksetController.getTurnaus_id();
    ArrayList<Pelaaja> pelaajat = Tietokanta.getTurnauksenPelaajat(TulevatTurnauksetController.getTurnaus_id());
    Turnaus turnaus = Tietokanta.haeTurnaus(TulevatTurnauksetController.getTurnaus_id());

    @FXML
    private AnchorPane Anchorpane;

    @FXML
    private Text UusiTurnausTxt;

    @FXML
    private TextField TextField2;

    @FXML
    private Button LisääBtn2;

    @FXML
    private Button PoistaBtn;

    @FXML
    private TableView<Pelaaja> TableView;

    @FXML
    private TableColumn<Pelaaja, String> TablecolumPelaajat;

    @FXML
    private TableColumn<?, ?> TableColumnLuku;

    @FXML
    private Button ValmisBtndrop;

    @FXML
    private MenuButton ValitsePeliBtn;

    @FXML
    private DatePicker DatePicker1;

    @FXML
    private Text AloituspäivämääräTxt;

    @FXML
    private Button TakaisinBtn;

    @FXML
    private Text LopetuspäivämääräTxt;

    @FXML
    private DatePicker DatePicker2;

    @FXML
    private Text NimeäTurnausTxt;

    @FXML
    private TextField TextField1;

    @FXML
    private Button LisääBtn1;


   @FXML
    void initialize() {
       TablecolumPelaajat.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
       TableView.getItems().clear();
       TextField1.setText(turnaus.getNimi());

       for (Pelaaja p : pelaajat) {
           TableView.getItems().add(p);
       }
        //TableView.setItems(Tietokanta.haePelaajat());
       //TableView.setItems(pelaajatO);


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
    @FXML
    public void LisaaPelaaja(ActionEvent event) throws IOException {
        String nimi = TextField2.getText();
        pelaajat.add((new Pelaaja(nimi)));
        //TableView.getItems().add(new Pelaaja(nimi));
        TextField2.setText("");
        //Tietokanta.LisaaPelaaja(temp);
        initialize();
    }

    @FXML
    public void PoistaPelaaja(ActionEvent event) throws IOException {
        Pelaaja p = TableView.getSelectionModel().getSelectedItem(); 
        Varmista(p); 
    }

    
    private void Varmista(Pelaaja p) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Poista pelaaja painamalla ok");
        alert.setTitle("Pelaajan poistaminen ");
        alert.setHeaderText("");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
          TableView.getItems().remove(p); 
        }
    }


    public void LuoTurnaus(ActionEvent event) throws IOException {
        Turnaus uusiturnaus = new Turnaus();
        uusiturnaus.setId(turnaus.getId());
        uusiturnaus.setNimi(TextField1.getText());
        String Aloitus = turnaus.getAloituspvm();
        String Lopetus = turnaus.getLopetuspvm();
        uusiturnaus.setAloituspvm(Aloitus);
        uusiturnaus.setLopetuspvm(Lopetus);

        ObservableList<Pelaaja> p = TableView.getItems();

        ArrayList<Pelaaja> temp = new ArrayList<Pelaaja>();
        for (Pelaaja pe : p) {
            temp.add(pe);
        }

        for (Pelaaja pe : temp) {
            if(pe.getId()==0){
                System.out.println("OK");
                Tietokanta.LisaaPelaaja(pe);
                int pid = Tietokanta.HaeUusinPelaajaID();
                pe.setId(pid);
            }

        }

        ArrayList<Integer> pel = Tietokanta.getTurnauksenPelaajatIDt(turnaus_id);

        PelaajaNumerot.arvoNumerot(temp);
        for (Pelaaja pelaaja : temp) {
            if(pel.contains(pelaaja.getId())){
                //System.out.println("OK");
            } else {
                Tietokanta.LuoTurnauksenPelaajalista(pelaaja, turnaus_id);
                System.out.println("OK");
            }
        }
        Tietokanta.PaivitaTurnaus(uusiturnaus);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Aloitusnäyttö.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene UusipeliS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(UusipeliS);
        window.show();

       /*
        Turnaus uusiturnaus = new Turnaus(); 
        uusiturnaus.setNimi(TextField1.getText());

        String Aloitus = DatePicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String Lopetus = DatePicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
        uusiturnaus.setAloituspvm(Aloitus);
        uusiturnaus.setLopetuspvm(Lopetus);
        
        ObservableList<Pelaaja> p = TableView.getItems();

        ArrayList<Pelaaja> pelaajat = new ArrayList<Pelaaja>(); 
        for (Pelaaja pe : p) {
            pelaajat.add(pe); 
        }
        Tietokanta.LisaaTurnaus(uusiturnaus);

        int id = Tietokanta.HaeuusinTurnausID(); 

        for (Pelaaja pel : pelaajat) {
            Tietokanta.LisaaPelaaja(pel);
            int pid = Tietokanta.HaeUusinPelaajaID();
            pel.setId(pid);
        }
        PelaajaNumerot.arvoNumerot(pelaajat); 

        for (Pelaaja peip : pelaajat) {
            Tietokanta.LuoTurnauksenPelaajalista(peip, id);
        }
       */

        /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Aloitusnäyttö.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene UusipeliS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(UusipeliS);
        window.show();

         */
    }



}
