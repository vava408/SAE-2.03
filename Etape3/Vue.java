public class Vue
{
	private LireFichier lireFichier;


	public Vue( LireFichier lireFichier )
	{
		this.lireFichier = lireFichier;
	}

	public String afficher (String parametre)
	{
		String sRet = "";

		switch (parametre)
		{
			case "class":
				return this.afficherClass(parametre);
		
			case "enum":
				return this.afficherEnum();
			
			case "record":
				return this.afficherClass(parametre);

			case "abstract":
				return this.afficherClass(parametre);

			default:
				break;
			
		}
		return sRet;
	}

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

			if (attribut.getVisibilite().equals("privée"))
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
			if (methode.getVisibilite().equals("privée"))
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

	private String afficherInterface()
	{
		return "<<Interface>>\n";
	}

	public String afficherHeritage()
	{
		return "<<Héritage>>\n";
	}

}