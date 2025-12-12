package src.ihm;

import java.util.ArrayList;

import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;
import src.metier.LireFichier;

/**
 * Gère l'affichage textuel formaté d'une classe UML.
 *
 * Produit une représentation ASCII d'une classe avec ses attributs, méthodes,
 * héritages et interfaces implémentées. Supporte les classes abstraites,
 * enumerations, records et classes standard.
 *
 * @author Groupe 6
 * @version Etape 4 - 10/12/2025
 */
public class Vue
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private LireFichier lireFichier;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise la vue avec un lecteur de fichier   */
	/*--------------------------------------------------------------*/
	/**
	 * Construit une vue pour afficher une classe analysée.
	 *
	 * @param lireFichier lecteur contenant les données de la classe
	 */
	public Vue(LireFichier lireFichier)
	{
		this.lireFichier = lireFichier;
	}

	/*--------------------------------------------------------------*/
	/* Retourne la représentation textuelle de la classe UML        */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne la représentation UML complète de la classe.
	 *
	 * Dirige le rendu selon le type de la classe
	 * (classe, abstract, enum, record).
	 *
	 * @return chaîne formatée contenant le diagramme UML textuel
	 */
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
	/**
	 * Affiche une classe avec ses attributs et méthodes au format UML.
	 *
	 * Formatage :
	 * - Attributs : visibilité (+ ou -), nom, type
	 * - Méthodes : visibilité, signature avec paramètres, type de retour
	 * - Static : souligné
	 * - Final : marqué avec `{geler}`
	 *
	 * @param typeClasse type de déclaration (`class`, `Abstract`, `Record`)
	 * @return chaîne formatée avec la classe et ses membres
	 */
	public String afficherClass(String typeClasse)
	{
		final String ANSI_UNDERLINE = "\033[4m";
		final String ANSI_RESET = "\033[0m";
		String sRet         = "";
		String ligne        = "------------------------------------------------";
		String sVisibilite;

		sRet += ligne + "\n";
		
		if (!typeClasse.equals("class"))
		{
			sRet += String.format("%29s","<<" + typeClasse + ">>\n");
		}

		sRet += String.format("%24s", this.lireFichier.getNomClasse()) + "\n";
		sRet += ligne + "\n";

		for (Attribut attribut : this.lireFichier.getListeAttributs())
		{
			String sModifier = ""; // pour final ou autres annotations

			// Déterminer la visibilité
			if (attribut.getVisibilite().equals("private"))
			{
				sVisibilite = "- ";
			}
			else
			{
				sVisibilite = "+ "; // public ou autre
			}

			// Ajouter l'indication "final" si nécessaire
			if (attribut.isFinal())
			{
				sModifier = " {geler}";
				sRet += String.format("%s%-35s: %s%s\n", sVisibilite, attribut.getNom(), attribut.getType(), sModifier);

			}
			// Souligner si static
			else if (attribut.isStatic())
			{
				sRet += String.format("%s%-35s: %s%s\n", sVisibilite, "\033[4m" + attribut.getNom() + "\033[0m", attribut.getType(), sModifier);

			}
			else
			{
				sRet += String.format("%s%-35s: %s%s\n", sVisibilite, attribut.getNom(), attribut.getType(), sModifier);
			}
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
	/**
	 * Affiche une énumération avec ses constantes au format UML.
	 *
	 * @return chaîne formatée avec l'enum et ses constantes
	 */
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
	/**
	 * Retourne la liste des interfaces implémentées par la classe.
	 *
	 * @return chaîne formatée listant chaque implémentation
	 */
		public String afficherInterface()
	{
		StringBuilder sRet = new StringBuilder();
	
		if (this.lireFichier.getMapImple() != null && !this.lireFichier.getMapImple().isEmpty())
		{
			for (String classe : this.lireFichier.getMapImple().keySet())
			{
				ArrayList<String> interfaces = this.lireFichier.getMapImple().get(classe);
				if (interfaces != null && !interfaces.isEmpty())
				{
					sRet.append(classe)
						.append(" implémente ")
						.append(String.join(", ", interfaces))
						.append("\n");
				}
			}
		}
	
		return sRet.toString();
	}
	/*--------------------------------------------------------------*/
	/* Affiche les relations d'héritage                             */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne la classe mère (héritage) de la classe.
	 *
	 * @return chaîne formatée listant chaque héritage
	 */
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