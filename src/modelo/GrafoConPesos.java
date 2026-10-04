package modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GrafoConPesos<V> {

	private Map<V, Map<V, Integer>> listaDeVecinosConPeso;

	public GrafoConPesos() {
		listaDeVecinosConPeso = new HashMap<>();
	}

	public void agregarVertice(V vertice) {
		if (vertice == null)
			throw new IllegalArgumentException("El vértice no puede ser null.");

		if (listaDeVecinosConPeso.containsKey(vertice))
			throw new IllegalArgumentException("El vértice ya existe.");

		listaDeVecinosConPeso.put(vertice, new HashMap<>());
	}

	public void agregarArista(V vertice1, V vertice2, int peso) {
		verificarVertice(vertice1);
		verificarVertice(vertice2);

		if (peso <= 0)
			throw new IllegalArgumentException("No se permite agregar una arista con peso 0 o negativo.");

		if (vertice1.equals(vertice2))
			throw new IllegalArgumentException("No se permiten loops (aristas de un vértice a sí mismo).");

		listaDeVecinosConPeso.get(vertice1).put(vertice2, peso);
		listaDeVecinosConPeso.get(vertice2).put(vertice1, peso);
	}

	public void eliminarArista(V vertice1, V vertice2) {
		verificarVertice(vertice1);
		verificarVertice(vertice2);

		listaDeVecinosConPeso.get(vertice1).remove(vertice2);
		listaDeVecinosConPeso.get(vertice2).remove(vertice1);
	}

	// Informa si existe la arista especificada
	public boolean existeArista(V vertice1, V vertice2) {
		verificarVertice(vertice1);
		verificarVertice(vertice2);

		if (vertice1.equals(vertice2))
			throw new IllegalArgumentException("No se permiten loops (aristas de un vértice a sí mismo).");

		return listaDeVecinosConPeso.get(vertice1).containsKey(vertice2);
	}

	// Vecinos de un vertice
	public Set<V> vecinos(V vertice) {
		verificarVertice(vertice);

		Set<V> ret = new HashSet<>();

		for (V vecino : listaDeVecinosConPeso.get(vertice).keySet()) {
			ret.add(vecino);
		}

		return ret;
	}

	// Validaciones
	private void verificarVertice(V vertice) {
		if (vertice == null)
			throw new IllegalArgumentException("El vértice no puede ser nulo.");

		if (!listaDeVecinosConPeso.containsKey(vertice))
			throw new IllegalArgumentException("El vértice " + vertice + " no existe en el GrafoConPesos.");

	}

	public int tamano() {
		return listaDeVecinosConPeso.size();
	}

	public int grado(V vertice) {
		verificarVertice(vertice);
		return listaDeVecinosConPeso.get(vertice).size();
	}

	public boolean existeVertice(V provincia1) {
		return listaDeVecinosConPeso.containsKey(provincia1);
	}

}
