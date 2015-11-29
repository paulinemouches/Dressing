/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing;

/**
 *
 * @author julie
 */
public class IdNonPresentException extends Exception {
    public IdNonPresentException(){}
    public IdNonPresentException(String message){
	super(message);
    }
}
