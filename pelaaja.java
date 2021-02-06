import java.util.*;
public class pelaaja {

    private String nimi;
    private int pelinro;
    private int voitot;
    private int tappiot;
    private int pisteet;
    private int kokonaispisteet;
    private boolean pudonnut; // totuusarvo onko pelaaja vielä pelissä.

    public pelaaja (String nimi) {
        this.nimi=nimi;
        this.pudonnut = false;
    }
    //getter nimelle
    public String getNimi() {
        return nimi;
    }
    //setteri Nimelle
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    //setter pelinumerolle
    public int getPelinro() {
        return pelinro;
    }
    //setter pelinumerolle
    public void setPelinro(int pelinro) {
        this.pelinro = pelinro;
    }
    //getter voitoille
    public int getVoitot() {
        return voitot;
    }
    //setter voitoille
    public void setVoitot(int voitot) {
        this.voitot = voitot;
    }
    //getterr Tappioille
    public int getTappiot() {
        return tappiot;
    }
    //setter Tappioille
    public void setTappiot(int tappiot) {
        this.tappiot = tappiot;
    }
    //getter pisteille
    public int getPisteet() {
        return pisteet;
    }
    //setter pisteille
    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
    }
    //getter kokonaispisteille
    public int getKokonaispisteet() {
        return kokonaispisteet;
    }
    //setter kokonaispisteille
    public void setKokonaispisteet(int kokonaispisteet) {
        this.kokonaispisteet = kokonaispisteet;
    }
    //getter
    public boolean isPudonnut() {
        return pudonnut;
    }
    //setter totuusarvolle onko pelaaja pudonnut kilpailusta
    public void setPudonnut(boolean pudonnut) {
        this.pudonnut = pudonnut;
    }

    @Override
    public String toString() {
        return "pelaaja{" +
                "nimi='" + nimi + '\'' +
                ", pelinro=" + pelinro +
                '}';
    }
}


