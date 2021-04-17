package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tietokanta {
    public static Connection connect() throws SQLException, Exception {
        Connection conn = null;
        //String url = "jdbc:sqlite:src/tietokanta.db";
        String url = "jdbc:sqlite:tietokanta.db";
        
        try {
            // ota yhteys kantaan, kayttaja = root, salasana = root
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            conn = null;
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return conn;
    }

        /* En saanut tällä toimimaan, sen vuoksi kommenttina
        try {
            //Tietokannan osoite
            //Luo yhteys tietokantaan
            conn = DriverManager.getConnection(url);
            System.out.println("Yhteys tietokantaan muodostettu");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    */

    public static void LisaaPelaaja(Pelaaja pelaaja){
        String query = "Insert Into pelaaja(nimi) " + "Values(?)";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, pelaaja.getNimi());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void LuoTurnauksenPelaajalista(Pelaaja pelaaja, int tid) { 
        String query = "Insert into pelaaja_turnaus(pelaaja_id, turnaus_id, pelinro) values (?, ?, ?)";
        try {
            Connection conn = connect(); 
            PreparedStatement stmt = conn.prepareStatement(query); 
            stmt.setInt(1, pelaaja.getId());
            stmt.setInt(2, tid); 
            stmt.setInt(3, pelaaja.getPelinro());
            stmt.executeUpdate();
            conn.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int HaeuusinTurnausID() {
        Statement stmt = null; 
        String query = "SELECT turnaus_id, MAX([turnaus_id]) from turnaus"; 
        int id = -1; 
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("turnaus_id");
            }
            connect.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static int HaeUusinPelaajaID() {
        Statement stmt = null; 
        String query = "Select pelaaja_id, MAX([pelaaja_id]) from pelaaja"; 
        int id = -1; 
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("pelaaja_id");
            }
            connect.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void LisaaTurnaus(Turnaus turnaus) {
        String query = "INSERT INTO turnaus(nimi, aloituspvm, lopetuspvm) " 
                        + "VALUES(?,?,?)";

        try {
            Connection conn = connect(); 
            PreparedStatement stmt = conn.prepareStatement(query); 
            stmt.setString(1, turnaus.getNimi());
            stmt.setString(2, turnaus.getAloituspvm());
            stmt.setString(3, turnaus.getLopetuspvm());
            stmt.executeUpdate();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ObservableList<Turnaus> haeTurnaukset() {
        Statement stmt = null;
        String query = "Select turnaus_id, nimi, aloituspvm, lopetuspvm From turnaus";
        List<Turnaus> lista = new ArrayList<>();
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Turnaus t = new Turnaus(); 
                t.setId(rs.getInt("turnaus_id"));
                t.setNimi(rs.getString("nimi"));
                t.setAloituspvm(rs.getString("aloituspvm"));
                t.setLopetuspvm(rs.getString("lopetuspvm"));
                lista.add(t);
            }
            connect.close();
            return FXCollections.observableList(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Pelaaja> haePelaajat(){
        Statement stmt = null;
        String query = "Select * From pelaaja";
        List<Pelaaja> lista = new ArrayList<>();
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Pelaaja temp = new Pelaaja(rs.getString("nimi"));
                lista.add(temp);
            }
            connect.close();
            return FXCollections.observableList(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
/*Hakee kaikki pelaajat eikä vaan yhen turnauksen ?
    public static ArrayList<Pelaaja> getTurnauksenPelaajat(){
      Statement stmt = null;
      String query = "Select * From pelaaja";
      ArrayList<Pelaaja> lista = new ArrayList<>();
      try {
          Connection connect = connect();
          stmt = connect.createStatement();
          ResultSet rs = stmt.executeQuery(query);
          while (rs.next()){
              Pelaaja temp = new Pelaaja(rs.getString("nimi"));
              temp.setId(rs.getInt("pelaaja_id"));
              lista.add(temp);
          }
          connect.close();
          return lista;
      } catch (SQLException e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }*/

    //Tän pitäs hakee kaikki tiedot yhen turnauksen pelaajista
    public static ArrayList<Pelaaja> getTurnauksenPelaajat(int turnId){
      Statement stmt = null;
      String query = "SELECT * FROM pelaaja " +
      "JOIN pelaaja_turnaus USING(pelaaja_id) " +
      " WHERE turnaus_id = " + turnId;
      ArrayList<Pelaaja> lista = new ArrayList<>();
      try {
          Connection connect = connect();
          stmt = connect.createStatement();
          ResultSet rs = stmt.executeQuery(query);
          while (rs.next()){
              Pelaaja temp = new Pelaaja(rs.getString("nimi"));
              temp.setId(rs.getInt("pelaaja_id"));
              lista.add(temp);
          }
          connect.close();
          return lista;
      } catch (SQLException e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return lista;
    }
  
    public static ArrayList<Pelaaja> getTurnauksenPelaajienTiedot(int turnId){
      Statement stmt = null;
      String query = "SELECT pelaaja.nimi, pelaaja_id FROM pelaaja " +
      "JOIN pelaaja_turnaus USING(pelaaja_id) " +
      " WHERE turnaus_id = " + turnId;
      ArrayList<Pelaaja> lista = new ArrayList<>();
      try {
          Connection connect = connect();
          stmt = connect.createStatement();
          ResultSet rs = stmt.executeQuery(query);
          while (rs.next()){
              Pelaaja temp = new Pelaaja(rs.getString("nimi"));
              temp.setId(rs.getInt("pelaaja_id"));
              lista.add(temp);
          }
          connect.close();
          return lista;
      } catch (SQLException e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return lista;
  }

    //Hakee pelaajan jonka id on id
    public static Pelaaja getPelaaja(int id) {  
      PreparedStatement stmt = null;
        String query = "SELECT * From pelaaja WHERE pelaaja_id = ?";
        Pelaaja temp = null;
        try {
            Connection connect = connect();
            stmt = connect.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                temp = new Pelaaja(rs.getString("nimi"));
                temp.setId(rs.getInt("pelaaja_id"));
            }
            connect.close();
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //HUOM! KESKEN
    //en keksinyt miten pelaajan nimet saataisiin pelaajan_idn avulla.
    //nyt haetaan  pelaaja_id nimeksi

    //Nyt löytää ottelun pelaajien nimetkin (jos tää toimii kai)
    //mutta pelinumeroiden haku vielä vaiheessa
    //-Pieti
    public static ArrayList<Pelaaja>haeKilpailuparinPelaajat(int ottelu_id){
        PreparedStatement stmt = null;
        //String query = "Select * From ottelu"; //tietokanta?
        String nimet = "SELECT nimi, pelaaja_id FROM pelaaja "
        + "JOIN pelaaja_ottelu USING(pelaaja_id) "
        + "JOIN ottelu USING(ottelu_id) "
        + "WHERE ottelu.ottelu_id = ?";

        ArrayList<Pelaaja> lista = new ArrayList<>();

        try {
            Connection connect = connect();
            stmt = connect.prepareStatement(nimet);
            stmt.setInt(1, ottelu_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
              Pelaaja temp = new Pelaaja(); 
              temp.setNimi(rs.getString("nimi"));
              temp.setId(rs.getInt("pelaaja_id"));
              lista.add(temp);
            }

            connect.close();
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //hakee turnauksen tiedot ja sen pelaajat turnaus_id:n perusteella
    public static ObservableList<String> haeTurnauksenTiedot(int turnaus_id) {
      String tiedot = "SELECT * FROM turnaus WHERE turnaus_id = ?";
      String nimet = "SELECT pelaaja.nimi, pelinro FROM pelaaja "
      + "JOIN pelaaja_turnaus USING(pelaaja_id) "
      + "WHERE turnaus.turnaus_id = ?";
    
      PreparedStatement nimiStmt = null;
      PreparedStatement tiedotStmt = null;
    
      ArrayList<String> lista = new ArrayList<>();
    
      try {
        Connection connect = connect();
        nimiStmt = connect.prepareStatement(nimet);
        nimiStmt.setInt(1, turnaus_id);
    
        tiedotStmt = connect.prepareStatement(tiedot);
        tiedotStmt.setInt(1, turnaus_id);
    
        ResultSet rsNimet = nimiStmt.executeQuery();
        ResultSet rsTiedot = tiedotStmt.executeQuery();
    
        while (rsTiedot.next()) {
          lista.add(rsTiedot.getString("nimi"));
          lista.add(rsTiedot.getString("aloituspvm"));
          lista.add(rsTiedot.getString("lopetuspvm"));
        }
        while (rsNimet.next()){
            lista.add(rsNimet.getString("nimi"));
            lista.add(rsNimet.getString("pelinro"));
        }
        connect.close();
        return FXCollections.observableList(lista);
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
    }
    public static int HaeTurnauksenID(String nimi){
        String query = "SELECT turnaus_id FROM turnaus WHERE nimi = ?";
        int id = -1;
        int rows = 0;
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,nimi);
            ResultSet rs = stmt.executeQuery();
            id = rs.getInt(1);
            conn.close();
            return id;
            /*
            Connection conn = connect();
            System.out.println(query);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,nimi);
            ResultSet rs = stmt.executeQuery(query);
            id = rs.getInt(1);
            conn.close();
            return id;

             */
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return id;
    }

    public static void LisaaPisteita(int pelaajaid, int otteluid, int pisteet){
        String ottelu_query = "UPDATE pelaaja_ottelu SET pisteet = ? " +
                "WHERE ottelu_id = ? " +
                " AND pelaaja_id = ?";
        String turnaus_query = "UPDATE pelaaja_turnaus SET turnauspisteet = ? "+
                "WHERE pelaaja_id = ?";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(ottelu_query);
            PreparedStatement stmt2 = conn.prepareStatement(turnaus_query);
            stmt.setInt(1,pisteet);
            stmt.setInt(2,otteluid);
            stmt.setInt(3,pelaajaid);
            stmt2.setInt(1,pisteet);
            stmt2.setInt(2,pelaajaid);

            stmt.executeUpdate();
            stmt2.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Pelaaja> TurnauksenPelaajat(Turnaus t){
        Pelaajataulu pelaajat = new Pelaajataulu();
        ArrayList<Pelaaja> pel = new ArrayList<>();
        String query = "SELECT pelaaja.nimi, pelaaja_turnaus.pelaaja_id, pelaaja_turnaus.pelinro " + 
        "FROM pelaaja_turnaus" +
        " INNER JOIN pelaaja ON pelaaja_turnaus.pelaaja_id = pelaaja.pelaaja_id" + 
        " WHERE pelaaja_turnaus.turnaus_id = " + t.getId();

        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Pelaaja p = new Pelaaja(); 
                p.setId(rs.getInt("pelaaja_id"));
                p.setNimi(rs.getString("nimi"));
                p.setPeliNro(rs.getInt("pelinro"));
                //System.out.println(p.getNimi());
                pel.add(p); 
            }
            conn.close();
            System.out.println("Pelaajat: " + pel); 
            return pel;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pel;


    }

    public static int PelaajanId(String nimi){
        String query = "SELECT pelaaja_id FROM pelaaja WHERE nimi = ?";
        int id = -1;
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,nimi);
            ResultSet rs = stmt.executeQuery();
            id = rs.getInt(1);
            conn.close();
            return id;
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return id;

    }


    public static void LisaaKierros(Kierros kierros) {
        String query = "Insert into kierros (turnaus_id, kierrosluku) values (?, ?) ";
        try {
            Connection conn = connect(); 
            PreparedStatement stmt = conn.prepareStatement(query); 
            stmt.setInt(1, kierros.getTurnaus().getId());
            stmt.setInt(2, kierros.getJnum());
            stmt.executeUpdate(); 
            conn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static int HaeUusinKierrosID() {
        Statement stmt = null; 
        String query = "Select kierros_id, MAX([kierros_id]) from kierros"; 
        int id = -1; 
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("kierros_id");
            }
            connect.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void LisaaOttelu(Ottelu ottelu) {
        String query = "Insert into ottelu (kierros_id) values (?)";
        try {
            Connection conn = connect(); 
            PreparedStatement stmt = conn.prepareStatement(query); 
            stmt.setInt(1, ottelu.getKierros());
            stmt.executeUpdate(); 
            conn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static int HaeUusinOtteluID() {
        Statement stmt = null; 
        String query = "Select ottelu_id, MAX([ottelu_id]) from ottelu"; 
        int id = -1; 
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("ottelu_id");
            }
            connect.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


    public static void PelaajatOtteluun(Ottelu o){
        String eka = "INSERT INTO pelaaja_ottelu (pelaaja_id,ottelu_id) "+
                      "VALUES (?,?)";
        String toka = "INSERT INTO pelaaja_ottelu (pelaaja_id,ottelu_id) "+
                     "VALUES (?,?)";

        int eka_id = -1;
        int toka_id = -1;

        if(o.getPelaaja2() != null){
          try {
              Connection conn = connect();
              PreparedStatement stmt_eka = conn.prepareStatement(eka);
              PreparedStatement stmt_toka = conn.prepareStatement(toka);
              stmt_eka.setInt(1, o.getPelaaja1().getId());
              stmt_eka.setInt(2, o.getID());
              stmt_toka.setInt(1, o.getPelaaja2().getId());
              stmt_toka.setInt(2, o.getID());
              stmt_eka.executeUpdate();
              stmt_toka.executeUpdate();
              conn.close();
          } catch (SQLException e) {

              e.printStackTrace();
          } catch (Exception e) {

              e.printStackTrace();
          }
      }
      else System.out.println("EI adsadf");
    }

    /*
    public ObservableList<Ottelu> haeKierroksenOttelut(int id) {
        String query = ""
    }
    */

 /* public static void main(String[] args) {  
    
  }*/

public static ArrayList<Integer> getKierroksenOttelut(int kierros_id) {
    Statement stmt = null; 
    String query = "Select ottelu_id from ottelu where kierros_id = " + kierros_id; 
    try {
        Connection connect = connect();
        stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<Integer> idt = new ArrayList<Integer>(); 
        while (rs.next()) {
            idt.add(rs.getInt("ottelu_id")); 
        }
        connect.close();
        return idt; 
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
    
}

public static boolean OnkoVoittajaa(int id) {
    String query = "Select voittaja from ottelu where ottelu_id = " + id; 
    Statement stmt = null; 
    boolean voitto = false; 
    try {
        Connection connect = connect();
        stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            if (rs.getInt("voittaja") == 0) {
                voitto = false; 
            } else { voitto = true; }
        }
        connect.close();
        return voitto; 
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return voitto;

}
//Hakee ottelun voittajan id:n
public static int getOttelunVoittaja(int otteluid) {
  String query = "SELECT voittaja FROM ottelu WHERE ottelu_id = " + otteluid; 
    try {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);
        int voittaja = -1;
        if (rs.next()) {
            voittaja = rs.getInt("voittaja");
        }
        conn.close();
        return voittaja;
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    //Palauttaa -1 jos ottelulla ei ole voittajaa
    return -1;
}

public static void OttelunVoittaja(int otteluid, int pelaajaid) {
    String query = "Update ottelu set voittaja = " + pelaajaid + " Where ottelu_id = " + otteluid; 
    try {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        conn.close();

    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void LisaaTappio(int turnaus_id, int pelaaja_id) {
    //String query = "Update pelaaja_turnaus set tappiot = ? " +
    //"where pelaaja_id = " + pelaaja_id + " and turnaus_id = " + turnaus_id; 
    //Kasvattaa tappioita yhdellä
    String query = "Update pelaaja_turnaus set tappiot = tappiot + 1 " +        
    "where pelaaja_id = " + pelaaja_id + " and turnaus_id = " + turnaus_id; 

    try {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void LisaaTPisteita(int turnaus_id, int pelaaja_id, int pisteet) {
  //Lisää vanhoihin pisteisiin "pisteet" muuttujan arvon
    String query = "Update pelaaja_turnaus set turnauspisteet = turnauspisteet + ? "
    + "WHERE pelaaja_id = " + pelaaja_id + " AND turnaus_id = " + turnaus_id; 
    try {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, pisteet);
        stmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static int HaeTPisteet(int turnaus_id, int pelaaja_id) {
    String query = "Select turnauspisteet from pelaaja_turnaus where turnaus_id = " + turnaus_id +
    " and pelaaja_id = " + pelaaja_id; 
    Statement stmt;
    int tappiot = -1;
    try {
        Connection connect = connect();
        stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            tappiot = rs.getInt("turnauspisteet"); 
        }
        connect.close();
        return tappiot; 
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return tappiot;
}

public static int HaeTappiot(int turnaus_id, int pelaaja_id) {
    String query = "Select tappiot from pelaaja_turnaus where turnaus_id = " + turnaus_id +
    " and pelaaja_id = " + pelaaja_id; 
    Statement stmt;
    int tappiot = 0;
    try {
        Connection connect = connect();
        stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            tappiot = rs.getInt("tappiot"); 
        }
        connect.close();
        return tappiot; 
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return tappiot;
}

public static void TurnausKäyntiin(int id) {
    String query = "Update turnaus set kaynnissa = 1 Where turnaus_id = " + id; 
    try {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        conn.close();

    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void TurnausEiKäyntiin () {
    String query = "Update turnaus set kaynnissa = 0 Where kaynnissa = 1"; 
    try {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        conn.close();

    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static int haeKierrosID() {
    Statement stmt = null;
    String query = "SELECT kierros.kierros_id, MAX([kierrosluku]) FROM kierros " + 
    "Inner join turnaus On kierros.turnaus_id = turnaus.turnaus_id " + 
    "where turnaus.kaynnissa = 1"; 
    int id = -1; 
    try {
        Connection connect = connect();
        stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            id = rs.getInt("kierros_id");
        }
        connect.close();
        return id; 
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return id;
}
    public static boolean PoistaTurnaus(int id){
        String eka = "DELETE FROM ottelu WHERE turnaus_id = ?";
        String toka = "DELETE FROM pelaaja_turnaus WHERE turnaus_id = ?";
        String kolmas = "DELETE FROM turnaus WHERE turnaus_id = ?";
        String neljas = "DELETE FROM kierros WHERE turnaus_id = ?";
        try {
            Connection conn = connect();
            PreparedStatement stmt_eka = conn.prepareStatement(eka);
            PreparedStatement stmt_toka = conn.prepareStatement(toka);
            PreparedStatement stmt_kolmas = conn.prepareStatement(kolmas);
            PreparedStatement stmt_neljas = conn.prepareStatement(neljas);
            stmt_eka.setInt(1, id);
            stmt_toka.setInt(1, id);
            stmt_kolmas.setInt(1, id);
            stmt_neljas.setInt(1, id);
            stmt_eka.executeUpdate();
            stmt_toka.executeUpdate();
            stmt_kolmas.executeUpdate();
            stmt_neljas.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void PoistaTurnauksenPelaaja_Ottelut(ArrayList<Integer> ottelut){
        String query = "DELETE FROM pelaaja_ottelu WHERE ottelu_id = ?";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            for (int i : ottelut){
                stmt.setInt(1,i);
                stmt.executeUpdate();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static ArrayList<Integer> HaeTurnauksenOttelut(int id){
        String query = "Select ottelu_id FROM ottelu WHERE turnaus_id = ?";
        ArrayList<Integer> ottelut = new ArrayList<>();
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ottelut.add(rs.getInt(1));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return ottelut;
        } catch (Exception e) {
            e.printStackTrace();
            return ottelut;
        }
        return ottelut;
    }

    public static ArrayList<Kierros> haeTurnauksenKierrokset(int id) {
        String query = "Select kierros_id, kierrosluku, turnaus_id from kierros where turnaus_id = " + id; 
        ArrayList<Kierros> kierrokset = new ArrayList<>();
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Kierros k = new Kierros(); 
                k.setID(rs.getInt("kierros_id")); 
                k.setKierros(rs.getInt("kierrosluku"));
                k.setTurnaus(Tietokanta.haeTurnaus(rs.getInt("turnaus_id")));
                kierrokset.add(k); 
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return kierrokset;
        } catch (Exception e) {
            e.printStackTrace();
            return kierrokset;
        }
        return kierrokset;
    }

    public static Turnaus haeTurnaus(int int1) {
       String query = "Select turnaus_id, nimi, aloituspvm, lopetuspvm from turnaus where turnaus_id = " + int1; 
       Turnaus t = new Turnaus(); 
       try {
           Connection conn = connect();
           PreparedStatement stmt = conn.prepareStatement(query);
           ResultSet rs = stmt.executeQuery();
           while (rs.next()) {
                t.setAloituspvm(rs.getString("aloituspvm"));
                t.setNimi(rs.getString("nimi"));
                t.setId(rs.getInt("turnaus_id"));
                t.setLopetuspvm(rs.getString("lopetuspvm")); 
           }
           conn.close();
       } catch (SQLException e) {
           e.printStackTrace();
           return t;
       } catch (Exception e) {
           e.printStackTrace();
           return t;
       }
       return t;
    }

    ///[YearlyIncome] = (SELECT MAX([YearlyIncome]) FROM Customer)


    public static int HaeSuurinKierrosnumero(int tid) {
        String query = "Select kierrosluku from kierros where (turnaus_id = " + tid +
        " AND [kierrosluku] = (SELECT MAX([kierrosluku]) FROM kierros))"; 
        Statement stmt;
        int kierros = 0;
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                kierros = rs.getInt("kierrosluku"); 
            }
            connect.close();
            return kierros; 
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kierros;
    }

    public static ArrayList<Pelaaja> haeJäljelläPelaajat(int turnaus_id) {
        String query = "SELECT pelaaja.nimi, pelaaja_turnaus.pelaaja_id, pelaaja_turnaus.pelinro " + 
        "FROM pelaaja_turnaus" +
        " INNER JOIN pelaaja ON pelaaja_turnaus.pelaaja_id = pelaaja.pelaaja_id" + 
        " WHERE pelaaja_turnaus.turnaus_id = " + turnaus_id + " and tappiot < 2";
        ArrayList<Pelaaja> pel = new ArrayList<Pelaaja>(); 
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Pelaaja p = new Pelaaja(); 
                p.setId(rs.getInt("pelaaja_id"));
                p.setNimi(rs.getString("nimi"));
                p.setPeliNro(rs.getInt("pelinro"));
                //System.out.println(p.getNimi());
                pel.add(p); 
            }
            conn.close();
            return pel;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pel;
    }

    //Hakee pelaajan jo pelaamat vastustajat
    public static ArrayList<Integer> haePelatut(int pelaaja_id){
      String ottelut = "SELECT ottelu_id FROM pelaaja_ottelu WHERE pelaaja_id = ?";
      ArrayList<Integer> ottelulista = new ArrayList<>();
      String vastustajat = "SELECT pelaaja_id FROM pelaaja_ottelu WHERE ottelu_id = ? "
      + "AND pelaaja_id != ?";
      ArrayList<Integer> pelatut = new ArrayList<>();
      try {
          Connection conn = connect();
          PreparedStatement stmt = conn.prepareStatement(ottelut);
          stmt.setInt(1,pelaaja_id);
          ResultSet rsOttelut = stmt.executeQuery();
          //Haetaan pelaajan pelaamat ottelut listaan
          while (rsOttelut.next()) {
              ottelulista.add(rsOttelut.getInt("ottelu_id"));
          }
          //Haetaan pelaajan pelaamien otteluiden toinen pelaaja (eli se vastustaja) toiseen listaan
          if(!ottelulista.isEmpty()){
            //Käydään otteluiden lista läpi
            for(int o : ottelulista){
              PreparedStatement stmtVastustajat = conn.prepareStatement(vastustajat);
              //Jokaisella loopilla ottelun id vaihtuu mutta pelaaja pysyy samana
              stmtVastustajat.setInt(1, o);
              stmtVastustajat.setInt(2, pelaaja_id);
              ResultSet rsVastustajat = stmtVastustajat.executeQuery();
              while(rsVastustajat.next())
                pelatut.add(rsVastustajat.getInt("pelaaja_id"));
            }
          }
          conn.close();
          return pelatut;
      } catch (SQLException e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return pelatut;
    }
    public static int get_pelaaja_ottelu_pisteet(int pelaaja_id, int ottelu_id){
        String query = "SELECT pisteet FROM pelaaja_ottelu WHERE pelaaja_id = ? AND ottelu_id = ?";
        int pisteet = 0;
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,pelaaja_id);
            stmt.setInt(2,ottelu_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pisteet = rs.getInt("pisteet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pisteet;
    }
}
