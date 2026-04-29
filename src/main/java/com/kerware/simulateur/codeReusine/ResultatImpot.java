package com.kerware.simulateur.codeReusine;

/**
 * Résultat détaillé d'un calcul d'impôt sur le revenu.
 *
 * Classe immuable exposant toutes les étapes intermédiaires pour la
 * traçabilité et les tests unitaires.
 */
public final class ResultatImpot {
 
    private final int    revenuNet;
    private final int    abattement;
    private final int    revenuFiscalDeReference;
    private final double partsDeclarants;
    private final double partsTotales;
    private final long   impotBrutDeclarants;
    private final long   impotBrutFoyer;
    private final long   impotApresPlafonnement;
    private final long   decote;
    private final long   impotNet;
 
    /** Constructeur package-private : instancié uniquement par {@link Simulateur}. */
    ResultatImpot(
            int    revenuNet,
            int    abattement,
            int    revenuFiscalDeReference,
            double partsDeclarants,
            double partsTotales,
            long   impotBrutDeclarants,
            long   impotBrutFoyer,
            long   impotApresPlafonnement,
            long   decote,
            long   impotNet) {
 
        this.revenuNet               = revenuNet;
        this.abattement              = abattement;
        this.revenuFiscalDeReference = revenuFiscalDeReference;
        this.partsDeclarants         = partsDeclarants;
        this.partsTotales            = partsTotales;
        this.impotBrutDeclarants     = impotBrutDeclarants;
        this.impotBrutFoyer          = impotBrutFoyer;
        this.impotApresPlafonnement  = impotApresPlafonnement;
        this.decote                  = decote;
        this.impotNet                = impotNet;
    }
 
    // -------------------------------------------------------------------------
    // Accesseurs
    // -------------------------------------------------------------------------
 
    /** Revenu net déclaré (entrée du simulateur). */
    public int getRevenuNet() { return revenuNet; }
 
    /** Abattement pour frais professionnels déduit. */
    public int getAbattement() { return abattement; }
 
    /** Revenu fiscal de référence (revenuNet − abattement). */
    public int getRevenuFiscalDeReference() { return revenuFiscalDeReference; }
 
    /** Nombre de parts des seuls déclarants. */
    public double getPartsDeclarants() { return partsDeclarants; }
 
    /** Nombre de parts total du foyer fiscal. */
    public double getPartsTotales() { return partsTotales; }
 
    /** Impôt brut calculé sur la base des parts déclarants uniquement. */
    public long getImpotBrutDeclarants() { return impotBrutDeclarants; }
 
    /** Impôt brut calculé avec toutes les parts du foyer. */
    public long getImpotBrutFoyer() { return impotBrutFoyer; }
 
    /** Impôt après application du plafonnement du quotient familial. */
    public long getImpotApresPlafonnement() { return impotApresPlafonnement; }
 
    /** Montant de la décote appliquée. */
    public long getDecote() { return decote; }
 
    /** Impôt net final dû (après décote). */
    public long getImpotNet() { return impotNet; }
 
    @Override
    public String toString() {
        return String.format(
            "ResultatImpot{"
          + "revenuNet=%d, abattement=%d, revenuFiscalRef=%d, "
          + "partsDeclarants=%.1f, partsTotales=%.1f, "
          + "impotBrutDeclarants=%d, impotBrutFoyer=%d, "
          + "impotApresPlafonnement=%d, decote=%d, impotNet=%d"
          + "}",
            revenuNet, abattement, revenuFiscalDeReference,
            partsDeclarants, partsTotales,
            impotBrutDeclarants, impotBrutFoyer,
            impotApresPlafonnement, decote, impotNet);
    }
}
