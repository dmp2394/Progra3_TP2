package modelo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ProvinciaTest {

	public static class ConstructorExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void nombreNuloTest() {
			new Provincia(null, -31.42, -64.18);
		}

		@Test(expected = IllegalArgumentException.class)
		public void nombreVacioTest() {
			new Provincia("", -31.42, -64.18);
		}

		@Test(expected = IllegalArgumentException.class)
		public void nombreSoloEspaciosTest() {
			new Provincia("   ", -31.42, -64.18);
		}

		@Test(expected = IllegalArgumentException.class)
		public void latitudMenorAMenos90Test() {
			new Provincia("Córdoba", -90.1, -64.18);
		}

		@Test(expected = IllegalArgumentException.class)
		public void latitudMayorA90Test() {
			new Provincia("Córdoba", 90.1, -64.18);
		}

		@Test(expected = IllegalArgumentException.class)
		public void latitudNaNTest() {
			new Provincia("Córdoba", Double.NaN, -64.18);
		}

		@Test(expected = IllegalArgumentException.class)
		public void longitudMenorAMenos180Test() {
			new Provincia("Córdoba", -31.42, -180.1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void longitudMayorA180Test() {
			new Provincia("Córdoba", -31.42, 180.1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void longitudNaNTest() {
			new Provincia("Córdoba", -31.42, Double.NaN);
		}

		@Test
		public void coordenadasCeroTest() {
			Provincia provincia = new Provincia("Córdoba", 0, 0);

			assertEquals(Arrays.asList(0.0, 0.0), Arrays.asList(provincia.obtenerLatitud(), provincia.obtenerLongitud()));
		}

		@Test
		public void coordenadasEnLosLimitesInferioresTest() {
			Provincia provincia = new Provincia("Córdoba", -90, -180);

			assertEquals(Arrays.asList(-90.0, -180.0),
					Arrays.asList(provincia.obtenerLatitud(), provincia.obtenerLongitud()));
		}

		@Test
		public void coordenadasEnLosLimitesSuperioresTest() {
			Provincia provincia = new Provincia("Córdoba", 90, 180);

			assertEquals(Arrays.asList(90.0, 180.0),
					Arrays.asList(provincia.obtenerLatitud(), provincia.obtenerLongitud()));
		}

		@Test
		public void nombreConEspaciosSeGuardaSinEspaciosTest() {
			Provincia provincia = new Provincia("  Córdoba  ", -31.42, -64.18);

			assertEquals("Córdoba", provincia.obtenerNombre());
		}
	}

	public static class ConstructorCasoFeliz {

		@Test
		public void constructorGuardaNombreTest() {
			Provincia provincia = new Provincia("Córdoba", -31.42, -64.18);

			assertEquals("Córdoba", provincia.obtenerNombre());
		}

		@Test
		public void constructorGuardaCoordenadasTest() {
			Provincia provincia = new Provincia("Córdoba", -31.42, -64.18);

			assertEquals(Arrays.asList(-31.42, -64.18),
					Arrays.asList(provincia.obtenerLatitud(), provincia.obtenerLongitud()));
		}
	}
}
