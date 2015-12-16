package fr.insarouen.asi.dressing.elements.objets;

import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.Matiere;
import fr.insarouen.asi.dressing.elements.Niveau;
import fr.insarouen.asi.dressing.elements.CoupeVetement;
import fr.insarouen.asi.dressing.dao.concret.VetementDAO;
import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.Signe;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import java.util.HashMap;
import java.util.Iterator;

public class Vetement extends Contenu {

    private Niveau niveau;
    private Signe[] signes;
    private CoupeVetement coupe;
    private TypeVetement type;
    private Matiere matiere;

    private String fils;
    private int couche;
    private boolean sale;
    public static HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
    public static HashMap<Integer, Vetement> hauts = new HashMap<Integer, Vetement>();
    public static HashMap<Integer, Vetement> bas = new HashMap<Integer, Vetement>();
    public static HashMap<Integer, Vetement> hautsbas = new HashMap<Integer, Vetement>();

    /* Constructeurs */
    public Vetement(int idV, int idDressing, Couleur couleur, CoupeVetement coupe, TypeVetement type, Matiere matiere, Signe[] signes, int couche, Niveau niveau) throws SQLException {
        super(couleur, idV, idDressing);
        this.coupe = coupe;
        this.type = type;
        this.matiere = matiere;
        this.sale = determinerSalePropre();
        this.niveau = niveau;
        this.couche = couche;
        this.signes = signes;
        this.fils = determinerFils(type);
    }

    public Vetement() {
    }

    /* Les méthodes */
    public static HashMap<Integer, Vetement> getHauts() {
        return hauts;
    }

    public static HashMap<Integer, Vetement> getBas() {
        return bas;
    }

    public static HashMap<Integer, Vetement> getHautsbas() {
        return hautsbas;
    }

