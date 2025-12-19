package src.membres;

import java.io.Serializable;

/*--------------------------------------------------------*/
/* Association.java                                       */
/* Représentation d'une association UML entre deux        */
/* classes avec leurs multiplicités                       */
/* Projet : Générateur de diagrammes UML                  */
/* Auteurs : groupe 6                                     */
/* Date de création : 18/12/2025 15:00                    */
/*--------------------------------------------------------*/

/**
 * Classe représentant une association UML entre deux classes.
 * Une association peut être unidirectionnelle ou bidirectionnelle
 * selon les multiplicités définies entre les classes.
 * Gère les multiplicités et détermine le type d'association.
 * 
 * @author groupe 6
 * @version 1.0
 * @since 18/12/2025
 */
public class Association implements Serializable
{
	private String nomClasseA;
	private String nomClasseB;
	private String multipliciteA;
	private String multipliciteB;

	/*--------------------------------------------------------*/
	/*                     CONSTRUCTEUR                        */
	/*--------------------------------------------------------*/

	/**
	 * Constructeur d'une association entre deux classes.
	 * 
	 * @param nomClasseA Nom de la première classe
	 * @param nomClasseB Nom de la seconde classe
	 * @param multiplicityA Multiplicité côté classe A (ex: "0..*", "1..1")
	 * @param multiplicityB Multiplicité côté classe B (ex: "0..*", "1..1")
	 */
	public Association( String nomClasseA, String nomClasseB, 
	                    String multiplicityA, String multiplicityB )
	{
		this.nomClasseA    = nomClasseA;
		this.nomClasseB    = nomClasseB;
		this.multipliciteA = multiplicityA;
		this.multipliciteB = multiplicityB;
	}

	/*--------------------------------------------------------*/
	/*                      ACCESSEURS                         */
	/*--------------------------------------------------------*/

	/**
	 * Retourne le nom de la première classe.
	 * 
	 * @return Le nom de la classe A
	 */
	public String getNomClasseA() 
	{ 
		return this.nomClasseA; 
	}

	/**
	 * Retourne le nom de la seconde classe.
	 * 
	 * @return Le nom de la classe B
	 */
	public String getNomClasseB() 
	{ 
		return this.nomClasseB; 
	}

	/**
	 * Retourne la multiplicité de la classe A.
	 * 
	 * @return La multiplicité côté A
	 */
	public String getMultiplicityA() 
	{ 
		return this.multipliciteA; 
	}

	/**
	 * Retourne la multiplicité de la classe B.
	 * 
	 * @return La multiplicité côté B
	 */
	public String getMultiplicityB() 
	{ 
		return this.multipliciteB; 
	}

	/*--------------------------------------------------------*/
	/*                  AUTRES MÉTHODES                        */
	/*--------------------------------------------------------*/

	/**
	 * Détermine si l'association est unidirectionnelle.
	 * Une association est unidirectionnelle si une classe a "0..*" et l'autre "1..1".
	 * 
	 * @return true si l'association est unidirectionnelle, false sinon
	 */
	public boolean estUnidirectionnelle() 
	{
		return ( this.multipliciteA.equals( "0..*" ) && this.multipliciteB.equals( "1..1" ) ) ||
		       ( this.multipliciteB.equals( "0..*" ) && this.multipliciteA.equals( "1..1" ) );
	}

	/**
	 * Retourne une représentation textuelle de l'association.
	 * Indique si elle est unidirectionnelle ou bidirectionnelle avec les multiplicités.
	 * 
	 * @return La chaîne décrivant l'association
	 */
	public String toString() 
	{
		String sRet;

		sRet = "";

		/* Vérifier si l'association est unidirectionnelle */
		if ( this.multipliciteA.equals( "0..*" ) && this.multipliciteB.equals( "1..1" ) ||
		     this.multipliciteB.equals( "0..*" ) && this.multipliciteA.equals( "1..1" ) )
		{
			sRet += "Unidirectionnelle de ";

			/* Déterminer le sens de la navigation */
			if ( this.multipliciteB.equals( "1..1" ) )
			{
				sRet += this.nomClasseA + " (" + this.multipliciteA + ") vers " + 
				        this.nomClasseB + " (" + this.multipliciteB + ")";
			}
			else
			{
				sRet += this.nomClasseB + " (" + this.multipliciteB + ") vers " + 
				        this.nomClasseA + " (" + this.multipliciteA + ")";
			}
		}
		else
		{
			/* Association bidirectionnelle */
			sRet += "Bidirectionnelle entre " + 
			        this.nomClasseA + " (" + this.multipliciteA + ") et " + 
			        this.nomClasseB + " (" + this.multipliciteB + ")";
		}

		return sRet;
	}
}