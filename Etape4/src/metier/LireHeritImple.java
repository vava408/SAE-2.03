package src.metier;

import java.util.HashMap;

/**
 * Analyse des relations d'héritage et d'implémentation d'interfaces.
 *
 * Extrait les informations `extends` et `implements` des lignes de
 * déclaration de classe pour construire une cartographie complète
 * des dépendances de la classe.
 *
 * @author Groupe 6
 * @version Etape 4 - 10/12/2025
 */
public class LireHeritImple
{
	/*--------------------------------------------------------------*/
	/* Attributs                                                    */
	/*--------------------------------------------------------------*/
	private LireFichier             lireFichier    ;
	private DecomposerLigne         decomposerLigne;
	private HashMap<String, String> mapImplements  ;
	private HashMap<String, String> mapExtends     ;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialisation des structures                 */
	/*--------------------------------------------------------------*/
	public LireHeritImple(LireFichier lireFichier)
	{
		this.lireFichier     = lireFichier;
		this.decomposerLigne = new DecomposerLigne();
		this.mapImplements   = new HashMap<>();
		this.mapExtends      = new HashMap<>();
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs                                                   */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne les interfaces implémentées par la classe.
	 *
	 * @return map associant nom de classe → nom de l'interface implémentée
	 */
	public HashMap<String, String> getMapImplements() { return this.mapImplements;}

	/**
	 * Retourne la classe mère de chaque classe (héritage).
	 *
	 * @return map associant nom de classe → nom de la classe mère
	 */
	public HashMap<String, String> getMapExtends()    { return this.mapExtends   ;}

	/*--------------------------------------------------------------*/
	/* Analyse la ligne reçue et extrait extends / implements       */
	/*--------------------------------------------------------------*/
	/**
	 * Analyse une ligne de déclaration de classe pour extraire
	 * les informations d'héritage (`extends`) et d'implémentation (`implements`).
	 *
	 * @param ligne ligne de déclaration contenant `extends` ou `implements`
	 */
	public void lireHeritImple(String ligne)
	{
		String[] mots = this.decomposerLigne.decomposerLigne(ligne);

		if (mots.length < 5)
		{
			return;
		}

		String nomClasse = mots[2];
		String motCle    = mots[4];

		// Vérification de "implements"                            

		if (mots[3].equals("implements"))
		{
			if (this.lireFichier.nomEstDansRepertoire(motCle))
			{
				this.mapImplements.put(nomClasse, motCle);
			}
		}

		//vérification s'il y a extends
		if (mots[3].equals("extends"))
		{
			this.mapExtends.put(nomClasse, motCle);
		}

		// Vérification d’un "implements" après "extends"
		if (mots.length > 6 && mots[5].equals("implements"))
		{
			motCle = mots[6];
			if (this.lireFichier.nomEstDansRepertoire(motCle))
			{
				this.mapImplements.put(nomClasse, motCle);
			}
		}
	}
}