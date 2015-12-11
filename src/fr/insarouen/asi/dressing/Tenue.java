package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.Signe;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import fr.insarouen.asi.dressing.elements.TypeEvenement;
import fr.insarouen.asi.dressing.elements.TypeSac;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;

public class Tenue {

    private Sac sac;
    private Chaussures chaussures;
    private ArrayList<Vetement> vetements = new ArrayList<Vetement>();

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
    /**
     * Permet de determiner la saison courante.
     */
    public String determinerSaison() {
        Calendar cal = Calendar.getInstance();
        if (((cal.get(Calendar.MONTH) >= Calendar.MARCH) && (cal.get(Calendar.DAY_OF_MONTH) >= 21)) && ((cal.get(Calendar.MONTH) <= Calendar.SEPTEMBER) && (cal.get(Calendar.DAY_OF_MONTH) <= 21))) {
            return "Printemps/Ete";
        }
        else {
            return "Automne/Hiver";
        }
    }

// ------------------------------------------------------------------------------------------                    
    /**
     * Permet de creer une tenue en fonction du type de tenue choisi par
     * l'utilisateur.
     */
    public Tenue creerTenue(int typeTenue, int id, TypeEvenement evenement) throws SQLException, TenueImpossibleException {
        Tenue t = new Tenue();
        switch (typeTenue) {
            case 1:
                t = creerTenueNormaleOuSigne(typeTenue, id, evenement);
                break;
            case 2:
                t = creerTenueNormaleOuSigne(typeTenue, id, evenement);
                break;
            case 3:
                Scanner sc = new Scanner(System.in);
                Vetement.afficherHauts();
                Vetement.afficherBas();
                Vetement.afficherHautsBas();
                System.out.println("Entrez l'id du vetement que vous voulez  (0 si vous ne désirez pas de vetement particulier):");
                int idVetement = sc.nextInt();
                System.out.println("Entrez l'id du sac que vous voulez  (0 si vous ne désirez pas de sac particulier):");
                Sac.afficherSacs();
                int idSac = sc.nextInt();
                System.out.println("Entrez l'id des chaussures que vous voulez  (0 si vous ne désirez pas de chaussures  particulières):");
                Chaussures.afficherChaussures();
                int idChaussures = sc.nextInt();
                t = creerTenueContenuParticulier(idVetement, idSac, idChaussures, typeTenue, id, evenement);
                break;
        }
        return t;
    }

    /**
     * Permet de creer une tenue si le type de tenue choisi par l'utilisateur
     * est "normal" ou "accordee au signe".
     */
    public Tenue creerTenueNormaleOuSigne(int typeTenue, int id, TypeEvenement evenement) throws SQLException, TenueImpossibleException {
        //
        Couleur couleurCorrespondante = new Couleur(0);
        String saison = determinerSaison();

        couleurCorrespondante = recupererTableauVetementsCouche1(typeTenue, id, evenement, couleurCorrespondante, saison);
        recupererTableauVetementsCouche2(typeTenue, id, evenement, couleurCorrespondante, saison);
        recupererSac(typeTenue, id, evenement, couleurCorrespondante, saison);
        recupererChaussures(typeTenue, id, evenement, couleurCorrespondante, saison);
        return this;

    }

