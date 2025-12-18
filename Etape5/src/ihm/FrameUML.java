package src.ihm;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import src.Controleur;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;
import src.metier.LireFichier;

public class FrameUML extends JFrame
{
	private Controleur        ctrl;

	private Menu              menuBar;
	private PanelPrincipal    panelPrincipal;
	private PanelListeFichier panelListeFichier;

	private JScrollPane       scrollPanelPrincipal;

	public FrameUML( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setTitle   ( "Schéma UML" );
		this.setLocation( 50, 25       );
		this.setSize    ( 1400, 800     );
		this.setLayout  ( new BorderLayout() );

		this.menuBar           = new Menu             ( this );
		this.panelPrincipal    = new PanelPrincipal   ( this );
		this.panelListeFichier = new PanelListeFichier( this );

		// --- ScrollPane ---
		this.scrollPanelPrincipal = new JScrollPane( this.panelPrincipal );
		this.scrollPanelPrincipal.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		this.scrollPanelPrincipal.setVerticalScrollBarPolicy  ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED   );

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.add( this.menuBar              , BorderLayout.NORTH  );
		this.add( this.scrollPanelPrincipal , BorderLayout.CENTER );
		this.add( this.panelListeFichier    , BorderLayout.EAST   );

		/*-------------------------------*/
		/* Finalisation                  */
		/*-------------------------------*/
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible( true );

		this.afficherChoixOuverture();
	}
	
	public int getNbClasses()
	{
		return this.ctrl.getNbClasses();
	}

	public ArrayList<LireFichier> getListeFichiers()
	{
		return this.ctrl.getListeFichiers();
	}
	
	public ArrayList<Association> getListeAssociation()
	{
		return this.ctrl.getListeAssociation();
	}
	
	public HashMap<Association, ArrayList<String>> gethMAttributsAssociations()
	{
		return this.ctrl.gethMAttributsAssociations();
	}
	
	public void setPosition( LireFichier lF, int x, int y )
	{
		this.ctrl.setPosition( lF, x, y );
	}

	public void lireDossier( String chemin )
	{
		this.ctrl.lireDossier( chemin );

		this.panelPrincipal   .instancierPanel();
		this.panelListeFichier.instancierPanel();
	}

	private void afficherChoixOuverture()
	{
		String[] options = { "Ouvrir un fichier .data", "Ouvrir un dossier", "Annuler" };

		int choix = JOptionPane.showOptionDialog(
			this,
			"Que voulez-vous ouvrir ?",
			"Ouverture",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]
		);

		if ( choix == 0 )
		{
			ouvrirFichierData();
		}
		else if ( choix == 1 )
		{
			ouvrirDossier();
		}
		else
		{
			System.exit(0);
		}
	}

	private void ouvrirDossier()
	{
		JFileChooser file = new JFileChooser();
		file.setCurrentDirectory( new File( "./src/data" ) );
		file.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

		int retour = file.showOpenDialog( this );

		if ( retour == JFileChooser.APPROVE_OPTION )
		{
			File dossier = file.getSelectedFile();

			lireDossier( dossier.getAbsolutePath() );

			this.maj();
		}
	}
	
	private void ouvrirFichierData()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory( new File( "./src/sauvegarde" ) );
		chooser.setFileFilter( new javax.swing.filechooser.FileNameExtensionFilter( "Fichier .data", "data" ) );

		int retour = chooser.showOpenDialog( this );

		if ( retour == JFileChooser.APPROVE_OPTION )
		{
			File fichier = chooser.getSelectedFile();

			this.ctrl.charger( fichier.getAbsolutePath() );

			this.maj();
		}
	}

	public String afficherAttribut( Attribut a )
	{
		return this.ctrl.afficherAttribut( a );
	}
	
	public String afficherMethode( Methode m, boolean complet )
	{
		return this.ctrl.afficherMethode( m, complet );
	}

	public boolean nomEstDansRepertoire( String nomClasse )
	{
		return this.ctrl.nomEstDansRepertoire( nomClasse );
	}

	public void exportToImage( String path )
	{
		this.panelPrincipal.exportToImage( path );
	}

	public void maj()
	{
		this.panelPrincipal   .repaint();
		this.panelListeFichier.maj();
	}

	public void sauvegarder()
	{
		this.ctrl.sauvegarder();
	}

	public void charger(String path)
	{
		String result = "";
			this.ctrl.charger(path);
			if (this.ctrl.getLireDossier() != null)
			{
				result = "Chargement terminé.";
				this.panelPrincipal.instancierPanel();
				this.panelListeFichier.instancierPanel();
			}
			else
			{
				result = "Erreur lors du chargement.";
			}

		JOptionPane.showOptionDialog(
			this,
			result,
			"Ouverture",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			new String[] { "OK" },
			"OK"
		);

	}
}
