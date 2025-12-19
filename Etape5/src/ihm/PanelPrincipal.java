package src.ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLayeredPane;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;
import src.metier.LireFichier;

/*--------------------------------------------------------*/
/* PanelPrincipal.java                                    */
/* Panneau principal pour l'affichage des diagrammes UML  */
/* Projet : Générateur de diagrammes UML                  */
/* Auteurs : Groupe 6                                     */
/* Date de création : 18/12/2025 14:30                    */
/*--------------------------------------------------------*/

/**
 * Panneau principal gérant l'affichage des diagrammes UML.
 * Gère l'affichage des blocs représentant les classes et des flèches
 * représentant les associations, héritages et implémentations.
 * Permet le positionnement, la mise à jour et l'export des diagrammes.
 * 
 * @author Groupe 6
 * @version 1.0
 * @since 18/12/2025
 */
public class PanelPrincipal extends JLayeredPane 
{
	private static final int MARGE_GAUCHE = 100;
	private static final int MARGE_HAUT   = 100;

	private FrameUML                      frameUML;
	private HashMap<Bloc, String>         hMBlocs;
	private HashMap<Fleche, Association>  hMFleches;
	private CreerImage                    creerImage;

	/*--------------------------------------------------------*/
	/*                     CONSTRUCTEUR                        */
	/*--------------------------------------------------------*/

	/**
	 * Constructeur du panneau principal.
	 * Initialise le panneau avec la frame UML parent et configure l'apparence.
	 * 
	 * @param frameUML La frame principale contenant les données UML
	 */
	public PanelPrincipal( FrameUML frameUML ) 
	{
		this.frameUML   = frameUML;
		this.creerImage = new CreerImage();
		this.hMBlocs    = new HashMap<>();
		this.hMFleches  = new HashMap<>();

		this.setBackground( new Color( 245, 245, 245 ) );
		this.setOpaque( true );
	}
	
	/*--------------------------------------------------------*/
	/*                      ACCESSEURS                         */
	/*--------------------------------------------------------*/

	/**
	 * Retourne le fichier correspondant au bloc.
	 * 
	 * @param b Le bloc dont on cherche le fichier
	 * @return Le LireFichier correspondant ou null si non trouvé
	 */
	public LireFichier getLireFichier( Bloc b )
	{
		for ( LireFichier lF : this.frameUML.getListeFichiers() )
		{
			if ( lF.getNomClasse().equals( this.hMBlocs.get( b ) ) )
			{
				return lF;
			}
		}

		return null;
	}

	/**
	 * Retourne la liste des attributs d'un bloc.
	 * 
	 * @param b Le bloc dont on veut les attributs
	 * @return La liste des attributs ou null si non trouvé
	 */
	public ArrayList<Attribut> getListeAttributs( Bloc b ) 
	{
		for ( LireFichier lF : this.frameUML.getListeFichiers() )
		{
			if ( lF.getNomClasse().equals( b.getName() ) )
			{
				return lF.getListeAttributs();
			}
		}

		return null;
	}

	/**
	 * Retourne la liste des méthodes d'un bloc.
	 * 
	 * @param b Le bloc dont on veut les méthodes
	 * @return La liste des méthodes ou null si non trouvé
	 */
	public ArrayList<Methode> getListeMethodes( Bloc b ) 
	{ 
		for ( LireFichier lF : this.frameUML.getListeFichiers() )
		{
			if ( lF.getNomClasse().equals( b.getName() ) )
			{
				return lF.getListeMethodes();
			}
		}

		return null;
	}

	/**
	 * Retourne le mot-clé du bloc (class, interface, abstract).
	 * 
	 * @param b Le bloc dont on veut le mot-clé
	 * @return Le mot-clé ou null si non trouvé
	 */
	public String getMotCle( Bloc b ) 
	{
		for ( LireFichier lF : this.frameUML.getListeFichiers() )
		{
			if ( lF.getNomClasse().equals( b.getName() ) )
			{
				return lF.getMotCle();
			}
		}

		return null;
	}

