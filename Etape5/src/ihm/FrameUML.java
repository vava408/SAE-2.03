package src.ihm;

import src.Controleur;
import src.metier.LireFichier;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

public class FrameUML extends JFrame
{
	private Controleur        ctrl;

	private Menu              menuBar;
	private PanelPrincipal    panelPrincipal;
	private PanelListeFichier panelListeFichier;
	
	public FrameUML( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setTitle   ( "Schéma UML" );
		this.setLocation( 50, 25       );
		this.setSize    ( 1400, 800     );
		this.setLayout  ( new BorderLayout() );

		this.menuBar           = new Menu             ( this );
		this.panelPrincipal    = new PanelPrincipal   ( this );
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.add( this.menuBar          , BorderLayout.NORTH  );
		this.add( this.panelPrincipal   , BorderLayout.CENTER );

		/*-------------------------------*/
		/* Finalisation                  */
		/*-------------------------------*/
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible( true );
	}

	public void lireDossier( String chemin )
	{
		this.ctrl.lireDossier( chemin );

		this.panelListeFichier = new PanelListeFichier( this );
		this.add( this.panelListeFichier, BorderLayout.EAST   );
	}

	public int getNbClasses()
	{
		return this.ctrl.getNbClasses();
	}

	public ArrayList<LireFichier> getListeFichiers()
	{
		return this.ctrl.getListeFichiers();
	}

	// Déclenche la méthode paintComponent
	public void maj()
	{
		this.panelPrincipal   .repaint();
		this.panelListeFichier.repaint();
	}
}