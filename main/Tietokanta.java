package main;

import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.SQLException;  

public class Tietokanta {
  public static void connect() {  
    Connection conn = null;  
    try {  
        //Tietokannan osoite
        String url = "jdbc:sqlite:C:tietokanta.db";  
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
  public static void main(String[] args) {  
      connect();  
  }  
}
