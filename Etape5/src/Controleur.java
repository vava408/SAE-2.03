package src;

import src.metier.LireDossier;
import src.metier.LireFichier;

import java.util.ArrayList;

import src.ihm.FrameUML;

public class Controleur
{
	LireDossier lireDossier;
	FrameUML    frameUML;
	
	public Controleur()
	{
		this.frameUML    = new FrameUML( this );
	}

	public void lireDossier( String chemin )
	{
		this.lireDossier = new LireDossier( chemin );
		this.lireDossier.afficherClasses();
	}

	public int getNbClasses()
	{
		return this.lireDossier.getNbClasses();
	}

	public ArrayList<LireFichier> getListeFichiers()
	{
		return this.lireDossier.getListeFichiers();
	}
	
	public static void main ( String[] args )
	{
		new Controleur();
	}
}
