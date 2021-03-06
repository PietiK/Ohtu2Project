package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.Kierros;
import main.Ottelu;
import main.Pelaaja;
import main.Tietokanta;

import java.util.ArrayList;


public class TulosController {

    public static int turnauksen_id;
    public static int kierros_id;
    public static int kierros_nro;
    public static ObservableList<Ottelu> ottelut;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text KierrosTxt;

    @FXML
    private TableView<Ottelu> TableViewTulos;

    @FXML
    private TableColumn<Ottelu,Integer> TableColumn1;

    @FXML
    private TableColumn<Ottelu,String> TableColumnPelaaja1;

    @FXML
    private TableColumn<Ottelu,Integer> TableColumnP1pisteet;

    @FXML
    private TableColumn<Ottelu, String> TableColumnPelaaja2;

    @FXML
    private TableColumn<Ottelu,Integer> TableColumnP2pisteet;

    @FXML
    private TableColumn<Ottelu,String> Voittaja;
    @FXML
    private ScrollBar ScrollBar;

    @FXML
    private Button EdellinenBtn;

    @FXML
    private Button KeskeytäTurnausBtn;

    @FXML
    private Button PäätäTurnausBtn;

    @FXML
    private Button SeuraavaBtn;

    @FXML
    void initialize() {
        turnauksen_id = TulevatTurnauksetController.getTurnaus_id();
        //Haetaan kaikki turnauksen ottelut
        //Ensin pitää hakea kierroksien ottelut, koska turnaus_id:itä ei ole ottelutaulussa tietokannassa
        ArrayList<Kierros> kierrokset = Tietokanta.haeTurnauksenKierrokset(turnauksen_id);
        ArrayList<Integer> kierrosidt = new ArrayList<>();
        for(Kierros k : kierrokset){
            kierrosidt.add(k.getID());
        }
        ArrayList<Integer> otteluidt = new ArrayList<>();
        for(int kid : kierrosidt){
             otteluidt.addAll(Tietokanta.getKierroksenOttelut(kid));
        }
        ObservableList<Ottelu> ottelut = FXCollections.observableArrayList();
        for(int i=0; i<otteluidt.size(); i++) {
            ottelut.add(Tietokanta.haeOttelunPelaajat(otteluidt.get(i)));
            ottelut.get(i).setID(otteluidt.get(i));
        }

        //ottelut = KilpailupariController.get_ottelut();
        kierros_id = TulevatTurnauksetController.getKierrosId();
        kierros_nro = Tietokanta.haeTurnauksenKierrokset(turnauksen_id).size();
        //TableColumn1.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("ottelu_id"));
        TableColumnPelaaja1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("Pelaaja1"));
        TableColumnP1pisteet.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("p1pisteet"));;
        TableColumnPelaaja2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("Pelaaja2"));
        TableColumnP2pisteet.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("p2pisteet"));;
        Voittaja.setCellValueFactory(new PropertyValueFactory<Ottelu,String>("Voittaja"));

        //if (kierros_nro > 2){
          //  KierrosTxt.setText("Kierroksen "+kierros_nro+" tulokset");
        //} else {
            KierrosTxt.setText("Tulokset");//Eikös tähän tuu lopulta kaikki tulokset niin siks tää teksti
        //}

        for (Ottelu ottelu : ottelut){
                ottelu.noollaaPisteet();
                ottelu.LisääPisteitä1(Tietokanta.get_pelaaja_ottelu_pisteet(ottelu.getPelaaja1().getId(),ottelu.getID()));
                ottelu.LisääPisteitä2(Tietokanta.get_pelaaja_ottelu_pisteet(ottelu.getPelaaja2().getId(),ottelu.getID()));

                //Etsitään pelaajille nimet
                ottelu.getPelaaja1().setNimi(Tietokanta.getPelaajanNimi(ottelu.getPelaaja1().getId()));
                ottelu.getPelaaja2().setNimi(Tietokanta.getPelaajanNimi(ottelu.getPelaaja2().getId()));

                int i = Tietokanta.getOttelunVoittaja(ottelu.getID());
                //System.out.println(i);
                if(ottelu.getPelaaja1().getId() == i){
                    ottelu.setVoittaja(ottelu.getPelaaja1().getNimi());
                } else if (ottelu.getPelaaja2().getId() == i){
                    ottelu.setVoittaja(ottelu.getPelaaja2().getNimi());
                } else {
                    ottelu.setVoittaja("");
                }
        }
        /*
        / Tämä poistaisi pelaamattomat pelit
        for (int i = 0; i < ottelut.size(); i++){
            if (!Tietokanta.OnkoVoittajaa(ottelut.get(i).getID())){
                ottelut.remove(ottelut.get(i));
            }
        }
        */

        TableViewTulos.setItems(ottelut);
    }

}
