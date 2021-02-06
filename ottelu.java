import java.util.*;
public class ottelu {

    private pelaaja Pelaaja1;
    private pelaaja Pelaaja2;
    private String otnimi;
    private int p1pisteet;
    private int p2pisteet;

    public ottelu (pelaaja eka, pelaaja toka) {
        this.Pelaaja1 = eka;
        this.Pelaaja2 = toka;
    }
    public pelaaja getPelaaja1() {
        return Pelaaja1;
    }

    public void setPelaaja1(pelaaja pelaaja1) {
        Pelaaja1 = pelaaja1;
    }

    public pelaaja getPelaaja2() {
        return Pelaaja2;
    }

    public void setPelaaja2(pelaaja pelaaja2) {
        Pelaaja2 = pelaaja2;
    }

    public String getOtnimi() {
        return otnimi;
    }
    public void setOtnimi(String otnimi) {
        this.otnimi = otnimi;
    }

    public int getP1pisteet() {
        return p1pisteet;
    }

    public void setP1pisteet(int p1pisteet) {
        this.p1pisteet = p1pisteet;
    }

    public int getP2pisteet() {
        return p2pisteet;
    }

    public void setP2pisteet(int p2pisteet) {
        this.p2pisteet = p2pisteet;
    }
}
