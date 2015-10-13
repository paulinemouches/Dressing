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

public class Vetement {

    private int idV;
    private String niveau;
    private String[] signes;
    private String coupe;
    private String type;
    private String matiere;
    private int couche;
    private boolean sale;
    
    /* Constructeurs */
    public Vetement(int idV, String coupe, String type, String matiere){
        this.idV = idV;
        this.coupe = coupe;
        this.type = type;
        this.matiere = matiere;
        this.sale = false;
        this.niveau = determinerNiveau(type);
        // Fonction pour avoir le signe automatiquement en fonction du type de vêtement ! 
        // Fonction pour avoir la couche automatiquement en fonction du type ! 
    }
    
    public Vetement() {
    }
    
    /* Les méthodes */ 
    public int getIdV() {
        return idV;
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

    public boolean isSale() {
        return sale;
    }

    public void setIdV(int idV) {
        this.idV = idV;
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
    
    
    
    // Des fonctions à ajouter !! 
    public String determinerNiveau(String typeVetement){
        String resultat="";
        switch(typeVetement){
            case "TeeShirt": resultat="HAUT";
                break;
            case "Chemisier": resultat="HAUT";
                break;
            case "Pull": resultat="HAUT";
                break;
            case "Veste": resultat="HAUT";
                break;
            case "Manteau": resultat="HAUT";
                break;
            case "Combinaison": resultat="HAUTBAS";
                break;
            case "Jogging": resultat="BAS";
                break;
            case "Jupe": resultat="BAS";
                break;
            case "Pantalon": resultat="BAS";
                break;
            case "Pantacourt": resultat="BAS";
                break;
            case "Short": resultat="BAS";
                break;
            case "Robe": resultat="HAUTBAS";
                break;
        }
        return resultat; 
    }

}
