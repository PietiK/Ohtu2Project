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
import main.Tietokanta;
import main.Turnaus; 

import java.sql.*;

public class UusiTurnausController {

    @FXML
    private AnchorPane Anchorpane;

    @FXML
    private Text UusiTurnausTxt;

    @FXML
    private TextField TextField2;

    @FXML
    private Button LisääBtn2;

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
        //TableView.setItems(Tietokanta.haePelaajat());
        //Ylläoleva hakee kaikki tietokannassa olevat pelaajat taulukkoon, miksi? 
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
        TableView.getItems().add(new Pelaaja(nimi)); 
        TextField2.setText("");
        //Tietokanta.LisaaPelaaja(temp);
        initialize();
    }

    /*tällä metodilla siis tarkoitus luoda turnaus tietokantaan, vielä ei toimi koska 
    tietokannan pathissa joku ongelma. puuttuu vielä pelaajalista, koska se vaatii eri 
    tietokantametodin jne. 
    */
    public void LuoTurnaus(ActionEvent event) throws IOException {
        Turnaus uusiturnaus = new Turnaus(); 
        uusiturnaus.setNimi(TextField1.getText()); 
        uusiturnaus.setAloituspvm(DatePicker1.getAccessibleText());
        uusiturnaus.setLopetuspvm(DatePicker2.getAccessibleText());
        System.out.println(TableView.getItems());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Aloitusnäyttö.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene UusipeliS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(UusipeliS);
        window.show();
    }


}
