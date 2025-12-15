package src.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import src.membres.Attribut;
import src.membres.Methode;

public class Bloc extends JPanel
{
	private PanelPrincipal panelPrincipal;

    public Bloc ( PanelPrincipal panelPrincipal )
    {
        this.panelPrincipal = panelPrincipal;
        this.setBackground ( new Color ( 250, 250, 250 ) );

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

		int hauteurTotale = margeVerticalNom + this.panelPrincipal.getListeAttributs( this ).size() * 
		                                       hauteurLigneAttribut + margeVerticalAttributs + 
											   this.panelPrincipal.getListeMethodes( this ).size()  * 
											   hauteurLigneMethode + margeVerticalMethodes;

		// Calcul largeur maximale en fonction des textes
		int         largeurMax = 100; // largeur minimum
		Font        fontNom    = new Font( "Arial", Font.BOLD, 12 );
		Font        fontTexte  = new Font( "Arial", Font.PLAIN, 11);
		FontMetrics fmNom      = getFontMetrics( fontNom                     );
		FontMetrics fmTexte    = getFontMetrics( fontTexte                   );

		if ( ! this.panelPrincipal.getMotCle( this ).equals("class"))
		{
			String stereotype = "<< " + this.panelPrincipal.getMotCle( this ) + " >>";
			largeurMax = Math.max(largeurMax, fmNom.stringWidth(stereotype) + 30);
		}

		largeurMax = Math.max( largeurMax, fmNom.stringWidth( this.panelPrincipal.getNomClasse( this ) ) + 30);

		for ( Attribut a : this.panelPrincipal.getListeAttributs( this ) )
		{
			largeurMax = Math.max( largeurMax, fmTexte.stringWidth( a.toString() ) + 30);
		}

		for ( Methode m : this.panelPrincipal.getListeMethodes( this ) )
		{
			largeurMax = Math.max( largeurMax, fmTexte.stringWidth( m.toString() ) + 30);
		}

		// Appliquer taille
		this.setSize( largeurMax, hauteurTotale );
		this.setPreferredSize( getSize() );
		this.repaint();
	}

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

		int hauteurAttributs = this.panelPrincipal.getListeAttributs( this ).size() * 
		                       hauteurLigneAttribut + margeVerticalAttributs;

		int hauteurMethodes  = this.panelPrincipal.getListeMethodes ( this ).size() * 
		                       hauteurLigneMethode + margeVerticalMethodes;

		int hauteurTotale    = hauteurNom + hauteurAttributs + hauteurMethodes;

		// Contour global
		g2.setColor(Color.BLACK);
		g2.setStroke(new java.awt.BasicStroke(1.0f));
		g2.drawRect(0, 0, largeur - 1, hauteurTotale - 1);

		// Nom de la classe
		g2.setFont(new Font("Arial", Font.BOLD, 12));
		FontMetrics fm = g2.getFontMetrics();
		int yTexte = 20;

		if (! this.panelPrincipal.getMotCle( this ).equals("class"))
		{
			String stereotype = "<< " + this.panelPrincipal.getMotCle( this ) + " >>";
			int xStereotype   = (largeur - fm.stringWidth(stereotype)) / 2;
			g2.drawString(stereotype, xStereotype, yTexte);
			yTexte += 18;
		}

		String nomClasse   = this.panelPrincipal.getNomClasse( this );
		int    xNomClasse  = ( largeur - fm.stringWidth( nomClasse ) ) / 2;
		g2.drawString( nomClasse, xNomClasse, yTexte );

		// Ligne séparatrice nom/attributs
		yCourant = hauteurNom;
		g2.drawLine(0, yCourant, largeur, yCourant);

		// Attributs
		g2.setFont( new Font( "Arial", Font.PLAIN, 11 ) );
		
		yTexte = yCourant + hauteurLigneAttribut;
		for ( Attribut a : this.panelPrincipal.getListeAttributs( this ) )
		{
			String affichage = this.panelPrincipal.afficherAttribut( a );

			g2.drawString( this.panelPrincipal.afficherAttribut( a ), margeHorizontale / 2, yTexte);
			
			if ( a.isStatic() )
			{
				int largeurTexte = g2.getFontMetrics().stringWidth( affichage );

				g2.drawLine( margeHorizontale / 2, yTexte + 1, margeHorizontale / 2 + largeurTexte, yTexte + 1 );
			}
				
			yTexte += hauteurLigneAttribut;
		}

		// Ligne séparatrice attributs/méthodes
		yCourant += hauteurAttributs;
		g2.drawLine(0, yCourant, largeur, yCourant);

		// Méthodes
		yTexte = yCourant + 16;
		int yBasMethodes = yTexte;

		for ( Methode m : this.panelPrincipal.getListeMethodes( this ) )
		{
			String affichage = this.panelPrincipal.afficherMethode( m );

			g2.drawString( affichage, margeHorizontale / 2, yTexte );

			if ( m.isStatic() )
			{
				int largeurTexte = g2.getFontMetrics().stringWidth( affichage );

				g2.drawLine( margeHorizontale / 2, yTexte + 1, margeHorizontale / 2 + largeurTexte, yTexte + 1 );
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

        public void mousePressed ( MouseEvent e )
        {
            coordonneePoint = e.getPoint ( );
        }

        public void mouseDragged ( MouseEvent e )
        {
            int dx = e.getX ( ) - coordonneePoint.x;
            int dy = e.getY ( ) - coordonneePoint.y;

			int nouveauX = e.getX() + dx;
			int nouveauY = e.getY() + dy;

			Bloc.this.panelPrincipal.setPosition( Bloc.this, nouveauX, nouveauY );

            setLocation ( getX ( ) + dx, getY ( ) + dy );
            getParent ( ).repaint ( );
        }
    }
}
