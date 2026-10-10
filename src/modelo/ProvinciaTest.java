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

			assertEquals(0, provincia.obtenerX());
			assertEquals(0, provincia.obtenerY());
		}
	}

	public static class Constructor {

		@Test
		public void constructorGuardaNombreTest() {
			Provincia provincia = new Provincia("Córdoba", 320, 180);

			assertEquals("Córdoba", provincia.obtenerNombre());
		}
		@Test
		public void constructorGuardaCoordenadasTest() {
			Provincia provincia = new Provincia("Córdoba", 320, 180);
			boolean coordenadasCorrectas = provincia.obtenerX() == 320 && provincia.obtenerY() == 180;
			
			assertEquals(true, coordenadasCorrectas);
		}
	}
}
