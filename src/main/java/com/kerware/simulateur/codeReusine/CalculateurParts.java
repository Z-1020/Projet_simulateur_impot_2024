package com.kerware.simulateur.codeReusine;

/**
 * Calcul du nombre de parts fiscales d'un foyer.
 */
public class CalculateurParts {
 
    /** Nombre de parts accordées par enfant à charge (jusqu'au 2ème). */
    private static final double PART_PAR_ENFANT_DEUX_PREMIERS = 0.5;
 
    /** Nombre de parts accordées pour chaque enfant à partir du 3ème. */
    private static final double PART_PAR_ENFANT_SUIVANT       = 1.0;
 
    /** Majoration pour parent isolé avec enfant(s) à charge. */
    private static final double PART_PARENT_ISOLE             = 0.5;
 
    /** Majoration par enfant en situation de handicap. */
    private static final double PART_ENFANT_HANDICAPE         = 0.5;
 
    /**
     * Nombre de parts des seuls déclarants (sans enfants ni majorations).
     *
     * @param situationFamiliale situation familiale du foyer
     * @param nombreEnfants      nombre d'enfants à charge (nécessaire pour le cas VEUF)
     * @return nombre de parts des déclarants
     */
    public double calculerPartsDeclarants(SituationFamiliale situationFamiliale, int nombreEnfants) {
        return switch (situationFamiliale) {
            case CELIBATAIRE, DIVORCE -> 1.0;
            case MARIE                -> 2.0;
            case VEUF                 -> 1.0; // Le veuf sans enfant = 1 part (2 parts uniquement via quotient)
		default -> throw new IllegalArgumentException("Unexpected value: " + situationFamiliale);
        };
    }
 
    /**
     * Nombre de parts total du foyer fiscal (déclarants + enfants + majorations).
     *
     * @param situationFamiliale              situation familiale du foyer
     * @param nombreEnfants                   nombre d'enfants à charge
     * @param nombreEnfantsEnSituationHandicap nombre d'enfants en situation de handicap
     * @param estParentIsole                  true si le déclarant est en situation de parent isolé
     * @return nombre de parts total du foyer fiscal
     */
    public double calculerPartsTotales(
            SituationFamiliale situationFamiliale,
            int nombreEnfants,
            int nombreEnfantsEnSituationHandicap,
            boolean estParentIsole) {
 
        double partsDeclarants = calculerPartsDeclarants(situationFamiliale, nombreEnfants);
        double partsEnfants    = calculerPartsEnfants(nombreEnfants);
        double majorationParentIsole   = estParentIsole && nombreEnfants > 0 ? PART_PARENT_ISOLE : 0.0;
        double majorationHandicap      = nombreEnfantsEnSituationHandicap * PART_ENFANT_HANDICAPE;
 
        return partsDeclarants + partsEnfants + majorationParentIsole + majorationHandicap;
    }
 
    // -------------------------------------------------------------------------
    // Méthode privée
    // -------------------------------------------------------------------------
 
    /**
     * Calcule les parts liées aux seuls enfants à charge.
     *
     * Règle : 0,5 part par enfant pour les 2 premiers, 1 part entière à partir du 3ème.
     */
    private double calculerPartsEnfants(int nombreEnfants) {
        if (nombreEnfants == 0) {
            return 0.0;
        }
        if (nombreEnfants <= 2) {
            return nombreEnfants * PART_PAR_ENFANT_DEUX_PREMIERS;
        }
        // 2 premiers = 1 part, puis 1 part par enfant supplémentaire
        return 1.0 + (nombreEnfants - 2) * PART_PAR_ENFANT_SUIVANT;
    }
}
