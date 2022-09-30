#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>



void main() {

while (1)
{
  fork();
  sleep(1);
}
   exit(0);
}

/*
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ gcc ejemplo1Fork.c -o ejemplo1Fork
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ ./ejemplo1Fork
Soy el proceso hijo 
	 Mi PID es 4037, El PID de mi padre es: 4036.
Soy el proceso padre:
	 Mi PID es 4036, El PID de mi padre es: 3586.
	 Mi hijo: 4037 terminó.
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ ps
  PID TTY          TIME CMD
 3586 pts/2    00:00:01 bash
 4044 pts/2    00:00:00 ps
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ 


*/
