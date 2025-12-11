package src.membres;

/*-------------------------------------------------------------------*/
/*- Classe Attribut : Gère un attribut d’une classe UML.             */
/*- Etape 1                                                          */
/*- Groupe 6                                                         */
/*- Date de création : 08/12/2025 10:30                              */
/*-------------------------------------------------------------------*/

public class Attribut
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs préfixés                           */
	/*--------------------------------------------------------------*/
	private int    mAttributId;
	private String mNom;
	private String mType;
	private String mVisibilite;
	private String mInstance;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise un attribut UML                    */
	/*--------------------------------------------------------------*/
	public Attribut(int attributId, String nom, String type, String visibilite, String instance)
	{
		mAttributId = attributId;
		mNom        = nom;
		mType       = type;
		mVisibilite = visibilite;
		mInstance   = instance;
	}

	/*--------------------------------------------------------------*/
	/* Accesseur : retourne les différents attribut                 */
	/*--------------------------------------------------------------*/
	public int    getAttributId() { return mAttributId;}

	public String getNom()        { return mNom       ;}

	public String getType()       { return mType      ;}

	public String getVisibilite() { return mVisibilite;}

	public String getInstance()   { return mInstance  ;}


	/*--------------------------------------------------------------*/
	/* Retourne une chaîne décrivant l’objet Attribut               */
	/*--------------------------------------------------------------*/
	public String toString()
	{
		return String.format("attribut : %d nom : %-8s type : %-8s visibilité : %s portée : %s",
									mAttributId,  mNom,      mType,      mVisibilite,    mInstance   );
	}
}