package com.kerware.simulateur.codeReusine;

/**
 * Calcul de la décote pour les foyers à faibles revenus.
 *
 *
 * La décote réduit (voire annule) l'impôt pour les contribuables dont le montant
 * d'impôt est inférieur à un seuil. Elle est différenciée selon que le foyer
 * comporte un ou deux déclarants.
 */
public class CalculateurDecote {
 
    // --- Seuils et décotes maximales pour un déclarant seul ---
    private static final double SEUIL_SEUL       = 1_929.0;
    private static final double DECOTE_MAX_SEUL  = 873.0;
 
    // --- Seuils et décotes maximales pour un couple (2 déclarants) ---
    private static final double SEUIL_COUPLE      = 3_191.0;
    private static final double DECOTE_MAX_COUPLE = 1_444.0;
 
    /** Taux de calcul de la décote. */
    private static final double TAUX_DECOTE = 0.4525;
 
    /**
     * Calcule la décote applicable à l'impôt.
     *
     * @param impotAvantDecote montant d'impôt avant décote (après plafonnement quotient familial)
     * @param partsDeclarants  nombre de parts des seuls déclarants (1 ou 2)
     * @return montant de la décote (toujours ≥ 0, jamais supérieur à l'impôt)
     */
    public long calculerDecote(double impotAvantDecote, double partsDeclarants) {
        double decote = 0.0;
 
        if (partsDeclarants == 1 && impotAvantDecote < SEUIL_SEUL) {
            decote = DECOTE_MAX_SEUL - (impotAvantDecote * TAUX_DECOTE);
        } else if (partsDeclarants == 2 && impotAvantDecote < SEUIL_COUPLE) {
            decote = DECOTE_MAX_COUPLE - (impotAvantDecote * TAUX_DECOTE);
        }
 
        decote = Math.round(decote);
 
        // La décote ne peut pas excéder l'impôt lui-même
        return (long) Math.min(decote, impotAvantDecote);
    }
}
