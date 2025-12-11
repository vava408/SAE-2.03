package src.metier;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;

/*--------------------------------------------------------------------*/
/*- Classe LireFichier : lit un fichier source et extrait les         */
/*-                      attributs et méthodes d'une classe UML.      */
/*- Exercice 5                                                        */
/*- Groupe 6                                                          */
/*- Date de création : 08/12/2025 11:10                               */
/*--------------------------------------------------------------------*/

public class LireFichier
{
	/*--------------------------------------------------------------*/
	/* Constantes                                                   */
	/*--------------------------------------------------------------*/
	private static final String[] TAB_VISIBILITE    = { "public", "private", "protected" };
	private static final String[] TAB_VISIBILITE_FR = { "public", "privée" ,  "protégée" };

	/*--------------------------------------------------------------*/
	/* Attributs préfixés                                           */
	/*--------------------------------------------------------------*/
	private static int mNbAttributs = 0;

	private Attribut[] mListeAttributs = new Attribut[200];
	private Methode [] mListeMethodes  = new Methode [200];

	private int mIndexAttribut = 0;
	private int mIndexMethode  = 0;

	/*--------------------------------------------------------------*/
	/* Lecture principale du fichier                                */
	/*--------------------------------------------------------------*/
	private void lireFichier(String fileName)
	{
		try
		{
			InputStream flux = new FileInputStream(fileName    );
			Scanner     sc   = new Scanner        (flux, "UTF8");

			while (sc.hasNextLine())
			{
				String ligne = sc.nextLine();

				ligne = ligne.replace   ("(", " ");
				ligne = ligne.replace   (")", " ");
				ligne = ligne.replace   ("{", " ");
				ligne = ligne.replace   ("}", " ");
				ligne = ligne.replaceAll("\\s+", " ").trim();

				if (!ligne.startsWith("import") && !ligne.isBlank() &&
					(ligne.contains("private")       ||  ligne.contains("public")))
				{
					if (ligne.endsWith(";"))
					{
						lireAttribut(ligne);
					}
					else
					{
						lireMethode(ligne);
					}
				}
			}

			sc.close();
		}
		catch (Exception e) { e.printStackTrace();}
	}

	/*--------------------------------------------------------------*/
	/* Lecture d'un attribut                                        */
	/*--------------------------------------------------------------*/
	private void lireAttribut(String ligne)
	{
		String nom ;
		String type;
		String visibilite = TAB_VISIBILITE_FR[0];
		String portee     = "classe";

		String[] mots = ligne.split  (" ");
		ligne         = ligne.replace(";", "");

		for (int i = 0; i < TAB_VISIBILITE.length; i++)
		{
			if (mots[0].equals(TAB_VISIBILITE [i]))
			{
				visibilite = TAB_VISIBILITE_FR[i];
			}
		}

		if (!mots[1].equals("static"))
		{
			portee = "instance";
			type   = mots[1];
			nom    = mots[2];
		}
		else
		{
			type = mots[2];
			nom  = mots[3];
		}

		Attribut a = new Attribut(++mNbAttributs, nom, type, visibilite, portee);
		mListeAttributs[mIndexAttribut++] = a;
	}

	/*--------------------------------------------------------------*/
	/* Lecture d'une méthode                                        */
	/*--------------------------------------------------------------*/
	private void lireMethode(String ligne)
	{
		int    nbParam = 0;
		String visibilite;
		String nom;
		String typeRetour;

		ligne = ligne.replace("(", " ");
		ligne = ligne.replace(")", " ");

		String[] split = ligne.split(" ");

		if (split.length % 2 == 0)
		{
			lireConstructeur(split);
			return;
		}

		visibilite = split[0];
		typeRetour = split[1];
		nom        = split[2];

		if (typeRetour.equals("class"))
		{
			return;
		}

		Parametre[] tabParam = new Parametre[20];

		if (split.length > 3)
		{
			for (int i = 3; i + 1 < split.length; i += 2)
			{
				String typeP = split[i];
				String nomP  = split[i + 1];
				nbParam++;

				tabParam[nbParam - 1] = new Parametre(nbParam, nomP, typeP);
			}
		}

		Methode m = new Methode(nom, visibilite, typeRetour, tabParam);
		mListeMethodes[mIndexMethode++] = m;
	}

	/*--------------------------------------------------------------*/
	/* Lecture d'un constructeur                                    */
	/*--------------------------------------------------------------*/
	private void lireConstructeur(String[] split)
	{
		int    nbParam       = 0;
		String visibilite    = split[0];
		String nom           = "Constructeur";

		Parametre[] tabParam = new Parametre[20];

		if (split.length > 2)
		{
			for (int i = 2; i + 1 < split.length; i += 2)
			{
				nbParam++;
				tabParam[nbParam - 1] = new Parametre(nbParam, split[i + 1], split[i]);
			}
		}

		Methode m = new Methode(nom, visibilite, null, tabParam);
		mListeMethodes[mIndexMethode++] = m;
	}

	/*--------------------------------------------------------------*/
	/* Affichage de tous les éléments extraits                      */
	/*--------------------------------------------------------------*/
	public String toString()
	{
		String result = "";

		for (int i = 0; i < mIndexAttribut; i++)
		{
			result += mListeAttributs[i].toString() + "\n";
		}

		result += "\n";

		for (int i = 0; i < mIndexMethode; i++)
		{
			result += mListeMethodes[i].toString() + "\n";
		}

		return result;
	}

	/*--------------------------------------------------------------*/
	/* Méthode principale                                            */
	/*--------------------------------------------------------------*/
	public static void main(String[] arg)
	{
		LireFichier lf = new LireFichier();
		lf.lireFichier(arg[0]);
		System.out.println(lf.toString());
	}
}