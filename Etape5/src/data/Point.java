/*------------------------------------------------------------------------*/
/*- Classe Point : Représente un point dans un plan 2D avec des          */
/*- coordonnées entières.                                                */
/*- Groupe 6                                                              */
/*- Date de création : 17/12/2025                                         */
/*------------------------------------------------------------------------*/

/**
 * Classe représentant un point dans un plan à deux dimensions.
 * 
 * Un point possède des coordonnées entières (x, y) et peut être déplacé
 * dans le plan. La classe implémente Serializable pour permettre la
 * sauvegarde et la transmission d'objets Point.
 */
public class Point implements Serializable
{
    /** Coordonnée horizontale du point */
    private int x;

    /** Coordonnée verticale du point */
    private int y;

    /**
     * Construit un point avec les coordonnées spécifiées.
     *
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la coordonnée horizontale du point.
     *
     * @return coordonnée x
     */
    public int getX()
    {
        return x;
    }

    /**
     * Retourne la coordonnée verticale du point.
     *
     * @return coordonnée y
     */
    public int getY()
    {
        return y;
    }

    /**
     * Modifie la coordonnée horizontale du point.
     *
     * @param x nouvelle coordonnée x
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * Modifie la coordonnée verticale du point.
     *
     * @param y nouvelle coordonnée y
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * Déplace le point selon les décalages spécifiés.
     *
     * @param dx décalage horizontal
     * @param dy décalage vertical
     */
    public void deplacer(int dx, int dy)
    {
        this.x += dx;
        this.y += dy;
    }
}