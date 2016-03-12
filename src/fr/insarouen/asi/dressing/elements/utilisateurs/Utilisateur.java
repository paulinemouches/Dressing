package fr.insarouen.asi.dressing.elements.utilisateurs;

import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.CouleurCheveux;
import fr.insarouen.asi.dressing.dao.concret.UtilisateurDAO;
import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.Signe;

public class Utilisateur {

    private String nom;
    private String prenom;
    private int id;
    private int age;
    private int taille;
    private Couleur couleurPreferee;
    private CouleurCheveux couleurCheveux;
    private Signe signeUtilisateur;

    public Utilisateur(int id, String nom, String prenom, int age, int taille, Couleur couleurPreferee, CouleurCheveux couleurCheveux, Signe signeUtilisateur) {
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

    public void setCouleurPreferee(Couleur couleurPreferee) {
        this.couleurPreferee = couleurPreferee;
    }

    public void setCouleurCheveux(CouleurCheveux couleurCheveux) {
        this.couleurCheveux = couleurCheveux;
    }

    public void setSigneUtilisateur(Signe signeUtilisateur) {
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

    public Couleur getCouleurPreferee() {
        return couleurPreferee;
    }

    public CouleurCheveux getCouleurCheveux() {
        return couleurCheveux;
    }

    public Signe getSigneUtilisateur() {
        return signeUtilisateur;
    }

    /**
     *Permet d'ajouter un nouvel utilisateur
     * @return VRAI si l'utilisateur a bien été créé, FAUX sinon
     */ 
    
    public boolean ajouterUtilisateur(String nom,String prenom,int age,int taille,CouleurCheveux couleurC, Couleur couleurP, Signe signe) throws SQLException {
        /*   String nom =" popo";
        String prenom ="popo";
        int age =12;
        int taille = 123;
       CouleurCheveux couleurC = CouleurCheveux.getfromInt(1);
       Couleur couleurP = new Couleur(2);
        Signe signe = Signe.getfromInt(1);*/
        
        
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Entrez votre nom");
       // String nom = sc.nextLine();
        
        //System.out.println("Entrez votre prenom");
        //String prenom = sc.nextLine();
        
        //System.out.println("Entrez votre age");
        //int age = Integer.parseInt(sc.nextLine());
        
        //System.out.println("Entrez votre taille");
       // int taille = Integer.parseInt(sc.nextLine());
        
       // System.out.println("Entrez couleur de cheveux ");
        //System.out.println("1:  Blond\t 2: Brun\t 3: Roux\t 4:  Chatain");
        //CouleurCheveux  couleurC = CouleurCheveux.getfromInt(sc.nextInt()); // attention a bien respecter les contraintes de la base (=Brun ou Blond ou ...)
        
        /*System.out.println("Entrez votre couleur preferee");
                System.out.println( "1 - bleu\n" +
                            "2 - bleu clair\n" +
                            "3 - bleu marine\n" +
                            "4 - turquoise\n" +
                            "5 - gris clair\n" +
                            "6 - argenté\n" +
                            "7 - gris foncé\n" +
                            "8 - marron clair\n" +
                            "9 - marron foncé\n" +
                            "10 - corail\n" +
                            "11 - orange\n" +
                            "12 - bordeau\n" +
                            "13 - brique\n" +
                            "14 - rouge\n" +
                            "15 - rose pale\n" +
                            "16 - rose fushia\n" +
                            "17 - rose foncé\n" +
                            "18 - mauve\n" +
                            "19 - violet\n" +
                            "20 - prune\n" +
                            "21 - blanc\n" +
                            "22 - jaune moutarde \n" +
                            "23 - jaune\n" +
                            "24 - doré\n" +
                            "25 - noir\n" +
                            "26 - kaki\n" +
                            "27 - vert pale\n" +
                            "28 - vert\n" +
                            "29 - jean clair\n" +
                            "30 - jean marine\n" +
                            "31 - beige");
        Couleur couleurP = new Couleur(sc.nextInt());
        
       
        System.out.println("Entrez votre forme");
        System.out.println("1: Huit\t 2:  V\t 3: O\t 4:  H\t 5:  A\t 6 :  X");
        Signe signe = Signe.getfromInt(sc.nextInt());*/
        
        
        System.out.println("utilisateur créé");
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setAge(age);
        this.setTaille(taille);
        this.setCouleurCheveux(couleurC);
        this.setCouleurPreferee(couleurP);
        this.setSigneUtilisateur(signe);
        UtilisateurDAO nouvelUtilisateur = new UtilisateurDAO();
        nouvelUtilisateur.create(this);
        System.out.println("Votre id d'utilisateur est : "+this.getId());
        return true;
    }

    /**
     *Permet de supprimer un utilisateur
     * @return VRAI si l'utilisateur a bien été supprimé, FAUX sinon
     */
    
    
    
    public boolean supprimerUtilisateur(int id) throws SQLException {
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Id utilisateur a supprimer ?");
        //int id = sc.nextInt();
        UtilisateurDAO uASupprimer = new UtilisateurDAO();
        if (uASupprimer.find(id,UtilisateurDAO.obtenirIdDressing(id)) != null) {
            uASupprimer.delete(uASupprimer.find(id,UtilisateurDAO.obtenirIdDressing(id)));  
            return true;
        }
        else{
        return false;
        }
    }

    
    /**
     *Permet de récupérer un utilisateur en fonction de son id
     * @param id id de l'utilisateur qu'on veut récupérer
     * @return objet Utilisateur java
     */
    
    
        public Utilisateur trouverUtilisateur(int id) throws SQLException {
        UtilisateurDAO u = new UtilisateurDAO();
        return u.find(id,UtilisateurDAO.obtenirIdDressing(id));
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "nom=" + nom + ", prenom=" + prenom + ", id=" + id + ", age=" + age + ", taille=" + taille + ", couleurPreferee=" + couleurPreferee + ", couleurCheveux=" + couleurCheveux + ", signeUtilisateur=" + signeUtilisateur + '}';
    }
    
    
    
    }
    



