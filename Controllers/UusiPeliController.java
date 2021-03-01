package Controllers;

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

import java.sql.*;

public class UusiPeliController {

    @FXML
    private AnchorPane Anchorpane;

    @FXML
    private Text LisääPelaajiaTxt;

    @FXML
    private TextField TextField;

    @FXML
    private Button LisääBtn;

    @FXML
    private TableView<Pelaaja> TableView;

    @FXML
    private TableColumn<Pelaaja, String> TablecolumPelaajat;

    @FXML
    void initialize() {
        TablecolumPelaajat.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
        TableView.setItems(Tietokanta.haePelaajat());

        TableView.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, oldValue, newValue) -> {
            if(TableView.getSelectionModel().getSelectedItem() != null) {
                Pelaaja pelaaja = TableView.getSelectionModel().getSelectedItem();
            }
        });
    }
    @FXML
    private TableColumn<?, ?> TableColumnLuku;

    @FXML
    private Button ValmisBtndrop;

    @FXML
    private MenuButton ValitsePeliBtn;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private Text PäivämääräTxt;

    @FXML
    private Button TakaisinBtn;



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
        String nimi = TextField.getText();
        System.out.println(nimi);
        Pelaaja temp = new Pelaaja(nimi);
        Tietokanta.LisaaPelaaja(temp);
        initialize();
    }


}
