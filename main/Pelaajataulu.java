import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Pelaajataulu {

    private ArrayList<Pelaaja> pelaajat;
    private ArrayList<Pelaaja> pudonneet;
    int i;
    List<Ottelu> kierros = new ArrayList<>();

    public Pelaajataulu() {
        pelaajat = new ArrayList<>();
        pudonneet = new ArrayList<>();
        //i = 0;
    }

    //Lisätään pelaaja
    public void setPelaaja(Pelaaja pelaaja) {
        pelaajat.add(pelaaja);
        //pelaajat.get(i).setPeliNro(i+1);
        //i++;

        //Arvotaan pelaajille numerot
        //Numerot pitää jakaa jokasen pelaajan jälkeen uusiks
        arvoNumerot(pelaajat);
    }
    // Haetaan pelaaja pelaajanro:lla
    // Jos tätä ei tarvitsekkaan, niin poistetaan
    public Pelaaja getPelaaja(int nro) {
        Pelaaja temp = new Pelaaja( "a");
        for (int j = 0; j < this.pelaajat.size(); j++){
            if(this.pelaajat.get(j).getPeliNro() == nro){
                temp = this.pelaajat.get(j);
            }
        }
        return temp;
    }
    // Haetaan pelinroa pelaajalla
    public int getPelinro(Pelaaja pelaaja){
        int temp = 0;
        for(int j = 0; j < this.pelaajat.size(); j++){
            if (pelaajat.get(j).getNimi().equals(pelaaja.getNimi())) {
                temp = pelaajat.get(j).getPeliNro();
            }
        }
        return temp;
    }
    // Asetetaan pelaajalle pisteitä
    public void setPisteita(Pelaaja pelaaja, int pisteet){
        int nro = getPelinro(pelaaja);
        this.pelaajat.get(nro-1).setPisteet(pisteet);
    }
    // Käydään pelaajat lista läpi
    // jos pelaaja on pudonnut niin se siirretään pudonneet listaan
    public void setPudonnut(){
        for (Pelaaja p : pelaajat){
            if (!p.isPelissa()){
                pudonneet.add(p);
            }
        }
        pelaajat.removeAll(pudonneet);
    }

    /* Testi, jolla arvotaan pelaajille voitto tai tappio ja katsotaan putoavatko he luettelosta
    public void arvoVoittaja() {
        for (int i = 0; i < pelaajat.size(); i++) {
            int tmp = (int) (Math.random() * 2 + 1);
            if (tmp == 1){
                pelaajat.get(i).lisaaVoitto();
            } else {
                pelaajat.get(i).lisaaTappio();
            }
        }
        this.setPudonnut();
    }
    */

    // Tulostaa pelaajat sijoituksen mukaan
    public void printPelaajat(){
        int sijoitus = 1;
        ArrayList<Pelaaja> temp = new ArrayList<>(pelaajat);
        Collections.sort(temp, Pelaaja.sijoitusNro);
        for (int j = 0; j < temp.size(); j++ ) {
            System.out.println("Sijoitus: " + sijoitus + " | " + temp.get(j).toString());
            sijoitus++;
        }

        if (pudonneet.size() > 0){
            System.out.println("Poissa pelistä");
            for (Pelaaja p : pudonneet) {
                System.out.println("Sijoitus: " + sijoitus + " | " + p.toString());
                sijoitus++;
            }
        }
        temp.clear();
        System.out.println("");
    }
    
    //Palauttaa kaikki pelaajat
    public ArrayList<Pelaaja> getPelaajataulu() {
        return pelaajat;
    }

    // Jaetaan ottelupareja
    // HUOM! Puuttuu vielä, miten huomioidaan jos kaikki ovat jo pelanneet toisiaan vastaan eli joutuvat pelaamaan uudestaan keskenään
    public void jaaOtteluparit() {
        // Jos pelaajia on pariton määrä, lisätään pelaaja jolle eie löydy paria tälle listaan
        List<Pelaaja> ylimaarainen = new ArrayList<>();

        if (pelaajat.size() > 1) {
            for (int i = 0; i < pelaajat.size(); i++){
                for (int j = 0; j < pelaajat.size(); j++) {
                    // Tarkistetaan ovatko pelaajat pelanneet toisiaan vastaan
                    if (pelaajat.get(i).equals(pelaajat.get(j)) || pelaajat.get(i).getPelattu().contains(pelaajat.get(j))) {
                    } else {
                        kierros.add(new Ottelu(pelaajat.get(i), pelaajat.get(j))); // Lisätään pelaajat otteluun
                        pelaajat.get(i).setPelattu(pelaajat.get(j)); // Lisätään vastustaja pelattujen listalle
                        pelaajat.get(j).setPelattu(pelaajat.get(i)); // Lisätään vastustaja pelattujen listalle
                        pelaajat.remove(pelaajat.get(j)); // Poistetaan arvotut pelaajat listalta
                        pelaajat.remove(pelaajat.get(i)); // Poistetaan arvotut pelaajat listalta
                        // Nollataan indeksit
                        i = 0;
                        j = 0;
                    }
                }
            }
        }

        //Tulostetaan kierroksen ottelut
        for (Ottelu o : kierros) {
            System.out.println(o.toString());
        }
        System.out.println("");

        // Tuodaan pelaajat takaisin pelaajat listaan
        // Ensin tuodaan otteluparin eka pelaaja ja sitten toinen
        for (Ottelu o : kierros) {
            pelaajat.add(o.getPelaaja1());
            pelaajat.add(o.getPelaaja2());
        }
        kierros.clear();
    }

    //Metodi joka jakaa pelaajille pelinumerot
	public void arvoNumerot(ArrayList<Pelaaja> pelaajat) {
		
		//Taulukko pelaajien numeroille
		int randomi[] = new int[pelaajat.size()];

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
	public void sekoita(int[] taulu)
    {
      Random rnd = ThreadLocalRandom.current();
      for (int i=taulu.length-1; i>0; i--)
      {
        int index = rnd.nextInt(i + 1);
        int a = taulu[index];
        taulu[index] = taulu[i];
        taulu[i] = a;
      }
    }
}