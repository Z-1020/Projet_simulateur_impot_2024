package com.kerware.simulateur;

public class AdaptateurVersCodeHerite implements ICalculateurImpot {

    Simulateur simulateurHerite = new Simulateur();

    @Override
    public void setRevenusNet(int rn) {
        simulateurHerite.setRevenusNet( rn );
    }

    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
      simulateurHerite.setSituationFamilliale( sf );
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
       simulateurHerite.setNbEnfantsACharge( nbe );
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        simulateurHerite.setNbEnfantsEnSituationDeHandicap( nbesh );
    }

    @Override
    public void setParentIsole(boolean pi) {
        simulateurHerite.setParentIsole( pi );
    }

    @Override
    public void calculImpotSurRevenuNet() {
        simulateurHerite.calculImpotSurRevenuNet();
    }

    @Override
    public int getRevenuFiscalReference() {
        return simulateurHerite.getRevenuFiscalDeReference();
    }

    @Override
    public int getAbattement() {
        return simulateurHerite.getAbattement();
    }

    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateurHerite.getNbPartsFoyerFiscal();
    }

    @Override
    public int getImpotAvantDecote() {
        return simulateurHerite.getImpotSurRevenuNet() +
                simulateurHerite.getDecote();
    }

    @Override
    public int getDecote() {
        return simulateurHerite.getDecote();
    }

    @Override
    public int getImpotSurRevenuNet() {
        return simulateurHerite.getImpotSurRevenuNet();
    }
}
