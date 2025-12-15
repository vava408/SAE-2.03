package src;

import java.util.ArrayList;

import src.ihm.FrameUML;
import src.ihm.Vue;

import src.metier.LireDossier;
import src.metier.LireFichier;

import src.membres.Attribut;
import src.membres.Methode;

public class Controleur
{
	LireDossier lireDossier;
	FrameUML    frameUML   ;
	Vue         vue        ;
	
	public Controleur()
	{
		this.frameUML    = new FrameUML( this );
		this.vue         = new Vue     ( this );
	}

	public void lireDossier( String chemin )
	{
		this.lireDossier = new LireDossier( this, chemin );
	}

	public LireDossier getLireDossier()
	{
		return this.lireDossier;
	}

	public FrameUML getFrameUML()
	{
		return this.frameUML;
	}

	public Vue getVue()
	{
		return this.vue;
	}

	public int getNbClasses()
	{
		return this.lireDossier.getNbClasses();
	}

	public ArrayList<LireFichier> getListeFichiers()
	{
		return this.lireDossier.getListeFichiers();
	}

	public String afficherAttribut( Attribut a )
	{
		return this.vue.afficherAttribut( a );
	}

	public String afficherMethode( Methode m )
	{
		return this.vue.afficherMethode( m );
	}
	
	public static void main ( String[] args )
	{
		new Controleur();
	}
}
