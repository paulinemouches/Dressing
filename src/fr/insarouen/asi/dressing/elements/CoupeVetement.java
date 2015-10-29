package fr.insarouen.asi.dressing.elements;

public enum CoupeVetement {

    Cintre, Droit, Large,  Slim, Evase, Baggy, Court,Longue;
    
           public static CoupeVetement get(String saisie){
        for(CoupeVetement cv :  values()){
            if( saisie.equals(cv.toString()))
                return cv;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
            
};
