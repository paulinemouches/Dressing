package fr.insarouen.asi.dressing.elements.objets;

import fr.insarouen.asi.dressing.Initialisation;
import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.Matiere;
import fr.insarouen.asi.dressing.elements.Niveau;
import fr.insarouen.asi.dressing.elements.CoupeVetement;
import fr.insarouen.asi.dressing.dao.concret.VetementDAO;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Vetement {

    private int idObjet;
    private int idDressing;
    private Niveau niveau;
    private String[] signes;
    private CoupeVetement coupe;
    private TypeVetement type;
    private Matiere matiere;

    private String fils;
    private String couleur;
    private int couche;
    private boolean sale;
    public static ArrayList<Vetement> hauts;
    public static ArrayList<Vetement> bas;
    public static ArrayList<Vetement> hautsbas;

    // Il manque la couleur !! 
    /* Constructeurs */
    public Vetement(int idV, int idDressing, String couleur, CoupeVetement coupe, TypeVetement type, Matiere matiere, String[] signes, int couche, Niveau niveau) throws SQLException{
        this.idObjet = idV;
        this.idDressing = idDressing;
        this.couleur = couleur;
        this.coupe = coupe;
        this.type = type;
        this.matiere = matiere;
        this.sale = false;
        this.niveau = niveau;
        this.couche = couche;
        this.signes = signes;
        this.fils = determinerFils(type);
        //hauts = new ArrayList<Vetement>();
        //bas = new ArrayList<Vetement>();
        //hautsbas = new ArrayList<Vetement>();
        //this.ajouterVetementDansListe();
    }

    public Vetement() {
    }

        /* Les méthodes */
    public ArrayList<Vetement> getHauts() {
        return hauts;
    }

    public ArrayList<Vetement> getBas() {
        return bas;
    }


    public ArrayList<Vetement> getHautsbas() {
        return hautsbas;
    }

    public int getIdV() {
        return idObjet;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getIdDressing() {
        return idDressing;
    }

    public String[] getSignes() {
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

    public void setIdV(int idV) {
        this.idObjet = idV;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public void setSignes(String[] signes) {
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

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String determinerSaison(Vetement v){
        return VetementDAO.recupererSaison(v);
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
    
    public String[] determinerSignes() throws SQLException{
        return VetementDAO.recupererSignes(this);
    }

    public int determinerCouche() throws SQLException{
        return VetementDAO.recupererCouche(this);
    }


    public Vetement menuAjouterVetementTxt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le type de vêtement : ");
        TypeVetement type = TypeVetement.get(sc.nextLine());

        System.out.println("Entrez la matière : ");
        Matiere matiere = Matiere.get(sc.nextLine());

        System.out.println("Entrez la coupe : ");
        CoupeVetement coupe = CoupeVetement.get(sc.nextLine());

        System.out.println("Entrez la couleur : ");
        String couleur = sc.nextLine();

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
        String signes[] = v.determinerSignes();

        v.setNiveau(niveau);
        v.setCouche(couche);
        v.setSignes(signes);
        
        //v.ajouterVetementDansListe();
        return true;
    }
    
    public boolean supprimerVetement() throws SQLException {
        // Attention à gérer les exceptions !!! 
        VetementDAO vASupprimer = new VetementDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez l'id du vêtement à supprimer");
        int id = sc.nextInt();
        if (vASupprimer.find(id) != null) {
            vASupprimer.delete(vASupprimer.find(id));
            return true;
        }
        else {
            return false;
        }
    }
    
    public Vetement trouverVetement(int id) throws SQLException{
        VetementDAO v = new VetementDAO();
        return v.find(id);  
    }

    public void ajouterVetementDansListe() throws SQLException {
        System.out.println("v :"+this.toString());
        System.out.println("niveau :"+(this.determinerNiveau()).toString());
        switch (this.determinerNiveau()) {
            case Haut:
                hauts.add(this);
                break;
            case Bas:
                bas.add(this);
                break;
            case Hautbas:
                hautsbas.add(this);
                break;
        }
    }
    
    public static void afficherHauts() throws SQLException {
        if (hauts!=null){
            for (Vetement v : hauts){
                    System.out.println(v.toString());
            }
        }else{
            System.out.println("Il n'y a pas de hauts");
        }
    }
    
    public static void afficherBas() throws SQLException {
        if (bas!=null){
            for (Vetement v : bas){
                    System.out.println(v.toString());
            }
        }else{
            System.out.println("Il n'y a pas de bas");
        }
    }
    
    public static void afficherHautsBas() throws SQLException {
        if (hautsbas!=null){
            for (Vetement v : hautsbas){
                    System.out.println(v.toString());
            }
        }else{
            System.out.println("Il n'y a pas de hauts-bas");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        s.append("\nVetement : \n\t idObjet : "+ idObjet +"\n\t Type : "+ type+"\n\t Coupe : "+coupe+"\n\t Signes : ");
   
	for(String si : signes){
	    s.append(si.toString()+", ");
	}
        s.append("\n\t Matiere : "+matiere+"\n\t Couleur : "+couleur+"\n\t Couche : "+couche+"\n\t Est sale : "+sale);
	return s.toString();
    }
}

