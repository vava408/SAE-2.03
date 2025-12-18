package src.ihm;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import src.membres.Association;

/*------------------------------------------------------------------------*/
/*- Classe Fleche : Représente une flèche reliant deux blocs dans l'IHM   */
/*- Auteurs : Groupe 6                                                    */
/*- Date de création : 15/12/2025 10:30                                   */
/*------------------------------------------------------------------------*/

/**
 * Classe graphique permettant d'afficher une flèche entre deux blocs UML.
 * Elle gère les associations, héritages et implémentations.
 */
public class Fleche extends JPanel
{

	private PanelPrincipal panelPrincipal;

	private String         nomClasseA;
	private String         nomClasseB;

	private String         multipliciteA;
	private String         multipliciteB;

	/*------------------------- Méthodes publiques ------------------------*/

	/**
	 * Construit une flèche reliant deux classes UML.
	 *
	 * @param panelPrincipal panneau principal contenant les blocs
	 * @param nomClasseA     nom de la classe source
	 * @param nomClasseB     nom de la classe destination
	 * @param multipliciteA  multiplicité du côté A
	 * @param multipliciteB  multiplicité du côté B
	 */
	public Fleche( PanelPrincipal panelPrincipal,
				   String         nomClasseA    ,
				   String         nomClasseB    ,
				   String         multipliciteA ,
				   String         multipliciteB )
	{
		// Initialisation des attributs de la flèche
		this.panelPrincipal = panelPrincipal;
		this.nomClasseA     = nomClasseA;
		this.nomClasseB     = nomClasseB;
		this.multipliciteA  = multipliciteA;
		this.multipliciteB  = multipliciteB;

		// La flèche est transparente (ne masque pas le fond)
		this.setOpaque( false );
	}

	/**
	 * Met à jour la taille de la flèche et force son redessin.
	 */
	public void maj()
	{
		// La flèche occupe toute la surface du panel principal
		this.setBounds( 0,
						0,
						this.panelPrincipal.getWidth(),
						this.panelPrincipal.getHeight() );

		// Forcer le redessin
		this.repaint();
	}

	/**
	 * Retourne les rôles associés à une association.
	 *
	 * @param association association concernée
	 * @return liste des rôles
	 */
	public ArrayList<String> getRoles( Association association )
	{
		return this.panelPrincipal.gethMAttributsAssociations().get( association );
	}

	/*------------------------- Méthodes protégées ------------------------*/

	/**
	 * Dessine la flèche ainsi que ses éléments graphiques.
	 *
	 * @param g contexte graphique Swing
	 */
	protected void paintComponent( Graphics g )
	{
		super.paintComponent( g );

		Graphics2D g2 = ( Graphics2D ) g;

		// Améliore la qualité graphique (bords lissés)
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
							 RenderingHints.VALUE_ANTIALIAS_ON );

		g2.setStroke( new BasicStroke( 2 ) );
		g2.setColor ( Color.BLACK );

		Bloc blocA = this.panelPrincipal.getBloc( this.nomClasseA );
		Bloc blocB = this.panelPrincipal.getBloc( this.nomClasseB );

		if ( blocA == null || blocB == null ) { return; }

		// Points d'ancrage des deux blocs
		Point[] pointsA = this.getPointsAncrage( blocA );
		Point[] pointsB = this.getPointsAncrage( blocB );

		// Recherche des deux points les plus proches
		Point  p1      = pointsA[0];
		Point  p2      = pointsB[0];
		double minDist = p1.distance( p2 );

		for ( Point pa : pointsA )
		{
			for ( Point pb : pointsB )
			{
				double dist = pa.distance( pb );

				if ( dist < minDist )
				{
					minDist = dist;
					p1      = pa;
					p2      = pb;
				}
			}
		}

		// Dessin de la ligne et des éventuelles pointes
		this.dessinerLigneAvecPointes( g2, p1, p2, blocA, blocB );

		this.dessinerTexte( g2, this.multipliciteA, p1, p2, 15, -10 );
		this.dessinerTexte( g2, this.multipliciteB, p2, p1, 15, -10 );

