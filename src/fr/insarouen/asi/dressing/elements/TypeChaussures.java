package fr.insarouen.asi.dressing.elements;

public enum TypeChaussures {

    Escarpins("Escarpins"),
    Ballerines("Ballerines"),
    Baskets("Baskets"), 
    Bottesplates("Bottes plattes"), 
    Bottesatalons("Bottes à talons"), 
    Sandales("Sandales");
    
    String chaine;
    
    TypeChaussures(String chaine){
        this.chaine = chaine;
    }
    
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

    public String getNom() {
        return chaine;
    }
}
