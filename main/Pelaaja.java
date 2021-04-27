package main;

import java.util.ArrayList;
import java.util.Comparator;

public class Pelaaja {

    private int id; 
    private String nimi;
    private int pelinro;
    private int voitto;
    private int tappio;
    private int pisteet;

    public int getSyötetyt() {
        return syötetyt;
    }

    public void setSyötetyt(int syötetyt) {
        this.syötetyt = syötetyt;
    }

    private int syötetyt;
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
      this.pelinro = peliNro;
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

    public int getPelinro() {
        return pelinro;
    }

    public void setPeliNro(int peliNro) {
        this.pelinro = peliNro;
    }

    public int getVoitto() {
        return voitto;
    }

    public void setVoitto(int lukum){
        this.voitto = lukum;
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

            this.pisteet += pisteet;

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

    public void setPelattujj(){
      this.pelattu = new ArrayList<Pelaaja>();
    }

    public void addPelattu(Pelaaja pel){
      this.pelattu.add(pel);
    }

    public ArrayList<Integer> getPelattujenIdt(){ //Palauttaa pelaajan vastustajien id:t
      ArrayList<Integer> idt = new ArrayList<>();
      for(Pelaaja p : this.pelattu) {
        idt.add(p.getId());
      }
      return idt;
    }

    public void printPelattu (Pelaaja pelaaja){
        for (Pelaaja p : pelattu){
            System.out.print(p.getNimi() + ", ");
        }
    }

    @Override
    public String toString() {
        return nimi;
        //nää tulostu siihen TableVieWiin mukaan
        //+ " | " + peliNro + " | " + voitto + " | " + tappio + " | ";
    }

    //tällä vertaillaan kahden pelaajan eroa, jotta pelaajat saatiin voittojen mukaan järjestykseen
    public static Comparator <Pelaaja> sijoitusNro = new Comparator<Pelaaja>() {
        public int compare(Pelaaja p1, Pelaaja p2) {
            int eka = p1.getVoitto();
            int toka = p2.getVoitto();

            return toka-eka;
        }
    };

    public static Comparator <Pelaaja> peliNroSorter = new Comparator<Pelaaja>() {
        @Override
        public int compare(Pelaaja p1, Pelaaja p2) {
            return Integer.valueOf(p2.getPelinro()).compareTo(Integer.valueOf(p1.getPelinro()));
        }
    };

}