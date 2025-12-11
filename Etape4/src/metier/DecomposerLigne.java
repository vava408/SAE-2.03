package src.metier;
import java.util.ArrayList;
import java.util.Scanner;

/*-------------------------------------------------------------------*/
/*- Classe DecomposerLigne : Décompose une ligne de code pour        */
/*- extraction des mots utiles.                                      */
/*- Etape 1                                                          */
/*- Groupe 6                                                         */
/*- Date de création : 08/12/2025 10:30                              */
/*-------------------------------------------------------------------*/

public class DecomposerLigne
{
	/*--------------------------------------------------------------*/
	/* Méthode : décomposer une ligne de code en mots utiles        */
	/*--------------------------------------------------------------*/
	public String[] decomposerLigne(String ligne)
	{
		// création d'une liste pour stocker chaque mot et un string temporaire
		ArrayList<String> lstMots = new ArrayList<String>();
		String s;

		// on retire les parenthèses
		ligne = ligne.replace("(", " ");
		ligne = ligne.replace(")", " ");

		// on retire les accolades
		ligne = ligne.replace("{", " ");
		ligne = ligne.replace("}", " ");

		// on retire les virgules
		ligne = ligne.replace(",", " ");

		// on retire les égaux
		ligne = ligne.replace("=", " ");

		// on supprime tous les doubles espaces pour faciliter le traitement
		ligne = ligne.replaceAll("\\s+", " ").trim();


		// création d'un scanner pour traiter la ligne
		Scanner sc = new Scanner(ligne).useDelimiter("\\s");
		{
			// parcours chaque mot délimité par un espace
			while(sc.hasNext())
			{
				s = sc.next();

				// on supprime les espaces en trop
				s = s.trim();

				// on ignore les commentaires
				if(s.startsWith("//") || s.startsWith("/*") || s.startsWith("*/"))
				{
					s = "";
				}

				// on ignore les chaînes de caractères
				if(s.startsWith("\""))
				{
					s = "";
				}
				
				// quitte la boucle si on rencontre throws (toujours à la fin d'une méthode)
				if(s.startsWith("throws"))
				{
					break;
				}

				// on ajoute le mot à la liste si elle n'est pas vide
				if(!s.isBlank())
				{
					lstMots.add(s);
				}
			}
		}
		sc.close();

		// création d'un tableau de la taille de la liste, il est nécessaire pour les prochains traitements
		String[] tabRet = new String[lstMots.size()];

		// transfert chaque mot de la liste dans le tableau
		for(int i = 0; i < lstMots.size(); i++)
		{
			tabRet[i] = lstMots.get(i);
		}

		return tabRet;
	}

	// enlever les commentaires
	// enlever les exceptions
	// enlever les "strings"
	// vérifier si on me donne les imports
}