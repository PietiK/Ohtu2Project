package main.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;
import main.Pelaaja;
import main.Ottelu;
import main.Tietokanta;

import java.io.IOException;

public class PisteenlaskuController {

  private static final int SECONDS_PER_DAY = 86400;
  private static final int SECONDS_PER_HOUR = 3600;
  private static final int SECONDS_PER_MINUTE = 60;
  @FXML private TextField minutes;
  @FXML private TextField seconds;
  private Duration kesto;
  private long lastTimerCall;
  private AnimationTimer ajastin;

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
    private TextField Minuutit;
    @FXML
    private TextField Sekunnit;

    @FXML
    void initialize() {
      //Kommentoin nää pois koska muuten jokasella pisteiden lisäyksellä kello nollaantu 
      //ja siirsin nää hommat suoraan noihi lisaaPelaajalle metodeihi
      //mutta laittakaa takasin jos pilasin jotain toimivuutta
        //TableColumn1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
        //TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
        
      Minuutit.setText("0");
      Sekunnit.setText("0");

      kesto = Duration.minutes(60);

      lastTimerCall = System.nanoTime();
      ajastin = new AnimationTimer() {
        @Override public void handle(final long NOW) {
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
      };
    }
public void aloitaKello(ActionEvent event) throws IOException {
  ajastin.start();
}
    public void lisaaPelaajalle1(ActionEvent event) throws IOException {
        int pisteet = Integer.valueOf(TextField1.getText());
        pelaaja_1.setPisteet(pisteet);
        System.out.println(pelaaja_1.getPisteet());
        //Tietokanta.LisaaPisteita(pelaajaid, otteluid, pisteet);
        TextField1.setText("");
        //initialize();
        TableColumn1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
    }

    public void lisaaPelaajalle2(ActionEvent event) throws IOException {
        int pisteet = Integer.valueOf(TextField2.getText());
        pelaaja_2.setPisteet(pisteet);
        System.out.println(pelaaja_2.getPisteet());
        //Tietokanta.LisaaPisteita(pelaajaid, otteluid, pisteet);
        TextField2.setText("");
        //initialize();
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
      }

    }
