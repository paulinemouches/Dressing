package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.dao.concret.UtilisateurDAO;
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
import java.io.Console;
import java.util.ArrayList;

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
        System.out.println("tapez 5 pour mettre des vêtements au sale ou au propre");
        System.out.println("tapez 6 pour revenir au menu precedent\n");
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
        System.out.println("tapez 4 pour consultez vos vetements de cette saison");
        System.out.println("tapez 5 pour consultez vos vetements de votre couleur préférée");
        System.out.println("tapez 6 pour consultez vos vetements en fonction d'une forme");
        System.out.println("tapez 7 pour consultez vos vetements en fonction d'un type");
        System.out.println("tapez 8 pour revenir au menu precedent\n");

    }

    public static void menuSupprimerDansDressing() {
        System.out.println("------------------------SUPPRESSION------------------------\n");
        System.out.println("tapez 1 pour supprimer un sac");
        System.out.println("tapez 2 pour supprimer des chaussures");
        System.out.println("tapez 3 pour supprimer un vetement");
        System.out.println("tapez 4 pour revenir au menu precedent\n");
    }

    public static void connexion() {
        String nomBase;
        String nomUtilisateur;
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom de la base ?");
        nomBase = sc.next();
        System.out.println("Utilisateur?");
        nomUtilisateur = sc.next();
        System.out.println("mdp ?");
        String password = sc.next();
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + nomBase, nomUtilisateur, password);

            System.out.println("Connecté à la base ");
            System.out.println();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void ajouterDansDressing(int idDressing) throws SQLException {
        Scanner scAjout = new Scanner(System.in);
        boolean exit = false;
        do {
            menuAjouterDansDressing();
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
                    exit = true;
                    break;
                default:
                    System.out.println("Veuillez saisir un chiffre entre 1 et 4");
                    break;
            }
        } while (!exit);
    }

    public static void supprimerDansDressing(int idDressing) throws SQLException {
        Scanner scSup = new Scanner(System.in);
        boolean exit = false;
        do {
            menuSupprimerDansDressing();
            switch (scSup.nextInt()) {
                case 1:
                    Sac sac = new Sac();
                    boolean bsac = sac.supprimerSac(idDressing);
                    if (bsac) {
                        System.out.println("Le sac a été suprimé");
                    }
                    else {
                        System.out.println("Une erreur est survenue (l'id entré n'est peut être pas le bon)");
                    }
                    break;
                case 2:
                    Chaussures c = new Chaussures();
                    boolean bc = c.supprimerChaussures(idDressing);
                    if (bc) {
                        System.out.println("Les chaussures ont été suprimé");
                    }
                    else {
                        System.out.println("Une erreur est survenue (l'id entré n'est peut être pas le bon)");
                    }
                    break;
                case 3:
                    Vetement v = new Vetement();
                    boolean bv = v.supprimerVetement(idDressing);
                    if (bv) {
                        System.out.println("Le vêtement a été suprimé");
                    }
                    else {
                        System.out.println("Une erreur est survenue (l'id entré n'est peut être pas le bon)");
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Veulillez saisir un chiffre entre 1 et 4");
                    break;
            }
        } while (!exit);
    }

    public static void consulterDressing(int idDressing) throws SQLException {
        Scanner scCons = new Scanner(System.in);
        boolean exit = false;
        do {
            menuConsulterDressing();
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
                    Vetement.afficherVetementsSaison(idDressing);
                    break;
                case 5:
                    Vetement.afficherVetementsCouleurPreferee(idDressing);
                    break;
                case 6:
                    Vetement.afficherVetementsForme(idDressing);
                    break;
                case 7:
                    Vetement.afficherVetementsType(idDressing);
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Veulillez saisir un chiffre entre 1 et 7");
                    break;
            }
        } while (!exit);
    }

    public static void menuCreerTenue(int idDressing) throws SQLException {
        int suivant;
        int idUtilisateur = UtilisateurDAO.obtenirIdUtilisateur(idDressing);
        Scanner sc = new Scanner(System.in);
        System.out.println("Evenement :");
        System.out.println("1: Tous Les Jours\t 2: Sport\t 3: Soiree\t");
        TypeEvenement evt = TypeEvenement.getfromInt(sc.nextInt());
        System.out.println("Type de tenue :");
        System.out.println("1: Tenue normale\t 2: Tenue avec un contenu particulier\t 3: Tenue avec un type particulier\t");
        int typeTenue = sc.nextInt();
        System.out.println("Tenue accordee a la forme ? \n 1: Oui\t 2: Non");
        int avecForme = sc.nextInt();
        Tenue t = new Tenue();
        int[] tableauIdChoisis = {0, 0, 0};
        ArrayList<Vetement> vetementsTypeChoisis = new ArrayList();
        try {
            if (typeTenue == 2) {
                tableauIdChoisis = t.menuCreerTenueContenuParticulier();
            }
            if (typeTenue == 3) {
                vetementsTypeChoisis = t.menuCreerTenueTypeParticulier();
            }
            do {
                creationTenue(tableauIdChoisis, vetementsTypeChoisis, typeTenue, avecForme, idUtilisateur, evt);
                System.out.println("Appuyer sur 1 pour voir la tenue suivante, sur 0 pour quitter");
                suivant = sc.nextInt();
            } while (suivant == 1);//tant qu'on tape 1, ça calcule la tenue suivante
        } catch (TenueImpossibleException e) {
            System.out.println(e);
        }
    }
