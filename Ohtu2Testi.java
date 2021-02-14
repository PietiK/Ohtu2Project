import java.util.*;
public class Ohtu2Testi
{
    public static void main(String [] args)
    {
        Pelaaja sauli = new Pelaaja("Sauli Niinistö");
        Pelaaja tarja = new Pelaaja("Tarja Halonen ");
        Pelaaja martti = new Pelaaja("Martti Ahtisaari");
        Pelaaja mauno = new Pelaaja("Mauno Koivisto");
        Pelaaja urho = new Pelaaja("Urho Kekkonen");

        Ottelu kakka = new Ottelu(sauli, tarja);
        Ottelu pylly = new Ottelu(mauno, urho);

        System.out.println(kakka.toString());
        System.out.println(pylly.toString());

        


    }
}