    /**
     * Permet de creer une tenue si le type de tenue choisi par l'utilisateur
     * est "contenu particulier"
     */
    public Tenue creerTenueContenuParticulier(int idVetement, int idSac, int idChaussures, int typeTenue, int id, TypeEvenement evenement) throws SQLException, TenueImpossibleException {
        // Initialisation des variables :
        Couleur couleurCorrespondante = new Couleur(0);
        Vetement v1 = new Vetement();
        Vetement v2 = new Vetement();
        Vetement v3 = new Vetement();
        Sac s = new Sac();
        Chaussures c = new Chaussures();
        Random rand = new Random();

        String saison = determinerSaison();

        // Si l'idVetement est non nul, ça signifie que l'utilisateur à choisi un vetement particulier
        if (idVetement != 0) {
            Vetement v = new Vetement();
            // On cherche ce  vetement particulier à partir de l'idVetement entré par l'utilisateur
            v = v.trouverVetement(idVetement);
            couleurCorrespondante = v.getCouleur();
            // Si le vetement choisi est une couche 2 (Veste ou Manteau)
            if (v.getCouche() == 2) {
                // On place ce vetement dans v3 puis on l'ajoute à la tenue
                v3 = v;
                this.setVetements(v3);
                // On va chercher les vetements de couche 1 pour completer la tenue
                recupererTableauVetementsCouche1(typeTenue, id, evenement, couleurCorrespondante, saison);
            }
            else {
                // Si le vetement est de couche 1 
                v1 = v;
                switch (v1.getNiveau()) {
                    case Bas:
                        // Si le vetement choisi est un bas on va chercher un haut
                        System.out.println("cas bas");
                        // On cherche un bas car on a deja un haut
                        // On cherche ensuite un bas avec la couleurCorrespondante égale à celle du 1er vêtement
                        v2 = chercherDansVetements(typeTenue, id, Vetement.getHauts(), saison, couleurCorrespondante, evenement);
                        break;
                    case Haut:
                        // Si le vetement choisi est unhaut on va chercher un bas
                        System.out.println("cas haut");
                        // On cherche ensuite un haut avec la couleurCorrespondante égale à celle du 1er vêtement
                        // On cherche ensuite un haut de couche 1 avec la couleurCorrespondante égale à celle du 1er vêtement
                        v2 = chercherDansVetements(typeTenue, id, chercherDansCouche(Vetement.getBas(), 1), saison, couleurCorrespondante, evenement);
                        break;
                    case Hautbas:
                        // Si le vetement choisi est unhautbas, on n'a pas besoin d'autre vetement de couche 1
                        System.out.println("cas hautbas");
                        break;
                }
                // Si le vetement choisi est une couche 1 , on va chercher une couche 2
                // recupererTableauVetementsCouche2 renvoie une valeur seulement si on est en Automne/Hiver et si on ne fait pas de sport.
                recupererTableauVetementsCouche2(typeTenue, id, evenement, couleurCorrespondante, saison);
                this.setVetements(v1);
                if (v2 != null && v2.getIdObjet() != 0) {
                    this.setVetements(v2);
                }
            }
        }

        // Si l'idSac est non nul, ça signifie que l'utilisateur à choisi un sac particulier
        if (idSac != 0) {
            s = s.trouverSac(idSac);
            this.setSac(s);
            // La couleurCorrespondante devient celle du sac choisi.
            couleurCorrespondante = s.getCouleur();
        }

        // Si l'idChaussures est non nul, ça signifie que l'utilisateur à choisi des chaussures  particulières
        if (idChaussures != 0) {
            c = c.trouverChaussures(idChaussures);
            this.setChaussures(c);
            // La couleurCorrespondante devient celle des chaussures choisies.
            couleurCorrespondante = c.getCouleur();
        }

        if (idVetement == 0) {
            // Si l'idVetement vaut 0, l'utilisateur n'a pas choisi de vetement particulier, on cherche des vetements comme d'habitude
            // vetement choisis en fonction de la couleur correspondante qui sera donc celle des chaussures ou du sac choisi
            couleurCorrespondante = recupererTableauVetementsCouche1(typeTenue, id, evenement, couleurCorrespondante, saison);
            recupererTableauVetementsCouche2(typeTenue, id, evenement, couleurCorrespondante, saison);
        }
        if (idSac == 0) {
            // Si l'idSac vaut 0,  l'utilisateur n'a pas choisi de sac particulier, on cherche un sac comme d'habitude
            // sac choisi en fonction de la couleur correspondante qui sera donc celle des chaussures ou du vetement choisi
            recupererSac(typeTenue, id, evenement, couleurCorrespondante, saison);
        }
        if (idChaussures == 0) {
            // Si l'idChaussures vaut 0,  l'utilisateur n'a pas choisi de chaussures particulières, on cherche une paire de chaussures  comme d'habitude
            // chaussures choisies en fonction de la couleur correspondante qui sera donc celle du sac ou du vetement choisi
            recupererChaussures(typeTenue, id, evenement, couleurCorrespondante, saison);
        }
        return this;

    }
    // ------------------------------------------------------------------------------------------ 
    //VETEMENT//
    // ------------------------------------------------------------------------------------------ 
    // ------------------------------------------------------------------------------------------     

