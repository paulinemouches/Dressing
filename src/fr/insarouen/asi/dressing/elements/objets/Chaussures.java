package fr.insarouen.asi.dressing.elements.objets;

import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import fr.insarouen.asi.dressing.dao.concret.ChaussuresDAO;
import fr.insarouen.asi.dressing.elements.Couleur;
import java.util.HashMap;

public class Chaussures extends Contenu {

    TypeChaussures typeC;
    private static HashMap<Integer, Chaussures> chaussures = new HashMap<Integer, Chaussures>();

    public Chaussures() {
    }

    public Chaussures(int idObjet, int idDressing, TypeChaussures typeC, Couleur couleur) {
        super(couleur, idObjet, idDressing);
        this.typeC = typeC;
    }

    public static HashMap<Integer, Chaussures> getChaussures() {
        return chaussures;
    }

    public static void setChaussures(HashMap<Integer, Chaussures> chaussures) {
        Chaussures.chaussures = chaussures;
    }

    public TypeChaussures getTypeC() {
        return typeC;
    }

    public void setTypeC(TypeChaussures typeC) {
        this.typeC = typeC;
    }

    public static void afficherChaussures() {
        if (!chaussures.isEmpty()) {
            for (Chaussures c : chaussures.values()) {
                System.out.println(c.toString());
            }
        } else {
            System.out.println("\nIl n'y a pas de chaussuress");
        }
    }

    public Chaussures menuAjouterChaussuresTxt() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le type de chaussures");
        System.out.println("1: Escarpins\t 2: Ballerines\t 3: Baskets\t 4: Bottes plates\t 5: Bottes a talons\t 6: Sandales");
        TypeChaussures typeC = TypeChaussures.getfromInt(sc.nextInt());

        System.out.println("Entrez la couleur des chaussures");
        System.out.println("1 - bleu\n"
                + "2 - bleu clair\n"
                + "3 - bleu marine\n"
                + "4 - turquoise\n"
                + "5 - gris clair\n"
                + "6 - argenté\n"
                + "7 - gris foncé\n"
                + "8 - marron clair\n"
                + "9 - marron foncé\n"
                + "10 - corail\n"
                + "11 - orange\n"
                + "12 - bordeau\n"
                + "13 - brique\n"
                + "14 - rouge\n"
                + "15 - rose pale\n"
                + "16 - rose fushia\n"
                + "17 - rose foncé\n"
                + "18 - mauve\n"
                + "19 - violet\n"
                + "20 - prune\n"
                + "21 - blanc\n"
                + "22 - jaune moutarde \n"
                + "23 - jaune\n"
                + "24 - doré\n"
                + "25 - noir\n"
                + "26 - kaki\n"
                + "27 - vert pale\n"
                + "28 - vert\n"
                + "29 - jean clair\n"
                + "30 - jean marine\n"
                + "31 - beige");
        Couleur couleur = new Couleur(sc.nextInt());

        Chaussures c = new Chaussures();
        c.setTypeC(typeC);
        c.setCouleur(couleur);
        return c;
    }

    public void ajouterChaussuresDansListe() {
        chaussures.put(this.getIdObjet(), this);
    }

    public boolean ajouterChaussures(int idDressing) throws SQLException {

        Chaussures c = menuAjouterChaussuresTxt();
        c.setIdDressing(idDressing);
        ChaussuresDAO nouvellesChaussures = new ChaussuresDAO();
        nouvellesChaussures.create(c);
        c.ajouterChaussuresDansListe();
        return true;
    }

    public void supprimerChaussuresDansListe(int id) {
        if (chaussures.containsKey(id)) {
            chaussures.remove(id);
            System.out.println("Les chaussures sont supprimées");
        }
    }

    public boolean supprimerChaussures() throws SQLException {
        ChaussuresDAO cASupprimer = new ChaussuresDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("entrez l'id des chaussures à supprimer");
        int id = sc.nextInt();
        if (cASupprimer.find(id) != null) {
            cASupprimer.find(id).supprimerChaussuresDansListe(id);
            cASupprimer.delete(cASupprimer.find(id));
            return true;
        } else {
            return false;
        }

    }

    public static void initialiserChaussures(int id) {
        chaussures = ChaussuresDAO.initChaussures(id);
    }

    public Chaussures trouverChaussures(int id) throws SQLException {
        ChaussuresDAO c = new ChaussuresDAO();
        return c.find(id);
    }

    @Override
    public String toString() {
        return "\nChaussures:\n\t" + "\n\tType : " + typeC.name() + "\n\tCouleur : " + getCouleur() + "\n\tidDressing : " + getIdDressing() + "\n\tidObjet : " + getIdObjet();
    }

}
