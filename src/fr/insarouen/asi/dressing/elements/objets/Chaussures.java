package fr.insarouen.asi.dressing.elements.objets;

import java.sql.SQLException;
import java.util.Scanner;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import fr.insarouen.asi.dressing.dao.concret.ChaussuresDAO;
import java.util.HashMap;

public class Chaussures {

   TypeChaussures typeC;
    private String couleur;
    private int idDressing;
    private int idObjet;
    private static HashMap<Integer,Chaussures>  chaussures =  new  HashMap<Integer,Chaussures>();

    public Chaussures() {
    }

    public Chaussures(int idObjet,int idDressing,  TypeChaussures typeC, String couleur) {
        this.typeC = typeC;
        this.couleur = couleur;
        this.idDressing = idDressing;
        this.idObjet = idObjet;
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

    public String getCouleur() {
        return couleur;
    }

    public int getIdDressing() {
        return idDressing;
    }

    public int getIdObjet() {
        return idObjet;
    }

    public void setTypeC(TypeChaussures typeC) {
        this.typeC = typeC;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }
    
    
        public static void afficherChaussures() {
        if(!chaussures.isEmpty()){
        for ( Chaussures  c: chaussures.values()) {
            System.out.println(c.toString());
        }
        }
        else{
            System.out.println("\nIl n'y a pas de chaussuress");
        }
    }
        public Chaussures menuAjouterChaussuresTxt() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le type de chaussures");
        TypeChaussures typeC = TypeChaussures.get(sc.nextLine());

        System.out.println("Entrez la couleur des chaussures");
        String couleur = sc.nextLine();

        Chaussures c = new Chaussures();
        c.setTypeC(typeC);
        c.setCouleur(couleur);
        return c;
    }
public void ajouterChaussuresDansListe(){
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
    
public void supprimerChaussuresDansListe(int id){
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
        }
        else {
            return false;
        }

    }
    
    public static void initialiserChaussures(){
        chaussures = ChaussuresDAO.initChaussures();
    }


    public Chaussures trouverChaussures(int id) throws SQLException{
        ChaussuresDAO c = new ChaussuresDAO();
        return c.find(id);  
    }
    
    @Override
    public String toString() {
        return "\nChaussures:\n\t" + "\n\tType : " + typeC.name() + "\n\tCouleur : " + couleur + "\n\tidDressing : " + idDressing + "\n\tidObjet : " + idObjet;
    }

}
