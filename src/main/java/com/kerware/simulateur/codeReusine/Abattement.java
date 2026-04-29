package com.kerware.simulateur.codeReusine;

/**
 * Calcul de l'abattement (10 %).
 *
 *
 * L'abattement est plafonné et ne peut être inférieur à un minimum légal.
 */
public class Abattement {
 
    /** Taux d'abattement forfaitaire (10 %). */
    private static final double TAUX_ABATTEMENT     = 0.10;
 
    /** Abattement maximum autorisé en 2024 (en euros). */
    private static final int    ABATTEMENT_MAXIMUM  = 14_171;
 
    /** Abattement minimum garanti en 2024 (en euros). */
    private static final int    ABATTEMENT_MINIMUM  = 495;
 
    /**
     * Calcule l'abattement applicable au revenu net déclaré.
     *
     * @param revenuNet revenu net imposable avant abattement
     * @return montant de l'abattement (en euros, borné entre minimum et maximum légaux)
     */
    public int calculerAbattement(int revenuNet) {
        double abattement = revenuNet * TAUX_ABATTEMENT;
        abattement = Math.min(abattement, ABATTEMENT_MAXIMUM);
        abattement = Math.max(abattement, ABATTEMENT_MINIMUM);
        return (int) Math.round(abattement);
    }
}
 
