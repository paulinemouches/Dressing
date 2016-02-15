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

    public GeneriqueTenue() {
    }

    public ArrayList<T> chercherCouleur(ArrayList<T> contenus, Couleur couleurCorrespondante, ArrayList<Couleur> couleurs) throws TenueImpossibleException {
        //Initialisation : 
        ArrayList<T> nvTab5 = new ArrayList<T>();
        ArrayList<T> nvTab4 = new ArrayList<T>();
        ArrayList<T> nvTab3 = new ArrayList<T>();
        ArrayList<T> nvTab2 = new ArrayList<T>();
        ArrayList<T> nvTab1 = new ArrayList<T>();
        Set<Integer> hs = new HashSet<Integer>();
        for (Couleur c : couleurs) {
            hs.add(c.getCouleur());
        }
        int nbCoulDiff = hs.size();
        for (T c : contenus) {
            int note = couleurCorrespondante.obtenirNote(c.getCouleur());
            System.out.println("nbCoulDiff =" +hs);
            if (nbCoulDiff < 3) {
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
            else {
                if ((note == 5) && ((hs.contains(c.getCouleur().getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                    nvTab5.add(c);
                if ((note == 4) && ((hs.contains(c.getCouleur().getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                   nvTab4.add(c);
                if ((note == 3) && ((hs.contains(c.getCouleur().getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                    nvTab3.add(c);
                if ((note == 2) &&  ((hs.contains(c.getCouleur().getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
                    nvTab2.add(c);
                if ((note == 1) &&  ((hs.contains(c.getCouleur().getCouleur())) ||(c.getCouleur().getCouleur()==25))) 
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
