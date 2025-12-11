package src.ihm;

import src.Controleur;

import java.awt.BorderLayout;

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

	// Déclenche la méthode paintComponent du PanelPrincipal
	public void maj() { this.panelPrincipal.repaint(); }
}