package fr.insarouen.asi.dressing.elements;

public enum Matiere {

    Laine,Coton,Jean,Lin,Velours,Cuir,Dentelle,Daim, Satin,Paillete;
        
           public static Matiere get(String saisie){
        for(Matiere m :  values()){
            if( saisie.equals(m.toString()))
                return m;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
};
