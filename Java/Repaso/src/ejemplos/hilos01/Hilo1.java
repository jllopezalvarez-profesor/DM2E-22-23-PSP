package ejemplos.hilos01;

public class Hilo1 extends Thread {

	@Override
	public void run() {
		try {
			while (true) {
				
				
				
				
				
				if (Thread.interrupted()) {					
					throw new InterruptedException();
				}
				
				
			}
		} catch (InterruptedException e) {
//			limpiar();
			System.err.println("En excepción");
		}

	}

	private void limpiar() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
