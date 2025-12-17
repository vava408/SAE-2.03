package src.ihm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.JPanel;

import src.membres.Attribut;
import src.membres.Methode;

public class Bloc extends JPanel
{
	private PanelPrincipal panelPrincipal;

	//booleen pour savoir si on clique sur le bloc
	private boolean estClique;

    public Bloc ( PanelPrincipal panelPrincipal )
    {
		this.estClique = false;

        this.panelPrincipal = panelPrincipal;
        this.setBackground ( new Color ( 250, 250, 250 ) );

        GereSouris gs = new GereSouris ( );
        this.addMouseListener ( gs );
        this.addMouseMotionListener ( gs );
    }

    public void maj()
	{
		//on récupère la hauteur et la largeur des blocs
		int largeurMax    = this.panelPrincipal.getLargeurMax(this);
		int hauteurTotale = this.panelPrincipal.getTaille    (this, false);

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

		int hauteurTotale;


		/*---------------------------------------------*/
		/*on calcul la taille des différents composants*/
		/*---------------------------------------------*/

		int hauteurNom       = margeVerticalNom;

		int hauteurAttributs = this.panelPrincipal.getListeAttributs( this ).size() *
		                       hauteurLigneAttribut + margeVerticalAttributs;


		//change la taille du bloc en fonction de si on veut afficher toutes les informations
		if( this.estClique )
		{
			hauteurTotale = this.panelPrincipal.getTaille( this, true );
		}
		else
		{
			hauteurTotale = this.panelPrincipal.getTaille( this, false );
		}

		// Contour global
		g2.setColor ( Color.BLACK );
		g2.setStroke( new BasicStroke( 1.0f ) );
		g2.drawRect  ( 0, 0, largeur - 1, hauteurTotale - 1 );

		// Nom de la classe
		g2.setFont( new Font( "Arial", Font.BOLD, 12 ) );
		FontMetrics fm     = g2.getFontMetrics();
		int         yTexte = 20;


		/*-------------------------------*/
		/*on dessine les noms des classes*/
		/*-------------------------------*/

		if ( ! this.panelPrincipal.getMotCle( this ).equals( "class" ) )
		{
			String stereotype = "<< " + this.panelPrincipal.getMotCle( this ) + " >>";
			int xStereotype   = (largeur - fm.stringWidth(stereotype)) / 2;
			g2.drawString(stereotype, xStereotype, yTexte);
			yTexte += 18;
		}

		String nomClasse   = this.panelPrincipal.getNomClasse( this );
		int    xNomClasse  = ( largeur - fm.stringWidth( nomClasse ) ) / 2;
		g2.drawString( nomClasse, xNomClasse, yTexte );


		// Ligne séparatrice nom classe/attributs
		yCourant = hauteurNom;
		g2.drawLine(0, yCourant, largeur, yCourant);


		/*------------------------*/
		/*on dessine les attributs*/
		/*------------------------*/

		g2.setFont( new Font( "Arial", Font.PLAIN, 11 ) );

		yTexte = yCourant + hauteurLigneAttribut;

		//on parcours tous les attributs si on veux l'affichage complet
		if(this.estClique)
		{
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
		}
		else
		{
			int cpt = 0;

			//utilise un compteur pour parcourir les trois premiers attributs
			for ( Attribut a : this.panelPrincipal.getListeAttributs( this ) )
			{
				if(cpt > 2)
					break;

				cpt++;

				String affichage = this.panelPrincipal.afficherAttribut( a );

				g2.drawString( affichage, margeHorizontale / 2, yTexte);

				if ( a.isStatic() )
				{
					int largeurTexte = g2.getFontMetrics().stringWidth( affichage );

					g2.drawLine( margeHorizontale / 2, yTexte + 1, margeHorizontale / 2 + largeurTexte, yTexte + 1 );
				}

				yTexte += hauteurLigneAttribut;
			}

			//rajout des points de suspension s'il y a plus de trois attributs à cacher
			if(cpt > 2)
			{
				g2.drawString( "...", margeHorizontale / 2, yTexte);
				yTexte += hauteurLigneAttribut;
			}
		}

		// Ligne séparatrice attributs/méthodes
		yCourant += hauteurAttributs;
		g2.drawLine(0, yCourant, largeur, yCourant);


		/*-----------------------*/
		/*on dessine les méthodes*/
		/*-----------------------*/

		yTexte = yCourant + 16;
		int yBasMethodes = yTexte;

		if(this.estClique)
		{
			for ( Methode m : this.panelPrincipal.getListeMethodes( this ) )
			{
				String affichage = this.panelPrincipal.afficherMethode( m, true );

				g2.drawString( affichage, margeHorizontale / 2, yTexte );

				if ( m.isStatic() )
				{
					int largeurTexte = g2.getFontMetrics().stringWidth( affichage );

					g2.drawLine( margeHorizontale / 2, yTexte + 1, margeHorizontale / 2 + largeurTexte, yTexte + 1 );
				}

				yTexte += hauteurLigneMethode;
			}
		}
		else
		{
			int cpt = 0;

			//utilise un compteur pour parcourir seulement les trois premières méthodes
			for ( Methode m : this.panelPrincipal.getListeMethodes( this ) )
			{
				if(cpt > 2)
					break;

				cpt++;

				String affichage = this.panelPrincipal.afficherMethode( m, false );

				g2.drawString( affichage, margeHorizontale / 2, yTexte );

				if ( m.isStatic() )
				{
					int largeurTexte = g2.getFontMetrics().stringWidth( affichage );

					g2.drawLine( margeHorizontale / 2, yTexte + 1, margeHorizontale / 2 + largeurTexte, yTexte + 1 );
				}

				yTexte += hauteurLigneMethode;
			}

			//rajout de points de suspension s'il y a plus de trois méthodes à cacher
			if(cpt > 2)
			{
				g2.drawString( "...", margeHorizontale / 2, yTexte);
				yTexte += hauteurLigneAttribut;
			}

		}


		//augmentattion de la taille du bloc pour contenir l'entièreté des composants
		int largeurMax    = this.panelPrincipal.getLargeurMax(this);
		hauteurTotale = this.panelPrincipal.getTaille    (this, false);

		if(this.estClique)
			hauteurTotale = this.panelPrincipal.getTaille    (this, true);

		// Appliquer taille
		this.setSize( largeurMax, hauteurTotale );
		this.setPreferredSize( getSize() );

	}

    // --- Classe interne privée pour drag ---
    private class GereSouris extends MouseAdapter
    {
        private Point coordonneePoint;

        public void mousePressed ( MouseEvent e )
        {
            coordonneePoint = e.getPoint ( );

			//partie pour gérer le clic droit
			if ( e.getButton() == 3 )
			{
				estClique = true;
				repaint();
			}
        }

        public void mouseDragged ( MouseEvent e )
		{
			if ( estClique ) { return; }

			int dx = e.getX() - coordonneePoint.x;
			int dy = e.getY() - coordonneePoint.y;

			int nouveauX = getX() + dx;
			int nouveauY = getY() + dy;

			// blocage des coordonnées négatives
			if ( nouveauX < 0 ) nouveauX = 0;
			if ( nouveauY < 0 ) nouveauY = 0;

			// déplacement visuel
			setLocation( nouveauX, nouveauY );

			// sauvegarde côté modèle
			Bloc.this.panelPrincipal.setPosition( Bloc.this, nouveauX, nouveauY );

			// mise à jour flèches
			getParent().repaint();
		}

		public void mouseReleased(MouseEvent e)
		{
			estClique = false;
			repaint();
		}
    }
}
