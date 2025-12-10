package src.ihm;
import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;
import src.metier.LireFichier;

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

			sRet += String.format("%s%-35s: %s\n", sVisibilite, attribut.getNom(), attribut.getType());
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

			String signature = sVisibilite + methode.getNom() + " (";

			if (methode.getParametre().size() == 0)
			{
				signature += ")";
			}

			for (int cpt = 0; cpt < methode.getParametre().size(); cpt++)
			{
				Parametre parametre = methode.getParametre().get(cpt);

				signature += " " + parametre.getNom() + " : " + parametre.getType();

				if (cpt < methode.getParametre().size() - 1)
				{
					signature += ",";
				}
				else
				{
					signature += " )";
				}

			}

			if (methode.getRetour() != null && !methode.getRetour().equals("void"))
			{
				sRet += String.format("%-37s: %s\n", signature, methode.getRetour());
			}
			else
			{
				sRet += signature + "\n";
			}
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

	private String afficherInterface()
	{
		return "<<Interface>>\n";
	}

	public String afficherHeritage()
	{
		return "<<Héritage>>\n";
	}

}