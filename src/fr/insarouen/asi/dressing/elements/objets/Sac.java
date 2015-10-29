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

    private TypeSac typeS;
    private String couleur;
    private int idDressing;
    private int idObjet;

    public Sac(int idObjet, int idDressing, TypeSac typeS, String couleur) {
        this.typeS = typeS;
        this.couleur = couleur;
        this.idObjet = idObjet;
        this.idDressing = idDressing;
    }

    public Sac() {
    }

    public TypeSac getTypeS() {
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

    public void setTypeS(TypeSac typeS) {
        this.typeS = typeS;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }

    public Sac menuAjouterSacTxt() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le type de sac");
        TypeSac typeS = TypeSac.get(((String)sc.nextLine()).trim());

        System.out.println("Entrez la couleur du sac");
        String couleur = sc.nextLine();

        Sac s = new Sac();
        s.setTypeS(typeS);
        s.setCouleur(couleur);
        return s;
    }

    public boolean ajouterSac(int idDressing) throws SQLException {

        Sac s = menuAjouterSacTxt();
        System.out.println(s.toString());
        s.setIdDressing(idDressing);
        SacDAO nouveauSac = new SacDAO();
        nouveauSac.create(s);
        return true;
    }

    public boolean supprimerSac() throws SQLException {
        SacDAO sacASupprimer = new SacDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("entrez l'id du sac à supprimer");
        int id = sc.nextInt();
        if (sacASupprimer.find(id) != null) {
            sacASupprimer.delete(sacASupprimer.find(id));
            return true;
        }
        else {
            return false;
        }

    }
    
    public Sac trouverSac(int id) throws SQLException{
        SacDAO s = new SacDAO();
        System.out.println("trouver"+s.find(id).toString());
       return s.find(id);   
    }

    @Override
    public String toString() {
        return "Sac{" + "typeS=" +typeS + ", couleur=" + couleur + ", idDressing=" + idDressing + ", idObjet=" + idObjet + '}';
    }

}
