import java.util.*;

public class Turnaus {
	
	private ArrayList<ottelu> ottelut;
	private pelaaja voittaja;
	private pelaaja toinen;
	private pelaaja kolmas;
	
	public Turnaus(ArrayList<ottelu> lista) {
		this.ottelut = lista;
	}
	
	public Turnaus(ArrayList<ottelu> lista, pelaaja voittaja, pelaaja toinen, pelaaja kolmas) {
		this.ottelut = lista;
		this.voittaja = voittaja;
		this.toinen = toinen;
		this.kolmas = kolmas;
	}
	
	public void lisaaOttelu(ottelu o) {
		this.ottelut.add(o);
	}
	
	public void setVoittaja(pelaaja voittaja) {
		this.voittaja = voittaja;
	}
	
	public void setToinen(pelaaja toinen) {
		this.toinen = toinen;
	}
	
	public void setKolmas(pelaaja kolmas) {
		this.kolmas = kolmas;
	}
	
	public ArrayList<ottelu> getOttelut() {
		return ottelut;
	}
	
	public pelaaja getVoittaja() {
		return voittaja;
	}
	
	public pelaaja getToinen() {
		return toinen;
	}
	
	public pelaaja getKolmas() {
		return kolmas;
	}
	
	
	
}
