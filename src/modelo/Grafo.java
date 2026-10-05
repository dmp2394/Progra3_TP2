package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo<V> {

	private Map<V, Set<Arista<V>>> listaDeVecinos;
	private ArrayList<Arista<V>> aristas;

	public Grafo() {
		this.listaDeVecinos = new HashMap<>();
		this.aristas = new ArrayList<>();

	}

	public void agregarVertice(V vertice) {
		if (vertice == null)
			throw new IllegalArgumentException("Error: El vértice no puede ser nulo.");

		if (listaDeVecinos.containsKey(vertice))
			throw new IllegalArgumentException("Error: El vértice ya existe en el grafo.");

		// Crear la lista de listaDeVecinos vacía para ese vértice
		listaDeVecinos.put(vertice, new HashSet<>());
	}

	public void agregarArista(V vertice1, V vertice2, int peso) {
		if (!listaDeVecinos.containsKey(vertice1) || !listaDeVecinos.containsKey(vertice2))
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");

		if (vertice1.equals(vertice2))
			throw new IllegalArgumentException("Error: No se permiten loops (aristas de un vértice a sí mismo).");

		if (peso <= 0)
			throw new IllegalArgumentException("Error: No se permite agregar una arista con peso 0 o negativo.");

		Arista<V> arista = new Arista<V>(vertice1, vertice2, peso);

		for (Arista<V> aristaExistente : listaDeVecinos.get(vertice1))
			if (aristaExistente.equals(arista))
				throw new IllegalArgumentException("Error: La arista ya existe entre estos vértices.");

		listaDeVecinos.get(vertice1).add(arista);
		listaDeVecinos.get(vertice2).add(arista);
		aristas.add(arista);
		aristas.sort(Comparator.comparingInt(Arista::devolverPeso));

	}

	public void eliminarArista(V vertice1, V vertice2) {
		if (!listaDeVecinos.containsKey(vertice1) || !listaDeVecinos.containsKey(vertice2))
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");

		// Busca la arista guardada que une a vertice1 con vertice2 (en cualquier orden)
		Arista<V> buscada = new Arista<V>(vertice1, vertice2, 0);
		Arista<V> arista = null;
		for (Arista<V> aristaExistente : listaDeVecinos.get(vertice1))
			if (aristaExistente.equals(buscada))
				arista = aristaExistente;

		if (arista == null)
			throw new IllegalArgumentException("Error: La arista no existe entre estos vértices.");

		listaDeVecinos.get(vertice1).remove(arista);
		listaDeVecinos.get(vertice2).remove(arista);
		aristas.remove(arista);
	}

	public Map<V, Set<Arista<V>>> obtenerlistaDeVecinos() {
		Map<V, Set<Arista<V>>> copia = new HashMap<>();
		for (Map.Entry<V, Set<Arista<V>>> entrada : listaDeVecinos.entrySet())
			copia.put(entrada.getKey(), new HashSet<>(entrada.getValue()));

		return copia;
	}

	// Devuelve una copia de la lista de aristas
	public ArrayList<Arista<V>> obtenerAristas() {
		return new ArrayList<Arista<V>>(aristas);
	}

	public ArrayList<V> obtenerVertices() {

		ArrayList<V> vertices = new ArrayList<>();
		for (V vertice : listaDeVecinos.keySet())
			vertices.add(vertice);

		return vertices;
	}

	// Informa si el grafo no tiene vértices
	public boolean esVacio() {
		return listaDeVecinos.isEmpty();
	}

	// Suma de los pesos de todas las aristas del grafo
	public int pesoTotal() {
		int total = 0;
		for (Arista<V> arista : aristas)
			total += arista.devolverPeso();

		return total;
	}

	public boolean existeVertice(V vertice) {
		return listaDeVecinos.containsKey(vertice);
	}

	public boolean existeArista(V vertice1, V vertice2) {

		if (!existeVertice(vertice1) || !existeVertice(vertice2))
			throw new IllegalArgumentException("Error: Uno o ambos vertices no existen.");

		Set<Arista<V>> aristasDeVertice1 = listaDeVecinos.get(vertice1);

		for (Arista<V> unaArista : aristasDeVertice1)
			if (unaArista.obtenerExtremo1().equals(vertice1) && unaArista.obtenerExtremo2().equals(vertice2)
					|| unaArista.obtenerExtremo2().equals(vertice1) && unaArista.obtenerExtremo1().equals(vertice2)) {
				return true;
			}

		return false;
	}
	
	//Para eliminar vertice primerp verifica que el vertice exista , luego hace una lista de sus conexiones, las elimina y luego elimina el vertice.
	public void eliminarVertice(V vertice) {

	    if (!listaDeVecinos.containsKey(vertice)) {
	        throw new IllegalArgumentException(
	                "Error: El vértice no existe en el grafo.");
	    }

	    ArrayList<Arista<V>> aristasDelVertice =
	            new ArrayList<>(listaDeVecinos.get(vertice));

	    for (Arista<V> arista : aristasDelVertice) {
	        eliminarArista(
	                arista.obtenerExtremo1(),
	                arista.obtenerExtremo2());
	    }

	    listaDeVecinos.remove(vertice);
	}

}
