package fr.insarouen.asi.dressing.elements;

public enum TypeVetement {

    teeshirt, chemisier, pull, veste, manteau, pantalon, pantacourt, jogging, jupe,shorts, robe, combinaison;
    
                   public static TypeVetement get(String saisie){
        for(TypeVetement n :  values()){
            if( saisie.equals(n.toString()))
                return n;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
};
