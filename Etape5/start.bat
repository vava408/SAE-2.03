@echo off

set CLASS_DIR=class

if not exist %CLASS_DIR% (
    mkdir %CLASS_DIR%
)

javac @compile.list -d %CLASS_DIR%

java -cp %CLASS_DIR% src.Controleur
