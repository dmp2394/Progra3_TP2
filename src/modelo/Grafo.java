package modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class Grafo<V,A> {

	private Integer[][] AP;

	private Map<V, Set<A>> adyacencia;

	public Grafo(int vertices) {
		AP = new Integer[vertices][vertices];
		adyacencia = new HashMap<>();
	}
	

	public Grafo() {
		adyacencia = new HashMap<>();
	}

	public void agregarVertice1(V vertice) {
		if (vertice == null) {
			throw new IllegalArgumentException("El vértice no puede ser nulo.");
		}
		if (adyacencia.containsKey(vertice)) {
			throw new IllegalArgumentException("Error: El vértice ya existe en el grafo.");
		}
		// Crear la lista de adyacencia vacía para ese vértice
		adyacencia.put(vertice, new HashSet<>());
	}
	

	public void agregarArista(V origen, V destino, A arista) {
		if (!adyacencia.containsKey(origen) || !adyacencia.containsKey(destino)) {
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");
		}
		if (origen.equals(destino)) {
			throw new IllegalArgumentException("Error: No se permiten loops (aristas de un vértice a sí mismo).");	
		}
		if (adyacencia.get(origen).containsKey(destino)) {
			throw new IllegalArgumentException("Error: La arista ya existe en el grafo.");
		}
		adyacencia.get(origen).put(destino, arista);
		adyacencia.get(destino).put(origen, arista);
		}
	

	public void eliminarArista(Provincia origen, Provincia destino) {
		Map aristasOrigen = adyacencia.get(origen);
	    Map aristasDestino = adyacencia.get(destino);
	    if (aristasOrigen == null || aristasDestino == null) {
	        throw new IllegalArgumentException("Error: Una o ambas provincias no existen en el grafo.");
	    }
	    if (aristasOrigen.remove(destino) == null) {
	        throw new IllegalArgumentException("Error: La arista no existe entre estas provincias.");
	    }
	    aristasDestino.remove(origen);
	}

	// Agregado de aristas
	public void agregarAristaConPeso(int i, int j, int peso) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		AP[i][j] = peso;
		AP[j][i] = peso;
	}

	// Eliminacion de aristas
	public void eliminarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		AP[i][j] = null;
		AP[j][i] = null;
	}

	// Informa si existe la arista especificada
	public boolean existeArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		return AP[i][j] != null;
	}
	public boolean existeArista(V provincia1, V provincia2) {
		
		return adyacencia.get(provincia1).containsKey(provincia2);
	}

	// Cantidad de vertices
	public int tamano() {
		return AP.length;
	}

	// Vecinos de un vertice
	public Set<Integer> vecinos(int i) {
		verificarVertice(i);

		Set<Integer> ret = new HashSet<Integer>();
		for (int j = 0; j < this.tamano(); ++j)
			if (i != j) {
				if (this.existeArista(i, j))
					ret.add(j);
			}

		return ret;
	}
	public boolean existeVertice(Provincia provincia) {
		return adyacencia.containsKey(provincia);
	}

	// Verifica que sea un vertice valido
	private void verificarVertice(int i) {
		if (i < 0)
			throw new IllegalArgumentException("El vertice no puede ser negativo: " + i);

		if (i >= AP.length)
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + i);
	}

	// Verifica que i y j sean distintos
	private void verificarDistintos(int i, int j) {
		if (i == j)
			throw new IllegalArgumentException("No se permiten loops: (" + i + ", " + j + ")");
	}
}
