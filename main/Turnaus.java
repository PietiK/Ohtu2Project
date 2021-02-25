import java.util.*;

public class Turnaus {
	
	private String pelityyppi; 
	private String pvm; 
	private ArrayList<Ottelu> ottelut;
	private Pelaajataulu pelaajat = new Pelaajataulu();

	/*private Pelaaja voittaja;
	private Pelaaja toinen;
	private Pelaaja kolmas;*/
	
	public Turnaus(ArrayList<Ottelu> lista) {
		this.ottelut = lista;
	}
	
	public Turnaus(ArrayList<Ottelu> ottelulista, Pelaajataulu pelaajalista) {
		this.ottelut = ottelulista;
		this.pelaajat = pelaajalista;
	}
	
	public void lisaaOttelu(Ottelu o) {
		this.ottelut.add(o);
	}
	
	public ArrayList<Ottelu> getOttelut() {
		return ottelut;
	}
	
	public ArrayList<Pelaaja> getPelaajat() {
		return pelaajat.getPelaajataulu();
	}

	/*
	public void setVoittaja(Pelaaja voittaja) {
		this.voittaja = voittaja;
	}
	
	public void setToinen(Pelaaja toinen) {
		this.toinen = toinen;
	}
	
	public void setKolmas(Pelaaja kolmas) {
		this.kolmas = kolmas;
	}
	
	public Pelaaja getVoittaja() {
		return voittaja;
	}
	
	public Pelaaja getToinen() {
		return toinen;
	}
	
	public Pelaaja getKolmas() {
		return kolmas;
	}*/
	
}
