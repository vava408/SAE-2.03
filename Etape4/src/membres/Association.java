package src.membres;

/*-------------------------------------------------------------------*/
/*- Classe Association : Représente une association entre deux       */
/*- classes UML avec leur multiplicité.                              */
/*- Etape 4                                                          */
/*- Groupe 6                                                         */
/*- Date de création : 10/12/2025 13:30                              */
/*-------------------------------------------------------------------*/

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
	public String getNomClasseA()    { return this.nomClasseA; }
	public String getNomClasseB()    { return this.nomClasseB; }

	public String getMultiplicityA() { return this.multiplicityA; }
	public String getMultiplicityB() { return this.multiplicityB; }

	/*--------------------------------------------------------------*/
	/* Retourne une chaîne décrivant l'association                 */
	/*--------------------------------------------------------------*/
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