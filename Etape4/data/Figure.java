/*------------------------------------------------------------------------*/
/*- Classe Figure : Classe abstraite représentant une figure géométrique. */
/*- Etape 4                                                               */
/*- Groupe 6                                                              */
/*- Date de création : 12/12/2025 08:30                                   */
/*------------------------------------------------------------------------*/

public abstract class Figure
{
	protected Point centre;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise le centre de la figure             */
	/*--------------------------------------------------------------*/
	public Figure(Point centre)
	{
		this.centre = centre;
	}

	/*--------------------------------------------------------------*/
	/* Retourne le centre de la figure                              */
	/*--------------------------------------------------------------*/
	public Point getCentre()
	{
		return this.centre;
	}


	/*--------------------------------------------------------------*/
	/* Modifie la coordonnée X du centre                            */
	/*--------------------------------------------------------------*/
	public void setX(int x)
	{
		this.centre.setX(x);
	}


	/*--------------------------------------------------------------*/
	/* Modifie la coordonnée Y du centre                            */
	/*--------------------------------------------------------------*/
	public void setY(int y)
	{
		this.centre.setY(y);
	}
}