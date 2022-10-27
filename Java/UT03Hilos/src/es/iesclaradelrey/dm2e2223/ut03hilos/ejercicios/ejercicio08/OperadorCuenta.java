package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio08;

public class OperadorCuenta extends Thread {

	private Saldo saldo;
	private double totalAIngresar;
	private double cantidadMaximaIngresos;

	public OperadorCuenta(Saldo saldo, String nombreOperador, double totalAIngresar) {
		super(nombreOperador);
		this.saldo = saldo;
		this.totalAIngresar = totalAIngresar;
		// En cada ocasión ingresaremos un máximo de la décima parte del total que
		// tenemos que ingresar
		this.cantidadMaximaIngresos = totalAIngresar / 10;

	}

	@Override
	public void run() {
		System.out.printf("%s se pone a trabajar.\n", this.getName());
		while (totalAIngresar > 0) {
			double cantidadAIngresar = Math.random() * cantidadMaximaIngresos;
			// Si queda poco por ingresar, ajustamos la cantidad para que ingrese todo lo
			// que queda.
			if (cantidadAIngresar > totalAIngresar)
				cantidadAIngresar = totalAIngresar;
			System.out.printf("%s va a ingresar %f.\n", this.getName(), cantidadAIngresar);
			saldo.ingresar(cantidadAIngresar);
			// Decrementamos la cantidad a ingresar para no quedarnos en un bucle infinito
			totalAIngresar -= cantidadAIngresar;
			// El operador descansa un ratito
//			int descanso = (int)(Math.random()*500);
//			System.out.printf("%s va a descansar %d ms.\n", this.getName(), descanso);
//			try {
//				sleep(descanso);
//			} catch (InterruptedException e) {
//				System.err.printf("%s no ha podido descansar porque se le ha interrumpido.\nDetalle de la excepción:\n", this.getName());
//				e.printStackTrace();
//			}
		}
		System.out.printf("%s ha terminado.\n", this.getName());
	}
}
