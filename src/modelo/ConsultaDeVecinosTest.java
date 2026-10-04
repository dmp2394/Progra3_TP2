<<<<<<< HEAD
//package modelo;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//public class ConsultaDeVecinosTest {
//	@Test(expected = IllegalArgumentException.class)
//	public void verticeNegativoTest() {
//		Grafo grafo = new Grafo(5);
//		grafo.vecinos(-1);
=======
package modelo;

public class ConsultaDeVecinosTest {
//	@Test(expected = IllegalArgumentException.class)
//	public void verticeNegativoTest() {
//		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
//		GrafoConPesos.vecinos(-1);
>>>>>>> 6b4b6bc339de46536c8cff5ecf899bebff50d484
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void verticeExcedidoTest() {
<<<<<<< HEAD
//		Grafo grafo = new Grafo(5);
//		grafo.vecinos(5);
=======
//		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
//		GrafoConPesos.vecinos(5);
>>>>>>> 6b4b6bc339de46536c8cff5ecf899bebff50d484
//	}
//
//	@Test
//	public void todosAisladosTest() {
<<<<<<< HEAD
//		Grafo grafo = new Grafo(5);
//		assertEquals(0, grafo.vecinos(2).size());
=======
//		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
//		assertEquals(0, GrafoConPesos.vecinos(2).size());
>>>>>>> 6b4b6bc339de46536c8cff5ecf899bebff50d484
//	}
//
//	@Test
//	public void verticeUniversalTest() {
<<<<<<< HEAD
//		Grafo grafo = new Grafo(4);
//		grafo.agregarAristaConPeso(1, 0, 1);
//		grafo.agregarAristaConPeso(1, 2, 1);
//		grafo.agregarAristaConPeso(1, 3, 1);
//
//		int[] esperado = { 0, 2, 3 };
//		Assert.iguales(esperado, grafo.vecinos(1));
=======
//		GrafoConPesos GrafoConPesos = new GrafoConPesos(4);
//		GrafoConPesos.agregarAristaConPeso(1, 0, 1);
//		GrafoConPesos.agregarAristaConPeso(1, 2, 1);
//		GrafoConPesos.agregarAristaConPeso(1, 3, 1);
//
//		int[] esperado = { 0, 2, 3 };
//		Assert.iguales(esperado, GrafoConPesos.vecinos(1));
>>>>>>> 6b4b6bc339de46536c8cff5ecf899bebff50d484
//	}
//
//	@Test
//	public void verticeNormalTest() {
<<<<<<< HEAD
//		Grafo grafo = new Grafo(5);
//		grafo.agregarAristaConPeso(1, 3, 1);
//		grafo.agregarAristaConPeso(2, 3, 1);
//		grafo.agregarAristaConPeso(2, 4, 1);
//
//		int[] esperados = { 1, 2 };
//		Assert.iguales(esperados, grafo.vecinos(3));
//	}
//}
=======
//		GrafoConPesos GrafoConPesos = new GrafoConPesos(5);
//		GrafoConPesos.agregarAristaConPeso(1, 3, 1);
//		GrafoConPesos.agregarAristaConPeso(2, 3, 1);
//		GrafoConPesos.agregarAristaConPeso(2, 4, 1);
//
//		int[] esperados = { 1, 2 };
//		Assert.iguales(esperados, GrafoConPesos.vecinos(3));
//	}
}
>>>>>>> 6b4b6bc339de46536c8cff5ecf899bebff50d484
