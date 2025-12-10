package src.membres;

import java.util.*;

public class Methode 
{
    private String   nom;
    private String   visibilite;
    private String   retour;
	private ArrayList<Parametre> tabParametre;

	private static final String[] TAB_TYPES_RETOUR    = { "void", "int", "double", "float", "boolean", "char", "String"};
	private static final String[] TAB_TYPES_RETOUR_FR = {"vide", "entier", "double", "flottant", "booléen", "caractère", "chaîne" };



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
		{

			for (int cpt = 0; cpt < Methode.TAB_TYPES_RETOUR.length; cpt++)
			{
				if ( this.retour.equals(TAB_TYPES_RETOUR[cpt] ) )
				{
					this.retour = TAB_TYPES_RETOUR_FR[cpt];
				}
			}
			
			sRet += " type de retour : " + this.retour;
		}

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
