/*------------------------------------------------------------------------*/
/*- Classe Test : Classe de test ou wrapper pour un carré spécifique     */
/*- Groupe 6                                                              */
/*- Date de création : 17/12/2025                                         */
/*------------------------------------------------------------------------*/

/**
 * Classe représentant un objet Test associé à un carré.
 *
 * Cette classe conserve une référence à un objet Carre, permettant
 * éventuellement de réaliser des tests ou manipulations spécifiques
 * dans le cadre du projet.
 */
public class Test
{
	/** Carré associé à cet objet Test */
	private Carre carre;
	
	/**
	 * Construit un objet Test pour le carré spécifié.
	 *
	 * @param carre carré associé
	 */
	public Test(Carre carre)
	{
		this.carre = carre;
	}
}