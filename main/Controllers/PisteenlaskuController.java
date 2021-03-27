package main.Controllers;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
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

    //Ajastimien hommat
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
    private int taukolaskuri = 0;

    @FXML
    void initialize() {
      System.out.println("ottelu =" + ottelu.getID());
      Minuutit.setText("60");
      Sekunnit.setText("00");
      kesto = Duration.minutes(0.1);   //Ottelun kesto
      //taukoKesto = Duration.minutes(5); //tauon kesto
      edellinenAika = System.nanoTime();
      //Luodaan ajastin
      ajastin = new Ajastin();
      //Tauon ajastin
      taukoajastin = new Taukoajastin();
    }

    public static void aloitaKello() {  //Aloittaa pelin kellon
      ajastin.start();
    }

    public void aloitaTauko() {  //Aloittaa tauon kellon
      if (taukolaskuri < 2){
        taukoKesto = Duration.minutes(5);
        taukoajastin.start();
        if (TaukoBtn.isSelected()) {
          taukominuutit.setVisible(true);
          taukosekunnit.setVisible(true);
        }
      else {
        taukominuutit.setVisible(false);
        taukosekunnit.setVisible(false);
        taukolaskuri++;
        if (taukolaskuri >= 2) {
          TaukoBtn.setVisible(false);
        }
      }
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
        p2Label.setText(Integer.toString(pelaaja_2.getPisteet()));

      }

    public void Seuraava(ActionEvent event) throws IOException {
      Pelaaja voittaja; 
      Pelaaja häviäjä; 
      String p1p = p1Label.getText(); 
      String p2p = p2Label.getText(); 
      int p1pis = Integer.parseInt(p1p);
      int p2pis = Integer.parseInt(p2p); 
      
      if (Integer.parseInt(p1p) > Integer.parseInt(p2p)) {
        voittaja = pelaaja_1;
        häviäjä = pelaaja_2; 
      } else { voittaja = pelaaja_2; häviäjä = pelaaja_1; }
      int turnausid = TulevatTurnauksetController.getTurnaus_id();
      int vanhat1pisteet = Tietokanta.HaeTPisteet(turnausid, pelaaja_1.getId());
      int vanhat2pisteet = Tietokanta.HaeTPisteet(turnausid, pelaaja_2.getId());
      int tappiot = Tietokanta.HaeTappiot(turnausid, häviäjä.getId()); 
      System.out.println("Tappiot: " + tappiot);
      int lisatty = tappiot +1; 
      System.out.println("Lisatty tappio: " + lisatty); 
      Tietokanta.OttelunVoittaja(ottelu.getID(), voittaja.getId());
      Tietokanta.LisaaTappio(turnausid, häviäjä.getId(), lisatty);
      Tietokanta.LisaaTPisteita(turnausid, pelaaja_1.getId(), p1pis + vanhat1pisteet);
      Tietokanta.LisaaTPisteita(turnausid, pelaaja_2.getId(), p2pis + vanhat2pisteet); 

      Stage window = (Stage) TableView.getScene().getWindow(); 
      window.close();
    }

    //Ottelun kello
    class Ajastin extends AnimationTimer {
      @Override public void handle(final long NOW) {
        if(!TaukoBtn.isSelected()){       //Jos taukonappi on valittuna niin ollaan tauolla
          if (NOW > edellinenAika + 1_000_000_000l) {
            kesto = kesto.subtract(Duration.seconds(1));
            //Jäljellä oleva aika
            int remainingSeconds = (int) kesto.toSeconds();
            int m = remainingSeconds / SECONDS_PER_MINUTE;
            int s = remainingSeconds % SECONDS_PER_MINUTE;
            //Lopetetaan jos menee nollille
            if (m == 0 && s == 0) { 
              ajastin.stop(); 
              aikaLoppui();
            }
            //asetetaan tekstit labeleihin
            Minuutit.setText(String.format("%02d", m));
            Sekunnit.setText(String.format("%02d", s));
            //nykyhetki viimeiseksi mittaukseksi
            edellinenAika = NOW;
          }
        }
      }
    }
    //Tauon kello
    class Taukoajastin extends AnimationTimer {
      @Override public void handle(final long NOW) {
        if(NOW > edellinenAika + 1_000_000_000l){
          taukoKesto = taukoKesto.subtract(Duration.seconds(1));

          int remainingSeconds = (int) taukoKesto.toSeconds();
          int mi = remainingSeconds / SECONDS_PER_MINUTE;
          int ss = remainingSeconds % SECONDS_PER_MINUTE;

          if (mi == 0 && ss == 0) { //Jos aika loppuu niin tauko päättyy
            taukoajastin.stop(); 
            TaukoBtn.setSelected(false);
            taukominuutit.setVisible(false);
            taukosekunnit.setVisible(false);
            taukolaskuri++;
            //Jos ollut jo kaksi taukoa niin poistetaan tauko-nappi
            if (taukolaskuri >= 2) {
              TaukoBtn.setVisible(false);
            }
          }
          
          taukominuutit.setText(String.format("%02d", mi));
          taukosekunnit.setText(String.format("%02d", ss));
          edellinenAika = NOW;
        }  
      }
    }
    //Näyttää ilmoituksen kun pelin aika loppuu
    public void aikaLoppui(){
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setHeaderText("HUOMIO");
      alert.setContentText("Aika loppui");
      alert.show();
    }

}
