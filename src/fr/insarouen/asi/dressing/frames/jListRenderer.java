/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.frames;

import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author pauline
 */
public class jListRenderer extends JLabel implements ListCellRenderer {

    ArrayList<String> types = new ArrayList<String>();
    ArrayList<String> images = new ArrayList<String>();

    public jListRenderer(ArrayList<String> types, ArrayList<String> images) {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        this.types = types;
        this.images = images;
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
        ImageIcon icon = new ImageIcon(new ImageIcon("images/" + images.get(index)).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        String description = types.get(index);
        setIcon(icon);
        setText(description);
        setFont(list.getFont());

        return this;
    }

}
