package src.metier;

import java.util.ArrayList;
import src.membres.Methode;
import src.membres.Parametre;

/**
 * Analyse et extraction des méthodes d'une classe Java.
 *
 * Fournit la lecture des signatures de méthode (visibilité, nom,
 * type de retour, paramètres, modificateurs) et construit des
 * instances de {@link src.membres.Methode}.
 *
 * @author Groupe 6
 * @version Etape 4 - 09/12/2025
 */
public class LireMethode
{
	/*--------------------------------------------------------------*/
	/* Attributs                                                    */
	/*--------------------------------------------------------------*/
	private LireFichier        lireFichier;
	private ArrayList<Methode> listeMethodes = new ArrayList<>();

	/*--------------------------------------------------------------*/
	/* Constructeur                                                 */
	/*--------------------------------------------------------------*/
	public LireMethode(LireFichier lireFichier)
	{
		this.lireFichier = lireFichier;
	}

	/*--------------------------------------------------------------*/
	/* Accesseur : retourne la liste des méthodes                   */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne la liste complète des méthodes extraites de la classe.
	 *
	 * @return liste des {@link Methode} analysées
	 */
	public ArrayList<Methode> getListeMethodes() { return this.listeMethodes ;}

	/*--------------------------------------------------------------*/
	/* Lecture et construction d'une méthode                        */
	/*--------------------------------------------------------------*/
	/**
	 * Analyse une ligne décomposée et crée un objet {@link Methode}.
	 *
	 * Extrait la visibilité, le nom, le type de retour, les paramètres,
	 * et les modificateurs (static, final) de la signature de méthode.
	 * Détecte également les constructeurs.
	 *
	 * @param mots tableau de tokens provenant de {@link DecomposerLigne}
	 */
	public void lireMethode(String[] mots)
	{
		int     nbParametre   = 0;
		boolean estStatic     = false;
		boolean estFinal      = false;
		boolean constructeur  = false;

		String  visibilite    = mots[0];
		String  nom           = "";
		String  typeRetour    = "";
		String  typeParametre;
		String  nomParametre ;

		ArrayList<Parametre> tabParametres = new ArrayList<>();

		int cpt = 1;

		// Analyse des mots de la signature
		for (int i = 0; i < mots.length; i++)
		{
			String m = mots[i];

			// static ?
			if (m.equals("static"))
			{
				estStatic = true;
				continue;
			}

			// final ?
			if (m.equals("final"))
			{
				estFinal = true;
				continue;
			}

			// autres modificateurs = on avance
			for (String mod : this.lireFichier.TAB_MODIFIEURS)
			{
				if (mod.equals(m))
				{
					cpt++;
					continue;
				}
			}

			// constructeur = même nom que la classe
			if (this.lireFichier.getNomClasse().equals(m))
			{
				constructeur = true;
			}

			// si mot-clé de classe = ne pas analyser
			for (String motCle : this.lireFichier.TAB_MOTCLE)
			{
				if (m.equals(motCle))
					return;
			}
		}

		// Extraction du nom et du type retour
		if (cpt <= mots.length)
		{
			if (constructeur)
			{
				typeRetour = null;
				nom = mots[cpt];
				cpt++;
			}
			else
			{
				typeRetour = mots[cpt];
				nom        = mots[cpt + 1];
				cpt += 2;
			}

			// Lecture des paramètres
			while (cpt + 1 < mots.length)
			{
				typeParametre = mots[cpt];
				nomParametre  = mots[cpt + 1];

				nbParametre++;
				tabParametres.add(new Parametre(nbParametre, nomParametre, typeParametre));

				cpt += 2;
			}
		}

		Methode methode = new Methode(nom, visibilite, typeRetour, tabParametres, estStatic, estFinal);

		this.listeMethodes.add(methode);
	}
}