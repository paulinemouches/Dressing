package fr.insarouen.asi.dressing.elements.objets;

import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.TypeSac;
import fr.insarouen.asi.dressing.dao.concret.SacDAO;
import java.util.HashMap;

public class Sac {

    private TypeSac typeS;
    private String couleur;
    private int idDressing;
    private int idObjet;
    public static HashMap<Integer, Sac> sacs = new HashMap<Integer, Sac>();

    public Sac(int idObjet, int idDressing, TypeSac typeS, String couleur) {
        this.typeS = typeS;
        this.couleur = couleur;
        this.idObjet = idObjet;
        this.idDressing = idDressing;
    }

    public Sac() {
    }

    public TypeSac getTypeS() {
        return typeS;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getIdDressing() {
        return idDressing;
    }

    public int getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }

    public void setTypeS(TypeSac typeS) {
        this.typeS = typeS;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }

    public Sac menuAjouterSacTxt() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le type de sac");
        TypeSac typeS = TypeSac.get(sc.nextLine());

        System.out.println("Entrez la couleur du sac");
        String couleur = sc.nextLine();

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
    
    public static void initSacs(){
        sacs=SacDAO.initialiserSacs();
    }

    public static void afficherSacs() {
        if(!sacs.isEmpty()){
        for (Sac s : sacs.values()) {
            System.out.println(s.toString());
        }
        }
        else{
            System.out.println("\nIl n'y a pas de sacs");
        }
    }

    public Sac trouverSac(int id) throws SQLException {
        SacDAO s = new SacDAO();
        return s.find(id);
    }

    @Override
    public String toString() {
        return "Sac:\n\t" + "\n\tType : " + typeS + "\n\tCouleur : " + couleur + "\n\tidDressing : " + idDressing + "\n\tidObjet : " + idObjet;
    }

}