		this.dessinerRolesAssociation( g2, p1, p2 );
	}

	/*------------------------- Méthodes privées --------------------------*/

	/**
	 * Retourne les points d'ancrage d'un bloc.
	 *
	 * @param bloc bloc concerné
	 * @return tableau de points d'ancrage
	 */
	private Point[] getPointsAncrage( Bloc bloc )
	{
		// Coordonnées et dimensions du bloc
		int x = bloc.getX();
		int y = bloc.getY();
		int w = bloc.getWidth();
		int h = bloc.getHeight();

		// Centres des quatre côtés du bloc
		return new Point[]
		{
			new Point( x + w / 2, y         ), // haut
			new Point( x + w / 2, y + h     ), // bas
			new Point( x        , y + h / 2 ), // gauche
			new Point( x + w    , y + h / 2 )  // droite
		};
	}

	/**
	 * Dessine la ligne principale et les pointes associées.
	 *
	 * @param g2 contexte graphique
	 * @param p1 point de départ
	 * @param p2 point d'arrivée
	 * @param a  bloc source
	 * @param b  bloc destination
	 */
	private void dessinerLigneAvecPointes( Graphics2D g2, Point p1, Point p2, Bloc a , Bloc b )
	{

		this.dessinerLigne( g2, p1.x, p1.y, p2.x, p2.y, a, b );

		// Cas héritage ou implémentation (pas une association)
		if ( this.panelPrincipal.getAssociation( this ) == null )
		{
			// Implémentation d'interface
			if ( this.panelPrincipal.getImple( a ) != null &&
				 this.panelPrincipal.getImple( a ).contains( this.panelPrincipal.getNomClasse( b ) ) )
			{
				this.dessinerTriangleBlanc( g2, p1, p2 );
				return;
			}

			// Héritage
			if ( this.panelPrincipal.getNomClasse( b )
					.equals( this.panelPrincipal.getHerit( a ) ) )
			{
				this.dessinerTriangleBlanc( g2, p1, p2 );
				return;
			}
		}

		// Cas d'une association unidirectionnelle
		if ( this.panelPrincipal.getAssociation( this ).estUnidirectionnelle() )
		{
			// Flèche orientée vers la multiplicité 1..1
			if ( this.multipliciteB.equals( "1..1" ) )
			{
				this.dessinerPointe( g2, p1, p2 );
			}
			else
			{
				this.dessinerPointe( g2, p2, p1 );
			}
		}
	}

	/**
	 * Dessine une ligne simple entre deux points.
	 *
	 * @param g2 contexte graphique
	 * @param x1 abscisse départ
	 * @param y1 ordonnée départ
	 * @param x2 abscisse arrivée
	 * @param y2 ordonnée arrivée
	 * @param a  bloc source
	 * @param b  bloc destination
	 */
	private void dessinerLigne( Graphics2D g2, int x1, int y1, int x2, int y2, Bloc a , Bloc b )
	{
		// Cas d'une implémentation : ligne en pointillés
		if ( this.panelPrincipal.getImple( a ) != null )
		{
			for ( String nomInterface : panelPrincipal.getImple( a ) )
			{
				if ( this.panelPrincipal.getNomClasse( b ).equals( nomInterface ) )
				{
					float[] dash = { 8f, 8f };

					g2.setStroke( new BasicStroke(
													2                     ,
													BasicStroke.CAP_BUTT  ,
													BasicStroke.JOIN_MITER,
													10.0f                 ,
													dash                  ,
													0.0f                ));
				}
			}
		}
		else
		{
			// Ligne pleine pour association ou héritage
			g2.setStroke( new BasicStroke( 2 ) );
		}

		// Dessin effectif de la ligne
		g2.drawLine( x1, y1, x2, y2 );
	}

	/**
	 * Calcule les points d'une forme (triangle ou flèche) à partir d'une orientation.
	 *
	 * @param start    point de départ
	 * @param end      point d'arrivée
	 * @param longueur longueur de la forme
	 * @param angle    angle d'ouverture
	 * @return tableau contenant les coordonnées [x1, y1, x2, y2]
	 */
	private int[] calculerPointsForme( Point start, Point end, int longueur, double angle )
	{
		// Calcul de l'orientation de la forme
		double angleDirection = Math.atan2( end.y - start.y, end.x - start.x );

		// Calcul des deux points de la forme
		int x1 = ( int ) ( end.x - longueur * Math.cos( angleDirection - angle ) );
		int y1 = ( int ) ( end.y - longueur * Math.sin( angleDirection - angle ) );
		int x2 = ( int ) ( end.x - longueur * Math.cos( angleDirection + angle ) );
		int y2 = ( int ) ( end.y - longueur * Math.sin( angleDirection + angle ) );

		return new int[] { x1, y1, x2, y2 };
	}

	/**
	 * Dessine une pointe de flèche directionnelle.
	 *
	 * @param g2    contexte graphique
	 * @param start point de départ
	 * @param end   point d'arrivée
	 */
	private void dessinerPointe( Graphics2D g2, Point start, Point end )
	{
		int    longueur    = 12;
		double anglePointe = Math.PI / 6;

		// Calcul des points de la pointe
		int[] points = this.calculerPointsForme( start, end, longueur, anglePointe );

		// Dessin de la pointe
		g2.drawLine( end.x, end.y, points[0], points[1] );
		g2.drawLine( end.x, end.y, points[2], points[3] );
	}

	/**
	 * Dessine un triangle blanc pour héritage ou implémentation.
	 *
	 * @param g2    contexte graphique
	 * @param start point de départ
	 * @param end   point d'arrivée
	 */
	private void dessinerTriangleBlanc( Graphics2D g2, Point start, Point end )
	{
		// Réinitialisation du trait (évite les pointillés)
		g2.setStroke( new BasicStroke( 2 ) );

		int    longueur      = 16;
		double angleTriangle = Math.PI / 6;

		// Calcul des sommets du triangle
		int[] points = this.calculerPointsForme( start, end, longueur, angleTriangle );

		Polygon triangle = new Polygon();
		triangle.addPoint( end.x, end.y );
		triangle.addPoint( points[0], points[1] );
		triangle.addPoint( points[2], points[3] );

		// Remplissage blanc
		g2.setColor( Color.WHITE );
		g2.fillPolygon( triangle );

		// Contour noir
		g2.setColor( Color.BLACK );
		g2.drawPolygon( triangle );
	}

	/**
	 * Dessine les rôles d'une association.
	 *
	 * @param g2 contexte graphique
	 * @param p1 point côté A
	 * @param p2 point côté B
	 */
	private void dessinerRolesAssociation( Graphics2D g2, Point p1, Point p2 )
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

		Bloc a = panelPrincipal.getBloc( nomClasseA );
		Bloc b = panelPrincipal.getBloc( nomClasseB );

		// rôle côté A
		dessinerTexte( g2, roleA, p1, p2, 15, 10 );

		// rôle côté B seulement si bidirectionnelle
		if ( ! asso.estUnidirectionnelle( ) )
		{
			dessinerTexte( g2, roleB, p2, p1, 15, 10 );
		}
	}

	/**
	 * Dessine un texte (rôle ou multiplicité) près d'une flèche.
	 *
	 * @param g2            contexte graphique
	 * @param texte         texte à afficher
	 * @param blocPoint     point de référence
	 * @param autrePoint    point opposé
	 * @param distance      distance au bloc
	 * @param decalagePerp  décalage perpendiculaire
	 */
	private void dessinerTexte( Graphics2D g2, String texte, Point blocPoint, Point autrePoint,
								int distance, int decalagePerp )
	{
		// Rien à dessiner si le texte est vide
		if ( texte == null || texte.isEmpty() ) { return; }
			
		// Vecteur directeur de la flèche
		double dx = autrePoint.x - blocPoint.x;
		double dy = autrePoint.y - blocPoint.y;
		double longueur = Math.sqrt( dx * dx + dy * dy );

		if ( longueur == 0 ) { return; }

		// Vecteur unitaire
		double ux = dx / longueur;
		double uy = dy / longueur;

		int signeDistance = 1;

		// Détermination du bloc concerné
		Bloc bloc = this.panelPrincipal.getBloc( this.nomClasseA );

		if ( !this.estSurBloc( bloc, blocPoint ) )
		{
			bloc = this.panelPrincipal.getBloc( this.nomClasseB );
		}

		int bx = bloc.getX();
		int by = bloc.getY();
		int bw = bloc.getWidth();
		int bh = bloc.getHeight();

		// Ajustement du sens du texte selon le côté du bloc
		if ( ( blocPoint.x == bx && ux > 0 )
		  || ( blocPoint.y == by && uy > 0 )
		  || ( blocPoint.x == bx + bw && ux < 0 )
		  || ( blocPoint.y == by + bh && uy < 0 ) )
		{
			signeDistance = -1;
		}
		
		// Bonus du côté gauche
		int bonus = 0;
		if ( blocPoint.x == bx )
		{
			bonus = 25;
		}

		// Déterminer le décalage supplémentaire pour ne pas chevaucher la flèche
		int decalageTexte = decalagePerp;

		// Placement du texte selon l'orientation de la flèche
		if ( Math.abs( dx ) > Math.abs( dy ) )
		{
			// Flèche horizontale → texte au-dessus/dessous
			if ( decalagePerp < 0 ) decalageTexte -= 10;  // côté "A"
			if ( decalagePerp > 0 ) decalageTexte += 10;  // côté "B"

			int x = ( int ) ( blocPoint.x + ux * ( distance + bonus ) * signeDistance );
			int y = ( int ) ( blocPoint.y + uy * ( distance + bonus ) * signeDistance + decalageTexte );

			g2.drawString( texte, x, y );
		}
		else
		{
			// Flèche verticale → texte à gauche/droite
			if ( decalagePerp < 0 ) decalageTexte -= 25;  // décale encore plus côté gauche
			if ( decalagePerp > 0 ) decalageTexte += 0;   // côté droit inchangé

			int x = ( int ) ( blocPoint.x + ux * ( distance + bonus ) * signeDistance + decalageTexte );
			int y = ( int ) ( blocPoint.y + uy * ( distance + bonus ) * signeDistance );

			g2.drawString( texte, x, y );
		}
	}

	/**
	 * Vérifie si un point est contenu dans un bloc.
	 *
	 * @param bloc bloc à tester
	 * @param p    point à vérifier
	 * @return vrai si le point est dans le bloc
	 */
	private boolean estSurBloc( Bloc bloc, Point p )
	{
		// Vérifie si le point est à l'intérieur du rectangle du bloc
		return p.x >= bloc.getX()
			&& p.x <= bloc.getX() + bloc.getWidth()
			&& p.y >= bloc.getY()
			&& p.y <= bloc.getY() + bloc.getHeight();
	}
}