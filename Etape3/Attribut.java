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
        this.nom        = nom;
        this.type       = type;
        this.visibilite = visibilite;
        this.estStatic  = estStatic;
        this.estFinal   = estFinal;
    }

    public int getAttributId()    { return attributId; }
    public String getNom()        { return nom       ; }
    public String getType()       { return type      ; }
    public String getVisibilite() { return visibilite; }
    public boolean isStatic()     { return estStatic ; }
    public boolean isFinal()      { return estFinal  ; }

    public String toString() 
	{
        String sRet = "";

		sRet += "Attribut ID   : " + this.attributId + "\n";
		sRet += "Nom           : " + this.nom        + "\n";
		sRet += "Type          : " + this.type       + "\n";
		sRet += "Visibilité    : " + this.visibilite + "\n";
		sRet += "Est static    : " + this.estStatic  + "\n";
		sRet += "Est final     : " + this.estFinal   + "\n";

        return sRet;
    }
}
