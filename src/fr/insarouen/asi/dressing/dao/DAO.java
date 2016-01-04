/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.dao;
import java.sql.SQLException;

/**
 *
 * @author pauline
 */
public abstract class DAO<T> {
    
    public  abstract T find(int id, int idDressing)throws SQLException;
    
    public abstract boolean create(T obj) throws SQLException;
    
    public abstract boolean update(T obj);
    
    public abstract boolean delete(T obj) throws SQLException;
    
}
    
    

