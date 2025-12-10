package src.membres;
public class Association 
{
	private String  nomClasseA    ;
	private String  nomClasseB    ;
    private String  multiplicityA ;
	private String  multiplicityB ;
	private boolean bidirectionnel;

	// Constructeur
	public Association( String nomClasseA, String nomClasseB, String multiplicityA,
		                String multiplicityB, boolean bidirectionnel )
	{
		this.nomClasseA     = nomClasseA;
		this.nomClasseB     = nomClasseB;

		this.multiplicityA  = multiplicityA;
		this.multiplicityB  = multiplicityB;

		this.bidirectionnel = bidirectionnel;
	}

	public String  getNomClasseA   () { return this.nomClasseA    ; }
	public String  getNomClasseB   () { return this.nomClasseB    ; }
	public String  getMultiplicityA() { return this.multiplicityA ; }
	public String  getMultiplicityB() { return this.multiplicityB ; }
	public boolean isBidirectionnel() { return this.bidirectionnel; }

	public String toString() 
	{
		String sRet = "";

		if ( this.bidirectionnel )
		{
			sRet += "Bidirectionnelle entre " + this.nomClasseA + "(" + this.multiplicityA + ") et " + 
									            this.nomClasseB + "(" + this.multiplicityB + ")";
		}
		else
		{
			sRet += "Unidirectionnelle de ";

			if ( this.multiplicityB.equals( "1..1" ) )
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

		return sRet;	
    }
}
