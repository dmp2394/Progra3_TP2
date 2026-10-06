package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;

public class AristaTest {

	private String extremo1 = "Buenos Aires";
	private String extremo2 = "Santa Fé";
	private String extremo3 = "Rio Negro";
	private String extremo4 = "Santa Cruz";

	Arista<String> arista12YPeso100 = new Arista<>(extremo1, extremo2, 100);
	Arista<String> arista34YPeso100 = new Arista<>(extremo3, extremo4, 100);
	Arista<String> arista34YPeso50 = new Arista<>(extremo3, extremo4, 50);

	// constructor excepciones y casos borde
	// Hoy Arista no valida nada (lo valida Grafo): decidir qué se espera en cada
	// caso
	@Test(expected = IllegalArgumentException.class)
	public void noPermiteCrearAristaConExtremo1NuloTest() {
		Arista<String> arista = new Arista<>(null, extremo2, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void noPermiteCrearAristaConExtremo2NuloTest() {
		Arista<String> arista = new Arista<>(extremo1, null, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void noPermiteCrearAristaConAmbosExtremosNulosTest() {
		Arista<String> arista = new Arista<>(null, null, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void noPermiteCrearAristaConLoopTest() {
		Arista<String> arista = new Arista<>(extremo1, extremo1, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void noPermiteCrearAristaConPesoCeroTest() {
		Arista<String> arista = new Arista<>(extremo1, extremo2, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void noPermiteCrearAristaConPesoNegativoTest() {
		Arista<String> arista = new Arista<>(extremo1, extremo2, -100);
	}

	// getters happy path
	@Test
	public void devolverPesoTest() {
		assertEquals(100, arista12YPeso100.devolverPeso());
	}

	@Test
	public void obtenerExtremo1Test() {
		Arista<String> arista = new Arista<>("Extremo 1", "Extremo 2", 100);
		assertEquals(extremo1, arista12YPeso100.obtenerExtremo1());
	}

	@Test
	public void obtenerExtremo2Test() {
		Arista<String> arista = new Arista<>("Extremo 1", "Extremo 2", 100);
		assertEquals(extremo2, arista12YPeso100.obtenerExtremo2());
	}

	// compareTo excepciones y casos borde
	@Test(expected = IllegalArgumentException.class)
	public void compareToAristaNulaTest() {
		arista12YPeso100.compareTo(null);
	}

	@Test
	public void compareToMismoPesoTest() {
		assertEquals(0, arista12YPeso100.compareTo(arista12YPeso100));
	}

	// compareTo happy path
	@Test
	public void compareToMenorPesoTest() {
		assertEquals(1, arista12YPeso100.compareTo(arista34YPeso50));
	}

	@Test
	public void compareToMayorPesoTest() {
		assertEquals(-1, arista34YPeso50.compareTo(arista12YPeso100));
	}

	// equals excepciones y casos borde
	@Test
	public void equalsNuloTest() {
		fail("Sin implementar");
	}

	@Test
	public void equalsOtraClaseTest() {
		fail("Sin implementar");
	}

	@Test
	public void equalsMismoObjetoTest() {
		fail("Sin implementar");
	}

	@Test
	public void equalsExtremosAlRevesTest() {
		fail("Sin implementar");
	}

	@Test
	public void equalsMismosExtremosDistintoPesoTest() {
		fail("Sin implementar");
	}

	// equals happy path
	@Test
	public void equalsMismosExtremosTest() {
		fail("Sin implementar");
	}

	@Test
	public void equalsDistintosExtremosTest() {
		fail("Sin implementar");
	}

	@Test
	public void equalsSimetricoTest() {
		fail("Sin implementar");
	}

	// hashCode excepciones y casos borde
	@Test
	public void hashCodeExtremosAlRevesTest() {
		Arista<String> arista1 = new Arista<>("A", "B", 4);
		Arista<String> arista2 = new Arista<>("B", "A", 4);

		assertEquals(arista1.hashCode(), arista2.hashCode());
	}

	// hashCode happy path
	@Test
	public void hashCodeAristasIgualesTest() {
		Arista<String> arista1 = new Arista<>("A", "B", 4);
		Arista<String> arista2 = new Arista<>("A", "B", 9);

		assertEquals(arista1, arista2);
		assertEquals(arista1.hashCode(), arista2.hashCode());
	}

	@Test
	public void hashCodeContainsEnHashSetTest() {
		HashSet<Arista<String>> aristas = new HashSet<>();

		Arista<String> arista1 = new Arista<>("A", "B", 4);
		Arista<String> arista2 = new Arista<>("B", "A", 9);

		aristas.add(arista1);

		assertTrue(aristas.contains(arista2));

		aristas.add(arista2);

		assertEquals(1, aristas.size());
	}

}
