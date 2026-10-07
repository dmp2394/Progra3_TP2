package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo<V> {

	private Map<V, Set<Arista<V>>> listaDeVecinos;
	private ArrayList<Arista<V>> aristas;

	public Grafo() {
		listaDeVecinos = new HashMap<>();
		aristas = new ArrayList<>();

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

		Collections.sort(aristas);

	}

	public void eliminarArista(V vertice1, V vertice2) {
		if (!listaDeVecinos.containsKey(vertice1) || !listaDeVecinos.containsKey(vertice2))
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");

		// los vertices se colocan en el objeto Arista en variable "buscada" para
		// ejecutar el equals
		int pesoFicticio = 100;
		Arista<V> buscada = new Arista<V>(vertice1, vertice2, pesoFicticio);
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

	public ArrayList<Arista<V>> obtenerAristas() {
		return new ArrayList<Arista<V>>(aristas);
	}

	public ArrayList<V> obtenerVertices() {

		ArrayList<V> vertices = new ArrayList<>();
		for (V vertice : listaDeVecinos.keySet())
			vertices.add(vertice);

		return vertices;
	}

	public boolean esVacio() {
		return listaDeVecinos.isEmpty();
	}

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

		int pesoFicticio = 100;
		Arista<V> buscada = new Arista<V>(vertice1, vertice2, pesoFicticio);
		for (Arista<V> unaArista : aristasDeVertice1)
			if (unaArista.equals(buscada))
				return true;

		return false;
	}

	public void eliminarVertice(V vertice) {
		if (vertice == null) {
			throw new IllegalArgumentException("Error: No se puede borrar el vertice null");
		}

		if (!listaDeVecinos.containsKey(vertice)) {
			throw new IllegalArgumentException("Error: El vértice no existe en el grafo.");
		}

		ArrayList<Arista<V>> aristasDelVertice = new ArrayList<>(listaDeVecinos.get(vertice));

		for (Arista<V> arista : aristasDelVertice) {
			eliminarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());
		}

		listaDeVecinos.remove(vertice);
	}

}
