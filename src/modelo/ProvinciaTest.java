package modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ProvinciaTest {

	public static class ConstructorExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void nombreNuloTest() {
			new Provincia(null, 320, 180);
		}

		@Test(expected = IllegalArgumentException.class)
		public void nombreVacioTest() {
			new Provincia("", 320, 180);
		}

		@Test(expected = IllegalArgumentException.class)
		public void coordenadaXNegativaTest() {
			new Provincia("Córdoba", -1, 180);
		}

		@Test(expected = IllegalArgumentException.class)
		public void coordenadaYNegativaTest() {
			new Provincia("Córdoba", 320, -1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void nombreSoloEspaciosTest() {
			new Provincia("   ", 320, 180);
		}

		@Test
		public void coordenadasCeroTest() {
			Provincia provincia = new Provincia("Córdoba", 0, 0);

			assertEquals(0, provincia.getX());
			assertEquals(0, provincia.getY());
		}
	}

	public static class ConstructorHappyPath {

		@Test
		public void constructorGuardaDatosTest() {
			Provincia provincia = new Provincia("Córdoba", 320, 180);

			assertEquals("Córdoba", provincia.getNombre());
			assertEquals(320, provincia.getX());
			assertEquals(180, provincia.getY());
		}
	}
}
