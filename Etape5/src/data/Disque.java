/*-------------------------------------------------------------------*/
/*- Classe Disque : Gère un disque défini par un centre et un rayon. */
/*- Etape 1                                                          */
/*- Groupe 6                                                         */
/*- Date de création : 10/12/2025 08:30                              */
/*-------------------------------------------------------------------*/

public class Disque extends Figure implements Forme
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private Point  centre;
	private double rayon;
	private static int entierTest = 2;
	public final String coucou = "test";

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise un disque                           */
	/*--------------------------------------------------------------*/
	public Disque( Point centre, double rayon )
	{
		super ( centre );
		this.rayon  = rayon;
	}

	/*--------------------------------------------------------------*/
	/* Calcule et retourne l’aire du disque                         */
	/*--------------------------------------------------------------*/
	public double calculerAire()
	{
		return Math.PI * this.rayon * this.rayon;
	}

	/*--------------------------------------------------------------*/
	/* Calcule et retourne le périmètre du disque                   */
	/*--------------------------------------------------------------*/
	public double calculerPerimetre()
	{
		return 2 * Math.PI * this.rayon;
	}

	/*--------------------------------------------------------------*/
	/* Modifie la coordonnée X du centre                             */
	/*--------------------------------------------------------------*/
	public void setX(int x)
	{
		this.centre.setX(x);
	}

	/*--------------------------------------------------------------*/
	/* Modifie la coordonnée Y du centre                             */
	/*--------------------------------------------------------------*/
	public void setY(int y)
	{
		this.centre.setY(y);
	}

	/*--------------------------------------------------------------*/
	/* Méthode privée de test (exemple)                              */
	/*--------------------------------------------------------------*/
	private void setTest(int y)
	{
		this.centre.setY(y);
	}
}
