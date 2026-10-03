package modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Grafo<V,A> {

	private Map<V, Set<A>> adyacencia;
	private Set<A> aristas;
	private Set<V> vertices;

	public Grafo() {
		adyacencia = new HashMap<>();
		aristas = new HashSet<>();
		vertices = new HashSet<>();
	}

	
	public void agregarVertice(V vertice) {
		if (vertice == null) {
			throw new IllegalArgumentException("El vértice no puede ser nulo.");
		}
		if (adyacencia.containsKey(vertice)) {
			throw new IllegalArgumentException("Error: El vértice ya existe en el grafo.");
		}
		// Crear la lista de adyacencia vacía para ese vértice
		adyacencia.put(vertice, new HashSet<>());
	}
	

	public void agregarArista(V vertice1, V vertice2, A arista) {
		if (!adyacencia.containsKey(vertice1) || !adyacencia.containsKey(vertice2)) {
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");
		}
		if (vertice1.equals(vertice2)) {
			throw new IllegalArgumentException("Error: No se permiten loops (aristas de un vértice a sí mismo).");	
		}
		for (A aristaExistente : adyacencia.get(vertice1)) {
			if (aristaExistente.equals(arista)) {
				throw new IllegalArgumentException("Error: La arista ya existe entre estos vértices.");
			}}
			adyacencia.get(vertice1).add(arista);
			adyacencia.get(vertice2).add(arista);
			aristas.add(arista);
			
		}
	
	
	public void eliminarArista(V vertice1, V vertice2, A arista) {
	    if (!adyacencia.containsKey(vertice1) || !adyacencia.containsKey(vertice2)) {
	        throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");
	    }
	    if (!adyacencia.get(vertice1).contains(arista) || !adyacencia.get(vertice2).contains(arista)) {
	        throw new IllegalArgumentException("Error: La arista no existe entre estos vértices.");
	    }
	    
	    adyacencia.get(vertice1).remove(arista);
	    adyacencia.get(vertice2).remove(arista);
	}
	 public Map<V, Set<A>> obtenerAdyacencia() {
	        return adyacencia;
	    }
	 
	 public Set<V> obtenerVertices() {
	        return vertices;
	    }
	 
	 public Set<A> obtenerAristas() {
	        return aristas;
	    }

	
	/*public boolean existeArista(V provincia1, V provincia2) {
		
		return adyacencia.get(provincia1).containsKey(provincia2);
	}*/

	
	public boolean existeVertice(V vertice) {
		return adyacencia.containsKey(vertice);
	}


}
