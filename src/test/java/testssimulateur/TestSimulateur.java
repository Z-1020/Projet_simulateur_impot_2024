package testssimulateur;

import com.kerware.simulateur.AdaptateurVersCodeHerite;
import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimulateur {
    static final int CODE_HERITE = 1;
    static final int CODE_REUSINE = 2;
    static final int CODE = CODE_HERITE;
    static ICalculateurImpot calculateur;
    @BeforeAll
    public static void prepareCalculateurImpot() {
        switch( CODE ) {
            case  CODE_HERITE -> calculateur = new AdaptateurVersCodeHerite();
            case  CODE_REUSINE -> calculateur = null;
        }
    }

    @Test
    @DisplayName( "Test du calcul de l'impot pour un célibataire sans enfant")
    public void testImpotSurRevenuNetPourUnCelibataireSansEnfant() {
        // Arrange
        calculateur.setRevenusNet(35000);
        calculateur.setSituationFamiliale( SituationFamiliale.CELIBATAIRE );
        calculateur.setNbEnfantsACharge(0);
        calculateur.setNbEnfantsSituationHandicap(0);
        calculateur.setParentIsole( false );

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals( 2736 , calculateur.getImpotSurRevenuNet());
        assertEquals( 1 , calculateur.getNbPartsFoyerFiscal());

    }

}
