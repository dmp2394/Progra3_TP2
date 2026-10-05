package modelo;

import static org.junit.Assert.fail;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

public class AristaTest {

	// constructor excepciones y casos borde
	// Hoy Arista no valida nada (lo valida Grafo): decidir qué se espera en cada caso
	@Test
	public void constructorExtremo1NuloTest() {
		fail("Sin implementar");
	}

	@Test
	public void constructorExtremo2NuloTest() {
		fail("Sin implementar");
	}

	@Test
	public void constructorMismoExtremoTest() {
		fail("Sin implementar");
	}

	@Test
	public void constructorPesoCeroTest() {
		fail("Sin implementar");
	}

	@Test
	public void constructorPesoNegativoTest() {
		fail("Sin implementar");
	}

	// constructor happy path
	@Test
	public void constructorGuardaExtremosYPesoTest() {
		fail("Sin implementar");
	}

	// compareTo excepciones y casos borde
	@Test
	public void compareToMismoPesoTest() {
		fail("Sin implementar");
	}

	@Test
	public void compareToAristaNulaTest() {
		fail("Sin implementar");
	}

	// compareTo happy path
	@Test
	public void compareToMenorPesoTest() {
		fail("Sin implementar");
	}

	@Test
	public void compareToMayorPesoTest() {
		fail("Sin implementar");
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
