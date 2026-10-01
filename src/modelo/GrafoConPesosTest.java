package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GrafoConPesosTest {

	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeNulotest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<String>();

		grafo.agregarVertice(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeRepetidoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia1);
	}

	@Test
	public void agregarVerticeExitosoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";

		grafo.agregarVertice(provincia1);

		assertTrue(grafo.existeVertice(provincia1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarBucletest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		grafo.agregarVertice(provincia1);
		grafo.agregarArista(provincia1, provincia1, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaDuplicadaTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, 100);
		grafo.agregarArista(provincia1, provincia2, 100);
	}

	@Test
	public void agregarAristaExitosoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, 100);
		assertTrue(grafo.existeArista(provincia1, provincia2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaConFaltaDeVerticeTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);

		grafo.eliminarArista(provincia1, provincia2);
	}

	@Test
	public void eliminarAristaInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista(provincia1, provincia2);

		assertFalse(grafo.existeArista(provincia1, provincia2));
	}

	@Test
	public void eliminarAristaExitosoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, 100);
		grafo.eliminarArista(provincia1, provincia2);

		assertFalse(grafo.existeArista(provincia1, provincia2));
		assertFalse(grafo.existeArista(provincia2, provincia1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaPrimerVerticeNuloTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(null, provincia2, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaSegundoVerticeNuloTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, null, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaPrimerVerticeInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista("Salta", provincia2, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaSegundoVerticeInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, "Salta", 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaPesoCeroTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaPesoNegativoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, -5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaPrimerVerticeNuloTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista(null, provincia2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaSegundoVerticeNuloTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista(provincia1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaPrimerVerticeInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista("Salta", provincia2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void existeAristaVerticeNuloTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.existeArista(null, provincia2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void existeAristaVerticeInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.existeArista(provincia1, "Salta");
	}

	@Test(expected = IllegalArgumentException.class)
	public void existeAristaBucleTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.existeArista(provincia1, provincia1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void vecinosVerticeNuloTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.vecinos(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void vecinosVerticeInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.vecinos("Salta");
	}

	@Test(expected = IllegalArgumentException.class)
	public void gradoVerticeNuloTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.grado(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void gradoVerticeInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		String provincia1 = "Buenos Aires";
		String provincia2 = "Córdoba";
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.grado("Salta");
	}

	// Happy paths

	@Test
	public void grafoNuevoVacioTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();

		assertEquals(0, grafo.tamano());
	}

	@Test
	public void tamanoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");
		grafo.agregarVertice("Salta");

		assertEquals(3, grafo.tamano());
	}

	@Test
	public void existeVerticeInexistenteTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");

		assertFalse(grafo.existeVertice("Córdoba"));
	}

	@Test
	public void existeAristaSinAgregarlaTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");

		assertFalse(grafo.existeArista("Buenos Aires", "Córdoba"));
	}

	@Test
	public void agregarAristaSentidoInversoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");

		grafo.agregarArista("Buenos Aires", "Córdoba", 100);

		assertTrue(grafo.existeArista("Córdoba", "Buenos Aires"));
	}

	@Test
	public void vecinosVerticeAisladoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");

		assertEquals(0, grafo.vecinos("Buenos Aires").size());
	}

	@Test
	public void vecinosTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");
		grafo.agregarVertice("Santa Fe");
		grafo.agregarVertice("Salta");
		grafo.agregarArista("Córdoba", "Buenos Aires", 10);
		grafo.agregarArista("Córdoba", "Santa Fe", 20);

		assertEquals(2, grafo.vecinos("Córdoba").size());
		assertTrue(grafo.vecinos("Córdoba").contains("Buenos Aires"));
		assertTrue(grafo.vecinos("Córdoba").contains("Santa Fe"));
	}

	@Test
	public void vecinosNoModificaElGrafoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");
		grafo.agregarArista("Buenos Aires", "Córdoba", 100);

		grafo.vecinos("Buenos Aires").remove("Córdoba");

		assertTrue(grafo.existeArista("Buenos Aires", "Córdoba"));
	}

	@Test
	public void gradoVerticeAisladoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");

		assertEquals(0, grafo.grado("Buenos Aires"));
	}

	@Test
	public void gradoTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");
		grafo.agregarVertice("Santa Fe");
		grafo.agregarArista("Buenos Aires", "Córdoba", 10);
		grafo.agregarArista("Buenos Aires", "Santa Fe", 20);

		assertEquals(2, grafo.grado("Buenos Aires"));
		assertEquals(1, grafo.grado("Córdoba"));
	}

	@Test
	public void gradoDespuesDeEliminarAristaTest() {
		GrafoConPesos<String> grafo = new GrafoConPesos<>();
		grafo.agregarVertice("Buenos Aires");
		grafo.agregarVertice("Córdoba");
		grafo.agregarArista("Buenos Aires", "Córdoba", 10);

		grafo.eliminarArista("Buenos Aires", "Córdoba");

		assertEquals(0, grafo.grado("Buenos Aires"));
		assertEquals(0, grafo.grado("Córdoba"));
	}

}
