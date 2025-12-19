package src.metier;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import src.membres.Attribut;
import src.membres.Methode;

/*------------------------------------------------------------------------*/
/*- Classe LireFichier : Analyse un fichier Java et extrait ses données  */
/*- Auteurs : Groupe 6                                                    */
/*- Date de création : 08/12/2025 16:45                                   */
/*------------------------------------------------------------------------*/

/**
 * Lit et analyse un fichier Java pour en extraire la structure.
 *
 * Décompose le fichier source pour identifier la classe, ses attributs,
 * méthodes, relations d'héritage et implémentations d'interfaces.
 * Gère également les records et calcule les dimensions pour l'affichage.
 */
public class LireFichier implements Serializable
{
	public final String[]             TAB_VISIBILITE = { "public", "private", "protected" };
	public final String[]             TAB_MOTCLE     = { "class", "interface", "enum",
	                                                      "record", "abstract" };
	public final String[]             TAB_MODIFIEURS = { "static", "final", "abstract",
	                                                      "native", "strictfp", "synchronized" };

	private LireDossier               lectureDossier     ;
	private transient DecomposerLigne decomposerLigne    ;
	private LireMethode               lireMethode        ;
	private LireAttribut              lireAttribut       ;
	private LireHeritImple            lireHeritImplements;

	private String                    motCle, nomClasse     ;
	private int                       posX  , posY          ;
	private int                       hauteurMax, largeurMax;

	/*------------------------- Méthodes publiques ------------------------*/

	/**
	 * Construit un lecteur de fichier et analyse le fichier.
	 *
	 * @param lectureDossier le lecteur de dossier parent
	 * @param fileName le nom du fichier à lire
	 */
	public LireFichier( LireDossier lectureDossier, String fileName )
	{
		this.lectureDossier      = lectureDossier;
		this.decomposerLigne     = new DecomposerLigne();
		this.lireMethode         = new LireMethode   ( this );
		this.lireAttribut        = new LireAttribut  ( this );
		this.lireHeritImplements = new LireHeritImple( this );

		this.lireFichier( fileName );
	}

	/**
	 * Construit un lecteur de fichier à partir de données existantes.
	 *
	 * @param nom le nom de la classe
	 * @param motCle le mot-clé de la classe
	 * @param lstMethode le lecteur de méthodes
	 * @param lstAttribut le lecteur d'attributs
	 * @param posX position horizontale
	 * @param posY position verticale
	 */
	public LireFichier( String nom, String motCle, LireMethode lstMethode,
	                    LireAttribut lstAttribut, int posX, int posY )
	{
		this.nomClasse    = nom;
		this.motCle       = motCle;
		this.lireMethode  = lstMethode;
		this.lireAttribut = lstAttribut;
		this.posX         = posX;
		this.posY         = posY;
	}

	/**
	 * Retourne le nom de la classe lue.
	 *
	 * @return le nom de la classe
	 */
	public String getNomClasse()
	{
		return this.nomClasse;
	}

	/**
	 * Retourne le mot clé de la classe.
	 *
	 * @return le mot clé ( class, interface, enum, record, abstract )
	 */
	public String getMotCle()
	{
		return this.motCle;
	}

	/**
	 * Vérifie si un nom de classe existe dans le répertoire.
	 *
	 * @param nomClasse le nom de la classe à rechercher
	 * @return vrai si la classe existe
	 */
	public boolean nomEstDansRepertoire( String nomClasse )
	{
		return this.lectureDossier.nomEstDansRepertoire( nomClasse );
	}

	/**
	 * Retourne la liste des attributs lus.
	 *
	 * @return liste des attributs
	 */
	public ArrayList<Attribut> getListeAttributs()
	{
		return this.lireAttribut.getListeAttributs();
	}

	/**
	 * Retourne la liste des méthodes lues.
	 *
	 * @return liste des méthodes
	 */
	public ArrayList<Methode> getListeMethodes()
	{
		return this.lireMethode.getListeMethodes();
	}

	/**
	 * Retourne la map des héritages.
	 *
	 * @return HashMap des extends
	 */
	public HashMap<String, String> getMapHerit()
	{
		return this.lireHeritImplements.getMapExtends();
	}

	/**
	 * Retourne la map des implémentations.
	 *
	 * @return HashMap des implements
	 */
	public HashMap<String, ArrayList<String>> getMapImple()
	{
		return this.lireHeritImplements.getMapImplements();
	}

	/**
	 * Retourne la position horizontale.
	 *
	 * @return position X
	 */
	public int getPosX()
	{
		return this.posX;
	}

	/**
	 * Retourne la position verticale.
	 *
	 * @return position Y
	 */
	public int getPosY()
	{
		return this.posY;
	}

