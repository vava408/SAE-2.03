package src.ihm;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelListeFichier extends JPanel
{
    private FrameUML frameUML;

    public PanelListeFichier ( FrameUML frameUML )
	{
		this.frameUML = frameUML;

		this.setBackground( new Color( 230, 230, 230 ) );
		this.setLayout( new GridLayout( 0, 1 ) );
	}

	public void instancierPanel()
	{
		this.removeAll();
		
		JLabel titre = new JLabel( "Liste des fichiers" );
		titre.setHorizontalAlignment( SwingConstants.CENTER );
		this.add( titre );

		for ( int cpt = 0; cpt < this.frameUML.getNbClasses(); cpt++ )
		{
			JLabel lbl = new JLabel( this.frameUML.getListeFichiers().get( cpt ).getNomClasse() );
			lbl.setHorizontalAlignment( SwingConstants.CENTER );
			this.add( lbl );
		}
		
		this.maj();
	}

    public void maj ()
    {
        this.revalidate();
        this.repaint();
    }
}