/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao.concret;

import fr.insarouen.asi.dressing.dao.DAO;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.Initialisation;
import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author pauline
 */
public class ChaussuresDAO extends DAO<Chaussures> {
     
    @Override
     public  boolean create(Chaussures obj) {
        try{
        int id =0;
        Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT MAX(idObjet) FROM Contenu");   
        ResultSet res = st.getResultSet(); 
        while ( res.next() ){                          //recupère le max de l'id puis +1 pour notre nouvel id
            id = res.getInt(1) +1;
        }
        PreparedStatement prepare = Initialisation.getC().prepareStatement("INSERT INTO CHAUSSURE(idObjet,idDressing,couleur, typec) VALUES ("+id+",?,?,?)");
        prepare.setInt(1,  obj.getIdDressing());
        prepare.setInt(2,  obj.getCouleur().getCouleur()); 
        prepare.setString(3,  obj.getTypeC().name());
        prepare.executeUpdate();
       // obj = this.find(id); // Ne sert visiblement a rien mais je laisse au cas ou
                obj.setIdObjet(id);


        }catch(SQLException e){
             e.printStackTrace();
        }
                return true;
    }
    
 @Override
     public  boolean update (Chaussures obj){ return false;} // inutile car pas de fonctionnalité permettant de modifier un sac
     
         @Override
    public  boolean delete(Chaussures obj) throws SQLException{
    Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate("DELETE  FROM CHAUSSURE WHERE idObjet ="+obj.getIdObjet()); 
            return true;
    }
    
        @Override
    public Chaussures  find(int id) throws SQLException{
        
       Chaussures c = new Chaussures();
            ResultSet res = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM CHAUSSURE WHERE idObjet = "+id);
            
            if(res.first()){
                c = new Chaussures(id,res.getInt("idDressing"),TypeChaussures.get(res.getString("typec")),new Couleur(res.getInt("couleur")));
                return c;
            }else{
                return null;
            }

    }  
    
    public static void afficherChaussures(){
        try{
            Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sts.executeQuery("SELECT idObjet FROM Chaussure");
            ResultSet ress = sts.getResultSet();
            while (ress.next()) {
                Chaussures c = new Chaussures();
                System.out.println((c.trouverChaussures(ress.getInt("idobjet"))).toString());
            }
        }catch(SQLException e){
             e.printStackTrace();
        }
    }
    
        public static HashMap<Integer,Chaussures> initChaussures(int id){
             HashMap<Integer,Chaussures>  chaussures = new  HashMap<Integer,Chaussures>();
        try{
            Statement sts = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            sts.executeQuery("SELECT c.idObjet FROM Chaussure c WHERE c.idDressing="+id);
            ResultSet ress = sts.getResultSet();
            while (ress.next()) {
                Chaussures c = new Chaussures();
                chaussures.put(ress.getInt("idobjet"),c.trouverChaussures(ress.getInt("idobjet")));   
            }
            return chaussures;
        }catch(SQLException e){
             e.printStackTrace();
        }
        return null;
    }
}
