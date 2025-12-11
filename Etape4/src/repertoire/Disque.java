/*-----------------------------------------------------------------------*/
/*- Classe Disque : représente un disque défini par un centre et un      */
/*- rayon.                                                               */
/*- Etape 4                                                              */
/*- Groupe 6                                                             */
/*- Date de création : 08/12/2025  9:00                                  */
/*-----------------------------------------------------------------------*/

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
	public Disque(Point centre, double rayon)
	{
		this.centre = centre;
		this.rayon  = rayon ;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs du disque                */
	/*--------------------------------------------------------------*/
	public Point getCentre() { return this.centre ;}

	public double getRayon() { return this.rayon  ;}

	/*--------------------------------------------------------------*/
	/* Calcul de l’aire du disque                                   */
	/*--------------------------------------------------------------*/
	public double calculerAire()
	{
		return Math.PI * this.rayon * this.rayon;
	}

	/*--------------------------------------------------------------*/
	/* Calcul du périmètre du disque                                */
	/*--------------------------------------------------------------*/
	public double calculerPerimetre()
	{
		return 2 * Math.PI * this.rayon;
	}
}