package src.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*--------------------------------------------------------*/
/* Menu.java                                              */
/* Barre de menu de l'application permettant de gérer    */
/* les fichiers et les actions sur le diagramme UML      */
/* Projet : Générateur de diagrammes UML                 */
/* Groupe : 6                                             */
/* Auteurs : [Noms des auteurs]                          */
/* Date de création : 19/12/2024 14:45                   */
/*--------------------------------------------------------*/

/**
 * Classe représentant la barre de menu de l'application UML.
 * Gère les actions du menu fichier : ouvrir, sauvegarder, exporter,
 * rafraîchir et quitter. Implémente ActionListener pour gérer les
 * événements des items de menu.
 * 
 * @author [Noms des auteurs]
 * @version 1.0
 * @since 19/12/2024
 */
public class Menu extends JMenuBar implements ActionListener
{
	private FrameUML         frameUML;
	private JMenu            menuFichier;
	private JMenuItem        itemSauvegarde;
	private JMenuItem        itemOuvrirDossier;
	private JMenuItem        itemOuvrirSauvegarde;
	private JMenuItem        itemQuitter;
	private JMenuItem        itemExporter;
	private JMenuItem        itemRefresh;
	private JMenuItem        itemOuvrirData;
	private Runnable         onActualiser;

	/*--------------------------------------------------------*/
	/*                     CONSTRUCTEUR                        */
	/*--------------------------------------------------------*/

	/**
	 * Constructeur de la classe Menu.
	 * Initialise la barre de menu avec tous les items et leurs actions.
	 * Configure les listeners et les commandes d'action pour chaque item.
	 * 
	 * @param frameUML La frame principale de l'application
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

	/*--------------------------------------------------------*/
	/*                      ACCESSEURS                         */
	/*--------------------------------------------------------*/

	/**
	 * Retourne l'item de menu Sauvegarder.
	 * 
	 * @return L'item de menu pour sauvegarder
	 */
	public JMenuItem getItemSauvegarde() 
	{ 
		return this.itemSauvegarde; 
	}

	/**
	 * Retourne l'item de menu Ouvrir Dossier.
	 * 
	 * @return L'item de menu pour ouvrir un dossier
	 */
	public JMenuItem getItemOuvrirDossier() 
	{ 
		return this.itemOuvrirDossier; 
	}

	/**
	 * Retourne l'item de menu Ouvrir Data.
	 * 
	 * @return L'item de menu pour ouvrir le fichier data
	 */
	public JMenuItem getItemOuvrirData() 
	{ 
		return this.itemOuvrirData; 
	}

	/**
	 * Retourne l'item de menu Exporter.
	 * 
	 * @return L'item de menu pour exporter
	 */
	public JMenuItem getItemExporter() 
	{ 
		return this.itemExporter; 
	}

	/**
	 * Retourne l'item de menu Rafraîchir.
	 * 
	 * @return L'item de menu pour rafraîchir
	 */
	public JMenuItem getItemRefresh() 
	{ 
		return this.itemRefresh; 
	}

	/**
	 * Retourne l'item de menu Quitter.
	 * 
	 * @return L'item de menu pour quitter
	 */
	public JMenuItem getItemQuitter() 
	{ 
		return this.itemQuitter; 
	}

	/*--------------------------------------------------------*/
	/*                  AUTRES MÉTHODES                        */
	/*--------------------------------------------------------*/

	/**
	 * Gère les événements des items de menu.
	 * Redirige vers la méthode appropriée selon la commande d'action.
	 * 
	 * @param e L'événement d'action déclenché
	 */
	public void actionPerformed( ActionEvent e ) 
	{
		String cmd;
		
		cmd = e.getActionCommand();
		
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
	 * Ouvre un dossier via un sélecteur de fichiers.
	 * Permet de choisir un dossier contenant des fichiers sources à analyser.
	 * Le dossier par défaut est src/data.
	 */
	public void ouvrirDossier()
	{
		JFileChooser fileChooser;
		String       userDir;
		String       chemin;
		int          returnValue;
		
		fileChooser = new JFileChooser();
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
	 * Ouvre une sauvegarde existante via un sélecteur de fichiers.
	 * Permet de charger un diagramme UML précédemment sauvegardé.
	 * Le répertoire par défaut est src/sauvegarde.
	 */
	public void ouvrirSauvegarde()
	{
		JFileChooser fileChooser;
		int          returnValue;
		String       chemin;
		
		fileChooser = new JFileChooser();
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
	 * Sauvegarde l'état actuel du diagramme UML.
	 * Les erreurs sont gérées silencieusement.
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
	 * Exporte le diagramme UML en image.
	 * Ouvre un dialogue de sauvegarde pour choisir l'emplacement et le nom.
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
	 * Rafraîchit l'affichage du diagramme UML.
	 * Exécute la fonction de rappel onActualiser si elle est définie.
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

	/*--------------------------------------------------------*/
	/*                   MÉTHODES PRIVÉES                      */
	/*--------------------------------------------------------*/

	/**
	 * Ouvre le fichier de données par défaut.
	 * Charge automatiquement la sauvegarde save.ser depuis src/sauvegarde.
	 */
	private void ouvrirData()
	{
		System.out.println( "Ouverture du fichier data... depuis menu" );
		this.frameUML.charger( "src/sauvegarde/save.ser" );
	}
}