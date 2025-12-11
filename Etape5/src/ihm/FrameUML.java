package src.ihm;

import src.Controleur;

import java.awt.event.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FrameUML extends JFrame
{
	private Controleur        ctrl;

	private PanelPrincipal    panelPrincipal;
	private PanelListeFichier panelListeFichier;

	private JMenuItem      menuiFichierOuvrir ;
	private JMenuItem      menuiFichierQuitter;
	
	public FrameUML( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setTitle   ( "Schéma UML" );
		this.setLocation( 50, 25       );
		this.setSize    ( 410, 600     );

		this.panelPrincipal    = new PanelPrincipal   ( this.ctrl );
		this.panelListeFichier = new PanelListeFichier( this.ctrl );

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.add( this.panelPrincipal    );
		this.add( this.panelListeFichier );

		/*-------------------------------*/
		/* Finalisation                  */
		/*-------------------------------*/
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible( true );
	}

	// Déclenche la méthode paintComponent du PanelImage
	public void maj() { this.panelPrincipal.repaint(); }
}