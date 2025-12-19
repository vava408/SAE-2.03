package src.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;

/*------------------------------------------------------------------------*/
/*- Classe LireAttribut : Analyse et extrait les attributs d'une classe  */
/*- Auteurs : Groupe 6                                                    */
/*- Date de création : 08/12/2025 15:45                                   */
/*------------------------------------------------------------------------*/

/**
 * Lit et analyse les attributs d'une classe Java.
 *
 * Extrait les informations sur les attributs ( visibilité, type, nom,
 * modificateurs ) et détermine automatiquement les propriétés UML
 * ( requête, addOnly ) selon les méthodes et constructeurs présents.
 */
public class LireAttribut implements Serializable
{
	private LireFichier         lireFichier   ;
	private ArrayList<Attribut> listeAttributs;
	private int                 compteurId    ;

	/*------------------------- Méthodes publiques ------------------------*/

	/**
	 * Construit un lecteur d'attributs à partir d'un lecteur de fichier.
	 *
	 * @param lireFichier le lecteur de fichier source
	 */
	public LireAttribut( LireFichier lireFichier )
	{
		this.lireFichier    = lireFichier      ;
		this.listeAttributs = new ArrayList<>();
		this.compteurId     = 0                ;
	}

	/**
	 * Construit un lecteur d'attributs à partir d'une liste existante.
	 *
	 * @param lstAttribut liste d'attributs existante
	 */
	public LireAttribut( ArrayList<Attribut> lstAttribut )
	{
		this.listeAttributs = lstAttribut;
		this.compteurId     = 0;
	}

	/**
	 * Retourne la liste des attributs lus.
	 *
	 * @return liste des attributs
	 */
	public ArrayList<Attribut> getListeAttributs()
	{
		return this.listeAttributs;
	}

	/**
	 * Vérifie pour chaque attribut si un getter existe sans setter.
	 *
	 * @param listeMethodes liste des méthodes de la classe
	 */
	public void verifierGetters( ArrayList<Methode> listeMethodes )
	{
		Methode               m;
		Parametre             param;
		Attribut              attribut;
		Methode               methode, constructeur;

		ArrayList<Methode>    listeConstructeurs      ;
		ArrayList<Methode>    autresMethodes          ;
		String                nomClasse , nomAttribut ;
		String                getterName, setterName  ;
		boolean               hasGetter , hasSetter   ;
		boolean               initializedInConstructor;


		// Récupération du nom de la classe pour identifier les constructeurs
		nomClasse = this.lireFichier.getNomClasse();

		// Listes pour séparer constructeurs et autres méthodes
		listeConstructeurs = new ArrayList<>();
		autresMethodes     = new ArrayList<>();

		// Séparation des constructeurs et des autres méthodes
		for ( int i = 0; i < listeMethodes.size(); i++ )
		{
			m = listeMethodes.get( i );

			if ( m.getNom().equals( nomClasse ) )
			{
				listeConstructeurs.add( m );
			}
			else
			{
				autresMethodes.add( m );
			}
		}

		// Parcours de tous les attributs
		for ( int i = 0; i < this.listeAttributs.size(); i++ )
		{
			attribut     = this.listeAttributs.get( i );
			nomAttribut  = attribut.getNom();

			// On saute si le nom est null ou vide
			if ( nomAttribut == null || nomAttribut.isEmpty() )
			{
				continue;
			}

			// Construction des noms de getter et setter
			getterName = "get" + nomAttribut.substring( 0, 1 ).toUpperCase() +
			             nomAttribut.substring( 1 );
			setterName = "set" + nomAttribut.substring( 0, 1 ).toUpperCase() +
			             nomAttribut.substring( 1 );

			hasGetter                = false;
			hasSetter                = false;
			initializedInConstructor = false;

			// Vérification de la présence d'un getter et d'un setter
			for ( int j = 0; j < autresMethodes.size(); j++ )
			{
				methode = autresMethodes.get( j );

				if ( methode.getNom().equals( getterName ) &&
				     methode.getParametre().isEmpty() )
				{
					hasGetter = true;
				}

				if ( methode.getNom().equals( setterName ) &&
				     methode.getParametre().size() == 1 )
				{
					hasSetter = true;
				}
			}

			// Vérification si l'attribut est initialisé dans un constructeur
			for ( int j = 0; j < listeConstructeurs.size(); j++ )
			{
				constructeur = listeConstructeurs.get( j );

				for ( int k = 0; k < constructeur.getParametre().size(); k++ )
				{
					param = constructeur.getParametre().get( k );

					// Comparaison du nom du paramètre avec le nom de l'attribut
					if ( param.getNom().equals( nomAttribut ) )
					{
						initializedInConstructor = true;
						break;
					}
				}

				if ( initializedInConstructor )
				{
					break;
				}
			}

			// Marquer l'attribut comme "requete" si conditions remplies
			if ( hasGetter && !hasSetter && !initializedInConstructor )
			{
				attribut.setRequete( true );
			}
		}
	}

	/**
	 * Lit un attribut à partir des mots extraits d'une ligne.
	 *
	 * @param mots tableau de mots représentant une déclaration d'attribut
	 */
	public void lireAttribut( String[] mots )
	{
		Attribut a;

		String[] collectionsAddOnly;
		String   visibilite        ;
		String   type, nom         ;
		String   m, s, t, coll     ;

		boolean  isStatic , isFinal  ;
		boolean  isAddOnly, isRequete;

		int      cpt;

		collectionsAddOnly = new String[] { "arraylist<", "list<", "set<",
		                                    "hashset<", "linkedlist<", "treeset<" };

		visibilite = "default";
		type       = "";
		nom        = "";
		isStatic   = false;
		isFinal    = false;
		isAddOnly  = false;
		isRequete  = false;

		// Retrait du point-virgule
		for ( cpt = 0; cpt < mots.length; cpt++ )
		{
			mots[cpt] = mots[cpt].replace( ";", "" );
		}

		// Analyse des mots pour identifier les modificateurs
		for ( int i = 0; i < mots.length; i++ )
		{
			m = mots[i];

			// Parcours du tableau des visibilités
			for ( int j = 0; j < this.lireFichier.TAB_VISIBILITE.length; j++ )
			{
				s = this.lireFichier.TAB_VISIBILITE[j];

				if ( s.contains( m ) )
				{
					visibilite = m;
					continue;
				}
			}

			// Vérification pour static
			if ( m.equals( "static" ) )
			{
				isStatic = true;
				continue;
			}

			// Vérification pour final
			if ( m.equals( "final" ) )
			{
				isFinal = true;
				continue;
			}

			// Vérification pour requête
			if ( m.contains( "requete" ) || m.contains( "requête" ) )
			{
				isRequete = true;
				continue;
			}
		}

		// Récupération du type et du nom
		if ( mots.length >= 2 )
		{
			nom  = mots[mots.length - 1];
			type = mots[mots.length - 2];
		}
		else
		{
			System.out.println( "Impossible de lire type/nom dans : " +
			                    Arrays.toString( mots ) );
			return;
		}

		// Vérification si c'est une collection add-only
		t = type.trim().toLowerCase();

		for ( int i = 0; i < collectionsAddOnly.length; i++ )
		{
			coll = collectionsAddOnly[i];

			if ( t.contains( coll ) )
			{
				isAddOnly = true;
				break;
			}
		}

		// Création de l'objet attribut
		a = new Attribut( this.compteurId++, nom, type, visibilite,
		                  isStatic, isFinal, isAddOnly, false );

		// Ajout à la liste
		this.listeAttributs.add( a );
	}
}