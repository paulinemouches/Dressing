package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import fr.insarouen.asi.dressing.elements.TypeEvenement;
import fr.insarouen.asi.dressing.elements.TypeSac;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;

public class Tenue {

    private Sac sac;
    private Chaussures chaussures;
    private ArrayList<Vetement> vetements= new ArrayList<Vetement>();

    public Tenue() {
    }

    // Setters 
    public void setVetements(Vetement vetement) {
        this.vetements.add(vetement);
    }

    public void setSac(Sac sac) {
        this.sac = sac;
    }

    public void setChaussures(Chaussures chaussures) {
        this.chaussures = chaussures;
    }
    
    
    
    
    
    // --------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------

    public String determinerSaison() {
        Calendar cal = Calendar.getInstance();
        if(((cal.get(Calendar.MONTH)>= Calendar.MARCH) && (cal.get(Calendar.DAY_OF_MONTH)>= 21)) && ((cal.get(Calendar.MONTH)<= Calendar.SEPTEMBER) && (cal.get(Calendar.DAY_OF_MONTH)<= 21)) ){
            return "Printemps/Ete";
        }
        else {
            return "Automne/Hiver";
        }
    }

// ------------------------------------------------------------------------------------------                                     
    public Tenue creerTenue(TypeEvenement evenement) {
        // Initialisation des variables :
        Couleur couleurCorrespondante = new Couleur();
        Vetement v1 = new Vetement();
        Vetement v2 = new Vetement();
        Vetement v3 = new Vetement();
        Sac s = new Sac();
        Chaussures c = new Chaussures();
        Random rand = new Random();
        
        // Récupération de la saison :
        String saison = determinerSaison();
        
        // On créer un nombre aléatoire : 
            // Initialisation de nb alea : 
                // De base : 
                    // Si le nbalea=0, on choisit un haut en premier
                    // Si le nbalea=1, on choisit un bas en premier
                    // Si le nbalea=2, on choisit un hautbas en premier
                // Si l'évènement est sport : 
                    // Si le nbalea=0, on choisit un haut en premier
                    // Si le nbalea=1, on choisit un bas en premier
                    // On ne peut pas mettre de hautsbas quand on fait du sport
        int nbAleatoire = 3;
        if (evenement.equals(TypeEvenement.Sport)) {
            nbAleatoire = 2;
        }
        int alea = rand.nextInt(nbAleatoire); 
        
        switch (alea) {
            case 0:
                System.out.println("cas 0");
                // On cherche tout d'abord un haut de couche 1 avec une couleur correspondante = 0 
                v1 = chercherDansVetements(chercherDansCouche(Vetement.getHauts(),1), saison, couleurCorrespondante, evenement);
                couleurCorrespondante = v1.getCouleur();
                // On cherche ensuite un bas avec la couleurCorrespondante égale à celle du 1er vêtement
                v2 = chercherDansVetements(Vetement.getBas(), saison, couleurCorrespondante, evenement);
                break;
            case 1:
                System.out.println("cas 1");
                // On cherche tout d'abord un bas avec une couleur correspondante = 0 
                v1 = chercherDansVetements(Vetement.getBas(), saison, couleurCorrespondante, evenement);
                couleurCorrespondante = v1.getCouleur();
                // On cherche ensuite un haut de couche 1 avec la couleurCorrespondante égale à celle du 1er vêtement
                v2 = chercherDansVetements(chercherDansCouche(Vetement.getHauts(),1), saison, couleurCorrespondante, evenement);
                break;
            case 2:
                System.out.println("cas 2");
                // On cherche un hautbas avec une Couleur correspondante=0
                v1 = chercherDansVetements(Vetement.getHautsbas(), saison, couleurCorrespondante, evenement);
                couleurCorrespondante = v1.getCouleur();
                break;

        }
        
        
        // Si notre saison est Automne/Hiver et que notre évènement n'est pas sport, 
        // on choisit un vêtement de couche 2
        if ((saison.equals("Automne/Hiver")) && (evenement != TypeEvenement.Sport)) {
            v3 = chercherDansVetements(chercherDansCouche(Vetement.getHauts(),2), saison, couleurCorrespondante, evenement);
        }
        
        // On attribut les vêtements trouvés à notre tenue : 
        this.setVetements(v1);
        if (v2 != null && v2.getIdV()!=0) {
            this.setVetements(v2);
        }
        if (v3 != null && v3.getIdV()!=0) {
            this.setVetements(v3);
        }
        
        
        s = chercherDansSac(Sac.getSacs(),couleurCorrespondante, evenement);
        if (s != null && s.getIdObjet()!=0) {
            this.setSac(s);
        }
        
        c = chercherDansChaussures(Chaussures.getChaussures(),saison, couleurCorrespondante, evenement);
        if (c != null && c.getIdObjet()!=0) {
            this.setChaussures(c);
        }
        
        return this;
    }
    
   
    
