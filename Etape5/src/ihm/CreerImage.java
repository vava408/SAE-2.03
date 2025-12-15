package src.ihm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class CreerImage
{
	public CreerImage()
	{

	}

	public boolean exportToImage(BufferedImage img, String path)
	{
		System.out.println("Exportation de l'image vers : " + path);
		try
		{
			if (!path.contains(".png"))
			{
				path += ".png";
				
			}
			File outputFile = new File(path);
			ImageIO.write(img, "png", outputFile);
			JOptionPane.showMessageDialog(null, "Image exportée avec succès : " + outputFile.getAbsolutePath());
			System.out.println("Image créée avec succès : " + outputFile.getAbsolutePath());
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur lors de l'export : " + e.getMessage());
			return false;
		}
	}

}