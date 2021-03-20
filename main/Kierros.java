package main;

import java.util.ArrayList;

public class Kierros {
    private int id; 
    private Turnaus turnaus; 
    private ArrayList<Ottelu> ottelut; 
    private int jnum; 
    private ArrayList<Pelaaja> jäljellä; 
    private boolean pelattu; 


    //Kun kierros luodaan, tällä saa turnauksessa jäljellä olevat pelaajat
    public void setJäljellä() {

        this.jäljellä = this.turnaus.Jäljellä(); 
    }

    /*Tällä metodilla voi tarkistaa, onko kaikki sen ottelut pelattu
    ja jos on, asettaa myös kierroksen pelatuksi*/ 

    public boolean KierrosValmis() {
        for (Ottelu o : ottelut) {
            if (!o.getPelattu()) {
                return false; 
            }
        }
        this.pelattu = true; 
        return true; 
    }

    public void setTurnaus(Turnaus t) {
        this.turnaus = t; 
    }

    public int getID() {
        return this.id; 
    }
    public Turnaus getTurnaus() {
        return this.turnaus; 
    }
}


