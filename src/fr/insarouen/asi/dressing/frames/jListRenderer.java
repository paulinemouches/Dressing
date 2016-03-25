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
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author pauline
 */
public class jListRenderer extends JLabel implements ListCellRenderer {


    ArrayList<Contenu> contenus;
    String description;
    ImageIcon icon = null;

    public jListRenderer(ArrayList<Contenu> contenus) {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);

        this.contenus = contenus;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) { //To change body of generated methods, choose Tools | Templates.
        //int selectedIndex = ((Integer) value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
        icon = new ImageIcon(new ImageIcon("images/" + contenus.get(index).getImage()).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));

        if (contenus.get(index) instanceof Vetement) {
            Vetement v = (Vetement) contenus.get(index);
            description = v.getType().getNom();

        }
        if (contenus.get(index) instanceof Sac) {
            Sac s = (Sac) contenus.get(index);
            description = s.getTypeS().getNom();

        }
        if (contenus.get(index) instanceof Chaussures) {
            Chaussures c = (Chaussures) contenus.get(index);
            description = c.getTypeC().getNom();

        }
        this.setIcon(icon);
        this.setText(description);
        this.setFont(list.getFont());
        return this;
    }

}
