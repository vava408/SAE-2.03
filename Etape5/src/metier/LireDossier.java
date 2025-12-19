package src.metier;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import src.Controleur;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;

/*------------------------------------------------------------------------*/
/*- Classe LireDossier : Analyse un dossier et extrait les classes       */
/*- Auteurs : Groupe 6                                                    */
/*- Date de création : 08/12/2025 16:20                                   */
/*------------------------------------------------------------------------*/

/**
 * Lit un dossier contenant des fichiers Java et crée les associations.
 *
 * Analyse tous les fichiers .java d'un répertoire, extrait leurs
 * informations ( classes, attributs, méthodes ) et génère automatiquement
 * les associations UML entre les classes détectées.
 */
public class LireDossier implements Serializable
{
	private transient Controleur                     ctrl;
	private ArrayList<Association>                   lstAssociations;
	private ArrayList<LireFichier>                   lstLireFichiers;
	private HashMap<Association, ArrayList<String>>  hMAttrAsso;

	/*------------------------- Méthodes publiques ------------------------*/

	/**
	 * Construit un lecteur de dossier et analyse le répertoire.
	 *
	 * @param ctrl le contrôleur de l'application
	 * @param cheminDossier le chemin du dossier à analyser
	 */
	public LireDossier( Controleur ctrl, String cheminDossier )
	{
		this.ctrl = ctrl;

		this.lstAssociations = new ArrayList<Association>();
		this.lstLireFichiers = new ArrayList<LireFichier>();
		this.hMAttrAsso      = new HashMap<>();

		this.lireDossier( cheminDossier );

		this.creerAssociation();
	}

	/**
	 * Construit un lecteur de dossier à partir de listes existantes.
	 *
	 * @param lstFichier liste des fichiers lus
	 * @param lstAssociation liste des associations
	 * @param ctrl le contrôleur de l'application
	 */
	public LireDossier( ArrayList<LireFichier> lstFichier,
	                    ArrayList<Association> lstAssociation,
	                    Controleur ctrl )
	{
		this.lstLireFichiers = lstFichier;
		this.lstAssociations = lstAssociation;
		this.ctrl            = ctrl;
	}

	/**
	 * Retourne le nombre de classes lues.
	 *
	 * @return le nombre de classes
	 */
	public int getNbClasses()
	{
		return this.lstLireFichiers.size();
	}

	/**
	 * Retourne la liste des fichiers lus.
	 *
	 * @return liste des fichiers
	 */
	public ArrayList<LireFichier> getListeFichiers()
	{
		return this.lstLireFichiers;
	}

	/**
	 * Retourne la liste des associations créées.
	 *
	 * @return liste des associations
	 */
	public ArrayList<Association> getListeAssociation()
	{
		return this.lstAssociations;
	}

	/**
	 * Retourne la vue formatée d'une méthode.
	 *
	 * @param m la méthode à afficher
	 * @return représentation textuelle de la méthode
	 */
	public String getVueMethode( Methode m )
	{
		return this.ctrl.afficherMethode( m, true );
	}

	/**
	 * Retourne la vue formatée d'un attribut.
	 *
	 * @param a l'attribut à afficher
	 * @return représentation textuelle de l'attribut
	 */
	public String getVueAttributs( Attribut a )
	{
		return this.ctrl.afficherAttribut( a );
	}

	/**
	 * Retourne la map des attributs associés aux associations.
	 *
	 * @return HashMap association vers attributs
	 */
	public HashMap<Association, ArrayList<String>> gethMAttributsAssociations()
	{
		return this.hMAttrAsso;
	}

	/**
	 * Définit la position d'un fichier dans l'interface graphique.
	 *
	 * @param lF le fichier concerné
	 * @param x position horizontale
	 * @param y position verticale
	 */
	public void setPosition( LireFichier lF, int x, int y )
	{
		LireFichier classe;

		for ( int i = 0; i < this.lstLireFichiers.size(); i++ )
		{
			classe = this.lstLireFichiers.get( i );

			if ( classe == lF )
			{
				classe.setPosition( x, y );
			}
		}
	}

	/**
	 * Ajoute une association entre deux classes.
	 *
	 * @param lF le fichier source
	 * @param nomClasseB le nom de la classe destination
	 * @param multipliciteA multiplicité côté A
	 * @param multipliciteB multiplicité côté B
	 */
	public void ajoutAssociation( LireFichier lF, String nomClasseB,
	                              String multipliciteA, String multipliciteB )
	{
		Association a;

		a = new Association( lF.getNomClasse(), nomClasseB,
		                     multipliciteB, multipliciteA );

		this.lstAssociations.add( a );
	}

