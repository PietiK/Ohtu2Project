import java.util.*;
public class pelaaja {

    private String nimi;
    private int pelinro;
    private int voitot;
    private int tappiot;
    private int pisteet;
    private int kokonaispisteet;

    public pelaaja (String nimi) {
        this.nimi=nimi;
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

    public void setPelinro(int pelinro) {
        this.pelinro = pelinro;
    }

    public int getVoitot() {
        return voitot;
    }

    public void setVoitot(int voitot) {
        this.voitot = voitot;
    }

    public int getTappiot() {
        return tappiot;
    }

    public void setTappiot(int tappiot) {
        this.tappiot = tappiot;
    }

    public int getPisteet() {
        return pisteet;
    }

    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
    }

    public int getKokonaispisteet() {
        return kokonaispisteet;
    }

    public void setKokonaispisteet(int kokonaispisteet) {
        this.kokonaispisteet = kokonaispisteet;
    }

    @Override
    public String toString() {
        return "pelaaja{" +
                "nimi='" + nimi + '\'' +
                ", pelinro=" + pelinro +
                '}';
    }
}


