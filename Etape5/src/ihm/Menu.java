package src.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
* Classe représentant la barre de menu de l'application UML
* Gère les actions du menu fichier (ouvrir, sauvegarder, exporter, etc.)
* 
* Exercice    : Génération de diagrammes UML
* Groupe      : [Numéro de groupe]
* Auteurs     : [Noms des auteurs]
* Date/Heure  : 19/12/2024 14:45
*/

public class Menu extends JMenuBar implements ActionListener
{
	private FrameUML             frameUML;
	private JMenu                menuFichier;
	private JMenuItem            itemSauvegarde;
	private JMenuItem            itemOuvrirDossier;
	private JMenuItem            itemOuvrirSauvegarde;
	private JMenuItem            itemQuitter;
	private JMenuItem            itemExporter;
	private JMenuItem            itemRefresh;
	private JMenuItem            itemOuvrirData;
	private Runnable             onSauvegarder;
	private Consumer<String>     onExporter;
	private Runnable             onActualiser;
	private Bloc                 bloc;

	/**
	 * Constructeur de la classe Menu
	 * Initialise la barre de menu avec tous les items et leurs actions
	 */
	public Menu( FrameUML frameUML ) 
	{
		this.frameUML = frameUML;

		/* Création du menu principal */
		this.menuFichier = new JMenu( "Menu" );

		/* Création des items du menu */
		this.itemSauvegarde       = new JMenuItem( "Sauvegarder"          );
		this.itemOuvrirDossier    = new JMenuItem( "Ouvrir dossier"       );
		this.itemOuvrirData       = new JMenuItem( "Ouvrir data"          );
		this.itemOuvrirSauvegarde = new JMenuItem( "Ouvrir la sauvegarde" );
		this.itemExporter         = new JMenuItem( "Exporter"             );
		this.itemRefresh          = new JMenuItem( "Rafraîchir"           );
		this.itemQuitter          = new JMenuItem( "Quitter"              );

		/* Ajout des items dans le menu */
		this.menuFichier.add( this.itemSauvegarde       );
		this.menuFichier.add( this.itemOuvrirDossier    );
		this.menuFichier.add( this.itemOuvrirData       );
		this.menuFichier.add( this.itemOuvrirSauvegarde );
		this.menuFichier.add( this.itemExporter         );
		this.menuFichier.add( this.itemRefresh          );
		this.menuFichier.addSeparator();
		this.menuFichier.add( this.itemQuitter          );

		/* Ajout du menu à la barre */
		this.add( this.menuFichier );

		/* Définition des commandes d'action */
		this.itemOuvrirDossier   .setActionCommand( "ouvrirDossier"    );
		this.itemSauvegarde      .setActionCommand( "sauvegarder"      );
		this.itemOuvrirData      .setActionCommand( "ouvrirData"       );
		this.itemOuvrirSauvegarde.setActionCommand( "ouvrirSauvegarde" );
		this.itemExporter        .setActionCommand( "exporter"         );
		this.itemRefresh         .setActionCommand( "refresh"          );
		this.itemQuitter         .setActionCommand( "quitter"          );

		/* Enregistrement des listeners */
		this.itemOuvrirDossier   .addActionListener( this );
		this.itemSauvegarde      .addActionListener( this );
		this.itemOuvrirData      .addActionListener( this );
		this.itemOuvrirSauvegarde.addActionListener( this );
		this.itemExporter        .addActionListener( this );
		this.itemRefresh         .addActionListener( this );
		this.itemQuitter         .addActionListener( this );
	}

	/**
	 * Retourne l'item de menu Sauvegarder
	 */
	public JMenuItem getItemSauvegarde() { return this.itemSauvegarde; }

	/**
	 * Retourne l'item de menu Ouvrir Dossier
	 */
	public JMenuItem getItemOuvrirDossier() { return this.itemOuvrirDossier; }

	/**
	 * Retourne l'item de menu Ouvrir Data
	 */
	public JMenuItem getItemOuvrirData() { return this.itemOuvrirData; }

	/**
	 * Retourne l'item de menu Exporter
	 */
	public JMenuItem getItemExporter() { return this.itemExporter; }

