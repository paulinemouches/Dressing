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

    public Utilisateur(int id, String nom, String prenom, int age, int taille, String couleurPreferee, String couleurCheveux, String signeUtilisateur) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.couleurPreferee = couleurPreferee;
        this.couleurCheveux = couleurCheveux;
        this.signeUtilisateur = signeUtilisateur;
    }

    public Utilisateur() {
    }

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

    
    public boolean ajouterUtilisateur() throws SQLException {
        // Attention à gérer les exceptions !!!! 
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez votre nom");
        String nom = sc.nextLine();
        
        System.out.println("Entrez votre prenom");
        String prenom = sc.nextLine();
        
        System.out.println("Entrez votre age");
        int age = Integer.parseInt(sc.nextLine());
        
        System.out.println("Entrez votre taille");
        int taille = Integer.parseInt(sc.nextLine());
        
        System.out.println("Entrez couleur de cheveux ");
        String couleurC = sc.nextLine(); // attention a bien respecter les contraintes de la base (=Brun ou Blond ou ...)
        
        System.out.println("Entrez votre couleur preferee");
        String couleurP = sc.nextLine();
        
        System.out.println("Entrez votre forme");
        String signe = sc.nextLine();// attention a bien respecter les contraintes de la base (=X ou V ou A ou ...)
        
        Utilisateur u = new Utilisateur();
        System.out.println("creer user");
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setAge(age);
        u.setTaille(taille);
        u.setCouleurCheveux(couleurC);
        u.setCouleurPreferee(couleurP);
        u.setSigneUtilisateur(signe);
        UtilisateurDAO nouvelUtilisateur = new UtilisateurDAO();
        nouvelUtilisateur.create(u);
        return true;
    }

    public boolean supprimerUtilisateur() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Id utilisateur a supprimer ?");
        int id = sc.nextInt();
        UtilisateurDAO uASupprimer = new UtilisateurDAO();
        if (uASupprimer.find(id) != null) {
            uASupprimer.delete(uASupprimer.find(id));  
            return true;
        }
        else{
        return false;
        }
        }

    @Override
    public String toString() {
        return "Utilisateur{" + "nom=" + nom + ", prenom=" + prenom + ", id=" + id + ", age=" + age + ", taille=" + taille + ", couleurPreferee=" + couleurPreferee + ", couleurCheveux=" + couleurCheveux + ", signeUtilisateur=" + signeUtilisateur + '}';
    }
    
    
    
    }
    



