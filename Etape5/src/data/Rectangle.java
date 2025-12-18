/*------------------------------------------------------------------------*/
/*- Classe Rectangle : Représente un rectangle dans un plan 2D.         */
/*- Groupe 6                                                              */
/*- Date de création : 17/12/2025                                         */
/*------------------------------------------------------------------------*/

/**
 * Classe représentant un rectangle, spécialisation de FormeGeometrique.
 *
 * Un rectangle possède une largeur et une hauteur. Cette classe fournit
 * des méthodes pour obtenir ses dimensions ainsi que pour calculer son
 * aire et son périmètre.
 */
public class Rectangle extends FormeGeometrique
{
    /** Largeur du rectangle */
    protected int largeur;

    /** Hauteur du rectangle */
    protected int hauteur;

    /**
     * Construit un rectangle à la position donnée avec la largeur et la hauteur spécifiées.
     *
     * @param position position du rectangle (coin supérieur gauche ou point de référence selon la classe Point)
     * @param largeur  largeur du rectangle en unités entières (doit être positive)
     * @param hauteur  hauteur du rectangle en unités entières (doit être positive)
     */
    public Rectangle(Point position, int largeur, int hauteur)
    {
        super(position);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /**
     * Retourne la largeur du rectangle.
     *
     * @return largeur du rectangle
     */
    public int getLargeur()
    {
        return largeur;
    }

    /**
     * Retourne la hauteur du rectangle.
     *
     * @return hauteur du rectangle
     */
    public int getHauteur()
    {
        return hauteur;
    }

    /**
     * Calcule l'aire du rectangle.
     *
     * @return aire du rectangle
     */
    @Override
    public double aire()
    {
        return largeur * hauteur;
    }

    /**
     * Calcule le périmètre du rectangle.
     *
     * @return périmètre du rectangle
     */
    @Override
    public double perimetre()
    {
        return 2 * (largeur + hauteur);
    }
}