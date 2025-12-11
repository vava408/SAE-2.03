package src.membres;

/*--------------------------------------------------------------------*/
/*- Classe Parametre : représente un paramètre de méthode UML.        */
/*- Exercice 5                                                        */
/*- Groupe 6                                                          */
/*- Date de création : 08/12/2025 11:50                               */
/*--------------------------------------------------------------------*/

public class Parametre
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs préfixés                           */
	/*--------------------------------------------------------------*/
	private int    mId;
	private String mNom;
	private String mType;

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise un paramètre                       */
	/*--------------------------------------------------------------*/
	public Parametre(int id, String nom, String type)
	{
		mId   = id;
		mNom  = nom;
		mType = type;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs                                                   */
	/*--------------------------------------------------------------*/
	public int    getId()   { return mId   ;}
	public String getNom()  { return mNom  ;}
	public String getType() { return mType ;}

	/*--------------------------------------------------------------*/
	/* Modificateurs                                                */
	/*--------------------------------------------------------------*/
	public void setId  (int id)       { mId = id    ;}
	public void setNom (String nom)   { mNom = nom  ;}
	public void setType(String type)  { mType = type;}

	/*--------------------------------------------------------------*/
	/* Retourne une chaîne décrivant l’objet Parametre              */
	/*--------------------------------------------------------------*/
	public String toString()
	{
		return "p" + mId + " : " + mNom + " type : " + mType;
	}