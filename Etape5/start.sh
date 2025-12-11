#!/bin/bash

# Arrêter le script si une commande échoue
set -e

# Compile tous les .java dans src/
javac @compile.list

java src.metier.LireDossier src/repertoire
