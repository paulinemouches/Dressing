package fr.insarouen.asi.dressing.elements;

public enum TypeChaussures {

    Escarpins, Ballerines, Baskets, Bottesplates, Bottesatalons, Sandales;
    
    public static TypeChaussures get(String saisie){
        for(TypeChaussures tc :  values()){
            if( saisie.equals(tc.toString()))
                return tc;
        }
        return null;
    }
    
    public static TypeChaussures getfromInt(int numero){
        for(TypeChaussures tc :  values()){
            if( numero==(tc.ordinal()+1))
                return tc;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
