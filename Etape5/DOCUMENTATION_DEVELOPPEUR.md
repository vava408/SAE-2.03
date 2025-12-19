# Documentation Développeur - Générateur de Diagrammes UML
## Projet SAE-2.03 - Étape 5

---

## Table des matières

1. [Vue d'ensemble du projet](#1-vue-densemble-du-projet)
2. [Architecture globale](#2-architecture-globale)
3. [Structure des packages et classes](#3-structure-des-packages-et-classes)
4. [Flux de données et workflows](#4-flux-de-données-et-workflows)
5. [Guide d'extension et maintenance](#5-guide-dextension-et-maintenance)

---

## 1. Vue d'ensemble du projet

### 1.1 Objectif
Ce projet implémente un générateur de diagrammes UML à partir de code source Java. Il permet de :
- Analyser des fichiers Java (.java) dans un dossier
- Extraire les classes, attributs, méthodes et relations
- Générer un diagramme UML visuel avec les associations, héritages et implémentations
- Sauvegarder et charger des configurations de diagrammes
- Exporter les diagrammes en images

### 1.2 Technologies utilisées
- **Langage** : Java (JDK 11+)
- **Interface graphique** : Swing (JFrame, JPanel, JLayeredPane)
- **Persistance** : Sérialisation Java (ObjectOutputStream/ObjectInputStream)
- **Graphisme** : Java 2D (Graphics2D, BufferedImage)

### 1.3 Points d'entrée
Le programme démarre via la méthode `main()` de la classe **Controleur** :
```java
public static void main(String[] args) {
    new Controleur();
}
```

---

## 2. Architecture globale

### 2.1 Patron architectural : MVC (Modèle-Vue-Contrôleur)

Le projet suit une architecture MVC adaptée :

```
┌─────────────────────────────────────────────────────────────┐
│                        Controleur                            │
│  - Orchestration des interactions                            │
│  - Liaison entre les composants                              │
└──────────┬─────────────────────────────────┬────────────────┘
           │                                 │
           ▼                                 ▼
    ┌──────────────┐                  ┌──────────────┐
    │    Métier    │                  │     IHM      │
    │  (Modèle)    │                  │    (Vue)     │
    │              │                  │              │
    │ - LireDossier│                  │ - FrameUML   │
    │ - LireFichier│                  │ - Menu       │
    │ - Sauvegarder│                  │ - PanelPrin. │
    │ - Lire...    │                  │ - Bloc       │
    └──────────────┘                  │ - Fleche     │
                                      └──────────────┘
           ▲
           │
    ┌──────────────┐
    │   Membres    │
    │   (Entités)  │
    │              │
    │ - Attribut   │
    │ - Methode    │
    │ - Association│
    │ - Parametre  │
    └──────────────┘
```

### 2.2 Responsabilités des couches

| Couche | Package | Responsabilité |
|--------|---------|----------------|
| **Contrôleur** | `src` | Coordination des interactions entre IHM et métier |
| **Métier** | `src.metier` | Lecture, analyse et traitement des fichiers Java |
| **IHM** | `src.ihm` | Affichage graphique et interactions utilisateur |
| **Entités** | `src.membres` | Représentation des éléments UML (classes, attributs, méthodes) |
| **Données** | `src.data` | Exemples de données pour tester le générateur |

---

## 3. Structure des packages et classes

### 3.1 Package `src` - Contrôleur

#### **Controleur.java**
Classe principale orchestrant l'application.

**Attributs clés :**
```java
LireDossier lireDossier;    // Gestionnaire de lecture de dossiers
FrameUML frameUML;          // Interface graphique principale
Vue vue;                    // Gestionnaire d'affichage textuel
Sauvegarder sauvegarder;    // Gestionnaire de sauvegarde
LireSauvegarde charger;     // Gestionnaire de chargement
```

**Méthodes importantes :**
- `lireDossier(String chemin)` : Initialise la lecture d'un dossier Java
- `sauvegarder()` : Sauvegarde l'état actuel du diagramme
- `charger(String path)` : Charge une sauvegarde existante
- `getListeFichiers()` : Retourne la liste des classes analysées
- `getListeAssociation()` : Retourne les associations détectées

---

### 3.2 Package `src.metier` - Logique métier

#### **LireDossier.java**
Classe centrale pour l'analyse d'un dossier de fichiers Java.

**Rôle :**
- Parcourt un dossier et identifie les fichiers `.java`
- Instancie un `LireFichier` pour chaque classe
- Détecte et crée les associations entre classes
- Stocke les relations dans des collections

**Attributs principaux :**
```java
ArrayList<LireFichier> lstLireFichiers;        // Liste des fichiers lus
ArrayList<Association> lstAssociations;        // Associations détectées
HashMap<Association, ArrayList<String>> hMAttrAsso;  // Attributs liés aux associations
```

**Workflow :**
1. `lireDossier(cheminDossier)` : Lecture des fichiers `.java`
2. `creerAssociation()` : Création des associations basées sur les types d'attributs

#### **LireFichier.java**
Analyse un fichier Java et extrait ses éléments.

**Composants utilisés :**
- `DecomposerLigne` : Découpe et analyse les lignes de code
- `LireAttribut` : Extrait les attributs de la classe
- `LireMethode` : Extrait les méthodes
- `LireHeritImple` : Détecte les héritages et implémentations

**Données extraites :**
- Nom de la classe
- Type (class, interface, enum, abstract)
- Liste des attributs
- Liste des méthodes
- Héritages (extends)
- Implémentations (implements)

#### **Sauvegarder.java**
Permet de sauvegarder l'état du diagramme via sérialisation.

**Fonctionnement :**
```java
public void sauvegarder(LireDossier dossier) {
    ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream("src/sauvegarde/save.ser")
    );
    out.writeObject(dossier);
    out.close();
    
    // Création d'un fichier .data lisible
    CreeData data = new CreeData();
    data.creerData(dossier);
}
```

**Fichiers générés :**
- `save.ser` : Fichier binaire sérialisé
- `save.data` : Fichier texte lisible (via CreeData)

#### **LireSauvegarde.java**
Charge une sauvegarde sérialisée.

**Points importants :**
- Désérialise l'objet `LireDossier`
- Réinitialise les champs `transient` (non sérialisés)
- Relie le dossier au contrôleur

---

### 3.3 Package `src.ihm` - Interface utilisateur

#### **FrameUML.java**
Fenêtre principale de l'application.

**Composants :**
- `Menu` : Barre de menu (ouvrir, sauvegarder, exporter)
- `PanelPrincipal` : Zone de dessin des diagrammes
- `JScrollPane` : Permet le défilement du diagramme

**Interactions utilisateur :**
```java
// Au démarrage, choix entre :
- Ouvrir un fichier .data (sauvegarde)
- Ouvrir un dossier (nouveau diagramme)
- Annuler (ferme l'application)
```

#### **PanelPrincipal.java**
Panel principal gérant l'affichage du diagramme.

**Responsabilités :**
- Création des `Bloc` (classes UML)
- Création des `Fleche` (associations, héritages)
- Positionnement automatique des éléments
- Gestion du glisser-déposer des blocs
- Export en image

**Structures de données :**
```java
HashMap<Bloc, String> hMBlocs;           // Mappage bloc → nom de classe
HashMap<Fleche, Association> hMFleches;  // Mappage flèche → association
```

**Méthode clé - `instancierPanel()` :**
1. Crée un `Bloc` pour chaque classe
2. Crée une `Fleche` pour chaque association
3. Positionne les éléments sur le panel
4. Actualise l'affichage

#### **Bloc.java**
Représentation graphique d'une classe UML.

**Affichage :**
```
┌─────────────────────┐
│    NomClasse        │  ← Nom de la classe (centré)
├─────────────────────┤
│  - attribut1        │  ← Attributs avec visibilité
│  + attribut2        │     (-, +, # pour private/public/protected)
├─────────────────────┤
│  + methode1()       │  ← Méthodes avec visibilité
│  - methode2()       │
└─────────────────────┘
```

**Fonctionnalités :**
- Clic sur le bloc : Affiche/masque les détails
- Glisser-déposer : Déplace le bloc sur le diagramme
- Redimensionnement automatique selon le contenu

#### **Fleche.java**
Représentation graphique des relations UML.

**Types de flèches :**
1. **Association** : Ligne simple avec multiplicités
   ```
   ClasseA -------- ClasseB
         0..*    1..1
   ```
2. **Héritage** : Flèche avec triangle blanc
   ```
   Enfant ───────▷ Parent
   ```
3. **Implémentation** : Flèche pointillée avec triangle blanc
   ```
   Classe ─ ─ ─ ─▷ Interface
   ```

---

### 3.4 Package `src.membres` - Entités métier

#### **Attribut.java**
Représente un attribut de classe Java.

**Propriétés :**
```java
String visibilite;  // public, private, protected
String type;        // Type de l'attribut (int, String, etc.)
String nom;         // Nom de l'attribut
String modifieurs;  // static, final, etc.
```

#### **Methode.java**
Représente une méthode de classe Java.

**Propriétés :**
```java
String visibilite;               // public, private, protected
String typeRetour;               // Type de retour
String nom;                      // Nom de la méthode
ArrayList<Parametre> parametres; // Liste des paramètres
String modifieurs;               // static, abstract, etc.
```

#### **Association.java**
Représente une association UML entre deux classes.

**Propriétés :**
```java
String nomClasseA;       // Classe source
String nomClasseB;       // Classe destination
String multipliciteA;    // Multiplicité côté A (ex: "0..*")
String multipliciteB;    // Multiplicité côté B (ex: "1..1")
```

**Méthodes importantes :**
```java
boolean estUnidirectionnelle() {
    // Une association est unidirectionnelle si :
    // - ClasseA a "0..*" et ClasseB a "1..1", ou inversement
    return (multipliciteA.equals("0..*") && multipliciteB.equals("1..1")) ||
           (multipliciteB.equals("0..*") && multipliciteA.equals("1..1"));
}
```

#### **Parametre.java**
Représente un paramètre de méthode.

**Propriétés :**
```java
String type;  // Type du paramètre
String nom;   // Nom du paramètre
```

---

## 4. Flux de données et workflows

### 4.1 Workflow : Ouverture d'un dossier

```
[Utilisateur clique "Ouvrir dossier"]
              ↓
    [FrameUML.ouvrirDossier()]
              ↓
    [Controleur.lireDossier(chemin)]
              ↓
    [Instanciation de LireDossier]
              ↓
    [LireDossier.lireDossier(chemin)]
         - Parcourt les fichiers .java
         - Crée un LireFichier par fichier
              ↓
    [LireFichier analyse chaque fichier]
         - Extrait nom, type de classe
         - Lit attributs (via LireAttribut)
         - Lit méthodes (via LireMethode)
         - Lit héritages (via LireHeritImple)
              ↓
    [LireDossier.creerAssociation()]
         - Détecte les associations basées sur
           les types d'attributs
              ↓
    [PanelPrincipal.instancierPanel()]
         - Crée les Bloc pour chaque classe
         - Crée les Fleche pour chaque relation
         - Positionne et affiche le diagramme
              ↓
    [Diagramme UML affiché à l'écran]
```

### 4.2 Workflow : Sauvegarde d'un diagramme

```
[Utilisateur clique "Sauvegarder"]
              ↓
    [Menu.sauvegarder()]
              ↓
    [FrameUML.sauvegarder()]
              ↓
    [Controleur.sauvegarder()]
              ↓
    [Sauvegarder.sauvegarder(lireDossier)]
         - Sérialise l'objet LireDossier
         - Écrit dans "src/sauvegarde/save.ser"
              ↓
    [CreeData.creerData(lireDossier)]
         - Crée un fichier texte "save.data"
         - Format lisible pour visualisation
              ↓
    [Fichiers save.ser et save.data créés]
```

### 4.3 Workflow : Chargement d'une sauvegarde

```
[Utilisateur choisit "Ouvrir fichier .data"]
              ↓
    [FrameUML.ouvrirFichierData()]
              ↓
    [Controleur.charger(path)]
              ↓
    [LireSauvegarde.charger(ctrl, path)]
         - Désérialise le fichier .ser
         - Restaure l'objet LireDossier
              ↓
    [LireDossier.reinitialiser(ctrl)]
         - Réinitialise les champs transient
         - Relie le dossier au contrôleur
              ↓
    [PanelPrincipal.instancierPanel()]
         - Reconstruit le diagramme
              ↓
    [Diagramme restauré affiché]
```

### 4.4 Workflow : Export en image

```
[Utilisateur clique "Exporter"]
              ↓
    [Menu.exporter()]
         - Affiche JFileChooser
         - Utilisateur choisit emplacement
              ↓
    [FrameUML.exportToImage(path)]
              ↓
    [PanelPrincipal.exportToImage(path)]
              ↓
    [CreerImage.creerImage(panel, path)]
         - Crée un BufferedImage
         - Dessine le panel dans l'image
         - Sauvegarde en PNG
              ↓
    [Image PNG créée à l'emplacement choisi]
```

---

## 5. Guide d'extension et maintenance

### 5.1 Ajouter un nouveau type de relation

Pour ajouter une relation UML (ex: agrégation, composition) :

**Étape 1 : Modifier LireDossier**
```java
// Dans creerAssociation(), ajouter la détection :
if (/* condition pour détecter l'agrégation */) {
    Association aggr = new Association(
        lF1.getNomClasse(), 
        lF2.getNomClasse(),
        "1..1",  // Multiplicité côté agrégant
        "0..*"   // Multiplicité côté agrégé
    );
    aggr.setType("AGGREGATION");  // Nouveau champ à ajouter
    this.lstAssociations.add(aggr);
}
```

**Étape 2 : Modifier Association.java**
```java
private String typeRelation;  // ASSOCIATION, HERITAGE, IMPLEMENTATION, AGGREGATION

public String getTypeRelation() { return this.typeRelation; }
public void setType(String type) { this.typeRelation = type; }
```

**Étape 3 : Modifier Fleche.java**
```java
// Dans paintComponent(), ajouter le dessin de l'agrégation :
if (association.getTypeRelation().equals("AGGREGATION")) {
    // Dessiner un losange blanc à l'extrémité
    drawDiamond(g2, pointFin);
}
```

### 5.2 Améliorer l'analyse de code

Pour analyser des éléments Java supplémentaires (annotations, énumérations) :

**Modifier DecomposerLigne.java :**
```java
public class DecomposerLigne {
    // Ajouter la détection d'annotations
    public boolean estAnnotation(String ligne) {
        return ligne.trim().startsWith("@");
    }
    
    public String extraireAnnotation(String ligne) {
        // Extraire le nom et les paramètres de l'annotation
        return ligne.substring(1).split("\\(")[0];
    }
}
```

**Créer LireAnnotation.java :**
```java
public class LireAnnotation {
    private ArrayList<Annotation> listeAnnotations;
    
    public void analyser(String ligne) {
        // Extraire et stocker les annotations
    }
}
```

### 5.3 Personnaliser l'affichage graphique

**Modifier Bloc.java pour changer les couleurs :**
```java
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    
    // Personnalisation selon le type de classe
    if (typeClasse.equals("interface")) {
        g2.setColor(new Color(230, 240, 255));  // Bleu clair
    } else if (typeClasse.equals("abstract")) {
        g2.setColor(new Color(255, 250, 230));  // Jaune clair
    } else {
        g2.setColor(Color.WHITE);
    }
    
    // Remplir le fond
    g2.fillRect(0, 0, getWidth(), getHeight());
}
```

### 5.4 Ajouter de nouvelles fonctionnalités au menu

**Modifier Menu.java :**
```java
// Ajouter un nouvel item de menu
private JMenuItem itemGenererJavadoc;

public Menu(FrameUML frameUML) {
    // ... code existant ...
    
    this.itemGenererJavadoc = new JMenuItem("Générer Javadoc");
    menuFichier.add(itemGenererJavadoc);
    
    itemGenererJavadoc.setActionCommand("genererJavadoc");
    itemGenererJavadoc.addActionListener(this);
}

public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
        case "genererJavadoc" -> genererJavadoc();
        // ... autres cas ...
    }
}

private void genererJavadoc() {
    // Implémentation de la génération de Javadoc
}
```

### 5.5 Optimisation des performances

**Pour les grands projets avec beaucoup de classes :**

1. **Lecture asynchrone :**
```java
// Dans LireDossier, utiliser un ExecutorService
ExecutorService executor = Executors.newFixedThreadPool(4);
for (File fichier : listeFichiers) {
    executor.submit(() -> {
        LireFichier lireFichier = new LireFichier(this, fichier.getAbsolutePath());
        synchronized(lstLireFichiers) {
            lstLireFichiers.add(lireFichier);
        }
    });
}
executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);
```

2. **Cache des calculs de positionnement :**
```java
// Dans PanelPrincipal
private HashMap<String, Point> cachePositions = new HashMap<>();

public Point calculerPosition(Bloc bloc) {
    if (cachePositions.containsKey(bloc.getName())) {
        return cachePositions.get(bloc.getName());
    }
    Point pos = calculerPositionNouveau(bloc);
    cachePositions.put(bloc.getName(), pos);
    return pos;
}
```

### 5.6 Tests et débogage

**Ajouter des logs pour le débogage :**
```java
// Utiliser un logger au lieu de System.out.println
import java.util.logging.*;

public class LireDossier {
    private static final Logger LOGGER = Logger.getLogger(LireDossier.class.getName());
    
    public void lireDossier(String chemin) {
        LOGGER.info("Début de lecture du dossier : " + chemin);
        // ... code ...
        LOGGER.fine("Nombre de fichiers trouvés : " + lstLireFichiers.size());
    }
}
```

**Tests unitaires (avec JUnit) :**
```java
import org.junit.Test;
import static org.junit.Assert.*;

public class AssociationTest {
    @Test
    public void testEstUnidirectionnelle() {
        Association assoc = new Association("A", "B", "0..*", "1..1");
        assertTrue(assoc.estUnidirectionnelle());
        
        Association assocBi = new Association("A", "B", "0..*", "0..*");
        assertFalse(assocBi.estUnidirectionnelle());
    }
}
```

### 5.7 Points d'attention pour la maintenance

**Champs transient :**
- Les champs marqués `transient` ne sont pas sérialisés
- Ils doivent être réinitialisés lors du chargement
- Exemple : `private transient Controleur ctrl;`

**Sérialisation :**
- Toujours implémenter `Serializable` pour les classes sauvegardées
- Ajouter un `serialVersionUID` pour éviter les incompatibilités :
```java
private static final long serialVersionUID = 1L;
```

**Gestion mémoire :**
- Libérer les ressources graphiques après utilisation
- Utiliser `dispose()` sur les BufferedImage et Graphics2D
- Éviter les références circulaires qui empêchent le garbage collection

---

## Conclusion

Ce document fournit une vue d'ensemble complète de l'architecture et du fonctionnement du générateur de diagrammes UML. Pour toute question ou contribution, consultez le code source et les commentaires Javadoc intégrés dans les classes.

**Ressources supplémentaires :**
- Javadoc généré dans `Etape5/jdoc/` (si disponible)
- Code d'exemple dans `Etape5/src/data/`
- README.md du projet principal

**Auteurs :** Groupe 6  
**Version :** 1.0  
**Date :** Décembre 2025
