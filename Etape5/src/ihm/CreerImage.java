package src.ihm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CreerImage
{
	public void CreerImage()
	{
		int width = 100;
		int height = 100;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphicsIm = img.createGraphics();

		try
		{
			//met le fond en blanc
			graphicsIm.setColor(Color.WHITE);
			graphicsIm.fillRect(0, 0, width, height);
			//dessine un les rectangles  du diagramme UML a partir des coordonnees et de la taille de ses dernier 
			//... A COMPLETER ...//


			//dessine les fleches du diagramme UML a partir des coordonnees de ses dernier 
			//... A COMPLETER ...//


			//dessine le texte du diagramme UML a partir des coordonnees de ses dernier
			//... A COMPLETER ...//

			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//libere les ressources utilisees par l'objet Graphics 
		finally
		{
			graphicsIm.dispose();
		}

		try
		{
			File out = new File("DiagrameUml.png");
			ImageIO.write(img, "PNG", out);
			System.out.println("Image créée : " + out.getAbsolutePath());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
