package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class KruskalTest {

	// Criterio: el AGM es correcto si
	// 1) tiene la misma cantidad de vértices que el grafo original
	// 2) tiene el peso total mínimo esperado
	// 3) tiene exactamente (cantidad de vértices - 1) aristas

	public static class CrearArbolGeneradorMinimoExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void grafoNullTest() {
			Kruskal.crearArbolGeneradorMinimo(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void grafoNoConexoTest() {
			Grafo<String> grafo = new Grafo<>();
			grafo.agregarVertice("A");
			grafo.agregarVertice("B");
			grafo.agregarVertice("C");
			grafo.agregarArista("A", "B", 1);

			Kruskal.crearArbolGeneradorMinimo(grafo);
		}

		@Test(expected = IllegalArgumentException.class)
		public void grafoVacioTest() {
			Grafo<String> grafo = new Grafo<>();

			Kruskal.crearArbolGeneradorMinimo(grafo);

		}

		@Test
		public void unSoloVerticeTest() {
			Grafo<String> grafo = new Grafo<>();
			grafo.agregarVertice("A");

			Grafo<String> agm = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(0, agm.pesoTotal());
		}

		@Test
		public void unSoloVerticeNoTieneAristasTest() {
			Grafo<String> grafo = new Grafo<>();
			grafo.agregarVertice("A");

			Grafo<String> agm = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(0, agm.obtenerAristas().size());
		}

		@Test
		public void grafoQueYaEsArbolTest() {
			Grafo<String> grafo = new Grafo<>();
			grafo.agregarVertice("A");
			grafo.agregarVertice("B");
			grafo.agregarVertice("C");
			grafo.agregarArista("A", "B", 5);
			grafo.agregarArista("B", "C", 7);

			Grafo<String> agm = Kruskal.crearArbolGeneradorMinimo(grafo);
			boolean esConexo = DFS.esConexo(agm);
			boolean aristaIgualVmenos1 = agm.obtenerAristas().size() == agm.obtenerVertices().size() - 1;
			boolean esArbol = esConexo && aristaIgualVmenos1;
			assertTrue(esArbol);
		}

		@Test
		public void pesosMinimoTest() {
			Grafo<String> grafo = new Grafo<>();
			grafo.agregarVertice("A");
			grafo.agregarVertice("B");
			grafo.agregarVertice("C");
			grafo.agregarArista("A", "B", 4);
			grafo.agregarArista("B", "C", 3);
			grafo.agregarArista("A", "C", 2);

			Grafo<String> agm = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(5, agm.pesoTotal());
		}

		@Test
		public void noModificaElGrafoOriginalTest() {
			Grafo<String> grafo = creacionDeGrafo();

			Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(3, grafo.obtenerAristas().size());
		}
	}

	public static class CrearArbolGeneradorMinimoCasoFeliz {

		@Test
		public void kruskalConGrafoTriangularTest() {
			// creacionDeGrafo: A-B (1), B-C (2), A-C (3) => AGM A-B y B-C, peso total 3
			Grafo<String> grafo = creacionDeGrafo();
			Grafo<String> resultado = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(3, resultado.pesoTotal());
		}

		@Test
		public void kruskalConGrafoTriangularConservaLosVerticesTest() {
			Grafo<String> grafo = creacionDeGrafo();
			Grafo<String> resultado = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(new HashSet<>(grafo.obtenerVertices()), new HashSet<>(resultado.obtenerVertices()));
		}

		@Test
		public void kruskalConGrafoDiapositivaTest() {
			// AGM esperado: H-G (1), C-I (3), G-F (3), A-B (4), C-F (4), C-D (6), A-H (8),
			// D-E (9)
			Grafo<String> grafo = creacionDeGrafoDiapositiva();
			Grafo<String> resultado = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(38, resultado.pesoTotal());
		}

		@Test
		public void kruskalConGrafoDiapositivaConservaLosVerticesTest() {
			Grafo<String> grafo = creacionDeGrafoDiapositiva();
			Grafo<String> resultado = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(new HashSet<>(grafo.obtenerVertices()), new HashSet<>(resultado.obtenerVertices()));
		}

		@Test
		public void grafoTriangularCantidadDeAristasTest() {
			Grafo<String> grafo = creacionDeGrafo();
			Grafo<String> resultado = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(grafo.obtenerVertices().size() - 1, resultado.obtenerAristas().size());
		}

		@Test
		public void grafoDiapositivaCantidadDeAristasTest() {
			Grafo<String> grafo = creacionDeGrafoDiapositiva();
			Grafo<String> resultado = Kruskal.crearArbolGeneradorMinimo(grafo);

			assertEquals(grafo.obtenerVertices().size() - 1, resultado.obtenerAristas().size());
		}
	}

	public static Grafo<String> creacionDeGrafo() {
		Grafo<String> grafo = new Grafo<>();
		String verticeA = "A";
		String verticeB = "B";
		String verticeC = "C";
		grafo.agregarVertice(verticeA);
		grafo.agregarVertice(verticeB);
		grafo.agregarVertice(verticeC);
		grafo.agregarArista(verticeA, verticeB, 1);
		grafo.agregarArista(verticeB, verticeC, 2);
		grafo.agregarArista(verticeA, verticeC, 3);
		return grafo;
	}

	public static Grafo<String> creacionDeGrafoDiapositiva() {
		Grafo<String> grafo = new Grafo<>();
		grafo.agregarVertice("A");
		grafo.agregarVertice("B");
		grafo.agregarVertice("C");
		grafo.agregarVertice("D");
		grafo.agregarVertice("E");
		grafo.agregarVertice("F");
		grafo.agregarVertice("G");
		grafo.agregarVertice("H");
		grafo.agregarVertice("I");
		grafo.agregarArista("A", "B", 4);
		grafo.agregarArista("A", "H", 8);
		grafo.agregarArista("B", "C", 8);
		grafo.agregarArista("B", "H", 12);
		grafo.agregarArista("C", "D", 6);
		grafo.agregarArista("C", "I", 3);
		grafo.agregarArista("C", "F", 4);
		grafo.agregarArista("D", "E", 9);
		grafo.agregarArista("D", "F", 13);
		grafo.agregarArista("E", "F", 10);
		grafo.agregarArista("H", "I", 6);
		grafo.agregarArista("H", "G", 1);
		grafo.agregarArista("I", "G", 5);
		grafo.agregarArista("G", "F", 3);
		return grafo;
	}

	public static class BuscarRaizExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void verticeNoExisteTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "A");
			padre.put("C", "B");
			Kruskal.buscarRaiz(padre, "D");
		}
	}

	public static class BuscarRaizCasoFeliz {

		@Test
		public void buscarRaizTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "A");
			padre.put("C", "B");

			String raiz = Kruskal.buscarRaiz(padre, "C");
			assertEquals("A", raiz);
		}

		@Test
		public void buscarRaizDeVerticeQueEsRaizTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "A");
			padre.put("C", "B");

			String raiz = Kruskal.buscarRaiz(padre, "A");
			assertEquals("A", raiz);
		}

	}

	public static class UnirExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void unirRaicesQueNoExistenTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "A");
			padre.put("C", "B");

			Map<String, Integer> rango = new HashMap<>();
			rango.put("A", 1);
			rango.put("B", 0);
			rango.put("C", 0);

			Kruskal.unir(padre, rango, "D", "E");
		}
	}

	public static class UnirCasoFeliz {

		// Rango de raiz1 mayor: raiz2 pasa a colgar de raiz1.
		@Test
		public void unirRaicesTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "B");

			Map<String, Integer> rango = new HashMap<>();
			rango.put("A", 1);
			rango.put("B", 0);

			Kruskal.unir(padre, rango, "A", "B");

			assertEquals("A", padre.get("B"));
		}

		// Rango de raiz1 menor: raiz1 pasa a colgar de raiz2.
		@Test
		public void unirRaizConMenorRangoTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "B");

			Map<String, Integer> rango = new HashMap<>();
			rango.put("A", 0);
			rango.put("B", 1);

			Kruskal.unir(padre, rango, "A", "B");

			Map<String, String> padreEsperado = new HashMap<>();
			padreEsperado.put("A", "B");
			padreEsperado.put("B", "B");

			assertEquals(padreEsperado, padre);
		}

		// Rangos iguales: raiz2 pasa a colgar de raiz1 y el rango de raiz1 aumenta en 1.
		@Test
		public void unirRaicesConIgualRangoTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "B");

			Map<String, Integer> rango = new HashMap<>();
			rango.put("A", 0);
			rango.put("B", 0);

			Kruskal.unir(padre, rango, "A", "B");

			Map<String, String> padreEsperado = new HashMap<>();
			padreEsperado.put("A", "A");
			padreEsperado.put("B", "A");

			assertEquals(padreEsperado, padre);
		}

		@Test
		public void unirRaicesConIgualRangoAumentaElRangoTest() {
			Map<String, String> padre = new HashMap<>();
			padre.put("A", "A");
			padre.put("B", "B");

			Map<String, Integer> rango = new HashMap<>();
			rango.put("A", 0);
			rango.put("B", 0);

			Kruskal.unir(padre, rango, "A", "B");

			Map<String, Integer> rangoEsperado = new HashMap<>();
			rangoEsperado.put("A", 1);
			rangoEsperado.put("B", 0);

			assertEquals(rangoEsperado, rango);
		}
	}

}
