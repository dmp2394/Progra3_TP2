package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)

public class DFSTest {
	private static String provincia1 = "Buenos Aires";
	private static String provincia2 = "Santa Fé";
	private static String provincia3 = "La Pampa";
	private static String provincia4 = "Entre Ríos";

	public static class EsConexoExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void esConexoGrafoNullTest() {
			DFS.esConexo(null);
		}

		@Test
		public void esConexoSinVerticesTest() {
			assertTrue(DFS.esConexo(grafo));
		}

		@Test
		public void esConexoConUnSoloVerticeTest() {
			grafo.agregarVertice(provincia1);

			assertTrue(DFS.esConexo(grafo));
		}

		@Test
		public void esInconexoConVerticesSinAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);

			assertFalse(DFS.esConexo(grafo));
		}

		@Test
		public void esInconexoDespuesDeEliminarLaUnicaAristaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 10);

			grafo.eliminarArista(provincia1, provincia2);

			assertFalse(DFS.esConexo(grafo));
		}
	}

	public static class EsConexoCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void esConexoCasoFeliz() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);

			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia2, provincia3, 20);
			grafo.agregarArista(provincia3, provincia4, 30);

			assertTrue(DFS.esConexo(grafo));
		}

		@Test
		public void esInconexoCasoFeliz() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);

			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia3, provincia4, 30);

			assertFalse(DFS.esConexo(grafo));
		}
		
		
	}

	public static class RecorrerExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void recorrerGrafoNullTest() {
			DFS.recorrer(null, provincia1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void recorrerOrigenNullTest() {
			grafo.agregarVertice(provincia1);

			DFS.recorrer(grafo, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void recorrerVerticeNoPerteneceAlGrafoTest() {
			grafo.agregarVertice(provincia1);

			DFS.recorrer(grafo, "Córdoba");
		}

		@Test(expected = IllegalArgumentException.class)
		public void recorrerGrafoVacioTest() {
			DFS.recorrer(grafo, provincia1);
		}

		@Test
		public void recorrerVerticeAisladoTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);

			Set<String> visitados = DFS.recorrer(grafo, provincia1);

			assertEquals(new HashSet<>(Arrays.asList(provincia1)), visitados);
		}

		@Test
		public void recorrerGrafoConCicloTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);

			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia2, provincia3, 20);
			grafo.agregarArista(provincia3, provincia1, 30);

			Set<String> visitados = DFS.recorrer(grafo, provincia1);

			assertEquals(3, visitados.size());
		}

		@Test
		public void recorrerNoVisitaVerticesDeOtraComponenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);

			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia3, provincia4, 20);

			Set<String> visitados = DFS.recorrer(grafo, provincia1);

			assertEquals(new HashSet<>(Arrays.asList(provincia1, provincia2)), visitados);
		}
	}

	public static class RecorrerCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void recorrerDesdeUnExtremoCasoFeliz() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);

			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia2, provincia3, 20);
			grafo.agregarArista(provincia3, provincia4, 30);

			Set<String> visitados = DFS.recorrer(grafo, provincia1);

			assertEquals(new HashSet<>(Arrays.asList(provincia1, provincia2, provincia3, provincia4)), visitados);
		}

		@Test
		public void recorrerDesdeUnVerticeIntermedioCasoFeliz() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);

			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia2, provincia3, 20);
			grafo.agregarArista(provincia3, provincia4, 30);

			Set<String> visitados = DFS.recorrer(grafo, provincia3);

			assertEquals(4, visitados.size());
		}
	}
}
