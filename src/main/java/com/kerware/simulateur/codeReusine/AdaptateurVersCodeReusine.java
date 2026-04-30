package com.kerware.simulateur.codeReusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.codeReusine.Simulateur;
import com.kerware.simulateur.SituationFamiliale;

public final class AdaptateurVersCodeReusine implements ICalculateurImpot {

    private Simulateur simulateurReusine = new Simulateur();

    @Override
    public void setRevenusNet(int rn) {
        simulateurReusine.setRevenusNet( rn );
    }

    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        simulateurReusine.setSituationFamilliale( sf );
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
        simulateurReusine.setNbEnfantsACharge( nbe );
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        simulateurReusine.setNbEnfantsEnSituationDeHandicap( nbesh );
    }

    @Override
    public void setParentIsole(boolean pi) {
        simulateurReusine.setParentIsole( pi );
    }

    @Override
    public void calculImpotSurRevenuNet() {
        simulateurReusine.calculImpotSurRevenuNet();
    }

    @Override
    public int getRevenuFiscalReference() {
        return simulateurReusine.getRevenuFiscalDeReference();
    }

    @Override
    public int getAbattement() {
        return simulateurReusine.getAbattement();
    }

    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateurReusine.getNbPartsFoyerFiscal();
    }

    @Override
    public int getImpotAvantDecote() {
        return simulateurReusine.getImpotSurRevenuNet() +
                simulateurReusine.getDecote();
    }

    @Override
    public int getDecote() {
        return simulateurReusine.getDecote();
    }

    @Override
    public int getImpotSurRevenuNet() {
        return simulateurReusine.getImpotSurRevenuNet();
    }

    @Override
    public void reset() {
        simulateurReusine.reset();
    }


}
