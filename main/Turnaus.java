package main;

import java.util.*;
import java.util.Map.Entry;

public class Turnaus {

	private String pelityyppi;
	private String pvm;
	private ArrayList<Kierros> kierrokset;
	private Map<Pelaaja, Integer> tappiot;
	private Pelaajataulu pelaajat = new Pelaajataulu();


	/*
	 * private Pelaaja voittaja; private Pelaaja toinen; private Pelaaja kolmas;
	 */

	// Tällä metodilla lisätään tappio pelaajalle ottelun lopussa
	public void LisääTappio(Pelaaja p) {
		this.tappiot.put(p, PelaajanTappiot(p) +1);
	}

	public Integer PelaajanTappiot(Pelaaja p) {
		return this.tappiot.get(p); 
	}

	// Tämä metodi palauttaa turnauksessa jäljellä olevat pelaajat
	public ArrayList<Pelaaja> Jäljellä() {
		ArrayList<Pelaaja> jäljellä = new ArrayList<>();
		for (Entry<Pelaaja, Integer> pelaaja : this.tappiot.entrySet()) {
			if (pelaaja.getValue() < 2) {
				jäljellä.add(pelaaja.getKey()); 
			}
		}
		return jäljellä; 
	}
	
	/* Laitoin tästä metodeita kommenteiksi koska turnauksella ei ole enää otteluita vaan
	kierroksella t Siru

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
