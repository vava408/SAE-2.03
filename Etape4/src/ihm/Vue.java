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

	/**
	 * Construit une vue pour afficher une classe analysée avec un 
	 * lecteur de fichier.
	 *
	 * @param lireFichier lecteur contenant les données de la classe
	 */
	public Vue(LireFichier lireFichier)
	{
		this.lireFichier = lireFichier;
	}

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

        for ( LireFichier lF : this.ctrl.getLireDossier().getListeFichiers() )
        {
            switch ( lF.getMotCle() )
            {
                case "class"    -> sRet += this.afficherClass( lF, "class"    );
                case "enum"     -> sRet += this.afficherEnum ( lF );
                case "record"   -> sRet += this.afficherClass( lF, "Record"   );
                case "abstract" -> sRet += this.afficherClass( lF, "Abstract" );
                default         -> {}
            }
        }

        return sRet;
    }

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
		String sRet         = "";
		String ligne        = "------------------------------------------------";
		String sVisibilite;

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
            String sModifier = "";
            if ( attribut.getVisibilite().equals("private") ) sVisibilite = "- "; else sVisibilite = "+ ";

            if ( attribut.isFinal() ) sModifier = " {geler}";

            if ( attribut.isStatic() )
            {
                sRet += String.format("%s%-35s: %s%s\n", sVisibilite, "\033[4m" + attribut.getNom() + "\033[0m", attribut.getType(), sModifier);
            }
            else
            {
                sRet += String.format("%s%-35s: %s%s\n", sVisibilite, attribut.getNom(), attribut.getType(), sModifier);
            }
        }

        sRet += ligne + "\n";

        // --- Méthodes ---
        for ( Methode methode : lF.getListeMethodes() )
        {
            if ( methode.getVisibilite().equals("private") ) sVisibilite = "- "; else sVisibilite = "+ ";

            String signature = sVisibilite + methode.getNom() + " (";

            if ( methode.getParametre().isEmpty() )
            {
                signature += ")";
            }
            for ( int cpt = 0; cpt < methode.getParametre().size(); cpt++ )
            {
                Parametre parametre = methode.getParametre().get(cpt);
                signature += " " + parametre.getNom() + " : " + parametre.getType();
                if ( cpt < methode.getParametre().size() - 1 ) signature += ",";
                else signature += " )";
            }

            if ( methode.getRetour() != null && !methode.getRetour().equals("void") )
                sRet += String.format("%-37s: %s\n", signature, methode.getRetour());
            else
                sRet += signature + "\n";
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

<<<<<<< HEAD
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
=======

	/**
	 * Affiche une énumération avec ses constantes au format UML.
	 *
	 * @return chaîne formatée avec l'enum et ses constantes
	 */
	public String afficherEnum()
	{
		String sRet = "";
		String ligne = "------------------------------------------------";
>>>>>>> 620b768a8933da201bb45b94a21f77c17f45ab5f

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

<<<<<<< HEAD
        if ( lF.getMapHerit() != null && !lF.getMapHerit().isEmpty() )
        {
            for ( String classe : lF.getMapHerit().keySet() )
            {
                sRet += classe + " hérite de " + lF.getMapHerit().get(classe) + "\n";
            }
        }
=======

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


	/**
	 * Retourne la classe mère (héritage) de la classe.
	 *
	 * @return chaîne formatée listant chaque héritage
	 */
	public String afficherHeritage()
	{
		String sRet = "";
>>>>>>> 620b768a8933da201bb45b94a21f77c17f45ab5f

        return sRet;
    }
}
