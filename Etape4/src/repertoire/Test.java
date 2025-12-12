import java.util.ArrayList;

/**
 * Regroupe une liste de disques et un point associé.
 *
 * Encapsule une collection de disques avec un point de référence
 * pour faciliter la manipulation groupée de figures géométriques.
 *
 * @author Groupe 6
 * @version Etape 4 - 11/12/2025
 */
public class Test extends Object
{
	/*--------------------------------------------------------------*/
	/* Attributs                                                    */
	/*--------------------------------------------------------------*/
	private Point d;                    // un point associé
	private ArrayList<Disque> lstPoints; // liste de disques

	/*--------------------------------------------------------------*/
	/* Constructeur                                                 */
	/*--------------------------------------------------------------*/
	/**
	 * Construit un ensemble de disques avec un point associé.
	 *
	 * @param lstPoints liste de {@link Disque} à gérer
	 * @param d un {@link Point} de référence
	 */
	public Test(ArrayList<Disque> lstPoints, Point d)
	{
		this.lstPoints = lstPoints;
		this.d         = d;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs                          */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne la liste des disques gérés.
	 * @return {@link ArrayList} de {@link Disque}
	 */
	public ArrayList<Disque> getLstPoints() { return this.lstPoints; }
	/**
	 * Retourne le point de référence associé.
	 * @return le {@link Point} associé
	 */
	public Point getDisque()                { return this.d;         }
}
