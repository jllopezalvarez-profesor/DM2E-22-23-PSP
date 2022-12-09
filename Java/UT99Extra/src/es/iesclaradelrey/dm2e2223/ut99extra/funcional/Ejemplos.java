package es.iesclaradelrey.dm2e2223.ut99extra.funcional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ejemplos {

	public static void main(String[] args) {

		List<Persona> personas = Persona.getAllPersonas();

		System.out.println("Imperativa");
		for (Persona persona : personas) {
			if (persona.getEdad() > 25)
				System.out.print(persona);
		}

		// Programción imperativa for / foreach

		// stream

		// Declarativo

		// Foreach - Con lambda o con referencia a método
		System.out.println("Funcional");
		personas.stream().forEach(x -> System.out.println(x.getEdad()));

		System.out.println("Funcional 2");
		personas.stream().forEach(System.out::println);

		// Filter - Predicados
		System.out.println("Filtrado > 25");
		List<Persona> filtradas = personas.stream().filter(x -> x.getEdad() > 25).toList();
		// List<Persona> filtradas = personas.stream().filter(x -> x.getEdad() > 25).collect(Collectors.toList());
		System.out.println(filtradas);

		System.out.println("Filtrado > 25 y nombre contiene C");
		List<Persona> filtradas2 = personas.stream().filter(x -> x.getEdad() > 25)
				.filter(x -> x.getNombre().contains("C")).collect(Collectors.toList());
		System.out.println(filtradas2);

		int edad = 0; // No se quiere filtrar por edad
		String letraNombre = ""; // Sí se quiere filtrar por nombre
		var filtradas3 = personas.stream();
		if (edad > 0)
			filtradas3 = filtradas3.filter(x -> x.getEdad() > edad);

		if (!letraNombre.isEmpty()) {
			filtradas3 = filtradas3.filter(x -> x.getNombre().contains(letraNombre));
		}

		System.out.printf("Se han encontrado %d personas\n", filtradas3.count());

		// Buscar uno
		int idBuscado = 4;
		Optional<Persona> p = personas.stream().filter(x -> x.getId() == idBuscado).findFirst();

		System.out.printf("¿Se ha encontrado la persona con id %d? %b\n", idBuscado, p.isPresent());

		// Map - Obtener parcialmente
		List<Integer> edades = personas.stream().map(per -> per.getEdad()).filter(ed -> ed > 21)
				.collect(Collectors.toList());
		System.out.println(edades);

		// Ordenar - Sorted - Comparator<T>
		Comparator<Persona> comparador = (x, y) -> Integer.compare(x.getId(), y.getId());
		var alumnosOrdenados1 = personas.stream().sorted(comparador).collect(Collectors.toList());
		System.out.println(alumnosOrdenados1);
		var alumnosOrdenados2 = personas.stream().sorted((x, y) -> Integer.compare(x.getEdad(), y.getEdad()))
				.collect(Collectors.toList());
		System.out.println(alumnosOrdenados2);

		// Encontrar - matchs - any none all

		boolean existeAsis = personas.stream().anyMatch(per -> per.getNombre().contains("Asís"));
		System.out.println(existeAsis);
		boolean todosMayoresEdad = personas.stream().allMatch(per -> per.getEdad() >= 18);
		System.out.println(todosMayoresEdad);

		// Paginar / Skip / Limit

		var cuatroPrimeros = personas.stream().limit(4).toList();
		System.out.println(cuatroPrimeros);
		var cuatroSegundos = personas.stream().skip(4).limit(4).toList();
		System.out.println(cuatroSegundos);

		//

//		Persona pp = new Persona(1, "", "");
//		Persona pp2 = new Persona(1, "", "");
//
//		metodo(pp);
//

//

		System.out.println("Obtener datos agrupados por edad");
		Map<Integer, List<Persona>> agrupado = personas.stream().collect(Collectors.groupingBy(per -> per.getEdad()));
		for (var itemGroup : agrupado.entrySet()) {

			System.out.println("Edad " + itemGroup.getKey());
			System.out.println("Personas");
			System.out.println(itemGroup.getValue());

		}

		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce página que quieres ver: ");
		int pagina = Integer.parseInt(sc.nextLine());
		while (pagina > 0) {
			System.out.println(paginar(personas, pagina, 5));
			System.out.println("Introduce página que quieres ver: ");
			pagina = Integer.parseInt(sc.nextLine());
		}

		// Estadísticas de edad
		var estadisticas = personas.stream().collect(Collectors.summarizingInt(per -> {
			int eeee = per.getEdad();
			if (eeee % 2 == 0)
				eeee = eeee + 1;
			return eeee;
		}));
		System.out.println(estadisticas);

	}

	public static void metodo(Object cosa, Object cosa2) {
		System.out.println(cosa);
	}

	private static List<Persona> paginar(List<Persona> personas, int numPagina, int itemsPorPagina) {
		long saltar = (numPagina - 1) * itemsPorPagina;
		return personas.stream().skip(saltar).limit(itemsPorPagina).toList();
	}
}
