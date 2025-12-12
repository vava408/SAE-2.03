/*-------------------------------------------------------------------*/
/*- Classe Point : Représente un point dans un plan (coord. x, y).   */
/*- Etape 4                                                          */
/*- Groupe 6                                                         */
/*- Date de création : 10/12/2025 08:30                              */
/*-------------------------------------------------------------------*/

public class Point
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private static String nom;
	private int    x;
	private final int    y;

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
	/* Accesseur : retourne le nom, la coordonnée X et Y            */
	/*--------------------------------------------------------------*/
	public String getNom() { return this.nom ;}
	
	public int    getX()   { return this.x   ;}

	public int    getY()   { return this.y   ;}

	/*--------------------------------------------------------------*/
	/* Modifieurs : la coordonnée X et Y                            */
	/*--------------------------------------------------------------*/
	public void setX(int x) { this.x = x ;}

	public void setY(int y) { this.y = y ;}
}