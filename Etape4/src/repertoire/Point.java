/**
 * Représente un point dans un espace à deux dimensions.
 *
 * Encapsule un nom et des coordonnées (x, y) pour la gestion
 * de positions dans un système de coordonnées cartésien.
 *
 * @author Groupe 6
 * @version Etape 4 - 08/12/2025
 */
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
	/**
	 * Construit un point avec un nom et des coordonnées.
	 *
	 * @param nom identifiant du point
	 * @param x coordonnée X
	 * @param y coordonnée Y
	 */
	public Point(String nom, int x, int y)
	{
		this.nom = nom;
		this.x   = x;
		this.y   = y;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs du point                 */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne le nom du point.
	 * @return le nom du point
	 */
	public String getNom() { return this.nom; }
	/**
	 * Retourne la coordonnée X du point.
	 * @return l'abscisse
	 */
	public int    getX()   { return this.x;   }
	/**
	 * Retourne la coordonnée Y du point.
	 * @return l'ordonnée
	 */
	public int    getY()   { return this.y;   }

	/*--------------------------------------------------------------*/
	/* Mutateurs : modifient les attributs du point                 */
	/*--------------------------------------------------------------*/
	/**
	 * Modifie le nom du point.
	 * @param nom le nouveau nom
	 */
	public void setNom(String nom) { this.nom = nom; }
	/**
	 * Modifie la coordonnée X du point.
	 * @param x la nouvelle abscisse
	 */
	public void setX(int x)        { this.x   = x;   }
	/**
	 * Modifie la coordonnée Y du point.
	 * @param y la nouvelle ordonnée
	 */
	public void setY(int y)        { this.y   = y;   }
}
