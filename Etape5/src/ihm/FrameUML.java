package src.ihm;

import src.Controleur;

import src.membres.Attribut;
import src.membres.Methode;

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
		this.panelListeFichier = new PanelListeFichier( this );

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.add( this.menuBar          , BorderLayout.NORTH  );
		this.add( this.panelPrincipal   , BorderLayout.CENTER );
		this.add( this.panelListeFichier, BorderLayout.EAST   );

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

	public String afficherAttribut( Attribut a )
	{
		return this.ctrl.afficherAttribut( a );
	}

	public String afficherMethode( Methode m )
	{
		return this.ctrl.afficherMethode( m );
	}

	// Déclenche la méthode paintComponent
	public void maj()
	{
		this.panelPrincipal   .repaint();
		this.panelListeFichier.maj();
	}
}