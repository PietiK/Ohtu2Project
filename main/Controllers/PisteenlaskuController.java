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
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Ottelu;
import main.Pelaaja;
import main.Tietokanta;

import java.io.IOException;

public class PisteenlaskuController {



    Ottelu ottelu = KilpailupariController.getOttelu();
    Pelaaja pelaaja_1 = ottelu.getPelaaja1();
    Pelaaja pelaaja_2 = ottelu.getPelaaja2();

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
    private Label p1Label;

    @FXML
    private Label p2Label;

    //Ajastimen hommat
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    @FXML private ToggleButton TaukoBtn;
    @FXML private Label Minuutit;
    @FXML private Label Sekunnit;
    @FXML private Label taukominuutit;
    @FXML private Label taukosekunnit;
    private Duration kesto;
    private Duration taukoKesto;
    private long edellinenAika;
    private static AnimationTimer ajastin;
    private static AnimationTimer taukoajastin;

    @FXML
    void initialize() {
      //Kommentoin nää pois koska muuten jokasella pisteiden lisäyksellä kello nollaantu 
      //ja siirsin nää hommat suoraan noihi lisaaPelaajalle metodeihi
      //mutta laittakaa takasin jos pilasin jotain toimivuutta
        //TableColumn1.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
        //TableColumn2.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("pisteet"));
        System.out.println("ottelu =" + ottelu.getID());
      Minuutit.setText("60");
      Sekunnit.setText("00");

      kesto = Duration.minutes(60);   //Ottelun kesto
      edellinenAika = System.nanoTime();
      //Luodaan ajastin
      ajastin = new AnimationTimer() {
        @Override public void handle(final long NOW) {
          if(!TaukoBtn.isSelected()){       //Jos taukonappi on valittuna niin ollaan tauolla
            if (NOW > edellinenAika + 1_000_000_000l) {
              kesto = kesto.subtract(Duration.seconds(1));
              //Jäljellä oleva aika
              int remainingSeconds = (int) kesto.toSeconds();
              int m = ((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
              int s = (((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) % SECONDS_PER_MINUTE);
              //Lopetetaan jos menee nollille
              if (m == 0 && s == 0) { 
                ajastin.stop(); 
              }
              //asetetaan tekstit labeleihin
              Minuutit.setText(String.format("%02d", m));
              Sekunnit.setText(String.format("%02d", s));
              //nykyhetki viimeiseksi mittaukseksi
              edellinenAika = NOW;
            }
          }
        }
      };
      //Tauon ajastin
      //Toiminnallisuus oikeastaan sama kuin ylemmässä ajastimessa, kesto vain 5min
        taukoKesto = Duration.minutes(5);
        taukoajastin = new AnimationTimer(){
          @Override public void handle(final long NOW) {
              if(NOW > edellinenAika + 1_000_000_000l){
                taukoKesto = taukoKesto.subtract(Duration.seconds(1));
  
                int remainingSeconds = (int) taukoKesto.toSeconds();
                int mi = ((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
                int ss = (((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) % SECONDS_PER_MINUTE);
  
                if (mi == 0 && ss == 0) { 
                  taukoajastin.stop(); 
                }
                
                taukominuutit.setText(String.format("%02d", mi));
                taukosekunnit.setText(String.format("%02d", ss));
                edellinenAika = NOW;
              }  
            }
        };        
    }

    public static void aloitaKello() {  //Aloittaa pelin kellon
      ajastin.start();
    }

    public void aloitaTauko() {  //Aloittaa tauon kellon
      taukoajastin.start();
      if (TaukoBtn.isSelected()) {
        taukominuutit.setVisible(true);
        taukosekunnit.setVisible(true);
      }
      else {
        taukominuutit.setVisible(false);
        taukosekunnit.setVisible(false);
      }
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

      public void Seuraava(ActionEvent event) throws IOException {
        Pelaaja voittaja; 
        String p1p = p1Label.getText(); 
        String p2p = p2Label.getText(); 
        if (Integer.parseInt(p1p) > Integer.parseInt(p2p)) {
          voittaja = pelaaja_1;
        } else { voittaja = pelaaja_2; }

        System.out.println("Pelaaja1id: " + pelaaja_1.getId());
        System.out.println("Pelaaja2id: " + pelaaja_2.getId()); 
        System.out.println("Voittaja: " + voittaja.getNimi()); 
        Tietokanta.OttelunVoittaja(ottelu.getID(), voittaja.getId());

        Stage window = (Stage) TableView.getScene().getWindow(); 
        window.close();
      }
}
