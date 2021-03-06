/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insarouen.asi.dressing.frames;

import fr.insarouen.asi.dressing.Initialisation;
import fr.insarouen.asi.dressing.Tenue;
import fr.insarouen.asi.dressing.TenueImpossibleException;
import fr.insarouen.asi.dressing.conseil.Conseil;
import fr.insarouen.asi.dressing.elements.Couleur;
import fr.insarouen.asi.dressing.elements.CouleurCheveux;
import fr.insarouen.asi.dressing.elements.CoupeVetement;
import fr.insarouen.asi.dressing.elements.Matiere;
import fr.insarouen.asi.dressing.elements.Signe;
import fr.insarouen.asi.dressing.elements.TypeChaussures;
import fr.insarouen.asi.dressing.elements.TypeEvenement;
import fr.insarouen.asi.dressing.elements.TypeSac;
import fr.insarouen.asi.dressing.elements.TypeVetement;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Contenu;
import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import fr.insarouen.asi.dressing.elements.utilisateurs.Utilisateur;
import fr.insarouen.asi.dressing.notice.Notice;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author pauline
 */
public class InitFrame extends javax.swing.JFrame {

    int idDressing;
    Boolean connecte = false;
    public static ArrayList<String> oldPanel = new ArrayList<String>();

    JButton prec = new JButton("Précédent");
    JButton suiv = new JButton("Suivant");
    ArrayList<Tenue> tenues = new ArrayList<Tenue>();
    int indiceCourant;
    int indiceFinal;
    int[] tableauIdChoisis = {0, 0, 0};
    ArrayList<Vetement> vetementsTypeChoisis = new ArrayList<Vetement>();
    int typeTenue;
    int avecForme;
    int idUser;
    TypeEvenement evenement;
    //JPanel Affichage;

    /**
     * Creates new form InitFrame
     */
    public InitFrame() {
        initComponents();
        retour.setVisible(false);
        jMenuBar1.setVisible(false);
    }

    public int getIdDressing() {
        return idDressing;
    }

    public void setIdDressing(int idDressing) {
        this.idDressing = idDressing;
    }

