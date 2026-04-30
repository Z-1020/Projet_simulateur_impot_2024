package testssimulateur;

import com.kerware.simulateur.AdaptateurVersCodeHerite;
import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Test négatif setRevenuNet()")
    public void testNegatifSetRevenuNet(){
        assertThrows(IllegalArgumentException.class, () -> calculateur.setRevenusNet(-1));
        assertDoesNotThrow(() -> calculateur.setRevenusNet(0));
        assertDoesNotThrow(() -> calculateur.setRevenusNet(1));
    }

    @Test
    @DisplayName("Test négatif setNbEnfantsACharge()")
    public void testNegatifSetNbEnfantsACharge(){
        assertThrows(IllegalArgumentException.class, () -> calculateur.setNbEnfantsACharge(-1));
        assertDoesNotThrow(() -> calculateur.setNbEnfantsACharge(0));
        assertDoesNotThrow(() -> calculateur.setNbEnfantsACharge(1));
    }

    @Test
    @DisplayName("Test négatif setNbEnfantsSituationHandicap()")
    public void testNegatifSetNbEnfantsSituationHandicap(){
        assertThrows(IllegalArgumentException.class, () -> calculateur.setNbEnfantsSituationHandicap(-1));
        assertDoesNotThrow(() -> calculateur.setNbEnfantsACharge(0));
        calculateur.setNbEnfantsACharge(1);
        assertDoesNotThrow(() -> calculateur.setNbEnfantsSituationHandicap(0));
        assertDoesNotThrow(() -> calculateur.setNbEnfantsSituationHandicap(1));
        assertThrows(IllegalArgumentException.class, () -> calculateur.setNbEnfantsSituationHandicap(2));

    }

    private void tester(int revenuNet, SituationFamiliale sf, int nbEnfants, int nbEnfantsH, boolean parentIsole, int impotSurRevenuNetAttendu) {
        // Arrange
        calculateur.setRevenusNet(revenuNet);
        calculateur.setSituationFamiliale(sf);
        calculateur.setNbEnfantsACharge(nbEnfants);
        calculateur.setNbEnfantsSituationHandicap(nbEnfantsH);
        calculateur.setParentIsole(parentIsole);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals( impotSurRevenuNetAttendu , calculateur.getImpotSurRevenuNet());
    }

    @ParameterizedTest(name = "EXG_IMPOT_02 {2}")
    @CsvFileSource(resources = "/EXG_IMPOT_02_TestData.csv", numLinesToSkip = 1)
    public void testEXG_IMPOT_02(int revenuNet, String abattementAttendu, String description) {
        // Arrange
        calculateur.setRevenusNet(revenuNet);

        // Act
        if(abattementAttendu.equals("IllegalArgumentException")) {
            // Assert
            assertThrows(IllegalArgumentException.class, () -> calculateur.calculImpotSurRevenuNet());
        } else {
            calculateur.calculImpotSurRevenuNet();
            // Assert
            assertEquals(Integer.valueOf(abattementAttendu), calculateur.getAbattement());
        }
    }

    @ParameterizedTest(name = "EXG_IMPOT_03 {5}")
    @CsvFileSource(resources = "/EXG_IMPOT_03_TestData.csv", numLinesToSkip = 1)
    public void testEXG_IMPOT_03(String situationMaritale, int nbEnfants, int nbEnfantsH, boolean parentIsole, String nbPartsAttendues, String description) {

        // Arrange
        calculateur.setSituationFamiliale(SituationFamiliale.valueOf(situationMaritale));
        calculateur.setNbEnfantsACharge(nbEnfants);
        calculateur.setNbEnfantsSituationHandicap(nbEnfantsH);
        calculateur.setParentIsole(parentIsole);


        // Act
        if(nbPartsAttendues.equals("IllegalArgumentException")) {
            // Assert
            assertThrows(IllegalArgumentException.class, () -> calculateur.getNbPartsFoyerFiscal());
        } else {
            calculateur.calculImpotSurRevenuNet();
            // Assert
            assertEquals(Double.valueOf(nbPartsAttendues), calculateur.getNbPartsFoyerFiscal());
        }
    }

    @ParameterizedTest(name = "EXG_IMPOT_04 and EXG_IMPOT_05 {5}")
    @CsvFileSource(resources = "/EXG_IMPOT_04_05_TestData.csv", numLinesToSkip = 1)
    public void testEXG_IMPOT_04_05(int revenuNet, String situationMaritale, int nbEnfants, int nbEnfantsH, boolean parentIsole, double nbParts, int revenuFiscalDeReference, double revenuFiscalDeReferenceParParts, int impotAvantDecote, String description) {

        // Arrange
        calculateur.setRevenusNet(revenuNet);
        calculateur.setSituationFamiliale(SituationFamiliale.valueOf(situationMaritale));
        calculateur.setNbEnfantsACharge(nbEnfants);
        calculateur.setNbEnfantsSituationHandicap(nbEnfantsH);
        calculateur.setParentIsole(parentIsole);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(revenuFiscalDeReference, calculateur.getRevenuFiscalReference());
        assertEquals(impotAvantDecote, calculateur.getImpotAvantDecote());
    }
}
