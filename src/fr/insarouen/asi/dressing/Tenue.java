package fr.insarouen.asi.dressing;

import fr.insarouen.asi.dressing.dao.concret.UtilisateurDAO;
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
    private ArrayList<Couleur> couleurs = new ArrayList<Couleur>();//tableau contenant les couleurs de chaque element de la tenue

    public Tenue() {
    }

    // Setters 
    
     /**
     * Ajoute un vêtement au tableau contenant les vêtements de la tenue.
     */
    public void setVetements(Vetement vetement) {
        this.vetements.add(vetement);
        this.couleurs.add(vetement.getCouleur());
    }

    /**
     * Ajoute un sac à la tenue.
     */
    public void setSac(Sac sac) {
        this.sac = sac;
         this.couleurs.add(sac.getCouleur());
    }

    /**
     * Ajoute une paire de chaussures à la tenue.
     */
    public void setChaussures(Chaussures chaussures) {
        this.chaussures = chaussures;
         this.couleurs.add(chaussures.getCouleur());
    }
    
    // Getters 

    /**
     * Permet de récupérer le sac de la tenue
     * @return Sac
     */
    public Sac getSac() {
        return sac;
    }

    /**
     * Permet de récupérer la paire de chaussures de la tenue
     * @return Chaussures
     */
    public Chaussures getChaussures() {
        return chaussures;
    }

    /**
     * Permet de récupérer les vêtements de la tenue
     * @return ArrayList
     */
    public ArrayList<Vetement> getVetements() {
        return vetements;
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
     * l'utilisateur
     * 
     * <ul>
     * <li>1 : tenue normale </li>
     * <li>2 : Tenue avec un contenu particulier </li>
     * <li>3 : Tenue avec un type particulier </li>
     * </ul>
     * 
     * @return int[] : Tableau d'id 
     */
    public int[] menuCreerTenueContenuParticulier() throws SQLException , TenueImpossibleException{
        Scanner sc = new Scanner(System.in);
        Vetement.afficherVetementsSaleOuPropre(false);
        System.out.println("Entrez l'id du vetement que vous voulez  (0 si vous ne désirez pas de vetement particulier):");
        int idVetement = sc.nextInt();
        Sac.afficherSacs();
        System.out.println("Entrez l'id du sac que vous voulez  (0 si vous ne désirez pas de sac particulier):");
        int idSac = sc.nextInt();
        Chaussures.afficherChaussures();
        System.out.println("Entrez l'id des chaussures que vous voulez  (0 si vous ne désirez pas de chaussures  particulières):");
        int idChaussures = sc.nextInt();
        int[] tableauId = {idVetement, idSac, idChaussures};
        return tableauId;
    }

    /**
     * Permet de creer une tenue en fonction du type de tenue choisi par
     * l'utilisateur
     *
     * 
     * @return ArrayList : Contient les vêtements du type entré par l'utilisateur 
     */
    public ArrayList<Vetement> menuCreerTenueTypeParticulier() throws SQLException, TenueImpossibleException {
        Scanner scc = new Scanner(System.in);
        System.out.println("Quel type de vêtement voulez-vous particulièrement ?\n 1: Tee-shirt\t 2: Chemisier\t 3: Pull\t 4: Veste\t 5: Manteau\t 6: Pantalon\t 7: Pantacourt\t 8: Jogging\t 9: Jupe\t 10: Short\t 11: Robe\t 12: Combinaison ");
        TypeVetement type = TypeVetement.getfromInt(scc.nextInt());

        Vetement v = new Vetement();
        GeneriqueTenue g = new GeneriqueTenue();

        ArrayList<Vetement> tab = new ArrayList<Vetement>(Vetement.getVetements().values());
        tab = chercherVetementType(tab, type);
        return tab;
    }

    /**     
     * Permet de creer une tenue.
     * 
     * @param tableauIdChoisis  Tableau d'id choisis si l'utilisateur souhaite un cntenu particulier. Contien 0 sinon. 
     * @param vetementsTypeChoisis : Contient les vêtements du type choisi par l'utilisateur si celui-ci a demandé un type particulier
     * @param typeTenue  Type de la tenue choisie : 
     * <ul>
     * <li>1 : tenue normale </li>
     * <li>2 : Tenue avec un contenu particulier </li>
     * <li>3 : Tenue avec un type particulier </li>
     * </ul>
     * @param avecForme  Vrai si l'utilisateur a choisi l'option accordée à la forme, Faux sinon.
     * @param idUtilisateur  id de l'utilisateur.
     * @param evenement  evênement choisi par l'utilisateur
     * 
     * @return Tenue  La tenue créée selon les contraintes
     */
    public Tenue creerTenue(int[] tableauIdChoisis, ArrayList<Vetement> vetementsTypeChoisis, int typeTenue, int avecForme, int idUtilisateur, TypeEvenement evenement) throws SQLException, TenueImpossibleException {
        // int[] tableauId = menuChoixUtilisateur(typeTenue);
      Vetement v = new Vetement();
      GeneriqueTenue g = new GeneriqueTenue();
        int idVetement =  tableauIdChoisis[0];
        int idSac =  tableauIdChoisis[1];
        int idChaussures = tableauIdChoisis[2];
        if (typeTenue == 3) {
            v = (Vetement) g.prendreAleatoirement(vetementsTypeChoisis);
            idVetement = v.getIdObjet();
        }
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
            
            // On cherche ce  vetement particulier à partir de l'idVetement entré par l'utilisateur
            v = v.trouverVetement(idVetement,UtilisateurDAO.obtenirIdDressing(idUtilisateur));
            couleurCorrespondante = v.getCouleur();
            // Si le vetement choisi est une couche 2 (Veste ou Manteau)
            if (v.getCouche() == 2) {
                // On place ce vetement dans v3 puis on l'ajoute à la tenue
                v3 = v;
                this.setVetements(v3);
                // On va chercher les vetements de couche 1 pour completer la tenue
                recupererVetementsCouche1(null, avecForme, idUtilisateur, evenement, couleurCorrespondante, saison);
            }
            else {
                // Si le vetement est de couche 1 
                v1 = v;
                // On récupère le deuxième vetement de couche1
                couleurCorrespondante = recupererVetementsCouche1(v1, avecForme, idUtilisateur, evenement, couleurCorrespondante, saison);

                // Si le vetement choisi est une couche 1 , on va chercher une couche 2
                // recupererTableauVetementsCouche2 renvoie une valeur seulement si on est en Automne/Hiver et si on ne fait pas de sport.
                recupererVetementsCouche2(avecForme, idUtilisateur, evenement, couleurCorrespondante, saison);
            }
        }
        else {
            // Si l'idVetement vaut 0, l'utilisateur n'a pas choisi de vetement particulier, on cherche des vetements comme d'habitude
            // vetement choisis en fonction de la couleur correspondante qui sera donc celle des chaussures ou du sac choisi
            couleurCorrespondante = recupererVetementsCouche1(null, avecForme, idUtilisateur, evenement, couleurCorrespondante, saison);
            recupererVetementsCouche2(avecForme, idUtilisateur, evenement, couleurCorrespondante, saison);
        }

        // Si l'idSac est non nul, ça signifie que l'utilisateur à choisi un sac particulier
        if (idSac != 0) {
            s = s.trouverSac(idSac,UtilisateurDAO.obtenirIdDressing(idUtilisateur));
            this.setSac(s);
            // La couleurCorrespondante devient celle du sac choisi.
            couleurCorrespondante = s.getCouleur();
        }
        else {
            // Si l'idSac vaut 0,  l'utilisateur n'a pas choisi de sac particulier, on cherche un sac comme d'habitude
            // sac choisi en fonction de la couleur correspondante qui sera donc celle des chaussures ou du vetement choisi
            recupererSac(evenement, couleurCorrespondante);
        }

        // Si l'idChaussures est non nul, ça signifie que l'utilisateur à choisi des chaussures  particulières
        if (idChaussures != 0) {
            c = c.trouverChaussures(idChaussures,UtilisateurDAO.obtenirIdDressing(idUtilisateur));
            this.setChaussures(c);
            // La couleurCorrespondante devient celle des chaussures choisies.
            couleurCorrespondante = c.getCouleur();
        }
        else {
            // Si l'idChaussures vaut 0,  l'utilisateur n'a pas choisi de chaussures particulières, on cherche une paire de chaussures  comme d'habitude
            // chaussures choisies en fonction de la couleur correspondante qui sera donc celle du sac ou du vetement choisi
            recupererChaussures(evenement, couleurCorrespondante, saison);
        }

        return this;

    }

    // ------------------------------------------------------------------------------------------ 
    //VETEMENT//
    // ------------------------------------------------------------------------------------------ 
    // ------------------------------------------------------------------------------------------     
    /**
     * Permet de recuperer les vetements de couche 1 en fonction ou non d'une couleur correspondante
     * @param v1  contient un vetement uniquement si l'utilisateur a choisi un contenu particulier de couche 1.
     * @param avecForme  Vrai si l'utilisateur a choisi l'option accordée à la forme, Faux sinon.
     * @param idUtilisateur  id de l'utilisateur.
     * @param evenement  evênement choisi par l'utilisateur
     * @param couleurCorrespondante couleur à laquelle on veut faire correspondre les vetements choisis, vaut 0 si on a pas encore choisi d'autre element
     * @param saison saison courante
    */
    private Couleur recupererVetementsCouche1(Vetement v1, int avecForme, int idUtilisateur, TypeEvenement evenement, Couleur couleurCorrespondante, String saison) throws SQLException, TenueImpossibleException {
        // Initialisation des variables :
        Vetement v2 = new Vetement();
        Random rand = new Random();
        // Si aucun vêtement decouche 1 n'a été choisi : 
        if (v1 == null) {
            v1 = new Vetement();

            // Dans ce cas, on choisit v1 aléatoirement : 
            int nbAleatoire = 3;
            if (evenement.equals(TypeEvenement.Sport)) {
                nbAleatoire = 2;
            }
            int alea = rand.nextInt(nbAleatoire);

            switch (alea) {
                case 0:
                    //System.out.println("cas 0");
                    // On cherche tout d'abord un haut de couche 1 avec une couleur correspondante = 0 
                    v1 = chercherDansVetements(avecForme, idUtilisateur, chercherDansCouche(Vetement.getHauts(), 1), saison, couleurCorrespondante, evenement);
                    couleurCorrespondante = v1.getCouleur();
                    break;
                case 1:
                    //System.out.println("cas 1");
                    // On cherche tout d'abord un bas avec une couleur correspondante = 0 
                    v1 = chercherDansVetements(avecForme, idUtilisateur, chercherDansCouche(Vetement.getBas(), 1), saison, couleurCorrespondante, evenement);
                    couleurCorrespondante = v1.getCouleur();
                    break;
                case 2:
                    //System.out.println("cas 2");
                    // On cherche un hautbas \avec une Couleur correspondante=0
                    v1 = chercherDansVetements(avecForme, idUtilisateur, chercherDansCouche(Vetement.getHautsbas(), 1), saison, couleurCorrespondante, evenement);
                    couleurCorrespondante = v1.getCouleur();
                    break;

            }

        }
                    this.setVetements(v1);
        // On choisi un v2 en fonction du niveau du v1 : 
        switch (v1.getNiveau()) {
            case Bas:
                // Si le vetement choisi est un bas on va chercher un haut
                //System.out.println("cas bas");
                v2 = chercherDansVetements(avecForme, idUtilisateur, chercherDansCouche(Vetement.getHauts(), 1), saison, couleurCorrespondante, evenement);
                break;
            case Haut:
                // Si le vetement choisi est un haut on va chercher un bas
                //System.out.println("cas haut");
                v2 = chercherDansVetements(avecForme, idUtilisateur, chercherDansCouche(Vetement.getBas(), 1), saison, couleurCorrespondante, evenement);
                break;
        }

        // On attribut les vêtements trouvés à notre tenue : 
        // if (v1 != null && v1.getIdObjet() != 0) {
           // this.setVetements(v1);
       // }
        if (v2 != null && v2.getIdObjet() != 0) {
            this.setVetements(v2);
        }
        return couleurCorrespondante;

    }

    /**
     * Permet de recuperer les vetements de couche 2 en fonction ou non d'une couleur correspondante
     * 
     * @param avecForme  Vrai si l'utilisateur a choisi l'option accordée à la forme, Faux sinon.
     * @param idUtilisateur  id de l'utilisateur.
     * @param evenement  evênement choisi par l'utilisateur
     * @param couleurCorrespondante couleur à laquelle on veut faire correspondre les vetements choisis, vaut 0 si on a pas encore choisi d'autre element
     * @param saison saison courante
    */
    private void recupererVetementsCouche2(int avecForme, int idUtilisateur, TypeEvenement evenement, Couleur couleurCorrespondante, String saison) throws SQLException, TenueImpossibleException {
        Vetement v3 = new Vetement();

        // Si notre saison est Automne/Hiver et que notre évènement n'est pas sport, 
        // on choisit un vêtement de couche 2
        if ((saison.equals("Automne/Hiver")) && (evenement != TypeEvenement.Sport)) {
            v3 = chercherDansVetements(avecForme, idUtilisateur, chercherDansCouche(Vetement.getHauts(), 2), saison, couleurCorrespondante, evenement);
        }
        if (v3 != null && v3.getIdObjet() != 0) {
            this.setVetements(v3);
        }
    }

    /**
     * Permet de remplir l'attribut sac de la tenue
     * 
     * @param evenement evênement choisit par l'utilisateur
     * @param couleurCorrespondante couleur à laquelle on veut faire correspondre les vetements choisis, vaut 0 si on a pas encore choisi d'autre element
     */
    private void recupererSac( TypeEvenement evenement, Couleur couleurCorrespondante) throws SQLException, TenueImpossibleException {
        Sac s;
        s = chercherDansSac(Sac.getSacs(), couleurCorrespondante, evenement);
        if (s != null && s.getIdObjet() != 0) {
            this.setSac(s);
        }
    }

    /**
     * Permet de remplir l'attribut chaussures de la tenue
     * @param evenement evênement choisit par l'utilisateur
     * @param couleurCorrespondante couleur à laquelle on veut faire correspondre les vetements choisis, vaut 0 si on a pas encore choisi d'autre element
     */
    private void recupererChaussures( TypeEvenement evenement, Couleur couleurCorrespondante, String saison) throws SQLException, TenueImpossibleException {
        Chaussures c;
        c = chercherDansChaussures(Chaussures.getChaussures(), saison, couleurCorrespondante, evenement);
        if (c != null && c.getIdObjet() != 0) {
            this.setChaussures(c);
        }
    }

    /**
     * Permet de retourner un tableau de vetements qui correspondent à la couche
     * demandée par l'utilisateur
     * 
     * @param vetementsE Correspond à un tableau de vêtements dans lequel on va tester pour chaque vêtement 
     * si la couche correspond à celle voulue par l'utilisateur. 
     * @param numeroCouche Entier correspondant au numéro de la couche souhaité
     * @return HashMap Tableau de vêtements dont la couche correspond à celle demandée par l'uitilisateur
     */
    private HashMap<Integer, Vetement> chercherDansCouche(HashMap<Integer, Vetement> vetementsE, int numeroCouche) throws TenueImpossibleException {
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
     * Permet de renvoyer un vetement selon différents critères. 
     * 
     * 
     * @param avecForme  Vrai si l'utilisateur a choisi l'option accordée à la forme, Faux sinon.
     * @param idUtilisateur  Id de l'utilisateur.
     * @param evenement  Evênement choisi par l'utilisateur
     * @param vetements Tableau de vêtements dans lequel se fait la recherche 
     * @param couleurCorrespondante Couleur à laquelle on veut faire correspondre les vetements choisis, vaut 0 si on a pas encore choisi d'autre element
     * @param saison Saison courante
     * 
     * @return Vetement Vêtement qui sera ajouter à la Tenue
     */
    private Vetement chercherDansVetements(int avecForme, int idUtilisateur, HashMap<Integer, Vetement> vetements, String saison, Couleur couleurCorrespondante, TypeEvenement evenement) throws SQLException, TenueImpossibleException {

        GeneriqueTenue g = new GeneriqueTenue();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Vetement> t = new ArrayList<Vetement>(vetements.values());
        // Réduction du tableau en fonction des différents critères
        if (avecForme == 1) {
            t = chercherVetementSigne(idUtilisateur, t);
        }
        t=chercherVetementSalePropre(t);
        t = chercherVetementEvenement(t, evenement);
        t = chercherVetementSaison(t, saison);

        // si la couleur est differente de 0, donc que le vetement qu'on cherche n'est pas le premier, alors on l'accorde a la couleur du premier vetement.
        if (couleurCorrespondante.getCouleur() != 0) {
            t = g.chercherCouleur(t, couleurCorrespondante,couleurs);
        }
        return ((Vetement) g.prendreAleatoirement(t));
        // on choisi aleatoirement un vetement dans le dernier tableau de vetements renvoyé. (Tous ces vetements correspondent parfaitement à la tenue).

    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer des vêtements correspondant à un évênement rentré par l'utilisateur
     * 
     * @param vetements Tableau de vêtements dans lequel va s'effectuer la recherche
     * @param ev Evênement choisi par l'utilisateur 
     * @return ArrayList Tableau de vêtements correspondant à l'évênement
     */
    private ArrayList<Vetement> chercherVetementEvenement(ArrayList<Vetement> vetements, TypeEvenement ev) throws TenueImpossibleException {
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
        if (vetements.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de vêtements correspondant à cet évènement");
        }
        return vetements;
    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer un tableau de vetements selon la saison courante.
     *
     * @param vetements Tableau de vêtements dans lequel va s'effectuer la recherche
     * @param saison Saison courante 
     * @return ArrayList Tableau de vêtements correspondant à l'évênement
     */
    private ArrayList<Vetement> chercherVetementSaison(ArrayList<Vetement> vetements, String saison) throws TenueImpossibleException {
        // On ne garde que les vêtements dont la saison correspond à celle actuelle ou à "Toutes"
        Iterator<Vetement> it = vetements.iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if ((!v.determinerSaison().equals(saison)) && (!v.determinerSaison().equals("Toutes"))) {
                it.remove();
            }
        }
        if (vetements.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de vêtements correspondant à la saison");
        }
        return vetements;
    }
// ------------------------------------------------------------------------------------------ 

    /**
     * Permet de renvoyer un tableau de vetements qui correspond au signe de
     * l'utilisateur (du possesseur du dressing).
     * 
     * @param idUtilisateur Id de l'utilisateur 
     * @param vetements Tableau de vêtements dans lequel va s'effectuer la recherche
     * @return ArrayList Tableau de vêtements correspondant au signe
     */
    private ArrayList<Vetement> chercherVetementSigne(int idUtilisateur, ArrayList<Vetement> vetements) throws SQLException, TenueImpossibleException {
        Utilisateur u = new Utilisateur();
        Utilisateur ut = u.trouverUtilisateur(idUtilisateur);
        // On ne garde que les vêtements qui correspondent au signe de l'utilisateur
        Iterator<Vetement> it = vetements.iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if (!v.correspondAuSigne(ut)) {
                it.remove();
            }
        }
        if (vetements.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas assez de vêtements correspondant à votre forme");
        }
        return vetements;
    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer un tableau de vetements selon un type choisi par
     * l'utilisateur
     * 
     * @param vetements Tableau de vêtements dans lequel va s'effectuer la recherche
     * @param type Type du vêtement souhaité par l'utilisateur 
     * @return ArrayList Tableau de vêtements correspondant à l'évênement
     *
     */
    private ArrayList<Vetement> chercherVetementType(ArrayList<Vetement> vetements, TypeVetement type) throws SQLException, TenueImpossibleException {
        // On ne garde que les vêtements qui correspondent au type souhaité
        Iterator<Vetement> it = vetements.iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if (!v.getType().equals(type)) {
                it.remove();
            }
        }
        if (vetements.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de vêtements de ce type");
        }
        return vetements;
    }

    /**
     * Permet de renvoyer un tableau de vetements qui ne sont pas sales
     * 
     * @param vetements Tableau de vêtements dans lequel va s'effectuer la recherche 
     * @return ArrayList Tableau de vêtements correspondant à l'évênement
     *
     */
     private ArrayList<Vetement> chercherVetementSalePropre(ArrayList<Vetement> vetements) throws SQLException, TenueImpossibleException {
        Iterator<Vetement> it = vetements.iterator();
        while (it.hasNext()) {
            Vetement v = it.next();
            if (v.isSale()) {
                it.remove();
            }
        }
        if (vetements.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de vêtements de ce type");
        }
        return vetements;
    }
    
    // ------------------------------------------------------------------------------------------ 
    //SAC//
    // ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------         
    /**
     * Permet de renvoyer un sac selon différents critères. 
     * 
     * @param sacs Tableau de sacs dans lequel se fait la recherche 
     * @param evenement  Evênement choisi par l'utilisateur
     * @param couleurCorrespondante Couleur à laquelle on veut faire correspondre les sacs
     * 
     * @return Sac Sac qui sera ajouté à la Tenue
     */
    private Sac chercherDansSac(HashMap<Integer, Sac> sacs, Couleur couleurCorrespondante, TypeEvenement evenement) throws TenueImpossibleException {
        Sac s = new Sac();
        GeneriqueTenue g = new GeneriqueTenue();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Sac> t = new ArrayList<Sac>(sacs.values());
        // Réduction du tableau en fonction des différents critères
        t = chercherSacEvenement(t, evenement);
        t = g.chercherCouleur(t, couleurCorrespondante,couleurs);
        return ((Sac) g.prendreAleatoirement(t));

    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer des sacs correspondant à un évênement rentré par l'utilisateur
     * 
     * @param sacs Tableau de sacs dans lequel va s'effectuer la recherche
     * @param ev Evênement choisi par l'utilisateur 
     * @return ArrayList Tableau de sacs correspondant à l'évênement
     */
    private ArrayList<Sac> chercherSacEvenement(ArrayList<Sac> sacs, TypeEvenement ev) throws TenueImpossibleException {
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
        if (sacs.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de sacs correspondant à cet évènement");
        }
        return sacs;
    }

    // ------------------------------------------------------------------------------------------ 
    //CHAUSSURES//
    // ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------          
    /**
     * Permet de renvoyer une paire de chaussures selon différents critères. 
     * 
     * @param chaussures Tableau de chaussures dans lequel se fait la recherche 
     * @param saison Saison courante
     * @param evenement  Evênement choisi par l'utilisateur
     * @param couleurCorrespondante Couleur à laquelle on veut faire correspondre les chaussures
     * 
     * @return Chaussures Paire de chaussures qui sera ajoutée à la Tenue
     */
    private Chaussures chercherDansChaussures(HashMap<Integer, Chaussures> chaussures, String saison, Couleur couleurCorrespondante, TypeEvenement evenement) throws TenueImpossibleException {
        Chaussures c = new Chaussures();
        GeneriqueTenue g = new GeneriqueTenue();
        // initialisation du tableau de vetements (Hauts)
        ArrayList<Chaussures> t = new ArrayList<Chaussures>(chaussures.values());
        // Réduction du tableau en fonction des différents critères
        t = chercherChaussuresEvenement(t, evenement);
        t = chercherChaussuresSaison(t, saison);
        if (couleurCorrespondante.getCouleur() != 0) {
            t = g.chercherCouleur(t, couleurCorrespondante,couleurs);
        }
        return ((Chaussures) g.prendreAleatoirement(t));
    }

// ------------------------------------------------------------------------------------------ 
     /**
     * Permet de renvoyer des chaussures correspondant à un évênement rentré par l'utilisateur
     * 
     * @param chaussures Tableau de chaussures dans lequel va s'effectuer la recherche
     * @param ev Evênement choisi par l'utilisateur 
     * @return ArrayList Tableau de chaussures correspondant à l'évênement
     */
    private ArrayList<Chaussures> chercherChaussuresEvenement(ArrayList<Chaussures> chaussures, TypeEvenement ev) throws TenueImpossibleException {
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
        if (chaussures.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de chaussures correspondant à cet évènement");
        }
        return chaussures;
    }

// ------------------------------------------------------------------------------------------ 
    /**
     * Permet de renvoyer un tableau de chaussures selon la saison courante.
     *
     * @param chaussures Tableau de chaussures dans lequel va s'effectuer la recherche
     * @param saison Saison courante 
     * @return ArrayList Tableau de chaussures correspondant à l'évênement
     */
    private ArrayList<Chaussures> chercherChaussuresSaison(ArrayList<Chaussures> chaussures, String saison) throws TenueImpossibleException {
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
        if (chaussures.isEmpty()) {
            throw new TenueImpossibleException("Vous ne possédez pas de chaussures correspondant à cette saison");
        }
        return chaussures;
    }

// ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------ 
// ------------------------------------------------------------------------------------------ 
    @Override
    public String toString() {
        return "Tenue{" + "sac=" + sac + ", \nchaussures=" + chaussures + ", \nvetements=" + vetements + '}'+"couleurs ="+couleurs;
    }
}
