package src.membres.Etape3Package.src.membres;
public class Parametre 
{
	private int        id;
	private String     nom;
    private String     type;

    public Parametre(int numParametre, String nom, String type) 
	{
		this.id   = numParametre;
		this.nom  = nom;
        this.type = type;
    }

    public String getNom() 
	{
        return this.nom;
    }

    public String getType() 
	{
        return this.type;
    }

	public int getId() 
	{
        return this.id;
    }

    public void setNom(String nom) 
	{
        this.nom = nom;
    }

    public void setType(String type) 
	{
        this.type = type;
    }

	public int setId() 
	{
        return this.id;
    }

	public String toString() 
	{
        String sb = "";
        sb += "p" + getId() + " :" + getNom();
        sb += " type : " + getType();
        return sb;
    }

}
