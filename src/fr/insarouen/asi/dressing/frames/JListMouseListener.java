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
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author pauline
 */
public class JListMouseListener implements MouseListener {

    private JList jl;
    private int indexClic;
    JPanel panelAffichage;
    JDialog jd;
    JLabel photo, caracteristique1, caracteristique2, caracteristique3, caracteristique4, id;
    JButton mettreAuSale;

    public JListMouseListener(JList jl, JPanel panelAffichage, JDialog jd,JLabel photo,JLabel caracteristique1,JLabel caracteristique2,JLabel caracteristique3,JLabel caracteristique4, JButton mettreAuSale,JLabel id) {
        super();
        this.jl = jl;
        this.panelAffichage = panelAffichage;
        this.jd= jd;
        this.photo=photo;
        this.caracteristique1=caracteristique1;
        this.caracteristique2=caracteristique2;
        this.caracteristique3=caracteristique3;
        this.caracteristique4=caracteristique4;
        this.mettreAuSale=mettreAuSale;
        this.id =id;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jl = (JList) e.getSource();
        if (e.getClickCount() == 2) {
            indexClic = jl.getSelectedIndex();//locationToIndex(e.getPoint());
            affichageObjet(indexClic);
        }
    }

    public void affichageObjet(int index) {

        Contenu c = (Contenu) (jl.getModel().getElementAt(index));
        Component[] comps = jd.getComponents();
        
        if (c instanceof Sac) {
            Sac s = (Sac) c;
            photo.setIcon(new ImageIcon(new ImageIcon("images/sacs/" + c.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            caracteristique1.setText(s.getTypeS().getNom());
            caracteristique2.setText(String.valueOf(s.getCouleur().toString()));
            caracteristique3.setText("");
            caracteristique4.setText("");
            mettreAuSale.setVisible(false);
        }
        if (c instanceof Chaussures) {

            Chaussures ch = (Chaussures) c;
            photo.setIcon(new ImageIcon(new ImageIcon("images/chaussures/" + c.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            caracteristique1.setText(ch.getTypeC().getNom());
            caracteristique2.setText(String.valueOf(ch.getCouleur().toString()));
            caracteristique3.setText("");
            caracteristique4.setText("");
            mettreAuSale.setVisible(false);
        }
        if (c instanceof Vetement) {
            Vetement v = (Vetement) c;
            photo.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + c.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            caracteristique1.setText(v.getType().toString());
            caracteristique2.setText(String.valueOf(v.getCouleur().toString()));
            caracteristique3.setText(v.getCoupe().toString());
            caracteristique4.setText(v.getMatiere().toString());
            mettreAuSale.setVisible(true);
            if (v.isSale()){
                mettreAuSale.setText("Mettre au propre");
            }
        }
        id.setText(Integer.toString(c.getIdObjet()));
        id.setVisible(false);
        jd.pack();
        jd.setVisible(true);
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
