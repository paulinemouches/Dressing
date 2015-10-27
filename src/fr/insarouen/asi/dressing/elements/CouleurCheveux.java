package fr.insarouen.asi.dressing.elements;

public enum CouleurCheveux {
    blond, brun, roux, chatain;
    
   public static CouleurCheveux get(String saisie){
        for( CouleurCheveux c :  values()){
            if( saisie.equals(c.toString()))
                return c;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    
};
