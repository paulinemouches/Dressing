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

    public void ajouterSacDansListe() {
        sacs.put(this.getIdObjet(), this);
    }

    public boolean ajouterSac(int idDressing) throws SQLException {

        Sac s = menuAjouterSacTxt();
        s.setIdDressing(idDressing);
        SacDAO nouveauSac = new SacDAO();
        nouveauSac.create(s);
        s.ajouterSacDansListe();
        return true;
    }

    public void supprimerSacDansListe(int id) throws SQLException {
        if (sacs.containsKey(id)) {
            sacs.remove(id);
            System.out.println("Le sac est supprimé");
        }
    }

    public boolean supprimerSac() throws SQLException {
        SacDAO sacASupprimer = new SacDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("entrez l'id du sac à supprimer");
        int id = sc.nextInt();
        if (sacASupprimer.find(id) != null) {
            sacASupprimer.find(id).supprimerSacDansListe(id);
            sacASupprimer.delete(sacASupprimer.find(id));
            return true;
        }
        else {
            return false;
        }

    }
    
    public static void initSacs(int id){
        sacs=SacDAO.initialiserSacs(id);
    }

    public static void afficherSacs() {
        if(!sacs.isEmpty()){
            for (Sac s : sacs.values()) {
                System.out.println(s.toString());
            }
        }else{
            System.out.println("\nIl n'y a pas de sacs");
        }
    }

    public Sac trouverSac(int id) throws SQLException {
        SacDAO s = new SacDAO();
        return s.find(id);
    }

    @Override
    public String toString() {
        return "Sac:\n\t" + "\n\tType : " + typeS + "\n\tCouleur : " + getCouleur() + "\n\tidDressing : " + getIdDressing() + "\n\tidObjet : " + getIdObjet();
    }

}
