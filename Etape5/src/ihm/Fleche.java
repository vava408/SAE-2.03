package src.ihm;

import java.awt.*;
import javax.swing.JPanel;

public class Fleche extends JPanel 
{
    private PanelPrincipal panelPrincipal;

    private String nomClasseA;
    private String nomClasseB;
    private String multipliciteA;
    private String multipliciteB;

    public Fleche(PanelPrincipal panelPrincipal, String nomClasseA, String nomClasseB,
                  String multipliciteA, String multipliciteB) 
	{
        this.panelPrincipal = panelPrincipal;
        this.nomClasseA = nomClasseA;
        this.nomClasseB = nomClasseB;
        this.multipliciteA = multipliciteA;
        this.multipliciteB = multipliciteB;

        this.setOpaque(false);
    }

    public void maj() 
	{
        this.setBounds(0, 0, panelPrincipal.getWidth(), panelPrincipal.getHeight());
        this.repaint();
    }


    protected void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);

        Bloc a = panelPrincipal.getBloc(nomClasseA);
        Bloc b = panelPrincipal.getBloc(nomClasseB);

        if (a == null || b == null) {return;}

        Point[] pointsA = getAnchors(a);
        Point[] pointsB = getAnchors(b);

        // Trouver la paire la plus proche
        Point p1 = pointsA[0], p2 = pointsB[0];
        double minDist = p1.distance(p2);
        for (Point pa : pointsA) {
            for (Point pb : pointsB) {
                double dist = pa.distance(pb);
                if (dist < minDist) {
                    minDist = dist;
                    p1 = pa;
                    p2 = pb;
                }
            }
        }

        // Dessiner la ligne
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);

        // Dessiner la pointe
        drawArrowHead(g2, p1, p2);

        // Dessiner les multiplicités
        dessinerMultiplicite(g2, multipliciteA, p1);
        dessinerMultiplicite(g2, multipliciteB, p2);
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

    private void dessinerMultiplicite(Graphics2D g2, String texte, Point p)
    {
        if (texte == null || texte.isEmpty()) { return; }

        int decalage = 5;
        
        g2.drawString(texte, p.x + decalage, p.y - decalage);
    }


    private void drawArrowHead(Graphics2D g2, Point start, Point end) 
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
}

