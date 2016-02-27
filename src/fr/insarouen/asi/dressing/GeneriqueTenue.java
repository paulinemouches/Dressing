/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.objets.Contenu;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author julie
 */
public class GeneriqueTenue<T extends Contenu> {

    /**
    * Constructeur vide de Tenue
    */
    public GeneriqueTenue() {
    }
    
    /**
    * Méthode générique permettant de trouver les contenus qui correspondent à une couleur
    * 
    * @param contenus élément pouvant être de type "Vetement", "Sac" ou "Chaussures"
    * @param couleurCorrespondante couleur correspondante 
    * @return ArrayList Tableau de contenus ne contenant que des éléments s'accordant avec la couleur précisée
    */
    public ArrayList<T> chercherCouleur(ArrayList<T> contenus, Couleur couleurCorrespondante, ArrayList<Couleur> couleurs) throws TenueImpossibleException {

        //Initialisation : 
        ArrayList<T> nvTab5 = new ArrayList<T>();
        ArrayList<T> nvTab4 = new ArrayList<T>();
        ArrayList<T> nvTab3 = new ArrayList<T>();
        ArrayList<T> nvTab2 = new ArrayList<T>();
        ArrayList<T> nvTab1 = new ArrayList<T>();
        Set<Couleur> hs = new HashSet<Couleur>(); //un hashSet ne contient pas de doublons
        for (Couleur c : couleurs) {
            hs.add(c);//on copie les couleurs des vetements deja choisis dans le hasSet (si il y a 2 fois la même couleur dans le tableau de couleurs, cette couleur ne sera copiee que 1 fois dans la hashSet)
        }
        int nbCoulDiff = hs.size();//La taille du hashSet représente donc le nombre de couleurs differentes contenues dans la tenue en cours de construction
        for (T c : contenus) {
            int note = couleurCorrespondante.obtenirNote(c.getCouleur());
            if (nbCoulDiff < 3) {//Si le nombre de couleur différentes de la tenue est 1 ou 2, on prend donc un vetement de couleur choisies comme d'habitude
                switch (note) {
                    case 5:
                        nvTab5.add(c);
                        break;
                    case 4:
                        nvTab4.add(c);
                        break;
                    case 3:
                        nvTab3.add(c);
                        break;
                    case 2:
                        nvTab2.add(c);
                        break;
                    case 1:
                        nvTab1.add(c);
                        break;
                }
            }
            else {//si on a deja 3 couleurs differentes : on ajoute aux tableaux seulement les vetements dont la couleur est une des 3 deja presente, ou noir.
                if ((note == 5) && ((hs.contains(c.getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                    nvTab5.add(c);
                if ((note == 4) && ((hs.contains(c.getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                   nvTab4.add(c);
                if ((note == 3) && ((hs.contains(c.getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                    nvTab3.add(c);
                if ((note == 2) &&  ((hs.contains(c.getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                    nvTab2.add(c);
                if ((note == 1) &&  ((hs.contains(c.getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                    nvTab1.add(c);
            }
        }

        if (!nvTab5.isEmpty()) {
            return nvTab5;
        }else {
            if (!nvTab4.isEmpty()) {
                return nvTab4;
            } else {
                if (!nvTab3.isEmpty()) {
                    return nvTab3;
                } else {
                    if (!nvTab2.isEmpty()) {
                        return nvTab2;
                    } else {
                        if (nvTab1.isEmpty()) {
                            throw new TenueImpossibleException("Vous ne possédez pas de vêtements de couleur appropriée");
                        }
                        return nvTab1;
                    }
                }
            }
        }
    }
    
    /**
     * Permet de choisir aléatoirement un élément dans un tableau de contenus 
     * 
     * @param contenus élément pouvant être de type "Vetement", "Sac" ou "Chaussures"
     * @return T élément choisi aléatoirement
     * @throws TenueImpossibleException 
     */
    public T prendreAleatoirement(ArrayList<T> contenus) throws TenueImpossibleException {
        if (contenus.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de contenu de ce type.");
        }
        else {
            int i = 0;
            int taille = contenus.size();
            Random rand = new Random();
            int alea = rand.nextInt(taille);
            for (T c : contenus) {
                if (i == alea) {
                    return c;
                }
                else {
                    i++;
                }
            }
        }
        return null;
    }
}