    public static HashMap<Integer, Vetement> getVetements() {
        return vetements;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Signe[] getSignes() {
        return signes;
    }

    public CoupeVetement getCoupe() {
        return coupe;
    }

    public TypeVetement getType() {
        return type;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public int getCouche() {
        return couche;
    }

    public String getFils() {
        return fils;
    }

    public boolean isSale() {
        return sale;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public void setSignes(Signe[] signes) {
        this.signes = signes;
    }

    public void setCoupe(CoupeVetement coupe) {
        this.coupe = coupe;
    }

    public void setType(TypeVetement type) {
        this.type = type;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public void setCouche(int couche) {
        this.couche = couche;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public void setFils(String fils) {
        this.fils = fils;
    }

    public String determinerSaison() {
        return VetementDAO.recupererSaison(this);
    }

    public String determinerFils(TypeVetement typeVetement) {
        String resultat;
        switch (typeVetement) {
            case Teeshirt:
                resultat = "Haut";
                break;
            case Chemisier:
                resultat = "Haut";
                break;
            case Pull:
                resultat = "Haut";
                break;
            case Veste:
                resultat = "Haut";
                break;
            case Manteau:
                resultat = "Haut";
                break;
            case Jogging:
                resultat = "Pantalon";
                break;
            case Pantalon:
                resultat = "Pantalon";
                break;
            case Pantacourt:
                resultat = "Pantalon";
                break;
            default:
                resultat = "Autre";
        }
        return resultat;
    }

    public Niveau determinerNiveau() throws SQLException {
        return VetementDAO.recupererNiveau(this);
    }

    public Signe[] determinerSignes() throws SQLException {
        return VetementDAO.recupererSignes(this);
    }

    public int determinerCouche() throws SQLException {
        return VetementDAO.recupererCouche(this);
    }
    
    public boolean determinerSalePropre()throws SQLException{
         return VetementDAO.recupererSalePropre(this);
    }

    public boolean correspondAuSigne(Utilisateur u) {
        boolean res = false;
        for (int i = 1; i < signes.length; i++) {
            if (u.getSigneUtilisateur().equals(this.signes[i])) {
                return true;
            }
        }
        return res;
    }

    public Vetement menuAjouterVetementTxt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le type de vêtement : ");
        System.out.println("1: Tee-shirt\t 2: Chemisier\t 3: Pull\t 4: Veste\t 5: Manteau\t 6: Pantalon\t 7: Pantacourt\t 8: Jogging\t 9: Jupe\t 10: Short\t 11: Robe\t 12: Combinaison ");
        int numero = sc.nextInt();
        TypeVetement type = TypeVetement.getfromInt(numero);
        if (type == null) {
            return null;
        }

        System.out.println("Entrez la matière : ");
        System.out.println("1: Laine\t 2: Coton\t 3: Jean\t\t 4: Lin\t\t 5: Velours\t 6: Cuir\t 7: Dentelle\t 8: Daim\t 9: Satin\t 10: Paillette");
        Matiere matiere = Matiere.getfromInt(sc.nextInt());
        if (matiere == null) {
            return null;
        }

        System.out.println("Entrez la coupe : ");
        if ((numero == 1) || (numero == 2) || (numero == 3) || (numero == 4) || (numero == 5)) {
            System.out.println("1: Cintré\t 2: Droit\t 3: Large\t");
        }
        else {
            if ((numero == 6) || (numero == 7) || (numero == 8)) {
                System.out.println("2: Droit\t 4: Slim\t 5: Evase\t 6: Baggy\t");
            }
            else {
                if ((numero == 9) || (numero == 10) || (numero == 11) || (numero == 12)) {
                    System.out.println("7:Court\t 8: Long");
                }
            }
        }
        CoupeVetement coupe = CoupeVetement.getfromInt(sc.nextInt());
        if (coupe == null) {
            return null;
        }

        System.out.println("Entrez la couleur : ");
        System.out.println("1 - bleu\n"
                + "2 - bleu clair\n"
                + "3 - bleu marine\n"
                + "4 - turquoise\n"
                + "5 - gris clair\n"
                + "6 - argenté\n"
                + "7 - gris foncé\n"
                + "8 - marron clair\n"
                + "9 - marron foncé\n"
                + "10 - corail\n"
                + "11 - orange\n"
                + "12 - bordeau\n"
                + "13 - brique\n"
                + "14 - rouge\n"
                + "15 - rose pale\n"
                + "16 - rose fushia\n"
                + "17 - rose foncé\n"
                + "18 - mauve\n"
                + "19 - violet\n"
                + "20 - prune\n"
                + "21 - blanc\n"
                + "22 - jaune moutarde \n"
                + "23 - jaune\n"
                + "24 - doré\n"
                + "25 - noir\n"
                + "26 - kaki\n"
                + "27 - vert pale\n"
                + "28 - vert\n"
                + "29 - jean clair\n"
                + "30 - jean marine\n"
                + "31 - beige");
        Couleur couleur = new Couleur(sc.nextInt());

        String fils = determinerFils(type);

        Vetement v = new Vetement();
        v.setCouleur(couleur);
        v.setCoupe(coupe);
        v.setMatiere(matiere);
        v.setSale(false);
        v.setType(type);
        v.setFils(fils);
        return v;
    }

    public boolean ajouterVetement(int idDressing) throws SQLException {
        // Attention à gérer les exceptions !!! 
        Vetement v = menuAjouterVetementTxt();
        v.setIdDressing(idDressing);

        VetementDAO nouveauVetement = new VetementDAO();
        nouveauVetement.create(v);

        Niveau niveau = v.determinerNiveau();
        int couche = v.determinerCouche();
        Signe signes[] = v.determinerSignes();

        v.setNiveau(niveau);
        v.setCouche(couche);
        v.setSignes(signes);

        v.ajouterVetementDansListe();
        return true;
    }

    public boolean supprimerVetement() throws SQLException {
        // Attention à gérer les exceptions !!! 
        VetementDAO vASupprimer = new VetementDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez l'id du vêtement à supprimer");
        int id = sc.nextInt();
        if (vASupprimer.find(id) != null) {
            vASupprimer.find(id).supprimerVetementDansListe(id);
            vASupprimer.delete(vASupprimer.find(id));
            return true;
        }
        else {
            return false;
        }
    }

    public static Vetement trouverVetement(int id) throws SQLException {
        VetementDAO v = new VetementDAO();
        return v.find(id);
    }

    public void ajouterVetementDansListe() throws SQLException {
        vetements.put(getIdObjet(), this);
        switch (this.getNiveau()) {
            case Haut:
                hauts.put(getIdObjet(), this);
                break;
            case Bas:
                bas.put(getIdObjet(), this);
                break;
            case Hautbas:
                hautsbas.put(getIdObjet(), this);
                break;
        }
    }

    public static void initiVetements(int id) throws SQLException {
        // On vide réinitialise les tableaux de vêtements :
        vetements.clear();
        hauts.clear();
        bas.clear();
        hautsbas.clear();
        HashMap<Integer, Vetement> vetements = VetementDAO.initialiserVetements(id);
        for (Vetement v : vetements.values()) {
            v.ajouterVetementDansListe();
        }
    }

    public void supprimerVetementDansListe(int id) throws SQLException {
        vetements.remove(id);
        if (hauts.containsKey(id)) {
            hauts.remove(id);
            System.out.println("Le haut est supprimé");
        }
        else {
            if (bas.containsKey(id)) {
                bas.remove(id);
                System.out.println("Le bas est supprimé");
            }
            else {
                if (hautsbas.containsKey(id)) {
                    hautsbas.remove(id);
                    System.out.println("Le haut-bas est supprimé");
                }
            }
        }
    }

    public static void afficherHauts() throws SQLException {
        if (!hauts.isEmpty()) {
            for (Vetement v : hauts.values()) {
                System.out.println(v.toString());
            }
        }
        else {
            System.out.println("\nIl n'y a pas de hauts");
        }
    }

    public static void afficherBas() throws SQLException {
        if (!bas.isEmpty()) {
            for (Vetement v : bas.values()) {
                System.out.println(v.toString());
            }
        }
        else {
            System.out.println("\nIl n'y a pas de bas");
        }
    }

    public static void afficherHautsBas() throws SQLException {
        if (!hautsbas.isEmpty()) {
            for (Vetement v : hautsbas.values()) {
                System.out.println(v.toString());
            }
        }
        else {
            System.out.println("\nIl n'y a pas de hauts-bas");
        }
    }

    public static void afficherVetementsSaleOuPropre(boolean estSale) {
        if (estSale) {
            for (Vetement v : vetements.values()) {
                if (v.isSale()) {
                    System.out.println(v.toString());
                }
            }
        }
        else {
            for (Vetement v : vetements.values()) {
                if (!v.isSale()) {
                    System.out.println(v.toString());
                }
            }
        }
    }

    public static void modifierSalePropre(int idVetement, boolean mettreAuSale) {
        // try {
        VetementDAO vd = new VetementDAO();
        vd.modifierSalePropreBD(idVetement, mettreAuSale);
            //trouverVetement(idVetement).setSale(mettreAuSale);
        //} catch (SQLException e) {
        //e.printStackTrace();
        //}
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        s.append("\nVetement : \n\t idObjet : " + getIdObjet() + "\n\t Type : " + type + "\n\t Coupe : " + coupe + "\n\t Signes : ");

        for (Signe si : signes) {
            s.append(si.toString() + ", ");
        }
        s.append("\n\t Matiere : " + matiere + "\n\t Couleur : " + getCouleur() + "\n\t Couche : " + couche + "\n\t Est sale : " + sale);
        return s.toString();
    }

}
