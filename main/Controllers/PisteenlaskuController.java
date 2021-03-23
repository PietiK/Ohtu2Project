package main.Controllers;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.Ottelu;
import main.Pelaaja;
import main.Tietokanta;

import java.io.IOException;

public class PisteenlaskuController {



    Ottelu ottelu = KilpailupariController.getOttelu();
    Pelaaja pelaaja_1 = KilpailupariController.ottelu.getPelaaja1();
    Pelaaja pelaaja_2 = KilpailupariController.ottelu.getPelaaja2();

    //tähän varmaan pitäisi luoda jokin Ottelu?

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TableView<Pelaaja> TableView;
    @FXML
    private TableView<Pelaaja> TableView2;
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
    private Label Minuutit;
    @FXML
    private Label Sekunnit;

    @FXML
    private Label p1Label;

    @FXML
    private Label p2Label;

    //Ajastimen hommat
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    @FXML private TextField minutes;
    @FXML private TextField seconds;
    @FXML private ToggleButton PauseBtn;
    private Duration kesto;
    private long lastTimerCall;
    private AnimationTimer ajastin;

    @FXML
    void initialize() {
      //Kommentoin nää pois koska muuten jokasella pisteiden lisäyksellä kello nollaantu 
      //ja siirsin nää hommat suoraan noihi lisaaPelaajalle metodeihi
      //mutta laittakaa takasin jos pilasin jotain toimivuutta
        //TableColumn1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
        //TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
        System.out.println("ottelu =" + ottelu.getID());
      Minuutit.setText("60");
      Sekunnit.setText("0");

      kesto = Duration.minutes(60);

      lastTimerCall = System.nanoTime();
      ajastin = new AnimationTimer() {
        @Override public void handle(final long NOW) {
          if (!PauseBtn.isSelected()) {
            if (NOW > lastTimerCall + 1_000_000_000l) {
              kesto = kesto.subtract(Duration.seconds(1));

              int remainingSeconds = (int) kesto.toSeconds();
              int m = ((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
              int s = (((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) % SECONDS_PER_MINUTE);

              if (m == 0 && s == 0) { 
                ajastin.stop(); 
              }

              Minuutit.setText(String.format("%02d", m));
              Sekunnit.setText(String.format("%02d", s));

              lastTimerCall = NOW;
            }
          }
        }
      };
    }
    public void aloitaKello(ActionEvent event) throws IOException {
      ajastin.start();
    }

    public void lisaaPelaajalle1(ActionEvent event) throws IOException {
        int pisteet = Integer.valueOf(TextField1.getText());
        pelaaja_1.setPisteet(pisteet);
        //System.out.println(pelaaja_1.getPisteet());
        Tietokanta.LisaaPisteita(pelaaja_1.getId(), ottelu.getID(), pelaaja_1.getPisteet());

        TableView.getItems().add(new Pelaaja(Integer.toString(pisteet)));
        TableColumn1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));
        TextField1.setText("");

        //initialize();
        p1Label.setText(Integer.toString(pelaaja_1.getPisteet()));

    }

    public void lisaaPelaajalle2(ActionEvent event) throws IOException {
        int pisteet = Integer.valueOf(TextField2.getText());

        pelaaja_2.setPisteet(pisteet);
        //System.out.println(pelaaja_2.getPisteet());
        Tietokanta.LisaaPisteita(pelaaja_2.getId(), ottelu.getID(), pelaaja_2.getPisteet());
        TableView2.getItems().add(new Pelaaja(Integer.toString(pisteet)));
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("nimi"));

        TextField2.setText("");
        //initialize();
        p2Label.setText(Integer.toString(pelaaja_2.getPisteet()));

      }

    }
