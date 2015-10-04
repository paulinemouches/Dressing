package fr.insarouen.asi.dressing.elements.utilisateurs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import fr.insarouen.asi.dressing.elements.CouleurCheveux;
import fr.insarouen.asi.dressing.elements.Signe;

public class Utilisateur {

    private String nom;
    private String prenom;
    private int age;
    private int taille;
    private String couleurPreferee;
    CouleurCheveux couleurCheveux;
    Signe signeUtilisateur;

}
