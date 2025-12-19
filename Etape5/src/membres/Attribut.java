package src.membres;

import java.io.Serializable;

/*
* Classe représentant un attribut d'une classe dans un diagramme UML
* 
* Exercice    : Génération de diagrammes UML
* Auteurs     : groupe 6
* Date/Heure  : 19/12/2024 14:30
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
	 * Constructeur de la classe Attribut
	 * Initialise tous les attributs de l'objet
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
	 * Retourne l'identifiant unique de l'attribut
	 */
	public int getAttributId() { return this.attributId; }

	/**
	 * Retourne le nom de l'attribut
	 */
	public String getNom() { return this.nom; }

	/**
	 * Retourne le type de l'attribut
	 */
	public String getType() { return this.type; }

	/**
	 * Retourne la visibilité de l'attribut (public, private, protected)
	 */
	public String getVisibilite() { return this.visibilite; }

	/**
	 * Retourne true si l'attribut est statique, false sinon
	 */
	public boolean estStatic() { return this.estStatic; }

	/**
	 * Retourne true si l'attribut est final, false sinon
	 */
	public boolean estFinal() { return this.estFinal; }

	/**
	 * Retourne true si l'attribut est en mode addOnly, false sinon
	 */
	public boolean estAddOnly() { return this.estAddOnly; }

	/**
	 * Retourne true si l'attribut est une requête, false sinon
	 */
	public boolean estRequete() { return this.estRequete; }

	/**
	 * Modifie le statut de requête de l'attribut
	 */
	public void setRequete( boolean isRequete ) 
	{ 
		this.estRequete = isRequete; 
	}

	/**
	 * Retourne une représentation textuelle de l'attribut selon la notation UML
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