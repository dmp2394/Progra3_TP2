package modelo;

import static org.junit.Assert.*;

import static org.junit.Assert.fail;


import org.junit.Test;

public class KruskalTest {

	@Test

	public void testKruskal()
	{
		Grafo<String> grafo =creacionDeGrafo();
		Grafo<String> resultado = Kruskal.generarArbolMinimo(grafo);
		assertNotNull(resultado);
		
	}
	public Grafo<String> creacionDeGrafo() {
		Grafo<String> grafo = new Grafo<>();
		String verticeA =  "A";
		String verticeB =  "B";
		String verticeC =  "C";
		grafo.agregarVertice(verticeA);
		grafo.agregarVertice(verticeB);
		grafo.agregarVertice(verticeC);
		Arista<String> aristaAB = new Arista<>(verticeA, verticeB, 1);
		Arista<String> aristaBC = new Arista<>(verticeB, verticeC, 2);
		Arista<String> aristaAC = new Arista<>(verticeA, verticeC, 3);
		grafo.agregarArista(verticeA, verticeB, aristaAB);
		grafo.agregarArista(verticeB, verticeC, aristaBC);
		grafo.agregarArista(verticeA, verticeC, aristaAC);
		return grafo;
	}
	
	

	public void test() {
		fail("Not yet implemented");
	}

}