//séparation de la methode en deux, plus facile pour faire les 2 boucles necessaires
    public static void creationTenue(int[] tableauIdChoisis, ArrayList<Vetement> vetementsTypeChoisis, int typeTenue, int avecForme, int idUtilisateur, TypeEvenement evt) throws SQLException {
        int suivant = 0;
        boolean flag;//change si l'exception est levée
        Scanner sc = new Scanner(System.in);
        Tenue t = new Tenue();
        do {
            try {
                t=new Tenue();
               t.creerTenue(tableauIdChoisis, vetementsTypeChoisis, typeTenue, avecForme, idUtilisateur, evt).toString();
                flag = false;//l'exception n'est pas levée, le flag est faux, on sort de la boucle
            } catch (TenueImpossibleException e) {
                flag=true;//si l'exception est levée, le flag passe a true, on va refaire la boucle
                System.out.println("Attention !");
            }
        } while (flag);//tant que l'exception est levée, on recommence a creer une nouvelle tenue (toujours avec les meme caractéristiques)
        System.out.println(t);
    }

    public static void mettreAuSaleOuPropre(int idDressing) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean mettreAuSale = false;
        int idVetement = 10; //id bidon pour entrer au moins une fois dans le while
        System.out.println("1: Mettre au sale\t 2: Mettre au propre\t");
        int salePropre = sc.nextInt();
        if (salePropre == 1) {
            mettreAuSale = true; //je veux mettre des vetements au sale
        }
        boolean ok = Vetement.afficherVetementsSaleOuPropre(!mettreAuSale);//je veux donc afficher les vetements propres
        if (ok) {
            while (idVetement != 0) {
                System.out.println("id du vetement à mettre à mettre au sale ? (tapez 0 si plus de vetements a mettre au sale)");
                idVetement = sc.nextInt();
                if (idVetement != 0) {
                    Vetement.modifierSalePropre(idVetement, idDressing, mettreAuSale);
                }
            }
        }
        else {
            System.out.println("Pas de vêtements correspondants");
        }
    }

    public static void explorerDressing(int idDressing) throws SQLException {
        Scanner scDressing = new Scanner(System.in);
        boolean exit = false;
        do {
            menuDressing();
            switch (scDressing.nextInt()) {
                case 1:
                    ajouterDansDressing(idDressing);
                    break;
                case 2:
                    consulterDressing(idDressing);
                    break;
                case 3:
                    supprimerDansDressing(idDressing);
                    break;
                case 4:
                    menuCreerTenue(idDressing);
                    break;
                case 5:
                    mettreAuSaleOuPropre(idDressing);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Veulillez saisir un chiffre entre 1 et 6");
                    break;
            }
        } while (!exit);
    }

    public static void lancer() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        do {
            menuGeneral();
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
                    int id = scId.nextInt();
                    try {
                        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        st.executeQuery("select d.iddressing from dressing d where d.idpers=" + id);
                        ResultSet res = (st.getResultSet());
                        if (res.first()) {
                            id = res.getInt("iddressing");
                        }
                        else {
                            throw new IdNonPresentException("L'id saisi n'est pas correct.");
                        }
                        Chaussures.initialiserChaussures(id);
                        Sac.initSacs(id);
                        Vetement.initiVetements(id);
                        explorerDressing(id);

                    } catch (IdNonPresentException e) {
                        System.out.println(e);
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Veulillez saisir un chiffre entre 1 et 4");
                    break;
            }
        } while (!exit);
        System.out.println("deconnection");
        c.close();
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
