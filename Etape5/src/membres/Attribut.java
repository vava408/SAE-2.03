package src.membres;
import java.io.Serializable;

/**
 * Classe représentant un attribut d'une classe dans un diagramme UML.
 * Un attribut possède un nom, un type, une visibilité et des modificateurs
 * (static, final, addOnly, requête) conformes à la notation UML standard.
 * 
 * @author Groupe 6
 * @version 1.0
 * @since 19/12/2024 14:30
 */
public class Attribut implements Serializable
{

	private int     attributId;
	
	private String  nom;
	private String  type;
	private String  visibilite;

	private boolean estStatic;
	private boolean estFinal;
	private boolean estAddOnly;
	private boolean estRequete;
	
	/**
	 * Constructeur de la classe Attribut.
	 * Initialise tous les attributs de l'objet avec les valeurs fournies.
	 * 
	 * @param attributId L'identifiant unique de l'attribut
	 * @param nom Le nom de l'attribut
	 * @param type Le type de l'attribut
	 * @param visibilite La visibilité de l'attribut (public, private, protected)
	 * @param estStatic true si l'attribut est statique, false sinon
	 * @param estFinal true si l'attribut est final, false sinon
	 * @param isAddOnly true si l'attribut est en mode addOnly, false sinon
	 * @param isRequete true si l'attribut est une requête, false sinon
	 */
	public Attribut( int attributId, String nom, String type, String visibilite, 
					 boolean estStatic, boolean estFinal, boolean isAddOnly, 
					 boolean isRequete )
	{
		this.attributId = attributId;
		this.nom        = nom;
		this.type       = type;
		this.visibilite = visibilite;
		this.estStatic  = estStatic;
		this.estFinal   = estFinal;
		this.estAddOnly = isAddOnly;
		this.estRequete = isRequete;
	}
	
	/**
	 * Retourne l'identifiant unique de l'attribut.
	 * 
	 * @return L'identifiant de l'attribut
	 */
	public int getAttributId() { return this.attributId; }
	
	/**
	 * Retourne le nom de l'attribut.
	 * 
	 * @return Le nom de l'attribut
	 */
	public String getNom() { return this.nom; }
	
	/**
	 * Retourne le type de l'attribut.
	 * 
	 * @return Le type de l'attribut (int, String, etc.)
	 */
	public String getType() { return this.type; }
	
	/**
	 * Retourne la visibilité de l'attribut (public, private, protected).
	 * 
	 * @return La visibilité de l'attribut
	 */
	public String getVisibilite() { return this.visibilite; }
	
	/**
	 * Retourne true si l'attribut est statique, false sinon.
	 * 
	 * @return true si l'attribut est statique, false sinon
	 */
	public boolean estStatic() { return this.estStatic; }
	
	/**
	 * Retourne true si l'attribut est final, false sinon.
	 * 
	 * @return true si l'attribut est final (constante), false sinon
	 */
	public boolean estFinal() { return this.estFinal; }
	
	/**
	 * Retourne true si l'attribut est en mode addOnly, false sinon.
	 * Le mode addOnly signifie que l'attribut ne peut être que ajouté,
	 * mais pas modifié ou supprimé.
	 * 
	 * @return true si l'attribut est en mode addOnly, false sinon
	 */
	public boolean estAddOnly() { return this.estAddOnly; }
	
	/**
	 * Retourne true si l'attribut est une requête, false sinon.
	 * Une requête (query) est un attribut qui retourne une information
	 * sans modifier l'état de l'objet.
	 * 
	 * @return true si l'attribut est une requête, false sinon
	 */
	public boolean estRequete() { return this.estRequete; }
	
	/**
	 * Modifie le statut de requête de l'attribut.
	 * 
	 * @param isRequete true pour marquer l'attribut comme requête, false sinon
	 */
	public void setRequete( boolean isRequete ) 
	{ 
		this.estRequete = isRequete; 
	}
	
	/**
	 * Retourne une représentation textuelle de l'attribut selon la notation UML.
	 * Le format est : visibilité [final] [static] type nom
	 * Par exemple : "private static int compteur" ou "public String nom"
	 * 
	 * @return Une chaîne représentant l'attribut au format UML
	 */
	public String toString() 
	{
		String sRet = "";
		
		/* Construction de la chaîne avec visibilité */
		sRet += this.visibilite + " ";
		
		/* Ajout du modificateur final si nécessaire */
		if ( this.estFinal ) 
		{
			sRet += "final ";
		}
		
		/* Ajout du modificateur static si nécessaire */
		if ( this.estStatic ) 
		{
			sRet += "static ";
		}
		
		/* Ajout du type et du nom */
		sRet += this.type + " " + this.nom;
		
		return sRet;
	}
}