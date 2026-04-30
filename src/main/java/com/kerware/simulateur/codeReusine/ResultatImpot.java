package com.kerware.simulateur.codeReusine;

/**
 * Résultat détaillé d'un calcul d'impôt sur le revenu.
 *
 * Classe exposant toutes les étapes intermédiaires pour la
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
 
    /** Constructeur package-private : instancié uniquement par . */
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
    // Getters
    // -------------------------------------------------------------------------
 
    /** Revenu net déclaré. */
    public int getRevenuNet() { return revenuNet; }
 
    /** Abattement pour frais professionnels déduit. */
    public int getAbattement() { return abattement; }
 
    /** Revenu fiscal de référence. */
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
 
    /** Impôt net final dû. */
    public long getImpotNet() { return impotNet; }

}
