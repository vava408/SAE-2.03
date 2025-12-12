package src.membres;

/**
 * Représente un paramètre d'une méthode UML.
 *
 * Encapsule les propriétés d'un paramètre : identifiant, nom et type
 * pour la restitution d'une signature de méthode dans une représentation UML.
 *
 * @author Groupe 6
 * @version Etape 4 - 09/12/2025
 */
public class Parametre 
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private int    id;
	private String nom;
	private String type;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise un paramètre                       */
	/*--------------------------------------------------------------*/
	/**
	 * Construit un paramètre de méthode.
	 *
	 * @param numParametre numéro d'ordre du paramètre
	 * @param nom nom du paramètre
	 * @param type type du paramètre
	 */
	public Parametre(int numParametre, String nom, String type) 
	{
		this.id   = numParametre;
		this.nom  = nom;
		this.type = type;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs de la classe            */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne le numéro d'ordre de ce paramètre.
	 * @return l'ID du paramètre
	 */
	public int    getId  () { return this.id   ;}
	/**
	 * Retourne le nom de ce paramètre.
	 * @return le nom
	 */
	public String getNom () { return this.nom  ;}
	/**
	 * Retourne le type de ce paramètre.
	 * @return le type
	 */
	public String getType() { return this.type ;}

	/*--------------------------------------------------------------*/
	/* Modificateurs : modifie les attributs de la classe          */
	/*--------------------------------------------------------------*/
	/**
	 * Modifie le numéro d'ordre de ce paramètre.
	 * @param id le nouveau numéro
	 */
	public void setId  (int id)      { this.id   = id   ;}
	/**
	 * Modifie le nom de ce paramètre.
	 * @param nom le nouveau nom
	 */
	public void setNom (String nom)  { this.nom  = nom  ;}
	/**
	 * Modifie le type de ce paramètre.
	 * @param type le nouveau type
	 */
	public void setType(String type) { this.type = type ;}

	/*--------------------------------------------------------------*/
	/* Retourne une chaîne décrivant le paramètre                  */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne la représentation texuelle de ce paramètre.
	 *
	 * @return chaîne formatée avec ID, nom et type
	 */
	public String toString() 
	{
		String sRet = "";
		sRet += "p" + this.getId() + " :" + this.getNom();
		sRet += " type : " + this.getType();
		return sRet;
	}
}