	/**
	 * Définit la position du fichier.
	 *
	 * @param x position horizontale
	 * @param y position verticale
	 */
	public void setPosition( int x, int y )
	{
		this.posX = x;
		this.posY = y;
	}

	/**
	 * Retourne la hauteur.
	 *
	 * @return hauteur
	 */
	public int getHauteur()
	{
		return this.posX;
	}

	/**
	 * Retourne la largeur.
	 *
	 * @return largeur
	 */
	public int getLargeur()
	{
		return this.posY;
	}

	/**
	 * Retourne le lecteur d'héritage et implémentation.
	 *
	 * @return le lecteur d'héritage
	 */
	public LireHeritImple getLireHeritImplement()
	{
		return this.lireHeritImplements;
	}

	/**
	 * Définit la taille maximale.
	 *
	 * @param h hauteur maximale
	 * @param l largeur maximale
	 */
	public void setTaille( int h, int l )
	{
		this.hauteurMax = h;
		this.largeurMax = l;
	}

	/**
	 * Définit le lecteur de dossier.
	 *
	 * @param lireDossier le nouveau lecteur de dossier
	 */
	public void setLireDossier( LireDossier lireDossier )
	{
		this.lectureDossier = lireDossier;
	}

	/**
	 * Définit le lecteur d'héritage et implémentation.
	 *
	 * @param lireheritImplement le nouveau lecteur
	 */
	public void setLireHeritImplement( LireHeritImple lireheritImplement )
	{
		this.lireHeritImplements = lireheritImplement;
	}

	/**
	 * Traite les records Java.
	 *
	 * @param tabMots tableau de mots du record
	 */
	public void traiterRecord( String[] tabMots )
	{
		String[] tabAttributs;
		String[] tabTemporaireGet;
		String[] tabTemporaireSet;
		String[] tabTemporaireToString    ;
		String[] tabTemporaireConstructeur;

		tabAttributs = new String[tabMots.length - 3];

		// Création de tous les attributs en final static
		for ( int i = 3; i < tabMots.length; i += 2 )
		{
			tabAttributs[0] = "static";
			tabAttributs[1] = "final";
			tabAttributs[2] = tabMots[i];
			tabAttributs[3] = tabMots[i + 1];

			this.lireAttribut.lireAttribut( tabAttributs );

			// Création du getter pour le record
			tabTemporaireGet    = new String[3];
			tabTemporaireGet[0] = "public";
			tabTemporaireGet[1] = tabAttributs[2];
			tabTemporaireGet[2] = "get" + tabAttributs[3];
			this.lireMethode.lireMethode( tabTemporaireGet );

			// Création du setter pour le record
			tabTemporaireSet    = new String[5];
			tabTemporaireSet[0] = "public";
			tabTemporaireSet[1] = tabAttributs[2];
			tabTemporaireSet[2] = "set" + tabAttributs[3];
			tabTemporaireSet[3] = tabAttributs[2];
			tabTemporaireSet[4] = tabAttributs[2].substring( 0, 3 );
			this.lireMethode.lireMethode( tabTemporaireSet );
		}

		// Création du toString pour le record
		tabTemporaireToString    = new String[3];
		tabTemporaireToString[0] = "public";
		tabTemporaireToString[1] = "String";
		tabTemporaireToString[2] = "toString";
		this.lireMethode.lireMethode( tabTemporaireToString );

		// Création du constructeur
		tabTemporaireConstructeur    = new String[tabMots.length];
		tabTemporaireConstructeur[0] = "public";
		tabTemporaireConstructeur[1] = this.nomClasse;

		for ( int i = 2; i < tabAttributs.length + 2; i++ )
		{
			tabTemporaireConstructeur[i] = tabAttributs[i - 2];
		}

		this.lireMethode.lireMethode( tabTemporaireConstructeur );
	}

	/**
	 * Calcule la taille complète nécessaire pour afficher la classe.
	 *
	 * @return hauteur totale en pixels
	 */
	public int calculTailleComplet()
	{
		int margeVerticalNom;
		int margeVerticalAttributs;
		int margeVerticalMethodes;
		int hauteurLigneAttribut;
		int hauteurLigneMethode;
		int hauteurTotale;

		margeVerticalNom       = 40;
		margeVerticalAttributs = 20;
		margeVerticalMethodes  = 20;
		hauteurLigneAttribut   = 18;
		hauteurLigneMethode    = 18;

		hauteurTotale = margeVerticalNom +
		                this.getListeAttributs().size() * hauteurLigneAttribut +
		                margeVerticalAttributs +
		                this.getListeMethodes().size() * hauteurLigneMethode +
		                margeVerticalMethodes;

		return hauteurTotale;
	}

