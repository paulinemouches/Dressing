/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao.concret;

import fr.insarouen.asi.dressing.dao.DAO;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import fr.insarouen.asi.dressing.Initialisation;
import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.CouleurCheveux;
import fr.insarouen.asi.dressing.elements.Signe;
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

        /**
     *Permet d'ajouter un utilisateur à la base à partir d'un objet Utilisateur java
     * @param obj Objet Utilisateur java
     */
    
    @Override
    public  boolean create(Utilisateur obj) {
        try{
            int id =0;
        Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
         st.executeQuery("SELECT MAX(idPers) FROM PERSONNE");   
         ResultSet res = st.getResultSet(); 
         while ( res.next() ){                          //recupère le max de l'id puis +1 pour notre nouvel id
                          id = res.getInt(1) +1;
        }
         PreparedStatement prepare = Initialisation.getC().prepareStatement("INSERT INTO PERSONNE(idPers,nom,prenom, age, taille, couleurCheveux, couleurPreferee, signe) VALUES ("+id+",?,?,?,?,?,?,?)");
        prepare.setString(1,  obj.getNom()); 
        prepare.setString(2,  obj.getPrenom());
        prepare.setInt(3,  obj.getAge());
        prepare.setInt(4,  obj.getTaille());
        prepare.setString(5 , obj.getCouleurCheveux().name());
        prepare.setInt(6,  obj.getCouleurPreferee().getCouleur());
        prepare.setString(7,  obj.getSigneUtilisateur().name());
        prepare.executeUpdate();
        //obj = this.find(id); // Ne sert visiblement a rien mais je laisse au cas ou
        obj.setId(id);

        }catch(SQLException e){
             e.printStackTrace();
        }
                return true;
    }
    
    
    @Override
     public  boolean update (Utilisateur obj){ return false;} // inutile car pas de fonctionnalité permettant de modifier un utilisateur

        /**
     *Permet de supprimer un utilisateur dans la base à partir d'un objet Utilisateur java
     * @param obj objet java à supprimer 
     */
    
    @Override
    public  boolean delete(Utilisateur obj) throws SQLException{
    Statement st = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate("DELETE  FROM PERSONNE WHERE idPers ="+obj.getId()); 
            return true;
    }

        /**
     *Permet de trouver un utilisateur dans la base 
     * @param id identifiant de l'utilisateur à trouver
     * @param idDressing id du dressing de l'utilisateur
     * @return objet java utilisateur correspondant à l'utilisateur de la base
     */
    
    @Override
    public Utilisateur  find(int id, int idDressing) throws SQLException{
        
       Utilisateur u = new Utilisateur();
            ResultSet res = Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM PERSONNE WHERE idPers = "+id);
            
            if(res.first()){
                u = new Utilisateur(id,res.getString("nom"),res.getString("prenom"),res.getInt("age"),res.getInt("taille"),new Couleur(res.getInt("couleurPreferee")),CouleurCheveux.get(res.getString("couleurCheveux")),Signe.get(res.getString("signe")));
                return u;
            }else{
                return null;
            }

    }
    
    public static int obtenirIdUtilisateur(int idDressing) throws SQLException{
        Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT idPers FROM DRESSING WHERE idDressing="+idDressing);   
        ResultSet res = st.getResultSet(); 
        if(res.first()){
            return res.getInt("idpers");
        }else{
            return 0;
        }
    }

    /**
     *Permet l'id du dressing d'un utilisateur
     * @param idUtilisateur id de l'utilisateur dont on veut connaitre le dressing
     * @return id du dressing correspondant
     */
    
    public static int obtenirIdDressing(int idUtilisateur) throws SQLException{
        Statement st =  Initialisation.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        st.executeQuery("SELECT idDressing FROM DRESSING WHERE idPers="+idUtilisateur);   
        ResultSet res = st.getResultSet(); 
        if(res.first()){
            return res.getInt("iddressing");
        }else{
            return 0;
        }
    }
    
}
