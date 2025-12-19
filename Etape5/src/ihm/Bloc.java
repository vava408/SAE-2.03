package src.ihm;

import java.awt.BasicStroke;
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

/*
 * Classe représentant un bloc visuel d'une classe UML dans le diagramme
 * Affiche le nom de la classe, ses attributs et méthodes avec gestion du drag-and-drop
 * 
 * Exercice    : Génération de diagrammes UML
 * Auteurs     : Groupe 6
 * Date/Heure  : 19/12/2024 15:30
 */

public class Bloc extends JPanel
{
    private PanelPrincipal panelPrincipal;
    private String         nomClasse;
    private int            largeurMax;
    private int            hauteurMax;
    private boolean        estClique;

    /**
     * Constructeur de la classe Bloc
     * Initialise un bloc représentant une classe UML avec gestion des événements souris
     */
    public Bloc( PanelPrincipal panelPrincipal, String nomClasse )
    {
        GereSouris gs;

        this.estClique      = false;
        this.panelPrincipal = panelPrincipal;
        this.nomClasse      = nomClasse;
        this.largeurMax     = 0;
        this.hauteurMax     = 0;

        this.setBackground( new Color( 250, 250, 250 ) );

        gs = new GereSouris();
        this.addMouseListener( gs );
        this.addMouseMotionListener( gs );
    }

    /**
     * Retourne le nom de la classe représentée par ce bloc
     */
    public String getName()
    {
        return this.nomClasse;
    }

    /**
     * Met à jour les dimensions du bloc selon son contenu et son état d'affichage
     */
    public void maj()
    {
        /* Calcul des dimensions selon le contenu */
        if ( this.panelPrincipal.nomEstDansRepertoire( this ) )
        {
            this.largeurMax = this.panelPrincipal.getLargeurMax( this );

            if ( this.estClique )
            {
                this.hauteurMax = this.panelPrincipal.getTaille( this, true );
            }
            else
            {
                this.hauteurMax = this.panelPrincipal.getTaille( this, false );
            }
        }
        else
        {
            this.largeurMax = 10 * this.nomClasse.length();
            this.hauteurMax = 100;
        }

        /* Application de la nouvelle taille */
        this.setSize( this.largeurMax, this.hauteurMax );
        this.setPreferredSize( this.getSize() );
        this.repaint();
    }

    /**
     * Dessine le bloc avec son contenu (nom, attributs, méthodes)
     */
    protected void paintComponent( Graphics g )
    {
        FontMetrics fm;
        Graphics2D  g2;
        String      stereoType;
        int         yCourant;
        int         margeHorizontale;
        int         margeVerticalNom;
        int         margeVerticalAttributs;
        int         hauteurLigneAttribut;
        int         hauteurLigneMethode;
        int         hauteurNom;
        int         hauteurAttributs;
        int         yTexte;
        int         xStereotype;
        int         xNomClasse;

        super.paintComponent( g );

        g2 = (Graphics2D) g;
        g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON );

        yCourant               = 0;
        margeHorizontale       = 15;
        margeVerticalNom       = 40;
        margeVerticalAttributs = 20;
        hauteurLigneAttribut   = 18;
        hauteurLigneMethode    = 18;

        /* Calcul des hauteurs des sections */
        hauteurNom       = margeVerticalNom;
        hauteurAttributs = margeVerticalAttributs;

        if ( this.panelPrincipal.getListeAttributs( this ) != null )
        {
            hauteurAttributs += this.panelPrincipal.getListeAttributs( this ).size() * 
                                hauteurLigneAttribut;
        }

        /* Dessin du contour global */
        if ( this.panelPrincipal.nomEstDansRepertoire( this ) )
        {
            g2.setColor( Color.BLACK );
        }
        else
        {
            g2.setColor( Color.GRAY );
        }

        g2.setStroke( new BasicStroke( 1.0f ) );
        g2.drawRect( 0, 0, this.largeurMax - 1, this.hauteurMax - 1 );

        /* Dessin du stéréotype et du nom de la classe */
        g2.setFont( new Font( "Arial", Font.BOLD, 12 ) );
        fm     = g2.getFontMetrics();
        yTexte = 20;

