# SAE-2.03 – Étape 4
### Présentation :

Ce répertoire contient l’étape 4 du projet SAE‑2.03.
Il inclut le code source ainsi que les ressources nécessaires pour exécuter et tester le projet.

#### Cette étape se concentre sur :

La lecture et le traitement des données

La représentation des éléments du projet (attributs, méthodes, associations…)

L’affichage via l’interface utilisateur

## Lancer le projet

#### Linux / macOS

```.bash
chmod +x start.sh   # Donner les permissions d’exécution
./start.sh          # Lancer le projet
```
#### Windows

```.bash
start.bat           # Lancer le projet
```

## Structure du projet
```
Etape4/
├── class/          # Fichiers compilés
├── data/           # Données utilisées par le projet
├── jdoc/           # Documentation générée
├── src/            # Code source
│   ├── ihm/        # Interface utilisateur
│   │   └── Vue.java
│   ├── membres/    # Représentation des éléments du projet
│   │   ├── Association.java
│   │   ├── Attribut.java
│   │   ├── Methode.java
│   │   └── Parametre.java
│   └── metier/     # Traitement et lecture des données
│       ├── DecomposerLigne.java
│       ├── LireAttribut.java
│       ├── LireDossier.java
│       ├── LireFichier.java
│       ├── LireHeritImpl.java
│       └── LireMethode.java
├── compile.list    # Liste des fichiers à compiler
└── start.sh        # Script de lancement Linux/macOS
```

### Description des dossiers

metier : Classes pour lire et traiter les lignes de données

membres : Classes représentant les éléments du projet (attributs, méthodes, paramètres…)

ihm : Classes liées à l’affichage et à l’interface utilisateur