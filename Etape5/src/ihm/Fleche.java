package src.ihm;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import src.membres.Association;

public class Fleche extends JPanel 
{
	private PanelPrincipal panelPrincipal;

	private String nomClasseA   ;
	private String nomClasseB   ;
	private String multipliciteA;
	private String multipliciteB;

	public Fleche( PanelPrincipal panelPrincipal, String nomClasseA, String nomClasseB,
				   String multipliciteA, String multipliciteB )
	{
		this.panelPrincipal = panelPrincipal;
		this.nomClasseA     = nomClasseA    ;
		this.nomClasseB     = nomClasseB    ;
		this.multipliciteA  = multipliciteA ;
		this.multipliciteB  = multipliciteB ;

		this.setOpaque( false );
	}

	public void maj() 
	{
		this.setBounds( 0, 0, panelPrincipal.getWidth(), panelPrincipal.getHeight() );
		this.repaint();
	}

	protected void paintComponent(Graphics g) 
	{
		super.paintComponent( g );

		Graphics2D g2 = ( Graphics2D ) g;

		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		g2.setStroke( new BasicStroke( 2 ) );
		g2.setColor ( Color.BLACK );

		Bloc a = panelPrincipal.getBloc( this.nomClasseA );
		Bloc b = panelPrincipal.getBloc( this.nomClasseB );

		if (a == null || b == null) { return ;}

		Point[] pointsA = getAnchors( a );
		Point[] pointsB = getAnchors( b );

		// Trouver la paire la plus proche
		Point p1 = pointsA[ 0 ], p2 = pointsB[ 0 ];

		double minDist = p1.distance( p2 );
		for ( Point pa : pointsA ) 
		{
			for ( Point pb : pointsB )
			{
				double dist = pa.distance(pb);

				if ( dist < minDist )
				{
					minDist = dist;
					p1      = pa  ;
					p2      = pb  ;
				}
			}
		}

		// Dessiner la pointe
		dessinerLigneAvecPointes( g2, p1, p2, a, b );

		// multiplicités légèrement plus proches de la flèche
		
		dessinerTexte(g2, multipliciteA, p1, p2, 10, -20);
		dessinerTexte(g2, multipliciteB, p2, p1, 10, -20);

		// Rôles
		dessinerRolesAssociation(g2, p1, p2);
	}

	private Point[] getAnchors(Bloc b) 
	{
		int x = b.getX(), y = b.getY(), w = b.getWidth(), h = b.getHeight();

		return new Point[]
		{
			new Point(x + w / 2, y),         // haut
			new Point(x + w / 2, y + h),     // bas
			new Point(x, y + h / 2),         // gauche
			new Point(x + w, y + h / 2)      // droite
		};
	}

	private void dessinerLigneAvecPointes( Graphics2D g2, Point p1, Point p2, Bloc a, Bloc b )
	{
		// Dessiner la ligne
		dessinerLigne( g2, p1.x, p1.y, p2.x, p2.y, a, b );

		// CAS implements ou héritage → pas de multiplicité, pas de direction
		if ( this.panelPrincipal.getAssociation( this ) == null )
		{
			// IMPLEMENTS ?
			if ( this.panelPrincipal.getImple( a ) != null &&
				 this.panelPrincipal.getImple( a ).contains( this.panelPrincipal.getNomClasse( b ) ) )
			{
				dessinerTriangleBlanc(g2, p1, p2);
				return;
			}

			// HERITAGE ?
			if ( this.panelPrincipal.getNomClasse( b ).equals(this.panelPrincipal.getHerit( a ) ) )
			{
				dessinerTriangleBlanc(g2, p1, p2);
				return;
			}
		}
		
		if ( this.panelPrincipal.getAssociation( this ).estUnidirectionnelle() )
		{
			// Flèche vers le côté "1..1"
			if ( this.multipliciteB.equals( "1..1" ) )
			{
				dessinerPointe( g2, p1, p2 ); // flèche vers B
			}
			else
			{
				dessinerPointe( g2, p2, p1 ); // flèche vers A
			}
		} 

	}

