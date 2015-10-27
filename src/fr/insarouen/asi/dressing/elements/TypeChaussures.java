package fr.insarouen.asi.dressing.elements;

public enum TypeChaussures {

    escarpins, ballerines, baskets, bottesplates, bottesatalons, sandales;
    
       public static TypeChaussures get(String saisie){
        for(TypeChaussures tc :  values()){
            if( saisie.equals(tc.toString()))
                return tc;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
};
