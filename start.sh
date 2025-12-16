#!/bin/bash

# Arrêter le script si une commande échoue
set -e

if [ -z "$1" ]; then
    echo "Usage : ./start.sh <repertoire>"
    exit 1
fi

# Compile tous les .java dans src/
javac @compile.list -d ./class

java LireDossier "$1"
