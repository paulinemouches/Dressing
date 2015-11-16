package fr.insarouen.asi.dressing.elements;

public enum TypeEvenement{

    TousLesJours, Sport, Soiree;
    
    public static TypeEvenement get(String saisie){
        for(TypeEvenement te :  values()){
            if( saisie.equals(te.toString()))
                return te;
        }
        return null;
    }
    
    public static TypeEvenement getfromInt(int numero){
        for(TypeEvenement te :  values()){
            if( numero==(te.ordinal()+1))
                return te;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
