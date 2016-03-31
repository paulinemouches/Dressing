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
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
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

    public JListMouseListener(JList jl, JPanel panelAffichage) {
        super();
        this.jl = jl;
        this.panelAffichage = panelAffichage;
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

        String[] options = {"Supprimer", "Mettre au sale"};
        JDialog jd = new JDialog();

        JLabel photo = new JLabel();
        JLabel caracteristique1 = new JLabel();
        JLabel caracteristique2 = new JLabel();
        JLabel caracteristique3 = new JLabel();
        JLabel caracteristique4 = new JLabel();
        JPanel panel = new JPanel();

        Contenu c = (Contenu) (jl.getModel().getElementAt(index));

        if (c instanceof Sac) {
            Sac s = (Sac) c;
            photo.setIcon(new ImageIcon(new ImageIcon("images/sacs/" + c.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            caracteristique1.setText(s.getTypeS().getNom());
            caracteristique2.setText(String.valueOf(s.getCouleur().toString()));
            caracteristique3.setText("");
            caracteristique4.setText("");
        }
        if (c instanceof Chaussures) {

            Chaussures ch = (Chaussures) c;
            photo.setIcon(new ImageIcon(new ImageIcon("images/chaussures/" + c.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            caracteristique1.setText(ch.getTypeC().getNom());
            caracteristique2.setText(String.valueOf(ch.getCouleur().toString()));
            caracteristique3.setText("");
            caracteristique4.setText("");
        }
        if (c instanceof Vetement) {
            Vetement v = (Vetement) c;
            photo.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + c.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            caracteristique1.setText(v.getType().toString());
            caracteristique2.setText(String.valueOf(v.getCouleur().toString()));
            caracteristique3.setText(v.getCoupe().toString());
            caracteristique4.setText(v.getMatiere().toString());

        }
        panel.setLayout(new GridLayout(5, 1));
        panel.add(new JLabel("Description :"));
        panel.add(caracteristique1);
        panel.add(caracteristique2);
        panel.add(caracteristique3);
        panel.add(caracteristique4);
        jd.getContentPane().add(photo, BorderLayout.CENTER);
        jd.getContentPane().add(panel, BorderLayout.SOUTH);
        jd.pack();
        jd.setVisible(true);

        //CardLayout card = (CardLayout) InitFrame.MainFrame.getLayout();
        //card.show(InitFrame.MainFrame, "AffichageObjet");
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
