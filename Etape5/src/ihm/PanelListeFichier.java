package src.ihm;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelListeFichier extends JPanel 
{
	private FrameUML frameUML;

	public PanelListeFichier ( FrameUML frameUML )
	{
		this.frameUML = frameUML;

		this.setLayout( new GridLayout( this.frameUML.getNbClasses(), 0 ) );

		this.add( new JLabel( "Liste des fichiers dans le dossier choisi : " ) );

		for ( int cpt = 0; cpt < this.frameUML.getNbClasses(); cpt++ )
		{
			this.add( new JLabel( this.frameUML.getListeFichiers().get( cpt ).getNomClasse() ) );
		}
	}
}
