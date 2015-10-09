package fr.insarouen.asi.dressing.elements.utilisateurs;

import fr.insarouen.asi.dressing.Initialisation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import fr.insarouen.asi.dressing.elements.CouleurCheveux;
import fr.insarouen.asi.dressing.elements.Signe;
import fr.insarouen.asi.dressing.dao.concret.UtilisateurDAO;

public class Utilisateur {

    private String nom;
    private String prenom;
    private int id;
    private int age;
    private int taille;
    private String couleurPreferee;
    private String couleurCheveux;
    private String signeUtilisateur;

    public Utilisateur(/*int id,*/ String nom, String prenom, int age, int taille, String couleurPreferee, String couleurCheveux, String signeUtilisateur) {
       // this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.couleurPreferee = couleurPreferee;
        this.couleurCheveux = couleurCheveux;
        this.signeUtilisateur = signeUtilisateur;
    }
    
    public Utilisateur(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setCouleurPreferee(String couleurPreferee) {
        this.couleurPreferee = couleurPreferee;
    }

    public void setCouleurCheveux(String couleurCheveux) {
        this.couleurCheveux = couleurCheveux;
    }

    public void setSigneUtilisateur(String signeUtilisateur) {
        this.signeUtilisateur = signeUtilisateur;
    }

    
public Utilisateur ajouterUtilisateur() throws SQLException{
    Scanner sc = new Scanner(System.in);
    System.out.println("Entrez votre nom");
    String nom = sc.nextLine();
     Utilisateur u = new Utilisateur();
     System.out.println("creer user");
    u.setNom(nom);
    u.setPrenom("payuline");
    u.setAge(12);
    u.setTaille(132);
    u.setCouleurCheveux("Brun");
    u.setCouleurPreferee("Bleu");
    u.setSigneUtilisateur("X");
    UtilisateurDAO nouvelUtilisateur = new UtilisateurDAO();
        nouvelUtilisateur.create(u);
        return u;
}

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public int getTaille() {
        return taille;
    }

    public String getCouleurPreferee() {
        return couleurPreferee;
    }

    public String getCouleurCheveux() {
        return couleurCheveux;
    }

    public String getSigneUtilisateur() {
        return signeUtilisateur;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "nom=" + nom + ", prenom=" + prenom + ", id=" + id + ", age=" + age + ", taille=" + taille + ", couleurPreferee=" + couleurPreferee + ", couleurCheveux=" + couleurCheveux + ", signeUtilisateur=" + signeUtilisateur + '}';
    }
    
    
}
