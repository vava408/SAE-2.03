package src.ihm;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;

public class PanelPrincipal extends JPanel
{
    private FrameUML frame;

    public PanelPrincipal ( FrameUML frame )
    {
        this.frame = frame;

        this.setBackground( new Color( 245, 245, 245 ) );
        this.setLayout( new FlowLayout() );
    }

	public void instancierPanel()
	{
		this.removeAll();
	
		for ( var lf : frame.getListeFichiers() )
		{
			Bloc bloc = new Bloc( lf );
			this.add( bloc );
		}

		this.maj();
	}

    public void maj ()
    {

		this.revalidate();
        this.repaint();
    }
}