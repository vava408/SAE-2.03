package src.membres.Etape3Package.src.metier;
public class Association 
{
	String         nomClasse;
    private String multiplicity;

	public Association(String nomClasse, String multiplicity)
	{
		this.nomClasse    = nomClasse;
		this.multiplicity = multiplicity;
	}

	public String getNomClasse   () { return this.nomClasse; }

	public String getMultiplicity() { return this.multiplicity; }

	public String toString() 
	{
		return this.nomClasse + "(" + this.multiplicity + ")";	
    }
}
