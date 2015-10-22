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
    private String niveau;
    private String[] signes;
    private String coupe;
    private String type;
    private String matiere;
    private String fils;
    private String couleur;
    private int couche;
    private boolean sale;
    // Il manque la couleur !! 
    
    /* Constructeurs */
    public Vetement(int idV,int idDressing, String couleur, String coupe, String type, String matiere, String[] signes, int couche, String niveau){
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

    public String getCouleur() {
        return couleur;
    }
    

    public int getIdDressing() {
        return idDressing;
    }
    

    public String getNiveau() {
        return niveau;
    }

    public String[] getSignes() {
        return signes;
    }

    public String getCoupe() {
        return coupe;
    }

    public String getType() {
        return type;
    }

    public String getMatiere() {
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

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setSignes(String[] signes) {
        this.signes = signes;
    }

    public void setCoupe(String coupe) {
        this.coupe = coupe;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMatiere(String matiere) {
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
    
    
   
    public String[] determinerSignes(String coupeVetement){
        String resultat[]=new String[6];
        switch (coupeVetement){
            case "Cintre": 
                resultat[0]="H";
                resultat[1]="8";
                resultat[2]="V";
                resultat[3]="X";
                resultat[4]="A";
                break;
            case "HautDroit": 
                resultat[0]="H";
                resultat[1]="O";
                resultat[2]="A";
                break;
            case "Large": 
                resultat[0]="V";
                resultat[1]="X";
                resultat[2]="O";
                resultat[3]="8";
                break;
            case "PantalonDroit": 
                resultat[0]="H";
                resultat[1]="8";
                resultat[2]="O";
                resultat[3]="X";
                resultat[4]="A";
                break;
            case "Slim": 
                resultat[0]="H";
                resultat[1]="V";
                resultat[2]="X";
                resultat[3]="A";
                break;
            case "Evase": 
                resultat[0]="O";
                resultat[1]="8";
                break;
            case "Baggy": 
                resultat[0]="V";
                resultat[1]="8";
                resultat[1]="O";
                break;
            case "Long": 
                resultat[0]="H";
                resultat[1]="8";
                resultat[2]="O";
                resultat[3]="X";
                resultat[4]="A";
                resultat[5]="V";
                break;
            case "Court": 
                resultat[1]="H";
                resultat[2]="8";
                resultat[3]="V";
                break;
        }
        return resultat;
    } 
    
    public int determinerCouche(String typeVetement){
        int resultat=0;
        switch(typeVetement){
            case "Veste": resultat=2;
                break;
            case "Manteau": resultat=2;
                break;
            default : resultat=1;
        }
        return resultat; 
    }
    
    public String determinerFils(String typeVetement){
        String resultat;
        switch(typeVetement){
            case "TeeShirt": resultat="Haut";
                break;
            case "Chemisier": resultat="Haut";
                break;
            case "Pull": resultat="Haut";
                break;
            case "Veste": resultat="Haut";
                break;
            case "Manteau": resultat="Haut";
                break;
            case "Jogging": resultat="Pantalon";
                break;
            case "Pantalon": resultat="Pantalon";
                break;
            case "Pantacourt": resultat="Pantalon";
                break;
            default : resultat="Autre";
        }
        return resultat;
    }
    
     public String determinerNiveau(String fils, String typeVetement){
        String resultat="";
        if (fils.equals("Haut")){
            resultat="Haut";
        }else if (fils.equals("Pantalon")){
            resultat="Bas";
        }else{
                switch(typeVetement){
                case "Combinaison": resultat="Haut-Bas";
                    break;
                case "Jupe": resultat="Bas";
                    break;
                case "Short": resultat="Bas";
                    break;
                case "Robe": resultat="Haut-Bas";
                    break;
            }
        }
        
        return resultat; 
    }
     
     
     public Vetement menuAjouterVetementTxt() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le type de vêtement : ");
        String type = sc.nextLine();
        
        System.out.println("Entrez la matière : ");
        String matiere = sc.nextLine();
        
        System.out.println("Entrez la coupe : ");
        String coupe = sc.nextLine();
        
        System.out.println("Entrez la couleur : ");
        String couleur = sc.nextLine();
        
        String signes[]= determinerSignes(coupe);
        
        String fils = determinerFils(type);
       
        String niveau = determinerNiveau(fils, type);
        
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
