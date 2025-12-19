package src.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;
import javax.swing.*;

public class Menu extends JMenuBar implements ActionListener
{
    private FrameUML frameUML;

    private JMenu     menuFichier;
    private JMenuItem itemSauvegarde;
    private JMenuItem itemOuvrirDossier;
    private JMenuItem itemOuvrirSauvegarde;
    private JMenuItem itemQuitter;
    private JMenuItem itemExporter;
    private JMenuItem itemRefresh;
	private JMenuItem itemOuvrirData;

    private Runnable onSauvegarder;
    private Consumer<String> onExporter;
    private Runnable onActualiser;

	private Bloc bloc;

    public Menu(FrameUML frameUML) 
    {
        this.frameUML = frameUML;

        // Menu principal
        this.menuFichier       = new JMenu    ("Menu"          );

        // Items du menu
        this.itemSauvegarde       = new JMenuItem("Sauvegarder"   );
        this.itemOuvrirDossier    = new JMenuItem("Ouvrir dossier");
  		this.itemOuvrirData       = new JMenuItem("Ouvrir data");
        this.itemOuvrirSauvegarde = new JMenuItem("ouvrir la sauvegarde");
        this.itemExporter         = new JMenuItem("Exporter"      );
        this.itemRefresh          = new JMenuItem("Rafraîchir"    );
        this.itemQuitter          = new JMenuItem("Quitter"       );

        // Ajout des items dans le menu
        menuFichier.add         (itemSauvegarde      );
        menuFichier.add         (itemOuvrirDossier   );
		menuFichier.add         (itemOuvrirData      );
        menuFichier.add         (itemOuvrirSauvegarde);
        menuFichier.add         (itemExporter        );
        menuFichier.add         (itemRefresh         );
        menuFichier.addSeparator(                    );
        menuFichier.add         (itemQuitter         );

        // Ajout du menu à la barre
        add(menuFichier);

        // Définir des action commands et enregistrer ce menu comme listener
        itemOuvrirDossier      .setActionCommand("ouvrirDossier"   );
        itemSauvegarde         .setActionCommand("sauvegarder"     );
		itemOuvrirData         .setActionCommand("ouvrirData"      );
        itemOuvrirSauvegarde   .setActionCommand("ouvrirSauvegarde");
        itemExporter           .setActionCommand("exporter"        );
        itemRefresh            .setActionCommand("refresh"         );
        itemQuitter            .setActionCommand("quitter"         );

        itemOuvrirDossier   .addActionListener(this);
        itemSauvegarde      .addActionListener(this);
		itemOuvrirData      .addActionListener(this);
        itemOuvrirSauvegarde.addActionListener(this);
        itemExporter        .addActionListener(this);
        itemRefresh         .addActionListener(this);
        itemQuitter         .addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();
        if (cmd == null) return;

        switch (cmd) 
        {
            case "ouvrirDossier"    -> ouvrirDossier();
            case "sauvegarder"      -> sauvegarder();
			case "ouvrirData"       -> ouvrirData();
            case "ouvrirSauvegarde" -> ouvrirSauvegarde();
            case "exporter"         -> exporter();
            case "refresh"          -> refresh();
            case "quitter"          -> System.exit(0);
            default -> 
            {
                // no-op
            }
        }
    }

    private void ouvrirData()
	{
		System.out.println("Ouverture du fichier data... depuis menue");
		this.frameUML.charger("src/sauvegarde/save.ser");
	}

	// Getters si besoin de récupérer les actions
    public JMenuItem getItemSauvegarde()    { return itemSauvegarde;   }
    public JMenuItem getItemOuvrirDossier() { return itemOuvrirDossier;}
    public JMenuItem getItemOuvrirData()    { return itemOuvrirData;   }
    public JMenuItem getItemExporter()      { return itemExporter;     }
    public JMenuItem getItemRefresh()       { return itemRefresh;      }
    public JMenuItem getItemQuitter()       { return itemQuitter;      }


    public void ouvrirDossier()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Ouvre directement le dossier courant (où se trouve le programme)
        String userDir = System.getProperty("user.dir");
        fileChooser.setCurrentDirectory(new File(userDir + "/src/data"));

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            String chemin = fileChooser.getSelectedFile().getAbsolutePath();
            this.frameUML.lireDossier( chemin );
        }
    }

    public void ouvrirSauvegarde()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory( new File( "./src/sauvegarde" ) );
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            String chemin = fileChooser.getSelectedFile().getAbsolutePath();
            this.frameUML.lireData( chemin );
        }
    }

    public void sauvegarder() 
    {
        try 
        {
			this.frameUML.sauvegarder();
        } 
        catch ( Exception e ) {}
    }

	public void exporter()
	{
		try
		{
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION)
			{
				String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
				// if (onExporter != null)
				// {
				System.out.println("Exporting to: " + selectedPath);
				this.frameUML.exportToImage(selectedPath);
				// onExporter.accept(selectedPath);
				// }
			}
		} catch (Exception ex)
		{
		}
	}

    public void refresh()
    {
        try
        {
            if (onActualiser != null) 
            {
                onActualiser.run();
            }
        } catch (Exception ex) {}
    }
}
