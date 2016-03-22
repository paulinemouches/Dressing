/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.frames;

import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Contenu;
import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author pauline
 */
public class jListMouseListener implements MouseListener {
    
    private JList jl;
    public int index;
    ArrayList<Contenu> contenus;
    JPanel panelAffichage;
    
    public jListMouseListener(JList jl, ArrayList<Contenu> contenus, JPanel panelAffichage){
    super();
    this.jl = jl;
    this.contenus = contenus;
    this.panelAffichage = panelAffichage;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        jl = (JList) e.getSource();
        if(e.getClickCount() == 2){
            index = jl.locationToIndex(e.getPoint());
            System.out.println(index);
            blabla(index);
        }      
       }

    public void blabla(int index){

        Component[] composants = panelAffichage.getComponents();
        JLabel photo = (JLabel)composants[0];
        JLabel caracteristique1 = (JLabel) composants[2];
        JLabel caracteristique2 = (JLabel) composants[3];
        JLabel caracteristique3 = (JLabel) composants[4];
        JLabel caracteristique4 = (JLabel) composants[5];        
        
        photo.setIcon(new ImageIcon(new ImageIcon("images/" + contenus.get(index).getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        
        if(contenus.get(index) instanceof Sac){
            Sac s = (Sac) contenus.get(index);
            caracteristique1.setText(s.getTypeS().toString());
            caracteristique2.setText(String.valueOf(s.getCouleur().getCouleur()));
        }
        if(contenus.get(index) instanceof Chaussures){
            Chaussures c = ( Chaussures) contenus.get(index);
            caracteristique1.setText(c.getTypeC().toString());
            caracteristique2.setText(String.valueOf(c.getCouleur().getCouleur()));
        }
        if(contenus.get(index) instanceof Vetement){
            Vetement v = (Vetement) contenus.get(index);
            caracteristique1.setText(v.getType().toString());
            caracteristique2.setText(String.valueOf(v.getCouleur().getCouleur()));
            caracteristique3.setText(v.getCoupe().toString());
            caracteristique4.setText(v.getMatiere().toString());
            
        }
         
        
        
        CardLayout card = (CardLayout)InitFrame.MainFrame.getLayout();
        card.show(InitFrame.MainFrame, "AffichageObjet");
    
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
