package src.ihm;

import src.Controleur;

import java.awt.Graphics;

import javax.swing.JPanel;

public class PanelPrincipal extends JPanel 
{
	private Controleur ctrl;

	public PanelPrincipal ( Controleur ctrl )
	{
		this.ctrl = ctrl;
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent( g );
	}
}
