package main.Controllers;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.Pelaaja;
import main.Tietokanta;
import main.Turnaus;

public class PelaajalistaController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TableView<Pelaaja> TableView;

    @FXML
    private TableColumn<Pelaaja, Integer> TableColumnNro;

    @FXML
    private TableColumn<Pelaaja, String> TableColumnNimi;

    @FXML
    void initialize() {
        TableColumnNro.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("pelinro"));
        TableColumnNimi.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
        Integer tid = TulevatTurnauksetController.getTurnaus_id();
        System.out.println("Turnaus: " + tid); 
        Turnaus t = Tietokanta.haeTurnaus(tid); 
        ArrayList<Pelaaja> pelaajat = Tietokanta.TurnauksenPelaajat(t); 
        ObservableList<Pelaaja> pelaajatO = FXCollections.observableArrayList();
        System.out.println(pelaajat); 
        for (Pelaaja p : pelaajat) {
            pelaajatO.add(p); 
        }
        System.out.println(pelaajatO);

        TableView.setItems(pelaajatO); 
        
    }
}

