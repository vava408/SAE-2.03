public class Association 
{
	String         nomClasseA;
	String         nomClasseB;
    private String multiplicityA;
    private String multiplicityB;

	public Association(String nomClasseA, String nomClasseB, String multiplicityA, String multiplicityB)
	{
		this.nomClasseA    = nomClasseA;
		this.nomClasseB    = nomClasseB;
		this.multiplicityA = multiplicityA;
		this.multiplicityB = multiplicityB;
	}

	public String getNomClasseA() { return nomClasseA; }

	public String getNomClasseB() { return nomClasseB; }

	public String getMultiplicityA() { return multiplicityA; }

	public String getMultiplicityB() { return multiplicityB; }

	public String toString() 
	{
        String sRet = "";

		if ( this.multiplicityA.startsWith( "0" ) )
		{
			sRet += "Unidirectionnel de " + this.nomClasseA + " (" + this.multiplicityA + ") vers " + 
			                                this.nomClasseB + " (" + this.multiplicityB + ")"; 
		}
		else if ( this.multiplicityB.startsWith( "0" ) )
		{
			sRet += "Unidirectionnel de " + this.nomClasseB + " (" + this.multiplicityB + ") vers " + 
			                                this.nomClasseA + " (" + this.multiplicityA + ")"; 
		}
		else
		{
			sRet += "Bidirectionnel entre " + this.nomClasseA + " (" + this.multiplicityA + ") et " + 
			                                  this.nomClasseB + " (" + this.multiplicityB + ")";
		}

		return sRet;
    }
}
