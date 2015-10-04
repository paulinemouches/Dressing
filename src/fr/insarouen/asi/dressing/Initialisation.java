package fr.insarouen.asi.dressing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import java.sql.*;
import org.postgresql.Driver;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;

public class Initialisation {

    private String nomBase;
    private String utilisateur;

    private static Connection c = null;

    public Initialisation() {
    }

    public static Connection getC() {
        return c;
    }
    
    public void menu(){
         System.out.println("tapez 1 pour entrer un nouvel utilisateur");
    }
    
    public void lancer(){
        menu();
        Scanner sc = new Scanner(System.in);
        if(sc.nextInt()==1){
            ajouterUtilisateur();
        }
        
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "dressing", "pauline", "pauline");

            System.out.println("Connecté à la base ");
            System.out.println();
           

            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

}
