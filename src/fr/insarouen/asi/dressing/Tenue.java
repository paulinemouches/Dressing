package fr.insarouen.asi.dressing;

import java.util.Date;
import fr.insarouen.asi.dressing.elements.objets.Chaussures;
import fr.insarouen.asi.dressing.elements.objets.Sac;
import fr.insarouen.asi.dressing.elements.objets.Vetement;
import java.util.Random;

public class Tenue {

    private Sac sac;
    private Chaussures chaussures;
    private Vetement[] vetements;

    public Tenue() {
    }

    public String determinerSaison() {
        Date date = new Date();
        int mois = date.getMonth();
        int jour = date.getDay();
        if ((mois >= 2 && jour >= 21) || (mois <= 8 && jour <= 21)) {
            return "Printemps/Ete";
        }
        else {
            return "Automne/Hiver";
        }
    }

    public Tenue creerTenue() {
        String saison = determinerSaison();
        Random rand = new Random();
        int alea = rand.nextInt(2); // choisir dans hauts si alea =0, dans bas si alea =1, dans hautsbas sinon
        switch (alea) {
            case 0:
                //do{
                  // int tailleTableau = Vetement.getHauts().size;
                   // idée prendre un vetement aleétoire dans cette liste
               //}
                //while(la saison ne vaut pas toutes ou la saison actuelle et que la couche ne vaut pas 1)
        }
        // de meme pour case : 1 et case : 2
        // si le premier vetement est un hautbas on va chercher dans les chaussures puis dans les sacs
        // pour cela : recup couleur du premier vetement puis on cherche dans la matrice des couleurs les couleurs associées à celle ci par une note de 5
       // on cherche un sac de la couleur trouvée => si pas de sac on retourne dans la matrice des couleurs chercher une  association de note 4 etc..
        // si c'est un haut ou un bas on va chercher dans les bas ou hauts puis sac et chaussures de la même facon, gérer les couleurs
        return null;
    }

}
