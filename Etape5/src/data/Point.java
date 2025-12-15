/*-------------------------------------------------------------------*/
/*- Classe Point : Représente un point dans un plan (coord. x, y).   */
/*- Etape 4                                                           */
/*- Groupe 6                                                          */
/*- Date de création : 10/12/2025 08:30                               */
/*-------------------------------------------------------------------*/

public class Point implements Figure , Forme
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private String nom;
	private int    x;
	private int    y;
	public static char lettre = 'a';

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise un point                           */
	/*--------------------------------------------------------------*/
	public Point(String nom, int x, int y)
	{
		this.nom = nom;
		this.x   = x;
		this.y   = y;
	}

	/*--------------------------------------------------------------*/
	/* Accesseur : retourne le nom, la coordonnée X et Y                    */
	/*--------------------------------------------------------------*/
	public String getNom() { return this.nom ;}
	
	public int    getX()   { return this.x   ;}

	public int    getY()   { return this.y   ;}

	/*--------------------------------------------------------------*/
	/* Modifie la coordonnée X                                      */
	/*--------------------------------------------------------------*/
	public void setX(int x)
	{
		this.x = x;
	}

	/*--------------------------------------------------------------*/
	/* Modifie la coordonnée Y                                      */
	/*--------------------------------------------------------------*/
	public void setY(int y)
	{
		this.y = y;
	}
}