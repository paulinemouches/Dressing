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
import fr.insarouen.asi.dressing.dao.concret.UtilisateurDAO;
import fr.insarouen.asi.dressing.dao.DAO;

public class Initialisation {

    private String nomBase;
    private String utilisateur;

    private static Connection c = null;

    public Initialisation() {
    }

    public static Connection getC() {
        return c;
    }

    public static void menu() {
        System.out.println("tapez 1 pour entrer un nouvel utilisateur");
        System.out.println("tapez 2 pour supprimer utilisateur");
    }

    public static void connexion() {
        String nomBase;
        String nomUtilisateur;
        String mdp;
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom de la base ?");
        nomBase = sc.next();
        System.out.println("Utilisateur?");
        nomUtilisateur = sc.next();
        System.out.println("mdp ?");
        mdp = sc.next();
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + nomBase, nomUtilisateur, mdp);

            System.out.println("Connecté à la base ");
            System.out.println();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void lancer() throws SQLException {
        menu();
        Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    Utilisateur user1 = new Utilisateur();
                    user1.ajouterUtilisateur();
                    break;
                case 2:
                    Utilisateur user2 = new Utilisateur();
                    user2.supprimerUtilisateur();
                    break;
                default:
                    System.out.println("deconnection");
                    c.close();
            }

    }

    public static void main(String[] args) {
        try {
            connexion();
            lancer();
            DAO<Utilisateur> utilisateurDao = new UtilisateurDAO();
            for (int i = 1; i < 9; i++) {
                if (utilisateurDao.find(i).getNom() != null) {
                    System.out.println(utilisateurDao.find(i));
                }
            }
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
