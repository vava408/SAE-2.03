package src.membres;
import java.util.*;

/**
 * Représente une méthode (ou constructeur) d'une classe UML.
 *
 * Encapsule les propriétés d'une méthode : nom, visibilité, type de retour,
 * liste de paramètres et modificateurs (static, final) pour la restitution
 * d'une représentation UML texuelle.
 *
 * @author Groupe 6
 * @version Etape 4 - 09/12/2025
 */
public class Methode
{
	private String               nom         ;
	private String               visibilite  ;
	private String               retour      ;
	private ArrayList<Parametre> tabParametre;
	private boolean              estStatic   ;
	private boolean              estFinal    ;

	/**
	 * Construit une méthode avec toutes ses propriétés UML.
	 *
	 * @param nom nom de la méthode (ou du constructeur)
	 * @param visibilite visibilité (`public`, `private`, `protected`, `default`)
	 * @param retour type de retour (ou `null` pour un constructeur)
	 * @param tabParametre liste des paramètres de la méthode
	 * @param estStatic `true` si la méthode est statique
	 * @param estFinal `true` si la méthode est final
	 */
	// constructeur
	public Methode(String nom, String visibilite, String retour, ArrayList<Parametre> tabParametre, 
		           boolean estStatic, boolean estFinal)
	{
		this.nom          = nom         ;
		this.visibilite   = visibilite  ;
		this.retour       = retour      ;
		this.tabParametre = tabParametre;
		this.estStatic    = estStatic   ;
		this.estFinal     = estFinal    ;
	}

	/**
	 * Retourne le nom de cette méthode.
	 * @return le nom de la méthode
	 */
	//retourne le nom de la méthode
	public String getNom()                     { return this.nom         ; }

	/**
	 * Retourne la visibilité de cette méthode.
	 * @return la visibilité (`public`, `private`, `protected`, `default`)
	 */
	//retourne la visibilité de la méthode
	public String getVisibilite()              { return this.visibilite  ; }

	/**
	 * Retourne le type de retour de cette méthode.
	 * @return le type de retour (ou `null` pour un constructeur)
	 */
	//retourne le type de retour de la méthode
	public String getRetour()                  { return this.retour      ; }

	/**
	 * Retourne la liste des paramètres de cette méthode.
	 * @return {@link ArrayList} de {@link Parametre}
	 */
	//retourne la liste des paramètres de la méthode
	public ArrayList<Parametre> getParametre() { return this.tabParametre; }

	/**
	 * Indique si cette méthode est statique.
	 * @return `true` si statique, `false` sinon
	 */
	// retourne true si l'attribut est static
	public boolean isStatic()                  { return this.estStatic   ; }

	/**
	 * Indique si cette méthode est final.
	 * @return `true` si final, `false` sinon
	 */
	// retourne true si l'attribut est final
	public boolean isFinal()                   { return this.estFinal    ; }

	/**
	 * Modifie le nom de cette méthode.
	 * @param nom le nouveau nom
	 */
	//modifie le nom de la méthode
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Modifie la visibilité de cette méthode.
	 * @param visibilite la nouvelle visibilité
	 */
	//modifie la visibilité de la méthode
	public void setVisibilite(String visibilite)
	{
		this.visibilite = visibilite;
	}

	/**
	 * Modifie le type de retour de cette méthode.
	 * @param retour le nouveau type de retour
	 */
	//modifie le type de retour de la méthode
	public void setRetour(String retour)
	{
		this.retour = retour;
	}

	/**
	 * Modifie la liste des paramètres de cette méthode.
	 * @param parametre la nouvelle liste de paramètres
	 */
	//modifie la liste des paramètres de la méthode
	public void setParametre(ArrayList<Parametre> parametre) 
	{
		this.tabParametre = parametre;
	}

	/**
	 * Retourne la représentation texuelle de cette méthode.
	 *
	 * @return chaîne formatée avec signature et paramètres
	 */
	// affichage textuel de la méthode
	public String toString() 
	{
		String sRet = "";

		sRet += "méthode : " + this.nom;
		sRet += " visibilité : " + this.visibilite;

		if(this.retour != null)
			sRet += " type de retour : " + this.retour;

		sRet += "\nparamètres : ";

		if(this.tabParametre.isEmpty())
			sRet += "aucun";
		else
			for ( Parametre p : this.tabParametre )
			{
				sRet += "\n" + String.format( "%14s", p.toString() );
			}

		sRet += "\n";

		return sRet;
	}

}