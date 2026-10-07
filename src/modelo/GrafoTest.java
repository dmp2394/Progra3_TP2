package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class GrafoTest {

	private static String provincia1 = "Buenos Aires";
	private static String provincia2 = "Santa Fé";
	private static String provincia3 = "La Pampa";
	private static String provincia4 = "Entre Ríos";

	public static class AgregarVerticeExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeNuloTest() {
			grafo.agregarVertice(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeYaExistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia1);
		}
	}

	public static class AgregarVerticeHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void agregarVerticeTest() {
			grafo.agregarVertice(provincia1);
			assertTrue(grafo.existeVertice(provincia1));
		}
	}

	public static class AgregarAristaExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaAmbosVerticesNoExistenTest() {
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaVerticeNullTest() {
			grafo.agregarArista(null, null, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarLoopTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarArista(provincia1, provincia1, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaAlRevesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia2, provincia1, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPesoCeroTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);

			grafo.agregarArista(provincia1, provincia2, 0);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPesoNegativoTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);

			grafo.agregarArista(provincia1, provincia2, -100);
		}
	}

	public static class AgregarAristaHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void agregarAristaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertTrue(grafo.existeArista(provincia1, provincia2));
			assertTrue(grafo.existeArista(provincia2, provincia1));
		}
	}

	public static class EliminarAristaExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(provincia2);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(provincia1);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaAmbosVerticesNoExistenTest() {
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaVerticeNuloTest() {
			grafo.agregarVertice(provincia1);
			grafo.eliminarArista(provincia1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaInexistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaDosVecesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia1, provincia2);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test
		public void eliminarAristaConVerticesInvertidosTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia2, provincia1);

			assertFalse(grafo.existeArista(provincia1, provincia2));
			assertFalse(grafo.existeArista(provincia2, provincia1));
		}
	}

	public static class EliminarAristaHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void eliminarAristaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia1, provincia2);

			assertFalse(grafo.existeArista(provincia1, provincia2));
			assertFalse(grafo.existeArista(provincia2, provincia1));
		}

		@Test
		public void eliminarAristaLaSacaDeLaListaDeAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia1, provincia2);

			assertEquals(0, grafo.obtenerAristas().size());
		}

		@Test
		public void eliminarAristaNoAfectaOtrasAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia2, provincia3, 50);
			grafo.eliminarArista(provincia1, provincia2);

			assertTrue(grafo.existeArista(provincia2, provincia3));
			assertEquals(1, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerListaDeVecinosExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerListaDeVecinosGrafoVacioTest() {
			assertTrue(grafo.obtenerlistaDeVecinos().isEmpty());
		}

		@Test
		public void obtenerListaDeVecinosVerticesAisladosTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);

			assertEquals(2, grafo.obtenerlistaDeVecinos().size());
			assertTrue(grafo.obtenerlistaDeVecinos().get(provincia1).isEmpty());
			assertTrue(grafo.obtenerlistaDeVecinos().get(provincia2).isEmpty());
		}

		@Test
		public void obtenerListaDeVecinosDevuelveCopiaDelMapaTest() {
			grafo.agregarVertice(provincia1);
			grafo.obtenerlistaDeVecinos().remove(provincia1);

			assertTrue(grafo.existeVertice(provincia1));
		}

		@Test
		public void obtenerListaDeVecinosDevuelveCopiaDeLosConjuntosTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.obtenerlistaDeVecinos().get(provincia1).clear();

			assertTrue(grafo.existeArista(provincia1, provincia2));
		}
	}

	public static class ObtenerListaDeVecinosHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerListaDeVecinosAristaEnAmbosExtremosTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertEquals(1, grafo.obtenerlistaDeVecinos().get(provincia1).size());
			assertEquals(1, grafo.obtenerlistaDeVecinos().get(provincia2).size());
			assertTrue(grafo.obtenerlistaDeVecinos().get(provincia3).isEmpty());
		}
	}

	public static class ObtenerAristasExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerAristasGrafoVacioTest() {
			assertTrue(grafo.obtenerAristas().isEmpty());
		}

		@Test
		public void obtenerAristasDevuelveCopiaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.obtenerAristas().clear();

			assertEquals(1, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerAristasHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerAristasCantidadTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia2, provincia3, 50);

			assertEquals(2, grafo.obtenerAristas().size());
		}

		@Test
		public void obtenerAristasOrdenadasPorPesoTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);
			grafo.agregarArista(provincia1, provincia2, 30);
			grafo.agregarArista(provincia2, provincia3, 10);
			grafo.agregarArista(provincia3, provincia4, 20);

			assertEquals(10, grafo.obtenerAristas().get(0).devolverPeso());
			assertEquals(20, grafo.obtenerAristas().get(1).devolverPeso());
			assertEquals(30, grafo.obtenerAristas().get(2).devolverPeso());
		}
	}

	public static class ObtenerVerticesExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerVerticesGrafoVacioTest() {
			assertTrue(grafo.obtenerVertices().isEmpty());
		}

		@Test
		public void obtenerVerticesDevuelveCopiaTest() {
			grafo.agregarVertice(provincia1);
			grafo.obtenerVertices().clear();

			assertTrue(grafo.existeVertice(provincia1));
		}
	}

	public static class ObtenerVerticesHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerVerticesTodosLosVerticesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);

			assertEquals(3, grafo.obtenerVertices().size());
			assertTrue(grafo.obtenerVertices().contains(provincia1));
			assertTrue(grafo.obtenerVertices().contains(provincia2));
			assertTrue(grafo.obtenerVertices().contains(provincia3));
		}
	}

	public static class ExisteVerticeExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void existeVerticeNuloTest() {
			assertFalse(grafo.existeVertice(null));
		}

		@Test
		public void existeVerticeConOtroObjetoStringTest() {
			grafo.agregarVertice(provincia1);
			assertTrue(grafo.existeVertice(new String("Buenos Aires")));
		}
	}

	public static class ExisteVerticeHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void existeVerticeExistenteTest() {
			grafo.agregarVertice(provincia1);
			assertTrue(grafo.existeVertice(provincia1));
		}

		@Test
		public void existeVerticeInexistenteTest() {
			grafo.agregarVertice(provincia1);
			assertFalse(grafo.existeVertice(provincia2));
		}
	}

	public static class ExisteAristaExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(provincia2);
			grafo.existeArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(provincia1);
			grafo.existeArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaVerticeNuloTest() {
			grafo.agregarVertice(provincia1);
			grafo.existeArista(provincia1, null);
		}

		@Test
		public void existeAristaAlRevesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia2, provincia1, 100);

			assertTrue(grafo.existeArista(provincia1, provincia2));
		}

		@Test
		public void existeAristaConOtroObjetoYMismoNombreStringTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertTrue(grafo.existeArista(new String("Buenos Aires"), new String("Santa Fé")));
		}
	}

	public static class ExisteAristaHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void existeAristaExistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertTrue(grafo.existeArista(provincia1, provincia2));
		}

		@Test
		public void existeAristaInexistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertFalse(grafo.existeArista(provincia1, provincia3));
		}
	}

	public static class EsVacioExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void esVacioGrafoRecienCreadoTest() {
			assertTrue(grafo.esVacio());
		}

		@Test
		public void esVacioConUnVerticeAisladoTest() {
			grafo.agregarVertice(provincia1);
			assertFalse(grafo.esVacio());
		}
	}

	public static class EsVacioHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void esVacioConVerticesYAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertFalse(grafo.esVacio());
		}
	}

	public static class PesoTotalExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void pesoTotalGrafoVacioTest() {
			assertEquals(0, grafo.pesoTotal());
		}
	}

	public static class PesoTotalHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void pesoTotalSumaLosPesosDeLasAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 30);
			grafo.agregarArista(provincia2, provincia3, 10);
			grafo.agregarArista(provincia1, provincia3, 20);

			assertEquals(60, grafo.pesoTotal());
		}
	}

	public static class EliminarVerticeExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarVerticeInexistenteTest() {
			grafo.eliminarVertice(provincia1);
		}
	}

	public static class EliminarVerticeHappyPath {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void eliminarVerticeAisladoTest() {
			grafo.agregarVertice(provincia1);

			grafo.eliminarVertice(provincia1);

			assertFalse(grafo.existeVertice(provincia1));
			assertTrue(grafo.esVacio());
		}

		@Test
		public void eliminarVerticeEliminaSusAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);

			grafo.agregarArista(provincia1, provincia2, 4);
			grafo.agregarArista(provincia3, provincia1, 7);

			grafo.eliminarVertice(provincia1);

			assertFalse(grafo.existeVertice(provincia1));
			assertTrue(grafo.existeVertice(provincia2));
			assertTrue(grafo.existeVertice(provincia3));

			assertTrue(grafo.obtenerAristas().isEmpty());
			assertTrue(grafo.obtenerlistaDeVecinos().get(provincia2).isEmpty());
			assertTrue(grafo.obtenerlistaDeVecinos().get(provincia3).isEmpty());
		}

		@Test
		public void eliminarVerticeNoAfectaOtrasConexionesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);

			grafo.agregarArista(provincia1, provincia2, 4);
			grafo.agregarArista(provincia2, provincia3, 8);

			grafo.eliminarVertice(provincia1);

			assertEquals(2, grafo.obtenerVertices().size());
			assertEquals(1, grafo.obtenerAristas().size());
			assertTrue(grafo.existeArista(provincia2, provincia3));
			assertEquals(8, grafo.pesoTotal());

			assertEquals(1, grafo.obtenerlistaDeVecinos().get(provincia2).size());
			assertEquals(1, grafo.obtenerlistaDeVecinos().get(provincia3).size());
		}
	}

}
