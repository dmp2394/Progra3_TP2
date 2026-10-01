package modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EdicionDeAristasTest {
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeNegativoTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(-1, 3, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeExcedidoTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(5, 2, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeNegativoTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeExcedidoTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 5, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 2, 1);
	}

	@Test
	public void aristaExistenteTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 3, 1);
		assertTrue(GrafoConPesos.existeArista(2, 3));
	}

	@Test
	public void aristaOpuestaTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 3, 1);
		assertTrue(GrafoConPesos.existeArista(3, 2));
	}

	@Test
	public void aristaInexistenteTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 3, 1);
		assertFalse(GrafoConPesos.existeArista(1, 4));
	}

	@Test
	public void agregarAristaDosVecesTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 3, 1);
		GrafoConPesos.agregarAristaConPeso(2, 3, 1);

		assertTrue(GrafoConPesos.existeArista(2, 3));
	}

	@Test
	public void eliminarAristaExistenteTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 4, 1);

		GrafoConPesos.eliminarArista(2, 4);
		assertFalse(GrafoConPesos.existeArista(2, 4));
	}

	@Test
	public void eliminarAristaInexistenteTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.eliminarArista(2, 4);
		assertFalse(GrafoConPesos.existeArista(2, 4));
	}

	@Test
	public void eliminarAristaDosVecesTest() {
		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
		GrafoConPesos.agregarAristaConPeso(2, 4, 1);

		GrafoConPesos.eliminarArista(2, 4);
		GrafoConPesos.eliminarArista(2, 4);
		assertFalse(GrafoConPesos.existeArista(2, 4));
	}
}