    public void consulterVetements(int typeV) {
        Collection<Vetement> vetements;
        try {
            AffichageDressing.removeAll();
            DefaultListModel<Contenu> dlmSac = new DefaultListModel<Contenu>();
            JList listeObjets = new JList(dlmSac);
            JScrollPane jsp = new JScrollPane(listeObjets);
            jsp.setPreferredSize(new Dimension(200, 440));

            if (typeV != 0) {
                vetements = Vetement.getVetementsType(idDressing, typeV);
            } else {
                vetements = Vetement.getVetements().values();
            }
            int i = 0;
            if (vetements.isEmpty()) {
                if(typeV!=0){
                    JOptionPane jop1 = new JOptionPane();
                    jop1.showMessageDialog(ConsulterDressing, "Vous n'avez pas de vêtements de ce type !", "Information", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane jop1 = new JOptionPane();
                    jop1.showMessageDialog(ConsulterDressing, "Vous n'avez pas de vêtements !", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } else {
                for (Vetement v : vetements) {
                    dlmSac.add(i, v);
                    i++;
                }

                listeObjets.setCellRenderer(new JListRenderer());
                listeObjets.addMouseListener(new JListMouseListener(listeObjets, detailContenu, imageContenu, caracteristique1, caracteristique2, caracteristique3, caracteristique4, mettreAuSalePropre, idobjet));
                //listeObjets.setVisibleRowCount(i);

                oldPanel.add("ConsulterDressing");
                AffichageDressing.add(jsp, BorderLayout.CENTER);
                CardLayout card = (CardLayout) MainFrame.getLayout();
                card.show(MainFrame, "AffichageDressing");
                AffichageDressing.repaint();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InitFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Integer> idsTenueCorrespondant(Tenue t) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(t.getSac().getIdObjet());
        ids.add(t.getChaussures().getIdObjet());
        ArrayList<Vetement> vetements = t.getVetements();
        for (Vetement v : vetements) {
            ids.add(v.getIdObjet());
        }
        return ids;
    }

    public boolean estContenuDans(Tenue t, ArrayList<Tenue> tenues) {
        int contenu = 0;
        boolean estDedans = false;
        ArrayList<Integer> idsTenue = idsTenueCorrespondant(t);
        ArrayList<Integer> idsTableau = new ArrayList<Integer>();
        if (tenues.isEmpty()) {
            return false;
        } else {
            for (Tenue ten : tenues) {
                idsTableau = idsTenueCorrespondant(ten);
                if (idsTenue.size() == idsTableau.size()) {
                    contenu = 0;
                    for (int j = 0; j < idsTableau.size(); j++) {
                        if (idsTableau.contains(idsTenue.get(j))) {
                            contenu = contenu + 1;
                        }
                    }
                    if (contenu == idsTableau.size()) {
                        return true;
                    }
                }
            }
            return estDedans;
        }
    }

    public Tenue creationTenue(int[] tableauIdChoisis, ArrayList<Vetement> vetementsTypeChoisis, int typeTenue, int avecForme, int idUser, TypeEvenement evenement) throws SQLException, TenueImpossibleException {

        boolean flag;//change si l'exception est levée
        Tenue t = new Tenue();
        int i = 0;
        int k = 0;
        do {
            try {
                t.creerTenue(tableauIdChoisis, vetementsTypeChoisis, typeTenue, avecForme, idUser, evenement).toString();
                if (estContenuDans(t, tenues) && tenues.size() < 100) {
                    flag = true;
                    t.viderTenue();
                    k++;
                    if (k == 100) { // Si on retombe toujours sur les meme tenues, on sort
                        suiv.setVisible(false);
                        flag = false;
                        indiceFinal = tenues.size() - 1;
                    }
                } else if (!estContenuDans(t, tenues) && tenues.size() < 100) {
                    tenues.add(t);
                    indiceCourant = indiceCourant + 1;
                    consulterTenue(t);
                    flag = false;//l'exception n'est pas levée, le flag est faux, on sort de la boucle
                } else {
                    suiv.setVisible(false);
                    flag = false;
                    indiceFinal = tenues.size() - 1;
                }
            } catch (TenueImpossibleException e) {
                i++;
                System.out.println("i" + i);
                flag = true;//si l'exception est levée, le flag passe a true, on va refaire la boucle
                t.viderTenue();
                if (i == 100) {
                    flag = false;
                    suiv.setVisible(false);
                    indiceFinal = tenues.size() - 1;
                    throw new TenueImpossibleException();
                }
            }
        } while (flag);//tant que l'exception est levée, on recommence a creer une nouvelle tenue (toujours avec les meme caractéristiques)
        return t;
    }

    public void initSuivantPrecedent() {

        retour.setVisible(true);
        jMenuBar1.setVisible(true);
        JPanel Boutons = new JPanel();
        Boutons.removeAll();

        tenues.clear();
        indiceFinal = 100;
        indiceCourant = -1;
        Boutons.setLayout(new BorderLayout());

        Boutons.add(prec, BorderLayout.WEST);

        Boutons.add(suiv, BorderLayout.EAST);

        Boutons.revalidate();
        Boutons.repaint();

        prec.setVisible(true);
        suiv.setVisible(true);
        prec.validate();
        suiv.validate();
        prec.repaint();
        suiv.repaint();

        prec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceCourant == 0) {
                    prec.setVisible(false);
                } else {
                    indiceCourant = indiceCourant - 1;
                    suiv.setVisible(true);
                    consulterTenue(tenues.get(indiceCourant));
                }
            }
        });
        suiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceCourant < (tenues.size() - 1) && indiceCourant < indiceFinal) {
                    prec.setVisible(true);
                    indiceCourant = indiceCourant + 1;
                    consulterTenue(tenues.get(indiceCourant));
                } else if (indiceCourant >= indiceFinal) {
                    suiv.setVisible(false);

                } else {
                    try {
                        prec.setVisible(true);
                        creationTenue(tableauIdChoisis, vetementsTypeChoisis, typeTenue, avecForme, idUser, evenement);
                    } catch (SQLException | TenueImpossibleException ex) {
                        Logger.getLogger(InitFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        AffichageTenue.add(Boutons, BorderLayout.SOUTH);
        AffichageTenue.revalidate();
        AffichageTenue.repaint();

    }

    public void consulterTenue(Tenue t) {
        Affichage.setLayout(new GridLayout(3, 3));
        Affichage.removeAll();
        Affichage.revalidate();
        Affichage.repaint();

        JPanel pn1 = new JPanel();
        pn1.removeAll();
        pn1.revalidate();
        pn1.repaint();
        JPanel pn2 = new JPanel();
        pn2.removeAll();
        pn2.revalidate();
        pn2.repaint();
        JPanel pn3 = new JPanel();
        pn3.removeAll();
        pn3.revalidate();
        pn3.repaint();
        JPanel pn4 = new JPanel();
        pn4.removeAll();
        pn4.revalidate();
        pn4.repaint();
        JPanel pn5 = new JPanel();
        pn5.removeAll();
        pn5.revalidate();
        pn5.repaint();

        JLabel lab1 = new JLabel();
        JLabel lab1txt = new JLabel();
        lab1.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + t.getVetements().get(0).getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        lab1txt.setText("<html>" + t.getVetements().get(0).getCouleur().toString() + "<br>" + t.getVetements().get(0).getCoupe().toString() + "<br>" + t.getVetements().get(0).getMatiere().toString() + "</html>");

        pn1.add(lab1, BorderLayout.CENTER);
        pn1.add(lab1txt, BorderLayout.EAST);
        pn1.revalidate();
        pn1.repaint();
        Affichage.add(pn1);

        if (t.getVetements().size() > 1) {
            JLabel lab2 = new JLabel();
            JLabel lab2txt = new JLabel();
            lab2txt.setText("<html>" + t.getVetements().get(1).getCouleur().toString() + "<br>" + t.getVetements().get(1).getCoupe().toString() + "<br>" + t.getVetements().get(1).getMatiere().toString() + "</html>");

            pn2.add(lab2, BorderLayout.CENTER);
            pn2.add(lab2txt, BorderLayout.EAST);
            lab2.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + t.getVetements().get(1).getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            pn2.revalidate();
            pn2.repaint();
            Affichage.add(pn2);

            if (t.getVetements().size() > 2) {
                JLabel lab3 = new JLabel();
                JLabel lab3txt = new JLabel();
                lab3txt.setText("<html>" + t.getVetements().get(2).getCouleur().toString() + "<br>" + t.getVetements().get(2).getCoupe().toString() + "<br>" + t.getVetements().get(2).getMatiere().toString() + "</html>");

                pn3.add(lab3, BorderLayout.CENTER);
                pn3.add(lab3txt, BorderLayout.EAST);
                lab3.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + t.getVetements().get(2).getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                pn3.revalidate();
                pn3.repaint();
                Affichage.add(pn3);

            } else {
                pn3.removeAll();
            }
        } else {
            pn2.removeAll();
        }
        JLabel lab4 = new JLabel();
        JLabel lab4txt = new JLabel();
        lab4txt.setText("<html>" + t.getSac().getCouleur().toString() + "<br>" + t.getSac().getTypeS().getNom() + "</html>");

        pn4.add(lab4, BorderLayout.CENTER);
        pn4.add(lab4txt, BorderLayout.EAST);
        lab4.setIcon(new ImageIcon(new ImageIcon("images/sacs/" + t.getSac().getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        pn4.revalidate();
        Affichage.add(pn4);

        JLabel lab5 = new JLabel();
        JLabel lab5txt = new JLabel();
        lab5txt.setText("<html>" + t.getChaussures().getCouleur().toString() + "<br>" + t.getChaussures().getTypeC().getNom() + "</html>");

        pn5.add(lab5, BorderLayout.CENTER);
        pn5.add(lab5txt, BorderLayout.EAST);
        lab5.setIcon(new ImageIcon(new ImageIcon("images/chaussures/" + t.getChaussures().getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        pn5.revalidate();
        Affichage.add(pn5);

        Affichage.revalidate();

        CardLayout card = (CardLayout) MainFrame.getLayout();
        card.show(MainFrame, "AffichageTenue");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jComboBox3 = new javax.swing.JComboBox();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        detailContenu = new javax.swing.JDialog();
        imageContenu = new javax.swing.JLabel();
        caracteristique1 = new javax.swing.JLabel();
        caracteristique2 = new javax.swing.JLabel();
        caracteristique4 = new javax.swing.JLabel();
        caracteristique3 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        mettreAuSalePropre = new javax.swing.JButton();
        supprimer = new javax.swing.JButton();
        idobjet = new javax.swing.JLabel();
        suppressionContenu = new javax.swing.JDialog();
        javax.swing.JLabel jLabel39 = new javax.swing.JLabel();
        photoContenu = new javax.swing.JLabel();
        caract1 = new javax.swing.JLabel();
        caract2 = new javax.swing.JLabel();
        caract3 = new javax.swing.JLabel();
        caract4 = new javax.swing.JLabel();
        boutonSupprimerContenu = new javax.swing.JButton();
        bouttonAnnulationSuppression = new javax.swing.JButton();
        idcontenu = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        MainFrame = new javax.swing.JPanel();
        premiereConnexion = new javax.swing.JButton();
        ConsulterDressing = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        sacs = new javax.swing.JButton();
        dressingComplet = new javax.swing.JButton();
        pantalons = new javax.swing.JButton();
        chemisiers = new javax.swing.JButton();
        teeshirts = new javax.swing.JButton();
        pulls = new javax.swing.JButton();
        combinaisons = new javax.swing.JButton();
        jupes = new javax.swing.JButton();
        chaussures = new javax.swing.JButton();
        vestes = new javax.swing.JButton();
        shorts = new javax.swing.JButton();
        robes = new javax.swing.JButton();
        pantacourts = new javax.swing.JButton();
        manteaux = new javax.swing.JButton();
        joggins = new javax.swing.JButton();
        ajoutContenu = new javax.swing.JButton();
        supprimerContenu = new javax.swing.JButton();
        saisonCourante = new javax.swing.JLabel();
        saisonCourante1 = new javax.swing.JLabel();
        couleurPreferee = new javax.swing.JLabel();
        AffichageVetementSaison = new javax.swing.JPanel();
        AffichageVetementCouleurPreferee = new javax.swing.JPanel();
        AjoutUtilisateur = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        nouveauCoulC = new javax.swing.JComboBox();
        validerAjoutUtilisateur = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nouveauSigne = new javax.swing.JComboBox();
        nouveauCoulP = new javax.swing.JComboBox();
        nouveauPrenom = new javax.swing.JTextField();
        nouveauNom = new javax.swing.JTextField();
        annulerAjoutUtilisateur = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        nouveauIdentifiant = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        nouveauAge = new javax.swing.JComboBox();
        nouveauTaille = new javax.swing.JComboBox();
        nouveauMdp = new javax.swing.JPasswordField();
        Connexion = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nomBase = new javax.swing.JTextField();
        mdp = new javax.swing.JTextField();
        connecter = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        nomUtilisateur1 = new javax.swing.JTextField();
        AjoutSac = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        typeSac = new javax.swing.JComboBox();
        couleurSac = new javax.swing.JComboBox();
        validerAjoutSac = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        parcourirSac = new javax.swing.JButton();
        photoSac = new javax.swing.JLabel();
        cheminPhotoSac = new javax.swing.JLabel();
        annulerAjoutSac = new javax.swing.JButton();
        Accueilv2 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        identifiantUtilisateur = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        connection = new javax.swing.JButton();
        creationCompte = new javax.swing.JButton();
        mdpUtilisateur = new javax.swing.JPasswordField();
        AccueilDressing = new javax.swing.JPanel();
        dressing = new javax.swing.JButton();
        corbeille = new javax.swing.JButton();
        creerTenue = new javax.swing.JButton();
        age = new javax.swing.JLabel();
        taille = new javax.swing.JLabel();
        coulCheveux = new javax.swing.JLabel();
        coulPreferee = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        champConseil = new javax.swing.JLabel();
        AffichageDressing = new javax.swing.JPanel();
        TenueNormale = new javax.swing.JPanel();
        evtTenueNormale = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        formeTenueNormale = new javax.swing.JCheckBox();
        validerTenueNormale = new javax.swing.JButton();
        annuleTenueNormale = new javax.swing.JButton();
        AffichageTenue = new javax.swing.JPanel();
        Affichage = new javax.swing.JPanel();
        TenueAvecTypeParticulier = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        evtTenueAvecTypeParticulier = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        typeTenueAvecTypeParticulier = new javax.swing.JComboBox<>();
        formeTenueAvecTypeParticulier = new javax.swing.JCheckBox();
        validerTenueAvecTypeParticulier = new javax.swing.JButton();
        annuleTenueAvecTypePartculier = new javax.swing.JButton();
        TenueAvecContenuParticulier = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        validerTenueAvecContenuParticulier = new javax.swing.JButton();
        formeTenueAvecContenuParticulier = new javax.swing.JCheckBox();
        jLabel42 = new javax.swing.JLabel();
        evtTenueAvecContenuParticulier = new javax.swing.JComboBox<>();
        annuleTenueAvecContenuParticulier = new javax.swing.JButton();
        Accueil = new javax.swing.JPanel();
        accesDressing = new javax.swing.JButton();
        ajoutUtilisateur = new javax.swing.JButton();
        supprUtilisateur = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        idUtilisateur = new javax.swing.JTextField();
        identifiant = new javax.swing.JLabel();
        idSuppr = new javax.swing.JTextField();
        AjoutChaussures = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        typeChaussures = new javax.swing.JComboBox();
        couleurChaussures = new javax.swing.JComboBox();
        validerAjoutChaussures = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        parcourirChaussures = new javax.swing.JButton();
        photoChaussures = new javax.swing.JLabel();
        cheminPhotoChaussures = new javax.swing.JLabel();
        annulerAjoutChaussures = new javax.swing.JButton();
        AjoutVetement = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        typeV = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        coupeV = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        matiereV = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        couleurV = new javax.swing.JComboBox();
        validerAjoutVetement = new javax.swing.JButton();
        annulerAjoutVetement = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        parcourirVetement = new javax.swing.JButton();
        photoVetement = new javax.swing.JLabel();
        cheminPhotoVetement = new javax.swing.JLabel();
        AffichageSuppression = new javax.swing.JPanel();
        ConsulterCorbeille = new javax.swing.JPanel();
        AffichageCorbeille = new javax.swing.JPanel();
        mettreAuSale = new javax.swing.JButton();
        mettreAuPropre = new javax.swing.JButton();
        ko = new javax.swing.JButton();
        validerCorbeille = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        deconnexion = new javax.swing.JMenu();
        retourAccueilItem = new javax.swing.JMenuItem();
        notice = new javax.swing.JMenuItem();
        deconnexionItem = new javax.swing.JMenuItem();
        retour = new javax.swing.JMenu();

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jFormattedTextField1.setText("jFormattedTextField1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel44.setText("Description :");

        mettreAuSalePropre.setText("Mettre au sale");
        mettreAuSalePropre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mettreAuSalePropreActionPerformed(evt);
            }
        });

        supprimer.setText("Supprimer");
        supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout detailContenuLayout = new javax.swing.GroupLayout(detailContenu.getContentPane());
        detailContenu.getContentPane().setLayout(detailContenuLayout);
        detailContenuLayout.setHorizontalGroup(
            detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailContenuLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(imageContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailContenuLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel44)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailContenuLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(idobjet)
                            .addGroup(detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(caracteristique1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(caracteristique2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(caracteristique3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(caracteristique4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(73, 73, 73))))
            .addGroup(detailContenuLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addGroup(detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mettreAuSalePropre, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supprimer, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        detailContenuLayout.setVerticalGroup(
            detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailContenuLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailContenuLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(caracteristique1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(caracteristique2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(caracteristique3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(caracteristique4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))
                    .addGroup(detailContenuLayout.createSequentialGroup()
                        .addComponent(imageContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(supprimer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mettreAuSalePropre)
                    .addComponent(idobjet))
                .addGap(43, 43, 43))
        );

        jLabel39.setText("Voulez-vous vraiment supprimer cet élément de votre dressing ?");

        boutonSupprimerContenu.setText("Oui");
        boutonSupprimerContenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonSupprimerContenuActionPerformed(evt);
            }
        });

        bouttonAnnulationSuppression.setText("Non");
        bouttonAnnulationSuppression.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bouttonAnnulationSuppressionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout suppressionContenuLayout = new javax.swing.GroupLayout(suppressionContenu.getContentPane());
        suppressionContenu.getContentPane().setLayout(suppressionContenuLayout);
        suppressionContenuLayout.setHorizontalGroup(
            suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(suppressionContenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(suppressionContenuLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(photoContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boutonSupprimerContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(suppressionContenuLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(caract1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(caract2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(caract3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(caract4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(suppressionContenuLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bouttonAnnulationSuppression, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 68, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, suppressionContenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(idcontenu)
                .addGap(97, 97, 97))
        );
        suppressionContenuLayout.setVerticalGroup(
            suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(suppressionContenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addGroup(suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(suppressionContenuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(photoContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(suppressionContenuLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(caract1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(caract2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(caract3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(caract4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(suppressionContenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutonSupprimerContenu)
                    .addComponent(bouttonAnnulationSuppression))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idcontenu)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainFrame.setLayout(new java.awt.CardLayout());

        premiereConnexion.setText("Se connecter");
        premiereConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                premiereConnexionActionPerformed(evt);
            }
        });
        MainFrame.add(premiereConnexion, "card2");

        sacs.setText("Sacs");
        sacs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sacsActionPerformed(evt);
            }
        });

        dressingComplet.setText("Dressing complet");
        dressingComplet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dressingCompletActionPerformed(evt);
            }
        });

        pantalons.setText("Pantalons");
        pantalons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pantalonsActionPerformed(evt);
            }
        });

        chemisiers.setText("Chemisiers");
        chemisiers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chemisiersActionPerformed(evt);
            }
        });

        teeshirts.setText("Tee-shirts");
        teeshirts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teeshirtsActionPerformed(evt);
            }
        });

        pulls.setText("Pulls");
        pulls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pullsActionPerformed(evt);
            }
        });

        combinaisons.setText("Combinaisons");
        combinaisons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combinaisonsActionPerformed(evt);
            }
        });

        jupes.setText("Jupes");
        jupes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jupesActionPerformed(evt);
            }
        });

        chaussures.setText("Chaussures");
        chaussures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chaussuresActionPerformed(evt);
            }
        });

        vestes.setText("Vestes");
        vestes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vestesActionPerformed(evt);
            }
        });

        shorts.setText("Shorts");
        shorts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shortsActionPerformed(evt);
            }
        });

        robes.setText("Robes");
        robes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                robesActionPerformed(evt);
            }
        });

        pantacourts.setText("Pantacourts");
        pantacourts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pantacourtsActionPerformed(evt);
            }
        });

        manteaux.setText("Manteaux");
        manteaux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manteauxActionPerformed(evt);
            }
        });

        joggins.setText("Joggins");
        joggins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jogginsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sacs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pantalons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vestes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(combinaisons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chemisiers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pantacourts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manteaux, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(joggins, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shorts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pulls, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jupes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(robes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chaussures, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(teeshirts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dressingComplet, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dressingComplet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sacs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chaussures)
                .addGap(12, 12, 12)
                .addComponent(teeshirts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(combinaisons)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vestes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pantalons)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pulls)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(robes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(shorts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(joggins)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(manteaux)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pantacourts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chemisiers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jupes)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel1);

        ajoutContenu.setText("+");
        ajoutContenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajoutContenuActionPerformed(evt);
            }
        });

        supprimerContenu.setText("-");
        supprimerContenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerContenuActionPerformed(evt);
            }
        });

        saisonCourante1.setText("Couleur préférée :");

        javax.swing.GroupLayout ConsulterDressingLayout = new javax.swing.GroupLayout(ConsulterDressing);
        ConsulterDressing.setLayout(ConsulterDressingLayout);
        ConsulterDressingLayout.setHorizontalGroup(
            ConsulterDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsulterDressingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(ConsulterDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ConsulterDressingLayout.createSequentialGroup()
                        .addComponent(ajoutContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(supprimerContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(saisonCourante, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ConsulterDressingLayout.createSequentialGroup()
                        .addComponent(saisonCourante1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(couleurPreferee, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(AffichageVetementSaison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AffichageVetementCouleurPreferee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        ConsulterDressingLayout.setVerticalGroup(
            ConsulterDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsulterDressingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConsulterDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConsulterDressingLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(ConsulterDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ajoutContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supprimerContenu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(saisonCourante, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AffichageVetementSaison, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ConsulterDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(saisonCourante1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(couleurPreferee, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AffichageVetementCouleurPreferee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                .addContainerGap())
        );

        MainFrame.add(ConsulterDressing, "ConsulterDressing");

        nouveauCoulC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Blond", "Brun", "Roux", "Chatain" }));
        nouveauCoulC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nouveauCoulCActionPerformed(evt);
            }
        });

        validerAjoutUtilisateur.setText("Valider");
        validerAjoutUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerAjoutUtilisateurActionPerformed(evt);
            }
        });

        jLabel11.setText("Signe :");

        jLabel10.setText("couleur préférée :");

        jLabel9.setText("couleur cheveux :");

        jLabel8.setText("taille (cm) :");

        jLabel7.setText("âge :");

        jLabel6.setText("prénom :");

        jLabel5.setText("nom :");

        nouveauSigne.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8", "V", "O", "H", "A", "X" }));

        nouveauCoulP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bleu", "Bleu clair", "Bleu marine", "Turquoise", "Gris clair", "Argenté", "Gris foncé", "Marron clair", "Marron foncé", "Corail", "Orange", "Bordeau", "Brique", "Rouge", "Rose pale", "Rose fushia", "Rose foncé", "Mauve", "Violet", "Prune", "Blanc", "Jaune moutarde ", "Jaune", "Doré", "Noir", "Kaki", "Vert pale", "Vert", "Jean clair", "Jean marine", "Beige" }));
        nouveauCoulP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nouveauCoulPActionPerformed(evt);
            }
        });

        annulerAjoutUtilisateur.setText("Annuler");
        annulerAjoutUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerAjoutUtilisateurActionPerformed(evt);
            }
        });

        jLabel22.setText("identifiant:");

        jLabel23.setText("mot de passe:");

        nouveauAge.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99" }));

        nouveauTaille.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "150", "151", "152", "153", "154", "155", "156", "157", "158", "159", "160", "161", "162", "163", "164", "165", "166", "167", "168", "169", "170", "171", "172", "173", "174", "175", "176", "177", "178", "179", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189", "190", "191", "192", "193", "194", "195", "196", "197", "198", "199", "200", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(validerAjoutUtilisateur)
                .addGap(18, 18, 18)
                .addComponent(annulerAjoutUtilisateur)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel5))
                        .addGap(139, 139, 139)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nouveauSigne, 0, 116, Short.MAX_VALUE)
                            .addComponent(nouveauNom)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel22)
                                                .addComponent(jLabel23)
                                                .addComponent(jLabel8))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addGap(61, 61, 61)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(57, 57, 57)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(151, 151, 151)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(122, 122, 122)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nouveauPrenom)
                            .addComponent(nouveauAge, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nouveauCoulP, 0, 1, Short.MAX_VALUE)
                            .addComponent(nouveauCoulC, javax.swing.GroupLayout.Alignment.TRAILING, 0, 116, Short.MAX_VALUE)
                            .addComponent(nouveauTaille, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nouveauIdentifiant, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nouveauMdp, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(107, 107, 107))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nouveauNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(nouveauPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nouveauAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(nouveauTaille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(nouveauCoulC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(nouveauCoulP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nouveauSigne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(nouveauIdentifiant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(nouveauMdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(validerAjoutUtilisateur)
                    .addComponent(annulerAjoutUtilisateur))
                .addContainerGap())
        );

        javax.swing.GroupLayout AjoutUtilisateurLayout = new javax.swing.GroupLayout(AjoutUtilisateur);
        AjoutUtilisateur.setLayout(AjoutUtilisateurLayout);
        AjoutUtilisateurLayout.setHorizontalGroup(
            AjoutUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjoutUtilisateurLayout.createSequentialGroup()
                .addGap(0, 43, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );
        AjoutUtilisateurLayout.setVerticalGroup(
            AjoutUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjoutUtilisateurLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        MainFrame.add(AjoutUtilisateur, "AjoutUtilisateur");

        jLabel2.setText("Base :");

        jLabel3.setText("Utilisateur :");

        mdp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mdpActionPerformed(evt);
            }
        });

        connecter.setText("Se connecter");
        connecter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connecterActionPerformed(evt);
            }
        });

        jLabel30.setText("Mot de passe:");

        nomUtilisateur1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomUtilisateur1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ConnexionLayout = new javax.swing.GroupLayout(Connexion);
        Connexion.setLayout(ConnexionLayout);
        ConnexionLayout.setHorizontalGroup(
            ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnexionLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConnexionLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(connecter))
                    .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(ConnexionLayout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mdp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ConnexionLayout.createSequentialGroup()
                            .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addGap(43, 43, 43)
                            .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nomBase, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nomUtilisateur1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        ConnexionLayout.setVerticalGroup(
            ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConnexionLayout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConnexionLayout.createSequentialGroup()
                        .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(nomUtilisateur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(50, 50, 50)
                .addComponent(connecter)
                .addGap(123, 123, 123))
        );

        MainFrame.add(Connexion, "Connexion");

        jLabel16.setText("type de sac :");

        jLabel17.setText("couleur du sac :");

        typeSac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "sac à dos", "sac à main", "pochette" }));

        couleurSac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bleu", "Bleu clair", "Bleu marine", "Turquoise", "Gris clair", "Argenté", "Gris foncé", "Marron clair", "Marron foncé", "Corail", "Orange", "Bordeau", "Brique", "Rouge", "Rose pale", "Rose fushia", "Rose foncé", "Mauve", "Violet", "Prune", "Blanc", "Jaune moutarde ", "Jaune", "Doré", "Noir", "Kaki", "Vert pale", "Vert", "Jean clair", "Jean marine", "Beige" }));
        couleurSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                couleurSacActionPerformed(evt);
            }
        });

        validerAjoutSac.setText("Valider");
        validerAjoutSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerAjoutSacActionPerformed(evt);
            }
        });

        jLabel20.setText("image:");

        parcourirSac.setText("Parcourir ...");
        parcourirSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcourirSacActionPerformed(evt);
            }
        });

        annulerAjoutSac.setText("Annuler");
        annulerAjoutSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerAjoutSacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AjoutSacLayout = new javax.swing.GroupLayout(AjoutSac);
        AjoutSac.setLayout(AjoutSacLayout);
        AjoutSacLayout.setHorizontalGroup(
            AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AjoutSacLayout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AjoutSacLayout.createSequentialGroup()
                        .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel20))
                        .addGap(65, 65, 65)
                        .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AjoutSacLayout.createSequentialGroup()
                                .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(couleurSac, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(parcourirSac, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(photoSac, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AjoutSacLayout.createSequentialGroup()
                        .addComponent(annulerAjoutSac)
                        .addGap(27, 27, 27)
                        .addComponent(validerAjoutSac)
                        .addGap(31, 31, 31)
                        .addComponent(cheminPhotoSac, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        AjoutSacLayout.setVerticalGroup(
            AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjoutSacLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(typeSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(couleurSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AjoutSacLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(photoSac, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AjoutSacLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(parcourirSac))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cheminPhotoSac, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AjoutSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(validerAjoutSac)
                        .addComponent(annulerAjoutSac)))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        MainFrame.add(AjoutSac, "AjoutSac");

        jLabel28.setText("identifiant :");

        identifiantUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identifiantUtilisateurActionPerformed(evt);
            }
        });

        jLabel29.setText("mot de passe :");

        connection.setText("se connecter");
        connection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectionActionPerformed(evt);
            }
        });

        creationCompte.setText("créer un compte");
        creationCompte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creationCompteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Accueilv2Layout = new javax.swing.GroupLayout(Accueilv2);
        Accueilv2.setLayout(Accueilv2Layout);
        Accueilv2Layout.setHorizontalGroup(
            Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Accueilv2Layout.createSequentialGroup()
                .addGroup(Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Accueilv2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(connection)
                        .addGap(32, 32, 32)
                        .addComponent(creationCompte))
                    .addGroup(Accueilv2Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mdpUtilisateur)
                            .addComponent(identifiantUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        Accueilv2Layout.setVerticalGroup(
            Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Accueilv2Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addGroup(Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(identifiantUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(mdpUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(Accueilv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connection)
                    .addComponent(creationCompte))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        MainFrame.add(Accueilv2, "Accueilv2");

        dressing.setText("Dressing");
        dressing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dressingActionPerformed(evt);
            }
        });

        corbeille.setText("Corbeille à linge");
        corbeille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corbeilleActionPerformed(evt);
            }
        });

        creerTenue.setText("Créer une tenue");
        creerTenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creerTenueActionPerformed(evt);
            }
        });

        age.setText("age");

        taille.setText("taille");

        coulCheveux.setText("couleur Cheveux");

        coulPreferee.setText("couleur Préférée");

        jLabel1.setText("Nos Conseils :");

        jLabel12.setText("age :");

        jLabel13.setText("couleur Cheveux :");

        jLabel14.setText("couler préférée :");

        jLabel15.setText("taille :");

        javax.swing.GroupLayout AccueilDressingLayout = new javax.swing.GroupLayout(AccueilDressing);
        AccueilDressing.setLayout(AccueilDressingLayout);
        AccueilDressingLayout.setHorizontalGroup(
            AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccueilDressingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dressing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(creerTenue, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(corbeille, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addGroup(AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(AccueilDressingLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(84, 84, 84))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccueilDressingLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(7, 7, 7)))
                .addGap(63, 63, 63)
                .addGroup(AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(taille, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(age, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(coulCheveux, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(coulPreferee, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(55, 55, 55))
            .addGroup(AccueilDressingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(champConseil, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AccueilDressingLayout.setVerticalGroup(
            AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccueilDressingLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AccueilDressingLayout.createSequentialGroup()
                        .addComponent(dressing)
                        .addGap(12, 12, 12)
                        .addComponent(creerTenue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(corbeille))
                    .addGroup(AccueilDressingLayout.createSequentialGroup()
                        .addComponent(age)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(coulCheveux, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(taille)
                        .addGap(18, 18, 18)
                        .addComponent(coulPreferee))
                    .addGroup(AccueilDressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addGroup(AccueilDressingLayout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel15)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel14))))
                .addGap(58, 58, 58)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(champConseil, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MainFrame.add(AccueilDressing, "AccueilDressing");
        MainFrame.add(AffichageDressing, "AffichageDressing");

        evtTenueNormale.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les jours", "Sport", "Soirée" }));

        jLabel36.setText("Evenement:");

        formeTenueNormale.setText("Accordée à la forme ");

        validerTenueNormale.setText("Valider");
        validerTenueNormale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerTenueNormaleActionPerformed(evt);
            }
        });

        annuleTenueNormale.setText("Annuler");
        annuleTenueNormale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annuleTenueNormaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TenueNormaleLayout = new javax.swing.GroupLayout(TenueNormale);
        TenueNormale.setLayout(TenueNormaleLayout);
        TenueNormaleLayout.setHorizontalGroup(
            TenueNormaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TenueNormaleLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(TenueNormaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TenueNormaleLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(evtTenueNormale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TenueNormaleLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(formeTenueNormale))
                    .addGroup(TenueNormaleLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(annuleTenueNormale)
                        .addGap(26, 26, 26)
                        .addComponent(validerTenueNormale)))
                .addContainerGap(238, Short.MAX_VALUE))
        );
        TenueNormaleLayout.setVerticalGroup(
            TenueNormaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TenueNormaleLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(TenueNormaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(evtTenueNormale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(54, 54, 54)
                .addComponent(formeTenueNormale)
                .addGap(45, 45, 45)
                .addGroup(TenueNormaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(validerTenueNormale)
                    .addComponent(annuleTenueNormale))
                .addContainerGap(197, Short.MAX_VALUE))
        );

        MainFrame.add(TenueNormale, "TenueNormale");

        AffichageTenue.setLayout(new java.awt.BorderLayout());

        Affichage.setLayout(new java.awt.GridLayout(3, 3));
        AffichageTenue.add(Affichage, java.awt.BorderLayout.CENTER);

        MainFrame.add(AffichageTenue, "AffichageTenue");

        jLabel37.setText("Evenement:");

        evtTenueAvecTypeParticulier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les jours", "Sport", "Soirée" }));

        jLabel38.setText("Type de Vetements :");

        typeTenueAvecTypeParticulier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tee-shirt", "Chemisier", "Pull", "Veste", "Manteau", "Pantalon", " Pantacourt", "Jogging", "Jupe", " Short", "Robe", "Combinaison" }));

        formeTenueAvecTypeParticulier.setText("Accordée à la forme");

        validerTenueAvecTypeParticulier.setText("Valider");
        validerTenueAvecTypeParticulier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerTenueAvecTypeParticulierActionPerformed(evt);
            }
        });

        annuleTenueAvecTypePartculier.setText("Annuler");
        annuleTenueAvecTypePartculier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annuleTenueAvecTypePartculierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TenueAvecTypeParticulierLayout = new javax.swing.GroupLayout(TenueAvecTypeParticulier);
        TenueAvecTypeParticulier.setLayout(TenueAvecTypeParticulierLayout);
        TenueAvecTypeParticulierLayout.setHorizontalGroup(
            TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TenueAvecTypeParticulierLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TenueAvecTypeParticulierLayout.createSequentialGroup()
                        .addGroup(TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37))
                        .addGap(69, 69, 69)
                        .addGroup(TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeTenueAvecTypeParticulier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(evtTenueAvecTypeParticulier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(TenueAvecTypeParticulierLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(formeTenueAvecTypeParticulier))
                    .addGroup(TenueAvecTypeParticulierLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(annuleTenueAvecTypePartculier)
                        .addGap(26, 26, 26)
                        .addComponent(validerTenueAvecTypeParticulier)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        TenueAvecTypeParticulierLayout.setVerticalGroup(
            TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TenueAvecTypeParticulierLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(evtTenueAvecTypeParticulier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(60, 60, 60)
                .addGroup(TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(typeTenueAvecTypeParticulier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addComponent(formeTenueAvecTypeParticulier)
                .addGap(31, 31, 31)
                .addGroup(TenueAvecTypeParticulierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(validerTenueAvecTypeParticulier)
                    .addComponent(annuleTenueAvecTypePartculier))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        MainFrame.add(TenueAvecTypeParticulier, "TenueAvecTypeParticulier");

        TenueAvecContenuParticulier.setLayout(new java.awt.BorderLayout());

        validerTenueAvecContenuParticulier.setText("Valider");
        validerTenueAvecContenuParticulier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerTenueAvecContenuParticulierActionPerformed(evt);
            }
        });

        formeTenueAvecContenuParticulier.setText("Accordée à la forme");

        jLabel42.setText("Evenement:");

        evtTenueAvecContenuParticulier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les jours", "Sport", "Soirée" }));

        annuleTenueAvecContenuParticulier.setText("Annuler");
        annuleTenueAvecContenuParticulier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annuleTenueAvecContenuParticulierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(evtTenueAvecContenuParticulier, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(annuleTenueAvecContenuParticulier)
                                .addGap(23, 23, 23)
                                .addComponent(validerTenueAvecContenuParticulier))
                            .addComponent(formeTenueAvecContenuParticulier))))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(evtTenueAvecContenuParticulier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(formeTenueAvecContenuParticulier, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(validerTenueAvecContenuParticulier)
                    .addComponent(annuleTenueAvecContenuParticulier))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        TenueAvecContenuParticulier.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        MainFrame.add(TenueAvecContenuParticulier, "TenueAvecContenuParticulier");

        accesDressing.setText("Acceder à votre dressing");
        accesDressing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accesDressingActionPerformed(evt);
            }
        });

        ajoutUtilisateur.setText("Ajouter un utilisateur");
        ajoutUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajoutUtilisateurActionPerformed(evt);
            }
        });

        supprUtilisateur.setText("Supprimer un utilisateur");
        supprUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprUtilisateurActionPerformed(evt);
            }
        });

        jLabel4.setText("Identifiant :");

        idUtilisateur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                idUtilisateurMouseReleased(evt);
            }
        });
        idUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idUtilisateurActionPerformed(evt);
            }
        });

        identifiant.setText("Identifiant :");

        javax.swing.GroupLayout AccueilLayout = new javax.swing.GroupLayout(Accueil);
        Accueil.setLayout(AccueilLayout);
        AccueilLayout.setHorizontalGroup(
            AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccueilLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccueilLayout.createSequentialGroup()
                        .addGroup(AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(AccueilLayout.createSequentialGroup()
                                .addComponent(identifiant)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idSuppr))
                            .addGroup(AccueilLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(97, 97, 97)
                        .addGroup(AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(accesDressing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supprUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AccueilLayout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(ajoutUtilisateur)))
                .addGap(49, 49, 49))
        );
        AccueilLayout.setVerticalGroup(
            AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccueilLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addGroup(AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(idUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accesDressing))
                .addGap(45, 45, 45)
                .addGroup(AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supprUtilisateur)
                    .addComponent(identifiant)
                    .addComponent(idSuppr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(ajoutUtilisateur)
                .addContainerGap(169, Short.MAX_VALUE))
        );

        MainFrame.add(Accueil, "Accueil");

        AjoutChaussures.setPreferredSize(new java.awt.Dimension(476, 433));

        jLabel18.setText("type de chaussures : ");

        jLabel19.setText("couleur de chaussures :");

        typeChaussures.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "escarpins", "ballerines", "baskets", "bottes plates", "bottes à talons", "sandales" }));

        couleurChaussures.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bleu", "Bleu clair", "Bleu marine", "Turquoise", "Gris clair", "Argenté", "Gris foncé", "Marron clair", "Marron foncé", "Corail", "Orange", "Bordeau", "Brique", "Rouge", "Rose pale", "Rose fushia", "Rose foncé", "Mauve", "Violet", "Prune", "Blanc", "Jaune moutarde ", "Jaune", "Doré", "Noir", "Kaki", "Vert pale", "Vert", "Jean clair", "Jean marine", "Beige" }));

        validerAjoutChaussures.setText("Valider");
        validerAjoutChaussures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerAjoutChaussuresActionPerformed(evt);
            }
        });

        jLabel21.setText("image :");

        parcourirChaussures.setText("Parcourir ...");
        parcourirChaussures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcourirChaussuresActionPerformed(evt);
            }
        });

        annulerAjoutChaussures.setText("Annuler");
        annulerAjoutChaussures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerAjoutChaussuresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AjoutChaussuresLayout = new javax.swing.GroupLayout(AjoutChaussures);
        AjoutChaussures.setLayout(AjoutChaussuresLayout);
        AjoutChaussuresLayout.setHorizontalGroup(
            AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(annulerAjoutChaussures)
                    .addComponent(jLabel21)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                                .addComponent(parcourirChaussures)
                                .addGap(18, 18, 18)
                                .addComponent(photoChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(couleurChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(validerAjoutChaussures)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cheminPhotoChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        AjoutChaussuresLayout.setVerticalGroup(
            AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(typeChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(couleurChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(parcourirChaussures)
                            .addComponent(jLabel21)))
                    .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(photoChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cheminPhotoChaussures, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AjoutChaussuresLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(AjoutChaussuresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(validerAjoutChaussures)
                            .addComponent(annulerAjoutChaussures))))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        MainFrame.add(AjoutChaussures, "AjoutChaussures");

        jLabel24.setText("type :");

        typeV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tee-shirt", "chemisier", "pull", "veste", "manteau", "pantalon", "pantacourt", "jogging", "jupe", "short", "robe", "combinaison" }));
        typeV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeVActionPerformed(evt);
            }
        });

        jLabel25.setText("coupe:");

        jLabel26.setText("matière:");

        matiereV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "laine", "coton", "jean", "lin", "velours", "cuir", "dentelle", "daim", "satin", "paillette" }));

        jLabel27.setText("couleur:");

        couleurV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bleu", "Bleu clair", "Bleu marine", "Turquoise", "Gris clair", "Argenté", "Gris foncé", "Marron clair", "Marron foncé", "Corail", "Orange", "Bordeau", "Brique", "Rouge", "Rose pale", "Rose fushia", "Rose foncé", "Mauve", "Violet", "Prune", "Blanc", "Jaune moutarde ", "Jaune", "Doré", "Noir", "Kaki", "Vert pale", "Vert", "Jean clair", "Jean marine", "Beige" }));

        validerAjoutVetement.setText("valider");
        validerAjoutVetement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerAjoutVetementActionPerformed(evt);
            }
        });

        annulerAjoutVetement.setText("annuler");
        annulerAjoutVetement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerAjoutVetementActionPerformed(evt);
            }
        });

        jLabel35.setText("image:");

        parcourirVetement.setText("parcourir...");
        parcourirVetement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcourirVetementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AjoutVetementLayout = new javax.swing.GroupLayout(AjoutVetement);
        AjoutVetement.setLayout(AjoutVetementLayout);
        AjoutVetementLayout.setHorizontalGroup(
            AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AjoutVetementLayout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel35)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(typeV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coupeV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(matiereV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(couleurV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AjoutVetementLayout.createSequentialGroup()
                        .addComponent(parcourirVetement)
                        .addGap(18, 18, 18)
                        .addComponent(photoVetement, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(133, 133, 133))
            .addGroup(AjoutVetementLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(annulerAjoutVetement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(validerAjoutVetement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cheminPhotoVetement, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );
        AjoutVetementLayout.setVerticalGroup(
            AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjoutVetementLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(typeV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(coupeV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(matiereV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(15, 15, 15)
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(couleurV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AjoutVetementLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(parcourirVetement)
                            .addComponent(jLabel35)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AjoutVetementLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(photoVetement, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AjoutVetementLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cheminPhotoVetement, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AjoutVetementLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(AjoutVetementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(annulerAjoutVetement)
                            .addComponent(validerAjoutVetement))))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        MainFrame.add(AjoutVetement, "AjoutVetement");
        MainFrame.add(AffichageSuppression, "AffichageSuppression");

        mettreAuSale.setText("Ajouter des vêtements au sale");
        mettreAuSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mettreAuSaleActionPerformed(evt);
            }
        });

        mettreAuPropre.setText("Retirer des vêtements de la corbeille");
        mettreAuPropre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mettreAuPropreActionPerformed(evt);
            }
        });

        ko.setText("Annuler");
        ko.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                koActionPerformed(evt);
            }
        });

        validerCorbeille.setText("Valider");

        javax.swing.GroupLayout ConsulterCorbeilleLayout = new javax.swing.GroupLayout(ConsulterCorbeille);
        ConsulterCorbeille.setLayout(ConsulterCorbeilleLayout);
        ConsulterCorbeilleLayout.setHorizontalGroup(
            ConsulterCorbeilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsulterCorbeilleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConsulterCorbeilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AffichageCorbeille, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ConsulterCorbeilleLayout.createSequentialGroup()
                        .addComponent(mettreAuSale, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mettreAuPropre))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConsulterCorbeilleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ko, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(validerCorbeille, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)))
                .addContainerGap())
        );
        ConsulterCorbeilleLayout.setVerticalGroup(
            ConsulterCorbeilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConsulterCorbeilleLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(ConsulterCorbeilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mettreAuSale)
                    .addComponent(mettreAuPropre))
                .addGap(18, 18, 18)
                .addComponent(AffichageCorbeille, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ConsulterCorbeilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ko)
                    .addComponent(validerCorbeille))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        AffichageCorbeille.getAccessibleContext().setAccessibleName("AffichageCorbeille");

        MainFrame.add(ConsulterCorbeille, "ConsulterCorbeille");

        deconnexion.setText("Aller à ...");

        retourAccueilItem.setText("Retour Accueil");
        retourAccueilItem.setFocusCycleRoot(true);
        retourAccueilItem.setFocusPainted(true);
        retourAccueilItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourAccueilItemActionPerformed(evt);
            }
        });
        deconnexion.add(retourAccueilItem);

        notice.setText("Notice d'Utilisation");
        notice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noticeActionPerformed(evt);
            }
        });
        deconnexion.add(notice);

        deconnexionItem.setText("Deconnexion");
        deconnexionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deconnexionItemActionPerformed(evt);
            }
        });
        deconnexion.add(deconnexionItem);

        jMenuBar1.add(deconnexion);

        retour.setText("Retour");
        retour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                retourMousePressed(evt);
            }
        });
        jMenuBar1.add(retour);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(MainFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(MainFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void premiereConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_premiereConnexionActionPerformed
        CardLayout card = (CardLayout) MainFrame.getLayout();
        card.show(MainFrame, "Connexion");
    }//GEN-LAST:event_premiereConnexionActionPerformed

    private void connecterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connecterActionPerformed
        connecte = Initialisation.connexion(nomUtilisateur1.getText(), mdp.getText(), nomBase.getText());
        if (connecte) {
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "Accueilv2");
            try {
                Conseil.initialiserConseils();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InitFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "Connexion");
        }
    }//GEN-LAST:event_connecterActionPerformed

    private void accesDressingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accesDressingActionPerformed


        /*if (idUtilisateur.getText().equals("")){
         JOptionPane j = new JOptionPane();
         idUtilisateur.setBorder(BorderFactory.createLineBorder(Color.RED));
         j.showMessageDialog(Accueil, "Veuillez saisir un identifiant", "Erreur", JOptionPane.ERROR_MESSAGE);
         }else{
         try {
         Utilisateur user = Initialisation.accederDressing(Integer.parseInt(idUtilisateur.getText()));
         if (user!=null ){
         CardLayout card = (CardLayout) MainFrame.getLayout();
         card.show(MainFrame, "AccueilDressing");
         idDressing=user.getId();
         age.setText(Integer.toString(user.getAge()));
         taille.setText(Integer.toString(user.getTaille()));
         coulPreferee.setText(user.getCouleurPreferee().toString());
         coulCheveux.setText((user.getCouleurCheveux().toString())); 
         }else{
         // boite de dialogue :
         JOptionPane jop = new JOptionPane();
         jop.showMessageDialog(Accueil, "Identifiant incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
         }

         } catch (SQLException ex) {
         ex.printStackTrace();
         } catch (IOException ex) {
         ex.printStackTrace();
         }
         }*/
    }//GEN-LAST:event_accesDressingActionPerformed

    private void supprUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprUtilisateurActionPerformed
        Utilisateur user = new Utilisateur();
        try {
            boolean buser = user.supprimerUtilisateur(Integer.parseInt(idSuppr.getText()));

            // boite de dialogue : 
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(Accueil, "Utilisateur supprimé !", "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            Logger.getLogger(InitFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_supprUtilisateurActionPerformed

    private void nouveauCoulPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nouveauCoulPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nouveauCoulPActionPerformed

    private void validerAjoutUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerAjoutUtilisateurActionPerformed
        nouveauIdentifiant.setBorder(BorderFactory.createLineBorder(Color.gray));
        nouveauNom.setBorder(BorderFactory.createLineBorder(Color.gray));
        nouveauMdp.setBorder(BorderFactory.createLineBorder(Color.gray));
        nouveauPrenom.setBorder(BorderFactory.createLineBorder(Color.gray));
        Utilisateur user = new Utilisateur();
        try {
            if (nouveauNom.getText().equals("") || nouveauPrenom.getText().equals("") || nouveauIdentifiant.getText().equals("") || String.valueOf(nouveauMdp.getPassword()).equals("")) {
                JOptionPane j1 = new JOptionPane();
                j1.showMessageDialog(AjoutUtilisateur, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                if (nouveauNom.getText().equals("")) {
                    nouveauNom.setBorder(BorderFactory.createLineBorder(Color.RED));
                }
                if (nouveauPrenom.getText().equals("")) {
                    nouveauPrenom.setBorder(BorderFactory.createLineBorder(Color.RED));
                }
                if (nouveauIdentifiant.getText().equals("")) {
                    nouveauIdentifiant.setBorder(BorderFactory.createLineBorder(Color.RED));
                }
                if (String.valueOf(nouveauMdp.getPassword()).equals("")) {
                    nouveauMdp.setBorder(BorderFactory.createLineBorder(Color.RED));
                }
            } else if (user.identifiantDejaUtilise(nouveauIdentifiant.getText())) {
                JOptionPane j = new JOptionPane();
                j.showMessageDialog(AjoutUtilisateur, "Identifiant non disponible", "Erreur", JOptionPane.ERROR_MESSAGE);
                nouveauIdentifiant.setBorder(BorderFactory.createLineBorder(Color.RED));
            } else {
                JOptionPane jop = new JOptionPane();
                int option = jop.showConfirmDialog(AjoutUtilisateur, "Voulez-vous vraiment valider ?", "Validation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    boolean buser = user.ajouterUtilisateur(nouveauIdentifiant.getText(), String.valueOf(nouveauMdp.getPassword()), nouveauNom.getText(), nouveauPrenom.getText(), Integer.parseInt((String) nouveauAge.getSelectedItem()), Integer.parseInt((String) nouveauTaille.getSelectedItem()), CouleurCheveux.get((String) nouveauCoulC.getSelectedItem()), new Couleur(nouveauCoulP.getSelectedIndex() + 1), Signe.getfromInt(nouveauSigne.getSelectedIndex() + 1));
                    JOptionPane jop1 = new JOptionPane();
                    jop1.showMessageDialog(AjoutUtilisateur, "Utilisateur ajouté ! \n Votre identifiant est : " + nouveauIdentifiant.getText(), "Information", JOptionPane.INFORMATION_MESSAGE);

                    CardLayout card = (CardLayout) MainFrame.getLayout();
                    card.show(MainFrame, "Accueilv2");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InitFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_validerAjoutUtilisateurActionPerformed

    private void nouveauCoulCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nouveauCoulCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nouveauCoulCActionPerformed

    private void ajoutUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajoutUtilisateurActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) MainFrame.getLayout();
        card.show(MainFrame, "AjoutUtilisateur");
        oldPanel.add("Accueilv2");
    }//GEN-LAST:event_ajoutUtilisateurActionPerformed

    private void dressingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dressingActionPerformed
        try {
            AffichageVetementCouleurPreferee.removeAll();
            AffichageVetementSaison.removeAll();
            int nbLignesCouleur;
            int nbLignesSaison;
            retour.setVisible(true);
            jMenuBar1.setVisible(true);
            couleurPreferee.setText(coulPreferee.getText());
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "ConsulterDressing");
            oldPanel.add("AccueilDressing");
            saisonCourante.setText(Tenue.determinerSaison());
            ArrayList<Vetement> vetementsCouleurs = new ArrayList(Vetement.getVetementsCouleurPreferee(idDressing));
            ArrayList<Vetement> vetementsSaison = new ArrayList(Vetement.getVetementsSaison(idDressing));
            nbLignesCouleur = (int) Math.ceil(vetementsCouleurs.size() / 3.0);
            nbLignesSaison = (int) Math.ceil(vetementsSaison.size() / 3.0);
            if (vetementsCouleurs.isEmpty()) {
                JLabel i = new JLabel("Vous n'avez pas de vêtements de cette couleur");
                AffichageVetementCouleurPreferee.add(i, BorderLayout.CENTER);
            } else {
                GridLayout grid = new GridLayout(nbLignesCouleur, 3, 30, 20);
                JPanel jp = new JPanel();
                jp.removeAll();
                jp.setLayout(grid);
                JLabel photo = null;
                for (Vetement v : vetementsCouleurs) {
                    jp.add(photo = new JLabel());
                    photo.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + v.getImage()).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    photo.setPreferredSize(new Dimension(60, 60));
                }
                JScrollPane jsp = new JScrollPane(jp);
                jsp.setPreferredSize(new Dimension(300, 130));
                AffichageVetementCouleurPreferee.add(jsp);
                AffichageVetementCouleurPreferee.revalidate();
                AffichageVetementCouleurPreferee.repaint();
            }

            if (vetementsSaison.isEmpty()) {
                JLabel j = new JLabel("Vous n'avez pas de vêtements pour cette saison");
                AffichageVetementSaison.add(j, BorderLayout.CENTER);
            } else {
                GridLayout grid1 = new GridLayout(nbLignesSaison, 3, 30, 20);
                JPanel jp1 = new JPanel();
                jp1.removeAll();
                jp1.setLayout(grid1);
                JLabel photo1 = null;
                for (Vetement v : vetementsSaison) {
                    jp1.add(photo1 = new JLabel());
                    photo1.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + v.getImage()).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    photo1.setPreferredSize(new Dimension(60, 60));
                }
                JScrollPane jsp1 = new JScrollPane(jp1);
                jsp1.setPreferredSize(new Dimension(300, 150));
                AffichageVetementSaison.add(jsp1);
                AffichageVetementSaison.revalidate();
                AffichageVetementSaison.repaint();
                ConsulterDressing.repaint();
            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_dressingActionPerformed

    private void sacsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sacsActionPerformed

        AffichageDressing.removeAll();
        DefaultListModel<Contenu> dlmSac = new DefaultListModel<Contenu>();
        JList listeObjets = new JList(dlmSac);
        JScrollPane jsp = new JScrollPane(listeObjets);
        jsp.setPreferredSize(new Dimension(200, 440));

        if (Sac.getSacs().isEmpty()) {
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(ConsulterDressing, "Vous n'avez pas de sacs !", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int i = 0;
            for (Sac s : Sac.getSacs().values()) {
                dlmSac.add(i, s);
                i++;
            }

            listeObjets.setCellRenderer(new JListRenderer());
            listeObjets.addMouseListener(new JListMouseListener(listeObjets, detailContenu, imageContenu, caracteristique1, caracteristique2, caracteristique3, caracteristique4, mettreAuSalePropre, idobjet));
            //listeObjets.setVisibleRowCount(i);

            oldPanel.add("ConsulterDressing");
            AffichageDressing.add(jsp, BorderLayout.CENTER);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AffichageDressing");
            AffichageDressing.repaint();
        }


    }//GEN-LAST:event_sacsActionPerformed

    private void chaussuresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chaussuresActionPerformed
        AffichageDressing.removeAll();
        DefaultListModel<Contenu> dlmSac = new DefaultListModel<Contenu>();
        JList listeObjets = new JList(dlmSac);
        JScrollPane jsp = new JScrollPane(listeObjets);
        jsp.setPreferredSize(new Dimension(200, 440));

        if (Chaussures.getChaussures().isEmpty()) {
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(ConsulterDressing, "Vous n'avez pas de chaussures !", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int i = 0;
            for (Chaussures c : Chaussures.getChaussures().values()) {
                dlmSac.add(i, c);
                i++;
            }

            listeObjets.setCellRenderer(new JListRenderer());
            listeObjets.addMouseListener(new JListMouseListener(listeObjets, detailContenu, imageContenu, caracteristique1, caracteristique2, caracteristique3, caracteristique4, mettreAuSalePropre, idobjet));

            oldPanel.add("ConsulterDressing");
            AffichageDressing.add(jsp, BorderLayout.CENTER);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AffichageDressing");
            AffichageDressing.repaint();
        }
    }//GEN-LAST:event_chaussuresActionPerformed

    private void pantalonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pantalonsActionPerformed
        // TODO add your handling code here:
        consulterVetements(6);
    }//GEN-LAST:event_pantalonsActionPerformed

    private void chemisiersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chemisiersActionPerformed
        // TODO add your handling code here:
        consulterVetements(2);
    }//GEN-LAST:event_chemisiersActionPerformed

    private void teeshirtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teeshirtsActionPerformed
        // TODO add your handling code here:
        consulterVetements(1);
    }//GEN-LAST:event_teeshirtsActionPerformed

    private void pullsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pullsActionPerformed
        // TODO add your handling code here:
        consulterVetements(3);
    }//GEN-LAST:event_pullsActionPerformed

    private void combinaisonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combinaisonsActionPerformed
        // TODO add your handling code here:
        consulterVetements(12);
    }//GEN-LAST:event_combinaisonsActionPerformed

    private void jupesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jupesActionPerformed
        // TODO add your handling code here:
        consulterVetements(9);
    }//GEN-LAST:event_jupesActionPerformed

    private void jogginsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jogginsActionPerformed
        // TODO add your handling code here:
        consulterVetements(8);
    }//GEN-LAST:event_jogginsActionPerformed

    private void manteauxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manteauxActionPerformed
        // TODO add your handling code here:
        consulterVetements(5);
    }//GEN-LAST:event_manteauxActionPerformed

    private void pantacourtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pantacourtsActionPerformed
        // TODO add your handling code here:
        consulterVetements(7);
    }//GEN-LAST:event_pantacourtsActionPerformed

    private void robesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_robesActionPerformed
        // TODO add your handling code here:
        consulterVetements(11);
    }//GEN-LAST:event_robesActionPerformed

    private void shortsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shortsActionPerformed
        // TODO add your handling code here:
        consulterVetements(10);
    }//GEN-LAST:event_shortsActionPerformed

    private void vestesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vestesActionPerformed
        // TODO add your handling code here:
        consulterVetements(4);
    }//GEN-LAST:event_vestesActionPerformed

    private void ajoutContenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajoutContenuActionPerformed
        String[] ajout = {"Vetement", "Sac", "Chaussures"};
        JOptionPane jop = new JOptionPane();
        int rang = jop.showOptionDialog(ConsulterDressing,
                "Que voulez-vous ajouter ?",
                "Ajout",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                ajout,
                ajout[2]);

        CardLayout card = (CardLayout) MainFrame.getLayout();
        jMenuBar1.setVisible(false);
        switch (rang) {
            case 0:
                cheminPhotoVetement.setText("");
                photoVetement.setIcon(null);
                if (coupeV.getSelectedItem() != null) {
                    coupeV.setSelectedIndex(0);
                }
                typeV.setSelectedIndex(0);
                couleurV.setSelectedIndex(0);
                matiereV.setSelectedIndex(0);
                card.show(MainFrame, "AjoutVetement");
                break;
            case 1:
                typeSac.setSelectedIndex(0);
                couleurSac.setSelectedIndex(0);
                photoSac.setIcon(null);
                cheminPhotoSac.setText("");
                card.show(MainFrame, "AjoutSac");
                break;
            case 2:
                typeChaussures.setSelectedIndex(0);
                couleurChaussures.setSelectedIndex(0);
                photoChaussures.setIcon(null);
                cheminPhotoChaussures.setText("");
                card.show(MainFrame, "AjoutChaussures");
                break;
        }
    }//GEN-LAST:event_ajoutContenuActionPerformed

    private void supprimerContenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimerContenuActionPerformed
        int nbLignes = 0;
        ArrayList<Contenu> contenus = new ArrayList<Contenu>();
        String chemin = "", message = "";
        caract1.setText("");
        caract2.setText("");
        caract3.setText("");
        caract4.setText("");
        String[] ajout = {"Vetement", "Sac", "Chaussures"};
        JOptionPane jop = new JOptionPane();
        int rang = jop.showOptionDialog(ConsulterDressing,
                "Que voulez-vous supprimer ?",
                "Suppression",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                ajout,
                ajout[2]);
        CardLayout card = (CardLayout) MainFrame.getLayout();
        AffichageSuppression.removeAll();
        jMenuBar1.setVisible(true);
        retour.setVisible(true);
        switch (rang) {
            case 0:
                contenus = new ArrayList(Vetement.getVetements().values());
                chemin = "images/vetements/";
                message = "Vous n'avez pas de vêtements !";
                break;
            case 1:
                contenus = new ArrayList(Sac.getSacs().values());
                chemin = "images/sacs/";
                message = "Vous n'avez pas de sacs !";
                break;
            case 2:
                contenus = new ArrayList(Chaussures.getChaussures().values());
                chemin = "images/chaussures/";
                message = "Vous n'avez pas de chaussures !";
                break;
        }
        if (contenus.isEmpty() && !message.equals("")) {
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(ConsulterDressing, message, "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (!contenus.isEmpty()) {
            nbLignes = (int) Math.ceil(contenus.size() / 5.0);
            GridLayout grid = new GridLayout(nbLignes, 5, 30, 20);
            JPanel jp = new JPanel();
            jp.setLayout(grid);
            JButton button = null;
            for (Contenu c : contenus) {
                jp.add(button = new JButton(new ImageIcon(new ImageIcon(chemin + c.getImage()).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT))));
                button.setPreferredSize(new Dimension(80, 80));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        idcontenu.setText(Integer.toString(c.getIdObjet()));
                        idcontenu.setVisible(false);
                        if (c instanceof Sac) {
                            Sac s = (Sac) c;
                            photoContenu.setIcon(new ImageIcon(new ImageIcon("images/sacs/" + c.getImage()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                            caract1.setText(s.getTypeS().getNom());
                            caract2.setText(String.valueOf(s.getCouleur().toString()));
                        }
                        if (c instanceof Chaussures) {
                            Chaussures ch = (Chaussures) c;
                            photoContenu.setIcon(new ImageIcon(new ImageIcon("images/chaussures/" + c.getImage()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                            caract1.setText(ch.getTypeC().getNom());
                            caract2.setText(String.valueOf(ch.getCouleur().toString()));
                        }
                        if (c instanceof Vetement) {
                            Vetement v = (Vetement) c;
                            photoContenu.setIcon(new ImageIcon(new ImageIcon("images/vetements/" + c.getImage()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                            caract1.setText(v.getType().toString());
                            caract2.setText(String.valueOf(v.getCouleur().toString()));
                            caract3.setText(v.getCoupe().toString());
                            caract4.setText(v.getMatiere().toString());
                        }
                        suppressionContenu.pack();
                        suppressionContenu.setVisible(true);
                    }
                }
                );
            }
            JScrollPane jsp = new JScrollPane(jp);
            jsp.setPreferredSize(new Dimension(560, 450));
            AffichageSuppression.add(jsp, BorderLayout.CENTER);
            card.show(MainFrame, "AffichageSuppression");
            AffichageSuppression.repaint();
        }
        oldPanel.add("ConsulterDressing");

    }//GEN-LAST:event_supprimerContenuActionPerformed

    private void mdpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mdpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mdpActionPerformed

    private void validerAjoutSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerAjoutSacActionPerformed
        Sac sac = new Sac();
        String cheminImage = "sac-icone.jpg";
        try {
            if (cheminPhotoSac.getText().length() > 0) {
                cheminImage = cheminPhotoSac.getText();
            }
            boolean bsac = sac.ajouterSac(this.getIdDressing(), TypeSac.getfromInt(typeSac.getSelectedIndex() + 1), new Couleur(couleurSac.getSelectedIndex() + 1), cheminImage);
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(AjoutSac, "Sac ajouté ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "ConsulterDressing");
            jMenuBar1.setVisible(true);
            retour.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(InitFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_validerAjoutSacActionPerformed

    private void couleurSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_couleurSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_couleurSacActionPerformed

    private void validerAjoutChaussuresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerAjoutChaussuresActionPerformed
        Chaussures c = new Chaussures();
        String cheminImage = "chaussures-icone.jpeg";
        try {
            if (cheminPhotoChaussures.getText().length() > 0) {
                cheminImage = cheminPhotoChaussures.getText();
            }
            boolean bc = c.ajouterChaussures(this.getIdDressing(), TypeChaussures.getfromInt(typeChaussures.getSelectedIndex() + 1), new Couleur(couleurChaussures.getSelectedIndex() + 1), cheminImage);
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(AjoutChaussures, "Chaussures ajoutées ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "ConsulterDressing");
            jMenuBar1.setVisible(true);
            retour.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(InitFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_validerAjoutChaussuresActionPerformed


    private void parcourirSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcourirSacActionPerformed
        int returnVal = jFileChooser1.showOpenDialog(AjoutSac);
        if (jFileChooser1.APPROVE_OPTION == returnVal) {
            String completeFileName = jFileChooser1.getSelectedFile().getName();
            ImageIcon icon = new ImageIcon(new ImageIcon("images/sacs/" + completeFileName).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
            photoSac.setIcon(icon);
            cheminPhotoSac.setText(completeFileName);
        }
    }//GEN-LAST:event_parcourirSacActionPerformed

    private void parcourirChaussuresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcourirChaussuresActionPerformed

        int returnVal = jFileChooser1.showOpenDialog(AjoutChaussures);
        if (jFileChooser1.APPROVE_OPTION == returnVal) {
            String completeFileName = jFileChooser1.getSelectedFile().getName();
            ImageIcon icon = new ImageIcon(new ImageIcon("images/chaussures/" + completeFileName).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
            photoChaussures.setIcon(icon);
            cheminPhotoChaussures.setText(completeFileName);
        }
    }//GEN-LAST:event_parcourirChaussuresActionPerformed

    private void annulerAjoutUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulerAjoutUtilisateurActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(AjoutUtilisateur, "Voulez-vous vraiment annuler ?", "Annulation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "Accueilv2");
        }
    }//GEN-LAST:event_annulerAjoutUtilisateurActionPerformed

    private void idUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idUtilisateurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idUtilisateurActionPerformed

    private void idUtilisateurMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_idUtilisateurMouseReleased
        idUtilisateur.setBorder(BorderFactory.createLineBorder(Color.gray));
    }//GEN-LAST:event_idUtilisateurMouseReleased

    private void identifiantUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identifiantUtilisateurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_identifiantUtilisateurActionPerformed

    private void connectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionActionPerformed
        jMenuBar1.setVisible(true);
        identifiantUtilisateur.setBorder(BorderFactory.createLineBorder(Color.gray));
        mdpUtilisateur.setBorder(BorderFactory.createLineBorder(Color.gray));
        if (identifiantUtilisateur.getText().equals("") || mdpUtilisateur.getPassword().equals("")) {
            JOptionPane j = new JOptionPane();
            if (identifiantUtilisateur.getText().equals("")) {
                identifiantUtilisateur.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            if (String.valueOf(mdpUtilisateur.getPassword()).equals("")) {
                mdpUtilisateur.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            j.showMessageDialog(Accueil, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Utilisateur user = Initialisation.accederDressing(identifiantUtilisateur.getText(), String.valueOf(mdpUtilisateur.getPassword()));
                if (user != null) {
                    CardLayout card = (CardLayout) MainFrame.getLayout();
                    card.show(MainFrame, "AccueilDressing");
                    idDressing = user.getId();
                    idUser = user.getId();
                    age.setText(Integer.toString(user.getAge()));
                    taille.setText(Integer.toString(user.getTaille()));
                    coulPreferee.setText(user.getCouleurPreferee().toString());
                    coulCheveux.setText((user.getCouleurCheveux().toString()));
                    ArrayList<Conseil> conseils = new ArrayList<Conseil>();
                    switch (user.getSigneUtilisateur()) {
                        case Huit:
                            conseils = Conseil.getConseilsHuit();
                            break;
                        case O:
                            conseils = Conseil.getConseilsO();
                            break;
                        case A:
                            conseils.addAll(Conseil.getConseilsA());
                            break;
                        case V:
                            conseils = Conseil.getConseilsV();
                            break;
                        case X:
                            conseils = Conseil.getConseilsX();
                            break;
                        case H:
                            conseils = Conseil.getConseilsH();
                            break;
                    }
                    Random rand = new Random();
                    int alea = rand.nextInt(conseils.size());
                    champConseil.setText(conseils.get(alea).getDescription());
                } else {
                    // boite de dialogue :
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(Accueil, "Identifiant ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
                    identifiantUtilisateur.setBorder(BorderFactory.createLineBorder(Color.RED));
                    mdpUtilisateur.setBorder(BorderFactory.createLineBorder(Color.RED));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_connectionActionPerformed

    private void creationCompteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creationCompteActionPerformed
        nouveauNom.setText("");
        nouveauPrenom.setText("");
        nouveauAge.setSelectedIndex(0);
        nouveauTaille.setSelectedIndex(0);
        nouveauCoulP.setSelectedIndex(0);
        nouveauCoulC.setSelectedIndex(0);
        nouveauIdentifiant.setText("");
        nouveauMdp.setText("");
        nouveauSigne.setSelectedItem("");
        CardLayout card = (CardLayout) MainFrame.getLayout();
        card.show(MainFrame, "AjoutUtilisateur");
    }//GEN-LAST:event_creationCompteActionPerformed

    private void typeVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeVActionPerformed
        final List<String> values = new ArrayList<String>();
        if (typeV.getSelectedIndex() == 0 || typeV.getSelectedIndex() == 1 || typeV.getSelectedIndex() == 2 || typeV.getSelectedIndex() == 3 || typeV.getSelectedIndex() == 4) {
            values.add("Cintre");
            values.add("Droit");
            values.add("Large");
        } else if (typeV.getSelectedIndex() == 5 || typeV.getSelectedIndex() == 6 || typeV.getSelectedIndex() == 7) {
            values.add("Droit");
            values.add("Slim");
            values.add("Evase");
            values.add("Baggy");
        } else if (typeV.getSelectedIndex() == 8 || typeV.getSelectedIndex() == 9 || typeV.getSelectedIndex() == 10 || typeV.getSelectedIndex() == 11) {
            values.add("Court");
            values.add("Long");
        }
        coupeV.setModel(new DefaultComboBoxModel(values.toArray()));
    }//GEN-LAST:event_typeVActionPerformed

    private void annulerAjoutVetementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulerAjoutVetementActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(AjoutVetement, "Voulez-vous vraiment annuler ?", "Annulation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            retour.setVisible(true);
            jMenuBar1.setVisible(true);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "ConsulterDressing");
        }
    }//GEN-LAST:event_annulerAjoutVetementActionPerformed

    private void validerAjoutVetementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerAjoutVetementActionPerformed
        Vetement v = new Vetement();
        String cheminImage = "";
        try {
            if (coupeV.getSelectedItem() != null) {
                if (cheminPhotoVetement.getText().length() > 0) {
                    cheminImage = cheminPhotoVetement.getText();
                } else {
                    switch (TypeVetement.getfromInt(typeV.getSelectedIndex() + 1)) {
                        case Teeshirt:
                            cheminImage = "teeshirt-icone.png";
                            break;
                        case Chemisier:
                            cheminImage = "chemisier-icone.png";
                            break;
                        case Pull:
                            cheminImage = "pull-icone.png";
                            break;
                        case Veste:
                            cheminImage = "veste-icone.png";
                            break;
                        case Manteau:
                            cheminImage = "manteau-icone.png";
                            break;
                        case Jogging:
                            cheminImage = "jogging-icone.jpeg";
                            break;
                        case Pantalon:
                            cheminImage = "pantalon-icone.png";
                            break;
                        case Pantacourt:
                            cheminImage = "pantacourt-icone.jpeg";
                            break;
                        case Jupe:
                            cheminImage = "jupe-icone.png";
                            break;
                        case Robe:
                            cheminImage = "robe-icone.png";
                            break;
                        case Combinaison:
                            cheminImage = "combinaison-icone.jpeg";
                            break;
                        case Short:
                            cheminImage = "short-icone.png";
                            break;
                    }
                }
                boolean bv = v.ajouterVetement(this.getIdDressing(), new Couleur(couleurV.getSelectedIndex() + 1), CoupeVetement.get((String) coupeV.getSelectedItem()), Matiere.getfromInt(matiereV.getSelectedIndex() + 1), TypeVetement.getfromInt(typeV.getSelectedIndex() + 1), cheminImage);
                JOptionPane jop1 = new JOptionPane();
                jop1.showMessageDialog(AjoutVetement, "Vetement ajouté ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
                CardLayout card = (CardLayout) MainFrame.getLayout();
                card.show(MainFrame, "ConsulterDressing");
                jMenuBar1.setVisible(true);
                retour.setVisible(true);

                this.dressingActionPerformed(evt);
            } else {
                JOptionPane.showMessageDialog(AjoutVetement, "Veuillez remplir tous les champs ", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InitFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_validerAjoutVetementActionPerformed

    private void deconnexionItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deconnexionItemActionPerformed
        JOptionPane jop = new JOptionPane();
        if (connecte) {
            int option = jop.showConfirmDialog(AccueilDressing, "Voulez-vous vraiment vous déconnecter ?", "Deconnexion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                retour.setVisible(false);
                jMenuBar1.setVisible(false);
                identifiantUtilisateur.setText("");
                mdpUtilisateur.setText("");
                CardLayout card = (CardLayout) MainFrame.getLayout();
                card.show(MainFrame, "Accueilv2");
            }
        } else {
            jop.showMessageDialog(null, "Vous n'êtes pas encore connecté !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deconnexionItemActionPerformed

    private void retourAccueilItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retourAccueilItemActionPerformed
        JOptionPane jop = new JOptionPane();
        if (connecte) {

            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AccueilDressing");

        } else {
            jop.showMessageDialog(null, "Vous n'êtes pas encore connecté !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_retourAccueilItemActionPerformed

    private void retourMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retourMousePressed
        JOptionPane jop = new JOptionPane();
        CardLayout card = (CardLayout) MainFrame.getLayout();
        card.show(MainFrame, oldPanel.get(oldPanel.size() - 1));
        if (oldPanel.get(oldPanel.size() - 1).equals("AccueilDressing")) {
            retour.setVisible(false);
            jMenuBar1.setVisible(true);
        }
        if (oldPanel.get(oldPanel.size() - 1).equals("ConsulterDressing")) {
            retour.setVisible(true);
            jMenuBar1.setVisible(true);
        }
        oldPanel.remove(oldPanel.size() - 1);
    }//GEN-LAST:event_retourMousePressed

    private void creerTenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creerTenueActionPerformed
        String[] tenue = {"Normale", "Avec un contenu particulier", "Avec un type particulier"};
        JOptionPane jop = new JOptionPane();
        int rang = jop.showOptionDialog(AccueilDressing,
                "Quel type de tenue voulez-vous créer ?",
                "Création",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tenue,
                tenue[2]);
        CardLayout card = (CardLayout) MainFrame.getLayout();
        switch (rang) {
            case 0:
                jMenuBar1.setVisible(false);
                retour.setVisible(false);
                card.show(MainFrame, "TenueNormale");
                break;
            case 1:
                jMenuBar1.setVisible(false);
                retour.setVisible(false);
                card.show(MainFrame, "TenueAvecContenuParticulier");
                sacscb.setEnabled(true);
                chaussurescb.setEnabled(true);
                vetementscb.setEnabled(true);

                panelChoixVetements.removeAll();
                dcmSac.removeAllElements();
                dcmChaussures.removeAllElements();
                dcmVetement.removeAllElements();

                if (Sac.getSacs() != null) {
                    dcmSac.addElement(null);
                    for (Sac s : Sac.getSacs().values()) {
                        dcmSac.addElement(s);
                    }
                }

                if (Chaussures.getChaussures() != null) {
                    dcmChaussures.addElement(null);
                    for (Chaussures c : Chaussures.getChaussures().values()) {
                        dcmChaussures.addElement(c);
                    }
                }

                if (Vetement.getVetements() != null) {
                    dcmVetement.addElement(null);
                    for (Vetement v : Vetement.getVetements().values()) {
                        dcmVetement.addElement(v);
                    }
                }

                sacscb.setRenderer(new JListRenderer());
                chaussurescb.setRenderer(new JListRenderer());
                vetementscb.setRenderer(new JListRenderer());
                JPanel jp1 = new JPanel();
                GridLayout grid = new GridLayout(2, 1);
                jp1.setLayout(grid);
                jp1.add(new JLabel("Sacs"));
                jp1.add(sacscb);

                panelChoixVetements.add(jp1, BorderLayout.WEST);

                JPanel jp2 = new JPanel();
                jp2.setLayout(grid);
                jp2.add(new JLabel("Chaussures"));
                jp2.add(chaussurescb);

                panelChoixVetements.add(jp2, BorderLayout.CENTER);

                JPanel jp3 = new JPanel();
                jp3.setLayout(grid);
                jp3.add(new JLabel("Vetements"));
                jp3.add(vetementscb);

                panelChoixVetements.add(jp3, BorderLayout.EAST);
                panelChoixVetements.setPreferredSize(new Dimension(400, 400));
                TenueAvecContenuParticulier.add(panelChoixVetements, BorderLayout.NORTH);

                break;
            case 2:
                jMenuBar1.setVisible(false);
                retour.setVisible(false);
                card.show(MainFrame, "TenueAvecTypeParticulier");
                break;
        }

    }//GEN-LAST:event_creerTenueActionPerformed

    private void validerTenueNormaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerTenueNormaleActionPerformed
        tenues.clear();
        tableauIdChoisis[0] = 0;
        tableauIdChoisis[1] = 0;
        tableauIdChoisis[2] = 0;
        avecForme = 2;
        if (formeTenueNormale.isSelected()) {
            avecForme = 1;
        } else {
            avecForme = 2;
        }
        typeTenue = 1;
        evenement = TypeEvenement.getfromInt(evtTenueNormale.getSelectedIndex() + 1);
        try {
            initSuivantPrecedent();
            creationTenue(tableauIdChoisis, vetementsTypeChoisis, typeTenue, avecForme, idUser, evenement);

            oldPanel.add("AccueilDressing");
        } catch (SQLException | TenueImpossibleException e) {
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AccueilDressing");
            JOptionPane.showMessageDialog(AccueilDressing, "Vous n'avez pas assez de vêtements pour créer une tenue", "Erreur", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_validerTenueNormaleActionPerformed

    private void validerTenueAvecTypeParticulierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerTenueAvecTypeParticulierActionPerformed
        tenues.clear();
        vetementsTypeChoisis.clear();

        if (formeTenueNormale.isSelected()) {
            avecForme = 1;
        } else {
            avecForme = 2;
        }
        TypeVetement type = TypeVetement.getfromInt(typeTenueAvecTypeParticulier.getSelectedIndex() + 1);

        ArrayList<Vetement> tab = new ArrayList<Vetement>(Vetement.getVetements().values());
        Tenue t = new Tenue();
        typeTenue = 3;
        vetementsTypeChoisis.addAll(Vetement.getVetements().values());

        evenement = TypeEvenement.getfromInt(evtTenueAvecTypeParticulier.getSelectedIndex() + 1);
        tableauIdChoisis[0] = 0;
        tableauIdChoisis[1] = 0;
        tableauIdChoisis[2] = 0;
        try {
            vetementsTypeChoisis = t.chercherVetementType(vetementsTypeChoisis, type);
            initSuivantPrecedent();
            creationTenue(tableauIdChoisis, vetementsTypeChoisis, typeTenue, avecForme, idDressing, evenement);

            oldPanel.add("AccueilDressing");
        } catch (SQLException e) {
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AccueilDressing");
            JOptionPane.showMessageDialog(AccueilDressing, "Vous n'avez pas assez de vêtements pour créer une tenue", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (TenueImpossibleException ex) {
            JOptionPane.showMessageDialog(AccueilDressing, "Vous n'avez pas assez de vêtements pour créer une tenue", "Erreur", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_validerTenueAvecTypeParticulierActionPerformed

    private void validerTenueAvecContenuParticulierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerTenueAvecContenuParticulierActionPerformed
        tenues.clear();
        Tenue t = new Tenue();
        tableauIdChoisis[0] = 0;
        tableauIdChoisis[1] = 0;
        tableauIdChoisis[2] = 0;
        if (sacscb.getSelectedItem() != null) {
            Contenu c1 = (Contenu) (sacscb.getSelectedItem());
            tableauIdChoisis[1] = c1.getIdObjet();
        }
        if (chaussurescb.getSelectedItem() != null) {
            Contenu c2 = (Contenu) (chaussurescb.getSelectedItem());
            tableauIdChoisis[2] = c2.getIdObjet();

        }
        if (vetementscb.getSelectedItem() != null) {
            Contenu c3 = (Contenu) (vetementscb.getSelectedItem());
            tableauIdChoisis[0] = c3.getIdObjet();

        }
        if (formeTenueNormale.isSelected()) {
            avecForme = 1;
        } else {
            avecForme = 2;
        }
        evenement = TypeEvenement.getfromInt(evtTenueAvecContenuParticulier.getSelectedIndex() + 1);
        typeTenue = 2;
        try {
            initSuivantPrecedent();
            creationTenue(tableauIdChoisis, vetementsTypeChoisis, typeTenue, avecForme, idUser, evenement);

            oldPanel.add("AccueilDressing");
        } catch (SQLException | TenueImpossibleException e) {
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AccueilDressing");
            JOptionPane.showMessageDialog(AccueilDressing, "Vous n'avez pas assez de vêtements pour créer une tenue", "Erreur", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_validerTenueAvecContenuParticulierActionPerformed

    private void mettreAuSalePropreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mettreAuSalePropreActionPerformed
        // TODO add your handling code here:
        JOptionPane jop = new JOptionPane();
        if (mettreAuSalePropre.getText().equals("Mettre au sale")) {
            int option = jop.showConfirmDialog(detailContenu, "Voulez-vous vraiment mettre ce vêtement au sale ?", "Corbeille à linge", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                Vetement.modifierSalePropre(Integer.parseInt(idobjet.getText()), idDressing, true);
                detailContenu.setVisible(false);
                CardLayout card = (CardLayout) MainFrame.getLayout();
                card.show(MainFrame, "ConsulterDressing");
                JOptionPane jop1 = new JOptionPane();
                jop1.showMessageDialog(ConsulterDressing, "Vêtement ajouté à la corbeille à linge", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            int option = jop.showConfirmDialog(detailContenu, "Voulez-vous vraiment retirer ce vêtement de la corbeille à linge ?", "Corbeille à linge", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                Vetement.modifierSalePropre(Integer.parseInt(idobjet.getText()), idDressing, false);
                detailContenu.setVisible(false);
                CardLayout card = (CardLayout) MainFrame.getLayout();
                card.show(MainFrame, "ConsulterDressing");
                JOptionPane jop1 = new JOptionPane();
                jop1.showMessageDialog(ConsulterDressing, "Vêtement retiré à la corbeille à linge", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_mettreAuSalePropreActionPerformed

    private void supprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimerActionPerformed
        // TODO add your handling code here:
        try {
            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(detailContenu, "Voulez-vous vraiment vous supprimer ?", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                Contenu.supprimer(Integer.parseInt(idobjet.getText()), idDressing);
                detailContenu.setVisible(false);
                CardLayout card = (CardLayout) MainFrame.getLayout();
                card.show(MainFrame, "ConsulterDressing");
                JOptionPane jop1 = new JOptionPane();
                jop1.showMessageDialog(ConsulterDressing, "Suppression effectuée", "Information", JOptionPane.INFORMATION_MESSAGE);
                this.dressingActionPerformed(evt);
            }

        } catch (SQLException e) {
        }
    }//GEN-LAST:event_supprimerActionPerformed

    private void parcourirVetementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcourirVetementActionPerformed
        // TODO add your handling code here:
        int returnVal = jFileChooser1.showOpenDialog(AjoutVetement);
        if (jFileChooser1.APPROVE_OPTION == returnVal) {
            String completeFileName = jFileChooser1.getSelectedFile().getName();
            ImageIcon icon = new ImageIcon(new ImageIcon("images/vetements/" + completeFileName).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
            photoVetement.setIcon(icon);
            cheminPhotoVetement.setText(completeFileName);
        }
    }//GEN-LAST:event_parcourirVetementActionPerformed

    private void boutonSupprimerContenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonSupprimerContenuActionPerformed
        // TODO add your handling code here:
        try {
            Contenu.supprimer(Integer.parseInt(idcontenu.getText()), idDressing);
            suppressionContenu.setVisible(false);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "ConsulterDressing");
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(ConsulterDressing, "Suppression effectuée", "Information", JOptionPane.INFORMATION_MESSAGE);
            this.dressingActionPerformed(evt);
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_boutonSupprimerContenuActionPerformed

    private void bouttonAnnulationSuppressionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bouttonAnnulationSuppressionActionPerformed
        // TODO add your handling code here:
        suppressionContenu.setVisible(false);
    }//GEN-LAST:event_bouttonAnnulationSuppressionActionPerformed

    private void dressingCompletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dressingCompletActionPerformed
        consulterVetements(0);
    }//GEN-LAST:event_dressingCompletActionPerformed

    private void corbeilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corbeilleActionPerformed
        // TODO add your handling code here:
        retour.setVisible(true);
        jMenuBar1.setVisible(true);
        oldPanel.add("AccueilDressing");
        CardLayout card = (CardLayout) MainFrame.getLayout();
        card.show(MainFrame, "ConsulterCorbeille");
        mettreAuSale.setVisible(true);
        mettreAuPropre.setVisible(true);
        validerCorbeille.setVisible(false);
        ko.setVisible(false);
        affichageCorbeille(1);
    }//GEN-LAST:event_corbeilleActionPerformed

    private void mettreAuPropreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mettreAuPropreActionPerformed
        // TODO add your handling code here:
        validerCorbeille.setVisible(true);
        ko.setVisible(true);
        mettreAuSale.setVisible(false);
        mettreAuPropre.setVisible(false);
        retour.setVisible(false);
        jMenuBar1.setVisible(false);
        affichageCorbeille(2);
    }//GEN-LAST:event_mettreAuPropreActionPerformed

    private void koActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_koActionPerformed
        // TODO add your handling code here:
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(ConsulterCorbeille, "Voulez-vous vraiment quitter ?", "Corbeille à linge", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            mettreAuSale.setVisible(true);
            mettreAuPropre.setVisible(true);
            validerCorbeille.setVisible(false);
            ko.setVisible(false);
            retour.setVisible(true);
            jMenuBar1.setVisible(true);
            affichageCorbeille(1);
        }
    }//GEN-LAST:event_koActionPerformed

    private void mettreAuSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mettreAuSaleActionPerformed
        // TODO add your handling code here:
        validerCorbeille.setVisible(true);
        ko.setVisible(true);
        mettreAuSale.setVisible(false);
        mettreAuPropre.setVisible(false);
        retour.setVisible(false);
        jMenuBar1.setVisible(false);
        affichageCorbeille(3);
    }//GEN-LAST:event_mettreAuSaleActionPerformed

    private void annulerAjoutSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulerAjoutSacActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(AjoutSac, "Voulez-vous vraiment annuler ?", "Annulation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            retour.setVisible(true);
            jMenuBar1.setVisible(true);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "ConsulterDressing");
        }
    }//GEN-LAST:event_annulerAjoutSacActionPerformed

    private void annulerAjoutChaussuresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulerAjoutChaussuresActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(AjoutChaussures, "Voulez-vous vraiment annuler ?", "Annulation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            retour.setVisible(true);
            jMenuBar1.setVisible(true);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "ConsulterDressing");
        }
    }//GEN-LAST:event_annulerAjoutChaussuresActionPerformed

    private void annuleTenueNormaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annuleTenueNormaleActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(TenueNormale, "Voulez-vous vraiment annuler ?", "Annulation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            jMenuBar1.setVisible(true);
            retour.setVisible(false);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AccueilDressing");
        }

    }//GEN-LAST:event_annuleTenueNormaleActionPerformed

    private void annuleTenueAvecTypePartculierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annuleTenueAvecTypePartculierActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(TenueAvecTypeParticulier, "Voulez-vous vraiment annuler ?", "Annulation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            jMenuBar1.setVisible(true);
            retour.setVisible(false);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AccueilDressing");
        }

    }//GEN-LAST:event_annuleTenueAvecTypePartculierActionPerformed

    private void annuleTenueAvecContenuParticulierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annuleTenueAvecContenuParticulierActionPerformed
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(TenueAvecContenuParticulier, "Voulez-vous vraiment annuler ?", "Annulation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            jMenuBar1.setVisible(true);
            retour.setVisible(false);
            CardLayout card = (CardLayout) MainFrame.getLayout();
            card.show(MainFrame, "AccueilDressing");
        }

    }//GEN-LAST:event_annuleTenueAvecContenuParticulierActionPerformed

    private void noticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noticeActionPerformed
        JDialog jd = new JDialog();
        Notice not = new Notice("notice.txt");

        JPanel jp = new JPanel();
        jp.add(new JLabel(not.getNotice()));
        JScrollPane jsp = new JScrollPane(jp);
        jsp.setPreferredSize(new Dimension(650, 500));
        jsp.repaint();
        jd.add(jsp);
        jd.pack();
        jd.setVisible(true);
    }//GEN-LAST:event_noticeActionPerformed

    private void nomUtilisateur1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomUtilisateur1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomUtilisateur1ActionPerformed

    private void affichageCorbeille(int styleDAffichage) {
        // 1 : affichage corbeille normal
        // 2 : affichage mettre vetement propre
        // 3 : affichage corbeille mettre vetement sale
        int nbLignes = 0;
        Integer i = 0;
        Boolean salePropre;
        ArrayList<Vetement> contenus = null;
        HashMap<Integer, Vetement> vets = new HashMap<Integer, Vetement>();
        HashMap<Integer, JCheckBox> boxes = new HashMap<Integer, JCheckBox>();

        AffichageCorbeille.removeAll();
        if (styleDAffichage != 3) {
            contenus = new ArrayList(Vetement.getVetementsSaleOuPropre(true));
            salePropre = true;
        } else {
            contenus = new ArrayList(Vetement.getVetementsSaleOuPropre(false));
            salePropre = false;
            mettreAuPropre.setEnabled(true);
        }

        if (contenus == null || contenus.isEmpty()) {
            if (styleDAffichage == 1) {
                JLabel j = new JLabel("Vous n'avez pas de vêtements sales");
                AffichageCorbeille.setLayout(new FlowLayout());
                AffichageCorbeille.add(j);
                AffichageCorbeille.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                mettreAuPropre.setEnabled(false);
            } else {
                JOptionPane j = new JOptionPane();
                j.showMessageDialog(ConsulterCorbeille, "Vous n'avez pas de vêtements propres", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            nbLignes = (int) Math.ceil(contenus.size() / 4.0);
            GridLayout grid = new GridLayout(nbLignes, 4, 30, 20);
            JPanel jp = new JPanel();
            jp.removeAll();
            jp.setLayout(grid);
            JComponent photo = null;
            for (Vetement v : contenus) {
                if (styleDAffichage == 1) {
                    jp.add(photo = new JLabel());
                    ((JLabel) (photo)).setIcon(new ImageIcon(new ImageIcon("images/vetements/" + v.getImage()).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
                    photo.setPreferredSize(new Dimension(80, 80));
                } else {
                    jp.add(photo = new JCheckBox());
                    String label = "<html><img src=\"file:images/vetements/" + v.getImage() + "\" width=\"60\" height=\"60\"/></html>";
                    ((JCheckBox) (photo)).setText(label);
                    boxes.put(i, (JCheckBox) photo);
                    vets.put(i, v);
                    i++;
                }
            }
            JScrollPane jsp = new JScrollPane(jp);
            jsp.setPreferredSize(new Dimension(500, 320));
            AffichageCorbeille.add(jsp);
            AffichageCorbeille.repaint();
            validerCorbeille.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Integer indice = 0; indice < boxes.size(); indice++) {
                        if ((boxes.get(indice)).isSelected()) {
                            Vetement vet = vets.get(indice);
                            Vetement.modifierSalePropre(vet.getIdObjet(), idDressing, !salePropre);
                        }
                    }
                    oldPanel.add("AccueilDressing");
                    CardLayout card = (CardLayout) MainFrame.getLayout();
                    card.show(MainFrame, "ConsulterCorbeille");
                    mettreAuSale.setVisible(true);
                    mettreAuPropre.setVisible(true);
                    validerCorbeille.setVisible(false);
                    retour.setVisible(true);
                    jMenuBar1.setVisible(true);
                    ko.setVisible(false);
                    affichageCorbeille(1);
                    vets.clear();
                    boxes.clear();
                }
            }
            );
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InitFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InitFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InitFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InitFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new InitFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Accueil;
    private javax.swing.JPanel AccueilDressing;
    private javax.swing.JPanel Accueilv2;
    private javax.swing.JPanel Affichage;
    private javax.swing.JPanel AffichageCorbeille;
    private javax.swing.JPanel AffichageDressing;
    private javax.swing.JPanel AffichageSuppression;
    public static javax.swing.JPanel AffichageTenue;
    private javax.swing.JPanel AffichageVetementCouleurPreferee;
    private javax.swing.JPanel AffichageVetementSaison;
    private javax.swing.JPanel AjoutChaussures;
    private javax.swing.JPanel AjoutSac;
    private javax.swing.JPanel AjoutUtilisateur;
    private javax.swing.JPanel AjoutVetement;
    private javax.swing.JPanel Connexion;
    private javax.swing.JPanel ConsulterCorbeille;
    private javax.swing.JPanel ConsulterDressing;
    public static javax.swing.JPanel MainFrame;
    private javax.swing.JPanel TenueAvecContenuParticulier;
    private javax.swing.JPanel TenueAvecTypeParticulier;
    private javax.swing.JPanel TenueNormale;
    private javax.swing.JButton accesDressing;
    private javax.swing.JLabel age;
    private javax.swing.JButton ajoutContenu;
    private javax.swing.JButton ajoutUtilisateur;
    private javax.swing.JButton annuleTenueAvecContenuParticulier;
    private javax.swing.JButton annuleTenueAvecTypePartculier;
    private javax.swing.JButton annuleTenueNormale;
    private javax.swing.JButton annulerAjoutChaussures;
    private javax.swing.JButton annulerAjoutSac;
    private javax.swing.JButton annulerAjoutUtilisateur;
    private javax.swing.JButton annulerAjoutVetement;
    private javax.swing.JButton boutonSupprimerContenu;
    private javax.swing.JButton bouttonAnnulationSuppression;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel caract1;
    private javax.swing.JLabel caract2;
    private javax.swing.JLabel caract3;
    private javax.swing.JLabel caract4;
    private javax.swing.JLabel caracteristique1;
    private javax.swing.JLabel caracteristique2;
    private javax.swing.JLabel caracteristique3;
    private javax.swing.JLabel caracteristique4;
    private javax.swing.JLabel champConseil;
    private javax.swing.JButton chaussures;
    private javax.swing.JLabel cheminPhotoChaussures;
    private javax.swing.JLabel cheminPhotoSac;
    private javax.swing.JLabel cheminPhotoVetement;
    private javax.swing.JButton chemisiers;
    private javax.swing.JButton combinaisons;
    private javax.swing.JButton connecter;
    private javax.swing.JButton connection;
    private javax.swing.JButton corbeille;
    private javax.swing.JLabel coulCheveux;
    private javax.swing.JLabel coulPreferee;
    private javax.swing.JComboBox couleurChaussures;
    private javax.swing.JLabel couleurPreferee;
    private javax.swing.JComboBox couleurSac;
    private javax.swing.JComboBox couleurV;
    private javax.swing.JComboBox coupeV;
    private javax.swing.JButton creationCompte;
    private javax.swing.JButton creerTenue;
    private javax.swing.JMenu deconnexion;
    private javax.swing.JMenuItem deconnexionItem;
    private javax.swing.JDialog detailContenu;
    private javax.swing.JButton dressing;
    private javax.swing.JButton dressingComplet;
    private javax.swing.JComboBox<String> evtTenueAvecContenuParticulier;
    private javax.swing.JComboBox<String> evtTenueAvecTypeParticulier;
    private javax.swing.JComboBox<String> evtTenueNormale;
    private javax.swing.JCheckBox formeTenueAvecContenuParticulier;
    private javax.swing.JCheckBox formeTenueAvecTypeParticulier;
    private javax.swing.JCheckBox formeTenueNormale;
    private javax.swing.JTextField idSuppr;
    private javax.swing.JTextField idUtilisateur;
    private javax.swing.JLabel idcontenu;
    private javax.swing.JLabel identifiant;
    private javax.swing.JTextField identifiantUtilisateur;
    private javax.swing.JLabel idobjet;
    private javax.swing.JLabel imageContenu;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton joggins;
    private javax.swing.JButton jupes;
    private javax.swing.JButton ko;
    private javax.swing.JButton manteaux;
    private javax.swing.JComboBox matiereV;
    private javax.swing.JTextField mdp;
    private javax.swing.JPasswordField mdpUtilisateur;
    private javax.swing.JButton mettreAuPropre;
    private javax.swing.JButton mettreAuSale;
    private javax.swing.JButton mettreAuSalePropre;
    private javax.swing.JTextField nomBase;
    private javax.swing.JTextField nomUtilisateur1;
    private javax.swing.JMenuItem notice;
    private javax.swing.JComboBox nouveauAge;
    private javax.swing.JComboBox nouveauCoulC;
    private javax.swing.JComboBox nouveauCoulP;
    private javax.swing.JTextField nouveauIdentifiant;
    private javax.swing.JPasswordField nouveauMdp;
    private javax.swing.JTextField nouveauNom;
    private javax.swing.JTextField nouveauPrenom;
    private javax.swing.JComboBox nouveauSigne;
    private javax.swing.JComboBox nouveauTaille;
    private javax.swing.JButton pantacourts;
    private javax.swing.JButton pantalons;
    private javax.swing.JButton parcourirChaussures;
    private javax.swing.JButton parcourirSac;
    private javax.swing.JButton parcourirVetement;
    private javax.swing.JLabel photoChaussures;
    private javax.swing.JLabel photoContenu;
    private javax.swing.JLabel photoSac;
    private javax.swing.JLabel photoVetement;
    private javax.swing.JButton premiereConnexion;
    private javax.swing.JButton pulls;
    private javax.swing.JMenu retour;
    private javax.swing.JMenuItem retourAccueilItem;
    private javax.swing.JButton robes;
    private javax.swing.JButton sacs;
    private javax.swing.JLabel saisonCourante;
    private javax.swing.JLabel saisonCourante1;
    private javax.swing.JButton shorts;
    private javax.swing.JButton supprUtilisateur;
    private javax.swing.JDialog suppressionContenu;
    private javax.swing.JButton supprimer;
    private javax.swing.JButton supprimerContenu;
    private javax.swing.JLabel taille;
    private javax.swing.JButton teeshirts;
    private javax.swing.JComboBox typeChaussures;
    private javax.swing.JComboBox typeSac;
    private javax.swing.JComboBox<String> typeTenueAvecTypeParticulier;
    private javax.swing.JComboBox typeV;
    private javax.swing.JButton validerAjoutChaussures;
    private javax.swing.JButton validerAjoutSac;
    private javax.swing.JButton validerAjoutUtilisateur;
    private javax.swing.JButton validerAjoutVetement;
    private javax.swing.JButton validerCorbeille;
    private javax.swing.JButton validerTenueAvecContenuParticulier;
    private javax.swing.JButton validerTenueAvecTypeParticulier;
    private javax.swing.JButton validerTenueNormale;
    private javax.swing.JButton vestes;
    // End of variables declaration//GEN-END:variables
    private DefaultComboBoxModel<Contenu> dcmSac = new DefaultComboBoxModel<Contenu>();
    private DefaultComboBoxModel<Contenu> dcmChaussures = new DefaultComboBoxModel<Contenu>();
    private DefaultComboBoxModel<Contenu> dcmVetement = new DefaultComboBoxModel<Contenu>();
    private JComboBox sacscb = new JComboBox(dcmSac);
    private JComboBox chaussurescb = new JComboBox(dcmChaussures);
    private JComboBox vetementscb = new JComboBox(dcmVetement);
    private JPanel panelChoixVetements = new JPanel();

    private void setContentPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
