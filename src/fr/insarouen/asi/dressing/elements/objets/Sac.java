package fr.insarouen.asi.dressing.elements.objets;

import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.TypeSac;
import fr.insarouen.asi.dressing.dao.concret.SacDAO;
import fr.insarouen.asi.dressing.elements.Couleur;
import java.util.HashMap;

public class Sac extends Contenu{

    private TypeSac typeS;
    public static HashMap<Integer, Sac> sacs = new HashMap<Integer, Sac>();

    public Sac(int idObjet, int idDressing, TypeSac typeS, Couleur couleur) {
        super(couleur, idObjet, idDressing);
        this.typeS = typeS;
    }

    public Sac() {
    }

    public TypeSac getTypeS() {
        return typeS;
    }


    public static HashMap<Integer, Sac> getSacs() {
        return sacs;
    }

    public void setTypeS(TypeSac typeS) {
        this.typeS = typeS;
    }
    /** 
     *Premet de demander à l'utilisateur les caractéristiques nécessaire à la création d'un nouveau sac
     * @return sac java créé
     */

    public Sac menuAjouterSacTxt() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le type de sac");
        System.out.println("1: Sac a dos\t 2: Sac a main\t 3: Pochette");
        TypeSac typeS = TypeSac.getfromInt(sc.nextInt());

        System.out.println("Entrez la couleur du sac");
        System.out.println( "1 - bleu\n" +
                            "2 - bleu clair\n" +
                            "3 - bleu marine\n" +
                            "4 - turquoise\n" +
                            "5 - gris clair\n" +
                            "6 - argenté\n" +
                            "7 - gris foncé\n" +
                            "8 - marron clair\n" +
                            "9 - marron foncé\n" +
                            "10 - corail\n" +
                            "11 - orange\n" +
                            "12 - bordeau\n" +
                            "13 - brique\n" +
                            "14 - rouge\n" +
                            "15 - rose pale\n" +
                            "16 - rose fushia\n" +
                            "17 - rose foncé\n" +
                            "18 - mauve\n" +
                            "19 - violet\n" +
                            "20 - prune\n" +
                            "21 - blanc\n" +
                            "22 - jaune moutarde \n" +
                            "23 - jaune\n" +
                            "24 - doré\n" +
                            "25 - noir\n" +
                            "26 - kaki\n" +
                            "27 - vert pale\n" +
                            "28 - vert\n" +
                            "29 - jean clair\n" +
                            "30 - jean marine\n" +
                            "31 - beige");
        Couleur couleur = new Couleur(sc.nextInt());

        Sac s = new Sac();
        s.setTypeS(typeS);
        s.setCouleur(couleur);
        return s;
    }

    /**
     *Permet d'ajouter un sac dans le tableau de sac qui est un attribut de la classe
     */
    
    public void ajouterSacDansListe() {
        sacs.put(this.getIdObjet(), this);
    }

    /**
     * Permet d'ajouter un sac 
     * @param idDressing id du dressing auquel on veut ajouter le sac
     * @return VRAI si le sac a été bien ajouté, FAUX sinon
     */
    
    public boolean ajouterSac(int idDressing, TypeSac typeS, Couleur couleurS) throws SQLException {

        //Sac s = menuAjouterSacTxt();
        this.setIdDressing(idDressing);
        this.setTypeS(typeS);
        this.setCouleur(couleurS);
        SacDAO nouveauSac = new SacDAO();
        nouveauSac.create(this);
        this.ajouterSacDansListe();
        return true;
    }

    /**
     *Permet de supprimer un sac dans le tableau de sacs en attribut de la classe
     * @param id du sac à supprimer
     */
    
    public void supprimerSacDansListe(int id) throws SQLException {
        if (sacs.containsKey(id)) {
            sacs.remove(id);
        }
    }

        /**
     *Permet de supprimer un sac du dressing
     * @param idDressing dressing contenant le sac
     * @return VRAI si le sac a bien été supprimé, FAUX sinon
     */

    public boolean supprimerSac(int idDressing) throws SQLException {
        if(sacs.isEmpty()){
            System.out.println("Vous n'avez pas de sacs ! ");
            return false;
        }else{
        afficherSacs();
        SacDAO sacASupprimer = new SacDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("entrez l'id du sac à supprimer");
        int id = sc.nextInt();
        if (sacASupprimer.find(id, idDressing) != null) {
            sacASupprimer.find(id, idDressing).supprimerSacDansListe(id);
            sacASupprimer.delete(sacASupprimer.find(id, idDressing));
            return true;
        }
        else {
            return false;
        }
        }
    }

    /**
     *Permet d'initialiser le tableau de sac contenu en attribut dans la classe avec les données de la base
     *id id du dressing qu'on veut initialiser
     */
    
    public static void initSacs(int id){
        sacs=SacDAO.initialiserSacs(id);
    }
    /**
     *Permet d'afficher tous les sacs
     */
    
    public static void afficherSacs() {
        if(!sacs.isEmpty()){
            for (Sac s : sacs.values()) {
                System.out.println(s.toString());
            }
        }else{
            System.out.println("\nIl n'y a pas de sacs");
        }
    }
    

        
    /**
     *Permet de récupérer un sac en fonction de son id
     * @param id id du sac qu'on veut récupérer
     * @return objet Sac java
     */
    
    public Sac trouverSac(int id, int idDressing) throws SQLException {
        SacDAO s = new SacDAO();
        return s.find(id, idDressing);
    }

    @Override
    public String toString() {
        return "Sac:\n\t" + "\n\tType : " + typeS + "\n\tCouleur : " + getCouleur() + "\n\tidDressing : " + getIdDressing() + "\n\tidObjet : " + getIdObjet();
    }

}
