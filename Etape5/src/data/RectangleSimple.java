/*------------------------------------------------------------------------*/
/*- Record RectangleSimple : Représente un rectangle simple immuable      */
/*- Groupe 6                                                              */
/*- Date de création : 17/12/2025                                         */
/*------------------------------------------------------------------------*/

/**
 * Record représentant un rectangle simple avec des dimensions immuables.
 *
 * Un RectangleSimple possède une largeur et une hauteur, et fournit des
 * méthodes pour calculer son aire et son périmètre.
 */
public record RectangleSimple(int largeur, int hauteur)
{
    /**
     * Calcule l'aire du rectangle.
     *
     * @return aire du rectangle
     */
    public int aire()
    {
        return largeur * hauteur;
    }

    /**
     * Calcule le périmètre du rectangle.
     *
     * @return périmètre du rectangle
     */
    public int perimetre()
    {
        return 2 * (largeur + hauteur);
    }
}