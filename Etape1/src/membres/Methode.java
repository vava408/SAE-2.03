package src.membres;

/*--------------------------------------------------------------------*/
/*- Classe Methode : gère une méthode d’une classe UML.              */
/*- Exercice 5                                                       */
/*- Groupe 6                                                         */
/*- Date de création : 08/12/2025 10:45                              */
/*--------------------------------------------------------------------*/

public class Methode
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs préfixés                           */
	/*--------------------------------------------------------------*/
	private String      mNom;
	private String      mVisibilite;
	private String      mRetour;
	private Parametre[] mTabParametre;

	private static final String[] TAB_TYPES_RETOUR    =
		{ "void", "int",    "double", "float",    "boolean", "char",      "String" };
	private static final String[] TAB_TYPES_RETOUR_FR =
		{ "vide", "entier", "double", "flottant", "booléen", "caractère", "chaîne" };

	/*--------------------------------------------------------------*/
	/* Constructeur : initialise une méthode UML                    */
	/*--------------------------------------------------------------*/
	public Methode(String nom, String visibilite, String retour, Parametre[] tabParametre)
	{
		mNom          = nom;
		mVisibilite   = visibilite;
		mRetour       = retour;
		mTabParametre = tabParametre;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs                                                   */
	/*--------------------------------------------------------------*/
	public String      getNom()        { return mNom          ;}
	public String      getVisibilite() { return mVisibilite   ;}
	public String      getRetour()     { return mRetour       ;}
	public Parametre[] getParametre()  { return mTabParametre ;}

	/*--------------------------------------------------------------*/
	/* Modificateurs                                                */
	/*--------------------------------------------------------------*/
	public void setNom       (String      nom)           { mNom = nom                   ;}
	public void setVisibilite(String      visibilite)    { mVisibilite = visibilite     ;}
	public void setRetour    (String      retour)        { mRetour = retour             ;}
	public void setParametre (Parametre[] tabParametre)  { mTabParametre = tabParametre ;}

	/*--------------------------------------------------------------*/
	/* Retourne une chaîne décrivant l’objet Methode                */
	/*--------------------------------------------------------------*/
	public String toString()
	{
		String sRet = "";

		sRet += "méthode : "     + mNom;
		sRet += " visibilité : " + mVisibilite;

		if (mRetour != null)
		{
			for (int i = 0; i < TAB_TYPES_RETOUR.length; i++)
			{
				if (mRetour.equals(TAB_TYPES_RETOUR[i]))
				{
					mRetour = TAB_TYPES_RETOUR_FR[i];
				}
			}

			sRet += " type de retour : " + mRetour;
		}

		sRet += "\nparamètres : ";

		if (mTabParametre == null || mTabParametre.length == 0)
		{
			sRet += "aucun\n";
			return sRet;
		}

		for (int i = 0; i < mTabParametre.length; i++)
		{
			sRet += "\n" + String.format("%14s", mTabParametre[i].toString());
		}

		sRet += "\n";
		return sRet;
	}
}