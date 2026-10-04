package modelo;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

public class Kruskal <V>  {
	
	
	
	public static<V> Grafo<V> generarArbolMinimo(Grafo<V> grafo) {
		ArrayList<Arista<V>> aristasOriginales = grafo.obtenerAristas();
		
		ArrayList<V> verticesGrafoMin = grafo.obtenerVertices();
		Grafo<V> arbolMinimo = new Grafo<>();
		
		Map<V, V> padre = new HashMap<>();
		Map<V, Integer> rango = new HashMap<>();
	
		// Agregar todos los vértices al resultado
		// y crear un conjunto independiente para cada uno
		for (V vertice : verticesGrafoMin) {
			arbolMinimo.agregarVertice(vertice);
		
			padre.put(vertice, vertice);
			rango.put(vertice, 0);
		}
		
		int cantidadAristas = 0;
		int aristasNecesarias = grafo.obtenerVertices().size() - 1;
		
		// Recorrer las aristas ordenadas
		for ( Arista <V>arista : aristasOriginales) {
		
			V vertice1 = (V) arista.obtenerExtremo1();
			V vertice2 = (V) arista.obtenerExtremo2();
		
			V raiz1 = buscarRaiz(padre, vertice1);
			V raiz2 = buscarRaiz(padre, vertice2);
		
			/*
			* Si tienen raíces diferentes, todavía no están conectados.
			* Por lo tanto, la arista no genera un ciclo.
			*/
			if (!raiz1.equals(raiz2)) {
			
				arbolMinimo.agregarArista(vertice1,	vertice2,arista	);
				
				unir(padre, rango, raiz1, raiz2);
				cantidadAristas++;
				
				// El árbol ya está completo
				if (cantidadAristas == aristasNecesarias) {
					break;
				}
			}
		}
		
	
		if (cantidadAristas != aristasNecesarias) {
			throw new IllegalArgumentException(	"El grafo no es conexo. No existe un árbol generador.");
		}
		return arbolMinimo;
		}
	
	
	private static <V> void unir(Map<V, V> padre,Map<V, Integer> rango,V raiz1,V raiz2) {
			
			int rango1 = rango.get(raiz1);
			int rango2 = rango.get(raiz2);
			
			if (rango1 < rango2) {

				padre.put(raiz1, raiz2);
			} 
			
			else if (rango1 > rango2) {
				padre.put(raiz2, raiz1);
			}
			
			else {
				padre.put(raiz2, raiz1);
				rango.put(raiz1, rango1 + 1);
			}
		}
	
	
	private static <V> V buscarRaiz(Map<V, V> padre, V vertice) {
		if (!padre.get(vertice).equals(vertice)) {
			V raiz = buscarRaiz(padre, padre.get(vertice));
			padre.put(vertice, raiz);
			}
		return padre.get(vertice);
		
		}

	
	}
	

	

	
	


