package src.ihm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;
import src.metier.LireFichier;

public class PanelPrincipal extends JLayeredPane 
{
	private static final int MARGE_GAUCHE = 100;
	private static final int MARGE_HAUT   = 100;

	private FrameUML frameUML;
	private HashMap<Bloc, LireFichier> hMBlocs;
	private HashMap<Fleche, Association> hMFleches;
	private CreerImage creerImage;

	public PanelPrincipal(FrameUML frameUML) 
	{
		this.frameUML   = frameUML;
		this.creerImage = new CreerImage();
		this.hMBlocs    = new HashMap<>();
		this.hMFleches  = new HashMap<>();

		this.setBackground(new Color(245, 245, 245));
		this.setOpaque(true);
	}
	
	
	// --- Méthodes pour accéder aux informations des blocs ---
	public ArrayList<Attribut> getListeAttributs(Bloc b) { return hMBlocs.get(b).getListeAttributs();}
	public ArrayList<Methode>  getListeMethodes (Bloc b) { return hMBlocs.get(b).getListeMethodes ();}
	
	public String getMotCle   (Bloc b) { return hMBlocs.get(b).getMotCle   ();}
	public String getNomClasse(Bloc b) { return hMBlocs.get(b).getNomClasse();}
	
	public Bloc getBloc(String nomClasse)
	{
		for (Bloc b : hMBlocs.keySet())
		{
			if (hMBlocs.get(b).getNomClasse().equals(nomClasse))
				{
				return b;
			}
		}
		return null;
	}

	public Association getAssociation(Fleche f)
	{
		return this.hMFleches.get(f);
	}

	public String getHerit(Bloc b)
	{
		for (LireFichier lF : this.frameUML.getListeFichiers()) 
		{
			if (lF.getMapHerit().containsKey(this.getNomClasse(b)))
			{
				return lF.getMapHerit().get(this.getNomClasse(b));
			}
		}
		return null; // pas d'héritage
	}

	public ArrayList<String> getImple(Bloc b)
	{
		for (LireFichier lF : this.frameUML.getListeFichiers()) 
		{
			if (lF.getMapImple().containsKey(this.getNomClasse(b)))
			{
				return lF.getMapImple().get(this.getNomClasse(b));
			}
		}
		return null; // pas d'implementaion
	}

	public int getTaille(Bloc b, boolean complet)
	{
		if ( complet )
		{
			return this.hMBlocs.get(b).calculTailleComplet();
		}
		else
			{
			return this.hMBlocs.get(b).calculTaille();
		}
	}

	public int getLargeurMax(Bloc b)
	{
		return this.hMBlocs.get(b).caulculLargeurMax(); 
	}

	public HashMap<Association, ArrayList<String>> gethMAttributsAssociations()
	{
		return this.frameUML.gethMAttributsAssociations();
	}
	
	public void setPosition(Bloc b, int x, int y)
	{
		this.frameUML.setPosition( hMBlocs.get( b ), x - PanelPrincipal.MARGE_GAUCHE, y - PanelPrincipal.MARGE_HAUT);
		maj(); // mettre à jour les flèches
	}
	

	public void instancierPanel() 
	{
		this.removeAll();
		this.hMBlocs   .clear();
		this.hMFleches .clear();

		// --- Ajouter les blocs ---
		for (LireFichier lF : this.frameUML.getListeFichiers()) 
		{
			Bloc bloc = new Bloc(this);

			hMBlocs.put(bloc, lF);

			bloc.setBounds( lF.getPosX() + PanelPrincipal.MARGE_GAUCHE, lF.getPosY() + PanelPrincipal.MARGE_HAUT, 
							lF.getLargeur(), lF.getHauteur()); // taille provisoire

			this.add(bloc, JLayeredPane.DEFAULT_LAYER);
			bloc.maj();
		}

		// --- Ajouter les flèches ---
		for ( Association a : this.frameUML.getListeAssociation() )
		{
			Fleche fleche = new Fleche( this, a.getNomClasseA(), a.getNomClasseB(),
										a.getMultiplicityA(), a.getMultiplicityB() );

			hMFleches.put( fleche, a );	

			fleche.setBounds(0, 0, getWidth(), getHeight());

			this.add(fleche, JLayeredPane.PALETTE_LAYER); // toujours au-dessus
			fleche.maj();
		}
		// --- Ajouter les flèches implements et héritage ---
		for ( Bloc b : hMBlocs.keySet() )
		{
			String nomClasse = this.getNomClasse( b );
			String heritage = this.getHerit( b );
			ArrayList<String> implement = this.getImple( b );

			if ( heritage != null )
			{
				ajouterFleche( nomClasse, heritage );
			}
			
			if ( implement != null )
			{
				for ( String nomImplement : implement )
				{
					ajouterFleche( nomClasse, nomImplement );
				}
			}
		}

		this.placerBlocs();
		this.revalidate();
		this.repaint();
	}


	private void ajouterFleche(String source, String cible)
	{
		Fleche fleche = new Fleche(
			this,
			source,
			cible,
			"", "" // pas de multiplicités
		);

		hMFleches.put(fleche, null);
		this.add(fleche, JLayeredPane.PALETTE_LAYER);
		fleche.maj();
	}


	
	public void placerBlocs() 
	{
		int margeHorizontale = 50;
		int margeVerticale   = 50;

		int x = margeHorizontale;
		int y = margeVerticale;
		int ligneMax = 3; // nombre de blocs par ligne avant de passer à la ligne suivante
		int compteur = 0;


		for (Bloc b : hMBlocs.keySet()) 
		{
			b.setBounds(x, y, b.getWidth(), b.getHeight());

			x += b.getWidth() + margeHorizontale;
			compteur++;

			if (compteur >= ligneMax)
			{
				compteur = 0;
				x = margeHorizontale;
				y += 300; // hauteur approximative d'un bloc + marge verticale
			}
			System.out.println("Placement du bloc " + hMBlocs.get(b).getNomClasse() + " en (" + b.getX() + ", " + b.getY() + ")");
		}
	}
	
	private void recalculerTaille()
	{
		int maxX = 0;
		int maxY = 0;

		for ( Bloc b : hMBlocs.keySet() )
		{
			maxX = Math.max( maxX, b.getX() + b.getWidth()  );
			maxY = Math.max( maxY, b.getY() + b.getHeight() );
		}

		// marge de confort
		maxX += 100;
		maxY += 100;

		this.setPreferredSize( new Dimension( maxX, maxY ) );
	}

	public void maj() 
	{
		// Mettre à jour les blocs et les flèches
		for ( Bloc   b : hMBlocs  .keySet() ) { b.maj() ;}
		for ( Fleche f : hMFleches.keySet() ) { f.maj() ;}

		this.recalculerTaille();
		this.revalidate      ();
		this.repaint         ();
	}

	public void exportToImage(String path)
	{
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g2d = img.createGraphics();
		
		this.paint(g2d);
		g2d.dispose();

		System.out.println("Export de l'image vers : " + path);
		System.out.println(creerImage.exportToImage(img, path));
	}

	public String afficherAttribut(Attribut a) 
	{
		return this.frameUML.afficherAttribut(a);
	}
	
	public String afficherMethode(Methode m, boolean complet )
	{
		return this.frameUML.afficherMethode(m, complet);
	}

}