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

        String url = "jdbc:sqlite:C:/Users/Siru_/OHTU2/R02/main/tietokanta.db";

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

    public static void LuoTurnauksenPelaajalista(List<Pelaaja> pelaajat, Turnaus turnaus) { 
        String query = "Insert into pelaaja_turnaus(pelaaja_id, turnaus_id, pelinro) values (?, ?, ?)";
        try {
            Connection conn = connect(); 
            PreparedStatement stmt = conn.prepareStatement(query); 
        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void LisaaTurnaus(Turnaus turnaus) {
        String query = "Insert Into turnaus(nimi, aloituspvm, lopetuspvm) " + "Values(?,?, ?)";
        try {
            Connection conn = connect(); 
            PreparedStatement stmt = conn.prepareStatement(query); 
            System.out.println(turnaus.getNimi()); 
            stmt.setString(1, turnaus.getNimi());
            stmt.setString(2, turnaus.getAloituspvm());
            stmt.setString(3, turnaus.getLopetuspvm());
            stmt.executeUpdate();
        } catch (SQLException e) {
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


  public static void main(String[] args) {  

  }


}
