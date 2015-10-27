package fr.insarouen.asi.dressing.elements.objets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.Matiere;
import fr.insarouen.asi.dressing.elements.Niveau;
import fr.insarouen.asi.dressing.elements.Signe;
import fr.insarouen.asi.dressing.elements.CoupeVetement;
import fr.insarouen.asi.dressing.dao.concret.VetementDAO;

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
    // Il manque la couleur !! 
    
    /* Constructeurs */


    public Vetement(int idV,int idDressing, String couleur, CoupeVetement coupe,TypeVetement type, Matiere matiere, String[] signes, int couche, Niveau niveau){
        this.idObjet = idV;
        this.idDressing = idDressing;
        this.couleur= couleur;
        this.coupe = coupe;
        this.type = type;
        this.matiere = matiere;
        this.sale = false;
        this.niveau = niveau;
        this.couche= couche;
        this.signes = signes;
    }
    
    public Vetement() {
    }
    
    /* Les méthodes */ 
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
    
    
       
    
   

    public String[] determinerSignes(CoupeVetement coupeVetement){
        String resultat[]={};
        switch (coupeVetement){
            case cintre: 
                resultat[1]="H";
                resultat[2]="8";
                resultat[3]="V";
                resultat[4]="X";
                resultat[5]="A";
                break;
            case large: 
                resultat[1]="V";
                resultat[2]="X";
                resultat[3]="O";
                resultat[4]="8";
                break;
            case droit: 
                resultat[1]="H";
                resultat[2]="8";
                resultat[3]="O";
                resultat[4]="X";
                resultat[5]="A";
                break;
            case slim: 
                resultat[1]="H";
                resultat[2]="V";
                resultat[3]="X";
                resultat[4]="A";
                break;
            case evase: 
                resultat[1]="O";
                resultat[2]="8";
                break;
            case baggy: 
                resultat[1]="V";
                resultat[2]="8";
                resultat[3]="O";
                break;
            case longue: 
                resultat[1]="H";
                resultat[2]="8";
                resultat[3]="O";
                resultat[4]="X";
                resultat[5]="A";
                resultat[6]="V";
                break;
            case court: 
                resultat[1]="H";
                resultat[2]="8";
                resultat[3]="V";
                break;
        }
        return resultat;
    } 
    
    public int determinerCouche(TypeVetement typeVetement){
        int resultat=0;
        switch(typeVetement){
            case veste: resultat=2;
                break;
            case manteau: resultat=2;
                break;
            default : resultat=1;
        }
        return resultat; 
    }
    

    /*  public Niveau determinerNiveau(TypeVetement typeVetement){
       Niveau resultat = null;
        switch(typeVetement){
            case teeshirt : resultat= Niveau.haut;
                break;
            case chemisier: resultat=Niveau.haut;
                break;
            case pull: resultat=Niveau.haut;
                break;
            case veste: resultat=Niveau.haut;
                break;
            case manteau: resultat=Niveau.haut;
                break;
            case combinaison: resultat=Niveau.hautbas;
                break;
            case jogging: resultat=Niveau.bas;
                break;
            case jupe: resultat=Niveau.bas;
                break;
            case pantalon: resultat=Niveau.bas;
                break;
            case pantacourt: resultat=Niveau.bas;
                break;
            case shorts: resultat=Niveau.bas;
                break;
            case robe: resultat=Niveau.hautbas;*/

    
    public String determinerFils(TypeVetement typeVetement){
        String resultat;
        switch(typeVetement){
            case teeshirt: resultat="Haut";
                break;
            case chemisier: resultat="Haut";
                break;
            case pull: resultat="Haut";
                break;
            case veste: resultat="Haut";
                break;
            case manteau: resultat="Haut";
                break;
            case jogging: resultat="Pantalon";
                break;
            case pantalon: resultat="Pantalon";
                break;
            case pantacourt: resultat="Pantalon";
                break;
            default : resultat="Autre";
        }
        return resultat;
    }
    
     public Niveau determinerNiveau(String fils, TypeVetement typeVetement){
        Niveau resultat=null;
        if (fils.equals("Haut")){
            resultat=Niveau.haut;
        }else if (fils.equals("Pantalon")){
            resultat=Niveau.bas;
        }else{
                switch(typeVetement){
                case combinaison: resultat=Niveau.hautbas;
                    break;
                case jupe: resultat=Niveau.bas;
                    break;
                case shorts: resultat=Niveau.bas;
                    break;
                case robe: resultat=Niveau.hautbas;
                    break;
            }
        }
        
        return resultat; 
    }
     
     
     public Vetement menuAjouterVetementTxt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le type de vêtement : ");
        TypeVetement type = TypeVetement.get(sc.nextLine().toLowerCase());
        
        System.out.println("Entrez la matière : ");
        Matiere matiere =Matiere.get(sc.nextLine().toLowerCase());
        
        System.out.println("Entrez la coupe : ");
        CoupeVetement coupe = CoupeVetement.get(sc.nextLine().toLowerCase());
        
        System.out.println("Entrez la couleur : ");
        String couleur = sc.nextLine();
        
        String signes[]= determinerSignes(coupe);
        
        String fils = determinerFils(type);
       
        Niveau niveau = determinerNiveau(fils, type);
        
        int couche = determinerCouche(type); 
        
        Vetement v = new Vetement();
        v.setCouleur(couleur);
        v.setCouche(couche);
        v.setCoupe(coupe);
        v.setMatiere(matiere);
        v.setNiveau(niveau);
        v.setSale(false);
        v.setSignes(signes);
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
        return true;
     }   

}
