public class Methode 
{
    private String   nom;
    private String   visibilite;
    private String   retour;
	private Parametre[] tabParametre;


	public Methode(String nom, String visibilite, String retour, Parametre[] tabParametre)
	{
		this.nom = nom;
		this.visibilite = visibilite;
		this.retour = retour;
		this.tabParametre = tabParametre;
	}

	public String getNom() 
	{
		return this.nom;
	}

	public String getVisibilite() 
	{
		return this.visibilite;
	}


	public String getRetour() 
	{
		return this.retour;
	}


	public Parametre[] getParametre() 
	{
		return this.tabParametre;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public void setVisibilite(String visibilite) 
	{
		this.visibilite = visibilite;
	}

	public void setRetour(String retour) 
	{
		this.retour = retour;
	}

	public void setParametre(Parametre[] parametre) 
	{
		this.tabParametre = parametre;
	}

    public String toString() 
	{
        String sRet = "";

        sRet += "méthode : " + this.nom;
        sRet += " visibilité : " + this.visibilite;
		sRet += " type de retour : " + this.retour;

		sRet += "\nparamètres : ";

		for ( Parametre p : this.tabParametre )
		{
			sRet += "\n" + String.format( "%14s", p.toString() );
		}

		sRet += "\n";

        return sRet;
    }

}
