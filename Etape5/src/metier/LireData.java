package src.metier;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;
import src.membres.Association;
import src.Controleur;

/*
* Classe permettant de lire et parser un fichier de données UML
* Reconstruit les classes, attributs, méthodes et associations depuis un fichier texte
* 
* Exercice    : Génération de diagrammes UML
* Auteurs     : Groupe 6
* Date/Heure  : 19/12/2024 15:00
*/

public class LireData implements Serializable
{
	public final String[] TAB_VISIBILITE = { "public", "private", "protected" };
	public final String[] TAB_MOTCLE     = { "class", "interface", "enum", "record", "abstract" };
	public final String[] TAB_MODIFIEURS = { "static", "final", "abstract", 
											"native", "strictfp", "synchronized" };

	private transient DecomposerLigne decomposerLigne;
	private Controleur                ctrl;

	/**
	 * Constructeur de la classe LireData
	 * Initialise le décomposeur de lignes et lance la lecture du fichier
	 */
	public LireData( String chemin, Controleur ctrl )
	{
		this.decomposerLigne = new DecomposerLigne();
		this.ctrl            = ctrl;

		this.lireFichierData( chemin );
	}

	/**
	 * Lit et parse le fichier de données UML
	 * Extrait les classes, attributs, méthodes, associations et relations d'héritage
	 */
	public void lireFichierData( String chemin )
	{
		Scanner                sc;
		String                 ligne;
		String[]               tabMots;
		String                 nomClasse;
		String                 typeClasse;
		int                    posX;
		int                    posY;
		int                    nbAttribut;
		String                 nomAttribut;
		String                 typeAttribut;
		String                 visibiliteAttribut;
		boolean                estStatic;
		boolean                estFinal;
		String                 nomMethode;
		String                 visibiliteMethode;
		String                 typeRetour;
		String                 typeParam;
		String                 nomParam;
		int                    nbParam;
		boolean                estPremiere;
		ArrayList<Attribut>    lstAttribut;
		ArrayList<Methode>     lstMethode;
		ArrayList<Parametre>   lstParametre;
		ArrayList<Association> lstAssociation;
		ArrayList<LireFichier> lstLireFichiers;
		int                    cptTemporaire;
		boolean                ligneEstTraite;
		LireAttribut           lireAttribut;
		LireMethode            lireMethode;
		LireFichier            lF;
		LireDossier            lireDossier;

		nomClasse      = "";
		typeClasse     = "";
		posX           = 0;
		posY           = 0;
		estStatic      = false;
		estFinal       = false;
		estPremiere    = false;
		ligneEstTraite = false;

		lstAttribut     = new ArrayList<Attribut>();
		lstMethode      = new ArrayList<Methode>();
		lstParametre    = new ArrayList<Parametre>();
		lstAssociation  = new ArrayList<Association>();
		lstLireFichiers = new ArrayList<LireFichier>();

		try
		{
			sc = new Scanner( new FileInputStream( chemin ), "UTF8" );

			/* Parcours de tout le fichier de sauvegarde */
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();
				ligne = ligne.trim();

				/* Récupération des données de la classe principale */
				if ( !ligne.startsWith( "-" ) && !ligne.startsWith( "+" ) && !ligne.isBlank()
					&& !ligne.contains( "Association" ) && !ligne.contains( "=" ) 
					&& !ligne.contains( "étend" ) && !ligne.contains( "implémente" ) )
				{
					tabMots = this.decomposerLigne.decomposerLigne( ligne );
					
					/* Identification du type de classe */
					for ( String s : this.TAB_MOTCLE )
					{
						if ( s.equals( tabMots[0] ) )
							typeClasse = s;
					}

					nomClasse = tabMots[1];
					posX      = Integer.parseInt( tabMots[2] );
					posY      = Integer.parseInt( tabMots[3] );

					/* Réinitialisation des listes pour la prochaine classe */
					lstMethode  = new ArrayList<Methode>();
					lstAttribut = new ArrayList<Attribut>();

					lstMethode .clear();
					lstAttribut.clear();

					ligneEstTraite = false;

					continue;
				}

				/* Traitement des attributs de la classe */
				if ( ligne.startsWith( "+" ) )
				{
					nbAttribut     = 0;
					ligneEstTraite = true;

					while ( sc.hasNextLine() )
					{
						estStatic = false;
						estFinal  = false;

						ligne = sc.nextLine();

						if ( ligne.isBlank() )
							continue;

						/* Fin de la section attributs */
						if ( ligne.startsWith( "-" ) || ligne.startsWith( "=" ) )
						{
							estPremiere = true;
							break;
						}

						tabMots = this.decomposerLigne.decomposerLigne( ligne );

						/* Cas d'un attribut sans type ni visibilité */
						if ( tabMots.length == 1 )
						{
							nomAttribut        = tabMots[0];
							typeAttribut       = null;
							visibiliteAttribut = "protected";

							continue;
						}

						visibiliteAttribut = tabMots[0];
						cptTemporaire      = 1;

						/* Vérification des modificateurs static et final */
						for ( int i = 1; i < tabMots.length; i++ )
						{
							if ( tabMots[i].equals( "final" ) )
							{
								estFinal = true;
								cptTemporaire++;
							}

							if ( tabMots[i].equals( "static" ) )
							{
								estStatic = true;
								cptTemporaire++;
							}
						}

						typeAttribut = tabMots[cptTemporaire];
						nomAttribut  = tabMots[cptTemporaire + 1];

						nbAttribut++;

						/* Ajout de l'attribut dans la liste */
						lstAttribut.add( new Attribut( nbAttribut, nomAttribut, typeAttribut, 
													visibiliteAttribut, estStatic, estFinal, 
													false, false ) );
					}
				}

				/* Traitement des méthodes de la classe */
				if ( ligne.startsWith( "-" ) )
				{
					if ( !ligneEstTraite )
						estPremiere = true;

					while ( sc.hasNextLine() )
					{
						if ( !estPremiere )
						{
							ligne = sc.nextLine();
						}
						else
						{
							estPremiere = false;
						}

						estStatic = false;
						estFinal  = false;

						if ( ligne.isBlank() )
							continue;

						/* Fin de la section méthodes */
						if ( ligne.startsWith( "=" ) )
							break;

						nbParam       = 0;
						cptTemporaire = 1;

						tabMots = this.decomposerLigne.decomposerLigne( ligne );

						visibiliteMethode = tabMots[0];

						/* Vérification des modificateurs static et final */
						for ( int i = 1; i < tabMots.length; i++ )
						{
							if ( tabMots[i].equals( "final" ) )
							{
								estFinal = true;
								cptTemporaire++;
							}

							if ( tabMots[i].equals( "static" ) )
							{
								estStatic = true;
								cptTemporaire++;
							}
						}

						/* Vérification si c'est un constructeur */
						if ( tabMots[1].equals( nomClasse ) )
						{
							nomMethode = tabMots[1];
							typeRetour = null;

							for ( String s : tabMots )
								System.out.print( s );

							System.out.println();

							/* Récupération des paramètres du constructeur */
							if ( tabMots.length > 2 )
								for ( int i = 2; i < tabMots.length; i += 2 )
								{
									typeParam = tabMots[i];
									nomParam  = tabMots[i + 1];

									nbParam++;

									lstParametre.add( new Parametre( nbParam, nomParam, typeParam ) );
								}

							lstMethode.add( new Methode( nomMethode, visibiliteMethode, typeRetour, 
														lstParametre, estStatic, estFinal ) );

							lstParametre = new ArrayList<Parametre>();
							lstParametre.clear();

							continue;
						}

						typeRetour = tabMots[1];
						nomMethode = tabMots[2];

						cptTemporaire--;

						/* Création des paramètres de la méthode */
						if ( tabMots.length > 2 )
							for ( int i = 1 + cptTemporaire; i < tabMots.length; i += 2 )
							{
								typeParam = tabMots[i];
								nomParam  = tabMots[i + 1];

								nbParam++;

								lstParametre.add( new Parametre( nbParam, nomParam, typeParam ) );
							}

						/* Ajout de la nouvelle méthode dans la liste */
						lstMethode.add( new Methode( nomMethode, visibiliteMethode, typeRetour, 
													lstParametre, estStatic, estFinal ) );

						lstParametre = new ArrayList<Parametre>();
						lstParametre.clear();
					}
				}

				/* Création d'une nouvelle classe avec ses attributs et méthodes */
				if ( ligne.startsWith( "=" ) && !ligne.contains( "ASSOCIATIONS" ) 
					&& !ligne.contains( "extends" ) && !ligne.contains( "implements" ) )
				{
					lireAttribut = new LireAttribut( lstAttribut );
					lireMethode  = new LireMethode( lstMethode );

					lF = new LireFichier( nomClasse, typeClasse, lireMethode, lireAttribut, 
										posX, posY );
					lstLireFichiers.add( lF );

					lF.setLireHeritImplement( new LireHeritImple( lF ) );
				}

				/* Création et traitement des associations entre classes */
				if ( ligne.contains( "Association" ) )
				{
					while ( sc.hasNextLine() )
					{
						String nomClasseA;
						String nomClasseB;
						String multipliciteA;
						String multipliciteB;
						int    emplacementA;
						int    emplacementB;

						nomClasseA    = "";
						nomClasseB    = "";
						multipliciteA = "";
						multipliciteB = "";
						emplacementA  = 0;
						emplacementB  = 0;

						ligne = sc.nextLine();

						if ( ligne.isBlank() )
							continue;

						/* Fin de la section associations */
						if ( ligne.startsWith( "=" ) )
						{
							estPremiere = true;
							break;
						}

						tabMots = this.decomposerLigne.decomposerLigne( ligne );
						
						/* Identification des classes associées */
						for ( int cpt = 0; cpt < tabMots.length; cpt++ )
						{
							for ( Methode m : lstMethode )
							{
								if ( m.getNom().equals( tabMots[cpt] ) )
								{
									if ( nomClasseA.isBlank() )
									{
										nomClasseA   = m.getNom();
										emplacementA = cpt;
									}
									else
									{
										nomClasseB   = m.getNom();
										emplacementB = cpt;
									}
								}
							}
						}

						lstAssociation.add( new Association( nomClasseA, nomClasseB, 
															multipliciteA, multipliciteB ) );
					}
				}

				/* Création et traitement des relations d'héritage */
				if ( ligne.contains( "extends" ) )
				{
					while ( sc.hasNextLine() )
					{
						LireFichier lF1;
						LireFichier lF2;

						lF1 = null;
						lF2 = null;

						if ( !estPremiere )
							ligne = sc.nextLine();
						else
							estPremiere = false;

						if ( ligne.isBlank() )
							continue;

						/* Fin de la section extends */
						if ( ligne.startsWith( "=" ) )
						{
							estPremiere = true;
							break;
						}

						tabMots = this.decomposerLigne.decomposerLigne( ligne );

						/* Recherche des classes parent et enfant */
						for ( LireFichier lireFichier : lstLireFichiers )
						{
							if ( lireFichier.getNomClasse().equals( tabMots[1] ) )
								lF1 = lireFichier;

							if ( lireFichier.getNomClasse().equals( tabMots[3] ) )
								lF2 = lireFichier;
						}

						lF1.getLireHeritImplement().setHerit( lF1.getNomClasse(), 
															lF2.getNomClasse() );
					}
				}

				/* Création et traitement des implémentations d'interfaces */
				if ( ligne.contains( "implements" ) )
				{
					while ( sc.hasNextLine() )
					{
						LireFichier       lF1;
						LireFichier       lF2;
						ArrayList<String> lstImplement;

						lF1 = null;
						lF2 = null;

						if ( !estPremiere )
							ligne = sc.nextLine();
						else
							estPremiere = false;

						if ( ligne.isBlank() )
							continue;

						/* Fin de la section implements */
						if ( ligne.startsWith( "=" ) )
						{
							estPremiere = true;
							break;
						}

						tabMots = this.decomposerLigne.decomposerLigne( ligne );

						/* Recherche des classes et interfaces */
						for ( LireFichier lireFichier : lstLireFichiers )
						{
							if ( lireFichier.getNomClasse().equals( tabMots[1] ) )
								lF1 = lireFichier;

							if ( lireFichier.getNomClasse().equals( tabMots[3] ) )
								lF2 = lireFichier;
						}

						lstImplement = new ArrayList<String>();
						lstImplement.add( lF2.getNomClasse() );

						lF1.getLireHeritImplement().setImplement( lF1.getNomClasse(), 
																lstImplement );
					}
				}
			}

			/* Création du dossier global et liaison avec le contrôleur */
			lireDossier = new LireDossier( lstLireFichiers, lstAssociation, this.ctrl );

			this.ctrl.setLireDossier( lireDossier );

			/* Liaison bidirectionnelle entre LireDossier et LireFichiers */
			for ( LireFichier lireFichier : lireDossier.getListeFichiers() )
			{
				lireFichier.setLireDossier( lireDossier );
			}

			sc.close();
		}
		catch ( Exception e )
		{ 
			e.printStackTrace(); 
		}
	}
}