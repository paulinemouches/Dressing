/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.conseil;

import fr.insarouen.asi.dressing.elements.Signe;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauline
 */
public class Conseil {

    private int id;
    private String description;
    private Signe signe;
    private static ArrayList<Conseil> conseilsHuit = new ArrayList<Conseil>();
    private static ArrayList<Conseil> conseilsA = new ArrayList<Conseil>();
    private static ArrayList<Conseil> conseilsO = new ArrayList<Conseil>();
    private static ArrayList<Conseil> conseilsH= new ArrayList<Conseil>();
    private static ArrayList<Conseil> conseilsV= new ArrayList<Conseil>();
    private static ArrayList<Conseil> conseilsX= new ArrayList<Conseil>();

    public Conseil(int id, Signe signe, String description) {
        this.id = id;
        this.signe = signe;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Signe getSigne() {
        return signe;
    }

    public static ArrayList<Conseil> getConseilsHuit() {
        return conseilsHuit;
    }

    public static ArrayList<Conseil> getConseilsA() {
        return conseilsA;
    }

    public static ArrayList<Conseil> getConseilsO() {
        return conseilsO;
    }

    public static ArrayList<Conseil> getConseilsH() {
        return conseilsH;
    }

    public static ArrayList<Conseil> getConseilsV() {
        return conseilsV;
    }

    public static ArrayList<Conseil> getConseilsX() {
        return conseilsX;
    }

    public static void initialiserConseils() throws FileNotFoundException {
        InputStream ips = new FileInputStream("conseils.txt");
        InputStreamReader ipsr = new InputStreamReader(ips);
        BufferedReader br = new BufferedReader(ipsr);
        String ligne;
        try {
            while ((ligne = br.readLine()).length() > 0) {
                String[] parts = ligne.split("-");
                Conseil conseil = new Conseil(Integer.parseInt(parts[0]), Signe.get(parts[1]), parts[2]);
                switch (conseil.getSigne()) {
                    case Huit:
                        conseilsHuit.add(conseil);
                        break;
                    case O:
                        conseilsO.add(conseil);
                        break;
                    case A:
                        conseilsA.add(conseil);
                        break;
                    case V:
                        conseilsV.add(conseil);
                        break;
                    case X:
                        conseilsX.add(conseil);
                        break;
                    case H:
                        conseilsH.add(conseil);
                        break;
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Conseil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return "Conseil{" + "id=" + id + ", description=" + description + ", signe=" + signe + '}';
    }

}
