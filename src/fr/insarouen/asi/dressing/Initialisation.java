package fr.insarouen.asi.dressing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.TypeEvenement;
import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import java.sql.ResultSet;
import java.sql.Statement;
 
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


    
    public static void menuDressing() {
        System.out.println("--------------------------DRESSING--------------------------\n");
        System.out.println("tapez 1 pour ajouter un nouvel objet au dressing");
        System.out.println("tapez 2 pour consulter votre dressing");
        System.out.println("tapez 3 pour supprimer un objet de votre dressing");
        System.out.println("tapez 4 pour créer une tenue");
        System.out.println("tapez 5 pour revenir au menu precedent\n");
    }

    public static void menuAjouterDansDressing() {
        System.out.println("----------------------------AJOUT----------------------------\n");
        System.out.println("tapez 1 pour entrer un nouveau sac");
        System.out.println("tapez 2 pour entrer des nouvelles chaussures");
        System.out.println("tapez 3 pour entrer un nouveau vetement");
        System.out.println("tapez 4 pour revenir au menu precedent\n");
    }

    public static void menuConsulterDressing() {
        System.out.println("------------------------CONSULTATION------------------------\n");
        System.out.println("tapez 1 pour consulter vos sacs");
        System.out.println("tapez 2 pour consulter vos chaussures");
        System.out.println("tapez 3 pour consultez vos vetements");
        System.out.println("tapez 4 pour revenir au menu precedent\n");

    }

    public static void menuSupprimerDansDressing() {
        System.out.println("------------------------SUPPRESSION------------------------\n");
        System.out.println("tapez 1 pour supprimer un sac");
        System.out.println("tapez 2 pour supprimer des chaussures");
        System.out.println("tapez 3 pour supprimer un vetement");
        System.out.println("tapez 4 pour revenir au menu precedent\n");
    }
    
    public static void menuCreerTenueDansDressing() {
       
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

    public static void ajouterDansDressing(int idDressing) throws SQLException {
        
        menuAjouterDansDressing();
        Scanner scAjout = new Scanner(System.in);
        switch (scAjout.nextInt()) {
            case 1:
                Sac sac = new Sac();
                boolean bsac = sac.ajouterSac(idDressing);
                break;
            case 2:
                Chaussures c = new Chaussures();
                boolean bc = c.ajouterChaussures(idDressing);
                break;
            case 3:
                Vetement v = new Vetement();
                boolean bv = v.ajouterVetement(idDressing);
                break;
            case 4:
                explorerDressing(idDressing);
                break;
            default:
                break;
        }
    }

    public static void supprimerDansDressing(int idDressing) throws SQLException {
        menuSupprimerDansDressing();
        Scanner scSup = new Scanner(System.in);
        switch (scSup.nextInt()) {
            case 1:
                Sac sac = new Sac();
                boolean bsac = sac.supprimerSac();
                break;
            case 2:
                Chaussures c = new Chaussures();
                boolean bc = c.supprimerChaussures();
                break;
            case 3:
                Vetement v = new Vetement();
                boolean bv = v.supprimerVetement();
                break;
            case 4:
                explorerDressing(idDressing);
                break;
            default:
                break;
        }
    }

    public static void consulterDressing(int idDressing) throws SQLException {
        menuConsulterDressing();
        Scanner scCons = new Scanner(System.in);
        switch (scCons.nextInt()) {
            case 1:
                Sac.afficherSacs();
                break;
            case 2:
                Chaussures.afficherChaussures();
                break;
            case 3:
                //VetementDAO.afficherVetements();
                Vetement.afficherHauts();
                Vetement.afficherBas();
                Vetement.afficherHautsBas();
                break;
            case 4:
                explorerDressing(idDressing);
                break;
            default:
                break;
        }
    }
    
       public static void menuCreerTenue(int idDressing) throws SQLException {
           Tenue t = new Tenue();
        Scanner sc = new Scanner(System.in);
        System.out.println("Evenement :");
        System.out.println("1: Tous Les Jours\t 2: Sport\t 3: Soiree\t");
        TypeEvenement evt = TypeEvenement.getfromInt(sc.nextInt());
        System.out.println("Type de tenue :");
        System.out.println("1: Tenue normale\t 2: Tenue avec un contenu particulier\t 3: Tenue avec un type particulier\t");
        int typeTenue = sc.nextInt();
        System.out.println("Tenue accordee a la forme ? \n 1: Oui\t 2: Non");
        int avecForme = sc.nextInt();
        try{
            System.out.println(t.creerTenue(typeTenue, avecForme, idDressing, evt).toString());
            explorerDressing(idDressing);
        } catch (TenueImpossibleException e) {
            System.out.println(e);
            explorerDressing(idDressing);
        }
    }

    public static void explorerDressing(int idDressing) throws SQLException{
        
            menuDressing();
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
                    menuCreerTenue(idDressing);
                    break;
                case 5:
                    lancer();
                    break;
                default:
                    break;
            } 
    }

    public static void lancer() throws SQLException {
        Scanner sc = new Scanner(System.in);
        menuGeneral();
        switch (sc.nextInt()) {
            case 1:
                Utilisateur user1 = new Utilisateur();
                boolean buser1 = user1.ajouterUtilisateur();
                lancer();
                break;
            case 2:
                Utilisateur user2 = new Utilisateur();
                boolean buser2 = user2.supprimerUtilisateur();
                lancer();
                break;
            case 3:
                Scanner scId = new Scanner(System.in);
                System.out.println("Votre id d'utilisateur ?");
                int id = scId.nextInt();
                try {
                    Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    st.executeQuery("select d.iddressing from personne p, dressing d where p.idpers=d.idpers and p.idpers=" + id);
                    ResultSet res = (st.getResultSet());
                    if (res.first()) {
                        id = res.getInt("iddressing");
                    }else{ 
                        throw new IdNonPresentException("L'id saisi n'est pas correct.");
                    }
                    Chaussures.initialiserChaussures(id);
                    Sac.initSacs(id);
                    Vetement.initiVetements(id);
                    explorerDressing(id);
                    
                }catch(IdNonPresentException e){
                    System.out.println(e);
                }
                lancer();
                break;
            case 4:
                System.out.println("deconnection");
                c.close();
            default:
                break;
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
