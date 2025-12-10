public class Attribut {

	private int     attributId;
	private String  nom;
	private String  type;
	private String  visibilite;
	private boolean estStatic;
	private boolean estFinal;

	// constructeur
	public Attribut(int attributId, String nom, String type, String visibilite, boolean estStatic, boolean estFinal) 
	{

		this.attributId = attributId;
		this.nom        = nom;
		this.type       = type;
		this.visibilite = visibilite;
		this.estStatic  = estStatic;
		this.estFinal   = estFinal;
	}

	// retourne l'identifiant de l'attribut
	public int getAttributId()    { return attributId; }

	// retourne le nom de l'attribut
	public String getNom()        { return nom       ; }

	// retourne le type de l'attribut
	public String getType()       { return type      ; }

	// retourne la visibilité de l'attribut
	public String getVisibilite() { return visibilite; }

	// retourne true si l'attribut est static
	public boolean isStatic()     { return estStatic ; }

	// retourne true si l'attribut est final
	public boolean isFinal()      { return estFinal  ; }

	// affichage textuel de l'attribut
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
