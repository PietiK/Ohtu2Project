package main;

import java.util.ArrayList;
import java.util.Comparator;

public class Pelaaja {

    private int id; 
    private String nimi;
    private int peliNro;
    private int voitto;
    private int tappio;
    private int pisteet;
    private int kokonaispisteet;
    private boolean pelissa; // totuusarvo onko pelaaja vielä pelissä.
    private ArrayList<Pelaaja> pelattu; // lista johon lisätään pelaajat joita vastaan on jo pelattu

    public Pelaaja(String nimi) {
        this.nimi=nimi;
        this.pisteet=0;
        this.pelissa = true;
        this.pelattu = new ArrayList<>();
    }

    public Pelaaja(String nimi, int peliNro) {
      this.nimi=nimi;
      this.peliNro = peliNro;
      this.pisteet=0;
      this.pelissa = true;
      this.pelattu = new ArrayList<>();
  }
    public Pelaaja() {
    }

    public void setTappio(int tappio){
        this.tappio = tappio;
    }
    public int getId() {
        return this.id; 
    }

    public void setId(int id) {
        this.id = id; 
    }
    
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getPeliNro() {
        return peliNro;
    }

    public void setPeliNro(int peliNro) {
        this.peliNro = peliNro;
    }

    public int getVoitto() {
        return voitto;
    }

    public int getTappio() {
        return tappio;
    }

    public int getPisteet() {
        return pisteet;
    }
    // lisätään pelaajalle pisteitä
    //jos pisteet ylittävät 60, asetetaan pisteiksi 60
    public void setPisteet(int pisteet) {
        if(this.pisteet + pisteet >= 60){
            this.pisteet = 60;
        } else {
            this.pisteet += pisteet;
        }
    }

    public int getKokonaispisteet() {
        return kokonaispisteet;
    }
    // HUOM! muokkaa kokonaispisteiden laskemiseksi
    public void setKokonaispisteet(int kokonaispisteet) {
        this.kokonaispisteet = kokonaispisteet;
    }
    // lisätään pelaajalle voitto
    public void lisaaVoitto(){
        this.voitto += 1;
    }
    // lisätään pelaajalle tappio
    // jos 2 tappiota, poistetaan pelaaja pelistä
    public void lisaaTappio(){
        this.tappio += 1;
        if (tappio == 2){
            this.setPelissa(false);
        }
    }

    public boolean isPelissa() {
        return pelissa;
    }

    public void setPelissa(boolean pelissa) {
        this.pelissa = pelissa;
    }
    public ArrayList<Pelaaja> getPelattu(){
        return pelattu;
    }

    // Lisätään pelaaja pelattujen listaan
    public void setPelattu(Pelaaja pelaaja){
        this.pelattu = new ArrayList<>();
        this.pelattu.add(pelaaja);
    }

    public void printPelattu (Pelaaja pelaaja){
        for (Pelaaja p : pelattu){
            System.out.print(p.getNimi() + ", ");
        }
    }

    @Override
    public String toString() {
        return nimi + " | " + peliNro + " | " + voitto + " | " + tappio + " | ";
    }

    //tällä vertaillaan kahden pelaajan eroa, jotta pelaajat saatiin voittojen mukaan järjestykseen
    public static Comparator <Pelaaja> sijoitusNro = new Comparator<Pelaaja>() {
        public int compare(Pelaaja p1, Pelaaja p2) {
            int eka = p1.getVoitto();
            int toka = p2.getVoitto();

            return toka-eka;
        }
    };
}