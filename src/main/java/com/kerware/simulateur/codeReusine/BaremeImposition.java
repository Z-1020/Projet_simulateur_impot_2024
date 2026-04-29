package com.kerware.simulateur.codeReusine;

/**
 * Barème progressif de l'impôt sur le revenu 2024 (revenus 2023).
 *
 *
 * Chaque tranche définit une limite inférieure et le taux applicable
 * aux revenus compris dans cette tranche.
 */
public class BaremeImposition {
 
    /**
     * Borne inférieure de chaque tranche (en euros).
     * La borne supérieure est la borne inférieure de la tranche suivante.
     * La dernière tranche est ouverte (pas de plafond).
     */
    private static final int[] BORNES = {
        0,
        11_294,
        28_797,
        82_341,
        177_106,
        Integer.MAX_VALUE
    };
 
    /**
     * Taux applicable à chaque tranche.
     * BORNES[i] → BORNES[i+1] est taxé à TAUX[i].
     */
    private static final double[] TAUX = {
        0.00,   // 0 %   jusqu'à 11 294 €
        0.11,   // 11 %  de 11 294 € à 28 797 €
        0.30,   // 30 %  de 28 797 € à 82 341 €
        0.41,   // 41 %  de 82 341 € à 177 106 €
        0.45    // 45 %  au-delà de 177 106 €
    };
 
    /**
     * Calcule l'impôt brut pour un revenu imposable par part,
     * puis le multiplie par le nombre de parts.
     *
     * @param revenuImposableParPart revenu fiscal de référence divisé par le nombre de parts
     * @param nombreParts            nombre de parts total du foyer fiscal
     * @return impôt brut arrondi à l'euro
     */
    public long calculerImpotBrut(double revenuImposableParPart, double nombreParts) {
        double impot = 0.0;
 
        for (int i = 0; i < TAUX.length; i++) {
            if (revenuImposableParPart <= BORNES[i]) {
                break;
            }
            double borneHaute = Math.min(revenuImposableParPart, BORNES[i + 1]);
            impot += (borneHaute - BORNES[i]) * TAUX[i];
        }
 
        return Math.round(impot * nombreParts);
    }
}

