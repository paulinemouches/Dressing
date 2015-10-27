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
    private Niveau niveau;
    private String[] signes;
    private CoupeVetement coupe;
    private TypeVetement type;
    private Matiere matiere;
    private int couche;
    private boolean sale;
    
    /* Constructeurs */
    public Vetement(int idV, CoupeVetement coupe, TypeVetement type, Matiere matiere){
        this.idV = idV;
        this.coupe = coupe;
        this.type = type;
        this.matiere = matiere;
        this.sale = false;
        this.niveau = determinerNiveau(type);
        this.couche= determinerCouche(type);
        // Fonction pour avoir le signe automatiquement en fonction du type de vêtement ! 
        // Fonction pour avoir la couche automatiquement en fonction du type ! 
    }
    
    public Vetement() {
    }
    
    /* Les méthodes */ 
    public int getIdV() {
        return idV;
    }

    public Niveau getNiveau() {
        return niveau;
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

    public boolean isSale() {
        return sale;
    }

    public void setIdV(int idV) {
        this.idV = idV;
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
    
     public Niveau determinerNiveau(TypeVetement typeVetement){
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
            case robe: resultat=Niveau.hautbas;
                break;
        }
        return resultat; 
    }
     
        

}
