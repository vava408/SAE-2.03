package src.metier;

import java.util.ArrayList;
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
	private LireFichier                        lireFichier    ;
	private DecomposerLigne                    decomposerLigne;
	private ArrayList<String>                  listeImplements;
	private HashMap<String, ArrayList<String>> mapImplements  ;
	private HashMap<String, String>            mapExtends     ;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialisation des structures                 */
	/*--------------------------------------------------------------*/
	public LireHeritImple(LireFichier lireFichier)
	{
		this.lireFichier     = lireFichier;
		this.decomposerLigne = new DecomposerLigne();
		this.mapImplements   = new HashMap<>();
		this.mapExtends      = new HashMap<>();
		this.listeImplements = new ArrayList<>();
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs                                                   */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne les interfaces implémentées par la classe.
	 *
	 * @return map associant nom de classe → nom de l'interface implémentée
	 */
		public HashMap<String, ArrayList<String> > getMapImplements()  { return this.mapImplements;}

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

		String nomClasse = mots[2];
		String motCle = mots[4];

		if (mots[3].equals("extends"))
		{
			// System.out.println(this.lireFichier.getNomClasse());
			this.mapExtends.put(nomClasse, motCle);
		}

		if (mots[3].equals("implements"))
		{
			int index = 0;
			for (String stringMot : mots)
			{
				index++;
				if (this.lireFichier.nomEstDansRepertoire(stringMot) && index > 4)
				{
					this.listeImplements.add(stringMot);
				}
			}
			this.mapImplements.put(nomClasse, this.listeImplements);
		}

		if (mots.length > 6 && mots[5].equals("implements"))
		{
			int index = 0;
			for (String stringMot : mots)
			{
				index++;
				if (this.lireFichier.nomEstDansRepertoire(stringMot) && index > 6)
				{
					this.listeImplements.add(stringMot);
				}
			}
			this.mapImplements.put(nomClasse, this.listeImplements);

		}
	}
}