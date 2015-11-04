/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao.concret;

import fr.insarouen.asi.dressing.dao.DAO;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import fr.insarouen.asi.dressing.Initialisation;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.CoupeVetement;
import fr.insarouen.asi.dressing.elements.Matiere;
import fr.insarouen.asi.dressing.elements.Niveau;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Array; 
import java.util.*;


/**
 *
 * @author julie
 */
public class VetementDAO extends DAO<Vetement>{
    
    public VetementDAO(){}

    @Override
    public Vetement find(int id) throws SQLException {
        Vetement v = new Vetement();
        String fils="";
        Statement st= Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet res=st.executeQuery("select p.relname from vetement v, pg_class p where v.tableoid=p.oid and v.idobjet="+id);
        
        if(res.first()){
            fils=res.getString("relname");
        }
        
        
        Statement stat =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stat.executeQuery("SELECT * FROM "+ fils+" WHERE idObjet = "+id);
        ResultSet result = stat.getResultSet();
        
        if(result.first()){
            v = new Vetement(id,result.getInt("idDressing"), result.getString("couleur"), CoupeVetement.get(result.getString("coupeH")), TypeVetement.get(result.getString("typeH")), Matiere.get(result.getString("matiere")), v.determinerSignes(),v.determinerCouche(), v.determinerNiveau());
        }
        
        /*Statement stH =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stH.executeQuery("SELECT * FROM HAUT WHERE idObjet = "+id);
        ResultSet resH = stH.getResultSet();
        
        Statement stP =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stP.executeQuery("SELECT * FROM PANTALON WHERE idObjet = "+id);
        ResultSet resP = stP.getResultSet();
        
        Statement stA =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stA.executeQuery("SELECT * FROM AUTRE WHERE idObjet = "+id);
        ResultSet resA = stA.getResultSet();
        
        if(resH.first()){
            String fils = v.determinerFils(TypeVetement.get(resH.getString("typeH")));
            v = new Vetement(id,resH.getInt("idDress"), resH.getString("couleur"), CoupeVetement.get(resH.getString("coupeH")), TypeVetement.get(resH.getString("typeH")), Matiere.get(resH.getString("matiere")), v.determinerSignes( CoupeVetement.get(resH.getString("coupeH"))),v.determinerCouche(TypeVetement.get(resH.getString("typeH"))), v.determinerNiveau(fils,TypeVetement.get(resH.getString("typeH"))));
        }else if(resP.first()){
            String fils = v.determinerFils(TypeVetement.get(resP.getString("typeP")));
            v = new Vetement(id,resP.getInt("idDress"), resP.getString("couleur"), CoupeVetement.get(resP.getString("coupeP")), TypeVetement.get(resP.getString("typeP")), Matiere.get(resP.getString("matiere")), v.determinerSignes( CoupeVetement.get(resP.getString("coupeP"))),v.determinerCouche(TypeVetement.get(resH.getString("typeP"))), v.determinerNiveau(fils,TypeVetement.get(resP.getString("typeP"))));
        }else if (resA.first()){
            String fils = v.determinerFils(TypeVetement.get(resA.getString("typeA")));
            v = new Vetement(id,resA.getInt("idDress"), resA.getString("couleur"), CoupeVetement.get(resA.getString("coupeA")), TypeVetement.get(resA.getString("typeA")), Matiere.get(resA.getString("matiere")), v.determinerSignes( CoupeVetement.get(resA.getString("coupeA"))),v.determinerCouche(TypeVetement.get(resA.getString("typeA"))), v.determinerNiveau(fils,TypeVetement.get(resA.getString("typeA"))));
        }*/
        return v;
    }

    @Override
    public boolean create(Vetement obj) throws SQLException {
        try{
            int id =0;
            Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st.executeQuery("SELECT MAX(idObjet) FROM CONTENU");   
            ResultSet res = st.getResultSet(); 
            while ( res.next() ){                          //recupère le max de l'id puis +1 pour notre nouvel id
                          id = res.getInt(1) +1;
            }
            // on attribut cet id à l'objet : 
            obj.setIdV(id);
            PreparedStatement prepare;
            switch(obj.getFils()){
                case "Haut": 
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO HAUT(idobjet,idDressing,couleur,matiere,sale_propre,typeh,coupeh) VALUES ("+id+",?,?,?,?,?,?)");
                    break;
                case "Pantalon":
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO PANTALON(idobjet,idDressing,couleur,matiere,sale_propre,typep,coupep) VALUES ("+id+",?,?,?,?,?,?)");
                    break;
                default :
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO AUTRE(idobjet,idDressing,couleur,matiere,sale_propre,typea,coupea) VALUES ("+id+",?,?,?,?,?,?)");
                    break;
            }
            
            prepare.setInt(1, obj.getIdDressing()); 
            prepare.setString(2, obj.getCouleur());
            prepare.setString(3, obj.getMatiere().name());
            prepare.setBoolean(4, obj.isSale());
            prepare.setString(5, obj.getType().name());
            prepare.setString(6, obj.getCoupe().name());
            prepare.executeUpdate();
            //obj = this.find(id); // Ne sert visiblement a rien mais je laisse au cas ou

        }catch(SQLException e){
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
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate("DELETE  FROM CONTENU WHERE idObjet ="+obj.getIdV()); 
            return true;
    }
    
    
    public static Niveau recupererNiveau(Vetement obj) throws SQLException{
        Niveau resultat=null;
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT niveau FROM "+obj.getFils()+" WHERE idObjet ="+obj.getIdV());
        ResultSet res = st.getResultSet();
        if(res.first()){
            resultat = Niveau.get(res.getString("niveau"));
        }
        return resultat;
    }
    
    public static int recupererCouche(Vetement obj) throws SQLException{
        int resultat=0;
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet res = st.executeQuery("SELECT couche FROM "+obj.getFils()+" WHERE idObjet ="+obj.getIdV());
        if(res.first()){
            resultat = res.getInt("couche");
        }
        return resultat;
    }
    
    public static String[] recupererSignes(Vetement obj) throws SQLException{
        String[] resultat= new String[15];
        Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT c.signe FROM VETEMENT v, CORRESPOND c WHERE v.idobjet=c.idobjet and v.idobjet="+obj.getIdV());   
        ResultSet res = st.getResultSet();
        List rowValues = new ArrayList();
        while (res.next()) {
            rowValues.add(res.getString(1));
        }
        resultat = (String[]) rowValues.toArray(new String[rowValues.size()]);
        return resultat;
    }
    
    
    public static String recupererSaison(Vetement v){
        try{
        Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT saison  FROM MATIERE_SAISON  WHERE matiere = "+v.getMatiere());
        ResultSet res = st.getResultSet();
        return res.getString("saison");
        }
        catch(SQLException e){
             e.printStackTrace();
        }
        return null;
    }
    
}