        stereoType = "";
        
        if ( this.panelPrincipal.getMotCle( this ) == null )
        {
            stereoType  = "<< interface >>";
            xStereotype = ( this.largeurMax - fm.stringWidth( stereoType ) ) / 2;
            g2.drawString( stereoType, xStereotype, yTexte );
            yTexte += 18;
        }
        else
        {
            if ( !this.panelPrincipal.getMotCle( this ).equals( "class" ) )
            {
                stereoType  = "<< " + this.panelPrincipal.getMotCle( this ) + " >>";
                xStereotype = ( this.largeurMax - fm.stringWidth( stereoType ) ) / 2;
                g2.drawString( stereoType, xStereotype, yTexte );
                yTexte += 18;
            }
        }

        xNomClasse = ( this.largeurMax - fm.stringWidth( this.nomClasse ) ) / 2;
        g2.drawString( this.nomClasse, xNomClasse, yTexte );

        /* Ligne séparatrice nom/attributs */
        yCourant = hauteurNom;
        g2.drawLine( 0, yCourant, this.largeurMax, yCourant );

        /* Dessin des attributs selon l'état */
        g2.setFont( new Font( "Monospaced", Font.PLAIN, 10 ) );
        yTexte = yCourant + hauteurLigneAttribut;

        if ( this.estClique )
        {
            this.dessinerAttributsComplet( g2, yTexte, hauteurLigneAttribut, 
                                          margeHorizontale );
        }
        else
        {
            this.dessinerAttributsReduit( g2, yTexte, hauteurLigneAttribut, 
                                         margeHorizontale );
        }

        /* Ligne séparatrice attributs/méthodes */
        yCourant += hauteurAttributs;
        g2.drawLine( 0, yCourant, this.largeurMax, yCourant );

        /* Dessin des méthodes selon l'état */
        yTexte = yCourant + 16;

