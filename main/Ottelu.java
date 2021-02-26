package main;

import java.util.Scanner;

public class Ottelu {

    private Pelaaja pelaaja1;
    private Pelaaja pelaaja2;
    private String otNimi;
    Scanner skanneri = new Scanner(System.in);

    public Ottelu(Pelaaja eka, Pelaaja toka) {
        this.pelaaja1 = eka;
        this.pelaaja2 = toka;
        pelaaja1.setPisteet(0);
        pelaaja2.setPisteet(0);
    }

    public Pelaaja getPelaaja1() {
        return pelaaja1;
    }

    public void setPelaaja1(Pelaaja pelaaja1) {
        this.pelaaja1 = pelaaja1;
    }

    public Pelaaja getPelaaja2() {
        return pelaaja2;
    }

    public void setPelaaja2(Pelaaja pelaaja2) {
        this.pelaaja2 = pelaaja2;
    }

    public String getOtNimi() {
        return otNimi;
    }
    public void setOtNimi(String otNimi) {
        this.otNimi = otNimi;
    }


    @Override
    public String toString() {
        return "Ottelu{" +
                "Pelaaja1 = " + pelaaja1.getNimi() +
                "Pisteet = " + pelaaja1.getPisteet() +
                ", Pelaaja2 = " + pelaaja2.getNimi() +
                " Pisteet = " + pelaaja2.getPisteet() +
                '}';
    }

    // Tarkistetaan onko pelaaja voittanut pelin
    // Voittoon tarvitaan 60 pistettä

    public boolean onVoittanut(Pelaaja pelaaja) {
        boolean totuus = false;
        if (pelaaja.equals(pelaaja1)){
            if (pelaaja1.getPisteet() >= 60){
                totuus = true;
            }
        } else {
            if (pelaaja2.getPisteet() >= 60){
                totuus = true;
            }
        }

        return totuus;
    }

    // Testataan miten yksi ottelu toimisi
    // HUOM! Tästä puuttuu pisteiden lisäys kokonaispisteisiin
    public void peli(){
        // vuorossa oleva pelaaja
        int vuoro = 1;
        System.out.println(toString());

        while (true) {
            if (vuoro == 1) {
                System.out.print("Vuorossa: " + pelaaja1.getNimi() + " ");
                int pisteet = skanneri.nextInt();
                pelaaja1.setPisteet(pisteet);
                // jos pelaaja on voittanut, peli loppuu
                // jos pelaaja ei ole voittanut, peli jatkuu
                if (onVoittanut(pelaaja1)){
                    System.out.println(pelaaja1.getNimi() + " voitti");
                    pelaaja1.lisaaVoitto();
                    pelaaja2.lisaaTappio();
                    // pelin lopussa näytetään tilanne/pisteet
                    System.out.println(toString());
                    break;
                }

                System.out.println(toString());
                // vaihdetaan vuoroa
                vuoro = 2;
            }

            if (vuoro == 2) {
                System.out.print("Vuorossa: " + pelaaja2.getNimi() + " ");
                int pisteet = skanneri.nextInt();
                pelaaja2.setPisteet(pisteet);
                if (onVoittanut(pelaaja1)){
                    System.out.println(pelaaja2.getNimi() + " voitti");
                    pelaaja2.lisaaVoitto();
                    pelaaja1.lisaaTappio();
                    System.out.println(toString());
                    break;
                }

                System.out.println(toString());
                vuoro = 1;
            }
        }

        System.out.println(" ");
    }
}

