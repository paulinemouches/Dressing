package fr.insarouen.asi.dressing.elements;

public enum TypeVetement {

    Teeshirt, Chemisier, Pull, Veste, Manteau, Pantalon, Pantacourt, Jogging, Jupe, Short, Robe, Combinaison;
    
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
