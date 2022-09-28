#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    printf("Ejemplo de uso de system():\n");
    printf("\n\tVolcando el listado del directorio actual a un fichero.");
    printf("\n\t%d", system("ls -l / > listadodirectorio.txt"));
    printf("\n\tAbriendo con Kate el fichero generado...");
    printf("\n\t%d", system("kate listadodirectorio.txt"));
    printf("\n\tEjecutando un comando incorrecto...");
    printf("\n\t%d", system("noexiste"));
    printf("\nFin del ejemplo");
    
    return 0;
}
