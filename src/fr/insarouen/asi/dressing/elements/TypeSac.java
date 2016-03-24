package fr.insarouen.asi.dressing.elements;

public enum TypeSac {

    Sacados("Sac à dos"),
    Sacamain("Sac à main"), 
    Pochette("Pochette");
    
    private String chaine;
    
    TypeSac(String chaine){
        this.chaine = chaine;
    }
    
    public static TypeSac get(String saisie){
        for(TypeSac ts :  values()){
            if( saisie.equals(ts.toString()))
                return ts;
        }
        return null;
    }
    
    public static TypeSac getfromInt(int numero){
        for(TypeSac s :  values()){
            if( numero==(s.ordinal()+1))
                return s;
        }
        return null;
    }
    
    public String getNom() {
        return chaine;
    }
};
