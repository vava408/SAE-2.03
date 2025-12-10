package src.ihm;
import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;
import src.metier.LireFichier;
import src.metier.LireHeritImple;

public class Vue
{
	private LireFichier lireFichier;



	// constructeur prend en paramètre la classe LireFichier
	public Vue( LireFichier lireFichier )
	{
		this.lireFichier = lireFichier;
	}

	//affichage de la classe lue sous forme textuelle 
	public String afficher()
	{
		String sRet = "";

		switch ( this.lireFichier.getMotCle() )
		{
			case "class"    -> { sRet = this.afficherClass( this.lireFichier.getMotCle() ); }
		
			case "enum"     -> { sRet = this.afficherEnum (                              ); }
			
			case "record"   -> { sRet = this.afficherClass( "Record"          ); }

			case "abstract" -> { sRet = this.afficherClass( "Abstract"        ); }

			default         -> { break; }
			
		}

		return sRet;
	}

	//creation du String pour afficher une classe
	public String afficherClass(String typeClasse)
	{
		String sRet = "";
		String sVisibilite = "";
		String ligne = "------------------------------------------------";
		if(!typeClasse.equals("class"))
			sRet += "<<"+  typeClasse +">>\n";
	

		sRet += ligne + "\n";

		sRet += String.format("%24s", this.lireFichier.getNomClasse()) + "\n";

		sRet += ligne + "\n";

		for (Attribut attribut : this.lireFichier.getListeAttributs() )
		{

			if (attribut.getVisibilite().equals("private"))
			{

				sVisibilite = "- ";
			}
			else
			{
				sVisibilite = "+ ";
			}

			sRet += sVisibilite + attribut.getNom() + "\t" + ": " + attribut.getType() + "\n";
		}

		sRet += ligne + "\n";

		for (Methode methode : this.lireFichier.getListeMethodes() )
		{
			if (methode.getVisibilite().equals("private"))
			{
				sVisibilite = "- ";
			}
			else
			{
				sVisibilite = "+ ";
			}

			sRet += sVisibilite + methode.getNom() + " (";

			if (methode.getParametre().size() == 0)
			{
				sRet += ")";
			}

			for (int cpt = 0; cpt < methode.getParametre().size(); cpt++)
			{
				Parametre parametre = methode.getParametre().get(cpt);

				sRet += " " + parametre.getNom() + " : " + parametre.getType();

				if (cpt < methode.getParametre().size() - 1)
				{
					sRet += ",";
				}
				else
				{
					sRet += " )";
				}

			}

			if (methode.getRetour() != null && !methode.getRetour().equals("void"))
			{
				sRet += String.format("%20s", ": " + methode.getRetour());
			}

			sRet += "\n";
		}

		sRet += ligne + "\n";

		return sRet;

	}

	public String afficherEnum()
	{
		String sRet = "";
		String sVisibilite = "";
		String ligne = "------------------------------------------------";
		sRet += "<<Enumération>>\n";

		sRet += ligne + "\n";

		sRet += String.format ("%24s", this.lireFichier.getNomClasse() ) + "\n";

		sRet += ligne + "\n";

		for ( Attribut attribut : this.lireFichier.getListeAttributs() )
		{
			sRet += attribut.getNom() + "\n";
		}

		sRet += ligne + "\n";
		return sRet;
	}

	public String afficherInterface()
	{
		String sRet = "";
		if (this.lireFichier.getMapImple() != null  &&!this.lireFichier.getMapImple().isEmpty())
		{
			for (String classe : this.lireFichier.getMapImple().keySet())
			{
				sRet += classe + " implémente " + this.lireFichier.getMapImple().get(classe) + "\n";
			}
		}
		return sRet;
	}

	public String afficherHeritage()
	{
		String sRet = "";
		if (this.lireFichier.getMapHerit() != null  && !this.lireFichier.getMapHerit().isEmpty())
		{
			for (String classe : this.lireFichier.getMapHerit().keySet())
			{
				sRet += classe + " hérite de  " + this.lireFichier.getMapHerit().get(classe) + "\n";
			}
		}
		return sRet;
	} 

}