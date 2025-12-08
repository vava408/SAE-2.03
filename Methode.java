public class Methode 
{
    private String   nom;
    private String   visibilite;
    private String   retour;
	private Parametre[] parametre;


	public Methode(String nom, String visibilite, String retour, Parametre[] parametre)
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


	public Parametre[] getParametre() 
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

	public void setParametre(Parametre[] parametre) 
	{
		this.parametre = parametre;
	}

    public String toString() {
        String sb = "";
        sb += "méthode : " + getNom();
        sb += " visibilité : " + getVisibilite();
		sb += " type de retour : " + getRetour();
		sb += "\nparamètres : " + getParametre();
        return sb;
    }

}
