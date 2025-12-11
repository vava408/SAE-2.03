package src.ihm;

import src.Controleur;

import java.awt.event.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FrameUML extends JFrame implements ActionListener
{
	private Controleur     ctrl;

	private PanelPrincipal panelPrincipal;

	private JMenuItem      menuiFichierOuvrir ;
	private JMenuItem      menuiFichierQuitter;
	
	public FrameUML( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setTitle   ( "Schéma UML" );
		this.setLocation( 50, 25       );
		this.setSize    ( 410, 600     );

		// l'ensemble du menu
		JMenuBar menubMaBarre = new JMenuBar();

		// un element de la barre de menu
		JMenu menuFichier = new JMenu( "Fichier" );

		// les items du menu fichier
		this.menuiFichierOuvrir  = new JMenuItem ( "Ouvrir"  );
		this.menuiFichierQuitter = new JMenuItem ( "Quitter" );

		// ajouts des items au menu correspondant
		menuFichier.add( this.menuiFichierOuvrir );
		menuFichier.addSeparator();
		menuFichier.add( this.menuiFichierQuitter );

		// ajout du menu 'Fichier' a la barre de menu
		menubMaBarre.add( menuFichier );

		this.panelPrincipal = new PanelPrincipal( this.ctrl );


		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.setJMenuBar( menubMaBarre );
		
		this.add( this.panelPrincipal );
		
		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiFichierOuvrir .addActionListener ( this );
		this.menuiFichierQuitter.addActionListener ( this );

		/*-------------------------------*/
		/* Finalisation                  */
		/*-------------------------------*/
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible( true );
	}

	// Déclenche la méthode paintComponent du PanelImage
	public void maj() { this.panelPrincipal.repaint(); }
}