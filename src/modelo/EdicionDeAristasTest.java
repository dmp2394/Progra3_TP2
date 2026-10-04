<<<<<<< HEAD
//package modelo;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//public class EdicionDeAristasTest {
//	@Test(expected = IllegalArgumentException.class)
//	public void primerVerticeNegativoTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(-1, 3, 1);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void primerVerticeExcedidoTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(5, 2, 1);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void segundoVerticeNegativoTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, -1, 1);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void segundoVerticeExcedidoTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 5, 1);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void agregarLoopTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 2, 1);
//	}
//
//	@Test
//	public void aristaExistenteTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 3, 1);
//		assertTrue(grafo.existeArista(2, 3));
//	}
//
//	@Test
//	public void aristaOpuestaTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 3, 1);
//		assertTrue(grafo.existeArista(3, 2));
//	}
//
//	@Test
//	public void aristaInexistenteTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 3, 1);
//		assertFalse(grafo.existeArista(1, 4));
//	}
//
//	@Test
//	public void agregarAristaDosVecesTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 3, 1);
//		grafo.agregarAristaConPeso(2, 3, 1);
//
//		assertTrue(grafo.existeArista(2, 3));
//	}
//
//	@Test
//	public void eliminarAristaExistenteTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 4, 1);
//
//		grafo.eliminarArista(2, 4);
//		assertFalse(grafo.existeArista(2, 4));
//	}
//
//	@Test
//	public void eliminarAristaInexistenteTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.eliminarArista(2, 4);
//		assertFalse(grafo.existeArista(2, 4));
//	}
//
//	@Test
//	public void eliminarAristaDosVecesTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(2, 4, 1);
//
//		grafo.eliminarArista(2, 4);
//		grafo.eliminarArista(2, 4);
//		assertFalse(grafo.existeArista(2, 4));
//	}
//}
=======
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
>>>>>>> 6b4b6bc339de46536c8cff5ecf899bebff50d484
