package fr.insarouen.asi.dressing.elements.objets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import fr.insarouen.asi.dressing.dao.concret.ChaussuresDAO;

public class Chaussures {

   TypeChaussures typeC;
    private String couleur;
    private int idDressing;
    private int idObjet;

    public Chaussures() {
    }

    public Chaussures(int idObjet,int idDressing,  TypeChaussures typeC, String couleur) {
        this.typeC = typeC;
        this.couleur = couleur;
        this.idDressing = idDressing;
        this.idObjet = idObjet;
    }

    public TypeChaussures getTypeC() {
        return typeC;
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

    public void setTypeC(TypeChaussures typeC) {
        this.typeC = typeC;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }
    
    
        public Chaussures menuAjouterChaussuresTxt() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le type de chaussures");
        TypeChaussures typeC = TypeChaussures.get(sc.nextLine());

        System.out.println("Entrez la couleur des chaussures");
        String couleur = sc.nextLine();

        Chaussures c = new Chaussures();
        c.setTypeC(typeC);
        c.setCouleur(couleur);
        return c;
    }

    public boolean ajouterChaussures(int idDressing) throws SQLException {

        Chaussures c = menuAjouterChaussuresTxt();
        c.setIdDressing(idDressing);
        ChaussuresDAO nouvellesChaussures = new ChaussuresDAO();
        nouvellesChaussures.create(c);
        return true;
    }

    public boolean supprimerChaussures() throws SQLException {
        ChaussuresDAO cASupprimer = new ChaussuresDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("entrez l'id des chaussures à supprimer");
        int id = sc.nextInt();
        if (cASupprimer.find(id) != null) {
            cASupprimer.delete(cASupprimer.find(id));
            return true;
        }
        else {
            return false;
        }

    }

    public Chaussures trouverChaussures(int id) throws SQLException{
                ChaussuresDAO c = new ChaussuresDAO();
       return c.find(id);  
    }
    
    @Override
    public String toString() {
        return "Chaussures{" + "typeC=" + typeC.name() + ", couleur=" + couleur + ", idDressing=" + idDressing + ", idObjet=" + idObjet + '}';
    }

}
