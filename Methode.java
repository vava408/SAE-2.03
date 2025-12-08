public class Methode 
{
    private String nom;
    private String visibilite;
    private String retour;
	private String parametre;


	public Methode(String nom, String visibilite, String retour, String parametre)
	{
		this.nom = nom;
		this.visibilite = visibilite;
		this.retour = retour;
		this.parametre = parametre;
	}

	public String getNom() 
	{
		return nom;
	}

	public String getVisibilite() 
	{
		return visibilite;
	}


	public String getRetour() 
	{
		return retour;
	}


	public String getParametre() 
	{
		return parametre;
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

	public void setParametre(String parametre) 
	{
		this.parametre = parametre;
	}

	



}
