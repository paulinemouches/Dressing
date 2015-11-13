package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.dao.concret.ChaussuresDAO;
import fr.insarouen.asi.dressing.dao.concret.SacDAO;
import fr.insarouen.asi.dressing.dao.concret.VetementDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;

import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Vetement;


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
        System.out.println("---------------------------ACCEUIL---------------------------\n");
        System.out.println("tapez 1 pour entrer un nouvel utilisateur");
        System.out.println("tapez 2 pour supprimer  un utilisateur");
        System.out.println("tapez 3 pour accéder à un dressing existant");
        System.out.println("tapez 4 pour vous déconnecter");
    }
    
        public static void menuDressing(int id) {
        System.out.println("--------------------------DRESSING--------------------------\n");
        System.out.println("tapez 1 pour ajouter un nouvel objet au dressing");
        System.out.println("tapez 2 pour consulter votre dressing");
        System.out.println("tapez 3 pour supprimer un objet de votre dressing");
        System.out.println("tapez 4 pour revenir au menu precedent\n");
    }

    public static void menuAjouterDansDressing(int id) {
        System.out.println("----------------------------AJOUT----------------------------\n");
        System.out.println("tapez 1 pour entrer un nouveau sac");
        System.out.println("tapez 2 pour entrer des nouvelles chaussures");
        System.out.println("tapez 3 pour entrer un nouveau vetement");
        System.out.println("tapez 4 pour revenir au menu precedent\n");
    }
    
    public static void menuConsulterDressing(int id) {
        System.out.println("------------------------CONSULTATION------------------------\n");
        System.out.println("tapez 1 pour consulter vos sacs");
        System.out.println("tapez 2 pour consulter vos chaussures");
        System.out.println("tapez 3 pour consultez vos vetements");
        System.out.println("tapez 4 pour revenir au menu precedent\n");
    }
    
    public static void menuSupprimerDansDressing(int id) {
        System.out.println("------------------------SUPPRESSION------------------------\n");
        System.out.println("tapez 1 pour supprimer un sac");
        System.out.println("tapez 2 pour supprimer des chaussures");
        System.out.println("tapez 3 pour supprimer un vetement");
        System.out.println("tapez 4 pour revenir au menu precedent\n");
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
    
    public static void ajouterDansDressing(int id) throws SQLException {
       // Dire que l'id d'utilisateur est le meme que l'id dressing c'est pas très juste ... 
        int idDressing = id;
        menuAjouterDansDressing(idDressing);
        Scanner scAjout = new Scanner(System.in);
        switch (scAjout.nextInt()) {
            case 1:
                Sac sac = new Sac();
                boolean bsac = sac.ajouterSac(idDressing);
                break;
            case 2 : 
                Chaussures c = new Chaussures();
                boolean bc = c.ajouterChaussures(idDressing);
                break;
            case 3 :
                Vetement v = new Vetement();
                boolean bv = v.ajouterVetement(idDressing);
                break;
            case 4 :
                explorerDressing(idDressing);
                break;
            default : 
                break;
        }
    }
    
    public static void supprimerDansDressing(int id) throws SQLException {
       // Dire que l'id d'utilisateur est le meme que l'id dressing c'est pas très juste ... 
        int idDressing = id;
        menuSupprimerDansDressing(idDressing);
        Scanner scSup = new Scanner(System.in);
        switch (scSup.nextInt()) {
            case 1:
                Sac sac = new Sac();
                boolean bsac = sac.supprimerSac();
                break;
            case 2 : 
                Chaussures c = new Chaussures();
                boolean bc = c.supprimerChaussures();
                break;
            case 3 :
                Vetement v = new Vetement();
                boolean bv = v.supprimerVetement();
                break;
            case 4 :
                explorerDressing(idDressing);
                break;
            default : 
                break;
        }
    }
    
    public static void consulterDressing(int id) throws SQLException {
       // Dire que l'id d'utilisateur est le meme que l'id dressing c'est pas très juste ... 
        int idDressing = id;
        menuConsulterDressing(idDressing);
        Scanner scCons = new Scanner(System.in);
        switch (scCons.nextInt()) {
            case 1:
                Sac.afficherSacs();
                break;
            case 2 : 
                Chaussures.afficherChaussures();
                break;
            case 3 :
                //VetementDAO.afficherVetements();
                Vetement.afficherHauts();
                Vetement.afficherBas();
                Vetement.afficherHautsBas();
                break;
            case 4 :
                explorerDressing(idDressing);
                break;
            default : 
                break; 
        }
    }
    
    public static void explorerDressing(int id) throws SQLException {
        // Dire que l'id d'utilisateur est le meme que l'id dressing c'est pas très juste ... 
        int idDressing = id;
        menuDressing(idDressing);
        Scanner scDressing = new Scanner(System.in);
        switch (scDressing.nextInt()) {
            case 1:
                ajouterDansDressing(idDressing);
                explorerDressing(idDressing);
                break;
            case 2:
                consulterDressing(idDressing);
                explorerDressing(idDressing);
                break;
            case 3:
                supprimerDansDressing(idDressing);
                explorerDressing(idDressing);
                break;
            case 4:
                lancer();
                break;
            default : 
                break;
        }
    }

   

    public static void lancer() throws SQLException {
       Chaussures.initialiserChaussures();
       Sac.initSacs();
       Vetement.initiVetements();
        menuGeneral();
        Scanner sc = new Scanner(System.in);
        switch (sc.nextInt()) {
            case 1:
                Utilisateur user1 = new Utilisateur();
                boolean buser1 = user1.ajouterUtilisateur();
                menuGeneral();
                break;
            case 2:
                Utilisateur user2 = new Utilisateur();
                boolean buser2 = user2.supprimerUtilisateur();
                menuGeneral();
                break;
            case 3:
                Scanner scId = new Scanner(System.in);
                System.out.println("Votre id d'utilisateur ?");
                explorerDressing(scId.nextInt());
                menuGeneral();
                break;
            case 4:
                System.out.println("deconnection");
                c.close();
            default :
                break;
        }

    }

    public static void main(String[] args) {
        try {
            connexion();
            //test couleurs
            //Couleurs coul = new Couleurs();
           // int entier = coul.recupererValeurAssociee(2,1);
           // System.out.println("la couleur associee a 1 est :"+entier);
            lancer();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}