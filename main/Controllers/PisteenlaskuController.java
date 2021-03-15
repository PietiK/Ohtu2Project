package main.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.Pelaaja;
import main.Ottelu;
import main.Tietokanta;

import java.io.IOException;

public class PisteenlaskuController {

    // väliaikaiset pelaajat, jotta sai testattua pelaajien pisteitä.
    Pelaaja pelaaja_1 = new Pelaaja("eka", 2);
    Pelaaja pelaaja_2 = new Pelaaja("toka", 3);

    //tähän varmaan pitäisi luoda jokin Ottelu?

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<Pelaaja, String> TableColumn1;

    @FXML
    private TableColumn<Pelaaja, String> TableColumn2;

    @FXML
    private TextField TextField1;

    @FXML
    private Button LisääBtn1;

    @FXML
    private Button LisääBtn2;

    @FXML
    private TextField TextField2;

    @FXML
    void initialize() {
        TableColumn1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
    }

    public void lisaaPelaajalle1(ActionEvent event) throws IOException {
        int pisteet = Integer.valueOf(TextField1.getText());
        pelaaja_1.setPisteet(pisteet);
        System.out.println(pelaaja_1.getPisteet());
        //Tietokanta.LisaaPisteita(pelaajaid, otteluid, pisteet);
        TextField1.setText("");
        initialize();

    }

    public void lisaaPelaajalle2(ActionEvent event) throws IOException {
        int pisteet = Integer.valueOf(TextField2.getText());
        pelaaja_2.setPisteet(pisteet);
        System.out.println(pelaaja_2.getPisteet());
        //Tietokanta.LisaaPisteita(pelaajaid, otteluid, pisteet);
        TextField2.setText("");
        initialize();
    }

    }
