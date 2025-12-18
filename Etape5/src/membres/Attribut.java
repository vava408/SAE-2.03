package src.membres;

import java.io.Serializable;

public class Attribut implements Serializable
{

	private int     attributId;
	private String  nom;
	private String  type;
	private String  visibilite;
	private boolean estStatic;
	private boolean estFinal;
	private boolean estAddOnly;
	private boolean estRequete;

	// constructeur
	public Attribut(int attributId, String nom, String type, String visibilite, boolean estStatic, boolean estFinal,
			boolean isAddOnly, boolean isRequete)
	{

		this.attributId = attributId;
		this.nom        = nom;
		this.type       = type;
		this.visibilite = visibilite;
		this.estStatic  = estStatic;
		this.estFinal   = estFinal;
		this.estAddOnly = isAddOnly;
		this.estRequete = isRequete;
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

	//retourbe true si l attibut est addOnly
	public boolean isAddOnly()    { return this.estAddOnly ; }

	//retourne true si l'attribut est requête
	public boolean isRequete()    { return this.estRequete  ; }

	//modifie le statut de requête de l'attribut
	public void setRequete(boolean isRequete) { this.estRequete = isRequete; }

	// affichage textuel de l'attribut
	public String toString() 
    {
        String sRet = "";

		sRet += this.visibilite + " ";

		if (this.estFinal) 
		{
			sRet += "final ";
		}

		if (this.estStatic) 
		{
			sRet += "static ";
		}

		sRet += this.type + " " + this.nom;

        return sRet;
    }
}
