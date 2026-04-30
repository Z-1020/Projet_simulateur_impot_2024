package testssimulateur;

import com.kerware.simulateur.AdaptateurVersCodeHerite;
import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateur.codeReusine.AdaptateurVersCodeReusine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimulateur {
    static final int CODE_HERITE = 1;
    static final int CODE_REUSINE = 2;
    static final int CODE = CODE_REUSINE;
    static ICalculateurImpot calculateur;
    @BeforeAll
    public static void prepareCalculateurImpot() {
        switch( CODE ) {
            case  CODE_HERITE -> calculateur = new AdaptateurVersCodeHerite();
            case  CODE_REUSINE -> calculateur = new AdaptateurVersCodeReusine();
        }
    }


    @Test
    @DisplayName("Test négatif setRevenuNet()")
    public void testNegatifSetRevenuNet(){
        // Arrange
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setNbEnfantsACharge(0);
        calculateur.setNbEnfantsSituationHandicap(0);
        calculateur.setParentIsole(false);

        calculateur.setRevenusNet(-1);
        assertThrows(IllegalArgumentException.class, () -> calculateur.calculImpotSurRevenuNet());
        calculateur.setRevenusNet(0);
        assertDoesNotThrow(() -> calculateur.calculImpotSurRevenuNet());
        calculateur.setRevenusNet(1);
        assertDoesNotThrow(() -> calculateur.calculImpotSurRevenuNet());
    }

    @Test
    @DisplayName("Test négatif setNbEnfantsACharge()")
    public void testNegatifSetNbEnfantsACharge(){
        // Arrange
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setRevenusNet(0);
        calculateur.setNbEnfantsSituationHandicap(0);
        calculateur.setParentIsole(false);

        calculateur.setNbEnfantsACharge(-1);
        assertThrows(IllegalArgumentException.class, () -> calculateur.calculImpotSurRevenuNet());
        calculateur.setNbEnfantsACharge(0);
        assertDoesNotThrow(() -> calculateur.calculImpotSurRevenuNet());
        calculateur.setNbEnfantsACharge(1);
        assertDoesNotThrow(() -> calculateur.calculImpotSurRevenuNet());
    }

    @Test
    @DisplayName("Test négatif setNbEnfantsSituationHandicap()")
    public void testNegatifSetNbEnfantsSituationHandicap(){
        // Arrange
        calculateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        calculateur.setRevenusNet(0);
        calculateur.setParentIsole(false);

        calculateur.setNbEnfantsACharge(0);
        calculateur.setNbEnfantsSituationHandicap(-1);
        assertThrows(IllegalArgumentException.class, () -> calculateur.calculImpotSurRevenuNet());

        calculateur.setNbEnfantsACharge(1);
        calculateur.setNbEnfantsSituationHandicap(0);
        assertDoesNotThrow(() -> calculateur.calculImpotSurRevenuNet());
        calculateur.setNbEnfantsSituationHandicap(2);
        assertThrows(IllegalArgumentException.class, () -> calculateur.calculImpotSurRevenuNet());
        calculateur.setNbEnfantsSituationHandicap(1);
        calculateur.setNbEnfantsACharge(2);
        assertDoesNotThrow(() -> calculateur.calculImpotSurRevenuNet());

    }

    @Test
    @DisplayName("Test négatif parent isolé sans enfants")
    public void testNegatifSetParentIsole(){
        calculateur.setNbEnfantsACharge(0);
        calculateur.setParentIsole(true);
        assertThrows(IllegalArgumentException.class, () -> calculateur.calculImpotSurRevenuNet());
        calculateur.setNbEnfantsACharge(1);
        assertDoesNotThrow(() -> calculateur.calculImpotSurRevenuNet());

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

    @ParameterizedTest(name = "EXG_IMPOT_02 {3}")
    @CsvFileSource(resources = "/EXG_IMPOT_02_TestData.csv", numLinesToSkip = 1)
    public void testEXG_IMPOT_02(int revenuNet, String situationMaritale, String abattementAttendu, String description) {
        // Arrange
        calculateur.setSituationFamiliale(SituationFamiliale.valueOf(situationMaritale));
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

    @ParameterizedTest(name = "EXG_IMPOT_04 and EXG_IMPOT_05 {11}")
    @CsvFileSource(resources = "/EXG_IMPOT_04_05_06_TestData.csv", numLinesToSkip = 1)
    public void testEXG_IMPOT_04_05(int revenuNet, String situationMaritale, int nbEnfants, int nbEnfantsH, boolean parentIsole, double nbParts, int revenuFiscalDeReference, double revenuFiscalDeReferenceParParts,int impotAvantDecote, int decote, int impotApresDecote, String nom, String description) {

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

    @ParameterizedTest(name = "EXG_IMPOT_06 {11}")
    @CsvFileSource(resources = "/EXG_IMPOT_04_05_06_TestData.csv", numLinesToSkip = 1)
    public void testEXG_IMPOT_06(int revenuNet, String situationMaritale, int nbEnfants, int nbEnfantsH, boolean parentIsole, double nbParts, int revenuFiscalDeReference, double revenuFiscalDeReferenceParParts,int impotAvantDecote, int decote, int impotApresDecote, String nom, String description) {

        // Arrange
        calculateur.setRevenusNet(revenuNet);
        calculateur.setSituationFamiliale(SituationFamiliale.valueOf(situationMaritale));
        calculateur.setNbEnfantsACharge(nbEnfants);
        calculateur.setNbEnfantsSituationHandicap(nbEnfantsH);
        calculateur.setParentIsole(parentIsole);

        // Act
        calculateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(decote, calculateur.getDecote());
        assertEquals(impotApresDecote, calculateur.getImpotSurRevenuNet());
    }

}
