package src.ihm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import src.metier.LireFichier;

public class Bloc extends JPanel
{
    private LireFichier lireFichier;
	private CreerImage   creerImage;

    public Bloc ( LireFichier lireFichier )
    {
        this.lireFichier = lireFichier;
		this.creerImage  = new CreerImage();
        this.setBackground ( new Color ( 250, 250, 250 ) );
        this.maj ( );

        GereSouris gs = new GereSouris ( );
        this.addMouseListener ( gs );
        this.addMouseMotionListener ( gs );
    }

    public void maj()
	{
		// Calcul de la hauteur
		int margeVerticalNom       = 40;
		int margeVerticalAttributs = 20;
		int margeVerticalMethodes  = 20;
		int hauteurLigneAttribut   = 18;
		int hauteurLigneMethode    = 18;

		int hauteurTotale = margeVerticalNom
						+ lireFichier.getListeAttributs().size() * hauteurLigneAttribut + margeVerticalAttributs
						+ lireFichier.getListeMethodes().size()  * hauteurLigneMethode + margeVerticalMethodes;

		// Calcul largeur maximale en fonction des textes
		int         largeurMax = 100; // largeur minimum
		Font        fontNom    = new Font( "Arial", Font.BOLD, 12 );
		Font        fontTexte  = new Font( "Arial", Font.PLAIN, 11);
		FontMetrics fmNom      = getFontMetrics( fontNom                     );
		FontMetrics fmTexte    = getFontMetrics( fontTexte                   );

		if (!lireFichier.getMotCle().equals("class"))
		{
			String stereotype = "<< " + lireFichier.getMotCle() + " >>";
			largeurMax = Math.max(largeurMax, fmNom.stringWidth(stereotype) + 30);
		}

		largeurMax = Math.max(largeurMax, fmNom.stringWidth(lireFichier.getNomClasse()) + 30);

		for (var att : lireFichier.getListeAttributs())
		{
			largeurMax = Math.max(largeurMax, fmTexte.stringWidth(att.toString()) + 30);
		}

		for (var m : lireFichier.getListeMethodes())
		{
			largeurMax = Math.max(largeurMax, fmTexte.stringWidth(m.toString()) + 30);
		}

		// Appliquer taille
		this.setSize(largeurMax, hauteurTotale);
		this.setPreferredSize(getSize());
		this.repaint();
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		int largeur  = getWidth();
		int yCourant = 0;

		int margeHorizontale       = 15;
		int margeVerticalNom       = 40;
		int margeVerticalAttributs = 20;
		int margeVerticalMethodes  = 20;
		int hauteurLigneAttribut   = 18;
		int hauteurLigneMethode    = 18;

		int hauteurNom       = margeVerticalNom;
		int hauteurAttributs = lireFichier.getListeAttributs().size() * hauteurLigneAttribut + margeVerticalAttributs;
		int hauteurMethodes  = lireFichier.getListeMethodes().size() * hauteurLigneMethode + margeVerticalMethodes;
		int hauteurTotale    = hauteurNom + hauteurAttributs + hauteurMethodes;

		// Contour global
		g2.setColor(Color.BLACK);
		g2.setStroke(new java.awt.BasicStroke(1.0f));
		g2.drawRect(0, 0, largeur - 1, hauteurTotale - 1);

		// Nom de la classe
		g2.setFont(new Font("Arial", Font.BOLD, 12));
		FontMetrics fm = g2.getFontMetrics();
		int yTexte = 20;

		if (!lireFichier.getMotCle().equals("class"))
		{
			String stereotype = "<< " + lireFichier.getMotCle() + " >>";
			int xStereotype   = (largeur - fm.stringWidth(stereotype)) / 2;
			g2.drawString(stereotype, xStereotype, yTexte);
			yTexte += 18;
		}

		String nomClasse   = lireFichier.getNomClasse();
		int xNomClasse     = (largeur - fm.stringWidth(nomClasse)) / 2;
		g2.drawString(nomClasse, xNomClasse, yTexte);

		// Ligne séparatrice nom/attributs
		yCourant = hauteurNom;
		g2.drawLine(0, yCourant, largeur, yCourant);

		// Attributs
		g2.setFont(new Font("Arial", Font.PLAIN, 11));
		
		yTexte = yCourant + hauteurLigneAttribut;
		for (var att : lireFichier.getListeAttributs())
		{
			g2.drawString(att.toString(), margeHorizontale / 2, yTexte);
			
			if (att.isStatic())
			{
				int largeurTexte = g2.getFontMetrics().stringWidth(att.toString());
				g2.drawLine(margeHorizontale / 2, yTexte + 1, margeHorizontale / 2 + largeurTexte, yTexte + 1);
			}
				
			yTexte += hauteurLigneAttribut;
		}

		// Ligne séparatrice attributs/méthodes
		yCourant += hauteurAttributs;
		g2.drawLine(0, yCourant, largeur, yCourant);

		// Méthodes
		yTexte = yCourant + 16;
		int yBasMethodes = yTexte;

		for (var m : lireFichier.getListeMethodes())
		{
			g2.drawString(m.toString(), margeHorizontale / 2, yTexte);

			if (m.isStatic())
			{
				int largeurTexte = g2.getFontMetrics().stringWidth(m.toString());
				g2.drawLine(margeHorizontale / 2, yTexte + 1, margeHorizontale / 2 + largeurTexte, yTexte + 1);
			}

			yTexte += hauteurLigneMethode;
		}

		yBasMethodes = yTexte + margeVerticalMethodes / 2;
		g2.drawLine(0, yBasMethodes, largeur, yBasMethodes);
	}



    // --- Classe interne privée pour drag ---
    private class GereSouris extends MouseAdapter
    {
        private Point coordonneePoint;

        @Override
        public void mousePressed ( MouseEvent e )
        {
            coordonneePoint = e.getPoint ( );
        }

        @Override
        public void mouseDragged ( MouseEvent e )
        {
            int dx = e.getX ( ) - coordonneePoint.x;
            int dy = e.getY ( ) - coordonneePoint.y;

            setLocation ( getX ( ) + dx, getY ( ) + dy );
            getParent ( ).repaint ( );
        }
    }

	public void exportToImage(String path)
	{

		BufferedImage img = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_ARGB);
		
		// Récupération du Graphics2D de l'image
		Graphics2D g2d = img.createGraphics();

		// Dessiner le panel dans le BufferedImage
		this.paint(g2d);

		// Libération des ressources graphiques
		g2d.dispose();

		System.out.println("Export de l'image vers : " + path);

		System.out.println(this.creerImage.saveImg(img, path));

	}
}
