package ejemplos.hilos01;

public class Lanzador {
	public static void main(String[] args) throws InterruptedException {

		Hilo1 hilo = new Hilo1();
		hilo.start();
		
		//Thread t = new Thread(new )
		
		
		Thread.sleep(1000);
		
		hilo.interrupt();
		
		hilo.join();
		
		System.out.println("Fin");
		
		
		
	}
}
