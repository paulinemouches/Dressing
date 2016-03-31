/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao.concret;

import fr.insarouen.asi.dressing.dao.DAO;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import fr.insarouen.asi.dressing.Initialisation;
import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.CoupeVetement;
import fr.insarouen.asi.dressing.elements.Matiere;
import fr.insarouen.asi.dressing.elements.Niveau;
import fr.insarouen.asi.dressing.elements.Signe;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author julie
 */
public class VetementDAO extends DAO<Vetement> {

    public VetementDAO() {
    }

    /**
     *Permet de trouver un vêtement dans la base 
     * @param id identifiant du vêtement à trouver
     * @param idDressing id du dressing auquel le vêtement appartient
     * @return objet java vêtement correspondant au vêtement de la base
     */
    @Override
    public Vetement find(int id, int idDressing) throws SQLException {
        Vetement v = new Vetement();
        String fils = "";
        String type = "";
        String coupe = "";

        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("select p.relname from vetement v, pg_class p where v.tableoid=p.oid and v.idobjet=" + id+" and v.iddressing="+idDressing);

        if (res.first()) {
            fils = res.getString("relname");
        }

        switch (fils) {
            case "haut":
                type = "typeH";
                coupe = "coupeH";
                break;
            case "pantalon":
                type = "typeP";
                coupe = "coupeP";
                break;
            case "autre":
                type = "typeA";
                coupe = "coupeA";
                break;
            default:
                System.out.println("Erreur : Ce n'est ni un haut, ni un pantalon, ni un autre ... ");
                return null;
        }

        Statement stat = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stat.executeQuery("SELECT * FROM " + fils + " WHERE idObjet = " + id);
        ResultSet result = stat.getResultSet();

        if (result.first()) {
            v = new Vetement(id, result.getInt("idDressing"), new Couleur(result.getInt("couleur")), CoupeVetement.get(result.getString(coupe)), TypeVetement.get(result.getString(type)), Matiere.get(result.getString("matiere")), null, result.getInt("couche"), Niveau.get(result.getString("niveau")),result.getString("image"));
            v.setSignes(v.determinerSignes());
            return v;
        }
        else {
            return null;
        }

    }

    /**
     *Permet d'ajouter un vêtement à la base à partir d'un objet Vetement java
     * @param obj Objet Vetement java
     */

