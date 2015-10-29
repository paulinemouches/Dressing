package fr.insarouen.asi.dressing.elements;

public enum Niveau {

    Haut, Bas, Hautbas;
    
               public static Niveau get(String saisie){
        for(Niveau n :  values()){
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
