package fr.insarouen.asi.dressing.elements;

public enum CoupeVetement {

    cintre, droit, large,  slim, evase, baggy, court,longue;
    
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
