package com.kerware.simulateur.codeReusine;

public class SituationParental {
	
	
	private boolean parentIsole;
	
	/**
	 * Constructeur de la SituationParental
	 * @param parentIsole (boolean)
	 */
	public SituationParental(boolean parentIsole) {
		this.parentIsole = parentIsole;
	}
	
	/**
	 * Permet de modifier le statut d'un membre du foyer s'il est un parent isolé ou non
	 * @param estParentIsole (boolean)
	 */
	public void setParentIsole(boolean estParentIsole) {
		this.parentIsole = parentIsole;
	}
	
	/**
	 * Retourne  true si c'est un parent isolé ou false s'il ne l'est pas.
	 * @return parentIsole (boolean)
	 */
	public boolean getParentIsole() {
		return this.parentIsole;
	}
}
