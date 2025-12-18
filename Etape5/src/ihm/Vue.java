package src.ihm;

import java.util.ArrayList;

import src.Controleur;

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
 * Affichage : visibilité (+ ou -), static (souligné), final ({geler})
 * 
 * @author Groupe 6
 * @version Etape 4 - 10/12/2025
 */
public class Vue
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private Controleur ctrl;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise la vue avec le contrôleur          */
	/*--------------------------------------------------------------*/
	/**
	 * Construit une vue pour afficher les classes analysées.
	 *
	 * @param ctrl le contrôleur contenant les données et le dossier lu
	 */
	public Vue( Controleur ctrl )
	{
		this.ctrl = ctrl;
	}

	/*--------------------------------------------------------------*/
	/* Affichage complet des classes UML                             */
	/*--------------------------------------------------------------*/
	/**
	 * Parcourt toutes les classes lues et produit une chaîne
	 * contenant leur représentation UML textuelle.
	 *
	 * @return chaîne formatée avec toutes les classes
	 */
	public String afficher()
	{
		String sRet = "";

		for ( LireFichier lF : this.ctrl.getLireDossier().getListeFichiers() )
		{
			switch ( lF.getMotCle() )
			{
				case "class"     -> sRet += this.afficherClass( lF, "class"    );
				case "enum"      -> sRet += this.afficherEnum ( lF );
				case "record"    -> sRet += this.afficherClass( lF, "Record"   );
				case "abstract"  -> sRet += this.afficherClass( lF, "Abstract" );
				case "interface" -> sRet += this.afficherClass( lF, "Interface" );
				default         -> {}
			}
		}

		for ( LireFichier lF : this.ctrl.getLireDossier().getListeFichiers() )
		{
			sRet += this.afficherHeritage( lF );
		}

		for ( LireFichier lF : this.ctrl.getLireDossier().getListeFichiers() )
		{
			sRet += this.afficherInterface( lF );
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
	 * @param lF la classe à afficher
	 * @param typeClasse type de déclaration (`class`, `Abstract`, `Record`)
	 * @return chaîne formatée avec la classe et ses membres
	 */
	public String afficherClass( LireFichier lF, String typeClasse )
	{
		String sRet = "";
		String ligne = "------------------------------------------------";

		sRet += ligne + "\n";

		// Affichage du stéréotype si nécessaire
		if ( !typeClasse.equals("class") )
		{
			sRet += String.format("%29s", "<<" + typeClasse + ">>") + "\n";
		}

		// Nom de la classe
		sRet += String.format("%24s", lF.getNomClasse()) + "\n";
		sRet += ligne + "\n";

		// --- Attributs ---
		for ( Attribut attribut : lF.getListeAttributs() )
		{
			this.afficherAttribut( attribut );
		}

		sRet += ligne + "\n";

		// --- Méthodes ---
		for ( Methode methode : lF.getListeMethodes() )
		{
			this.afficherMethode( methode, true );
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
	 * @param lF la classe enum à afficher
	 * @return chaîne formatée avec l'enum et ses constantes
	 */
	public String afficherEnum( LireFichier lF )
	{
		String sRet = "";
		String ligne = "------------------------------------------------";

		sRet += "<<Enumération>>\n";
		sRet += ligne + "\n";
		sRet += String.format("%24s", lF.getNomClasse()) + "\n";
		sRet += ligne + "\n";

		for ( Attribut attribut : lF.getListeAttributs() )
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
	 * @param lF la classe à analyser
	 * @return chaîne formatée listant chaque implémentation
	 */
	public String afficherInterface( LireFichier lF )
	{
		StringBuilder sRet = new StringBuilder();

		if ( lF.getMapImple() != null && !lF.getMapImple().isEmpty() )
		{
			for ( String classe : lF.getMapImple().keySet() )
			{
				ArrayList<String> interfaces = lF.getMapImple().get(classe);
				if ( interfaces != null && !interfaces.isEmpty() )
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
	 * @param lF la classe à analyser
	 * @return chaîne formatée listant chaque héritage
	 */
	public String afficherHeritage( LireFichier lF )
	{
		String sRet = "";

		if ( lF.getMapHerit() != null && !lF.getMapHerit().isEmpty() )
		{
			for ( String classe : lF.getMapHerit().keySet() )
			{
				sRet += classe + " hérite de " + lF.getMapHerit().get(classe) + "\n";
			}
		}

		return sRet;
	}

	public String afficherAttribut( Attribut a )
	{
		String sRet        = "";
		String sVisibilite = "";
		String sFinal      = "";

		if ( a.getVisibilite().equals("private") ) 
		{		
			sVisibilite = "- ";
		}
		else if ( a.getVisibilite().equals( "public" ) )
		{
			sVisibilite = "+ ";
		}
		else
		{
			sVisibilite = "# ";
		}

		if ( a.isFinal() ) sFinal = " {gelé}";
		if ( a.isAddOnly() ) sFinal = " {addOnly}";
		
		sRet += sVisibilite + String.format( "%-15s", a.getNom() ) + " : " + String.format( "%-5s", a.getType() ) + sFinal;

		return sRet;
	}

	public String afficherMethode( Methode m, boolean complet )
	{
		String sRet        = "";
		String sVisibilite = "";
		String sSignature  = "";

		if ( m.getVisibilite().equals("private") )
		{
			sVisibilite = "- "; 
		}
		else if ( m.getVisibilite().equals( "public" ) )
		{
			sVisibilite = "+ ";
		}
		else
		{
			sVisibilite = "# ";
		}

		sSignature = sVisibilite + m.getNom() + " (";

		if ( m.getParametre().isEmpty() )
		{
			sSignature += ")";
		}

		for ( int cpt = 0; cpt < m.getParametre().size(); cpt++ )
		{
			if( !complet && cpt >= 2 )
			{
				sSignature += " ... )";
				break;
			}

			Parametre parametre = m.getParametre().get( cpt );

			sSignature += " " + parametre.getNom() + " : " + parametre.getType();
			if ( cpt < m.getParametre().size() - 1 ) sSignature += ",";
			else sSignature += " )";
		}

		if ( m.getRetour() != null && ! m.getRetour().equals("void") )
		{
			sRet += String.format("%-30s", sSignature ) + " : " + String.format( "%-10s", m.getRetour() );
		}
		else
		{
			sRet += sSignature + "\n";
		}

		return sRet;
	}
}