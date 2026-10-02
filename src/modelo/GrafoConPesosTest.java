package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GrafoConPesosTest {

	private GrafoConPesos<String> grafo;
	private String provincia1;
	private String provincia2;
	private String provincia3;
	private String provincia4;

	@Before
	public void inicializar() {
		grafo = new GrafoConPesos<>();

		provincia1 = "Buenos Aires";
		provincia2 = "Córdoba";
		provincia3 = "Santa Fé";
		provincia4 = "Rio Negro";

	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeNulotest() {

		grafo.agregarVertice(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeRepetidoTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia1);
	}

	@Test
	public void agregarVerticeExitosoTest() {

		grafo.agregarVertice(provincia1);

		assertTrue(grafo.existeVertice(provincia1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaConFaltaDeVerticeTest() {

		grafo.agregarVertice(provincia1);

		grafo.eliminarArista(provincia1, provincia2);
	}

	@Test
	public void eliminarAristaExitosoTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, 100);
		grafo.eliminarArista(provincia1, provincia2);

		assertFalse(grafo.existeArista(provincia1, provincia2));
		assertFalse(grafo.existeArista(provincia2, provincia1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaPrimerVerticeNuloTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista(null, provincia2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaSegundoVerticeNuloTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista(provincia1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaPrimerVerticeInexistenteTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista("Salta", provincia2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void existeAristaVerticeNuloTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.existeArista(null, provincia2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void vecinosVerticeNuloTest() {

		grafo.vecinos(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void vecinosVerticeInexistenteTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.vecinos("Salta");
	}

	@Test(expected = IllegalArgumentException.class)
	public void gradoVerticeNuloTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.grado(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void gradoVerticeInexistenteTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.grado("Salta");
	}

	// Happy paths
	@Test
	public void grafoNuevoVacioTest() {

		assertEquals(0, grafo.tamano());
	}

	@Test
	public void tamanoTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarVertice(provincia3);

		assertEquals(3, grafo.tamano());
	}

	@Test
	public void existeVerticeInexistenteTest() {
		grafo.agregarVertice(provincia1);

		assertFalse(grafo.existeVertice(provincia3));
	}

	@Test
	public void vecinosTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarVertice(provincia3);
		grafo.agregarVertice(provincia4);
		grafo.agregarArista(provincia2, provincia1, 10);
		grafo.agregarArista(provincia2, provincia3, 20);

		assertEquals(2, grafo.vecinos(provincia2).size());
		assertTrue(grafo.vecinos(provincia2).contains(provincia1));
		assertTrue(grafo.vecinos(provincia2).contains(provincia3));
	}

	@Test
	public void gradoVerticeAisladoTest() {
		grafo.agregarVertice(provincia1);

		assertEquals(0, grafo.grado(provincia1));
	}

	@Test
	public void gradoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarVertice(provincia3);

		grafo.agregarArista(provincia1, provincia2, 10);
		grafo.agregarArista(provincia1, provincia3, 20);

		assertEquals(2, grafo.grado(provincia1));
		assertEquals(1, grafo.grado(provincia2));
	}

	@Test
	public void gradoDespuesDeEliminarAristaTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarArista(provincia1, provincia2, 10);

		grafo.eliminarArista(provincia1, provincia2);

		assertEquals(0, grafo.grado(provincia1));
		assertEquals(0, grafo.grado(provincia2));
	}

}
