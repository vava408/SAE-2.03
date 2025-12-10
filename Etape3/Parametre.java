public class Parametre 
{
	private int        id;
	private String     nom;
	private String     type;

	//constructeur
	public Parametre(int numParametre, String nom, String type) 
	{
		this.id   = numParametre;
		this.nom  = nom;
		this.type = type;
	}

	//retourne le nom
	public String getNom() 
	{
		return this.nom;
	}

	//retourne le type
	public String getType() 
	{
		return this.type;
	}

	//retourne l'id
	public int getId() 
	{
		return this.id;
	}

	//modifie le nom
	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	//modifie le type
	public void setType(String type) 
	{
		this.type = type;
	}

	//modifie l'id
	public int setId() 
	{
		return this.id;
	}

	//affichage textuel du paramètre
	public String toString() 
	{
		String sb = "";
		sb += "p" + getId() + " :" + getNom();
		sb += " type : " + getType();
		return sb;
	}

}
