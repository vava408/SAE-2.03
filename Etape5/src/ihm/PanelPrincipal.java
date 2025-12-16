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

	public void instancierPanel() 
	{
		this.removeAll();

		// --- Ajouter les blocs ---
		for (LireFichier lF : this.frameUML.getListeFichiers()) 
		{
			Bloc bloc = new Bloc(this);

			hMBlocs.put(bloc, lF);

			bloc.setBounds(lF.getPosX(), lF.getPosY(), lF.getLargeur(), lF.getHauteur()); // taille provisoire

			this.add(bloc, JLayeredPane.DEFAULT_LAYER);
			bloc.maj();
		}

		// --- Ajouter les flèches ---
		for (Association a : this.frameUML.getListeAssociation())
		{
			Fleche fleche = new Fleche(this, a.getNomClasseA(), a.getNomClasseB(),
										a.getMultiplicityA(), a.getMultiplicityB());

			hMFleches.put(fleche, a);	

			fleche.setBounds(0, 0, getWidth(), getHeight());

			this.add(fleche, JLayeredPane.PALETTE_LAYER); // toujours au-dessus
			fleche.maj();
		}
		
		this.placerBlocs();
		this.revalidate();
		this.repaint();
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
		}
	}
    


	public void maj() {
		// Mettre à jour les blocs et les flèches
		for (Bloc b   : hMBlocs  .keySet()){b.maj();}
		for (Fleche f : hMFleches.keySet()){f.maj();}
		this.revalidate();
		this.repaint();
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
	
	public void setPosition(Bloc b, int x, int y)
	{
		this.frameUML.setPosition(hMBlocs.get(b), x, y);
		maj(); // mettre à jour les flèches
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

	public String afficherMethode(Methode m) 
	{
		return this.frameUML.afficherMethode(m);
	}

}