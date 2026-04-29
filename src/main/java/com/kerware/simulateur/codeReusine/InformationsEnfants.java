package com.kerware.simulateur.codeReusine;

public class InformationsEnfants {
	
	/**
	 * La classe InformationEnfants Sert à renseigner le nombre d'enfants à charge,
	 * ainsi que le nombre d'enfants en situation de handicap dans un foyer
	 */
	
	//	variables	//
	
	private int NbEnfantsACharge;
	
	private int NbEnfantsSituationHandicap;
	
	//	Constructeur	//
	
	/**
	 * Constructeur de la classe InformationsEnfants, Permet de définir le nombre d'enfants à charge
	 * @param NbEnfantsACharge ( int )
	 * @param NbEnfantsSituationHandicap ( int )
	 */
	
	public InformationsEnfants(int NbEnfantsACharge, int NbEnfantsSituationHandicap) {
		this.NbEnfantsACharge = NbEnfantsACharge;
		this.NbEnfantsSituationHandicap = NbEnfantsSituationHandicap;
	}
	
	//	Setters	//
	
	/**
	 * Permet de modifier le nombre d'enfants à charge
	 * @param NbEnfantsACharge ( int )
	*/
	
    public void setNbEnfantsACharge(int NbEnfantsACharge) {
       this.NbEnfantsACharge = NbEnfantsACharge;
    }
    
    /**
     * Permet de modifier le nombre d'enfants en situation de handicap à charge
     * @param NbEnfantsSituationHandicap ( int )
     */

    public void setNbEnfantsSituationHandicap(int NbEnfantsSituationHandicap) {
    	this.NbEnfantsSituationHandicap = NbEnfantsSituationHandicap;
    }
    
    //	Getters	//
    
    /**
     * Retourne le nombre d'enfants à charge
     * @return NbEnfantsACharge ( int )
     */
    
    public int getNBEnfantsACharge() {
    	return this.NbEnfantsACharge;
    }
    
    /**
     * Retourne le nombre d'enfants en situation de handicap à charge
     * @return NbEnfantsSituationHandicap ( int )
     */
    
    public int getNbEnfantsSituationHandicap() {
    	return this.NbEnfantsSituationHandicap;
    }

}
