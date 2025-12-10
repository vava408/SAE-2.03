package src.membres.Etape3Package.src.membres;
public class Attribut {

    private int     attributId;
    private String  nom;
    private String  type;
    private String  visibilite;
    private boolean estStatic;
    private boolean estFinal;

    public Attribut(int attributId, String nom, String type, String visibilite, boolean estStatic, boolean estFinal) 
	{

        this.attributId = attributId;
        this.nom = nom;
        this.type = type;
        this.visibilite = visibilite;
        this.estStatic = estStatic;
        this.estFinal = estFinal;
    }

    public int getAttributId()    { return attributId; }
    public String getNom()        { return nom; }
    public String getType()       { return type; }
    public String getVisibilite() { return visibilite; }
    public boolean isStatic()     { return estStatic; }
    public boolean isFinal()      { return estFinal; }

    @Override
    public String toString() 
	{
        StringBuilder sb = new StringBuilder();

        sb.append("attribut : ").append(attributId)
          .append(" nom : ").append(nom)
          .append(" type : ").append(type)
          .append(" visibilité : ").append(visibilite)
          .append(" static : ").append(estStatic)
          .append(" final : ").append(estFinal);

        return sb.toString();
    }
}
