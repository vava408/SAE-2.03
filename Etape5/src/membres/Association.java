package src.membres;

import java.io.Serializable;

public class Association implements Serializable
{
	private String nomClasseA   ;
	private String nomClasseB   ;
	private String multipliciteA;
	private String multipliciteB;

	// Constructeur
	public Association( String nomClasseA, String nomClasseB, String multiplicityA, String multiplicityB )
	{
		this.nomClasseA    = nomClasseA;
		this.nomClasseB    = nomClasseB;

		this.multipliciteA = multiplicityA;
		this.multipliciteB = multiplicityB;
	}

	public String getNomClasseA   () { return this.nomClasseA   ; }
	public String getNomClasseB   () { return this.nomClasseB   ; }

	public String getMultiplicityA() { return this.multipliciteA; }
	public String getMultiplicityB() { return this.multipliciteB; }

	public boolean estUnidirectionnelle() 
	{
		return (this.multipliciteA.equals("0..*") && this.multipliciteB.equals("1..1")) ||
		   	   (this.multipliciteB.equals("0..*") && this.multipliciteA.equals("1..1"));
	}

	public String toString() 
	{
		String sRet = "";

		if ( this.multipliciteA.equals( "0..*" ) && this.multipliciteB.equals( "1..1" ) ||
			 this.multipliciteB.equals( "0..*" ) && this.multipliciteA.equals( "1..1" )    )
		{
			sRet += "Unidirectionnelle de ";

			if ( this.multipliciteB.equals( "1..1" ) )
			{
				sRet += this.nomClasseA + "(" + this.multipliciteA + ") vers " + 
						this.nomClasseB + "(" + this.multipliciteB + ")";
			}
			else
			{
				sRet += this.nomClasseB + "(" + this.multipliciteB + ") vers " + 
						this.nomClasseA + "(" + this.multipliciteA + ")";
			}
		}
		else
		{
			sRet += "Bidirectionnelle entre " + this.nomClasseA + "(" + this.multipliciteA + ") et " + 
												this.nomClasseB + "(" + this.multipliciteB + ")";
		}

		return sRet;	
	}
}
