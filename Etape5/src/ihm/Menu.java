package src.ihm;

import java.util.function.Consumer;
import javax.swing.*;

public class Menu extends JMenuBar {

    private JMenu     menuFichier;
    private JMenuItem itemSauvegarde;
    private JMenuItem itemOuvrirDossier;
    private JMenuItem itemQuitter;
    private JMenuItem itemExporter;
    private JMenuItem itemRefresh;
    
    // Callbacks to be provided by the controller / application
    private Consumer<String> onChargerDossier;
    private Runnable onSauvegarder;
    private Consumer<String> onExporter;
    private Runnable onActualiser;

    public Menu() {
        
        // Menu principal
        menuFichier       = new JMenu    ("Fichier"          );

        // Items du menu
        itemSauvegarde    = new JMenuItem("Sauvegarder"   );
        itemOuvrirDossier = new JMenuItem("Ouvrir dossier");
        itemExporter      = new JMenuItem("Exporter"      );
        itemRefresh       = new JMenuItem("Rafraîchir"    );
        itemQuitter       = new JMenuItem("Quitter"       );

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
        itemOuvrirDossier.addActionListener(e -> {OuvrirDossier();});
        itemSauvegarde.   addActionListener(e -> {Sauvegarder();  });
        itemExporter.     addActionListener(e -> {Exporter();     });
        itemRefresh.      addActionListener(e -> {Refresh();      });
            
        // Action Quitter
        itemQuitter.addActionListener(e -> System.exit(0));
    }

    // Getters si besoin de récupérer les actions
    public JMenuItem getItemSauvegarde()    { return itemSauvegarde;   }
    public JMenuItem getItemOuvrirDossier() { return itemOuvrirDossier;}
    public JMenuItem getItemExporter()      { return itemExporter;     }
    public JMenuItem getItemRefresh()       { return itemRefresh;      }
    public JMenuItem getItemQuitter()       { return itemQuitter;      }


    public void OuvrirDossier() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
            if (onChargerDossier != null) {
                onChargerDossier.accept(selectedPath);
            }
        }
    }

    public void Sauvegarder() {
        if (onSauvegarder != null) {
            onSauvegarder.run();
        }
    }

    public void Exporter() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
            if (onExporter != null) {
                onExporter.accept(selectedPath);
            }
        }
    }

    public void Refresh() {
        if (onActualiser != null) {
            onActualiser.run();
        }
    }
}