	/**
	 * Retourne le nom de la classe associée au bloc.
	 * 
	 * @param b Le bloc dont on veut le nom
	 * @return Le nom de la classe
	 */
	public String getNomClasse( Bloc b ) 
	{ 
		return this.hMBlocs.get( b );
	}
	
	/**
	 * Retourne le bloc correspondant au nom de classe donné.
	 * 
	 * @param nomClasse Le nom de la classe recherchée
	 * @return Le bloc correspondant ou null si non trouvé
	 */
	public Bloc getBloc( String nomClasse )
	{
		for ( Bloc b : this.hMBlocs.keySet() )
		{
			if ( this.hMBlocs.get( b ).equals( nomClasse ) )
			{
				return b;
			}
		}
		
		return null;
	}

	/**
	 * Retourne l'association liée à une flèche.
	 * 
	 * @param f La flèche dont on veut l'association
	 * @return L'association correspondante
	 */
	public Association getAssociation( Fleche f )
	{
		return this.hMFleches.get( f );
	}

	/**
	 * Retourne le nom de la classe parente (héritage).
	 * 
	 * @param b Le bloc dont on cherche le parent
	 * @return Le nom de la classe parente ou null si pas d'héritage
	 */
	public String getHerit( Bloc b )
	{
		for ( LireFichier lF : this.frameUML.getListeFichiers() ) 
		{
			if ( lF.getMapHerit().containsKey( this.getNomClasse( b ) ) )
			{
				return lF.getMapHerit().get( this.getNomClasse( b ) );
			}
		}
		
		return null; /* Pas d'héritage */
	}

	/**
	 * Retourne la liste des interfaces implémentées par le bloc.
	 * 
	 * @param b Le bloc dont on veut les interfaces
	 * @return La liste des noms d'interfaces ou null si aucune
	 */
	public ArrayList<String> getImple( Bloc b )
	{
		for ( LireFichier lF : this.frameUML.getListeFichiers() ) 
		{
			if ( lF.getMapImple().containsKey( this.getNomClasse( b ) ) )
			{
				return lF.getMapImple().get( this.getNomClasse( b ) );
			}
		}
		
		return null; /* Pas d'implémentation */
	}

	/**
	 * Retourne la taille (hauteur) d'un bloc.
	 * 
	 * @param b Le bloc dont on veut la taille
	 * @param complet true pour taille complète, false pour taille réduite
	 * @return La hauteur du bloc en pixels
	 */
	public int getTaille( Bloc b, boolean complet )
	{
		if ( this.getLireFichier( b ) == null ) { return 0; }

		if ( complet )
		{
			return this.getLireFichier( b ).calculTailleComplet();
		}
		else
		{
			return this.getLireFichier( b ).calculTaille();
		}
	}

	/**
	 * Retourne la largeur maximale d'un bloc.
	 * 
	 * @param b Le bloc dont on veut la largeur
	 * @return La largeur maximale en pixels
	 */
	public int getLargeurMax( Bloc b )
	{
		return this.getLireFichier( b ).calculLargeurMax(); 
	}

	/**
	 * Retourne la map des attributs d'associations.
	 * 
	 * @return HashMap associant les associations à leurs attributs
	 */
	public HashMap<Association, ArrayList<String>> gethMAttributsAssociations()
	{
		return this.frameUML.gethMAttributsAssociations();
	}
	
	/*--------------------------------------------------------*/
	/*                    MODIFICATEURS                        */
	/*--------------------------------------------------------*/

	/**
	 * Modifie la position d'un bloc dans le diagramme.
	 * 
	 * @param b Le bloc à déplacer
	 * @param x Coordonnée X de la nouvelle position
	 * @param y Coordonnée Y de la nouvelle position
	 */
	public void setPosition( Bloc b, int x, int y )
	{
		/* Ajuster selon les marges */
		this.frameUML.setPosition( this.getLireFichier( b ), 
		                           x - PanelPrincipal.MARGE_GAUCHE, 
		                           y - PanelPrincipal.MARGE_HAUT );
		
		this.maj(); /* Mettre à jour les flèches */
	}

