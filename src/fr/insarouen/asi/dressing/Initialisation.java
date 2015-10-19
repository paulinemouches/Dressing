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

import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;


public class Initialisation {

    private String nomBase;
    private String utilisateur;

    private static Connection c = null;

    public Initialisation() {
    }

    public static Connection getC() {
        return c;
    }

    public static void menuGeneral() {
        System.out.println("tapez 1 pour entrer un nouvel utilisateur");
        System.out.println("tapez 2 pour supprimer  un utilisateur");
        System.out.println("tapez 3 pour accéder à un dressing existant");
    }

    public static void menuDressing(int id) {
        System.out.println("tapez 1 pour entrer un nouveau sac");
        System.out.println("tapez 2 pour supprimer  un sac");
        System.out.println("tapez 3 pour afficher vos sac");
        System.out.println("tapez 4 pour entrer des nouvelles chaussures");
        System.out.println("tapez 5 pour supprimer  des chaussures");
        System.out.println("tapez 6 pour afficher vos chaussures");
                System.out.println("tapez 7 pour revenir au menu precedent");
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

    public static void explorerDressing(int id) throws SQLException {
        int idDressing = id;
        menuDressing(idDressing);
        Scanner scDressing = new Scanner(System.in);
        switch (scDressing.nextInt()) {
            case 1:
                Sac sac1 = new Sac();
                boolean bsac1 = sac1.ajouterSac(idDressing);
                explorerDressing(idDressing);
                break;
            case 2:
                Sac sac2 = new Sac();
                boolean bsac2 = sac2.supprimerSac();
                explorerDressing(idDressing);
                break;
            case 3:
                Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                sts.executeQuery("SELECT idObjet FROM Sac");
                ResultSet ress = sts.getResultSet();
                while (ress.next()) {
                    Sac sac = new Sac();
                    System.out.println(sac.trouverSac(ress.getInt(1)));
                }
                System.out.println("tapez 1 pour revenir au menu");
                if (scDressing.nextInt() == 1) {
                    explorerDressing(idDressing);
                }
                break;
            case 4:
                Chaussures c1 = new Chaussures();
                boolean bc1 = c1.ajouterChaussures(idDressing);
                explorerDressing(idDressing);
                break;
            case 5:
                Chaussures c2 = new Chaussures();
                boolean bc2 = c2.supprimerChaussures();
                explorerDressing(idDressing);
                break;
            case 6:
                Statement stc = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stc.executeQuery("SELECT idObjet FROM CHAUSSURE");
                ResultSet resc = stc.getResultSet();
                while (resc.next()) {
                    Chaussures c =new Chaussures();
                    System.out.println(c.trouverChaussures(resc.getInt(1)));
                }
                System.out.println("tapez 1 pour revenir au menu");
                if (scDressing.nextInt() == 1) {
                    explorerDressing(idDressing);
                }
                break;
            case 7:
                lancer();
                break;
            default:
        }
    }

    public static void lancer() throws SQLException {
        menuGeneral();
        Scanner sc = new Scanner(System.in);
        switch (sc.nextInt()) {
            case 1:
                Utilisateur user1 = new Utilisateur();
                boolean buser1 = user1.ajouterUtilisateur();
                break;
            case 2:
                Utilisateur user2 = new Utilisateur();
                boolean buser2 = user2.supprimerUtilisateur();
                break;
            case 3:
                Scanner scId = new Scanner(System.in);
                System.out.println("Votre id d'utilisateur ?");
                explorerDressing(scId.nextInt());
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
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}