    @Override
    public boolean create(Vetement obj) throws SQLException {
        try {
            int id = 0;
            Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.executeQuery("SELECT MAX(idObjet) FROM CONTENU");
            ResultSet res = st.getResultSet();
            while (res.next()) {                          //recupère le max de l'id puis +1 pour notre nouvel id
                id = res.getInt(1) + 1;
            }
            // on attribut cet id à l'objet : 
            obj.setIdObjet(id);
            PreparedStatement prepare;
            switch (obj.getFils()) {
                case "Haut":
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO HAUT(idobjet,idDressing,couleur,matiere,sale_propre,typeh,coupeh,image) VALUES (" + id + ",?,?,?,?,?,?,?)");
                    break;
                case "Pantalon":
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO PANTALON(idobjet,idDressing,couleur,matiere,sale_propre,typep,coupep,image) VALUES (" + id + ",?,?,?,?,?,?,?)");
                    break;
                default:
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO AUTRE(idobjet,idDressing,couleur,matiere,sale_propre,typea,coupea,image) VALUES (" + id + ",?,?,?,?,?,?,?)");
                    break;
            }

            prepare.setInt(1, obj.getIdDressing());
            prepare.setInt(2, obj.getCouleur().getCouleur());
            prepare.setString(3, obj.getMatiere().name());
            prepare.setBoolean(4, obj.isSale());
            prepare.setString(5, obj.getType().name());
            prepare.setString(6, obj.getCoupe().name());
            prepare.setString(7, obj.getImage());
            prepare.executeUpdate();
            //obj = this.find(id); // Ne sert visiblement a rien mais je laisse au cas ou

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // inutile car pas de fonctionnalité permettant de modifier un utilisateur
    @Override
    public boolean update(Vetement obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *Permet de supprimer un vêtement dans la base à partir d'un objet Vetement java
     * @param obj objet java à supprimer 
     */
    
    @Override
    public boolean delete(Vetement obj) throws SQLException {
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        st.executeUpdate("DELETE  FROM CONTENU WHERE idObjet =" + obj.getIdObjet());
        return true;
    }

    /**
     *Permet de recuperer la valeur de l'attribut niveau correspondant à un vetement stocké dans la base
     * @param obj objet Vetement java dont on veut le niveau
     */
    
    public static Niveau recupererNiveau(Vetement obj) throws SQLException {
        Niveau resultat = null;
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT niveau FROM " + obj.getFils() + " WHERE idObjet =" + obj.getIdObjet());
        ResultSet res = st.getResultSet();
        if (res.first()) {
            resultat = Niveau.get(res.getString("niveau"));
        }
        return resultat;
    }
    
    /**
     *Permet de récupérer la valeur de l'attribut couche correspondant à un vêtement stocké dans la base
     * @param obj objet Vetement java dont on veut la couche
     */
    public static int recupererCouche(Vetement obj) throws SQLException {
        int resultat = 0;
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT couche FROM " + obj.getFils() + " WHERE idObjet =" + obj.getIdObjet());
        if (res.first()) {
            resultat = res.getInt("couche");
        }
        return resultat;
    }

        /**
     *Permet de recuperer les valeurs des signes  correspondant à un vêtement stocké dans la base
     * @param obj objet Vetement java dont on veut les signes
     */
    
    public static Signe[] recupererSignes(Vetement obj) throws SQLException {
        Signe[] resultat = new Signe[15];
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT c.signe FROM VETEMENT v, CORRESPOND c WHERE v.idobjet=c.idobjet and v.idobjet=" + obj.getIdObjet());
        ResultSet res = st.getResultSet();
        List rowValues = new ArrayList();
        while (res.next()) {
            rowValues.add(Signe.get(res.getString(1)));
        }
        resultat = (Signe[]) rowValues.toArray(new Signe[rowValues.size()]);
        return resultat;
    }
    
        /**
     *Permet de recuperer la valeur de l'attribut salePropre correspondant à un vêtement stocké dans la base
     * @param obj objet Vetement java dont on veut l'attribut salePropre
     */

    public static boolean recupererSalePropre(Vetement obj) throws SQLException{
         boolean resultat = false;
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT sale_propre FROM   VETEMENT  WHERE idObjet =" + obj.getIdObjet());
        if (res.first()) {
            resultat = res.getBoolean("sale_propre");
        }
        return resultat;
        
    }

        /**
     *Permet de recuperer les vetements correspondant à la saison courante
     * @param idDressing Id du dressing
     */
    
    public static HashMap<Integer, Vetement> recupererVetementsSaison(int idDressing) throws SQLException{
        HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT idObjet FROM vetementsaison WHERE idDressing="+idDressing);
        while (res.next()) {
            Vetement v = new Vetement();
            vetements.put(res.getInt("idobjet"),v.trouverVetement(res.getInt("idobjet"), idDressing));
        }
        return vetements;
    }
    
    public static HashMap<Integer, Vetement> recupererVetementsCouleurPreferee(int idDressing) throws SQLException{
        HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT idObjet FROM vetementcouleurpreferee WHERE idDressing="+idDressing);
        while (res.next()) {
            Vetement v = new Vetement();
            vetements.put(res.getInt("idobjet"),v.trouverVetement(res.getInt("idobjet"),idDressing));
        }
        return vetements;
    }

        /**
     *Permet de recuperer tous les vêtements correspondants à un signe
     * @param idDressing dressing dans lequel on veut récupérer les vêtements
     * @param signe signe auquel on veut que les vêtements récupérés correspondent
     * @return tableau contenant les vêtements désirés  
     */
    
    public static HashMap<Integer, Vetement> recupererVetementsForme(int idDressing, Signe signe) throws SQLException{
        HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT v.idObjet FROM vetement v, correspond c WHERE c.signe='"+signe+"' AND c.idobjet=v.idobjet AND v.idDressing="+idDressing);
        while (res.next()) {
            Vetement v = new Vetement();
            vetements.put(res.getInt("idobjet"),v.trouverVetement(res.getInt("idobjet"),idDressing));
        }
        return vetements;
    }

            /**
     *Permet de recuperer tous les vêtements correspondants à un type
     * @param idDressing dressing dans lequel on veut récupérer les vêtements
     * @param type type auquel on veut que les vêtements récupérés correspondent
     * @return tableau contenant les vêtements désirés  
     */
    
    public static HashMap<Integer, Vetement> recupererVetementsType(int idDressing, int type) throws SQLException{
        HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res= null;
        do{
            switch(type){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    res = st.executeQuery("SELECT idObjet FROM HAUT WHERE typeh='"+TypeVetement.getfromInt(type)+"' AND idDressing="+idDressing);
                    break;
                case 6:
                case 7:
                case 8:
                    res = st.executeQuery("SELECT idObjet FROM PANTALON WHERE typep='"+TypeVetement.getfromInt(type)+"' AND idDressing="+idDressing);
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                    res = st.executeQuery("SELECT idObjet FROM AUTRE WHERE typea='"+TypeVetement.getfromInt(type)+"' AND idDressing="+idDressing);
                    break;
                default:
                    System.out.println("Veuillez Saisir un chiffre entre 1 et 12");
            }
        }while(type>12);
        
        while (res.next()) {
            Vetement v = new Vetement();
            vetements.put(res.getInt("idobjet"),v.trouverVetement(res.getInt("idobjet"),idDressing));
        }
        return vetements;
    }
    
            /**
     *Permet de recupererla saison correspondante à un vêtement
     * @param v vêtement dont on veut connaitre la saison
     * @return saison  
     */
    
    public static String recupererSaison(Vetement v) {
        try {
            Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.executeQuery("SELECT saison  FROM MATIERE_SAISON  WHERE matiere = '" + v.getMatiere() + "'");
            ResultSet res = st.getResultSet();
            if (res.first()) {
                return res.getString("saison");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
    * Permet de récupérer tous les vêtements d'un dressing
    * @param id id du dressing
    * @return Tableau de vêtements
    */
    public static HashMap<Integer, Vetement> initialiserVetements(int id) {
        try {
            HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
            Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sts.executeQuery("SELECT idObjet FROM Vetement WHERE idDressing=" + id);
            ResultSet ress = sts.getResultSet();
            while (ress.next()) {
                Vetement v = new Vetement();
                vetements.put(ress.getInt("idObjet"), v.trouverVetement(ress.getInt("idobjet"),id));
            }
            return vetements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *Permet de modifier l'attribut salePropre d'un vêtement dans la base de données
     * @param id id du vêtement à modifier
     * @param sale vaut VRAI si le vêtement est à mettre au sale, FAUX sinon
     */
    
    public static void modifierSalePropreBD(int id, boolean sale) {
        try {
            Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sts.executeUpdate("UPDATE vetement SET sale_propre = "+sale+" WHERE idObjet ="+ id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
