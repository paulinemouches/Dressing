package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.TypeEvenement;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import java.util.Date;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

public class Tenue {

    //private Sac sac;
    //private Chaussures chaussures;
    private Vetement[] vetements;

    public Tenue() {
    }

    public void setVetements(Vetement vetement) {
        this.vetements[this.vetements.length] = vetement;
    }

    public String determinerSaison() {
        Date date = new Date();
        int mois = date.getMonth();
        int jour = date.getDay();
        if ((mois >= 2 && jour >= 21) || (mois <= 8 && jour <= 21)) {
            return "Printemps/Ete";
        }
        else {
            return "Automne/Hiver";
        }
    }

    public Tenue creerTenue(TypeEvenement evenement) {
        Couleur couleurCorrespondante = new Couleur();
        Vetement v1 = new Vetement();
        Vetement v2 = new Vetement();
        Vetement v3 = new Vetement();
        String saison = determinerSaison();
        Random rand = new Random();
        int nbAleatoire = 2;
        if (evenement.equals(TypeEvenement.Sport)) {
            nbAleatoire = 1;
        } // si on fait du sport on ne met pas de robe ou combinaison
        int alea = rand.nextInt(nbAleatoire); // choisir dans hauts si alea =0, dans bas si alea =1, dans hautsbas sinon
        switch (alea) {
            case 0:
                v1 = chercherDansVetements(Vetement.getHauts(), saison, couleurCorrespondante, evenement);
                System.out.println("cas O");
                couleurCorrespondante = v1.getCouleur();
                v2 = chercherDansVetements(Vetement.getBas(), saison, couleurCorrespondante, evenement);
                break;
            case 1:
                v1 = chercherDansVetements(Vetement.getBas(), saison, couleurCorrespondante, evenement);
                System.out.println("cas 1");
                couleurCorrespondante = v1.getCouleur();
                v2 = chercherDansVetements(Vetement.getHauts(), saison, couleurCorrespondante, evenement);
                break;
            case 2:
                v1 = chercherDansVetements(Vetement.getHautsbas(), saison, couleurCorrespondante, evenement);
                System.out.println("cas 2");
                couleurCorrespondante = v1.getCouleur();
                break;

        }
      //  if ((saison.equals("Automne/Hiver")) && (evenement != TypeEvenement.Sport)) {
        //    chercherCouche2(couleurCorrespondante);
        // }
        this.setVetements(v1);
        if (v2 != null) {
            this.setVetements(v2);
        }
      //  chercherDansSac(couleurCorrespondante);
        //  chercherDansChaussures(saison, couleurCorrespondante);
        return this;
    }

    private Vetement chercherCouche2(Couleur couleur) {
        return null;
    }

    private Vetement chercherDansVetements(HashMap<Integer, Vetement> vetements, String saison, Couleur couleur, TypeEvenement evenement) {
        Vetement v = new Vetement();
        ArrayList<Vetement> t = new ArrayList<Vetement>(vetements.values());
        // initialisation du tableau de vetements (Hauts)
       /* t = (ArrayList<Vetement>)( vetements.values());*/
        // Réduction du tableau en fonction des différents critères
        t = chercherVetementEvenement(t, evenement);
        t = chercherVetementSaison(t, saison);
        t = chercherVetementCouleur(t, couleur);
        if (t.isEmpty()) {
            System.out.println("Votre dressing ne permet pas de créer de tenue");
        }
        else {
            int i = 0;
            int taille = t.size();
            Random rand = new Random();
            int alea = rand.nextInt(taille);
            for (Vetement ve : t) {
                if (i == alea) {
                    return ve;
                }
                else {
                    i++;
                }
            }
        }
        return null;
    }

    private Vetement chercherDansChaussures(String saison, Couleur couleur) {
        return null;
    }

    private Vetement chercherDansSac(Couleur couleur) {
        return null;
    }

    private ArrayList<Vetement> chercherVetementEvenement(ArrayList<Vetement> vetements, TypeEvenement ev) {
        switch (ev) {
            case Sport:
                Iterator<Vetement> it = vetements.iterator(); 
                while(it.hasNext()){
                    Vetement v = it.next();
                    if ((!v.getType().equals(TypeVetement.Jogging)) || (!v.determinerSaison().equals(TypeVetement.Teeshirt)) || (!v.determinerSaison().equals(TypeVetement.Pull))) {
                        it.remove();
                    }
                }
                break;
            case Soiree:
                Iterator<Vetement> it2 = vetements.iterator(); 
                while(it2.hasNext()){
                    Vetement v = it2.next();
                    // Pas de jogging ni pantacourt en soirée ! 
                    if ((v.getType().equals(TypeVetement.Jogging)) || (v.determinerSaison().equals(TypeVetement.Pantacourt))) {
                        it2.remove();
                    }
                    // Que certaines couleur en soirée !
                    if ((v.getCouleur().getCouleur() != (6)) || (v.getCouleur().getCouleur() != 14) || (v.getCouleur().getCouleur() != 24) || (v.getCouleur().getCouleur() != 25)) {
                         it2.remove();
                    }
                }
                break;
            case TousLesJours:
                Iterator<Vetement> it3 = vetements.iterator(); 
                while(it3.hasNext()){
                    Vetement v = it3.next();
                    if ((v.getType().equals(TypeVetement.Jogging))) {
                         it3.remove();
                    }
                }
                break;
        }
        return vetements;
    }

    private ArrayList<Vetement> chercherVetementSaison(ArrayList<Vetement> vetements, String saison) {
        Iterator<Vetement> it = vetements.iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if ((!v.determinerSaison().equals(saison)) || (!v.determinerSaison().equals("Toutes"))) {
                it.remove();
            }
        }
        return vetements;
    }

    private ArrayList<Vetement> chercherVetementCouleur(ArrayList<Vetement> vetements, Couleur couleurCorrespondante) {
        ArrayList<Couleur> couleursAssociees = new ArrayList<Couleur>();
        int i = 5;
                Iterator<Vetement> it = vetements.iterator();
        while ( it.hasNext()) {
            Vetement v = it.next();
            do {
                couleursAssociees = couleurCorrespondante.recupererValeurAssociee(i);
                i--;
            } while (!couleursAssociees.isEmpty() && i > 0);
            if (!couleursAssociees.contains(v.getCouleur())) {
                it.remove();
            }
        }
        return vetements;
    }

    public  void menuCreerTenue() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Evenement :");
        System.out.println("1: Tous Les Jours\t 2: Sport\t 3: Soiree\t");
        TypeEvenement evt = TypeEvenement.getfromInt(sc.nextInt());
        System.out.println(creerTenue(evt));
    }

    @Override
    public String toString() {
        return "Tenue{" + "vetements=" + vetements + '}';
    }

    
}
