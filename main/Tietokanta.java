package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Ottelu;

import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tietokanta {
    public static Connection connect() throws SQLException, Exception {
        Connection conn = null;
        String url = "jdbc:sqlite:src/tietokanta.db";

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
        String query = "Insert Into pelaaja(pelinro, nimi, pisteet) " + "Values(?, ?, ?)";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,pelaaja.getPeliNro());
            stmt.setString(2,pelaaja.getNimi());
            stmt.setInt(3,pelaaja.getPisteet());
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
            stmt.setInt(3, pelaaja.getPeliNro());
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
        String query = "Select nimi, aloituspvm, lopetuspvm From turnaus";
        List<Turnaus> lista = new ArrayList<>();
        try {
            Connection connect = connect();
            stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Turnaus t = new Turnaus(); 
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

    //HUOM! KESKEN
    //en keksinyt miten pelaajan nimet saataisiin pelaajan_idn avulla.
    //nyt haetaan  pelaaja_id nimeksi

    //Nyt löytää ottelun pelaajien nimetkin (jos tää toimii kai)
    //mutta pelinumeroiden haku vielä vaiheessa
    //-Pieti
    public static ObservableList<Pelaaja>haeKilpailuparinPelaajat(int ottelu_id){
        PreparedStatement stmt = null;
        //String query = "Select * From ottelu"; //tietokanta?
        String nimet = "SELECT nimi FROM pelaaja "
        + "JOIN pelaaja_ottelu USING(pelaaja_id) "
        + "JOIN ottelu USING(ottelu_id) "
        + "WHERE ottelu.ottelu_id = ?";

        List<Pelaaja> lista = new ArrayList<>();

        try {
            Connection connect = connect();
            stmt = connect.prepareStatement(nimet);
            stmt.setInt(1, ottelu_id);
            ResultSet rs = stmt.executeQuery();

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
        String ottelu_query = "UPDATE pelaaja_ottelu SET pisteet = pisteet + ? " +
                "WHERE ottelu_id = ? " +
                " AND pelaaja_id = ?";
        String turnaus_query = "UPDATE pelaaja_turnaus SET turnauspisteet = turnauspisteet + ? "+
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
    public static ArrayList<Pelaaja> TurnauksenPelaajat(int turnausid){
        Pelaajataulu pelaajat = new Pelaajataulu();
        ArrayList<Pelaaja> pel = new ArrayList<>();
        String query = "SELECT pelaaja.pelaaja_id,pelaaja.nimi, pelinro FROM turnaus, pelaaja " +
        "JOIN pelaaja_turnaus USING(pelaaja_id)" +
        "WHERE turnaus.turnaus_id = ?";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,turnausid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String nimi = rs.getString(2);
                int id = rs.getInt(1);
                Pelaaja temp = new Pelaaja(nimi);
                temp.setId(id);
                pel.add(temp);
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

    public static void PelaajatOtteluun(List<Ottelu> kierros){
        String eka = "INSERT INTO pelaaja_ottelu (pelaaja_id,ottelu_id) "+
                      "VALUES (?,?)";
        String toka = "INSERT INTO pelaaja_ottelu (pelaaja_id,ottelu_id) "+
                     "VALUES (?,?)";

        int eka_id = -1;
        int toka_id = -1;
        int laskuri = 1;
        try {
            Connection conn = connect();
            for (Ottelu o : kierros){
                PreparedStatement stmt_eka = conn.prepareStatement(eka);
                PreparedStatement stmt_toka = conn.prepareStatement(toka);
                stmt_eka.setInt(1, PelaajanId(o.getPelaaja1().getNimi()));
                stmt_eka.setInt(2, laskuri);
                stmt_toka.setInt(1, PelaajanId(o.getPelaaja2().getNimi()));
                stmt_toka.setInt(2, laskuri);
                stmt_eka.executeUpdate();
                stmt_toka.executeUpdate();
                laskuri++;
            }
            conn.close();

        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

  public static void main(String[] args) {  
    
  }


}
