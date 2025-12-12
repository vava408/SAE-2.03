package src.membres;

/**
 * Représente une association entre deux classes UML.
 *
 * Encapsule une relation directionnelle ou bidirectionnelle entre deux classes
 * avec leurs multiplicités respectives, pour la restitution graphique ou textuelle
 * du diagramme UML.
 *
 * @author Groupe 6
 * @version Etape 4 - 10/12/2025
 */
public class Association 
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private String nomClasseA;
	private String nomClasseB;
	private String multiplicityA;
	private String multiplicityB;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise une association                   */
	/*--------------------------------------------------------------*/
	/**
	 * Construit une association entre deux classes.
	 *
	 * @param nomClasseA nom de la première classe
	 * @param nomClasseB nom de la deuxième classe
	 * @param multiplicityA multiplicité du côté de la classe A
	 * @param multiplicityB multiplicité du côté de la classe B
	 */
	public Association(String nomClasseA, String nomClasseB, String multiplicityA, String multiplicityB)
	{
		this.nomClasseA    = nomClasseA;
		this.nomClasseB    = nomClasseB;
		this.multiplicityA = multiplicityA;
		this.multiplicityB = multiplicityB;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs de la classe            */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne le nom de la première classe de l'association.
	 * @return nom de la classe A
	 */
	public String getNomClasseA()    { return this.nomClasseA; }
	/**
	 * Retourne le nom de la deuxième classe de l'association.
	 * @return nom de la classe B
	 */
	public String getNomClasseB()    { return this.nomClasseB; }

	/**
	 * Retourne la multiplicité du côté de la classe A.
	 * @return multiplicité au format UML (ex: `0..*`, `1..1`)
	 */
	public String getMultiplicityA() { return this.multiplicityA; }
	/**
	 * Retourne la multiplicité du côté de la classe B.
	 * @return multiplicité au format UML (ex: `0..*`, `1..1`)
	 */
	public String getMultiplicityB() { return this.multiplicityB; }

	/*--------------------------------------------------------------*/
	/* Retourne une chaîne décrivant l'association                 */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne la représentation textuelle de cette association.
	 *
	 * Indique si l'association est unidirectionnelle ou bidirectionnelle
	 * et affiche les classes impliquées avec leurs multiplicités.
	 *
	 * @return chaîne formatée décrivant l'association
	 */
	public String toString() 
	{
		String sRet = "";

		// unidirectionnelle si une des multiplicités est 0..* et l'autre 1..1
		if ((this.multiplicityA.equals("0..*") && this.multiplicityB.equals("1..1")) ||
			(this.multiplicityB.equals("0..*") && this.multiplicityA.equals("1..1")))
		{
			sRet += "Unidirectionnelle de ";

			if (this.multiplicityB.equals("1..1"))
			{
				sRet += this.nomClasseA + "(" + this.multiplicityA + ") vers " +
						this.nomClasseB + "(" + this.multiplicityB + ")";
			}
			else
			{
				sRet += this.nomClasseB + "(" + this.multiplicityB + ") vers " +
						this.nomClasseA + "(" + this.multiplicityA + ")";
			}
		}
		else
		{
			// bidirectionnelle dans les autres cas
			sRet += "Bidirectionnelle entre " + this.nomClasseA + "(" + this.multiplicityA + ") et " +
					this.nomClasseB + "(" + this.multiplicityB + ")";
		}

		return sRet;    
	}
}