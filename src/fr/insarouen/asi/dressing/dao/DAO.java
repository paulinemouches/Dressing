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
    
    public abstract T find(int id)throws SQLException;
    
    public abstract T create(T obj) throws SQLException;
    
    public abstract T update(T obj);
    
    public abstract void delete(T obj) throws SQLException;
    
}
    
    

