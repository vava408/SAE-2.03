public class Attriubut 
{
	private int    attributId;
	private String nom;
	private String type;
	private String visibilite;
	private String instance;

	public Attriubut(int attributId, String nom, String type, String visibilite, String instance ) 
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
		return "Attribut [attributId=" + attributId + ", nom=" + nom + ", type=" + type + ", visibilite=" + visibilite
				+ ", instance=" + instance + "]";
	}
}
