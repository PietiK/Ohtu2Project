package main;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/*
-------------------------------------------------------------------
TÄÄ LUOKKA ON TURHA KOSKA KOODI ON MYÖS Pelaajataulu.java -LUOKASSA
-------------------------------------------------------------------
*/

public class PelaajaNumerot {
	//Metodi joka jakaa pelaajille pelinumerot
	public static void arvoNumerot(ArrayList<Pelaaja> pelaajat) {
		
		//Taulukko pelaajien numeroille
		int randomi[] = new int[pelaajat.size()];
		int alku = 1; 

		for (int i = 0; i < pelaajat.size(); i ++) {
			randomi[i] = alku; 
			alku++; 
		}


		//sekoitetaan taulukko
		sekoita(randomi);

		//Jaetaan pelaajille numerot
		int numero = 0;
		for (Pelaaja pelaaja : pelaajat) {
			pelaaja.setPeliNro(randomi[numero]);
			numero += 1;
		}
	}
	
	//metodi joka sekoittaa annetun taulukon
	public static void sekoita(int[] taulu)
	  {
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = taulu.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      int a = taulu[index];
	      taulu[index] = taulu[i];
	      taulu[i] = a;
	    }
	  }
	
}
