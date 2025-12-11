package src.metier;

import java.util.ArrayList;
import java.util.Arrays;
import src.membres.Attribut;

/*-------------------------------------------------------------------*/
/*- Classe LireAttribut : Lit les attributs d'une classe à partir    */
/*- de lignes de code décomposées et les stocke dans une liste.     */
/*- Etape 1                                                           */
/*- Groupe 6                                                          */
/*- Date de création : 08/12/2025 10:30                               */
/*-------------------------------------------------------------------*/

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
	public ArrayList<Attribut> getListeAttributs() 
	{
		return this.listeAttributs;
	}

	/*--------------------------------------------------------------*/
	/* Méthode : lit un attribut à partir des mots fournis         */
	/*--------------------------------------------------------------*/
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
					continue;
				}
			}

			// static
			if (m.equals("static")) 
			{
				isStatic = true; 
				continue;
			}

			// final
			if (m.equals("final"))
			{
				isFinal = true;
				continue;
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