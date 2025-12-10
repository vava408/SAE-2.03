package src.membres.Etape3Package.src.membres;
import java.util.*;

import src.membres.Parametre;

public class Methode 
{
    private String   nom;
    private String   visibilite;
    private String   retour;
	private ArrayList<Parametre> tabParametre;


	public Methode(String nom, String visibilite, String retour, ArrayList<Parametre> tabParametre)
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


	public ArrayList<Parametre> getParametre() 
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

	public void setParametre(ArrayList<Parametre> parametre) 
	{
		this.tabParametre = parametre;
	}

    public String toString() 
	{
        String sRet = "";

        sRet += "méthode : " + this.nom;
        sRet += " visibilité : " + this.visibilite;

		if(this.retour != null)
			sRet += " type de retour : " + this.retour;

		sRet += "\nparamètres : ";

		if(this.tabParametre.isEmpty())
			sRet += "aucun";
		else
			for ( Parametre p : this.tabParametre )
			{
				sRet += "\n" + String.format( "%14s", p.toString() );
			}

		sRet += "\n";

        return sRet;
    }

}
