import java.util.ArrayList;

/*-----------------------------------------------------------------------*/
/*- Classe Test : regroupe une liste de disques et un point associé      */
/*- Etape 4                                                              */
/*- Groupe 6                                                             */
/*- Date de création : 11/12/2025  14h                                   */
/*-----------------------------------------------------------------------*/

public class Test extends Object
{
	/*--------------------------------------------------------------*/
	/* Attributs                                                    */
	/*--------------------------------------------------------------*/
	private Point d;                    // un point associé
	private ArrayList<Disque> lstPoints; // liste de disques

	/*--------------------------------------------------------------*/
	/* Constructeur                                                 */
	/*--------------------------------------------------------------*/
	public Test(ArrayList<Disque> lstPoints, Point d)
	{
		this.lstPoints = lstPoints;
		this.d         = d;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs : retourne les attributs                          */
	/*--------------------------------------------------------------*/
	public ArrayList<Disque> getLstPoints() { return this.lstPoints; }
	public Point getDisque()                { return this.d;         }
}
