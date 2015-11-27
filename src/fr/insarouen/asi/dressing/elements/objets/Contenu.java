/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.elements.objets;

import fr.insarouen.asi.dressing.elements.Couleur;

/**
 *
 * @author julie
 */
public class Contenu {
    //Variable d'instance
    private Couleur couleur;
    private int idObjet;
    private int idDressing;
        

  //Constructeur par défaut
    public Contenu(){
  }

    public Contenu(Couleur couleur, int idObjet, int idDressing) {
        this.couleur = couleur;
        this.idObjet = idObjet;
        this.idDressing = idDressing;
    }
    
   

   // Setteurs et Getteurs 

    public Couleur getCouleur() {
        return couleur;
    }

    public int getIdObjet() {
        return idObjet;
    }

    public int getIdDressing() {
        return idDressing;
    }
    
    

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }
    
    
    
  
}

