package src.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import src.metier.LireFichier;

public class Bloc extends JPanel
{
    private LireFichier lireFichier;

    public Bloc ( LireFichier lireFichier )
    {
        this.lireFichier = lireFichier;
        this.setBackground( new Color( 250, 250, 250 ) );
        this.maj();

        GereSouris gs = new GereSouris();
        this.addMouseListener( gs );
        this.addMouseMotionListener( gs );
    }

    public void maj ()
    {
        int h = 40
                + lireFichier.getListeAttributs().size() * 20
                + lireFichier.getListeMethodes().size()  * 20;

        this.setSize( 200, h );
        this.setPreferredSize( getSize() );
        this.repaint();
    }

    @Override
    protected void paintComponent ( Graphics g )
    {
        super.paintComponent( g );

        int width  = getWidth();
        int height = getHeight();

        g.setColor( Color.BLACK );
        g.drawRect( 0, 0, width - 1, height - 1 );

        int y = 20;
        g.setFont( new Font( "Arial", Font.BOLD, 14 ) );
        FontMetrics fm = g.getFontMetrics();

        if ( ! lireFichier.getMotCle().equals( "class" ) )
        {
            String stereo = "<< " + lireFichier.getMotCle() + " >>";
            int xStereo = ( width - fm.stringWidth( stereo ) ) / 2;
            g.drawString( stereo, xStereo, y );
            y += 20;
        }

        String nomClasse = lireFichier.getNomClasse();
        int xNom = ( width - fm.stringWidth( nomClasse ) ) / 2;
        g.drawString( nomClasse, xNom, y );
        y += 20;

        g.drawLine( 0, y, width, y );

        g.setFont( new Font( "Arial", Font.PLAIN, 13 ) );

        for ( var att : lireFichier.getListeAttributs() )
        {
            y += 18;
            g.drawString( att.toString(), 10, y );
        }

        y += 10;
        g.drawLine( 0, y, width, y );

        for ( var m : lireFichier.getListeMethodes() )
        {
            y += 18;
            g.drawString( m.toString(), 10, y );
        }
    }

    // --- Classe interne privée pour gérer le drag ---
    private class GereSouris extends MouseAdapter
    {
        private Point mousePressedLocation;

        @Override
        public void mousePressed ( MouseEvent e )
        {
            mousePressedLocation = e.getPoint();
        }

        @Override
        public void mouseDragged ( MouseEvent e )
        {
            int dx = e.getX() - mousePressedLocation.x;
            int dy = e.getY() - mousePressedLocation.y;

            setLocation( getX() + dx, getY() + dy );
            getParent().repaint();
        }
    }
}
