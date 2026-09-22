package modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConsultaDeVecinosTest {
	@Test(expected = IllegalArgumentException.class)
	public void verticeNegativoTest() {
		Grafo grafo = new Grafo(5);
		grafo.vecinos(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verticeExcedidoTest() {
		Grafo grafo = new Grafo(5);
		grafo.vecinos(5);
	}

	@Test
	public void todosAisladosTest() {
		Grafo grafo = new Grafo(5);
		assertEquals(0, grafo.vecinos(2).size());
	}

	@Test
	public void verticeUniversalTest() {
		Grafo grafo = new Grafo(4);
		grafo.agregarAristaConPeso(1, 0, 1);
		grafo.agregarAristaConPeso(1, 2, 1);
		grafo.agregarAristaConPeso(1, 3, 1);

		int[] esperado = { 0, 2, 3 };
		Assert.iguales(esperado, grafo.vecinos(1));
	}

	@Test
	public void verticeNormalTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarAristaConPeso(1, 3, 1);
		grafo.agregarAristaConPeso(2, 3, 1);
		grafo.agregarAristaConPeso(2, 4, 1);

		int[] esperados = { 1, 2 };
		Assert.iguales(esperados, grafo.vecinos(3));
	}
}
