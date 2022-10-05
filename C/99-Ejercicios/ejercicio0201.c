#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>

// ABUELO-HIJO-NIETO

// Declaraciones de cabeceras de funciones
int generateRandom(int lower, int upper);
void procesoGenerador(int fdPipeEscritura, pid_t pidClasificador);
void procesoClasificador(int fdPipeLectura, int fdPipeEscrituraPares,
                         int fdPipeEscrituraImpares, pid_t pidPares,
                         pid_t pidImpares);
void procesoPares(int fdPipeLectura);
void procesoImpares(int fdPipeLectura);

void main() {
    // Inicialización del generador de números aleatorios.
    srand(time(NULL));

    char saludo[] = "Hola.\0";

    pid_t pidClasificador;

    int pipeClasificador[2];

    int result;
    result = pipe(pipeClasificador);

    pidClasificador = fork();

    if (result == -1) {
        printf("Error al crear el pipe para comunicar con el clasificador\n.");
        exit(-1);
    }

    switch (pidClasificador) {
        case -1:  // Error en fork
            printf(
                "Error al crear el proceso que clasifica los números (2ª "
                "generación)\n.");
            exit(-1);
        case 0:  // Hijo
            // Cerramos el pipe de escritura
            close(pipeClasificador[1]);

            // Variables para pid y pipe para los pares
            pid_t pidPares;
            int pipePares[2];

            // Abrimos pipe
            pipe(pipePares);

            // fork para pares
            pidPares = fork();

            switch (pidPares) {
                case -1:
                    printf(
                        "Error al crear el proceso para los números pares (3ª "
                        "generación)\n.");
                    exit(-1);
                case 0:  // Hijo (proceso de pares)
                    // Cerramos pipe de escritura
                    close(pipePares[1]);
                    procesoPares(pipePares[0]);
                    break;
                default:  // Padre. Seguimos en proceso de clasificación
                    // Cerramos pipe de lectura
                    close(pipePares[0]);

                    // Variables para pid y pipe para los impares)
                    pid_t pidImpares;
                    int pipeImpares[2];

                    // Abrimos pipe
                    pipe(pipeImpares);

                    // fork para impares
                    pidImpares = fork();

                    switch (pidImpares) {
                        case -1:
                            printf(
                                "Error al crear el proceso para los números "
                                "impares (3ª "
                                "generación)\n.");
                            exit(-1);
                        case 0:  // Hijo (proceso de impares)
                            // Cerramos pipe de escritura
                            close(pipeImpares[1]);
                            procesoImpares(pipePares[0]);
                            break;
                        default:
                            procesoClasificador(pipeClasificador[0],
                                                pipePares[1], pipeImpares[1],
                                                pidPares, pidImpares);
                            break;
                    }

                    break;
            }

            break;
        default:  // Padre
            // Cerramos el pipe de lectura
            close(pipeClasificador[0]);
            procesoGenerador(pipeClasificador[1], pidClasificador);
            break;
    }
    exit(0);
}

void procesoGenerador(int fdPipeEscritura, pid_t pidClasificador) {
    printf("Iniciado el proceso generador ...\n");

    // Generamos 10 números aleatorios
    for (int i = 0; i < 10; i++) {
        sleep(1);
        int numeroAleatorio = generateRandom(1, 9);
        printf(
            "Número generado en el proceso generador: %d. Escribiendo"
            " en el pipe del clasificador.\n",
            numeroAleatorio);
        // Escribimos el número en el pipe
        write(fdPipeEscritura, &numeroAleatorio, sizeof(numeroAleatorio));
    }

    // Cerramos el pipe de escritura
    close(fdPipeEscritura);

    // Esperamos a que el hijo termine.
    waitpid(pidClasificador, NULL, 0);

    // Mensaje para verificar que se escriben las cosas en orden
    printf("Finalizando generador de números...");
}

void procesoClasificador(int fdPipeLectura, int fdPipeEscrituraPares,
                         int fdPipeEscrituraImpares, pid_t pidPares,
                         pid_t pidImpares) {
    printf("Iniciado el proceso clasificador ...\n");

    // Leemos del pipe
    int numeroLeido, bytesLeidos;
    printf("Clasificador - Leyendo primer número.\n");
    bytesLeidos = read(fdPipeLectura, &numeroLeido, sizeof(numeroLeido));
    while (bytesLeidos != 0) {  // El número de bytes será 0 cuando se
                                // haya cerrado el pipe de escritura
        printf("Leidos %d bytes. Número leído: %d.\n", bytesLeidos,
               numeroLeido);

        if ((numeroLeido % 2) == 0) {  // Par.
            write(fdPipeEscrituraPares, &numeroLeido, sizeof(numeroLeido));
        } else {
            write(fdPipeEscrituraImpares, &numeroLeido, sizeof(numeroLeido));
        }

        bytesLeidos = read(fdPipeLectura, &numeroLeido, sizeof(numeroLeido));
    }

    // Cerramos el pipe de lectura
    close(fdPipeLectura);

    // Cerramos los pipes de escritura
    close(fdPipeEscrituraPares);
    close(fdPipeEscrituraImpares);

    // Esperamos a que terminen los hijos;
    waitpid(pidPares, NULL, 0);
    waitpid(pidImpares, NULL, 0);

    // Mensaje para verificar que se escriben las cosas en orden
    printf("Finalizando clasificador de números...\n");
}

void procesoPares(int fdPipeLectura) {
    printf("Iniciado el proceso de pares ...\n");

    // Leemos del pipe
    int numeroLeido, bytesLeidos;
    printf("Pares - Leyendo primer número.\n");
    bytesLeidos = read(fdPipeLectura, &numeroLeido, sizeof(numeroLeido));
    while (bytesLeidos != 0) {  // El número de bytes será 0 cuando se
                                // haya cerrado el pipe de escritura
        printf("Leidos %d bytes en pares. Número leído: %d.\n", bytesLeidos,
               numeroLeido);

        bytesLeidos = read(fdPipeLectura, &numeroLeido, sizeof(numeroLeido));
    }

    // Cerramos el pipe de lectura
    close(fdPipeLectura);

    // Mensaje para verificar que se escriben las cosas en orden
    printf("Finalizando proceso de pares...\n");
}

void procesoImpares(int fdPipeLectura) {
    printf("Iniciado el proceso de impares ...\n");

    // Leemos del pipe
    int numeroLeido, bytesLeidos;
    printf("Impares - Leyendo primer número.\n");
    bytesLeidos = read(fdPipeLectura, &numeroLeido, sizeof(numeroLeido));
    while (bytesLeidos != 0) {  // El número de bytes será 0 cuando se
                                // haya cerrado el pipe de escritura
        printf("Leidos %d bytes en impares. Número leído: %d.\n", bytesLeidos,
               numeroLeido);

        bytesLeidos = read(fdPipeLectura, &numeroLeido, sizeof(numeroLeido));
    }

    // Cerramos el pipe de lectura
    close(fdPipeLectura);

    // Mensaje para verificar que se escriben las cosas en orden
    printf("Finalizando proceso de impares...\n");
}

int generateRandom(int lower, int upper) {
    int num = (rand() % (upper - lower + 1)) + lower;
    return num;
}