    /**
     * Permet de remplir le tableau de vetementde la tenue avec 1 ou 2 vetements
     * de couche 1 ( hors manteau et veste)
     */
    private Couleur recupererTableauVetementsCouche1(int typeTenue, int id, TypeEvenement evenement, Couleur couleurCorrespondante, String saison) throws SQLException, TenueImpossibleException {
        // Initialisation des variables :
        Vetement v1 = new Vetement();
        Vetement v2 = new Vetement();
        Random rand = new Random();

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
                v1 = chercherDansVetements(typeTenue, id, chercherDansCouche(Vetement.getHauts(), 1), saison, couleurCorrespondante, evenement);
                couleurCorrespondante = v1.getCouleur();
                // On cherche ensuite un bas avec la couleurCorrespondante égale à celle du 1er vêtement
                v2 = chercherDansVetements(typeTenue, id, Vetement.getBas(), saison, couleurCorrespondante, evenement);
                break;
            case 1:
                System.out.println("cas 1");
                // On cherche tout d'abord un bas avec une couleur correspondante = 0 
                v1 = chercherDansVetements(typeTenue, id, Vetement.getBas(), saison, couleurCorrespondante, evenement);
                couleurCorrespondante = v1.getCouleur();
                // On cherche ensuite un haut de couche 1 avec la couleurCorrespondante égale à celle du 1er vêtement
                v2 = chercherDansVetements(typeTenue, id, chercherDansCouche(Vetement.getHauts(), 1), saison, couleurCorrespondante, evenement);
                break;
            case 2:
                System.out.println("cas 2");
                // On cherche un hautbas \avec une Couleur correspondante=0

                v1 = chercherDansVetements(typeTenue, id, Vetement.getHautsbas(), saison, couleurCorrespondante, evenement);
                couleurCorrespondante = v1.getCouleur();
                break;

        }

