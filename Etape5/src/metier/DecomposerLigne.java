package src.metier;

import java.util.ArrayList;
import java.util.Scanner;

/*------------------------------------------------------------------------*/
/*- Classe DecomposerLigne : Analyse et fragmente une ligne de code      */
/*- Auteurs : Groupe 6                                                    */
/*- Date de création : 08/12/2025 14:30                                   */
/*------------------------------------------------------------------------*/

/**
 * Décompose une ligne de code source en mots utiles pour analyse.
 *
 * Supprime les caractères spéciaux ( parenthèses, accolades, virgules )
 * et fragmente la ligne selon les espaces pour faciliter l'extraction
 * des tokens significatifs.
 */
public class DecomposerLigne
{

	/*------------------------- Méthodes publiques ------------------------*/

	/**
	 * Décompose une ligne de code en un tableau de tokens significatifs.
	 *
	 * @param ligne la ligne de code à décomposer
	 * @return tableau de mots extraits de la ligne
	 */
	public String[] decomposerLigne( String ligne )
	{
		ArrayList<String> lstMots;
		String            s;
		Scanner           sc;
		String[]          tabRet;
		int               i;

		// Création d'une liste pour stocker chaque mot
		lstMots = new ArrayList<String>();

		// Retrait des parenthèses
		ligne = ligne.replace( "(", " " );
		ligne = ligne.replace( ")", " " );

		// Retrait des accolades
		ligne = ligne.replace( "{", " " );
		ligne = ligne.replace( "}", " " );

		// Retrait des virgules
		ligne = ligne.replace( ",", " " );

		// Retrait des tirets
		ligne = ligne.replace( "-", " " );

		// Retrait des plus
		ligne = ligne.replace( "+", " " );

		// Retrait des deux points
		ligne = ligne.replace( ":", " " );

		// Espaces autour des égaux
		ligne = ligne.replace( "=", " = " );

		// Suppression des doubles espaces
		ligne = ligne.replaceAll( "\\s+", " " ).trim();

		// Création d'un scanner pour traiter la ligne
		sc = new Scanner( ligne ).useDelimiter( "\\s" );

		// Parcours de chaque mot délimité par un espace
		while ( sc.hasNext() )
		{
			s = sc.next();

			// Suppression des espaces en trop
			s = s.trim();

			// Ignorer les commentaires
			if ( s.startsWith( "//" ) || s.startsWith( "/*" ) || s.startsWith( "*/" ) )
			{
				s = "";
			}

			// Ignorer les chaînes de caractères
			if ( s.startsWith( "\"" ) )
			{
				s = "";
			}

			// Quitter si on rencontre throws
			if ( s.startsWith( "throws" ) )
			{
				break;
			}

			// Quitter si on rencontre un égal
			if ( s.startsWith( "=" ) )
			{
				break;
			}

			// Ajout du mot à la liste s'il n'est pas vide
			if ( !s.isBlank() )
			{
				lstMots.add( s );
			}
		}

		sc.close();

		// Création d'un tableau de la taille de la liste
		tabRet = new String[lstMots.size()];

		// Transfert de chaque mot de la liste dans le tableau
		for ( i = 0; i < lstMots.size(); i++ )
		{
			tabRet[i] = lstMots.get( i );
		}

		return tabRet;
	}
}