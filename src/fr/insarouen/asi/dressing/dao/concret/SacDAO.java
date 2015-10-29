/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao.concret;

import fr.insarouen.asi.dressing.dao.DAO;
import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.Initialisation;
import fr.insarouen.asi.dressing.elements.TypeSac;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author pauline
 */
public class SacDAO extends DAO<Sac> {
    
    @Override
     public  boolean create(Sac obj) {
        try{
            int id =0;
        Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
         st.executeQuery("SELECT MAX(idObjet) FROM Contenu");   
         ResultSet res = st.getResultSet(); 
         while ( res.next() ){                          //recupère le max de l'id puis +1 pour notre nouvel id
                          id = res.getInt(1) +1;
        }
         System.out.println(id);
         PreparedStatement prepare = Initialisation.getC().prepareStatement("INSERT INTO SAC(idObjet,idDressing,couleur, types) VALUES ("+id+",?,?,?)");
       prepare.setInt(1,  obj.getIdDressing());
         prepare.setString(2,  obj.getCouleur()); 
        prepare.setString(3,  obj.getTypeS().name());
        prepare.executeUpdate();
       // obj = this.find(id); // Ne sert visiblement a rien mais je laisse au cas ou

        }catch(SQLException e){
             e.printStackTrace();
        }
                return true;
    }
    
 @Override
     public  boolean update (Sac obj){ return false;} // inutile car pas de fonctionnalité permettant de modifier un sac
     
         @Override
    public  boolean delete(Sac obj) throws SQLException{
    Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate("DELETE  FROM SAC WHERE idObjet ="+obj.getIdObjet()); 
            return true;
    }
    
        @Override
    public Sac  find(int id) throws SQLException{
        
       Sac s = new Sac();
            ResultSet res = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM SAC WHERE idObjet = "+id);
            
            if(res.first()){
                s = new Sac(id,res.getInt("idDressing"),TypeSac.get(res.getString("typeS")),res.getString("couleur"));
                System.out.println("find dao"+res.getString("typeS"));
           }
return s;
    }
     
     
     
    
}
