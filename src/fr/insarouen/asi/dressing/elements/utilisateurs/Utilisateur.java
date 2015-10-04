package fr.insarouen.asi.dressing.elements.utilisateurs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import fr.insarouen.asi.dressing.elements.CouleurCheveux;
import fr.insarouen.asi.dressing.elements.Signe;

public class Utilisateur {

    private String nom;
    private String prenom;
    private int age;
    private int taille;
    private String couleurPreferee;
    CouleurCheveux couleurCheveux;
    Signe signeUtilisateur;

    public Utilisateur(String nom, String prenom, int age, int taille, String couleurPreferee, CouleurCheveux couleurCheveux, Signe signeUtilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.couleurPreferee = couleurPreferee;
        this.couleurCheveux = couleurCheveux;
        this.signeUtilisateur = signeUtilisateur;
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

    public void setCouleurCheveux(CouleurCheveux couleurCheveux) {
        this.couleurCheveux = couleurCheveux;
    }

    public void setSigneUtilisateur(Signe signeUtilisateur) {
        this.signeUtilisateur = signeUtilisateur;
    }

public Utilisateur ajouterUtilisateur(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Entrez votre nom");
            String nom = sc.nextLine();
            Statement st=("");
            
    
}
}
