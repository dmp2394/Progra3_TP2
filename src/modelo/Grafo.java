package modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Grafo {
	// Grafo con aristas con pesos, se implementa con matriz de adyacencia con
	// pesos, donde si una celda tiene un nro != null, hay vertice y tiene ese peso.
	// Se implementa con Integer para que los vertices sin arista valgan null
	private Integer[][] AP;
	private Provincia vertice;
	private Ruta arista;
	private HashMap<Provincia, HashMap<Provincia, Ruta>> adyacencia;
	
	
	// La cantidad de vertices esta predeterminada desde el constructor
	public Grafo(int vertices) {
		AP = new Integer[vertices][vertices];
	}
	
	public Grafo() {
		adyacencia = new HashMap<>();
	}
	
	public void agregarVertice(Provincia provincia) {
		if (provincia == null) {
	        throw new IllegalArgumentException("La provincia no puede ser nula.");
	    }
	    if (adyacencia.containsKey(provincia)) {
	        throw new IllegalArgumentException("Error: La provincia '" + provincia + "' ya existe en el grafo.");
	    }
		adyacencia.putIfAbsent(provincia, new HashMap<>());
	}
	
	public void agregarArista(Provincia origen, Provincia destino, int distancia) {
		Ruta ruta = new Ruta(distancia);
		adyacencia.get(origen).put(destino, ruta);
		adyacencia.get(destino).put(origen, ruta);
		}
	
	public void eliminarArista(Provincia origen, Provincia destino) {
		adyacencia.get(origen).remove(destino);
		adyacencia.get(destino).remove(origen);
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
