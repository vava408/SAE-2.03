/*--------------------------------------------------------------------*/
/*- Classe Disque : représente un disque géométrique                  */
/*- Etape 1                                                           */
/*- Groupe 6                                                          */
/*- Date de création : 08/12/2025 10:30                               */
/*--------------------------------------------------------------------*/

public class Disque
{
	/*--------------------------------------------------------------*/
	/* Attributs préfixés                                           */
	/*--------------------------------------------------------------*/
	private Point  mCentre;
	private double mRayon;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise un disque                          */
	/*--------------------------------------------------------------*/
	public Disque(Point centre, double rayon)
	{
		mCentre = centre;
		mRayon  = rayon;
	}

	/*--------------------------------------------------------------*/
	/* Calcule l'aire du disque                                     */
	/*--------------------------------------------------------------*/
	public double calculerAire()
	{
		return Math.PI * mRayon * mRayon;
	}

	/*--------------------------------------------------------------*/
	/* Calcule le périmètre du disque                               */
	/*--------------------------------------------------------------*/
	public double calculerPerimetre()
	{
		return 2 * Math.PI * mRayon;
	}

	/*--------------------------------------------------------------*/
	/* Modifie l'abscisse du centre du disque                       */
	/*--------------------------------------------------------------*/
	public void setX(int x)
	{
		mCentre.setX(x);
	}

	/*--------------------------------------------------------------*/
	/* Modifie l'ordonnée du centre du disque                       */
	/*--------------------------------------------------------------*/
	public void setY(int y)
	{
		mCentre.setY(y);
	}
}