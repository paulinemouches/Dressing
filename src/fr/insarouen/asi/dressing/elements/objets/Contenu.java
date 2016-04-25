/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.elements.objets;

import fr.insarouen.asi.dressing.dao.concret.ChaussuresDAO;
import fr.insarouen.asi.dressing.dao.concret.SacDAO;
import fr.insarouen.asi.dressing.dao.concret.VetementDAO;
import fr.insarouen.asi.dressing.elements.Couleur;
import java.sql.SQLException;

/**
 *
 * @author julie
 */
public class Contenu {

    //Variable d'instance

    private Couleur couleur;
    private String image;
    private int idObjet;
    private int idDressing;

    //Constructeur par défaut
    public Contenu() {
    }

    public Contenu(Couleur couleur, String image, int idObjet, int idDressing) {
        this.couleur = couleur;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public static Contenu trouver(int id, int idDressing) throws SQLException {
        VetementDAO v = new VetementDAO();
        if (v.find(id, idDressing) == null) {
            SacDAO s = new SacDAO();
            if (s.find(id, idDressing) == null) {
                ChaussuresDAO c = new ChaussuresDAO();
                if (c.find(id, idDressing) == null) {
                    return null;
                } else {
                    return (Contenu) (c.find(id, idDressing));
                }
            } else {
                return (Contenu) (s.find(id, idDressing));
            }
        } else {
            return (Contenu) (v.find(id, idDressing));
        }
    }

    public static void supprimer(int id, int idDressing) throws SQLException {
        VetementDAO v = new VetementDAO();
        if (v.find(id, idDressing) == null) {
            SacDAO s = new SacDAO();
            if (s.find(id, idDressing) == null) {
                ChaussuresDAO c = new ChaussuresDAO();
                Chaussures ch = c.find(id, idDressing);
                ch.supprimerChaussures(idDressing,id);
            } else {
                Sac sac = s.find(id, idDressing);
                sac.supprimerSac(idDressing,id);
            }
        } else {
            Vetement vet = v.find(id, idDressing);
            vet.supprimerVetement(idDressing,id);
        }
    }

}
