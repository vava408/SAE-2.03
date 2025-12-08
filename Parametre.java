public class Parametre 
{
    private int    parametre;
	private String nom;
    private String type;

    public Parametre(String nom, String type) 
	{
        this.nom = nom;
        this.type = type;
    }

    public String getNom() 
	{
        return nom;
    }

    public String getType() 
	{
        return type;
    }

    public void setNom(String nom) 
	{
        this.nom = nom;
    }

    public void setType(String type) 
	{
        this.type = type;
    }


}
