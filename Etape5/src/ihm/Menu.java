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

        // Définir des action commands et enregistrer ce menu comme listener
        itemOuvrirDossier.setActionCommand("ouvrirDossier");
        itemSauvegarde   .setActionCommand("sauvegarder");
        itemExporter     .setActionCommand("exporter");
        itemRefresh      .setActionCommand("refresh");
        itemQuitter      .setActionCommand("quitter");

        itemOuvrirDossier.addActionListener(this);
        itemSauvegarde   .addActionListener(this);
        itemExporter     .addActionListener(this);
        itemRefresh      .addActionListener(this);
        itemQuitter      .addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) 
    {
        String cmd = e.getActionCommand();
        if (cmd == null) return;

        switch (cmd) 
        {
            case "ouvrirDossier" -> ouvrirDossier();
            case "sauvegarder"   -> sauvegarder();
            case "exporter"      -> exporter();
            case "refresh"       -> refresh();
            case "quitter"       -> System.exit(0);
            default -> 
            {
                // no-op
            }
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
            fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

            int returnValue = fileChooser.showOpenDialog( null );
            if ( returnValue == JFileChooser.APPROVE_OPTION ) 
            {
                String chemin = fileChooser.getSelectedFile().getAbsolutePath();

                this.frameUML.lireDossier( chemin );
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
