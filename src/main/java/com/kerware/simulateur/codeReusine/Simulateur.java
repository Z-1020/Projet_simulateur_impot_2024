package com.kerware.simulateur.codeReusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Simulateur de l'impôt sur le revenu en France.
 *
 */
public class Simulateur {

    // -------------------------------------------------------------------------
    // Services injectés (composition)
    // -------------------------------------------------------------------------

    private final Abattement abattementService;
    private final CalculateurParts              calculateurParts;
    private final BaremeImposition              bareme;
    private final PlafonnementQuotientFamilial  plafonnement;
    private final CalculateurDecote             calculateurDecote;

    // -------------------------------------------------------------------------
    // État du formulaire 
    // -------------------------------------------------------------------------

    private int                revenuNet = 0;
    private SituationFamiliale situationFamiliale = null;
    private int                nombreEnfants = 0;
    private int                nombreEnfantsHandicapes = 0;
    private boolean            estParentIsole = false;


    // Dernier résultat calculé 
    private ResultatImpot dernierResultat = null;

    // -------------------------------------------------------------------------
    // Constructeurs
    // -------------------------------------------------------------------------

    /** Constructeur par défaut : utilise les implémentations standard. */
    public Simulateur() {
        this(
                new Abattement(),
                new CalculateurParts(),
                new BaremeImposition(),
                new PlafonnementQuotientFamilial(),
                new CalculateurDecote()
        );
    }

    /**
     * Constructeur permettant l'injection de dépendances (tests unitaires).
     */
    public Simulateur(
            Abattement abattementService,
            CalculateurParts              calculateurParts,
            BaremeImposition              bareme,
            PlafonnementQuotientFamilial  plafonnement,
            CalculateurDecote             calculateurDecote) {

        this.abattementService = abattementService;
        this.calculateurParts  = calculateurParts;
        this.bareme            = bareme;
        this.plafonnement      = plafonnement;
        this.calculateurDecote = calculateurDecote;
    }

    // -------------------------------------------------------------------------
    // API principale
    // -------------------------------------------------------------------------

    /**
     * Calcule l'impôt sur le revenu net et retourne le résultat détaillé.
     *
     * @param revenuNet                  revenu net imposable déclaré (en euros)
     * @param situationFamiliale         situation familiale du foyer
     * @param nombreEnfants              nombre d'enfants à charge
     * @param nombreEnfantsHandicapes    nombre d'enfants en situation de handicap
     * @param estParentIsole             true si le déclarant est parent isolé
     * @return  contenant l'impôt net et toutes les étapes intermédiaires
     * @throws IllegalArgumentException si les paramètres sont incohérents
     */
    public ResultatImpot calculer(
            int                revenuNet,
            SituationFamiliale situationFamiliale,
            int                nombreEnfants,
            int                nombreEnfantsHandicapes,
            boolean            estParentIsole) {

        validerParametres(revenuNet, nombreEnfants, nombreEnfantsHandicapes, estParentIsole);

        // --- Étape 1 : abattement et revenu fiscal de référence ---
        int abattement              = abattementService.calculerAbattement(revenuNet);
        int revenuFiscalDeReference = Math.max(0,revenuNet - abattement);


        // --- Étape 2 : nombre de parts ---
        double partsDeclarants = calculateurParts.calculerPartsDeclarants(situationFamiliale, nombreEnfants);
        double partsTotales    = calculateurParts.calculerPartsTotales(
                situationFamiliale, nombreEnfants, nombreEnfantsHandicapes, estParentIsole);

        // --- Étape 3 : impôt brut (déclarants seuls, puis foyer complet) ---
        long impotBrutDeclarants = bareme.calculerImpotBrut(
                revenuFiscalDeReference / partsDeclarants, partsDeclarants);

        long impotBrutFoyer = bareme.calculerImpotBrut(
                revenuFiscalDeReference / partsTotales, partsTotales);

        // --- Étape 4 : plafonnement du quotient familial ---
        long impotApresPlafonnement = Math.round(plafonnement.appliquerPlafonnement(
                impotBrutDeclarants, impotBrutFoyer, partsDeclarants, partsTotales));

        // --- Étape 5 : décote ---
        long decote   = calculateurDecote.calculerDecote(impotApresPlafonnement, partsDeclarants);
        decote = Math.max(0,decote);
        long impotNet = impotApresPlafonnement - decote;
        impotNet = Math.max(0,impotNet);

        dernierResultat = new ResultatImpot(
                revenuNet,
                abattement,
                revenuFiscalDeReference,
                partsDeclarants,
                partsTotales,
                impotBrutDeclarants,
                impotBrutFoyer,
                impotApresPlafonnement,
                decote,
                impotNet);

        return dernierResultat;
    }

    // -------------------------------------------------------------------------
    // Compatibilité avec tests existants
    // -------------------------------------------------------------------------

    public void setRevenusNet(int revenuNet) {
        this.revenuNet = revenuNet;
    }

    public void setSituationFamilliale(SituationFamiliale situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public void setNbEnfantsACharge(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public void setNbEnfantsEnSituationDeHandicap(int nombreEnfantsHandicapes) {
        this.nombreEnfantsHandicapes = nombreEnfantsHandicapes;
    }

    public void setParentIsole(boolean estParentIsole) {
        this.estParentIsole = estParentIsole;
    }

    /**
     * Lance le calcul avec les valeurs définies via les setters.
     * Le résultat est accessible via les getters ci-dessous.
     */
    public void calculImpotSurRevenuNet() {
        dernierResultat = calculer(
                revenuNet, situationFamiliale,
                nombreEnfants, nombreEnfantsHandicapes, estParentIsole);
    }

    // --- Getters de compatibilité (délèguent au dernier résultat) ---

    public int getRevenuFiscalDeReference() {
        return dernierResultat == null ? 0 : dernierResultat.getRevenuFiscalDeReference();
    }

    public int getAbattement() {
        return dernierResultat == null ? 0 : dernierResultat.getAbattement();
    }

    public double getNbPartsFoyerFiscal() {
        return dernierResultat == null ? 0 : dernierResultat.getPartsTotales();
    }

    public int getDecote() {
        return dernierResultat == null ? 0 : (int) dernierResultat.getDecote();
    }

    public int getImpotSurRevenuNet() {
        return dernierResultat == null ? 0 : (int) dernierResultat.getImpotNet();
    }

    // --- Reset
    public void reset() {
        revenuNet = 0;
        situationFamiliale = null;
        nombreEnfants = 0;
        nombreEnfantsHandicapes = 0;
        estParentIsole = false;
        dernierResultat = null;
    }

    // -------------------------------------------------------------------------
    // Validation des paramètres
    // -------------------------------------------------------------------------

    private void validerParametres(int revenuNet, int nombreEnfants, int nombreEnfantsHandicapes, boolean estParentIsole) {
        if (revenuNet < 0) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif.");
        }
        if (nombreEnfants < 0) {
            throw new IllegalArgumentException("Le nombre d'enfants à charge ne peut pas être négatif.");
        }
        if (nombreEnfantsHandicapes < 0) {
            throw new IllegalArgumentException("Le nombre d'enfants en situation de handicap ne peut pas être négatif.");
        }
        if (nombreEnfantsHandicapes > nombreEnfants) {
            throw new IllegalArgumentException(
                    "Le nombre d'enfants en situation de handicap ne peut pas dépasser le nombre total d'enfants à charge.");
        }
        if(estParentIsole && nombreEnfants<1){
            throw new IllegalArgumentException("Ne peut pas être un parent isolé s'il n'a pas d'enfants.");
        }
    }

}
