package src.ihm;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;
import src.metier.LireFichier;

public class PanelPrincipal extends JPanel
{
    private FrameUML frameUML;
	private HashMap<Bloc, LireFichier> hMBlocs;
	private CreerImage creerImage;

    public PanelPrincipal ( FrameUML frameUML )
    {
        this.frameUML   = frameUML;
		this.creerImage = new CreerImage();
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

			bloc.setLocation ( lF.getPosX(), lF.getPosY() );

			this.add( bloc );
			bloc.maj();
		}

		for ( Association a : this.frameUML.getListeAssociation() )
		{
			
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

	public int getTaille(Bloc b) { return this.hMBlocs.get(b).calculTaille(); }

	public int getLargeurMax(Bloc b) { return this.hMBlocs.get(b).caulculLargeurMax(); }

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

	public void setPosition( Bloc b, int x, int y )
	{
		this.frameUML.setPosition( this.hMBlocs.get( b ), x, y );
	}

    public void maj ()
    {

		this.revalidate();
        this.repaint();
    }

	public void exportToImage(String path)
	{

		BufferedImage img = new BufferedImage(this.getWidth(),  this.getHeight() , BufferedImage.TYPE_INT_ARGB);

		// Récupération du Graphics2D de l'image
		Graphics2D g2d = img.createGraphics();

		// Dessiner le panel dans le BufferedImage
		this.paint(g2d);

		// Libération des ressources graphiques
		g2d.dispose();

		System.out.println("Export de l'image vers : " + path);

		System.out.println(this.creerImage.exportToImage(img, path));

	}
}