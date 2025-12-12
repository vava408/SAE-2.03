package src.membres;
import java.util.*;



public class Methode
{
	private String               nom         ;
	private String               visibilite  ;
	private String               retour      ;
	private ArrayList<Parametre> tabParametre;
	private boolean              estStatic   ;
	private boolean              estFinal    ;

	// constructeur
	public Methode(String nom, String visibilite, String retour, ArrayList<Parametre> tabParametre, 
		           boolean estStatic, boolean estFinal)
	{
		this.nom          = nom         ;
		this.visibilite   = visibilite  ;
		this.retour       = retour      ;
		this.tabParametre = tabParametre;
		this.estStatic    = estStatic   ;
		this.estFinal     = estFinal    ;
	}

	//retourne le nom de la méthode
	public String getNom()                     { return this.nom         ; }

	//retourne la visibilité de la méthode
	public String getVisibilite()              { return this.visibilite  ; }

	//retourne le type de retour de la méthode
	public String getRetour()                  { return this.retour      ; }

	//retourne la liste des paramètres de la méthode
	public ArrayList<Parametre> getParametre() { return this.tabParametre; }

	// retourne true si l'attribut est static
	public boolean isStatic()                  { return this.estStatic   ; }

	// retourne true si l'attribut est final
	public boolean isFinal()                   { return this.estFinal    ; }

	//modifie le nom de la méthode
	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	//modifie la visibilité de la méthode
	public void setVisibilite(String visibilite) 
	{
		this.visibilite = visibilite;
	}

	//modifie le type de retour de la méthode
	public void setRetour(String retour) 
	{
		this.retour = retour;
	}

	//modifie la liste des paramètres de la méthode
	public void setParametre(ArrayList<Parametre> parametre) 
	{
		this.tabParametre = parametre;
	}

	// affichage textuel de la méthode
	public String toString() 
	{
		String sRet = "";
		String sVisibilite;

		String sModifier = ""; // pour final ou autres annotations

			// Déterminer la visibilité
			if (this.getVisibilite().equals("public"))
			{
				sVisibilite = "+ ";
			}
			else if (this.getVisibilite().equals("private"))
			{
				sVisibilite = "- "; 
			}
			else
			{
				sVisibilite = "# ";
			}

			
		String signature = sVisibilite + this.getNom() + " (";

			if (this.getParametre().isEmpty())
			{
				signature += ")";
			}

			for (int cpt = 0; cpt < this.getParametre().size(); cpt++)
			{
				Parametre parametre = this.getParametre().get(cpt);

				signature += " " + parametre.getNom() + " : " + parametre.getType();

				if (cpt < this.getParametre().size() - 1)
				{
					signature += ",";
				}
				else
				{
					signature += " )";
				}
			}

			if (this.getRetour() != null && !this.getRetour().equals("void"))
			{
				sRet += String.format("%-37s: %s\n", signature, this.getRetour());
			}
			else
			{
				sRet += signature + "\n";
			}
		return sRet;
	}

}