	/**
	 * Vérifie si un nom de classe existe dans le répertoire.
	 *
	 * @param nomClasse le nom de la classe à rechercher
	 * @return vrai si la classe existe
	 */
	public boolean nomEstDansRepertoire( String nomClasse )
	{
		LireFichier lF;

		for ( int i = 0; i < this.lstLireFichiers.size(); i++ )
		{
			lF = this.lstLireFichiers.get( i );

			if ( lF.getNomClasse().equals( nomClasse ) )
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Affiche toutes les classes et leurs relations.
	 */
	public void afficherClasses()
	{
		Association a;
		LireFichier lF;

		System.out.println( this.ctrl.getVue().afficher() );
		System.out.println();

		for ( int i = 0; i < this.lstAssociations.size(); i++ )
		{
			a = this.lstAssociations.get( i );
			System.out.println( a );
		}

		for ( int i = 0; i < this.lstLireFichiers.size(); i++ )
		{
			lF = this.lstLireFichiers.get( i );
			System.out.println( this.ctrl.getVue().afficherHeritage( lF ) );
			System.out.println( this.ctrl.getVue().afficherInterface( lF ) );
		}
	}

	/**
	 * Réinitialise le lecteur avec un nouveau contrôleur.
	 *
	 * @param ctrl le nouveau contrôleur
	 */
	public void reinitialiser( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.creerAssociation();
	}

	/*------------------------- Méthodes privées --------------------------*/

	/**
	 * Lit tous les fichiers Java d'un dossier.
	 *
	 * @param cheminDossier le chemin du dossier
	 */
	private void lireDossier( String cheminDossier )
	{
		File          dossier, fichier;
		File[]        listeFichiers   ;
		LireFichier   lireFichier     ;

		dossier       = new File( cheminDossier );
		listeFichiers = dossier.listFiles();

		for ( int i = 0; i < listeFichiers.length; i++ )
		{
			fichier = listeFichiers[i];

			if ( fichier.isFile() && fichier.getName().endsWith( ".java" ) )
			{
				lireFichier = new LireFichier( this, fichier.getAbsolutePath() );

				if ( !this.lstLireFichiers.contains( lireFichier ) )
				{
					this.lstLireFichiers.add( lireFichier );
				}
			}
		}
	}

	/**
	 * Crée les associations entre les classes détectées.
	 */
	private void creerAssociation()
	{
		Association a       ;
		LireFichier lF1, lF2;
		Attribut    a1 , a2 ;

		String      multipliciteA, multipliciteB;
		boolean     unidirectionnel             ;
		int         cpt1, cpt2                  ;

		for ( int i = 0; i < this.lstLireFichiers.size(); i++ )
		{
			lF1 = this.lstLireFichiers.get( i );

			for ( cpt1 = 0; cpt1 < lF1.getListeAttributs().size(); cpt1++ )
			{
				a1 = lF1.getListeAttributs().get( cpt1 );

				for ( int j = 0; j < this.lstLireFichiers.size(); j++ )
				{
					lF2 = this.lstLireFichiers.get( j );

					if ( a1.getType().contains( lF2.getNomClasse() ) )
					{
						multipliciteA   = this.calculMultiplicite( a1.getType(),
						                                           lF1.getNomClasse() );
						multipliciteB   = "0..*";
						unidirectionnel = true;

						for ( cpt2 = 0; cpt2 < lF2.getListeAttributs().size(); cpt2++ )
						{
							a2 = lF2.getListeAttributs().get( cpt2 );

							if ( a2.getType().contains( lF1.getNomClasse() ) )
							{
								unidirectionnel = false;

								multipliciteB = this.calculMultiplicite( a2.getType(),
								                                         lF2.getNomClasse() );

								this.ajoutAssociation( lF1, a1.getType(),
								                       multipliciteA, multipliciteB );

								a = this.lstAssociations.get( this.lstAssociations.size() - 1 );

								if ( !this.hMAttrAsso.containsKey( a ) )
								{
									this.hMAttrAsso.put( a, new ArrayList<>() );
								}

								this.hMAttrAsso.get( a ).add( lF2.getListeAttributs()
								                                 .get( cpt2 ).getNom() );

								lF2.getListeAttributs().remove( cpt2 );
								cpt2--;
							}
						}

						if ( unidirectionnel )
						{
							this.ajoutAssociation( lF1, a1.getType(),
							                       multipliciteA, multipliciteB );
						}

						a = this.lstAssociations.get( this.lstAssociations.size() - 1 );

						if ( !this.hMAttrAsso.containsKey( a ) )
						{
							this.hMAttrAsso.put( a, new ArrayList<>() );
						}

						this.hMAttrAsso.get( a ).add( lF1.getListeAttributs()
						                                 .get( cpt1 ).getNom() );

						lF1.getListeAttributs().remove( cpt1 );
						cpt1--;
					}
				}
			}
		}
	}

	/**
	 * Calcule la multiplicité d'une association.
	 *
	 * @param type le type de l'attribut
	 * @param nomClasse le nom de la classe
	 * @return la multiplicité calculée
	 */
	private String calculMultiplicite( String type, String nomClasse )
	{
		String sRet;

		sRet = "1..1";

		if ( type.contains( "<" ) && type.contains( ">" ) || type.contains( "[]" ) )
		{
			sRet = "0..*";
		}

		return sRet;
	}
}