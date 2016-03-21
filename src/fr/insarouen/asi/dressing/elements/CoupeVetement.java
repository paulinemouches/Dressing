package fr.insarouen.asi.dressing.elements;

public enum CoupeVetement {

    Cintre, Droit, Large,  Slim, Evase, Baggy, Court,Long;
    
    public static CoupeVetement get(String saisie){
        for(CoupeVetement cv :  values()){
            if( saisie.equals(cv.toString()))
                return cv;
        }
        return null;
    }
    
    public static CoupeVetement getfromInt(int numero){
        for(CoupeVetement c :  values()){
            if( numero==(c.ordinal()+1))
                return c;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
            
};
