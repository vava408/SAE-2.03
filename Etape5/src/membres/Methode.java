package src.membres;

import java.io.Serializable;
import java.util.ArrayList;

/*--------------------------------------------------------*/
/* Methode.java                                           */
/* Représentation d'une méthode UML avec ses paramètres,  */
/* sa visibilité et ses modificateurs                     */
/* Projet : Générateur de diagrammes UML                  */
/* Auteurs : groupe 6                                     */
/* Date de création : 18/12/2025 15:10                    */
/*--------------------------------------------------------*/

/**
 * Classe représentant une méthode d'une classe UML.
 * Une méthode possède un nom, une visibilité, un type de retour,
 * une liste de paramètres et peut être static et/ou final.
 * Gère l'affichage formaté de la méthode au format UML.
 * 
 * @author [Noms des auteurs]
 * @version 1.0
 * @since 18/12/2025
 */
public class Methode implements Serializable
{
	private String               nom;
	private String               visibilite;
	private String               retour;
	private ArrayList<Parametre> tabParametre;
	private boolean              estStatic;
	private boolean              estFinal;

	/*--------------------------------------------------------*/
	/*                     CONSTRUCTEUR                        */
	/*--------------------------------------------------------*/

	/**
	 * Constructeur d'une méthode.
	 * 
	 * @param nom Nom de la méthode
	 * @param visibilite Visibilité (public, private, protected, package)
	 * @param retour Type de retour de la méthode
	 * @param tabParametre Liste des paramètres de la méthode
	 * @param estStatic true si la méthode est statique
	 * @param estFinal true si la méthode est finale
	 */
	public Methode( String nom, String visibilite, String retour, 
	                ArrayList<Parametre> tabParametre, 
	                boolean estStatic, boolean estFinal )
	{
		this.nom          = nom;
		this.visibilite   = visibilite;
		this.retour       = retour;
		this.tabParametre = tabParametre;
		this.estStatic    = estStatic;
		this.estFinal     = estFinal;
	}

	/*--------------------------------------------------------*/
	/*                    MODIFICATEURS                        */
	/*--------------------------------------------------------*/

	/**
	 * Modifie le nom de la méthode.
	 * 
	 * @param nom Le nouveau nom
	 */
	public void setNom( String nom ) 
	{
		this.nom = nom;
	}

	/**
	 * Modifie la visibilité de la méthode.
	 * 
	 * @param visibilite La nouvelle visibilité
	 */
	public void setVisibilite( String visibilite ) 
	{
		this.visibilite = visibilite;
	}

	/**
	 * Modifie le type de retour de la méthode.
	 * 
	 * @param retour Le nouveau type de retour
	 */
	public void setRetour( String retour ) 
	{
		this.retour = retour;
	}

	/**
	 * Modifie la liste des paramètres de la méthode.
	 * 
	 * @param parametre La nouvelle liste de paramètres
	 */
	public void setParametre( ArrayList<Parametre> parametre ) 
	{
		this.tabParametre = parametre;
	}

	/*--------------------------------------------------------*/
	/*                      ACCESSEURS                         */
	/*--------------------------------------------------------*/

	/**
	 * Retourne le nom de la méthode.
	 * 
	 * @return Le nom de la méthode
	 */
	public String getNom()
	{ 
		return this.nom; 
	}

	/**
	 * Retourne la visibilité de la méthode.
	 * 
	 * @return La visibilité (public, private, protected, package)
	 */
	public String getVisibilite()
	{ 
		return this.visibilite; 
	}

	/**
	 * Retourne le type de retour de la méthode.
	 * 
	 * @return Le type de retour
	 */
	public String getRetour()
	{ 
		return this.retour; 
	}

	/**
	 * Retourne la liste des paramètres de la méthode.
	 * 
	 * @return La liste des paramètres
	 */
	public ArrayList<Parametre> getParametre()
	{ 
		return this.tabParametre; 
	}

	/**
	 * Indique si la méthode est statique.
	 * 
	 * @return true si la méthode est static, false sinon
	 */
	public boolean isStatic()
	{ 
		return this.estStatic; 
	}

	/**
	 * Indique si la méthode est finale.
	 * 
	 * @return true si la méthode est final, false sinon
	 */
	public boolean isFinal()
	{ 
		return this.estFinal; 
	}

	/*--------------------------------------------------------*/
	/*                  AUTRES MÉTHODES                        */
	/*--------------------------------------------------------*/

	/**
	 * Retourne une représentation textuelle de la méthode.
	 * Format : visibilité type_retour nom_méthode (paramètres)
	 * 
	 * @return La chaîne représentant la méthode
	 */
	public String toString() 
	{
		String sRet;

		sRet = "";
		
		/* Ajouter la visibilité */
		sRet += this.visibilite + " ";

		/* Ajouter le type de retour s'il existe */
		if ( this.retour != null )
		{
			sRet += this.retour + " ";
		}

		/* Ajouter le nom de la méthode */
		sRet += this.nom + " ";

		/* Ajouter les paramètres */
		if ( this.tabParametre.isEmpty() )
		{
			sRet += " ";
		}
		else
		{
			for ( Parametre p : this.tabParametre )
			{
				sRet += String.format( "%14s", p.toString() );
			}
		}

		sRet += "\n";

		return sRet;
	}
}