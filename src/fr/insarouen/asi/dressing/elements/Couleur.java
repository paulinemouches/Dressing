/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.elements;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pauline
 */
public class Couleur {

    // tableau des couleurs : la première ligne et première colone contient les valeurs associées aux couleurs, le centre contient les notes (de 1 pour moche à 5 pour très beau) qui correspondent à l'association des 2 couleurs en abscisse et en ordonnée
    private int couleurs[][] = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31}, {1, 5, 1, 1, 1, 4, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5, 1, 1, 1, 3, 1, 5}, {2, 1, 5, 2, 1, 4, 3, 4, 2, 2, 1, 1, 1, 2, 1, 4, 1, 1, 1, 1, 2, 5, 1, 1, 2, 5, 1, 1, 1, 5, 5, 4}, {3, 1, 2, 5, 1, 5, 4, 4, 2, 2, 1, 1, 1, 2, 1, 4, 1, 1, 1, 1, 2, 5, 1, 1, 5, 5, 1, 1, 1, 5, 4, 5}, {4, 1, 1, 1, 5, 3, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 5, 1, 1, 1, 5, 3, 4}, {5, 4, 4, 5, 3, 5, 5, 2, 1, 1, 5, 3, 5, 5, 4, 5, 5, 5, 3, 4, 5, 5, 5, 4, 5, 5, 4, 5, 5, 5, 5, 3}, {6, 2, 3, 4, 1, 5, 5, 4, 1, 1, 1, 1, 5, 5, 1, 5, 3, 4, 2, 2, 4, 5, 1, 1, 1, 4, 1, 2, 1, 5, 5, 4}, {7, 2, 4, 4, 5, 2, 4, 5, 1, 1, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 5, 5, 5, 4, 4},
    {8, 2, 2, 2, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 4, 1, 4, 5, 1, 1, 2, 1, 1, 5}, {9, 2, 2, 2, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 4, 1, 4, 5, 1, 1, 4, 1, 1, 5}, {10, 1, 1, 1, 1, 5, 1, 3, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 5, 4, 5}, {11, 1, 1, 1, 1, 3, 1, 5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5, 1, 1, 1, 4, 5, 3}, {12, 1, 1, 1, 1, 5, 5, 5, 1, 1, 1, 1, 5, 1, 1, 4, 1, 2, 1, 1, 1, 3, 1, 1, 5, 5, 4, 1, 1, 4, 5, 5}, {13, 2, 2, 2, 1, 5, 5, 5, 1, 1, 1, 1, 1, 5, 1, 4, 1, 2, 1, 1, 1, 4, 1, 1, 5, 5, 4, 1, 1, 4, 5, 5}, {14, 1, 1, 1, 1, 4, 1, 5, 1, 1, 1, 1, 1, 1, 5, 2, 1, 1, 1, 1, 1, 4, 1, 1, 3, 5, 2, 1, 1, 5, 5, 4}, {15, 1, 4, 4, 1, 5, 5, 5, 1, 1, 1, 1, 4, 4, 2, 5, 2, 3, 1, 1, 2, 4, 1, 1, 3, 5, 4, 1, 1, 5, 5, 4},
    {16, 1, 1, 1, 1, 5, 3, 5, 1, 1, 1, 1, 1, 1, 1, 2, 5, 1, 1, 1, 1, 4, 1, 1, 3, 5, 1, 1, 1, 4, 5, 5}, {17, 1, 1, 1, 1, 5, 4, 5, 1, 1, 1, 1, 2, 2, 1, 3, 1, 5, 1, 1, 1, 5, 1, 1, 3, 5, 2, 1, 1, 5, 4, 5}, {18, 1, 1, 1, 1, 3, 2, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 4, 1, 1, 3, 5, 1, 1, 1, 4, 5, 5}, {19, 1, 1, 1, 1, 4, 2, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 4, 1, 1, 3, 5, 1, 1, 1, 4, 5, 5}, {20, 1, 2, 2, 1, 5, 4, 5, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 5, 4, 1, 1, 3, 5, 3, 1, 1, 4, 5, 5}, {21, 5, 5, 5, 4, 5, 5, 5, 5, 5, 5, 5, 3, 4, 4, 4, 4, 5, 4, 4, 4, 5, 4, 4, 3, 3, 5, 5, 4, 5, 4, 2}, {22, 1, 1, 1, 1, 5, 1, 5, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 5, 1, 2, 5, 3, 1, 1, 5, 5, 2}, {23, 1, 1, 1, 1, 4, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 5, 1, 5, 2, 1, 1, 5, 5, 2},
    {24, 1, 2, 5, 1, 5, 1, 5, 4, 4, 1, 1, 5, 5, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 1, 4, 4, 1, 1, 3, 5, 5}, {25, 5, 5, 5, 5, 5, 4, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 5, 4, 5, 5, 5, 4, 5, 4, 5}, {26, 1, 1, 1, 1, 4, 1, 4, 1, 1, 1, 1, 4, 4, 2, 4, 1, 2, 1, 1, 3, 5, 3, 2, 4, 5, 5, 1, 1, 5, 4, 5}, {27, 1, 1, 1, 1, 5, 2, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5, 1, 5, 1, 5, 4, 4}, {28, 1, 1, 1, 1, 5, 1, 5, 2, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 5, 5, 5, 4}, {29, 3, 5, 5, 5, 5, 5, 5, 1, 1, 5, 4, 4, 4, 5, 5, 4, 5, 4, 4, 4, 5, 5, 5, 3, 5, 5, 5, 5, 5, 1, 5}, {30, 1, 5, 4, 3, 5, 5, 4, 1, 1, 4, 5, 5, 5, 5, 5, 5, 4, 5, 5, 5, 4, 5, 5, 5, 4, 4, 4, 5, 1, 5, 5}, {31, 5, 4, 5, 4, 3, 4, 4, 5, 5, 5, 3, 5, 5, 4, 4, 5, 5, 5, 5, 5, 2, 2, 2, 5, 5, 5, 4, 4, 5, 5, 5}};
    private int couleur;
    // listes à 2 dimensions représentant les associations des couleurs exemple pour t1: (noir blanc;orange beige)
    // les couleurs noir et blanc sont reliés par la valeur 1 dans le tableau des couleurs, de meme pour les couleurs orange et beige.
    /*private List<List<Integer>> t1;
     private List<List<Integer>> t2;
     private List<List<Integer>> t3;
     private List<List<Integer>> t4;
     private List<List<Integer>> t5;*/

    public Couleur() {
        /*     t1 = new ArrayList<List<Integer>>();
         t2 = new ArrayList<List<Integer>>();
         t3 = new ArrayList<List<Integer>>();
         t4 = new ArrayList<List<Integer>>();
         t5 = new ArrayList<List<Integer>>();*/

    }

    public Couleur(int c) {
        this.couleur = c;
    }

    public int getCouleur() {
        return this.couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    /*  public void InitTableauxAssociations() {
     for (int i = 1; i < couleurs.length; i++) { // on parcours toutes les lignes du tableau
     for (int j = 1; j < couleurs.length; j++) {
     if (j <= i) {            //on parcours que la partie inferieure de la matrice (diagonale + sous la diagonale) car elle est symétrique
     // => temps de parcouirs réduit et redondance dans nos tableaux t1,t2,... évitée.      
     List<Integer> l = new ArrayList<Integer>(); // SOUS LISTE A METTRE DANS NOS LISTES
     l.add(couleurs[0][j]); // on ajoute a la premiere case de la sous liste la valeur corresdant a l'abscisse de la valeur parcourue 
     l.add(couleurs[i][0]);// on ajoute a la seconde case de la sous liste la valeur corresdant a l'ordonnée de la valeur parcourue
     switch (couleurs[i][j]) { // selon la valeur parcourue (=note de 1 à 5 corresponadant à si les couleurs sont bien assorties) on remplit le bon tableau
     case 1:
     t1.add(l);
     break;
     case 2:
     t2.add(l);
     break;
     case 3:
     t3.add(l);
     break;
     case 4:
     t4.add(l);
     break;
     case 5:
     t5.add(l);
     break;
     }
     }
     }
      
     }
     }

     public List<List<Integer>> obtenirTableau(int valeurDuTableau) {
     switch (valeurDuTableau) {
     case 1:
     return t1;
     case 2:
     return t2;
     case 3:
     return t3;
     case 4:
     return t4;
     case 5:
     return t5;
     default:
     return null;
     }
     }

     public int recupererValeurAssociee(int valeurDuTableau, int couleurDesiree) {
     InitTableauxAssociations();
     // on entre une couleur et on voudrait savoir qu'elle couleur est associée à celle ci par un notre de valeurDuTableau
     List<List<Integer>> t = new ArrayList<List<Integer>>();
     t = obtenirTableau(valeurDuTableau);
     for (int a = 0; a < t.size(); a++) { // on parcours le tableau correspondant à la valeurDuTableau entrée par l'utilisateur
     if (t.get(a).get(0) == couleurDesiree){// on regarde dans le sous tableau de la case 1: si cette valeur correspond à lacouleur désiréé alors
     return t.get(a).get(1);// on retourne la couleur associée : cas2 du sous tableau
     }
     else {
     if (t.get(a).get(1) == couleurDesiree) {// on regarde la case 2 du sous tableau : si cette valeur correspond à lacouleur désiréé alors
     return t.get(a).get(0);// on retourne la couleur associée : cas2 du sous tableau
     }
     }

     }
     return 0;// si la couleur désirée n'est pas dans le tableau on retourne 0
     }*/
    public ArrayList<Integer> recupererValeurAssociee(int note) {
        ArrayList<Integer> t = new ArrayList<Integer>();
        for (int i = 1; i < couleurs.length; i++) {
            if (couleurs[this.getCouleur()][i] == note) {
                t.add(i);
            }
        }
        return t;
    }

    public Integer obtenirNote(Couleur c) {
        return couleurs[this.getCouleur()][c.getCouleur()];
    }

    @Override//utile pour pouvoir utiliser le hashSet qui utilise la methode equals
    public boolean equals(Object o) {
        if (o instanceof Couleur) {
            return (this.getCouleur() == ((Couleur)o).getCouleur());
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.couleur;
        return hash;
    }

    @Override
    public String toString() {
        return "Couleur{" + "couleur=" + couleur + '}';
    }

}
