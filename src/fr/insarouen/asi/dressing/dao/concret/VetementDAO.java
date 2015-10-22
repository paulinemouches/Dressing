/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao.concret;

import fr.insarouen.asi.dressing.dao.DAO;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import fr.insarouen.asi.dressing.Initialisation;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author julie
 */
public class VetementDAO extends DAO<Vetement>{
    
    public VetementDAO(){}

    @Override
    public Vetement find(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            PreparedStatement prepare;
            switch(obj.getFils()){
                case "Haut": 
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO HAUT(idobjet,idDressing,couleur,matiere,couche,niveau,sale_propre,typeh,coupeh) VALUES ("+id+",?,?,?,?,?,?,?,?)");
                    break;
                case "Pantalon":
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO PANTALON(idobjet,idDressing,couleur,matiere,couche,niveau,sale_propre,typep,coupep) VALUES ("+id+",?,?,?,?,?,?,?,?)");
                    break;
                default :
                    prepare = Initialisation.getC().prepareStatement("INSERT INTO AUTRE(idobjet,idDressing,couleur,matiere,couche,niveau,sale_propre,typea,coupea) VALUES ("+id+",?,?,?,?,?,?,?,?)");
                    break;
            }
            
            prepare.setInt(1, obj.getIdDressing()); 
            prepare.setString(2, obj.getCouleur());
            prepare.setString(3, obj.getMatiere());
            prepare.setInt(4, obj.getCouche());
            prepare.setString(5, obj.getNiveau());
            prepare.setBoolean(6, obj.isSale());
            prepare.setString(7, obj.getType());
            prepare.setString(8, obj.getCoupe());
            prepare.executeUpdate();
            //obj = this.find(id); // Ne sert visiblement a rien mais je laisse au cas ou

        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean update(Vetement obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Vetement obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
