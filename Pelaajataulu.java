import java.util.*;

public class Pelaajataulu {

    private ArrayList<Pelaaja> pelaajat;
    private ArrayList<Pelaaja> pudonneet;
    int i;

    public Pelaajataulu() {
        pelaajat = new ArrayList<>();
        pudonneet = new ArrayList<>();
        i = 0;
    }

    //Lisätään pelaaja
    public void setPelaaja(Pelaaja pelaaja) {
        pelaajat.add(pelaaja);
        pelaajat.get(i).setPeliNro(i+1);
        i++;
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
}