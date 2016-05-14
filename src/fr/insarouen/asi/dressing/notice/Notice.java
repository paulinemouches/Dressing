/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.notice;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauline
 */
public class Notice {

    private String notice = "<html>";
    private String fichier;

    public Notice(String fichier) {
        this.fichier = fichier;
        initNotice(fichier);
    }

    public String getNotice() {
        return notice;
    }
    

    private String initNotice(String fichier) {
        InputStream ips;
        try {
            ips = new FileInputStream(fichier);

            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;

            while ((ligne = br.readLine()).length() > 0) {
                notice += ligne+"<br>";
            }
            notice += "</html>";
        } catch (IOException ex) {
           notice+="Pas de notice</html>";
        }
        return notice;
    }

}
