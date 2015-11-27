package fr.insarouen.asi.dressing.elements;

public enum Signe {

    Huit, V, O, H, A, X;
    
        public static Signe get(String saisie){
        for(Signe s :  values()){
            if( saisie.equals(s.toString()))
                return s;
        }
        return null;
        }
    
    public static Signe getfromInt(int numero){
        for(Signe s :  values()){
            if( numero==(s.ordinal()+1))
                return s;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
};
