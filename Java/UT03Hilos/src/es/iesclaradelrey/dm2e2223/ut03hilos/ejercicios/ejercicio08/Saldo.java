package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio08;

public class Saldo {

	private double saldo;

	public Saldo(double saldoInicial) {
		this.saldo = saldoInicial;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	// Si quitamos el synchronized se pueden producir condiciones de carrera.
	public synchronized void ingresar(double cantidad) {
		double saldoAnterior = saldo;
		saldoAnterior += cantidad;
		saldo = saldoAnterior;
	}
	
}
