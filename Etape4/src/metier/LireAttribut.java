package src.metier;

import java.util.ArrayList;
import java.util.Arrays;
import src.membres.Attribut;

/**
 * Analyse et extraction des attributs d'une classe Java.
 *
 * Lit les attributs (visibilité, type, nom, modificateurs) à partir
 * de lignes de code décomposées et les stocke dans une liste pour
 * permettre une représentation UML complète.
 *
 * @author Groupe 6
 * @version Etape 4 - 08/12/2025
 */
public class LireAttribut
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private LireFichier            lireFichier;
	private ArrayList<Attribut>    listeAttributs;
	private int                    compteurId = 0;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise la liste d'attributs             */
	/*--------------------------------------------------------------*/
	public LireAttribut(LireFichier lireFichier) 
	{
		this.lireFichier    = lireFichier;
		this.listeAttributs = new ArrayList<>();
	}

	/*--------------------------------------------------------------*/
	/* Accesseur : retourne la liste des attributs lus             */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne la liste complète des attributs extraits de la classe.
	 *
	 * @return liste des {@link Attribut} analysés
	 */
	public ArrayList<Attribut> getListeAttributs() 
	{
		return this.listeAttributs;
	}

	/*--------------------------------------------------------------*/
	/* Méthode : lit un attribut à partir des mots fournis         */
	/*--------------------------------------------------------------*/
	/**
	 * Analyse une ligne décomposée et crée un objet {@link Attribut}.
	 *
	 * Extrait la visibilité, le type, le nom, et les modificateurs
	 * (static, final) de la signature d'attribut.
	 *
	 * @param mots tableau de tokens provenant de {@link DecomposerLigne}
	 */
	public void lireAttribut(String[] mots) 
	{
		String visibilite = "default";
		String type       = "";
		String nom        = "";
		boolean isStatic  = false;
		boolean isFinal   = false;

		// retrait du ;
		for (int cpt = 0; cpt < mots.length; cpt++)
		{
			mots[cpt] = mots[cpt].replace(";", "");
		}

		// 1. Analyse des mots pour visibilité, static et final
		for (String m : mots) 
		{
			// visibilite
			for (String s : this.lireFichier.TAB_VISIBILITE)
			{
				if (s.contains(m)) 
				{
					visibilite = m; 
				}
			}

			// static
			if (m.equals("static")) 
			{
				isStatic = true; 
			}

			// final
			if (m.equals("final"))
			{
				isFinal = true;
			}
		}

		// 2. Récupérer type et nom
		if (mots.length >= 2) 
		{
			nom  = mots[mots.length - 1];
			type = mots[mots.length - 2];
		}
		else
		{
			System.out.println("Impossible de lire type/nom dans : " + Arrays.toString(mots));
			return;
		}

		// 3. Créer l'objet Attribut
		Attribut a = new Attribut(this.compteurId++, nom, type, visibilite, isStatic, isFinal);

		// 4. Ajouter à la liste
		this.listeAttributs.add(a);
	}
}