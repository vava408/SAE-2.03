package src;

import src.metier.LireDossier;
import src.ihm.FrameUML;

public class Controleur
{
	LireDossier lireDossier;
	FrameUML    frameUML;
	
	public Controleur( String chemin )
	{
		this.frameUML    = new FrameUML( this );
	}

	public void lireDossier( String chemin )
	{
		this.lireDossier = new LireDossier( chemin );
		this.lireDossier.afficherClasses();
	}
	
	public static void main ( String[] args )
	{
		new Controleur( args[0] );
	}
}