        // On attribut les vêtements trouvés à notre tenue : 
        this.setVetements(v1);
        if (v2 != null && v2.getIdObjet() != 0) {
            this.setVetements(v2);
        }
        return couleurCorrespondante;

    }

    /**
     * Permet de remplir le tableau de vetement de la tenue avec le vetement de
     * couche 2 ( manteau et veste)
     */
    private void recupererTableauVetementsCouche2(int typeTenue, int id, TypeEvenement evenement, Couleur couleurCorrespondante, String saison) throws SQLException, TenueImpossibleException {
        Vetement v3 = new Vetement();
        // Si notre saison est Automne/Hiver et que notre évènement n'est pas sport, 
        // on choisit un vêtement de couche 2
        if ((saison.equals("Automne/Hiver")) && (evenement != TypeEvenement.Sport)) {
            v3 = chercherDansVetements(typeTenue, id, chercherDansCouche(Vetement.getHauts(), 2), saison, couleurCorrespondante, evenement);
        }
        if (v3 != null && v3.getIdObjet() != 0) {
            this.setVetements(v3);
        }
    }

    /**
     * Permet de remplir l'attribut sac de la tenue
     */
    private void recupererSac(int typeTenue, int id, TypeEvenement evenement, Couleur couleurCorrespondante, String saison) throws SQLException {
        Sac s;
        s = chercherDansSac(Sac.getSacs(), couleurCorrespondante, evenement);
        if (s != null && s.getIdObjet() != 0) {
            this.setSac(s);
        }
    }

    /**
     * Permet de remplir l'attribut chaussures de la tenue
     */
    private void recupererChaussures(int typeTenue, int id, TypeEvenement evenement, Couleur couleurCorrespondante, String saison) throws SQLException {
        Chaussures c;
        c = chercherDansChaussures(Chaussures.getChaussures(), saison, couleurCorrespondante, evenement);
        if (c != null && c.getIdObjet() != 0) {
            this.setChaussures(c);
        }
    }

    /**
     * Permet de retourner un tableau de vetements qui correspondent à la couche
     * demandée par l'utilisateur
     */
    private HashMap<Integer, Vetement> chercherDansCouche(HashMap<Integer, Vetement> vetementsE, int numeroCouche)throws TenueImpossibleException {
        // Initialisation d'un HashMap<Integer, Vetement>
        HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();

        // Copie des vetements en entrée dans ce nouveau HashMap 
        vetements.putAll(vetementsE);

        // On enlève de ce HashMap, les vetements dont la couche ne correspond pas à celle attendue
        Iterator<Vetement> it = vetements.values().iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if (v.getCouche() != numeroCouche) {
                it.remove();
            }
        }
        if (vetements.isEmpty()) {
            throw new TenueImpossibleException("Pas assez de vêtements dans le dressing pour créer une tenue correspondant aux contraintes.");
        }
        return vetements;
    }

