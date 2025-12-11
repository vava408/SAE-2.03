package src.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.*;

public class Menu extends JMenuBar implements ActionListener
{
    private FrameUML frameUML;

    private JMenu     menuFichier;
    private JMenuItem itemSauvegarde;
    private JMenuItem itemOuvrirDossier;
    private JMenuItem itemQuitter;
    private JMenuItem itemExporter;
    private JMenuItem itemRefresh;
    

    private Consumer<String> onChargerDossier;
    private Runnable onSauvegarder;
    private Consumer<String> onExporter;
    private Runnable onActualiser;

    public Menu(FrameUML frameUML) 
    {
        this.frameUML = frameUML;

        // Menu principal
        this.menuFichier       = new JMenu    ("Menu"          );

        // Items du menu
        this.itemSauvegarde    = new JMenuItem("Sauvegarder"   );
        this.itemOuvrirDossier = new JMenuItem("Ouvrir dossier");
        this.itemExporter      = new JMenuItem("Exporter"      );
        this.itemRefresh       = new JMenuItem("Rafraîchir"    );
        this.itemQuitter       = new JMenuItem("Quitter"       );

        // Ajout des items dans le menu
        menuFichier.add         (itemSauvegarde   );
        menuFichier.add         (itemOuvrirDossier);
        menuFichier.add         (itemExporter     );
        menuFichier.add         (itemRefresh      );
        menuFichier.addSeparator(                 );
        menuFichier.add         (itemQuitter      );

        // Ajout du menu à la barre
        add(menuFichier);

        // Action 
        itemOuvrirDossier.addActionListener(e -> {ouvrirDossier();});
        itemSauvegarde.   addActionListener(e -> {sauvegarder();  });
        itemExporter.     addActionListener(e -> {exporter();     });
        itemRefresh.      addActionListener(e -> {refresh();      });
            
        // Action Quitter
        itemQuitter.addActionListener(e -> System.exit(0));
    }

    public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.itemOuvrirDossier )
        {
            this.ouvrirDossier();
        }
        else if ( e.getSource() == this.itemSauvegarde )
        {
            this.sauvegarder();
        }
        else if ( e.getSource() == this.itemExporter )
        {
            this.exporter();
        }
        else if ( e.getSource() == this.itemRefresh )
        {
            this.refresh();
        }
	}

    // Getters si besoin de récupérer les actions
    public JMenuItem getItemSauvegarde()    { return itemSauvegarde;   }
    public JMenuItem getItemOuvrirDossier() { return itemOuvrirDossier;}
    public JMenuItem getItemExporter()      { return itemExporter;     }
    public JMenuItem getItemRefresh()       { return itemRefresh;      }
    public JMenuItem getItemQuitter()       { return itemQuitter;      }


    public void ouvrirDossier() 
    {
        try 
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) 
            {
                String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
                if (onChargerDossier != null) 
                {
                    onChargerDossier.accept(selectedPath);
                } 
            }
        } 
        catch (Exception ex) {}
    }

    public void sauvegarder() 
    {
        try 
        {
            if (onSauvegarder != null) 
            {
                onSauvegarder.run();
            } 
        } 
        catch (Exception ex) {}
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
                if (onExporter != null) 
                {
                    onExporter.accept(selectedPath);
                } 
            }
        } 
        catch (Exception ex) {}
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
