package com.kerware.simulateur.codeReusine;

/**
 * Plafonnement de l'avantage en impôt résultant du quotient familial.
 *
 *
 * L'avantage fiscal procuré par chaque demi-part supplémentaire (enfants, handicap,
 * parent isolé) est plafonné à un montant fixé annuellement.
 */
public class PlafonnementQuotientFamilial {
 
    /**
     * Avantage fiscal maximal accordé par demi-part supplémentaire en 2024 (en euros).
     */
    private static final double PLAFOND_PAR_DEMI_PART = 1_759.0;
 
    /**
     * Applique le plafonnement et retourne l'impôt après correction éventuelle.
     *
     * Si la réduction d'impôt liée aux demi-parts supplémentaires excède le plafond légal,
     * l'impôt est recalculé en appliquant uniquement le plafond autorisé.
     *
     * @param impotDeclarants impôt calculé sur la seule base des parts déclarants
     * @param impotFoyer      impôt calculé avec toutes les parts du foyer
     * @param partsDeclarants nombre de parts des déclarants
     * @param partsTotales    nombre de parts total du foyer
     * @return impôt après application du plafonnement
     */
    public double appliquerPlafonnement(
            double impotDeclarants,
            double impotFoyer,
            double partsDeclarants,
            double partsTotales) {
 
        double ecartParts         = partsTotales - partsDeclarants;
        double nombreDemiParts    = ecartParts / 0.5;
        double plafond            = nombreDemiParts * PLAFOND_PAR_DEMI_PART;
 
        double avantageReel = impotDeclarants - impotFoyer;
 
        if (avantageReel >= plafond) {
            // L'avantage dépasse le plafond : on limite la réduction
            return impotDeclarants - plafond;
        }
 
        return impotFoyer;
    }
}
