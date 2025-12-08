public class Parametre 
{
    private static int nbParametre = 0;
	private int        id;
	private String     nom;
    private String     type;

    public Parametre(String nom, String type) 
	{
		this.id   = ++nbParametre;
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

}
