package org.ordinaria.ev1.jllopezalvarez.ejercicio1;

public class Paciente implements Comparable<Paciente> {
	private final int orden;
	private final int gravedad;

	public Paciente(int orden, int gravedad) {
		this.orden = orden;
		this.gravedad = gravedad;
	}

	public int getOrden() {
		return orden;
	}

	public int getGravedad() {
		return gravedad;
	}

	@Override
	public int compareTo(Paciente o) {
		if (this.gravedad == o.gravedad)
			return Integer.compare(this.orden, o.orden);
		return Integer.compare(o.gravedad, this.gravedad);
	}

}
