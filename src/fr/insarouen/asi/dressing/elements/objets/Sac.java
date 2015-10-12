package fr.insarouen.asi.dressing.elements.objets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import fr.insarouen.asi.dressing.elements.TypeSac;
import fr.insarouen.asi.dressing.dao.concret.SacDAO;

public class Sac {

    private String typeS;
    private String couleur;
    private int idDressing;
    private int idObjet;

    public Sac(int idObjet, int idDressing,String typeS, String couleur) {
        this.typeS = typeS;
        this.couleur = couleur;
        this.idObjet=idObjet;
        this.idDressing =idDressing;
    }

    public Sac() {
    }

    public String getTypeS() {
        return typeS;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getIdDressing() {
        return idDressing;
    }

    public int getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }

    
    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }
    
 public boolean ajouterSac(int idDressing){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Entrez le type de sac");
        String typeS = sc.nextLine();
        
        System.out.println("Entrez la couleur du sac");
        String couleur = sc.nextLine();
        
        Sac s = new Sac();
        System.out.println("creer sac");
        s.setIdDressing(idDressing);
        s.setTypeS(typeS);
        s.setCouleur(couleur);
        SacDAO nouveauSac = new SacDAO();
        nouveauSac.create(s);
        return true;
 }

}
