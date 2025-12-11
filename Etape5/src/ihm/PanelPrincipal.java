package src.ihm;

import java.awt.Graphics;

import javax.swing.JPanel;

public class PanelPrincipal extends JPanel 
{
	private FrameUML frameUML;

	public PanelPrincipal ( FrameUML frameUML )
	{
		this.frameUML = frameUML;
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent( g );
	}
}
