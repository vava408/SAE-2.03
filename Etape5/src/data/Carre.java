/*------------------------------------------------------------------------*/
/*- Classe Carre : Représente un carré, figure géométrique avec côtés     */
/*- égaux.                                                                */
/*- Date de création : 17/12/2025                                         */
/*------------------------------------------------------------------------*/

 /**
  * Représente un carré, spécialisation de Rectangle où la largeur et la
  * hauteur sont égales.
  *
  * Cette classe conserve une référence à un objet Test (usage spécifique
  * au projet) et initialise le rectangle parent avec des côtés égaux.
  */
public class Carre extends Rectangle
{
    private Test test;

     /**
      * Construit un carré à la position donnée avec la longueur de côté fournie.
      *
      * @param test     instance de Test associée au carré (usage spécifique au projet)
      * @param position position du carré (coin supérieur gauche ou point de référence,
      *                 selon la convention de la classe Point)
      * @param cote     longueur du côté du carré en unités entières (doit être positive)
      */
    public Carre( Test test, Point position, int cote )
    {
        super( position, cote, cote );

        this.test = test;
    }
}