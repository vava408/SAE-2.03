/**
 * Représente un disque (cercle) défini par un centre et un rayon.
 *
 * Fournit des méthodes pour calculer l'aire et le périmètre du disque.
 *
 * @author Groupe 6
 * @version Etape 4 - 08/12/2025
 */
public class Disque
{
	/*--------------------------------------------------------------*/
	/* Attributs                                                    */
	/*--------------------------------------------------------------*/
	private Point  centre;   // centre du disque
	private double rayon;    // rayon du disque

	/*--------------------------------------------------------------*/
	/* Constructeur                                                 */
	/*--------------------------------------------------------------*/
	/**
	 * Construit un disque avec un centre et un rayon.
	 *
	 * @param centre le {@link Point} central du disque
	 * @param rayon le rayon du disque
	 */
	public Disque(Point centre, double rayon)
	{
		this.centre = centre;
		this.rayon  = rayon ;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs du disque                */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne le centre du disque.
	 * @return le {@link Point} central
	 */
	public Point getCentre() { return this.centre ;}

	/**
	 * Retourne le rayon du disque.
	 * @return le rayon
	 */
	public double getRayon() { return this.rayon  ;}

	/*--------------------------------------------------------------*/
	/* Calcul de l'aire du disque                                   */
	/*--------------------------------------------------------------*/
	/**
	 * Calcule l'aire du disque.
	 *
	 * Formule : A = π × r²
	 *
	 * @return l'aire du disque
	 */
	public double calculerAire()
	{
		return Math.PI * this.rayon * this.rayon;
	}

	/*--------------------------------------------------------------*/
	/* Calcul du périmètre du disque                                */
	/*--------------------------------------------------------------*/
	/**
	 * Calcule le périmètre (circonférence) du disque.
	 *
	 * Formule : P = 2 × π × r
	 *
	 * @return le périmètre du disque
	 */
	public double calculerPerimetre()
	{
		return 2 * Math.PI * this.rayon;
	}
}