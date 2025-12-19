package src.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe chargée de lire et analyser les relations d'héritage (extends) et
 * d'implémentation (implements) dans une déclaration de classe Java.
 */
public class LireHeritImple implements Serializable
{
	// Objet permettant la lecture du fichier source
	private LireFichier lireFichier;

	// Outil de découpage de ligne (non sérialisé)
	private transient DecomposerLigne decomposerLigne;

	// Liste temporaire des interfaces implémentées
	private ArrayList<String> listeImplements;

	// Map : NomClasse -> Liste des interfaces implémentées
	private HashMap<String, ArrayList<String>> mapImplements;

	// Map : NomClasse -> Classe mère (extends)
	private HashMap<String, String> mapExtends;

	// Tableau de mots issu du découpage d'une ligne
	private String[] mot;

	/**
	 * Constructeur
	 * 
	 * @param lireFichier
	 *            objet permettant l'accès au fichier analysé
	 */
	public LireHeritImple(LireFichier lireFichier)
	{
		this.lireFichier = lireFichier;
		this.decomposerLigne = new DecomposerLigne();
		this.mapImplements = new HashMap<>();
		this.mapExtends = new HashMap<>();
		this.listeImplements = new ArrayList<>();
	}

	public void setHerit(String classeFille, String classeMere)
	{
		mapExtends.put(classeFille, classeMere);
	}

	public void setImplement(String classeFille, ArrayList<String> classeMere)
	{
		mapImplements.put(classeFille, classeMere);
	}

	/**
	 * Analyse une ligne découpée en mots afin de détecter : - le nom de la
	 * classe - la classe héritée (extends) - les interfaces implémentées
	 * (implements)
	 *
	 * @param mots
	 *            tableau de mots issus d'une ligne de code Java
	 */
	public void lireHeritImple(String[] mots)
	{
		String nomClasse = "";

		// Recherche de l'index du mot "class"
		int indexClass = -1;
		for (int i = 0; i < mots.length; i++)
		{
			if (mots[i].equals("class"))
			{
				indexClass = i;
				break;
			}
		}

		// Si "class" n'existe pas ou qu'il n'y a pas de nom après, on arrête
		if (indexClass == -1 || indexClass + 1 >= mots.length)
			return;

		// Le nom de la classe est le mot juste après "class"
		nomClasse = mots[indexClass + 1];

		// Recherche du mot "extends"
		int indexExtends = -1;
		for (int i = indexClass + 2; i < mots.length; i++)
		{
			if (mots[i].equals("extends"))
			{
				indexExtends = i;
				break;
			}
		}

		// Recherche du mot "implements"
		int indexImplements = -1;
		for (int i = indexClass + 2; i < mots.length; i++)
		{
			if (mots[i].equals("implements"))
			{
				indexImplements = i;
				break;
			}
		}

		// ----- Gestion de l'héritage (extends) -----
		if (indexExtends != -1)
		{
			// La fin du extends est soit "implements", soit la fin de la ligne
			int endExtends = (indexImplements != -1) ? indexImplements : mots.length;

			// Vérifie qu'il existe bien une classe après "extends"
			if (indexExtends + 1 < endExtends)
			{
				String superClass = mots[indexExtends + 1];
				this.mapExtends.put(nomClasse, superClass);
			}
		}

		// ----- Gestion des interfaces (implements) -----
		if (indexImplements != -1)
		{
			ArrayList<String> lstInteface = new ArrayList<>();

			// Parcourt les mots après "implements"
			for (int i = indexImplements + 1; i < mots.length; i++)
			{
				String mot = mots[i];

				// Ignore les virgules et les mots vides
				if (!mot.equals(",") && !mot.isEmpty())
				{
					lstInteface.add(mot);
				}
			}

			// Association de la classe avec ses interfaces
			this.mapImplements.put(nomClasse, lstInteface);
		}
	}

	/**
	 * @return la map des interfaces implémentées par chaque classe
	 */
	public HashMap<String, ArrayList<String>> getMapImplements()
	{
		return this.mapImplements;
	}

	/**
	 * @return la map des relations d'héritage (classe -> classe mère)
	 */
	public HashMap<String, String> getMapExtends()
	{
		return this.mapExtends;
	}
}
