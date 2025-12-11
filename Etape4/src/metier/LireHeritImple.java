package src.metier;

import java.util.HashMap;

/*-----------------------------------------------------------------------*/
/*- Classe LireHeritImple : analyse des mots-clés extends / implements   */
/*- Etape 4                                                              */
/*- Groupe 6                                                             */
/*- Date de création : 10/12/2025 15:00                                  */
/*-----------------------------------------------------------------------*/

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
	public HashMap<String, String> getMapImplements() { return this.mapImplements;}

	public HashMap<String, String> getMapExtends()    { return this.mapExtends   ;}

	/*--------------------------------------------------------------*/
	/* Analyse la ligne reçue et extrait extends / implements       */
	/*--------------------------------------------------------------*/
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