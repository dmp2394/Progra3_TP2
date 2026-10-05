package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: REVISAR EN GENERAL POR LA ESTRUCTURA
// TODO: SUMAR TESTS PESO TOTAL
public class GrafoTest {

	private Grafo<String> grafo = new Grafo<>();
	private String provincia1 = "Buenos Aires";
	private String provincia2 = "Santa Fé";
	private String provincia3 = "La Pampa";
	private String provincia4 = "Entre Ríos";

	// agregarVertice excepciones y casos borde
	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeNuloTest() {
		grafo.agregarVertice(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeYaExistenteTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia1);
	}

	// agregarVertice happy path
	@Test
	public void agregarVerticeHappyPathTest() {
		grafo.agregarVertice(provincia1);
		assertTrue(grafo.existeVertice(provincia1));
	}

	// agregarArista excepciones y casos borde
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

	// agregarArista happy path
	@Test
	public void agregarAristaHappyPathTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarArista(provincia1, provincia2, 100);

		assertTrue(grafo.existeArista(provincia1, provincia2));
		assertTrue(grafo.existeArista(provincia2, provincia1));
	}

	// eliminarArista excepciones y casos borde
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

	// eliminarArista happy path
	@Test
	public void eliminarAristaHappyPathTest() {
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

	// obtenerListaDeVecinos excepciones y casos borde
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

	// obtenerListaDeVecinos happy path
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

	// obtenerAristas excepciones y casos borde
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

	// obtenerAristas happy path
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

	// obtenerVertices excepciones y casos borde
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

	// obtenerVertices happy path
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

	// existeVertice excepciones y casos borde
	@Test
	public void existeVerticeNuloTest() {
		assertFalse(grafo.existeVertice(null));
	}

	@Test
	public void existeVerticeConOtroObjetoStringTest() {
		grafo.agregarVertice(provincia1);
		assertTrue(grafo.existeVertice(new String("Buenos Aires")));
	}

	// existeVertice happy path
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

	// existeArista excepciones y casos borde
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
	public void existeAristaConOtroObjetoStringTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarArista(provincia1, provincia2, 100);

		assertTrue(grafo.existeArista(new String("Buenos Aires"), new String("Santa Fé")));
	}

	// existeArista happy path
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

	// esVacio excepciones y casos borde
	@Test
	public void esVacioGrafoRecienCreadoTest() {
		assertTrue(grafo.esVacio());
	}

	@Test
	public void esVacioConUnVerticeAisladoTest() {
		grafo.agregarVertice(provincia1);
		assertFalse(grafo.esVacio());
	}

	// esVacio happy path
	@Test
	public void esVacioConVerticesYAristasTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarArista(provincia1, provincia2, 100);

		assertFalse(grafo.esVacio());
	}
	
	// Comprueba que eliminar un vértice inexistente lance
	// una IllegalArgumentException.
	@Test(expected = IllegalArgumentException.class)
	public void eliminarVerticeInexistenteTest() {
	    grafo.eliminarVertice(provincia1);
	}

	// Comprueba que se pueda eliminar un vértice sin conexiones
	// y que el grafo quede vacío si era su único vértice.
	@Test
	public void eliminarVerticeAisladoTest() {
	    grafo.agregarVertice(provincia1);

	    grafo.eliminarVertice(provincia1);

	    assertFalse(grafo.existeVertice(provincia1));
	    assertTrue(grafo.esVacio());
	}

	// Comprueba que eliminar un vértice quite todas sus aristas
	// de la lista general y de los conjuntos de sus vecinos,
	// conservando los demás vértices.
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
	    assertTrue(
	            grafo.obtenerlistaDeVecinos().get(provincia2).isEmpty());
	    assertTrue(
	            grafo.obtenerlistaDeVecinos().get(provincia3).isEmpty());
	}

	// Comprueba que eliminar un vértice conserve las conexiones
	// entre los otros vértices y que el peso total se actualice.
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

	    assertEquals(
	            1, grafo.obtenerlistaDeVecinos().get(provincia2).size());
	    assertEquals(
	            1, grafo.obtenerlistaDeVecinos().get(provincia3).size());
	}
	

}
