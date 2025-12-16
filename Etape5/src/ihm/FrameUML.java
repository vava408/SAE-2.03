package src.ihm;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
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
	}

	public void lireDossier( String chemin )
	{
		this.ctrl.lireDossier( chemin );

		this.panelPrincipal   .instancierPanel();
		this.panelListeFichier.instancierPanel();
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

	public void setPosition( LireFichier lF, int x, int y )
	{
		this.ctrl.setPosition( lF, x, y );
	}

	public String afficherAttribut( Attribut a )
	{
		return this.ctrl.afficherAttribut( a );
	}

	public String afficherMethode( Methode m )
	{
		return this.ctrl.afficherMethode( m );
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

	public void charger( String path )
	{
		this.ctrl.charger( path );

		this.panelPrincipal   .instancierPanel();
		this.panelListeFichier.instancierPanel();
	}
}
