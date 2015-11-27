/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import fr.insarouen.asi.dressing.elements.TypeEvenement;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Contenu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author julie
 */
public class GeneriqueTenue<T extends Contenu> {

    public GeneriqueTenue() {
    }
    
    public ArrayList<T> chercherCouleur(ArrayList<T> contenus, Couleur couleurCorrespondante) {
        //Initialisation : 
        ArrayList<T> nvTab5 = new ArrayList<T>();
        ArrayList<T> nvTab4 = new ArrayList<T>();
        ArrayList<T> nvTab3 = new ArrayList<T>();
        ArrayList<T> nvTab2 = new ArrayList<T>();
        ArrayList<T> nvTab1 = new ArrayList<T>();
       
        for (T c: contenus){
            int note=couleurCorrespondante.obtenirNote(c.getCouleur());
            switch(note){
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
        
        if(!nvTab5.isEmpty()){
            return nvTab5;
        }else if(!nvTab4.isEmpty()){
            return nvTab4;
        }else if(!nvTab3.isEmpty()){
            return nvTab3;
        }else if(!nvTab2.isEmpty()){
            return nvTab2;
        }else {
            return nvTab1;
        }
    }
    
    public T prendreAleatoirement(ArrayList<T> contenus){
        if (contenus.isEmpty()) {
            System.out.println("Votre dressing ne permet pas de faire de tenue complete");
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
    
