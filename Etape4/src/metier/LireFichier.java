package src.metier;

import java.io.FileInputStream;
import java.util.*;
import src.ihm.Vue;
import src.membres.Attribut;
import src.membres.Methode;

/**
 * Lecture et analyse d'un fichier source Java.
 *
 * Lit un fichier Java ligne par ligne et délègue la décomposition
 * et l'extraction des éléments (attributs, méthodes, héritage,
 * interfaces) à des utilitaires dédiés. Sert de parser pour construire
 * une représentation UML minimale d'une classe Java.
 *
 * @author Groupe 6
 * @version Etape 4 - 08/12/2025
 */
public class LireFichier
{
	/*--------------------------------------------------------------*/
	/* Tableaux de référence pour lecture des mots clés            */
	/*--------------------------------------------------------------*/
	public final String[] TAB_VISIBILITE   = { "public"  , "private"  , "protected"                       };
	public final String[] TAB_MOTCLE       = { "class"   , "interface", "enum"     , "record", "abstract" };
	public final String[] TAB_MODIFIEURS   = { "static"  , "final"    , "abstract" , "native", 
											   "strictfp", "synchronized" };

	/*--------------------------------------------------------------*/
	/* Attributs de la classe                                      */
	/*--------------------------------------------------------------*/
	private LireDossier     lectureDossier     ;
	private LireHeritImple  lireHeritImplements;
	private LireMethode     lireMethode        ;
	private LireAttribut    lireAttribut       ;

	private DecomposerLigne decomposerLigne;

	private String          motCle         ;
	private String          nomClasse      ;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise la lecture d'un fichier           */
	/*--------------------------------------------------------------*/
	public LireFichier( LireDossier lectureDossier, String fileName )
	{
		this.lectureDossier      = lectureDossier           ;
		this.decomposerLigne     = new DecomposerLigne()    ;
		this.lireHeritImplements = new LireHeritImple (this);
		this.lireMethode         = new LireMethode    (this);
		this.lireAttribut        = new LireAttribut   (this);

		lireFichier(fileName);
	}

	/*--------------------------------------------------------------*/
	/* Retourne le nom de la classe lue                             */
	/*--------------------------------------------------------------*/
	/**
	 * Retourne le nom de la classe analysée dans le fichier.
	 *
	 * @return le nom de la classe
	 */
	public String getNomClasse()
	{
		return this.nomClasse;
	}

	/*--------------------------------------------------------------*/
	/* Retourne le mot clé de la classe lue (class, interface, etc)*/
	/*--------------------------------------------------------------*/
	/**
	 * Retourne le mot-clé de déclaration de la classe.
	 *
	 * @return `"class"`, `"interface"`, `"enum"`, `"record"` ou `"abstract"`
	 */
	public String getMotCle()
	{
		return this.motCle;
	}

	/*--------------------------------------------------------------*/
	/* Vérifie si une classe existe dans le répertoire              */
	/*--------------------------------------------------------------*/
	/**
	 * Vérifie si une classe avec le nom donné existe dans le dossier analysé.
	 *
	 * @param nomClasse le nom de la classe à rechercher
	 * @return `true` si la classe a été trouvée
	 */
	public boolean nomEstDansRepertoire(String nomClasse)
	{
		return this.lectureDossier.nomEstDansRepertoire(nomClasse);
	}

	/*---------------------------------------------------------------*/
	/*  Accesseur : retourne les attributs de la classe              */
	/*---------------------------------------------------------------*/
	/**
	 * Retourne la liste des attributs de la classe.
	 *
	 * @return liste des {@link Attribut} analysés
	 */
	public ArrayList<Attribut>     getListeAttributs() { return this.lireAttribut.getListeAttributs();}

	/**
	 * Retourne la liste des méthodes de la classe.
	 *
	 * @return liste des {@link Methode} analysées
	 */
	public ArrayList<Methode>      getListeMethodes () { return this.lireMethode .getListeMethodes ();}

	/**
	 * Retourne la classe mère (héritage) de cette classe.
	 *
	 * @return map `nomClasse` → `nomClasseMère`
	 */
	public HashMap<String, String> getMapHerit      () { return this.lireHeritImplements.getMapExtends   ();}

	/**
	 * Retourne les interfaces implémentées par cette classe.
	 *
	 * @return map `nomClasse` → `nomInterface`
	 */
	public HashMap<String, ArrayList<String> > getMapImple      () { return this.lireHeritImplements.getMapImplements();}

	/*--------------------------------------------------------------*/
	/* Lit le fichier Java et analyse son contenu                   */
	/*--------------------------------------------------------------*/
	private void lireFichier(String fileName)
	{
		Scanner  sc;
		String   ligne;
		String[] tabMots;

		try
		{
			sc = new Scanner( new FileInputStream( fileName ), "UTF8" );

			while (sc.hasNextLine())
			{
				ligne   = sc.nextLine().trim();
				tabMots = this.decomposerLigne.decomposerLigne(ligne);

				if ( ! ligne.startsWith( "import"    )     && ! ligne.isBlank()                     &&
				   (   ligne.startsWith( "private"   )     ||   ligne.startsWith( "public") || 
					   ligne.startsWith( "protected" ) ) )
				{
					if (ligne.contains("implements") || ligne.contains("extends"))
					{
						this.lireHeritImplements.lireHeritImple(ligne);
					}

					if (this.estLaPremiereLigne(tabMots[1]))
					{
						this.motCle    = tabMots[1];
						
						if   ( this.motCle.equals( "abstract" ) ) { this.nomClasse = tabMots[3]; }
						else                                      { this.nomClasse = tabMots[2]; }                       
					}

					if ( this.motCle.equals( "interface" ) )
					{
						if ( ligne.endsWith( ";" ) )
						{
							if ( ligne.contains( "(" ) && ligne.contains( ")" ) )
							{
								this.lireMethode.lireMethode( tabMots );
							}
							else
							{
								this.lireAttribut.lireAttribut( tabMots );
							}
						}
					}
					else
					{
						if (ligne.endsWith(";"))
						{
							this.lireAttribut.lireAttribut(tabMots);
						}
						else
						{
							this.lireMethode .lireMethode (tabMots);
						}
					}
				}
			}

			sc.close();
		}
		catch (Exception e) { e.printStackTrace();}
	}

	/*--------------------------------------------------------------*/
	/* Vérifie si le mot passé est un mot clé de déclaration       */
	/*--------------------------------------------------------------*/
	private boolean estLaPremiereLigne(String mot)
	{
		for (String motCle : TAB_MOTCLE)
		{
			if (mot.equals(motCle))
			{
				return true;
			}
		}
		return false;
	}
}
