#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <fcntl.h>
/*-------------------------------------------*/
/* gestión de señales en proceso padre       */
void gestion_padre( int segnal )
{
 printf("Padre recibe señal..%d\n", segnal);
}
/* gestión de señales en proceso hijo        */
void gestion_hijo( int segnal )
{
 printf("Hijo recibe señal..%d\n", segnal);
}
/*-------------------------------------------*/
int main()
{
  int pid_padre, pid_hijo;
  
  pid_padre = getpid();
  pid_hijo = fork(); //creamos hijo
    
  switch(pid_hijo)
  {
    case -1:
	  printf( "Error al crear el proceso hijo...\n");
      exit( -1 );        
    case 0:   //HIJO     	 
      //tratamiento de la señal en proceso hijo
	  signal( SIGUSR1, gestion_hijo );
      while(1) { //bucle infinito             
       sleep(1);
	   kill(pid_padre, SIGUSR1);//ENVIA SEÑAL AL PADRE
	   pause();//hijo espera hasta que llegue una señal de respuesta
	  }
    break;    
    default: //PADRE 
	  //tratamiento de la señal en proceso padre
      signal( SIGUSR1, gestion_padre );
      while(1)  {
        pause();//padre espera hasta recibir una señal del hijo
	    sleep(1);
	    kill(pid_hijo, SIGUSR1);//ENVIA SEÑAL AL HIJO
      }       
    break;
  } 
  return 0;
}
/*
administrador@ubuntu1:~$ ps -fe | grep sincronizar
1000      1678  1549  0 22:20 pts/0    00:00:00 ./sincronizar
1000      1679  1678  0 22:20 pts/0    00:00:00 ./sincronizar
1000      1687  1572  0 22:21 pts/1    00:00:00 grep --color=auto sincronizar
administrador@ubuntu1:~$ kill 1679
   el padre queda a la espera
Padre recibe señal..10
administrador@ubuntu1:~$ kill 1678
Termina el proceso

Terminado

administrador@ubuntu1:~$ 

*/
 

 


