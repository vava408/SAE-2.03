#!/bin/bash

# Arrêter le script si une commande échoue
set -e

# Compile tous les .java dans src/
javac @compile.list -d ./class

java -cp class src.metier.LireDossier data