	/**
	 * Calcule la taille adaptée pour l'affichage de la classe.
	 *
	 * @return hauteur calculée en pixels
	 */
	public int calculTaille()
	{
		int margeVerticalNom;
		int margeVerticalAttributs;
		int margeVerticalMethodes;
		int hauteurLigneAttribut;
		int hauteurLigneMethode;
		int hauteurTotale;

		margeVerticalNom       = 40;
		margeVerticalAttributs = 20;
		margeVerticalMethodes  = 20;
		hauteurLigneAttribut   = 18;
		hauteurLigneMethode    = 18;

		// Ajustement des marges selon le nombre d'attributs et méthodes
		margeVerticalNom       += this.getListeAttributs().size() * hauteurLigneAttribut;
		margeVerticalAttributs += this.getListeMethodes().size() * hauteurLigneMethode;

		// Réduction si dépassement du nombre maximum
		if ( this.getListeAttributs().size() > 3 )
		{
			margeVerticalNom = 40 + 3 * hauteurLigneAttribut;
		}

		if ( this.getListeMethodes().size() > 3 )
		{
			margeVerticalAttributs = 40 + 3 * hauteurLigneMethode;
		}

		hauteurTotale = margeVerticalNom + margeVerticalAttributs + margeVerticalMethodes;

		return hauteurTotale;
	}

	/**
	 * Calcule la largeur maximale pour adapter la taille des blocs.
	 *
	 * @return largeur maximale en pixels
	 */
	public int calculLargeurMax()
	{
		String    stereotype;
		Attribut  a;
		Methode   m;

		this.largeurMax = 100;

		if ( !this.motCle.equals( "class" ) )
		{
			stereotype = "<< " + this.motCle + " >>";
			this.largeurMax = Math.max( this.largeurMax, stereotype.length() * 10 );
		}

		this.largeurMax = Math.max( this.largeurMax, this.nomClasse.length() * 10 );

		for ( int i = 0; i < this.getListeAttributs().size(); i++ )
		{
			a = this.getListeAttributs().get( i );
			this.largeurMax = Math.max( this.largeurMax,
			                            this.lectureDossier.getVueAttributs( a ).length() * 7 - 20 );
		}

		for ( int i = 0; i < this.getListeMethodes().size(); i++ )
		{
			m = this.getListeMethodes().get( i );
			this.largeurMax = Math.max( this.largeurMax,
			                            this.lectureDossier.getVueMethode( m ).length() * 7 - 20 );
		}

		return this.largeurMax;
	}

	/*------------------------- Méthodes privées --------------------------*/

	/**
	 * Lit le fichier passé en paramètre.
	 *
	 * @param fileName le nom du fichier à lire
	 */
	private void lireFichier( String fileName )
	{
		Scanner  sc;
		String   ligne;
		String[] tabMots;

		try
		{
			sc = new Scanner( new FileInputStream( fileName ), "UTF8" );

			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();
				ligne = ligne.trim();

				tabMots = this.decomposerLigne.decomposerLigne( ligne );

				if ( !ligne.startsWith( "import" ) && !ligne.isBlank() &&
				     ( ligne.startsWith( "private" ) ||
				       ligne.startsWith( "public" ) ||
				       ligne.startsWith( "protected" ) ) )
				{
					if ( ligne.contains( "implements" ) || ligne.contains( "extends" ) )
					{
						this.lireHeritImplements.lireHeritImple( tabMots );
					}

					// Traitement des records
					if ( ligne.contains( "record" ) )
					{
						this.nomClasse = tabMots[2];
						this.motCle    = tabMots[1];
						this.traiterRecord( tabMots );
					}

					if ( this.estLaPremiereLigne( tabMots[1] ) )
					{
						this.motCle = tabMots[1];

						if ( this.motCle.equals( "abstract" ) )
						{
							this.nomClasse = tabMots[3];
						}
						else
						{
							this.nomClasse = tabMots[2];
						}
					}
					else
					{
						if ( ligne.endsWith( ";" ) )
						{
							this.lireAttribut.lireAttribut( tabMots );
						}
						else
						{
							this.lireMethode.lireMethode( tabMots );
						}
					}
				}
			}

			sc.close();

			// Post-traitement : vérifier les getters pour chaque attribut
			this.lireAttribut.verifierGetters( this.lireMethode.getListeMethodes() );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Vérifie si le mot est un mot clé de déclaration de classe.
	 *
	 * @param mot le mot à vérifier
	 * @return vrai si c'est un mot clé
	 */
	private boolean estLaPremiereLigne( String mot )
	{
		String motCle;

		for ( int i = 0; i < this.TAB_MOTCLE.length; i++ )
		{
			motCle = this.TAB_MOTCLE[i];

			if ( mot.equals( motCle ) )
			{
				return true;
			}
		}

		return false;
	}
}