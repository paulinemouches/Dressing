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


    @Override//utile pour pouvoir utiliser le hashSet qui utilise la methode equals
    public boolean equals(Object o) {
        if (o instanceof Couleur) {
            return (this.getCouleur() == ((Couleur)o).getCouleur());
        }
        return false; // si la couleur désirée n'est pas dans le tableau on retourne 0
    }

    /**
     *Permet de récupérer un tableau d'elements associés à un autre élément par une certaine note
     * @param note note avec laquelle on veut que les éléments soient associés
     * @return Tableau d'éléments correctement associés au premier élément
     */
    
       public ArrayList<Integer> recupererValeurAssociee(int note) {
          ArrayList<Integer> t = new ArrayList<Integer>();
           for(int i =1; i<couleurs.length; i++){
               if (couleurs[this.getCouleur()][i]==note){
                        t.add(i);
               }
           }
           return  t; 
       }

    /**
     *Permet d'obtenir la note reliant deux couleurs
     * @param c Couleur avec laquel on veut obtenir la note
     * @return note liant les deux couleurs
     */
    
       public Integer obtenirNote(Couleur c){
           return couleurs[this.getCouleur()][c.getCouleur()];
       }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.couleur;
        return hash;
    }

    @Override
    public String toString() {
        switch(this.getCouleur()){
            case 1: return "Bleu"; 
            case 2: return "Bleu clair"; 
            case 3: return "Bleu marine"; 
            case 4: return "Turquoise"; 
            case 5: return "Gris clair"; 
            case 6: return "Argenté"; 
            case 7: return "gris foncé"; 
            case 8: return "Marron Clair"; 
            case 9: return "Marron Foncé"; 
            case 10: return "Corail"; 
            case 11: return "Orange"; 
            case 12: return "Bordeau"; 
            case 13: return "Brique"; 
            case 14: return "Rouge"; 
            case 15: return "rose pale"; 
            case 16: return "Rose fushia"; 
            case 17: return "Fose foncé"; 
            case 18: return "Mauve"; 
            case 19: return "Violet"; 
            case 20: return "Prune"; 
            case 21: return "Blanc"; 
            case 22: return "Jaune moutarde"; 
            case 23: return "Jaune"; 
            case 24: return "Doré"; 
            case 25: return "Noir"; 
            case 26: return "Kaki"; 
            case 27: return "Vert pale"; 
            case 28: return "Vert"; 
            case 29: return "Jean clair"; 
            case 30: return "Jean marine"; 
            case 31: return "Beige"; 
            default : return "";
        }
    }
}
