/*-----------------------------------------------------------------------*/
/*- Classe Point : représente un point avec un nom et des coordonnées    */
/*- Etape 4                                                              */
/*- Groupe 6                                                             */
/*- Date de création : 08/12/2025  9:00                                  */
/*-----------------------------------------------------------------------*/

public class Point 
{
	/*--------------------------------------------------------------*/
	/* Attributs                                                    */
	/*--------------------------------------------------------------*/
	private String nom;   // nom du point
	private int    x;     // coordonnée X
	private int    y;     // coordonnée Y

	/*--------------------------------------------------------------*/
	/* Constructeur                                                 */
	/*--------------------------------------------------------------*/
	public Point(String nom, int x, int y)
	{
		this.nom = nom;
		this.x   = x;
		this.y   = y;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs du point                 */
	/*--------------------------------------------------------------*/
	public String getNom() { return this.nom; }
	public int    getX()   { return this.x;   }
	public int    getY()   { return this.y;   }

	/*--------------------------------------------------------------*/
	/* Mutateurs : modifient les attributs du point                 */
	/*--------------------------------------------------------------*/
	public void setNom(String nom) { this.nom = nom; }
	public void setX(int x)        { this.x   = x;   }
	public void setY(int y)        { this.y   = y;   }
}