        if ( this.estClique )
        {
            this.dessinerMethodesComplet( g2, yTexte, hauteurLigneMethode, 
                                         margeHorizontale );
        }
        else
        {
            this.dessinerMethodesReduit( g2, yTexte, hauteurLigneMethode, 
                                        margeHorizontale );
        }
    }

    /**
     * Dessine tous les attributs de la classe en mode complet
     */
    private void dessinerAttributsComplet( Graphics2D g2, int yTexte, 
                                          int hauteurLigne, int marge )
    {
        String affichage;
        int    largeurTexte;

        if ( this.panelPrincipal.getListeAttributs( this ) != null )
        {
            for ( Attribut a : this.panelPrincipal.getListeAttributs( this ) )
            {
                affichage = this.panelPrincipal.afficherAttribut( a );
                g2.drawString( affichage, marge / 2, yTexte );

                /* Soulignement pour les attributs static */
                if ( a.estStatic() )
                {
                    largeurTexte = g2.getFontMetrics().stringWidth( affichage );
                    g2.drawLine( marge / 2, yTexte + 1, marge / 2 + largeurTexte, 
                                yTexte + 1 );
                }

                yTexte += hauteurLigne;
            }
        }
    }

    /**
     * Dessine les 3 premiers attributs avec ellipse si nécessaire
     */
    private void dessinerAttributsReduit( Graphics2D g2, int yTexte, 
                                         int hauteurLigne, int marge )
    {
        String affichage;
        int    cpt;
        int    largeurTexte;

        cpt = 0;

        if ( this.panelPrincipal.getListeAttributs( this ) != null )
        {
            /* Parcours des 3 premiers attributs */
            for ( Attribut a : this.panelPrincipal.getListeAttributs( this ) )
            {
                if ( cpt > 2 )
                    break;

                cpt++;

                affichage = this.panelPrincipal.afficherAttribut( a );
                g2.drawString( affichage, marge / 2, yTexte );

                /* Soulignement pour les attributs static */
                if ( a.estStatic() )
                {
                    largeurTexte = g2.getFontMetrics().stringWidth( affichage );
                    g2.drawLine( marge / 2, yTexte + 1, marge / 2 + largeurTexte, 
                                yTexte + 1 );
                }

                yTexte += hauteurLigne;
            }

            /* Ajout des points de suspension si plus de 3 attributs */
            if ( cpt > 2 )
            {
                g2.drawString( "...", marge / 2, yTexte );
            }
        }
    }

    /**
     * Dessine toutes les méthodes de la classe en mode complet
     */
    private void dessinerMethodesComplet( Graphics2D g2, int yTexte, 
                                         int hauteurLigne, int marge )
    {
        String affichage;
        int    largeurTexte;

        if ( this.panelPrincipal.getListeMethodes( this ) != null )
        {
            for ( Methode m : this.panelPrincipal.getListeMethodes( this ) )
            {
                affichage = this.panelPrincipal.afficherMethode( m, true );
                g2.drawString( affichage, marge / 2, yTexte );

                /* Soulignement pour les méthodes static */
                if ( m.isStatic() )
                {
                    largeurTexte = g2.getFontMetrics().stringWidth( affichage );
                    g2.drawLine( marge / 2, yTexte + 1, marge / 2 + largeurTexte, 
                                yTexte + 1 );
                }

                yTexte += hauteurLigne;
            }
        }
    }

    /**
     * Dessine les 3 premières méthodes avec ellipse si nécessaire
     */
    private void dessinerMethodesReduit( Graphics2D g2, int yTexte, 
                                        int hauteurLigne, int marge )
    {
        String affichage;
        int    cpt;
        int    largeurTexte;

        cpt = 0;

        if ( this.panelPrincipal.getListeMethodes( this ) != null )
        {
            /* Parcours des 3 premières méthodes */
            for ( Methode m : this.panelPrincipal.getListeMethodes( this ) )
            {
                if ( cpt > 2 )
                    break;

                cpt++;

                affichage = this.panelPrincipal.afficherMethode( m, false );
                g2.drawString( affichage, marge / 2, yTexte );

                /* Soulignement pour les méthodes static */
                if ( m.isStatic() )
                {
                    largeurTexte = g2.getFontMetrics().stringWidth( affichage );
                    g2.drawLine( marge / 2, yTexte + 1, marge / 2 + largeurTexte, 
                                yTexte + 1 );
                }

                yTexte += hauteurLigne;
            }

            /* Ajout des points de suspension si plus de 3 méthodes */
            if ( cpt > 2 )
            {
                g2.drawString( "...", marge / 2, yTexte );
            }
        }
    }

    /**
     * Classe interne gérant les événements souris pour le drag-and-drop et le clic
     */
    private class GereSouris extends MouseAdapter
    {
        private Point coordonneePoint;

        /**
         * Gère le clic souris (clic droit pour affichage complet/réduit)
         */
        public void mousePressed( MouseEvent e )
        {
            this.coordonneePoint = e.getPoint();

            /* Gestion du clic droit pour basculer l'affichage */
            if ( e.getButton() == 3 )
            {
                estClique = true;
                repaint();
            }
        }

        /**
         * Gère le déplacement du bloc par drag-and-drop
         */
        public void mouseDragged( MouseEvent e )
        {
            int dx;
            int dy;
            int nouveauX;
            int nouveauY;

            if ( estClique )
                return;

            dx = e.getX() - this.coordonneePoint.x;
            dy = e.getY() - this.coordonneePoint.y;

            nouveauX = getX() + dx;
            nouveauY = getY() + dy;

            /* Empêche les coordonnées négatives */
            if ( nouveauX < 0 ) nouveauX = 0;
            if ( nouveauY < 0 ) nouveauY = 0;

            /* Déplacement visuel du bloc */
            setLocation( nouveauX, nouveauY );

            /* Sauvegarde de la position dans le modèle */
            Bloc.this.panelPrincipal.setPosition( Bloc.this, nouveauX, nouveauY );

            /* Mise à jour des flèches de relations */
            getParent().repaint();
        }

        /**
         * Gère le relâchement de la souris pour revenir en mode réduit
         */
        public void mouseReleased( MouseEvent e )
        {
            estClique = false;
            repaint();
        }
    }
}