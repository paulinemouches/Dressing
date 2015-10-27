package fr.insarouen.asi.dressing.elements;

public enum TypeSac {

    sacados, sacamain, pochette;
    
           public static TypeSac get(String saisie){
        for(TypeSac ts :  values()){
            if( saisie.equals(ts.toString()))
                return ts;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
};
