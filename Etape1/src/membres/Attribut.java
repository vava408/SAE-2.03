package src.membres;

public class Attribut
{
	private int attributId;
	private String nom;
	private String type;
	private String visibilite;
	private String instance;

	public Attribut(int attributId, String nom, String type, String visibilite, String instance)
	{
		this.attributId = attributId;
		this.nom = nom;
		this.type = type;
		this.visibilite = visibilite;
		this.instance = instance;
	}

	public int getAttributId()
	{
		return attributId;
	}

	public String getNom()
	{
		return nom;
	}

	public String getType()
	{
		return type;
	}

	public String getVisibilite()
	{
		return visibilite;
	}

	public String getInstance()
	{
		return instance;
	}

	public String toString() 
	{
		return String.format("attribut : %d nom : %-8s type : %-8s visibilité : %s portée : %s",
				attributId, nom, type, visibilite, instance); 
	}
}
