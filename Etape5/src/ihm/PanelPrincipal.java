package src.ihm;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import src.metier.LireFichier;

import src.membres.Attribut;
import src.membres.Methode;

public class PanelPrincipal extends JPanel
{
<<<<<<< HEAD
    private FrameUML frameUML;
	private HashMap<Bloc, LireFichier> hMBlocs;
=======
    private FrameUML frame;
	private Bloc     bloc;
>>>>>>> 32871d97c1064388a7510bbc7751415aa51b071d

    public PanelPrincipal ( FrameUML frameUML )
    {
        this.frameUML   = frameUML;
		this.hMBlocs = new HashMap<>();

        this.setBackground( new Color( 245, 245, 245 ) );
        this.setLayout( new FlowLayout() );
    }

	public void instancierPanel()
	{
		this.removeAll();
	
		for ( LireFichier lF : this.frameUML.getListeFichiers() )
		{
			Bloc bloc = new Bloc( this );
			
			if ( ! this.hMBlocs.containsKey( bloc ) )
			{
				this.hMBlocs.put( bloc, lF );
			}

			this.add( bloc );
			bloc.maj();
		}

		this.maj();
	}

	public ArrayList<Attribut> getListeAttributs( Bloc b )
	{
		return this.hMBlocs.get( b ).getListeAttributs();
	}

	public ArrayList<Methode> getListeMethodes( Bloc b )
	{
		return this.hMBlocs.get( b ).getListeMethodes();
	}

	public String getMotCle( Bloc b )
	{
		return this.hMBlocs.get( b ).getMotCle();
	}

	public String getNomClasse( Bloc b )
	{
		return this.hMBlocs.get( b ).getNomClasse();
	}

	public String afficherAttribut( Attribut a )
	{
		return this.frameUML.afficherAttribut( a );
	}

	public String afficherMethode ( Methode m )
	{
		return this.frameUML.afficherMethode( m );
	}

    public void maj ()
    {

		this.revalidate();
        this.repaint();
    }

	public void exportToImage(String selectedPath)
	{
		System.out.println("Exporting de Panel to: " + selectedPath);
		this.bloc.exportToImage(selectedPath);
	}
}