	/**
	 * Retourne l'item de menu Rafraîchir
	 */
	public JMenuItem getItemRefresh() { return this.itemRefresh; }

	/**
	 * Retourne l'item de menu Quitter
	 */
	public JMenuItem getItemQuitter() { return this.itemQuitter; }

	/**
	 * Gère les événements des items de menu
	 * Redirige vers la méthode appropriée selon la commande
	 */
	public void actionPerformed( ActionEvent e ) 
	{
		String cmd = e.getActionCommand();
		
		if ( cmd == null ) return;

		switch ( cmd ) 
		{
			case "ouvrirDossier"    -> this.ouvrirDossier();
			case "sauvegarder"      -> this.sauvegarder();
			case "ouvrirData"       -> this.ouvrirData();
			case "ouvrirSauvegarde" -> this.ouvrirSauvegarde();
			case "exporter"         -> this.exporter();
			case "refresh"          -> this.refresh();
			case "quitter"          -> System.exit( 0 );
			default                 -> { }
		}
	}

	/**
	 * Ouvre un dossier via un sélecteur de fichiers
	 * Permet de choisir un dossier contenant des fichiers sources à analyser
	 */
	public void ouvrirDossier()
	{
		JFileChooser fileChooser = new JFileChooser();
		String       userDir, chemin;
		int          returnValue    ;
		
		fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

		/* Ouvre le dossier courant par défaut */
		userDir = System.getProperty( "user.dir" );
		fileChooser.setCurrentDirectory( new File( userDir + "/src/data" ) );

		returnValue = fileChooser.showOpenDialog( this );

		if ( returnValue == JFileChooser.APPROVE_OPTION )
		{
			chemin = fileChooser.getSelectedFile().getAbsolutePath();
			this.frameUML.lireDossier( chemin );
		}
	}

	/**
	 * Ouvre une sauvegarde existante via un sélecteur de fichiers
	 * Permet de charger un diagramme UML précédemment sauvegardé
	 */
	public void ouvrirSauvegarde()
	{
		JFileChooser fileChooser = new JFileChooser();
		int          returnValue;
		String       chemin;
		
		fileChooser.setCurrentDirectory( new File( "./src/sauvegarde" ) );
		fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

		returnValue = fileChooser.showOpenDialog( this );

		if ( returnValue == JFileChooser.APPROVE_OPTION )
		{
			chemin = fileChooser.getSelectedFile().getAbsolutePath();
			this.frameUML.lireData( chemin );
		}
	}

	/**
	 * Sauvegarde l'état actuel du diagramme UML
	 */
	public void sauvegarder() 
	{
		try 
		{
			this.frameUML.sauvegarder();
		} 
		catch ( Exception e ) 
		{
			/* Gestion silencieuse de l'erreur */
		}
	}

	/**
	 * Exporte le diagramme UML en image
	 * Ouvre un dialogue de sauvegarde pour choisir l'emplacement
	 */
	public void exporter()
	{
		JFileChooser fileChooser;
		int          returnValue;
		String       selectedPath;
		
		try
		{
			fileChooser = new JFileChooser();
			returnValue = fileChooser.showSaveDialog( null );

			if ( returnValue == JFileChooser.APPROVE_OPTION )
			{
				selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
				System.out.println( "Exporting to: " + selectedPath );
				this.frameUML.exportToImage( selectedPath );
			}
		} 
		catch ( Exception ex )
		{
			/* Gestion silencieuse de l'erreur */
		}
	}

	/**
	 * Rafraîchit l'affichage du diagramme UML
	 */
	public void refresh()
	{
		try
		{
			if ( this.onActualiser != null ) 
			{
				this.onActualiser.run();
			}
		} 
		catch ( Exception ex ) 
		{
			/* Gestion silencieuse de l'erreur */
		}
	}

	/**
	 * Ouvre le fichier de données par défaut
	 * Charge automatiquement la sauvegarde save.ser
	 */
	private void ouvrirData()
	{
		System.out.println( "Ouverture du fichier data... depuis menu" );
		this.frameUML.charger( "src/sauvegarde/save.ser" );
	}
}