	private void dessinerLigne( Graphics2D g2, int p1X, int p1Y, int p2X, int p2Y, Bloc a, Bloc b ) 
	{

		//implementation
		if ( this.panelPrincipal.getImple(a) != null )
		{
			for (String nomInterface : panelPrincipal.getImple(a))
			{
				if( this.panelPrincipal.getNomClasse(b).equals(nomInterface) )
				{
					float[] dash = {8f, 8f};

					g2.setStroke(new BasicStroke(
						2,
						BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER,
						10.0f,
						dash,
						0.0f
					));
				}
				
			}
		}
		else
		{
			// Association normale ou héritage  = ligne pleine
			g2.setStroke(new BasicStroke(2));
		}

		g2.drawLine(p1X, p1Y, p2X, p2Y);
	}

	private void dessinerPointe(Graphics2D g2, Point start, Point end) 
	{
		double angle       = Math.atan2(end.y - start.y, end.x - start.x);
		int    longueur    = 12;
		double anglePointe = Math.PI / 6;

		int xA = (int) (end.x - longueur * Math.cos(angle - anglePointe));
		int yA = (int) (end.y - longueur * Math.sin(angle - anglePointe));
		int xB = (int) (end.x - longueur * Math.cos(angle + anglePointe));
		int yB = (int) (end.y - longueur * Math.sin(angle + anglePointe));

		g2.drawLine(end.x, end.y, xA, yA);
		g2.drawLine(end.x, end.y, xB, yB);
	}

	private void dessinerTriangleBlanc(Graphics2D g2, Point start, Point end)
	{
		g2.setStroke(new BasicStroke(2));
		
		double angle = Math.atan2(end.y - start.y, end.x - start.x);
		int longueur = 16;
		double angleTriangle = Math.PI / 6;

		int x1 = (int) (end.x - longueur * Math.cos(angle - angleTriangle));
		int y1 = (int) (end.y - longueur * Math.sin(angle - angleTriangle));

		int x2 = (int) (end.x - longueur * Math.cos(angle + angleTriangle));
		int y2 = (int) (end.y - longueur * Math.sin(angle + angleTriangle));

		Polygon triangle = new Polygon();
		triangle.addPoint(end.x, end.y);
		triangle.addPoint(x1, y1);
		triangle.addPoint(x2, y2);

		g2.setColor(Color.WHITE);
		g2.fillPolygon(triangle);

		g2.setColor(Color.BLACK);
		g2.drawPolygon(triangle);
	}

	private void dessinerRolesAssociation(Graphics2D g2, Point p1, Point p2)
	{
		Association asso = panelPrincipal.getAssociation(this);
		if (asso == null) return;

		ArrayList<String> roles = getRoles(asso);
		if (roles == null || roles.isEmpty()) return;

		String roleA = roles.get(0);
		String roleB = roleA;

		if (roles.size() > 1)
		{
			roleB = roles.get(1);
		}

		Bloc a = panelPrincipal.getBloc(nomClasseA);
		Bloc b = panelPrincipal.getBloc(nomClasseB);

		// rôle côté A
		dessinerTexte(g2, roleA, p1, p2, -20, 20); 

		// rôle côté B seulement si bidirectionnelle
		if (!asso.estUnidirectionnelle())
		{
			dessinerTexte(g2, roleB, p2, p1, -20, 20);
		}
	}

	public ArrayList<String> getRoles(Association a)
	{
		return this.panelPrincipal.gethMAttributsAssociations().get( a );
	}

	// Méthode générale pour dessiner un texte (rôle ou multiplicité)
	private void dessinerTexte(Graphics2D g2, String texte, Point blocPoint, Point autrePoint, int distance, int decalagePerp) {
    if (texte == null || texte.isEmpty()) return;

    // vecteur flèche
    double dx = autrePoint.x - blocPoint.x;
    double dy = autrePoint.y - blocPoint.y;
    double longueur = Math.sqrt(dx*dx + dy*dy);
    if (longueur == 0) return;

    // vecteur normalisé perpendiculaire
    double nx = -dy / longueur;
    double ny =  dx / longueur;

    // augmenter le décalage si flèche presque verticale
    if (Math.abs(dx) < Math.abs(dy)) {
        decalagePerp *= 2; // ou 1.5 selon besoin
    }

    // décaler légèrement le long de la flèche et perpendiculairement
    int x = (int)(blocPoint.x + dx * 0.1 + nx * decalagePerp);
    int y = (int)(blocPoint.y + dy * 0.1 + ny * decalagePerp);

    g2.drawString(texte, x, y);
}




}