	/*--------------------------------------------------------*/
	/*                  AUTRES MÉTHODES                        */
	/*--------------------------------------------------------*/

	/**
	 * Vérifie si le nom du bloc existe dans le répertoire.
	 * 
	 * @param b Le bloc à vérifier
	 * @return true si le nom existe, false sinon
	 */
	public boolean nomEstDansRepertoire( Bloc b )
	{
		return this.frameUML.nomEstDansRepertoire( this.hMBlocs.get( b ) );
	}
	
	/**
	 * Instancie tous les éléments du panneau.
	 * Crée les blocs, les flèches d'association, d'héritage et d'implémentation.
	 */
	public void instancierPanel() 
	{
		Bloc                bloc;
		Fleche              fleche;
		String              nomClasse;
		String              heritage;
		ArrayList<String>   implement;

		this.removeAll();
		this.hMBlocs.clear();
		this.hMFleches.clear();

		/*----- Ajouter les blocs -----*/
		for ( LireFichier lF : this.frameUML.getListeFichiers() ) 
		{
			bloc = new Bloc( this, lF.getNomClasse() );

			this.hMBlocs.put( bloc, lF.getNomClasse() );

			/* Positionner le bloc avec les marges */
			bloc.setBounds( lF.getPosX() + PanelPrincipal.MARGE_GAUCHE, 
			                lF.getPosY() + PanelPrincipal.MARGE_HAUT, 
			                lF.getLargeur(), 
			                lF.getHauteur() );

			this.add( bloc, JLayeredPane.DEFAULT_LAYER );
			bloc.maj();
		}

		/*----- Ajouter les flèches d'association -----*/
		for ( Association assoc : this.frameUML.getListeAssociation() )
		{
			fleche = new Fleche( this, 
			                     assoc.getNomClasseA(), 
			                     assoc.getNomClasseB(),
			                     assoc.getMultiplicityA(), 
			                     assoc.getMultiplicityB() );

			this.hMFleches.put( fleche, assoc );

			fleche.setBounds( 0, 0, this.getWidth(), this.getHeight() );

			this.add( fleche, JLayeredPane.PALETTE_LAYER ); /* Toujours au-dessus */
			fleche.maj();
		}

		/*----- Ajouter les blocs d'héritage non présents -----*/
		for ( LireFichier lF : this.frameUML.getListeFichiers() )
		{
			for ( String nomHerit : lF.getMapHerit().keySet() )
			{
				if ( !this.frameUML.nomEstDansRepertoire( nomHerit ) )
				{
					bloc = new Bloc( this, nomHerit );
					this.hMBlocs.put( bloc, nomHerit );
					this.add( bloc, JLayeredPane.DEFAULT_LAYER );
				}
			}

			/*----- Ajouter les blocs d'implémentation non présents -----*/
			for ( String nomImpl1 : lF.getMapImple().keySet() )
			{
				for ( String nomImpl2 : lF.getMapImple().get( nomImpl1 ) )
				{
					if ( !this.frameUML.nomEstDansRepertoire( nomImpl2 ) )
					{
						bloc = new Bloc( this, nomImpl2 );
						this.hMBlocs.put( bloc, nomImpl2 );
						this.add( bloc, JLayeredPane.DEFAULT_LAYER );
						bloc.maj();
					}
				}
			}
		}

		/*----- Ajouter les flèches d'héritage et d'implémentation -----*/
		for ( Bloc b : this.hMBlocs.keySet() )
		{
			nomClasse = this.getNomClasse( b );
			heritage  = this.getHerit( b );
			implement = this.getImple( b );

			/* Flèche d'héritage */
			if ( heritage != null )
			{
				this.ajouterFleche( nomClasse, heritage );
			}
			
			/* Flèches d'implémentation */
			if ( implement != null )
			{
				for ( String nomImplement : implement )
				{
					this.ajouterFleche( nomClasse, nomImplement );
				}
			}
		}

		this.placerBlocs();
		this.revalidate();
		this.repaint();
	}