    // ------------------------------------------------------------------------------------------ 
                                            //VETEMENT//
    // ------------------------------------------------------------------------------------------ 
    
// ------------------------------------------------------------------------------------------                                     
    private HashMap<Integer, Vetement> chercherDansCouche(HashMap<Integer, Vetement> vetementsE,int numeroCouche) {
        // Initialisation d'un HashMap<Integer, Vetement>
        HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
        
        // Copie des vetements en entrée dans ce nouveau HashMap 
        vetements.putAll(vetementsE);
        
        // On enlève de ce HashMap, les vetements dont la couche ne correspond pas à celle attendue
        Iterator<Vetement> it = vetements.values().iterator(); 
        while(it.hasNext()){
            Vetement v = it.next();
            if (v.getCouche()!=numeroCouche) {
                it.remove();
            }
        }
        return vetements;
    }

    
// ------------------------------------------------------------------------------------------                                     

    private Vetement chercherDansVetements(HashMap<Integer, Vetement> vetements, String saison, Couleur couleurCorrespondante, TypeEvenement evenement) {
        Vetement v = new Vetement();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Vetement> t = new ArrayList<Vetement>(vetements.values());
        // Réduction du tableau en fonction des différents critères
        t = chercherVetementEvenement(t, evenement);
        t = chercherVetementSaison(t, saison);
        if (couleurCorrespondante.getCouleur()!=0){
            t = chercherVetementCouleur(t, couleurCorrespondante);
        }
        if (t.isEmpty()) {
            System.out.println("Votre dressing ne permet pas de créer de tenue complète");
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

// ------------------------------------------------------------------------------------------ 
    private ArrayList<Vetement> chercherVetementEvenement(ArrayList<Vetement> vetements, TypeEvenement ev) {
        // Différents cas en fonction de l'evenement : 
        switch (ev) {
            case Sport:
                Iterator<Vetement> it = vetements.iterator(); 
                while(it.hasNext()){
                    Vetement v = it.next();
                    // Que Jogging, Teeshirt, Pull pour le sport ! 
                    if ((!v.getType().equals(TypeVetement.Jogging)) && (!v.getType().equals(TypeVetement.Teeshirt)) && (!v.getType().equals(TypeVetement.Pull))) {
                        it.remove();
                    }
                }
                break;
            case Soiree:
                Iterator<Vetement> it2 = vetements.iterator(); 
                while(it2.hasNext()){
                    boolean estRetire = false;
                    Vetement v = it2.next();
                    // Pas de jogging ni pantacourt en soirée ! 
                    if ((v.getType().equals(TypeVetement.Jogging)) || (v.getType().equals(TypeVetement.Pantacourt))) {
                        it2.remove();
                        estRetire = true;
                    }
                    // Que certaines couleur en soirée !
                    if(!estRetire){
                        if ((v.getCouleur().getCouleur() != (6)) && (v.getCouleur().getCouleur() != 14) && (v.getCouleur().getCouleur() != 24) && (v.getCouleur().getCouleur() != 25)) {
                             it2.remove();
                        }
                    }
                }
                break;
            case TousLesJours:
                Iterator<Vetement> it3 = vetements.iterator(); 
                while(it3.hasNext()){
                    Vetement v = it3.next();
                    // Pas de Joggings tous les jours ! 
                    if ((v.getType().equals(TypeVetement.Jogging))) {
                         it3.remove();
                    }
                }
                break;
        }
        return vetements;
    }

// ------------------------------------------------------------------------------------------ 
    private ArrayList<Vetement> chercherVetementSaison(ArrayList<Vetement> vetements, String saison) {
        // On ne garde que les vêtements dont la saison correspond à celle actuelle ou à "Toutes"
        Iterator<Vetement> it = vetements.iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if ((!v.determinerSaison().equals(saison)) && (!v.determinerSaison().equals("Toutes"))) {
                it.remove();
            }
        }
        return vetements;
    }

// ------------------------------------------------------------------------------------------ 
    private ArrayList<Vetement> chercherVetementCouleur(ArrayList<Vetement> vetements, Couleur couleurCorrespondante) {
        //Initialisation : 
        ArrayList<Vetement> nvTab5 = new ArrayList<Vetement>();
        ArrayList<Vetement> nvTab4 = new ArrayList<Vetement>();
        ArrayList<Vetement> nvTab3 = new ArrayList<Vetement>();
        ArrayList<Vetement> nvTab2 = new ArrayList<Vetement>();
        ArrayList<Vetement> nvTab1 = new ArrayList<Vetement>();
       
        for (Vetement v: vetements){
            int note=couleurCorrespondante.obtenirNote(v.getCouleur());
            switch(note){
                case 5:
                    nvTab5.add(v);
                    break;
                case 4:
                    nvTab4.add(v);
                    break;
                case 3:
                    nvTab3.add(v);
                    break;
                case 2:
                    nvTab2.add(v);
                    break;
                case 1:
                    nvTab1.add(v);
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
    
    // ------------------------------------------------------------------------------------------ 
                                            //SAC//
    // ------------------------------------------------------------------------------------------ 
    
// ------------------------------------------------------------------------------------------                                     
    private Sac chercherDansSac(HashMap<Integer, Sac> sacs,Couleur couleurCorrespondante, TypeEvenement evenement) {
        Sac s = new Sac();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Sac> t = new ArrayList<Sac>(sacs.values());
        // Réduction du tableau en fonction des différents critères
        t = chercherSacEvenement(t, evenement);
        t = chercherSacCouleur(t, couleurCorrespondante);
        if (t.isEmpty()) {
            System.out.println("Votre dressing ne trouve pas de sac");
        }
        else {
            int i = 0;
            int taille = t.size();
            Random rand = new Random();
            int alea = rand.nextInt(taille);
            for (Sac sa : t) {
                if (i == alea) {
                    return sa;
                }
                else {
                    i++;
                }
            }
        }
        return null;
    }
    
// ------------------------------------------------------------------------------------------ 
    private ArrayList<Sac> chercherSacCouleur(ArrayList<Sac> sacs, Couleur couleurCorrespondante) {
        //Initialisation : 
        ArrayList<Sac> nvTab5 = new ArrayList<Sac>();
        ArrayList<Sac> nvTab4 = new ArrayList<Sac>();
        ArrayList<Sac> nvTab3 = new ArrayList<Sac>();
        ArrayList<Sac> nvTab2 = new ArrayList<Sac>();
        ArrayList<Sac> nvTab1 = new ArrayList<Sac>();
       
        for (Sac s: sacs){
            int note=couleurCorrespondante.obtenirNote(s.getCouleur());
            switch(note){
                case 5:
                    nvTab5.add(s);
                    break;
                case 4:
                    nvTab4.add(s);
                    break;
                case 3:
                    nvTab3.add(s);
                    break;
                case 2:
                    nvTab2.add(s);
                    break;
                case 1:
                    nvTab1.add(s);
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
    
// ------------------------------------------------------------------------------------------ 
    private ArrayList<Sac> chercherSacEvenement(ArrayList<Sac> sacs, TypeEvenement ev) {
        // Différents cas en fonction de l'evenement : 
        switch (ev) {
            case Sport:
                Iterator<Sac> it = sacs.iterator(); 
                while(it.hasNext()){
                    Sac s = it.next();
                    // Que Jogging, Teeshirt, Pull pour le sport ! 
                    if ((!s.getTypeS().equals(TypeSac.Sacados))) {
                        it.remove();
                    }
                }
                break;
            case Soiree:
                Iterator<Sac> it2 = sacs.iterator(); 
                while(it2.hasNext()){
                    Sac s = it2.next();
                    // Pas de jogging ni pantacourt en soirée ! 
                    if ((s.getTypeS().equals(TypeSac.Sacados))) {
                        it2.remove();
                    }
                }
                break;
        }
        return sacs;
    }
    
    
    // ------------------------------------------------------------------------------------------ 
                                            //CHAUSSURES//
    // ------------------------------------------------------------------------------------------ 
 
    
// ------------------------------------------------------------------------------------------                                     

    private Chaussures chercherDansChaussures(HashMap<Integer, Chaussures> chaussures, String saison, Couleur couleurCorrespondante, TypeEvenement evenement) {
        Chaussures c = new Chaussures();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Chaussures> t = new ArrayList<Chaussures>(chaussures.values());
        // Réduction du tableau en fonction des différents critères
        t = chercherChaussuresEvenement(t, evenement);
        t = chercherChaussuresSaison(t, saison);
        if (couleurCorrespondante.getCouleur()!=0){
            t = chercherChaussuresCouleur(t, couleurCorrespondante);
        }
        if (t.isEmpty()) {
            System.out.println("Votre dressing ne permet pas d'avoir des chaussures");
        }
        else {
            int i = 0;
            int taille = t.size();
            Random rand = new Random();
            int alea = rand.nextInt(taille);
            for (Chaussures ch : t) {
                if (i == alea) {
                    return ch;
                }
                else {
                    i++;
                }
            }
        }
        return null;
    }

// ------------------------------------------------------------------------------------------ 
    private ArrayList<Chaussures> chercherChaussuresEvenement(ArrayList<Chaussures> chaussures, TypeEvenement ev) {
        // Différents cas en fonction de l'evenement : 
        switch (ev) {
            case Sport:
                Iterator<Chaussures> it = chaussures.iterator(); 
                while(it.hasNext()){
                    Chaussures c = it.next();
                    // Que des Baskets ! 
                    if ((!c.getTypeC().equals(TypeChaussures.Baskets))) {
                        it.remove();
                    }
                }
                break;
            case Soiree:
                Iterator<Chaussures> it2 = chaussures.iterator(); 
                while(it2.hasNext()){
                    Chaussures c = it2.next();
                    // Pas de baskets ne de bottes plates en soirée ! 
                    if ((c.getTypeC().equals(TypeChaussures.Baskets)) || (c.getTypeC().equals(TypeChaussures.Bottesplates))) {
                        it2.remove();
                    }
                }
                break;
            case TousLesJours:
                Iterator<Chaussures> it3 = chaussures.iterator(); 
                while(it3.hasNext()){
                    Chaussures c = it3.next();
                    // Pas d'escarpins et pas de baskets tous les jours ! 
                    if ((c.getTypeC().equals(TypeChaussures.Baskets)) || (c.getTypeC().equals(TypeChaussures.Escarpins))) {
                         it3.remove();
                    }
                }
                break;
        }
        return chaussures;
    }

// ------------------------------------------------------------------------------------------ 
    private ArrayList<Chaussures> chercherChaussuresSaison(ArrayList<Chaussures> chaussures, String saison) {
        // On ne garde que les chaussures correspondantes à la saison
        Iterator<Chaussures> it = chaussures.iterator();
        while (it.hasNext()) {
            Chaussures c = it.next();
            if ((saison.equals("Automne/Hiver")) && ((c.getTypeC().equals(TypeChaussures.Sandales)) || (c.getTypeC().equals(TypeChaussures.Ballerines)))) {
                it.remove();
            }
            if ((saison.equals("Printemps/Ete")) && ((c.getTypeC().equals(TypeChaussures.Bottesplates)) || (c.getTypeC().equals(TypeChaussures.Bottesatalons)))) {
                it.remove();
            }
        }
        return chaussures;
    }

// ------------------------------------------------------------------------------------------ 
    private ArrayList<Chaussures> chercherChaussuresCouleur(ArrayList<Chaussures> chaussures, Couleur couleurCorrespondante) {
        //Initialisation : 
        ArrayList<Chaussures> nvTab5 = new ArrayList<Chaussures>();
        ArrayList<Chaussures> nvTab4 = new ArrayList<Chaussures>();
        ArrayList<Chaussures> nvTab3 = new ArrayList<Chaussures>();
        ArrayList<Chaussures> nvTab2 = new ArrayList<Chaussures>();
        ArrayList<Chaussures> nvTab1 = new ArrayList<Chaussures>();
       
        for (Chaussures c: chaussures){
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
    
// ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------ 
    

    public  void menuCreerTenue() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Evenement :");
        System.out.println("1: Tous Les Jours\t 2: Sport\t 3: Soiree\t");
        TypeEvenement evt = TypeEvenement.getfromInt(sc.nextInt());
        System.out.println(creerTenue(evt).toString());
    }

    @Override
    public String toString() {
        return "Tenue{" + "sac=" + sac + ", chaussures=" + chaussures + ", vetements=" + vetements + '}';
    }

    

    

    
}
