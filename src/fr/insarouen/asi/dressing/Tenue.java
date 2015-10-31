package fr.insarouen.asi.dressing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import java.util.Date;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.Matiere;
import fr.insarouen.asi.dressing.elements.Niveau;
import fr.insarouen.asi.dressing.elements.Signe;
import fr.insarouen.asi.dressing.elements.CoupeVetement;

public class Tenue {

    public Tenue() {
    }
    

    public String determinerSaison(){
        Date date = new Date();
        int mois = date.getMonth();
        int jour = date.getDay();
        if((mois>=2 && jour>=21)||(mois<=8 && jour<=21)){
            return "printemps/ete";
        }
        else{
            return "automne/hiver";
        }
        }
        
        
    } 