	/**
	 * Exporte le diagramme en image.
	 * 
	 * @param path Chemin de destination du fichier image
	 */
	public void exportToImage( String path )
	{
		BufferedImage img;
		Graphics2D    g2d;

		img = new BufferedImage( this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB );
		g2d = img.createGraphics();
		
		this.paint( g2d );
		g2d.dispose();

		System.out.println( "Export de l'image vers : " + path );
		System.out.println( this.creerImage.exportToImage( img, path ) );
	}

	/**
	 * Affiche un attribut au format UML.
	 * 
	 * @param a L'attribut à afficher
	 * @return La chaîne formatée de l'attribut
	 */
	public String afficherAttribut( Attribut a ) 
	{
		return this.frameUML.afficherAttribut( a );
	}
	
	/**
	 * Affiche une méthode au format UML.
	 * 
	 * @param m La méthode à afficher
	 * @param complet true pour affichage complet, false pour affichage simple
	 * @return La chaîne formatée de la méthode
	 */
	public String afficherMethode( Methode m, boolean complet )
	{
		return this.frameUML.afficherMethode( m, complet );
	}

	/**
	 * Met à jour tous les éléments du panneau.
	 * Recalcule les tailles et repositionne les éléments.
	 */
	public void maj() 
	{
		/* Mettre à jour les blocs et les flèches */
		for ( Bloc   b : this.hMBlocs.keySet()   ) { b.maj(); }
		for ( Fleche f : this.hMFleches.keySet() ) { f.maj(); }

		this.recalculerTaille();
		this.revalidate();
		this.repaint();
	}

	/*--------------------------------------------------------*/
	/*                   MÉTHODES PRIVÉES                      */
	/*--------------------------------------------------------*/

	/**
	 * Ajoute une flèche entre deux blocs (héritage ou implémentation).
	 * 
	 * @param source Nom de la classe source
	 * @param cible Nom de la classe cible
	 */
	private void ajouterFleche( String source, String cible )
	{
		Fleche fleche;

		fleche = new Fleche( this, source, cible, "", "" ); /* Pas de multiplicités */

		this.hMFleches.put( fleche, null );
		this.add( fleche, JLayeredPane.PALETTE_LAYER );
		fleche.maj();
	}
	
	/**
	 * Place les blocs en grille sur le panneau.
	 * Dispose les blocs en lignes avec des marges entre eux.
	 */
	private void placerBlocs() 
	{
		int margeHorizontale;
		int margeVerticale;
		int x;
		int y;
		int ligneMax;
		int compteur;

		margeHorizontale = 50;
		margeVerticale   = 50;
		x                = margeHorizontale;
		y                = margeVerticale;
		ligneMax         = 4; /* Nombre de blocs par ligne */
		compteur         = 0;

		for ( Bloc b : this.hMBlocs.keySet() ) 
		{
			b.setBounds( x, y, b.getWidth(), b.getHeight() );

			x += b.getWidth() + margeHorizontale;
			compteur++;

			/* Passer à la ligne suivante */
			if ( compteur >= ligneMax )
			{
				compteur = 0;
				x        = margeHorizontale;
				y       += 300; /* Hauteur approximative + marge */
			}
			
			System.out.println( "Placement du bloc " + this.hMBlocs.get( b ) + 
			                    " en (" + b.getX() + ", " + b.getY() + ")" );
		}
	}
	
	/**
	 * Recalcule la taille du panneau selon les positions des blocs.
	 * Ajuste les dimensions pour contenir tous les éléments.
	 */
	private void recalculerTaille()
	{
		int maxX;
		int maxY;

		maxX = 0;
		maxY = 0;

		/* Trouver les dimensions maximales */
		for ( Bloc b : this.hMBlocs.keySet() )
		{
			maxX = Math.max( maxX, b.getX() + b.getWidth()  );
			maxY = Math.max( maxY, b.getY() + b.getHeight() );
		}

		/* Ajouter une marge de confort */
		maxX += 100;
		maxY += 100;

		this.setPreferredSize( new Dimension( maxX, maxY ) );
	}
}