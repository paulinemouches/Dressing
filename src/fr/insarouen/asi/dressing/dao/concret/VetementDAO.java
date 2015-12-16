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

    @Override
    public Vetement find(int id) throws SQLException {
        Vetement v = new Vetement();
        String fils = "";
        String type = "";
        String coupe = "";

        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("select p.relname from vetement v, pg_class p where v.tableoid=p.oid and v.idobjet=" + id);

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
            v = new Vetement(id, result.getInt("idDressing"), new Couleur(result.getInt("couleur")), CoupeVetement.get(result.getString(coupe)), TypeVetement.get(result.getString(type)), Matiere.get(result.getString("matiere")), null, result.getInt("couche"), Niveau.get(result.getString("niveau")));
            v.setSignes(v.determinerSignes());
            return v;
        }
        else {
            return null;
        }

    }

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
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO HAUT(idobjet,idDressing,couleur,matiere,sale_propre,typeh,coupeh) VALUES (" + id + ",?,?,?,?,?,?)");
                    break;
                case "Pantalon":
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO PANTALON(idobjet,idDressing,couleur,matiere,sale_propre,typep,coupep) VALUES (" + id + ",?,?,?,?,?,?)");
                    break;
                default:
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO AUTRE(idobjet,idDressing,couleur,matiere,sale_propre,typea,coupea) VALUES (" + id + ",?,?,?,?,?,?)");
                    break;
            }

            prepare.setInt(1, obj.getIdDressing());
            prepare.setInt(2, obj.getCouleur().getCouleur());
            prepare.setString(3, obj.getMatiere().name());
            prepare.setBoolean(4, obj.isSale());
            prepare.setString(5, obj.getType().name());
            prepare.setString(6, obj.getCoupe().name());
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

    @Override
    public boolean delete(Vetement obj) throws SQLException {
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        st.executeUpdate("DELETE  FROM CONTENU WHERE idObjet =" + obj.getIdObjet());
        return true;
    }

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

    public static int recupererCouche(Vetement obj) throws SQLException {
        int resultat = 0;
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT couche FROM " + obj.getFils() + " WHERE idObjet =" + obj.getIdObjet());
        if (res.first()) {
            resultat = res.getInt("couche");
        }
        return resultat;
    }

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

    public static boolean recupererSalePropre(Vetement obj) throws SQLException{
         boolean resultat = false;
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT sale_propre FROM   VETEMENT  WHERE idObjet =" + obj.getIdObjet());
        if (res.first()) {
            resultat = res.getBoolean("sale_propre");
        }
        return resultat;
        
    }
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

    public static void afficherVetements() {
        try {
            Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sts.executeQuery("SELECT idObjet FROM Vetement");
            ResultSet ress = sts.getResultSet();
            while (ress.next()) {
                Vetement v = new Vetement();
                System.out.println((v.trouverVetement(ress.getInt("idobjet"))).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, Vetement> initialiserVetements(int id) {
        try {
            HashMap<Integer, Vetement> vetements = new HashMap<Integer, Vetement>();
            Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sts.executeQuery("SELECT idObjet FROM Vetement WHERE idDressing=" + id);
            ResultSet ress = sts.getResultSet();
            while (ress.next()) {
                Vetement v = new Vetement();
                vetements.put(ress.getInt("idObjet"), v.trouverVetement(ress.getInt("idobjet")));
            }
            return vetements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void modifierSalePropreBD(int id, boolean sale) {
        try {
            Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sts.executeUpdate("UPDATE vetement SET sale_propre = "+sale+" WHERE idObjet ="+ id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
