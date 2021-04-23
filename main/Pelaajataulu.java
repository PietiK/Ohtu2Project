package main;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Pelaajataulu {

    private ArrayList<Pelaaja> pelaajat;
    private ArrayList<Pelaaja> pudonneet;
    int i;


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
            if(this.pelaajat.get(j).getPelinro() == nro){
                temp = this.pelaajat.get(j);
            }
        }
        return temp;
    }

    //Haetaan pelaaja idllä
    public static Pelaaja getPelaajaWithID(int id){
     /* Pelaaja temp = null;
      ArrayList<Pelaaja> pel = new ArrayList<>();
      pel.addAll(Tietokanta.getTurnauksenPelaajat());
      for(int i=0; i<pel.size(); i++){
        if(pel.get(i).getId() == id)
          temp = pel.get(i);
      }*/
      return Tietokanta.getPelaaja(id);
    }
    
    public ArrayList<Pelaaja> getPelaajat() {
        return this.pelaajat; 
    }
    // Haetaan pelinroa pelaajalla
    public int getPelinro(Pelaaja pelaaja){
        int temp = 0;
        for(int j = 0; j < this.pelaajat.size(); j++){
            if (pelaajat.get(j).getNimi().equals(pelaaja.getNimi())) {
                temp = pelaajat.get(j).getPelinro();
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

    // Nyt tämä asettaa pelaajat tämän luokan "kierros"-listaan.
    // pitäiskö tämä muuttaa muottoon public ArrayList<Ottelu>?

    // HUOM! Koska pelaajat haetaan aina tietokannasta, pitää katsoa uudestaan miten tarkistetaan ovatko pelaajat pelannee keskenään.
    // ensimmäinen kierros toimii hyvin parittomilla ja parillisilla määrillä.

    public List<Ottelu> jaaOtteluparit() {
        // Jos pelaajia on pariton määrä, lisätään pelaaja jolle eie löydy paria tälle listaan
        List<Ottelu> kierros = new ArrayList<>();
        //kierros.clear();
        List<Pelaaja> ylimaarainen = new ArrayList<>();
        Pelaaja temp = pelaajat.get(0);
        //System.out.println(pelaajat.size());
        //System.out.println("testi " + pelaajat.get(0).getPelattu());
        if (pelaajat.size() > 0 && pelaajat.get(0).getPelattu() == null) {
            System.out.println("a");
            for (int i = 0; i < pelaajat.size(); i++){
                for (int j = 0; j < pelaajat.size(); j++) {
                    // Tarkistetaan ovatko pelaajat pelanneet toisiaan vastaan
                        if (pelaajat.get(i).equals(pelaajat.get(j))){
                        } else {
                            kierros.add(new Ottelu(pelaajat.get(i), pelaajat.get(j), 1)); // Lisätään pelaajat otteluun
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
        else if (pelaajat.size() % 2 != 0 && pelaajat.get(0).getPelattu().size() == 1) {
            System.out.println("b");
            for (int i = 0; i < pelaajat.size(); i++){
                for (int j = 0; j < pelaajat.size(); j++) {
                    // Tarkistetaan ovatko pelaajat pelanneet toisiaan vastaan
                    if (pelaajat.get(i).equals(pelaajat.get(j)) || pelaajat.get(i).getPelattu().contains(pelaajat.get(j))) {
                    } else {
                        kierros.add(new Ottelu(pelaajat.get(i), pelaajat.get(j),2)); // Lisätään pelaajat otteluun
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

            if (pelaajat.size() == 1){
                kierros.add(new Ottelu(pelaajat.get(0),temp,2));
            }
        }
        else if (pelaajat.size() > 1 && pelaajat.get(0).getPelattu().size() > 2) {
            System.out.println("c");
            for (int i = 0; i < pelaajat.size(); i++){
                for (int j = 0; j < pelaajat.size(); j++) {
                    // Tarkistetaan ovatko pelaajat pelanneet toisiaan vastaan
                    if (pelaajat.get(i).equals(pelaajat.get(j)) || pelaajat.get(i).getPelattu().contains(pelaajat.get(j))) {
                    } else {
                        kierros.add(new Ottelu(pelaajat.get(i), pelaajat.get(j),1)); // Lisätään pelaajat otteluun
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
        /*
        for (Ottelu o : kierros) {
            System.out.println(o.toString());
        }
        System.out.println("");
        */
        // Tuodaan pelaajat takaisin pelaajat listaan
        // Ensin tuodaan otteluparin eka pelaaja ja sitten toinen
        for (Ottelu o : kierros) {
            if (!pelaajat.contains(o.getPelaaja1())){
                pelaajat.add(o.getPelaaja1());
            }
            if (!pelaajat.contains(o.getPelaaja2())){
                pelaajat.add(o.getPelaaja2());
            }
            //pelaajat.add(o.getPelaaja2());
        }
        return kierros;
    }
    
    //Jakaa jäljellä olevista pelaajista parit
    //Parametrina jaettavat pelaajat, kierrosluku ja turnauksen id
    public static ArrayList<Ottelu> jaaSeuraavaKierros(ArrayList<Pelaaja> pelaajat, int kierrosluku, int turnId){
      ArrayList<Ottelu> seuraavat = new ArrayList<>();  //Seuraavien otteluiden lista
      ArrayList<Pelaaja> pelurit = new ArrayList<>();
      pelurit.addAll(pelaajat);
      //Collections.shuffle(pelurit);
      //Jos pelaajia enemmän kuin neljä niin jaetaan automaattisesti
      if(pelurit.size() < 1000) {
          //Hakee jokaisen pelaajan entiset vastustajat
          for(Pelaaja p : pelurit) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.addAll(Tietokanta.haePelatut(p.getId()));
            p.setPelattujj();
            for(int i : temp) {   //Lisätään pelaajan pelattuihin jos ei ole jo siellä
             // if(!p.getPelattujenIdt().contains(getPelaajaWithID(i).getId())){
                p.addPelattu(getPelaajaWithID(i));
              //}
            }
          }

          //Katsoo onko pelaajia joilla on muita vähemmän otteluita
          ArrayList<Pelaaja> pelinTarpeessa = new ArrayList<>(); //Lista pelaajille joilla on vähiten pelejä
          ArrayList<Integer> numero = new ArrayList<>();  //Pelaajien otteluiden määrät
          for(int i=0; i<=pelurit.size()-1; i++){
            numero.add(pelurit.get(i).getPelattu().size()); //Pelaajan otteluiden määrä
          }
          int suurin = Collections.max(numero); //Otetaan suurin otteluiden määrä (kaikilla pitäisi olla suunnilleen tämän verran)

          //Käydään otteluiden määrät läpi ja katsotaan onko jollain vähemmän kuin muilla
          for(int i=0; i<=pelurit.size()-1; i++){
            //Jos vähemmän kuin suurin niin lisätään peliä tarvitsevien listalle
            if(pelurit.get(i).getPelattu().size() < suurin){
              pelinTarpeessa.add(pelurit.get(i));
              pelurit.remove(i);     //poistetaan alkuperäisestä ettei tätä ole kahteen kertaan
            } 
          }

          //Parien jako
          ArrayList<Pelaaja> paritetut = new ArrayList<>(); //Lista jo paritetuille pelaajille

          //Ensin jaetaan pelin tarpeessa oleville parit
          if(pelinTarpeessa.size() > 0) {
            for(Pelaaja p : pelinTarpeessa) {
              for(Pelaaja v : pelurit) {
                if (!p.getPelattu().contains(v) && !p.equals(v)  && !paritetut.contains(p) && !paritetut.contains(v)) {
                  seuraavat.add(new Ottelu(p,v,kierrosluku)); //Luodaan ottelu
                  //Poistetaan pelaajat listoilta
                  paritetut.add(p);
                  paritetut.add(v);
                }
              }
            }
          }
          pelurit.removeAll(paritetut);   //Poistetaan jo paritetut listoilta
          pelinTarpeessa.removeAll(paritetut);

          //Jaetaan loput pelaajat
          for(Pelaaja p : pelurit) {  //Käydään pelaajat läpi
            //Etsitään pari
            for(Pelaaja v : pelurit) {   
              //Jos ei ole vielä pelannut tätä vastaan niin paritetaan
              if(!p.getPelattu().contains(v) && !p.equals(v) && !paritetut.contains(p) && !paritetut.contains(v)){
                seuraavat.add(new Ottelu(p, v, kierrosluku)); //Lisätään ottelu seuraavien listaan
                paritetut.add(v);
                paritetut.add(p);
              }
            }
          }
          //Poistetaan jo paritetut parittamattomien listoilta
          pelurit.removeAll(paritetut);

          //Jos pelaajia jäi yli tai kaikki jo pelanneet kaikkia vastaan
          if(!pelurit.isEmpty() || !pelinTarpeessa.isEmpty()) {  

            if(!pelurit.isEmpty() && !pelinTarpeessa.isEmpty()) { //Molemmissa listoissa vielä pelaajia 
              //TODO
              //eli kaikki ovat jo pelanneet kaikkia vastaan??
              for(Pelaaja p : pelinTarpeessa) { //Jaetaan ensin pelin tarpeessa oleville parit
                for(Pelaaja a : pelurit) {
                  if(!paritetut.contains(a) && !paritetut.contains(p) && !p.equals(a)) {
                    seuraavat.add(new Ottelu(p, a, kierrosluku));
                    paritetut.add(p);
                    paritetut.add(a);
                  }
                }
              }
              //Poistetaan taas paritetut pelaajat listoilta
              pelurit.removeAll(paritetut);
              pelinTarpeessa.removeAll(paritetut);

              //Jaetaan loput
              for(Pelaaja p : pelurit) {
                for(Pelaaja a : pelurit) {
                  if(!paritetut.contains(a) && !paritetut.contains(p)) {
                    seuraavat.add(new Ottelu(p, a, kierrosluku));
                    paritetut.add(p);
                    paritetut.add(a);
                  }
                }
              }
              //Kaikki pitäisi nyt olla jaettu
              pelurit.removeAll(paritetut); //Poistetaan loputkin listasta 
            }
    
            else if(!pelurit.isEmpty() && pelinTarpeessa.isEmpty()) {  //Vain toisella listalla pelaajia jäljellä

              if(pelurit.size() < 2){//Yksi pelaaja jäljellä
                //Lisätään yksinäinen pelaaja kuitenkin seuraavien otteluiden listalle yksinään
                //seuraavat.add(new Ottelu(pelurit.get(0), kierrosluku)); 
                //pelurit.remove(0);
              }

              else if(pelurit.size() >= 2) {//Vähintään 2 pelaajaa jäljellä
                //Jaa jäljellä olevista parit
                for(Pelaaja p : pelurit) {
                  for(Pelaaja a : pelurit) {
                    if(!paritetut.contains(p) && !paritetut.contains(a) && !p.equals(a)) {
                      seuraavat.add(new Ottelu(p,a,kierrosluku)); //Luodaan ottelu
                      paritetut.add(p); //Lisätään paritettuihin
                      paritetut.add(a);
                    }
                  }
                }
              }
            }
    
            else if(!pelinTarpeessa.isEmpty() && pelurit.isEmpty()){
              //joku pelaaja jolla oli vähemmän pelejä ku muilla jäi ilman paria
              //Ei pitäs tapahtuu
            }
          }

          //TODO
          //Jos pelaajia <=4 niin sillo se manuaalinen jako
      }
      return seuraavat;
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
    /*
    public List<Ottelu> getKierros() {
        return kierros;
    }

    public void setKierros(List<Ottelu> kierros) {
        this.kierros = kierros;
    }

     */
    public void Tyhjenna(){
        this.pelaajat.clear();
    }
}