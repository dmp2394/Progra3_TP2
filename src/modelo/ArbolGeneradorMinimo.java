package modelo;
import java.util.Map;
import java.util.Set;
public class ArbolGeneradorMinimo <V,A> {
	// Implementación del algoritmo de Prim o Kruskal para encontrar el árbol generador mínimo
	// Este es un ejemplo simple y no funcional, solo para ilustrar la estructura de la clase.
	
	public ArbolGeneradorMinimo() {
		// Constructor
	}
	
	public Grafo generarArbol(Grafo grafo) {
		Map <V, Set<A>> adyacencia = grafo.obtenerAdyacencia();
		Grafo arbolMinimo = new Grafo();
		
		
		// Lógica para generar el árbol generador mínimo a partir del grafo dado
		return arbolMinimo;
	}
	
	
}
