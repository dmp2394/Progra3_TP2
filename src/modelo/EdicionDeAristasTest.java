package modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EdicionDeAristasTest {

	private GrafoConPesos<String> grafo;
	private String provincia1;
	private String provincia2;
	private String provincia3;

	@Before
	public void inicializar() {
		grafo = new GrafoConPesos<>();

		provincia1 = "Buenos Aires";
		provincia2 = "Córdoba";
		provincia3 = "Santa Fé";

	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaPrimerVerticeNuloTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(null, provincia2, 100);
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

		grafo.agregarArista(provincia1, provincia2, -5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaPrimerVerticeInexistenteTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista("Salta", provincia2, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaSegundoVerticeNuloTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, null, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaSegundoVerticeInexistenteTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, "Salta", 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarArista(provincia1, provincia1, 100);
	}

	@Test
	public void aristaExistenteTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarArista(provincia1, provincia2, 100);

		assertTrue(grafo.existeArista(provincia1, provincia2));
	}

	@Test
	public void existeAristaOpuestaTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, 100);

		assertTrue(grafo.existeArista(provincia2, provincia1));
	}

	@Test
	public void aristaInexistenteTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.agregarVertice(provincia3);

		grafo.agregarArista(provincia1, provincia2, 100);

		grafo.existeArista(provincia1, provincia3);
	}

	@Test
	public void agregarAristaDosVecesTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.agregarArista(provincia1, provincia2, 100);
		grafo.agregarArista(provincia1, provincia2, 100);
	}

	@Test
	public void eliminarAristaExistenteTest() {

		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista(provincia1, provincia2);

		assertFalse(grafo.existeArista(provincia1, provincia2));
	}

	@Test
	public void eliminarAristaInexistenteTest() {
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);

		grafo.eliminarArista(provincia1, provincia2);
	}

	@Test
	public void eliminarAristaDosVecesTest() {

	}
}
