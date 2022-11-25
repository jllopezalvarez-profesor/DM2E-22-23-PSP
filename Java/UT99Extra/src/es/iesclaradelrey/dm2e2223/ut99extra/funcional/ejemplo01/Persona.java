package es.iesclaradelrey.dm2e2223.ut99extra.funcional.ejemplo01;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Persona {

	private int id;
	private String nombre;
	private LocalDate fechaNacimiento;

	public Persona(int id, String nombre, String fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public int getId() {
		return this.id;
	}
	
	public int getEdad() {
		return Period.between(fechaNacimiento, LocalDate.now()).getYears();
	}
		

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + "]\n";
	}

	public String getNombre() {
		return nombre;
	}

	public static List<Persona> getAllPersonas() {
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1, "Alameda Badurak, Daniel", "25/11/1999"));
		personas.add(new Persona(2, "Andrade Gómez, Santiago", "15/10/2002"));
		personas.add(new Persona(3, "Benedicte Pérez, Héctor", "12/09/2000"));
		personas.add(new Persona(4, "Bernal Falcón, Álvaro Alberto", "22/01/1999"));
		personas.add(new Persona(5, "Borreguero Del Pozo, Sonia", "30/10/1998"));
		personas.add(new Persona(6, "Catalán Romero, Iván", "15/10/1994"));
		personas.add(new Persona(7, "Ciudad Arranz, Francisco de Asís", "15/07/2000"));
		personas.add(new Persona(8, "Fernández Cantón, Francisco Javier", "06/09/1991"));
		personas.add(new Persona(9, "González De Pablo, Carlos", "22/08/1996"));
		personas.add(new Persona(10, "Hasan , Jihad", "24/08/2001"));
		personas.add(new Persona(11, "Huertas Naranjo, Gabriela Elizabeth", "05/04/1993"));
		personas.add(new Persona(12, "Jiménez Sorrel, Aitor", "12/04/2001"));
		personas.add(new Persona(13, "López Moraga, Mario", "12/11/2001"));
		personas.add(new Persona(14, "Moreno Brasero, Jorge", "20/05/2002"));
		personas.add(new Persona(15, "Moreno Carriazo, Darío", "26/11/2003"));
		personas.add(new Persona(16, "Olmo Rivera, Rubén", "19/09/2001"));
		personas.add(new Persona(17, "Patrón Fernández, David", "27/02/1998"));
		personas.add(new Persona(18, "Peña Morate, Alejandro de la", "20/08/1998"));
		personas.add(new Persona(19, "Yatsyk Szumilas, Mateusz Ryszard", "30/10/2001"));
		return personas;
	}
}
