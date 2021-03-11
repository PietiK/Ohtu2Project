package main.Controllers;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Pelaaja;
import main.Tietokanta;
import main.Ottelu;

public class KilpailupariController{

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text EnsimmäinenTxt;

    @FXML
    private TableView<Ottelu> TableView;

    @FXML
    private TableColumn<Pelaaja, String> TableColmun1;

    @FXML
    private TableColumn<Pelaaja, String> TableColumn2;

    @FXML
    private Button TakaisinBtn;

    @FXML
    private Button SeuraavaBtn;

    //HUOM! KESKEN
    //
    //
    @FXML
    void initialize() {
        TableColmun1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("Pelaaja1"));
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("Pelaaja2"));
       // TableView.setItems(Tietokanta.haeKilpailuparinPelaajat());

        TableView.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, oldValue, newValue) -> {
            if (TableView.getSelectionModel().getSelectedItem() != null) {
                Ottelu ottelu = TableView.getSelectionModel().getSelectedItem();
                //Pelaaja eka = ottelu.getPelaaja1();
                //Pelaaja toka = ottelu.getPelaaja2();

            }
        });
    }

    
    @FXML
    public void Pelaa(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/Pisteenlaskunäkymä.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene PisteS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(PisteS);
        window.show(); 
    }
    //siirry TulevatPelit näkymään
    @FXML
    public void SiirryTakaisin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/TulevatPelit.fxml"));
        Parent AloitusNayttoP = loader.load();
        Scene AloitusS = new Scene(AloitusNayttoP);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(AloitusS);
        window.show();
    }
}
