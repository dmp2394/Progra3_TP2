package modelo;
import java.util.Map;
import java.util.Set;
public class ArbolGeneradorMinimo <V,A>  {
	// Implementación del algoritmo de Prim o Kruskal para encontrar el árbol generador mínimo
	// Este es un ejemplo simple y no funcional, solo para ilustrar la estructura de la clase.
	private Set<A> aristas;
	

	public ArbolGeneradorMinimo()  {
		// Con
	}
	
	
	public Grafo generarArbol(Grafo grafo) {
		Set<Arista> aristasGrafoOriginal = grafo.obtenerAristas();
		Set<V> verticesGrafoOriginal = grafo.obtenerVertices();
		Set<A> aristasGrafoMin;
		Set<V> verticesGrafoMin;
		Grafo arbolMinimo = new Grafo();
		int contador = 0;
		
		while (contador < grafo.obtenerVertices().size()) {
		for(Arista arista : aristasGrafoOriginal) {
			int pesoMinimo= Integer.MAX_VALUE;
			Arista aristaMinima = null;
			if (arista.devolverPeso()< pesoMinimo) {
				aristaMinima = arista;
							}}
			
		
			
		}
			return arbolMinimo;
		}
		
		
		
	
	public Set<V> guardarVertices( , Grafo destinoVertices) {
		
		return null;
	}}
	
	
}
