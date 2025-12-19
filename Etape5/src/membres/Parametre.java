package src.membres;

import java.io.Serializable;

/*--------------------------------------------------------*/
/* Parametre.java                                         */
/* Représentation d'un paramètre de méthode avec son nom, */
/* son type et son identifiant                            */
/* Projet : Générateur de diagrammes UML                  */
/* Auteurs : groupe                                       */
/* Date de création : 18/12/2025 15:15                    */
/*--------------------------------------------------------*/

/**
 * Classe représentant un paramètre d'une méthode UML.
 * Un paramètre possède un identifiant, un nom et un type.
 * Permet de représenter les arguments d'une méthode dans le diagramme UML.
 * 
 * @author [Noms des auteurs]
 * @version 1.0
 * @since 18/12/2025
 */
public class Parametre implements Serializable
{
	private int    id;
	private String nom;
	private String type;

	/*--------------------------------------------------------*/
	/*                     CONSTRUCTEUR                        */
	/*--------------------------------------------------------*/

	/**
	 * Constructeur d'un paramètre de méthode.
	 * 
	 * @param numParametre Identifiant du paramètre
	 * @param nom Nom du paramètre
	 * @param type Type du paramètre
	 */
	public Parametre( int numParametre, String nom, String type ) 
	{
		this.id   = numParametre;
		this.nom  = nom;
		this.type = type;
	}

	/*--------------------------------------------------------*/
	/*                    MODIFICATEURS                        */
	/*--------------------------------------------------------*/

	/**
	 * Modifie le nom du paramètre.
	 * 
	 * @param nom Le nouveau nom
	 */
	public void setNom( String nom ) 
	{
		this.nom = nom;
	}

	/**
	 * Modifie le type du paramètre.
	 * 
	 * @param type Le nouveau type
	 */
	public void setType( String type ) 
	{
		this.type = type;
	}

	/**
	 * Modifie l'identifiant du paramètre.
	 * 
	 * @param id Le nouvel identifiant
	 */
	public void setId( int id ) 
	{
		this.id = id;
	}

	/*--------------------------------------------------------*/
	/*                      ACCESSEURS                         */
	/*--------------------------------------------------------*/

	/**
	 * Retourne le nom du paramètre.
	 * 
	 * @return Le nom du paramètre
	 */
	public String getNom() 
	{
		return this.nom;
	}

	/**
	 * Retourne le type du paramètre.
	 * 
	 * @return Le type du paramètre
	 */
	public String getType() 
	{
		return this.type;
	}

	/**
	 * Retourne l'identifiant du paramètre.
	 * 
	 * @return L'identifiant du paramètre
	 */
	public int getId() 
	{
		return this.id;
	}

	/*--------------------------------------------------------*/
	/*                  AUTRES MÉTHODES                        */
	/*--------------------------------------------------------*/

	/**
	 * Retourne une représentation textuelle du paramètre.
	 * Format : type nom
	 * 
	 * @return La chaîne représentant le paramètre
	 */
	public String toString() 
	{
		String sb;

		sb = "";
		sb += this.getType() + " " + this.getNom();

		return sb;
	}
}