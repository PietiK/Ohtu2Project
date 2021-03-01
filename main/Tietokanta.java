package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tietokanta {
    public static Connection connect() throws SQLException, Exception {
        Connection conn = null;

        String url = "jdbc:sqlite:src/main/tietokanta.db";

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

  public static void main(String[] args) {  

  }


}