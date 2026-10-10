package modelo;

import java.util.HashSet;
import java.util.Set;

public class DFS {

    public static <V> boolean esConexo(Grafo<V> grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser null");
        }
        
        if (grafo.esVacio()) {
            return true;
            }

        V origen = grafo.obtenerVertices().iterator().next();
        Set<V> visitados = recorrer(grafo, origen);
        return visitados.size() == grafo.obtenerVertices().size();
    }

    public static <V> Set<V> recorrer(Grafo<V> grafo, V origen) {
    	
    	   if (grafo == null) {
    	        throw new IllegalArgumentException("El grafo no puede ser null");
    	    }

    	    if (origen == null) {
    	        throw new IllegalArgumentException("El vertice origen no puede ser null");
    	    }

    	    if (!grafo.existeVertice(origen)) {
    	        throw new IllegalArgumentException("El vertice no pertenece al grafo");
    	    }

        Set<V> visitados = new HashSet<>();
        dfs(grafo, origen, visitados);

        return visitados;
    }

    private static <V> void dfs(Grafo<V> grafo, V vertice, Set<V> visitados) {

    	 visitados.add(vertice);

    	    for (V vecino : grafo.obtenerVecinos(vertice)) {

    	        if (!visitados.contains(vecino)) {
    	            dfs(grafo, vecino, visitados);
            }
        }
    }
}