// ------------------------------------------------------------------------------------------    
    /**
     * Permet de renvoyer un vetement selon différents critères. 1 - Si le type
     * de tenue est "normal", le vetement choisi correspond à l'evenement, la
     * saison et la couleur du vetement precedent. 2 -Si le type de tenue est
     * "accordée au signe", le vetement choisi correspond à l'evenement, la
     * saison, la couleur du vetement precedent et au signe de l'utilisateur. 3
     * -Si le type de tenue est "avec contenu particulier", le vetement choisi
     * correspond à l'evenement, la saison et la couleur du vetement precedent.
     */
    private Vetement chercherDansVetements(int typeTenue, int id, HashMap<Integer, Vetement> vetements, String saison, Couleur couleurCorrespondante, TypeEvenement evenement) throws SQLException, TenueImpossibleException {

        GeneriqueTenue g = new GeneriqueTenue();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Vetement> t = new ArrayList<Vetement>(vetements.values());
        // Réduction du tableau en fonction des différents critères
        switch (typeTenue) {
            case 1:
                t = chercherVetementEvenement(t, evenement);
                t = chercherVetementSaison(t, saison);
            case 2:
                t = chercherVetementEvenement(t, evenement);
                t = chercherVetementSigne(id, t);
                t = chercherVetementSaison(t, saison);
                break;
            case 3:
                t = chercherVetementEvenement(t, evenement);
                t = chercherVetementSaison(t, saison);
                break;
        }
        // si la couleur est differente de 0, donc que le vetement qu'on cherche n'est pas le premier, alors on l'accorde a la couleur du premier vetement.
        if (couleurCorrespondante.getCouleur() != 0) {
            t = g.chercherCouleur(t, couleurCorrespondante);
        }
        if (t.isEmpty()) {
            throw new TenueImpossibleException("Pas assez de vêtements dans le dressing pour créer une tenue correspondant aux contraintes.");
        }
        return ((Vetement) g.prendreAleatoirement(t));
        // on choisi aleatoirement un vetement dans le dernier tableau de vetements renvoyé. (Tous ces vetements correspondent parfaitement à la tenue).

    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer un vetement selon l'evenement. 1 - Si l'evenement est
     * sport on veut des joggings teeshirts ou pulls. 3 -Si l'evenement est
     * soirée on ne veut pas de joggings ni de pantacourts et on veut que
     * certaines couleurs. 3 -Si l'evenement est tous les jours on ne veut pas
     * de jogging.
     */
    private ArrayList<Vetement> chercherVetementEvenement(ArrayList<Vetement> vetements, TypeEvenement ev) {
        // Différents cas en fonction de l'evenement : 
        switch (ev) {

            case Sport:
                Iterator<Vetement> it = vetements.iterator();
                while (it.hasNext()) {
                    Vetement v = it.next();
                    // Que Jogging, Teeshirt, Pull pour le sport ! 
                    if ((!v.getType().equals(TypeVetement.Jogging)) && (!v.getType().equals(TypeVetement.Teeshirt)) && (!v.getType().equals(TypeVetement.Pull))) {
                        it.remove();
                    }
                }
                break;
            case Soiree:
                Iterator<Vetement> it2 = vetements.iterator();
                while (it2.hasNext()) {
                    boolean estRetire = false;
                    Vetement v = it2.next();
                    // Pas de jogging ni pantacourt en soirée ! 
                    if ((v.getType().equals(TypeVetement.Jogging)) || (v.getType().equals(TypeVetement.Pantacourt))) {
                        it2.remove();
                        estRetire = true;
                    }
                    // Que certaines couleur en soirée !
                    if (!estRetire) {
                        if ((v.getCouleur().getCouleur() != (6)) && (v.getCouleur().getCouleur() != 14) && (v.getCouleur().getCouleur() != 24) && (v.getCouleur().getCouleur() != 25)) {
                            it2.remove();
                        }
                    }
                }
                break;
            case TousLesJours:
                Iterator<Vetement> it3 = vetements.iterator();
                while (it3.hasNext()) {
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
    /**
     * Permet de renvoyer un vetement selon la saison.
     */
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

    /**
     * Permet de renvoyer un vetement qui correspond au signe de l'utilisateur
     * (du possesseur du dressing).
     */
    private ArrayList<Vetement> chercherVetementSigne(int id, ArrayList<Vetement> vetements) throws SQLException {
        Utilisateur u = new Utilisateur();
        Utilisateur ut = u.trouverUtilisateur(id);
        // On ne garde que les vêtements qui correspondent au signe de l'utilisateur
        Iterator<Vetement> it = vetements.iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if (!v.correspondAuSigne(ut)) {
                it.remove();
            }
        }
        return vetements;
    }

    // ------------------------------------------------------------------------------------------ 
    //SAC//
    // ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------         
    /**
     * Permet de renvoyer un sac selon l'evenement.
     */
    private Sac chercherDansSac(HashMap<Integer, Sac> sacs, Couleur couleurCorrespondante, TypeEvenement evenement) {
        Sac s = new Sac();
        GeneriqueTenue g = new GeneriqueTenue();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Sac> t = new ArrayList<Sac>(sacs.values());
        // Réduction du tableau en fonction des différents critères
        t = chercherSacEvenement(t, evenement);
        t = g.chercherCouleur(t, couleurCorrespondante);
        return ((Sac) g.prendreAleatoirement(t));

    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer un sac selon l'evenement. 1 - Si l'évenement est tous
     * les jours on ne veut pas de pochettes . 2 - Si l'évenement est sport on
     * ne veut que des sacs à dos . 3 - Si l'evenement est soirée on ne veut pas
     * de sacs à dos.
     */
    private ArrayList<Sac> chercherSacEvenement(ArrayList<Sac> sacs, TypeEvenement ev) {
        // Différents cas en fonction de l'evenement : 
        switch (ev) {
            case TousLesJours:
                Iterator<Sac> it = sacs.iterator();
                while (it.hasNext()) {
                    Sac s = it.next();
                    // Pasde pochette pour tous les jours !! 
                    if ((s.getTypeS().equals(TypeSac.Pochette))) {
                        it.remove();
                    }
                }
                break;
            case Sport:
                Iterator<Sac> it1 = sacs.iterator();
                while (it1.hasNext()) {
                    Sac s = it1.next();
                    // Que des sacs à dos pour sport ! 
                    if ((!s.getTypeS().equals(TypeSac.Sacados))) {
                        it1.remove();
                    }
                }
                break;
            case Soiree:
                Iterator<Sac> it2 = sacs.iterator();
                while (it2.hasNext()) {
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
    /**
     * Permet de renvoyer une paire de chaussures selon la saison et
     * l'evenement.
     */
    private Chaussures chercherDansChaussures(HashMap<Integer, Chaussures> chaussures, String saison, Couleur couleurCorrespondante, TypeEvenement evenement) {
        Chaussures c = new Chaussures();
        GeneriqueTenue g = new GeneriqueTenue();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Chaussures> t = new ArrayList<Chaussures>(chaussures.values());
        // Réduction du tableau en fonction des différents critères
        t = chercherChaussuresEvenement(t, evenement);
        t = chercherChaussuresSaison(t, saison);
        if (couleurCorrespondante.getCouleur() != 0) {
            t = g.chercherCouleur(t, couleurCorrespondante);
        }
        return ((Chaussures) g.prendreAleatoirement(t));
        /*if (t.isEmpty()) {
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
         return null;*/
    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer une paire de chaussures selon l'evenement. 1 -Si
     * l'évenement est sport on ne veut que des baskets . 2 - Si l'evenement est
     * soirée on ne veut pas de baskets ni de bottes plates. 3 - Si l'évenement
     * est tous les jours on ne veut pas de baskets ni d'escrapins.
     */
    private ArrayList<Chaussures> chercherChaussuresEvenement(ArrayList<Chaussures> chaussures, TypeEvenement ev) {
        // Différents cas en fonction de l'evenement : 
        switch (ev) {
            case Sport:
                Iterator<Chaussures> it = chaussures.iterator();
                while (it.hasNext()) {
                    Chaussures c = it.next();
                    // Que des Baskets ! 
                    if ((!c.getTypeC().equals(TypeChaussures.Baskets))) {
                        it.remove();
                    }
                }
                break;
            case Soiree:
                Iterator<Chaussures> it2 = chaussures.iterator();
                while (it2.hasNext()) {
                    Chaussures c = it2.next();
                    // Pas de baskets ne de bottes plates en soirée ! 
                    if ((c.getTypeC().equals(TypeChaussures.Baskets)) || (c.getTypeC().equals(TypeChaussures.Bottesplates))) {
                        it2.remove();
                    }
                }
                break;
            case TousLesJours:
                Iterator<Chaussures> it3 = chaussures.iterator();
                while (it3.hasNext()) {
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
    /**
     * Permet de renvoyer une paire de chaussures selon la saison. 1 -Si la
     * saison est Automne/Hiver, on ne veut pas de sandales ni de ballerines . 2
     * - Si la saison est Printemps/Ete, on ne veut pas de bottes .
     */
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
// ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------ 
    /**
     * Permet d'afficher le menu permettant à l'utilisateur de faire ces choix
     * pour la tenue désirée.
     */
    /*    public void menuCreerTenue(int id) throws SQLException {

     Scanner sc = new Scanner(System.in);
     System.out.println("Evenement :");
     System.out.println("1: Tous Les Jours\t 2: Sport\t 3: Soiree\t");
     TypeEvenement evt = TypeEvenement.getfromInt(sc.nextInt());
     System.out.println("Type de tenue :");
     System.out.println("1: Tenue normale\t 2: Tenue accordee a la forme\t 3: Tenue avec un contenu particulier\t 4: Tenue avec un type particulier\t");
     int typeTenue = sc.nextInt();
     System.out.println(creerTenue(typeTenue, id, evt).toString());

     }*/
    @Override
    public String toString() {
        return "Tenue{" + "sac=" + sac + ", \nchaussures=" + chaussures + ", \nvetements=" + vetements + '}';
    }
}
