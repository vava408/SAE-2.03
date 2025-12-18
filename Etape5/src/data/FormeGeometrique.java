/*------------------------------------------------------------------------*/
/*- Classe FormeGeometrique : Classe abstraite représentant une           */
/*- figure géométrique de base avec une position dans le plan.            */
/*- Groupe 6                                                              */
/*- Date de création : 17/12/2025                                        */
/*------------------------------------------------------------------------*/

/**
 * Classe abstraite représentant une forme géométrique.
 * 
 * Cette classe définit les propriétés communes à toutes les formes,
 * notamment la position dans le plan (représentée par un objet Point) et
 * la possibilité de se déplacer.
 *
 * Elle implémente l'interface IForme pour garantir que toutes les formes
 * respectent un certain contrat (méthodes à définir dans IForme).
 */
public abstract class FormeGeometrique implements IForme
{
    /** Position de la forme dans le plan. */
    protected Point position;

    /**
     * Construit une forme géométrique à la position spécifiée.
     *
     * @param position position initiale de la forme (coin supérieur gauche
     *                 ou point de référence selon la convention de la classe Point)
     */
    public FormeGeometrique(Point position)
    {
        this.position = position;
    }

    /**
     * Retourne la position actuelle de la forme.
     *
     * @return position de la forme
     */
    public Point getPosition()
    {
        return position;
    }

    /**
     * Déplace la forme selon les décalages spécifiés.
     *
     * @param dx décalage horizontal
     * @param dy décalage vertical
     */
    public void deplacer(int dx, int dy)
    {
        position.deplacer(dx, dy);
    }
}
