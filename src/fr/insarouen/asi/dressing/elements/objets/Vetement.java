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
    public Vetement(int idV, String coupe, String type, String matiere, int couche) {
        this.idV = idV;
        this.coupe = coupe;
        this.type = type;
        this.matiere = matiere;
        this.couche = couche;
        this.sale = false;
        // Comment fait-on pour signes ?? 
        // Et pour Niveau ? 
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
    
    // Des fonctions à ajouter !! 

}
