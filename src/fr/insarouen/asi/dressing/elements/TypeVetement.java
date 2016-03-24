package fr.insarouen.asi.dressing.elements;

public enum TypeVetement {

    Teeshirt("Tee-shirt"), 
    Chemisier("Chemisier"), 
    Pull("Pull"), 
    Veste("Veste"), 
    Manteau("Manteau"), 
    Pantalon("Pantalon"), 
    Pantacourt("Pantacourt"), 
    Jogging("Jogging"), 
    Jupe("Jupe"), 
    Short("Short"), 
    Robe("Robe"), 
    Combinaison("Combinaison");
    
    private String chaine;
    
    TypeVetement(String chaine){
        this.chaine = chaine;
    }
    
    public static TypeVetement get(String saisie){
        for(TypeVetement n :  values()){
            if( saisie.equals(n.toString()))
                return n;
        }
        return null;
    }
    
    public static TypeVetement getfromInt(int numero){
        for(TypeVetement n :  values()){
            if( numero==(n.ordinal()+1))
                return n;
        }
        return null;
    }
    public String getNom() {
        return chaine;
    }

    @Override
    public String toString() {
        return super.toString();
    }
};
