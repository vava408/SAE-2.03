package src;

import src.metier.LireDossier;

public class Controleur
{
	LireDossier lireDossier;
	
	public Controleur( String chemin )
	{
		this.lireDossier = new LireDossier( chemin );
		
		this.lireDossier.afficherClasses();
	}
	
	public static void main ( String[] args )
	{
		new Controleur( args[0] );
	}
}
