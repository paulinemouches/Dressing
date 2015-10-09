/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao.concret;

import fr.insarouen.asi.dressing.dao.DAO;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import fr.insarouen.asi.dressing.Initialisation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author pauline
 */
public class UtilisateurDAO extends DAO<Utilisateur>{
    
    public UtilisateurDAO(){}
    
    @Override
    public  Utilisateur create(Utilisateur obj) {
        try{
            int id =0;
        Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
         st.executeQuery("SELECT MAX(idPers) FROM PERSONNE");   
         ResultSet res = st.getResultSet(); 
         while ( res.next() ){                          //recupère le max de l'id puis +1 pour notre nouvel id
                          id = res.getInt(1) +1;
        }
         System.out.println(id);
         PreparedStatement prepare = Initialisation.getC().prepareStatement("INSERT INTO PERSONNE(idPers,nom,prenom, age, taille, couleurCheveux, couleurPreferee, signe) VALUES ("+id+",?,?,?,?,?,?,?)");
        prepare.setString(1,  obj.getNom()); 
        prepare.setString(2,  obj.getPrenom());
        prepare.setInt(3,  obj.getAge());
        prepare.setInt(4,  obj.getTaille());
        prepare.setString(5 , obj.getCouleurCheveux());
        prepare.setString(6,  obj.getCouleurPreferee());
        prepare.setString(7,  obj.getSigneUtilisateur());
        prepare.executeUpdate();
        obj = this.find(id); // Ne sert visiblement a rien mais je laisse au cas ou

        }catch(SQLException e){
             e.printStackTrace();
        }
                return obj;
    }
    
    
    @Override
     public  Utilisateur update (Utilisateur obj){ return null;} // inutile car pas de fonctionnalité permettant de modifier un utilisateur
    
    @Override
    public  void delete(Utilisateur obj) throws SQLException{
    Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate("DELETE  FROM PERSONNE WHERE idPers ="+obj.getId()); 
    }
    
    @Override
    public Utilisateur  find(int id) throws SQLException{
        
       Utilisateur u = new Utilisateur();
            ResultSet res = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM PERSONNE WHERE idPers = "+id);
            
            if(res.first()){
                u = new Utilisateur(id,res.getString("nom"),res.getString("prenom"),res.getInt("age"),res.getInt("taille"),res.getString("couleurCheveux"),res.getString("couleurPreferee"),res.getString("signe"));
           }
return u;
    }
}