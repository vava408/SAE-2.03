package src.ihm;

import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;
import src.metier.LireFichier;

/*-------------------------------------------------------------------*/
/*- Classe Vue : Gère l’affichage textuel d’une classe UML.          */
/*- Etape 4                                                           */
/*- Groupe 6                                                          */
/*- Date de création : 10/12/2025 14:30                               */
/*-------------------------------------------------------------------*/

public class Vue
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private LireFichier lireFichier;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise la vue avec un lecteur de fichier   */
	/*--------------------------------------------------------------*/
	public Vue(LireFichier lireFichier)
	{
		this.lireFichier = lireFichier;
	}

	/*--------------------------------------------------------------*/
	/* Retourne la représentation textuelle de la classe UML        */
	/*--------------------------------------------------------------*/
	public String afficher()
	{
		String sRet = "";

		switch (this.lireFichier.getMotCle())
		{
			case "class"    -> { sRet = this.afficherClass(this.lireFichier.getMotCle()) ;}
			case "enum"     -> { sRet = this.afficherEnum (                            ) ;}
			case "record"   -> { sRet = this.afficherClass("Record"  )                   ;}
			case "abstract" -> { sRet = this.afficherClass("Abstract")                   ;}
			default         -> { break                                                   ;}
		}

		return sRet;
	}

	/*--------------------------------------------------------------*/
	/* Affiche une classe UML (class, record, abstract…)            */
	/*--------------------------------------------------------------*/
	public String afficherClass(String typeClasse)
	{
		String sRet         = "";
		String ligne        = "------------------------------------------------";
		String sVisibilite;

		if (!typeClasse.equals("class"))
		{
			sRet += "<<" + typeClasse + ">>\n";
		}

		sRet += ligne + "\n";
		sRet += String.format("%24s", this.lireFichier.getNomClasse()) + "\n";
		sRet += ligne + "\n";

		for (Attribut attribut : this.lireFichier.getListeAttributs())
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

		for (Methode methode : this.lireFichier.getListeMethodes())
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

			if (methode.getParametre().isEmpty())
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

	/*--------------------------------------------------------------*/
	/* Affiche une énumération UML                                  */
	/*--------------------------------------------------------------*/
	public String afficherEnum()
	{
		String sRet = "";
		String ligne = "------------------------------------------------";

		sRet += "<<Enumération>>\n";
		sRet += ligne + "\n";
		sRet += String.format("%24s", this.lireFichier.getNomClasse()) + "\n";
		sRet += ligne + "\n";

		for (Attribut attribut : this.lireFichier.getListeAttributs())
		{
			sRet += attribut.getNom() + "\n";
		}

		sRet += ligne + "\n";
		return sRet;
	}

	/*--------------------------------------------------------------*/
	/* Affiche les relations Interface / Implémentation             */
	/*--------------------------------------------------------------*/
	public String afficherInterface()
	{
		String sRet = "";

		if (this.lireFichier.getMapImple() != null && !this.lireFichier.getMapImple().isEmpty())
		{
			for (String classe : this.lireFichier.getMapImple().keySet())
			{
				sRet += classe + " implémente " + this.lireFichier.getMapImple().get(classe) + "\n";
			}
		}

		return sRet;
	}

	/*--------------------------------------------------------------*/
	/* Affiche les relations d’héritage                             */
	/*--------------------------------------------------------------*/
	public String afficherHeritage()
	{
		String sRet = "";

		if (this.lireFichier.getMapHerit() != null && !this.lireFichier.getMapHerit().isEmpty())
		{
			for (String classe : this.lireFichier.getMapHerit().keySet())
			{
				sRet += classe + " hérite de  " + this.lireFichier.getMapHerit().get(classe) + "\n";
			}
		}

		return sRet;
	}
}