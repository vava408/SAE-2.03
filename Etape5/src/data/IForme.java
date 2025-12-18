/*------------------------------------------------------------------------*/
/*- Interface IForme : définit le comportement d'une forme géométrique  */
/*- Groupe 6                                                              */
/*- Date de création : 17/12/2025                                         */
/*------------------------------------------------------------------------*/

/**
 * Interface définissant le comportement commun à toutes les formes
 * géométriques.
 *
 * Toute classe implémentant cette interface doit fournir des méthodes
 * pour calculer l'aire et le périmètre de la forme.
 */
public interface IForme
{
    /**
     * Calcule l'aire de la forme.
     *
     * @return aire de la forme
     */
    public double aire();

    /**
     * Calcule le périmètre de la forme.
     *
     * @return périmètre de la forme
     */
    public double perimetre();
}